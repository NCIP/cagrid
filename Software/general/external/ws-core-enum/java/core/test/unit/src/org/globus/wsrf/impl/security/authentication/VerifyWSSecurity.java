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

import java.security.PrivateKey;

import javax.xml.rpc.handler.MessageContext;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeaderElement;

import org.apache.xml.security.signature.XMLSignature;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.ietf.jgss.GSSContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.globus.gsi.GlobusCredential;
import org.globus.gsi.proxy.ProxyPathValidator;
import org.globus.wsrf.impl.security.authentication.secureconv.service.SecurityContext;
import org.globus.wsrf.impl.security.authentication.wssec.WSSecurityEngine;
import org.globus.wsrf.providers.GSSPublicKey;

import javax.xml.namespace.QName;

public class VerifyWSSecurity extends WSSecurityEngine {

    private static Log logger =
        LogFactory.getLog(VerifyWSSecurity.class.getName());

    private GSSContext context;

    public VerifyWSSecurity(GSSContext context) {
        this.context = context;
    }

    public Document processSecurityHeader(SOAPEnvelope env,
                                          MessageContext msgCtx)
        throws Exception {
        logger.error("processes header");
        return processSecurityHeader(env, msgCtx, false);
    }

    public boolean verifyGssXMLSignature(XMLSignature sig,
                                         MessageContext msgContext)
        throws Exception {

        boolean result;
        logger.debug("Enter: verifyGssXMLSignature");

        result = sig.checkSignatureValue(new GSSPublicKey(
            String.valueOf(this.context.hashCode()), this.context));

        logger.debug("Exit: verifyGssXMLSignature");

        return result; // DOM unmodified
    }

    public boolean verifyXMLSignature(XMLSignature sig,
                                      MessageContext msgCtx)
        throws Exception {

        ProxyPathValidator validator = new ProxyPathValidator();
        return verifyXMLSignature(sig, msgCtx, validator);
    }

    /* public boolean decryptGssXMLEncryption(GSSEncryptedData encryptedData,
                                           MessageContext msgContext)
        throws Exception {

        logger.debug("Enter: decryptGssXMLEncryption");

        encryptedData.decryptAndReplace(context);

        logger.debug("Exit: decryptGssXMLEncryption");

        return true; // DOM modified
    } */
    public void prepareEncryptionContext(
        String contextId,
        MessageContext msgContext) throws Exception {
        logger.debug("Enter: initContext");
        // get secure context from the msg context
        msgContext.setProperty(Constants.CONTEXT,
                               new SecurityContext(context, contextId));
    }


    protected QName getResourceKeyHeaderQName(MessageContext msgCtx)
        throws Exception {
        return null;
    }

    public boolean decryptXMLEncryption(Element element,
                                        MessageContext msgCtx)
        throws Exception {

        GlobusCredential credential = GlobusCredential.getDefaultCredential();
        PrivateKey privateKey = credential.getPrivateKey();
        return decryptXMLEncryption(element, privateKey);
    }

    public void processTimestampHeader(Element timestampElem,
                                       MessageContext msgCtx,
                                       SOAPHeaderElement messageIDHeader)
        throws Exception {

        if (messageIDHeader == null) {
            throw new Exception("MessageID Header is not present. "
                                + "Element is null");
        }

        logger.debug("Calling timestamp in verifyWS");
        if (timestampElem != null) {
            checkMessageValidity(timestampElem, messageIDHeader, null);
        } else {
            if (rejectMsgSansTimestampHeader(msgCtx, null)) {
                throw new Exception("Timestamp is required");
            }
        }
    }

    protected void checkMessageValidity(Element timestampElem,
                                        SOAPHeaderElement messageIDHeader,
                                        String replayAttackWindow)
        throws Exception {
        VerifyReplayFilter filter = new VerifyReplayFilter();
        checkMessageValidity(filter, timestampElem, messageIDHeader);
    }
}
