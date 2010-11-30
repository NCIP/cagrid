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
package org.globus.wsrf.samples.authzService;

import java.rmi.RemoteException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.security.auth.Subject;

import org.apache.xml.security.signature.XMLSignature;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.utils.XMLUtils;

import org.ietf.jgss.GSSException;

import org.opensaml.ExtendedAuthorizationDecisionQuery;
import org.opensaml.SAMLAction;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLAuthorizationDecisionStatement;
import org.opensaml.SAMLDecision;
import org.opensaml.SAMLException;
import org.opensaml.SAMLRequest;
import org.opensaml.SAMLResponse;
import org.opensaml.SAMLSubject;
import org.opensaml.SimpleAuthorizationDecisionStatement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.gsi.gssapi.JaasGssUtil;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.impl.security.SecurityMessageElement;
import org.globus.wsrf.impl.security.authorization.SAMLAuthorizationConstants;
import org.globus.wsrf.security.SecurityManager;
import protocol._0._1.SAML.tc.names.oasis.Request;
import protocol._0._1.SAML.tc.names.oasis.Response;

/**
 * This is a sample authorization service. It can be configured with a
 * list of methods for which permission can be denied using the
 * service properties "declinedMethods". If the action does not
 * represent a method name (as determined by the action namespace),
 * indeterminate is returned as decision. To test returning of
 * exceptions embedded in SAMLResponse itself, the service embeds an
 * exception if a wildcard subject is presented to it.
 */
public class SampleAuthzService {

    static Log logger =
        LogFactory.getLog(SampleAuthzService.class.getName() );

    // Method added only for testing of SAMLAuthzCallout
    public void addDeclinedMethod(String methodName) throws RemoteException {
        logger.debug("Declined method: " + methodName);
        try {
            SampleAuthzResource resource = getResource();
            resource.addDeclinedMethod(methodName);
        } catch (Exception exp) {
            logger.error(exp);
            throw new RemoteException("", exp);
        }
    }

    // OGSA-Authz method.
    public Response SAMLRequest(Request requestType)
        throws RemoteException {

        logger.debug("In SAMLRequest");

        if (requestType == null) {
            logger.error("request is null");
            throw new RemoteException("request is null");
        }

        // Construct SAMLRequest from DOM.
        SAMLRequest request = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(XMLUtils.ElementToString(
                    ((MessageElement)
                    requestType.get_any()[0].getParentElement()).getAsDOM()));
            }
            request = new SAMLRequest(
                ((MessageElement) requestType.get_any()[0].getParentElement()).
                getAsDOM());
        } catch (Exception exp) {
            String err = "Error extracting SAML Request object ";
            logger.error(err, exp);
            throw new RemoteException(err, exp);
        }

        if (request == null) {
            throw new RemoteException("Request is null");
        }

        // signature needs to be verified.
        if (request.isSigned()) {
            try {
                request.verify(false);
            } catch (SAMLException samlExp) {
                String err = "Could not verify signature on request. ";
                logger.error(err, samlExp);
                throw new RemoteException(err, samlExp);
            }
        }

        // Check if respondWith has elements that authz service can
        // repond with.
        Iterator respondWiths = request.getRespondWiths();
        boolean valid = false;
        SampleAuthzResource sampleResource = null;
        try {
            sampleResource = getResource();
        } catch (Exception exp) {
            logger.error(exp);
            throw new RemoteException("", exp);
        }
        org.opensaml.QName[] supportedResponse =
            sampleResource.getSupportedResponse();
        if (respondWiths != null) {
            while (respondWiths.hasNext()) {
                org.opensaml.QName qName =
                    (org.opensaml.QName)respondWiths.next();
                if (qName.equals(supportedResponse[0])
                    || qName.equals(supportedResponse[1])) {
                    valid = true;
                    break;
                }
            }
        }

        // Declined method
        Vector declinedMethods = sampleResource.getDeclinedMethods();

        SAMLException samlException = null;
        // Get list of assertions.
        Vector assertions = new Vector();
        if (valid) {
            // Create an assertion with the SAML statement
            SAMLAssertion samlAssertion = null;
            // Exception to be added as a part of SAMLResponse.

            logger.debug("Valid respondWith found");

            // query
            ExtendedAuthorizationDecisionQuery query =
                (ExtendedAuthorizationDecisionQuery)request.getQuery();

            // subject
            SAMLSubject subject = query.getSubject();
            // For testing purposes, if this subject is a wild card
            // entry, this authz service returns an exception with
            // SAMLResponse.
            logger.debug("Subject is " + subject.getName());
            if (subject.getName().equals(
                        SAMLAuthorizationConstants.ANY_SUBJECT_NAME)) {
                logger.debug("Subject is wildcard, respond with exception");
                samlException =
                    new SAMLException(SAMLException.REQUEST_DENIED,
                                      "Request denied. Subject is wildcard");
            }

            Vector stmts = null;
            if (samlException == null) {
                stmts = getSAMLStatements(query, subject, request,
                                          declinedMethods);
            }

            Calendar notBefore = Calendar.getInstance();
            Calendar notAfter = Calendar.getInstance();
            notAfter.add(Calendar.MINUTE, 10);

            // Issuer name
            String issuer = "Unique issuer name";
            try {
                samlAssertion = new SAMLAssertion(issuer,
                                                  notBefore.getTime(),
                                                  notAfter.getTime(), null,
                                                  null, stmts);
            } catch (SAMLException samlExp) {
                String err = "Could not create assertion. ";
                logger.error(err, samlExp);
                throw new RemoteException(err, samlExp);
            }
            assertions.add(samlAssertion);
        }

        // Construct response with above assertion
        SAMLResponse response = null;
        String inResponseTo = request.getRequestId();
        try {
            response = new SAMLResponse(inResponseTo, "recepient", assertions,
                                        samlException);
        } catch (SAMLException samlExp) {
            String err = "Could not create response. ";
            logger.error(err, samlExp);
            throw new RemoteException(err, samlExp);
        }

        if (request.isSigned()) {
            Subject systemSubject = null;
            try {
                MessageContext ctx = MessageContext.getCurrentContext();
                SecurityManager manager = SecurityManager.getManager(ctx);
                systemSubject = manager.getServiceSubject();
            } catch (org.globus.wsrf.security.SecurityException exp) {
                String err = "Unable to obtain service credentials";
                logger.debug(err, exp);
                throw new RemoteException(err, exp);
            }

            GlobusGSSCredentialImpl credential =
                (GlobusGSSCredentialImpl)JaasGssUtil.getCredential(
                    systemSubject);
            Vector certs = getCertificates(credential);

            try {
                response.sign(XMLSignature.ALGO_ID_SIGNATURE_RSA,
                             credential.getPrivateKey(), certs, false);
            } catch (SAMLException exp) {
                String err = "Error signing SAMLResponse";
                logger.debug(err, exp);
                throw new RemoteException(err, exp);
            } catch (GSSException exp) {
                String err = "Error signing SAMLResponse";
                logger.debug(err, exp);
                throw new RemoteException(err, exp);
            }
        }

        Element responseElement = (Element) response.toDOM();
        NodeList responseChildren = responseElement.getChildNodes();
        List responseElements = new ArrayList();
        for (int i = 0; i < responseChildren.getLength(); i++) {
            Node child = responseChildren.item(i);
            if (child instanceof Element) {
                responseElements.add(
                    new SecurityMessageElement((Element) child));
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String issueInstant = formatter.format(response.getIssueInstant());

        Response samlResponse = new Response(
            (MessageElement[]) responseElements.toArray(
                new MessageElement[responseElements.size()]),
            inResponseTo,
            issueInstant,
            1,
            0,
            response.getRecipient(),
            response.getResponseId());

        return samlResponse;
    }

    private Vector getSAMLStatements(ExtendedAuthorizationDecisionQuery query,
                                     SAMLSubject subject, SAMLRequest request,
                                     Vector declinedMethods)
        throws RemoteException {

        logger.debug("called");
        boolean simpleDecision = query.getRequestSimpleDecision();
        // Resource
        String resource = query.getResource();
        // Actions
        Iterator actionIterator = query.getActions();
        Vector stmts = new Vector();
        // Simple decision - so no actions are set.
        if (simpleDecision) {
            logger.debug("Simple decision requested");

            // Set decision - all actions need to be permit to
            // send a permit decision.
            String decision = SAMLDecision.PERMIT;
            if (declinedMethods != null) {
                logger.debug("Declined methods is not null");
                while (actionIterator.hasNext()) {
                    SAMLAction action = (SAMLAction)actionIterator.next();
                    // if action an operation, set DENY if its in
                    // deniedMethods list. If not operation, set
                    // indeterminate.
                    if (action.getNamespace().equals(
                        SAMLAuthorizationConstants.ACTION_OPERATION_NS)) {
                        logger.debug("Correct namespace " + action.getData());
                        if (declinedMethods.contains(action.getData())) {
                            logger.debug("Must deny " + action.getData());
                            decision = SAMLDecision.DENY;
                            break;
                        }
                    } else {
                        logger.debug("Not right namespace, indeterminate");
                        decision = SAMLDecision.INDETERMINATE;
                        break;
                    }
                }
            } else
                logger.debug("Declined methods is null");
            SimpleAuthorizationDecisionStatement stmt = null;
            try {
                stmt = new SimpleAuthorizationDecisionStatement(
                           subject,
                           decision,
                           null,
                           request.getRequestId());
            } catch (SAMLException samlExp) {
                String err = "Could not create decision stmt. ";
                logger.error(err, samlExp);
                throw new RemoteException(err, samlExp);
            }
            stmts.add(stmt);
        } else {
            logger.debug("Not simple decision, need to list all actions");
            // Set decision - return actions for which decision is
            // PERMIT
            String decision = SAMLDecision.PERMIT;
            // check for declined methods
            if (declinedMethods != null) {
                while (actionIterator.hasNext()) {
                    SAMLAction action = (SAMLAction)actionIterator.next();
                    if (action.getNamespace().equals(
                               SAMLAuthorizationConstants.ACTION_OPERATION_NS)
                        && (declinedMethods.contains(action.getData()))) {
                        decision = SAMLDecision.DENY;
                        break;
                    }
                }
            } else {
                // check for actions not with namespace other than
                // namespace as operation. Return all actions tho'
                while (actionIterator.hasNext()) {
                    SAMLAction action = (SAMLAction)actionIterator.next();
                    if (!action.getNamespace().equals(
                         SAMLAuthorizationConstants.ACTION_OPERATION_NS))
                        decision = SAMLDecision.INDETERMINATE;
                    break;
                }
            }

            SAMLAuthorizationDecisionStatement stmt = null;
            try {
                stmt = new SAMLAuthorizationDecisionStatement(
                           subject, resource, decision, query.getActionsCol(),
                           null);
            } catch (SAMLException samlExp) {
                String err = "Could not create decision stmt. ";
                logger.error(err, samlExp);
                throw new RemoteException(err, samlExp);
            }
            stmts.add(stmt);
        }
        return stmts;
    }

    private SampleAuthzResource getResource() throws Exception{
        ResourceContext ctx = ResourceContext.getResourceContext();
        SampleAuthzHome home = (SampleAuthzHome) ctx.getResourceHome();
        return (SampleAuthzResource)home.find(null);
    }

    // Returns certs if it exists, else null
    private Vector getCertificates(GlobusGSSCredentialImpl credential) {

        X509Certificate certArray[] = credential.getCertificateChain();
        Vector certs = null;
        if (certArray.length > 0) {
            logger.debug("Cert array is not null");
            certs = new Vector(certArray.length);
            for (int i=0; i<certArray.length; i++) {
                certs.add(certArray[i]);
            }
        } else
            logger.debug("Null");
        return certs;
    }
}
