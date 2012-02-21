/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
package org.globus.wsrf.impl.security.authentication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.client.AxisClient;
import org.apache.axis.configuration.NullProvider;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPEnvelope;

import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSManager;

import org.globus.gsi.gssapi.GSSConstants;
import org.globus.wsrf.impl.security.authentication.encryption.GssEncryptedSOAPEnvelopeBuilder;
import org.globus.wsrf.impl.security.authentication.secureconv.service.SecurityContext;
import org.globus.wsrf.impl.security.authentication.wssec.GSSConfig;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class TestGssXMLEncryption extends TestCase {
    static Log logger = LogFactory.getLog(TestGssXMLEncryption.class.getName());
    SecurityContext clientContext;
    SecurityContext serverContext;
    MessageContext msgContext;
    static final String NS = "http://www.w3.org/2000/09/xmldsig#";
    static final String soapMsg =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" +
        "<SOAP-ENV:Body>\r\n" +
        "<add xmlns=\"http://samples.wsrf.globus.org/counter/counter_port_type\">\r\n" +
        "<value xmlns=\"\">15</value>\r\n" + "</add>\r\n" +
        "</SOAP-ENV:Body>\r\n" + "</SOAP-ENV:Envelope>";
    SOAPEnvelope clearEnvelope;

    static {
        GSSConfig.init();
    }

    public TestGssXMLEncryption(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TestGssXMLEncryption.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    protected void setUp() throws Exception {
        createContexts();

        AxisClient tmpEngine = new AxisClient(new NullProvider());
        msgContext = new MessageContext(tmpEngine);

        clearEnvelope = getSOAPEnvelope();
    }

    protected SOAPEnvelope getSOAPEnvelope() throws Exception {
        InputStream in = new ByteArrayInputStream(soapMsg.getBytes());
        Message msg = new Message(in);
        msg.setMessageContext(msgContext);

        return msg.getSOAPEnvelope();
    }

    protected void createContexts() throws Exception {
        GSSManager manager = ExtendedGSSManager.getInstance();
        GSSContext context = manager.createContext((GSSCredential) null);
        String id = "" + context.hashCode();
        serverContext = new SecurityContext(context, id);
        context =
            manager.createContext(null, GSSConstants.MECH_OID, null,
                                  GSSContext.DEFAULT_LIFETIME);
        clientContext = new SecurityContext(context, id);

        clientContext.getContext().requestConf(false);
        clientContext.getContext().requestCredDeleg(false);
    }

    protected void tearDown() throws Exception {
        if (clientContext != null) {
            clientContext.getContext().dispose();
        }

        if (serverContext != null) {
            serverContext.getContext().dispose();
        }
    }

    private void establishContext() throws Exception {
        assertTrue("client ctx already established.",
                   !clientContext.getContext().isEstablished());
        assertTrue("server ctx already established.",
                   !serverContext.getContext().isEstablished());

        byte[] inToken = new byte[0];
        byte[] outToken = null;

        while (!clientContext.getContext().isEstablished()) {
            outToken = clientContext.getContext().initSecContext(inToken, 0,
                                                    inToken.length);

            if (outToken != null) {
                inToken = serverContext.getContext().acceptSecContext(outToken, 0,
                                                         outToken.length);
            }
        }

        assertTrue("client ctx not established.",
                   clientContext.getContext().isEstablished());
        assertTrue("server ctx not established.",
                   serverContext.getContext().isEstablished());
    }

    // tests
    public void testSuccessfulDecrypt() throws Exception {
        establishContext();

        SOAPEnvelope encryptedEnvelope = null;
        GssEncryptedSOAPEnvelopeBuilder builder =
            new GssEncryptedSOAPEnvelopeBuilder(null, clientContext);
        encryptedEnvelope = (SOAPEnvelope) builder.build(clearEnvelope);
        decrypt(encryptedEnvelope, serverContext);
    }

    public void testRemoveBodyDecrypt() throws Exception {
        establishContext();

        SOAPEnvelope encryptedEnvelope = null;
        GssEncryptedSOAPEnvelopeBuilder builder =
            new GssEncryptedSOAPEnvelopeBuilder(null, clientContext);
        encryptedEnvelope = (SOAPEnvelope) builder.build(clearEnvelope);

        SOAPBodyElement el = encryptedEnvelope.getFirstBody();

        encryptedEnvelope.removeBodyElement(el);

        try {
            decrypt(encryptedEnvelope, serverContext);
            fail("Encryption verification did not fail as expected.");
        } catch (Exception e) {
            // this should fail
            logger.debug(e);
        }
    }

    // should fail
    public void testDecryptWithWrongContext() throws Exception {
        establishContext();

        SecurityContext pClientContext = clientContext;
        SecurityContext pServerContext = serverContext;

        createContexts();

        establishContext();

        SOAPEnvelope encryptedEnvelope = null;
        GssEncryptedSOAPEnvelopeBuilder builder =
            new GssEncryptedSOAPEnvelopeBuilder(null, clientContext);
        encryptedEnvelope = (SOAPEnvelope) builder.build(clearEnvelope);

        try {
            // verify with old server context
            decrypt(encryptedEnvelope, pServerContext);
            fail("Encryption verification did not fail as expected.");
        } catch (Exception e) {
            // this should fail
            logger.debug(e);
        } finally {
            try {
                pClientContext.getContext().dispose();
            } catch (Exception e) {
            }

            try {
                pServerContext.getContext().dispose();
            } catch (Exception e) {
            }
        }
    }

    private void decrypt(
        SOAPEnvelope env,
        SecurityContext context
    ) throws Exception {
        VerifyWSSecurity h = new VerifyWSSecurity(context.getContext());
        msgContext.setProperty(Constants.CONTEXT, context);
        h.processSecurityHeader(env, "", msgContext, false);
        msgContext.setProperty(Constants.CONTEXT, null);
    }
}
