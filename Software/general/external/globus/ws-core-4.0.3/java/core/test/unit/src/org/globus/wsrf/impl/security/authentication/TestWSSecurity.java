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
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.client.AxisClient;
import org.apache.axis.configuration.NullProvider;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.addressing.Constants;

import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSManager;

import org.globus.gsi.GlobusCredential;
import org.globus.gsi.gssapi.GSSConstants;
import org.globus.wsrf.impl.security.authentication.encryption.GssEncryptedSOAPEnvelopeBuilder;
import org.globus.wsrf.impl.security.authentication.encryption.X509WSEncryptedSOAPEnvelopeBuilder;
import org.globus.wsrf.impl.security.authentication.secureconv.service.SecurityContext;
import org.globus.wsrf.impl.security.authentication.signature.GssSignedSOAPEnvelopeBuilder;
import org.globus.wsrf.impl.security.authentication.signature.X509WSSignedSOAPEnvelopeBuilder;
import org.globus.wsrf.impl.security.authentication.wssec.GSSConfig;
import org.globus.wsrf.impl.security.authentication.wssec.WSSecurityException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestWSSecurity extends TestCase {
    private static Log logger =
        LogFactory.getLog(TestWSSecurity.class.getName());
    SecurityContext clientContext;
    SecurityContext serverContext;
    MessageContext msgContext;
    static final String NS = "http://www.w3.org/2000/09/xmldsig#";
    static final String soapMsg =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas."
        + "xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/"
        + "XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance"
        + "\" xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/03/addressing\">"
        + "<SOAP-ENV:Header> <wsa:To SOAP-ENV:mustUnderstand=\"0\" "
        + "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401"
        + "-wss-wssecurity-utility-1.0.xsd\">http://140.221.57.28:8080/wsrf/"
        + "services/SecureCounterService</wsa:To><wsa:Action  SOAP-ENV:must"
        + "Understand=\"0\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01"
        + "/oasis-200401-wss-wssecurity-utility-1.0.xsd\">http://counter.com/"
        + "CounterPortType/addRequest</wsa:Action><wsa:From SOAP-ENV:must"
        + "Understand=\"0\"><Address xmlns=\"http://schemas.xmlsoap.org/ws/"
        + "2004/03/addressing\">http://schemas.xmlsoap.org/ws/2004/03/"
        + "addressing/role/anonymous</Address></wsa:From><ns1:CounterKey "
        + "SOAP-ENV:mustUnderstand=\"0\" xmlns:ns1=\"http://counter.com\" "
        + "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/"
        + "oasis-200401-wss-wssecurity-utility-1.0.xsd\">4686652</ns1:Counter"
        + "Key></SOAP-ENV:Header><SOAP-ENV:Body><add xmlns=\"http://samples."
        + "wsrf.globus.org/counter/counter_port_type\"><value xmlns=\"\">15"
        + "</value></add></SOAP-ENV:Body>\r\n       \r\n"
        + "</SOAP-ENV:Envelope>";

    static final String soapMsgWithID =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas."
        + "xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/"
        + "XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance"
        + "\" xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/03/addressing\">"
        + "<SOAP-ENV:Header> <wsa:MessageID wsu:Id=\"id-145910\" "
        + "SOAP-ENV:mustUnderstand=\"0\" xmlns:wsu=\"http://docs.oasis-open."
        + "org/wss/2004/01/oasis-200401-"
        + "wss-wssecurity-utility-1.0.xsd\">uuid:6b4d3d80-2f7a-11d9-a445-"
        + "9feae912fa6b</wsa:MessageID>"
        + "<wsa:To SOAP-ENV:mustUnderstand=\"0\" "
        + "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401"
        + "-wss-wssecurity-utility-1.0.xsd\">http://140.221.57.28:8080/wsrf/"
        + "services/SecureCounterService</wsa:To><wsa:Action  SOAP-ENV:must"
        + "Understand=\"0\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01"
        + "/oasis-200401-wss-wssecurity-utility-1.0.xsd\">http://counter.com/"
        + "CounterPortType/addRequest</wsa:Action><wsa:From SOAP-ENV:must"
        + "Understand=\"0\"><Address xmlns=\"http://schemas.xmlsoap.org/ws/"
        + "2004/03/addressing\">http://schemas.xmlsoap.org/ws/2004/03/"
        + "addressing/role/anonymous</Address></wsa:From><ns1:CounterKey "
        + "SOAP-ENV:mustUnderstand=\"0\" xmlns:ns1=\"http://counter.com\" "
        + "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/"
        + "oasis-200401-wss-wssecurity-utility-1.0.xsd\">4686652</ns1:Counter"
        + "Key></SOAP-ENV:Header><SOAP-ENV:Body><add xmlns=\"http://samples."
        + "wsrf.globus.org/counter/counter_port_type\"><value xmlns=\"\">15"
        + "</value></add></SOAP-ENV:Body>\r\n       \r\n"
        + "</SOAP-ENV:Envelope>";

    SOAPEnvelope unsignedEnvelope;
    SOAPEnvelope unsignedEnvelopeWithID;
    X509Certificate keyCert = null;

    static {
        GSSConfig.init();
    }

    public TestWSSecurity(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TestWSSecurity.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    protected void setUp() throws Exception {
        createContexts();

        AxisClient tmpEngine = new AxisClient(new NullProvider());
        msgContext = new MessageContext(tmpEngine);
        HashMap map = new HashMap();
        map.put(new QName(Constants.NS_URI_ADDRESSING, Constants.TO), "");
        map.put(new QName(Constants.NS_URI_ADDRESSING, Constants.ACTION), "");
        map.put(new QName(Constants.NS_URI_ADDRESSING, Constants.FROM), "");
        map.put(new QName("http://counter.com", "CounterKey"), "");
        msgContext.setProperty(
         org.globus.wsrf.impl.security.authentication.Constants
         .SECURE_HEADERS, map);
        msgContext.setProperty(
         org.globus.wsrf.impl.security.authentication.Constants
         .ENFORCED_SECURE_HEADERS, map);
        unsignedEnvelope = getSOAPEnvelope(soapMsg);
        unsignedEnvelopeWithID = getSOAPEnvelope(soapMsgWithID);
        GlobusCredential credential = GlobusCredential.getDefaultCredential();
        keyCert = credential.getCertificateChain()[0];
    }

    protected SOAPEnvelope getSOAPEnvelope(String soapmsg) throws Exception {
        InputStream in = new ByteArrayInputStream(soapmsg.getBytes());
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
    // double GssSignature, GssSignatureAndGssEncryption,
    // GssEncryptionAndGssSignature and DoubleGssEncryption will fail
    // becuase of the order of processing the tokens for the same context!
    public void testGssSignatue() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        GssSignedSOAPEnvelopeBuilder builder =
            new GssSignedSOAPEnvelopeBuilder(msgContext,
                                             clientContext.getContext());
        envelope = (SOAPEnvelope) builder.build(unsignedEnvelope);

        verify(envelope, serverContext, false);
    }

    public void testGssEncryption() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        GssEncryptedSOAPEnvelopeBuilder builder =
            new GssEncryptedSOAPEnvelopeBuilder(msgContext, clientContext);
        envelope = (SOAPEnvelope) builder.build(unsignedEnvelope);
        verify(envelope, serverContext, false);
    }

    public void testX509Signature() throws Exception {
        SOAPEnvelope envelope = null;

        X509WSSignedSOAPEnvelopeBuilder builder =
            new X509WSSignedSOAPEnvelopeBuilder(msgContext, null);

        envelope = (SOAPEnvelope) builder.build(unsignedEnvelopeWithID);

        verify(envelope, null, true);

    }

    public void testX509Encryption() throws Exception {
        SOAPEnvelope envelope = null;

        X509WSEncryptedSOAPEnvelopeBuilder builder =
            new X509WSEncryptedSOAPEnvelopeBuilder(keyCert);

        envelope = (SOAPEnvelope) builder.build(unsignedEnvelopeWithID);

        verify(envelope, null, true);
    }

    public void testDoubleX509Encryption() throws Exception {
        SOAPEnvelope envelope = null;

        X509WSEncryptedSOAPEnvelopeBuilder builder =
            new X509WSEncryptedSOAPEnvelopeBuilder(keyCert);

        envelope = (SOAPEnvelope) builder.build(unsignedEnvelopeWithID);
        envelope = (SOAPEnvelope) builder.build(envelope);

        verify(envelope, null, true);
    }

    public void testGssSignatureAndX509Encryption() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        GssSignedSOAPEnvelopeBuilder gssBuilder =
            new GssSignedSOAPEnvelopeBuilder(msgContext,
                                             clientContext.getContext());

        envelope = (SOAPEnvelope) gssBuilder.build(unsignedEnvelopeWithID);

        X509WSEncryptedSOAPEnvelopeBuilder builder =
            new X509WSEncryptedSOAPEnvelopeBuilder(keyCert);

        envelope = (SOAPEnvelope) builder.build(envelope);

        verify(envelope, serverContext, true);
    }

    public void testX509EncryptionAndGssSignature() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        X509WSEncryptedSOAPEnvelopeBuilder builder =
            new X509WSEncryptedSOAPEnvelopeBuilder(keyCert);

        envelope = (SOAPEnvelope) builder.build(unsignedEnvelopeWithID);

        GssSignedSOAPEnvelopeBuilder gssBuilder =
            new GssSignedSOAPEnvelopeBuilder(msgContext,
                                             clientContext.getContext());

        envelope = (SOAPEnvelope) gssBuilder.build(envelope);

        verify(envelope, serverContext, true);
    }

    public void testX509EncryptionAndGssEncryption() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        X509WSEncryptedSOAPEnvelopeBuilder builder =
            new X509WSEncryptedSOAPEnvelopeBuilder(keyCert);

        envelope = (SOAPEnvelope) builder.build(unsignedEnvelopeWithID);

        GssEncryptedSOAPEnvelopeBuilder gssBuilder =
            new GssEncryptedSOAPEnvelopeBuilder(msgContext, clientContext);
        envelope = (SOAPEnvelope) gssBuilder.build(envelope);
        verify(envelope, serverContext, true);
    }

    public void testGssEncryptionAndX509Encryption() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        GssEncryptedSOAPEnvelopeBuilder gssBuilder =
            new GssEncryptedSOAPEnvelopeBuilder(msgContext, clientContext);
        envelope = (SOAPEnvelope) gssBuilder.build(unsignedEnvelopeWithID);
        X509WSEncryptedSOAPEnvelopeBuilder builder =
            new X509WSEncryptedSOAPEnvelopeBuilder(keyCert);
        envelope = (SOAPEnvelope) builder.build(envelope);
        verify(envelope, serverContext, true);
    }

    public void testDoubleX509Signature() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        X509WSSignedSOAPEnvelopeBuilder builder =
            new X509WSSignedSOAPEnvelopeBuilder(msgContext, null);

        envelope = (SOAPEnvelope) builder.build(unsignedEnvelopeWithID);

        envelope = (SOAPEnvelope) builder.build(envelope);

        verify(envelope, serverContext, true);
    }

    public void testGssSignatureAndX509Signature() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        GssSignedSOAPEnvelopeBuilder gssBuilder =
            new GssSignedSOAPEnvelopeBuilder(msgContext,
                                             clientContext.getContext());

        envelope = (SOAPEnvelope) gssBuilder.build(unsignedEnvelopeWithID);

        X509WSSignedSOAPEnvelopeBuilder x509Builder =
            new X509WSSignedSOAPEnvelopeBuilder(msgContext, null);

        envelope = (SOAPEnvelope) x509Builder.build(envelope);

        verify(envelope, serverContext, true);
    }

    public void testX509SignatureAndGssSignature() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        AxisClient tmpEngine = new AxisClient(new NullProvider());
        MessageContext tmpCtx = new MessageContext(tmpEngine);
        // no secure headers
        X509WSSignedSOAPEnvelopeBuilder x509Builder =
            new X509WSSignedSOAPEnvelopeBuilder(tmpCtx, null);

        envelope = (SOAPEnvelope) x509Builder.build(unsignedEnvelopeWithID);

        GssSignedSOAPEnvelopeBuilder gssBuilder =
            new GssSignedSOAPEnvelopeBuilder(msgContext,
                                             clientContext.getContext());

        envelope = (SOAPEnvelope) gssBuilder.build(envelope);

        boolean exception = false;
        try {
            verify(envelope, serverContext, true);
        } catch (WSSecurityException exp) {
            if (exp.getMessage()
                .indexOf("the headers used in dispatch") != -1) {
                exception = true;
            } else {
                logger.error(exp);
            }
        }
        assertTrue(exception);

        x509Builder = new X509WSSignedSOAPEnvelopeBuilder(msgContext, null);
        envelope = (SOAPEnvelope) x509Builder.build(unsignedEnvelopeWithID);
        gssBuilder =
            new GssSignedSOAPEnvelopeBuilder(msgContext,
                                             clientContext.getContext());

        envelope = (SOAPEnvelope) gssBuilder.build(envelope);
        verify(envelope, serverContext, true);
    }

    public void testX509SignatureAndGssEncryption() throws Exception {
        establishContext();

        SOAPEnvelope envelope = null;

        X509WSSignedSOAPEnvelopeBuilder x509Builder =
            new X509WSSignedSOAPEnvelopeBuilder(msgContext, null);

        envelope = (SOAPEnvelope) x509Builder.build(unsignedEnvelopeWithID);

        GssEncryptedSOAPEnvelopeBuilder gssBuilder =
            new GssEncryptedSOAPEnvelopeBuilder(msgContext, clientContext);
        envelope = (SOAPEnvelope) gssBuilder.build(envelope);
        verify(envelope, serverContext, true);
    }

    private void verify(SOAPEnvelope env, SecurityContext context,
                        boolean timestamp) throws Exception {

        VerifyWSSecurity h = new VerifyWSSecurity((context == null) ?
                                                  null :
                                                  context.getContext());
        msgContext.setProperty(
            org.globus.wsrf.impl.security.authentication.Constants.CONTEXT,
            context);
        h.processSecurityHeader(env, "", msgContext, timestamp);
        msgContext.setProperty(
            org.globus.wsrf.impl.security.authentication.Constants.CONTEXT,
            null);
    }
}
