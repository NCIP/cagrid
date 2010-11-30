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

import org.apache.xml.security.signature.XMLSignatureException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.client.AxisClient;
import org.apache.axis.configuration.NullProvider;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPHeaderElement;

import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import org.globus.gsi.gssapi.GSSConstants;
import org.globus.wsrf.impl.security.authentication.signature.GssSignedSOAPEnvelopeBuilder;
import org.globus.wsrf.impl.security.authentication.wssec.GSSConfig;
import org.globus.wsrf.impl.security.authentication.wssec.WSSecurityException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestGssXMLSignature extends TestCase {
    static Log logger = LogFactory.getLog(TestGssXMLSignature.class.getName());
    GSSContext clientContext;
    GSSContext serverContext;
    MessageContext msgContext;
    static final String NS = "http://www.w3.org/2000/09/xmldsig#";
    static final String soapMsg =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" +
        "<SOAP-ENV:Body>\r\n" +
        "<add xmlns=\"http://samples.wsrf.globus.org/counter/counter_port_type\">\r\n" +
        "<value xmlns=\"\">15</value>\r\n" + "</add>\r\n" +
        "</SOAP-ENV:Body>\r\n" + "</SOAP-ENV:Envelope>";
    SOAPEnvelope unsignedEnvelope;

    static {
        GSSConfig.init();
    }

    public TestGssXMLSignature(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TestGssXMLSignature.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    protected void setUp() throws Exception {
        createContexts();

        AxisClient tmpEngine = new AxisClient(new NullProvider());
        msgContext = new MessageContext(tmpEngine);

        unsignedEnvelope = getSOAPEnvelope();
    }

    protected SOAPEnvelope getSOAPEnvelope() throws Exception {
        InputStream in = new ByteArrayInputStream(soapMsg.getBytes());
        Message msg = new Message(in);
        msg.setMessageContext(msgContext);

        return msg.getSOAPEnvelope();
    }

    protected void createContexts() throws Exception {
        GSSManager manager = ExtendedGSSManager.getInstance();

        serverContext = manager.createContext((GSSCredential) null);

        clientContext =
            manager.createContext(null, GSSConstants.MECH_OID, null,
                                  GSSContext.DEFAULT_LIFETIME);

        clientContext.requestConf(false);
        clientContext.requestCredDeleg(false);
    }

    protected void tearDown() throws Exception {
        if (clientContext != null) {
            clientContext.dispose();
        }

        if (serverContext != null) {
            serverContext.dispose();
        }
    }

    private void establishContext() throws Exception {
        assertTrue("client ctx already established.",
                   !clientContext.isEstablished());
        assertTrue("server ctx already established.",
                   !serverContext.isEstablished());

        byte[] inToken = new byte[0];
        byte[] outToken = null;

        while (!clientContext.isEstablished()) {
            outToken = clientContext.initSecContext(inToken, 0,
                                                    inToken.length);

            if (outToken != null) {
                inToken = serverContext.acceptSecContext(outToken, 0,
                                                         outToken.length);
            }
        }

        assertTrue("client ctx not established.",
                   clientContext.isEstablished());
        assertTrue("server ctx not established.",
                   serverContext.isEstablished());
    }

    // tests
    public void testSuccessfulVerify() throws Exception {
        establishContext();

        GssSignedSOAPEnvelopeBuilder builder =
            new GssSignedSOAPEnvelopeBuilder(msgContext, clientContext);

        SOAPEnvelope signedEnvelope =
            (SOAPEnvelope) builder.build(unsignedEnvelope);

        verify(signedEnvelope, serverContext);
    }

    public void testRemoveBodyVerify() throws Exception {
        establishContext();

        GssSignedSOAPEnvelopeBuilder builder =
            new GssSignedSOAPEnvelopeBuilder(msgContext, clientContext);

        SOAPEnvelope signedEnvelope =
            (SOAPEnvelope) builder.build(unsignedEnvelope);

        SOAPBodyElement el = signedEnvelope.getFirstBody();

        signedEnvelope.removeBodyElement(el);

        try {
            verify(signedEnvelope, serverContext);
            fail("Signature verification did not fail as expected.");
        } catch (WSSecurityException e) {
            // this should fail
            logger.debug(e);
        }
    }

    public void testChangeBodyVerify() throws Exception {
        establishContext();

        GssSignedSOAPEnvelopeBuilder builder =
            new GssSignedSOAPEnvelopeBuilder(msgContext, clientContext);

        SOAPEnvelope signedEnvelope =
            (SOAPEnvelope) builder.build(unsignedEnvelope);

        SOAPBodyElement el = signedEnvelope.getFirstBody();

        signedEnvelope.removeBodyElement(el);

        Element newEl = el.getAsDOM();

        // Text text = (Text)newEl.getChildNodes().item(1).getFirstChild();
        Text text =
            (Text) ((Element) newEl).getElementsByTagName("value").item(0)
                    .getFirstChild();

        text.setData("16");

        SOAPBodyElement ee = new SOAPBodyElement(newEl);

        signedEnvelope.addBodyElement(ee);

        try {
            verify(signedEnvelope, serverContext);
            fail("Signature verification did not fail as expected.");
        } catch (WSSecurityException e) {
            // this should fail
            logger.debug(e);
        }
    }

    public void testChangeDigest() throws Exception {
        establishContext();

        GssSignedSOAPEnvelopeBuilder builder =
            new GssSignedSOAPEnvelopeBuilder(msgContext, clientContext);

        SOAPEnvelope signedEnvelope =
            (SOAPEnvelope) builder.build(unsignedEnvelope);

        SOAPHeaderElement header =
            (SOAPHeaderElement) signedEnvelope.getHeaders().elementAt(0);

        signedEnvelope.removeHeader(header);

        Element headerElement = header.getAsDOM();

        Node digestValue =
            ((Element) headerElement.getFirstChild()).getElementsByTagNameNS(
                NS, "DigestValue"
            ).item(0);

        Text digest = (Text) digestValue.getFirstChild();

        String data = digest.getData();
        int half = data.length() / 2;
        StringBuffer buf = new StringBuffer(data.length());
        buf.append(data.substring(0, half));
        buf.append( (data.charAt(half) == '6') ? "7" : "6" );
        buf.append(data.substring(half + 1));

        digest.setData(buf.toString());

        signedEnvelope.addHeader(new SOAPHeaderElement(headerElement));

        try {
            verify(signedEnvelope, serverContext);
            fail("Signature verification did not fail as expected.");
        } catch (WSSecurityException e) {
            // this should fail
            logger.debug(e);
        }
    }

    // should fails with Bad Record MAC
    public void testVerifyWithWrongContext() throws Exception {
        establishContext();

        GSSContext pClientContext = clientContext;
        GSSContext pServerContext = serverContext;

        createContexts();

        establishContext();

        // sign with new client context
        GssSignedSOAPEnvelopeBuilder builder =
            new GssSignedSOAPEnvelopeBuilder(msgContext, clientContext);

        SOAPEnvelope signedEnvelope =
            (SOAPEnvelope) builder.build(unsignedEnvelope);

        try {
            // verify with old server context
            verify(signedEnvelope, pServerContext);
            fail("Signature verification did not fail as expected.");
        } catch (XMLSignatureException e) {
            // this should fail
            logger.debug(e);
        } finally {
            try {
                pClientContext.dispose();
            } catch (Exception e) {
            }

            try {
                pServerContext.dispose();
            } catch (Exception e) {
            }
        }
    }

    private void verify(
        SOAPEnvelope env,
        GSSContext context
    ) throws Exception {
        VerifyWSSecurity h = new VerifyWSSecurity(context);
        Document doc = h.processSecurityHeader(env, "", msgContext, false);
    }
}
