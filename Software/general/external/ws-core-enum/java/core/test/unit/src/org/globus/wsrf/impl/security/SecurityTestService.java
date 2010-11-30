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
package org.globus.wsrf.impl.security;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.cert.X509Certificate;

import javax.security.auth.Subject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.axis.MessageContext;
import org.apache.axis.message.addressing.EndpointReferenceType;

import org.globus.security.gridmap.GridMap;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.config.ConfigException;
import org.globus.wsrf.container.ServiceHost;
import org.globus.wsrf.impl.security.authorization.Authorization;
import org.globus.wsrf.impl.security.authorization.SAMLAuthorizationCallout;
import org.globus.wsrf.impl.security.authorization.SAMLAuthorizationConstants;
import org.globus.wsrf.impl.security.authorization.ServicePropertiesPDPConfig;
import org.globus.wsrf.impl.security.descriptor.ContainerSecurityConfig;
import org.globus.wsrf.impl.security.descriptor.SecurityPropertiesHelper;
import org.globus.wsrf.impl.security.descriptor.ServiceSecurityConfig;
import org.globus.wsrf.impl.security.descriptor.ServiceSecurityDescriptor;
import org.globus.wsrf.impl.security.descriptor.TestContainerSecurityConfig;
import org.globus.wsrf.impl.security.util.AuthUtil;
import org.globus.wsrf.impl.security.util.PDPUtils;
import org.globus.wsrf.security.SecurityManager;
import org.globus.wsrf.tests.security.CreateResourceResponse;
import org.globus.wsrf.tests.security.GetValue;
import org.globus.wsrf.tests.security.GsiSec;
import org.globus.wsrf.tests.security.GsiSecConvDeleg;
import org.globus.wsrf.tests.security.GsiSecConvIntegrity;
import org.globus.wsrf.tests.security.GsiSecConvOnly;
import org.globus.wsrf.tests.security.GsiSecConvPrivacy;
import org.globus.wsrf.tests.security.GsiSecMsgIntegrity;
import org.globus.wsrf.tests.security.GsiSecMsgOnly;
import org.globus.wsrf.tests.security.GsiSecMsgPrivacy;
import org.globus.wsrf.tests.security.GsiTransportIntegrity;
import org.globus.wsrf.tests.security.GsiTransportOnly;
import org.globus.wsrf.tests.security.GsiTransportPrivacy;
import org.globus.wsrf.tests.security.NoAuthRequest;
import org.globus.wsrf.tests.security.SetAnonymousAuthz;
import org.globus.wsrf.tests.security.SetAnonymousAuthzResponse;
import org.globus.wsrf.tests.security.SetServiceAuthzOutput;
import org.globus.wsrf.utils.AddressingUtils;
import org.globus.wsrf.utils.ContextUtils;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.gsi.jaas.JaasGssUtil;

/**
 * This class is used by two test services. The first one is used to
 * test various authentication schemes, with authz set to self. In the
 * second case, the custom authz option with SAMLAuthzCallout is
 * tested. If property customAuthzTest is set to true, then the
 * security descriptor is altered to set authz to custom, load custom
 * authz class and authz service. Authz service is set to string
 * passed to that noAuth operation, if null, it is constructed to be
 * TestAuthzService in the same container as the service. The propery
 * customAuthzTest is reset, so the above is not repeated for every
 * call to noAuth method.
 */
public class SecurityTestService {

    public static final String CRED_FROM_CONTEXT = "credFromContext";

    private static final String CONTAINER_DESC =
        "org/globus/wsrf/impl/security/container-security-config.xml";

    public static final String CUSTOM_AUTHZ_TEST = "customAuthzTest";

    static Log logger = LogFactory.getLog(SecurityTestService.class.getName());

    public void noAuth(NoAuthRequest noAuthRequest) throws RemoteException {
        logger.debug("No Auth");

        MessageContext msgCtx = MessageContext.getCurrentContext();
        String customAuthTest =
            (String)ContextUtils.getServiceProperty(msgCtx, CUSTOM_AUTHZ_TEST);
        if ((customAuthTest != null) && (customAuthTest.equals("true"))) {
            logger.debug("reworking security settings");
            // set authz service
            String authzService = noAuthRequest.getAuthzService();
            String authzServiceIdentity =
                noAuthRequest.getAuthzServiceIdentity();
            SecurityManager manager = SecurityManager.getManager(msgCtx);
            Subject systemSubject = manager.getSystemSubject();
            GlobusGSSCredentialImpl credential =
                (GlobusGSSCredentialImpl)JaasGssUtil.getCredential(
                    systemSubject);
            X509Certificate authzServiceCert =
                credential.getCertificateChain()[0];
            try {
                if (authzService == null) {
                    authzService = ServiceHost.getBaseURL(msgCtx) +
                                   TestConstants.TEST_AUTHZ_SERVICE;
                }
            } catch (IOException exp) {
                throw new RemoteException("", exp);
            }

            logger.debug(ContextUtils.getTargetServicePath(msgCtx));
            String servicePath = ContextUtils.getTargetServicePath(msgCtx);
            String pdpChain = AuthUtil.substitutePDPNames(
                Authorization.AUTHZ_SAML);
            pdpChain = pdpChain + " prefix1:" +
                       SAMLAuthorizationCallout.class.getName() +
                       " prefix2:" + SAMLAuthorizationCallout.class.getName() +
                       " prefix3:" + SAMLAuthorizationCallout.class.getName() +
                       " prefix4:" + SAMLAuthorizationCallout.class.getName() +
                       " prefix5:" + SAMLAuthorizationCallout.class.getName() +
                       " prefix6:" + SAMLAuthorizationCallout.class.getName() +
                       " prefix7:" + SAMLAuthorizationCallout.class.getName() +
                       " prefix8:" + SAMLAuthorizationCallout.class.getName();
            ServicePropertiesPDPConfig config =
                new ServicePropertiesPDPConfig(msgCtx,
                                               servicePath,
                                               pdpChain);

            /* Default settings (Transport sec) */
            setSAMLCalloutProperties(config, Authorization.SAML_PREFIX,
                                     authzService, null, null, null,
                                     null, authzServiceIdentity, null);
            /* Message Sec - Integrity */
            setSAMLCalloutProperties(config, "prefix1",
                                     authzService,
                                     SAMLAuthorizationConstants.MESSAGE,
                                     null, null, null,
                                     authzServiceIdentity, null);

            /* No security */

            setSAMLCalloutProperties(config, "prefix2",
                                     authzService,
                                     SAMLAuthorizationConstants.NONE,
                                     null, null, null,
                                     authzServiceIdentity, null);

            /* Secure Conversation - Integrity */

            setSAMLCalloutProperties(config, "prefix3",
                                     authzService,
                                     SAMLAuthorizationConstants.CONVERSATION,
                                     SAMLAuthorizationConstants.INTEGRITY,
                                     null, null, authzServiceIdentity, null);

            /* Transport - Privacy */
            setSAMLCalloutProperties(config, "prefix4",
                                     authzService, null,
                                     SAMLAuthorizationConstants.PRIVACY, null,
                                     null, authzServiceIdentity, null);

            /* Message - Privacy */
            setSAMLCalloutProperties(config, "prefix5",
                                     authzService,
                                     SAMLAuthorizationConstants.MESSAGE,
                                     SAMLAuthorizationConstants.PRIVACY, null,
                                     null, authzServiceIdentity,
                                     authzServiceCert);

            /* Transport - signedReq */
            setSAMLCalloutProperties(config, "prefix6",
                                     authzService, null,
                                     null, null,
                                     "true", authzServiceIdentity,
                                     authzServiceCert);

            /* Message - signedReq */
            setSAMLCalloutProperties(config, "prefix7", authzService,
                                     SAMLAuthorizationConstants.MESSAGE,
                                     null, null,
                                     "true", authzServiceIdentity,
                                     authzServiceCert);

            /* Transport - signedReq - non-Simple*/
            setSAMLCalloutProperties(config, "prefix8",
                                     authzService, null,
                                     null, "false",
                                     "true", authzServiceIdentity,
                                     authzServiceCert);

            logger.debug("Authz service set to " + authzService);
            // set custom authz class (would not have been initialized
            // since authz was initially null
            try {
                ServiceSecurityDescriptor desc =
                    (ServiceSecurityDescriptor)ServiceSecurityConfig
                    .getSecurityDescriptor(servicePath);
                desc.setAuthzChain(
                    PDPUtils.getServiceAuthzChain(
                        config,
                        TestConstants.SECURITY_SERVICE_PATH));
                ServiceSecurityConfig.setSecurityDescriptor(desc, servicePath);
            } catch (ConfigException exp) {
                throw new RemoteException("", exp);
            }
        }
    }

    private void setSAMLCalloutProperties(ServicePropertiesPDPConfig config,
                                          String prefix,
                                          String authzService,
                                          String securityMechanism,
                                          String protectionLevel,
                                          String simpleDecision,
                                          String reqSigned,
                                          String serviceIdentity,
                                          X509Certificate serviceCert) {
        config.setProperty(
            prefix,
            SAMLAuthorizationConstants.AUTHZ_SERVICE_PROPERTY,
            authzService);

        config.setProperty(
            prefix,
            SAMLAuthorizationConstants.SECURITY_MECHANISM_PROPERTY,
            securityMechanism);

        config.setProperty(
            prefix,
            SAMLAuthorizationConstants.PROTECTION_LEVEL_PROPERTY,
            protectionLevel);

        config.setProperty(
            prefix,
            SAMLAuthorizationConstants.SIMPLE_DECISION_PROPERTY,
            simpleDecision);

        config.setProperty(
            prefix,
            SAMLAuthorizationConstants.REQ_SIGNED_PROPERTY,
            reqSigned);

        config.setProperty(
            prefix,
            SAMLAuthorizationConstants.AUTHZ_SERVICE_IDENTITY_PROPERTY,
            serviceIdentity);

        config.setProperty(
            prefix,
            SAMLAuthorizationConstants.AUTHZ_SERVICE_CERT_PROPERTY,
            serviceCert);
    }

    public void gsiSecConvDeleg(GsiSecConvDeleg param)
        throws RemoteException {
        logger.debug("Sec Conv Deleg");
    }

    public void gsiTransportIntegrity(GsiTransportIntegrity param)
        throws RemoteException {
        logger.error("tansport integrity");
    }

    public void gsiTransportPrivacy(GsiTransportPrivacy param)
        throws RemoteException {
        logger.error("transport privacy");
    }

    public void gsiTransportOnly(GsiTransportOnly param)
        throws RemoteException {
        logger.error("transport only");
    }

    public void gsiSecConvIntegrity(GsiSecConvIntegrity param)
        throws RemoteException {
        logger.debug("Sec Conv integrity");
    }

    public void gsiSecConvPrivacy(GsiSecConvPrivacy param)
        throws RemoteException {
        logger.debug("Sec Conv privacy");
    }

    public void gsiSecConvOnly(GsiSecConvOnly param) throws RemoteException {
        logger.debug("sec conv only");
    }

    public void gsiSecMsgOnly(GsiSecMsgOnly param) throws RemoteException {
        logger.debug("sec msg only");
    }

    public void gsiSecMsgPrivacy(GsiSecMsgPrivacy param)
        throws RemoteException {
        logger.debug("sec msg privacy only");
    }

    public void gsiSecMsgIntegrity(GsiSecMsgIntegrity param)
        throws RemoteException {
        logger.debug("sec msg integrity only");
    }

    public void gsiSec(GsiSec param) throws RemoteException {
        logger.debug("any gsi sec only");
    }

    public CreateResourceResponse createResource(boolean param)
        throws RemoteException {

        // Alter global property
        if (param) {
            // Force a initialization
            try {
                TestContainerSecurityConfig
                    .initContainerConfig(CONTAINER_DESC);
                ContainerSecurityConfig containerConfig =
                    ContainerSecurityConfig.getConfig();
            } catch (Exception exp) {
                throw new RemoteException("", exp);
            }
        }

        ResourceContext ctx = null;
        SecurityTestResourceHome home = null;
        ResourceKey key = null;

        try {
            ctx = ResourceContext.getResourceContext();
            home = (SecurityTestResourceHome) ctx.getResourceHome();
            key = home.create();
        } catch(Exception e) {
            throw new RemoteException("", e);
        }

        EndpointReferenceType epr = null;
        try {
            epr = AddressingUtils.createEndpointReference(ctx, key);
        } catch(Exception e) {
            throw new RemoteException("", e);
        }

        CreateResourceResponse response = new CreateResourceResponse();
        response.setEndpointReference(epr);
        return response;
    }

    // Assume if authzMethod is set to "cred", then invocation subject
    // is set as service subject.
    public void alterSecurityDesc(String authMethodName)
        throws RemoteException {

        logger.debug("Alter security desc " + authMethodName);

        if ((authMethodName != null) &&
            (authMethodName.equals(CRED_FROM_CONTEXT))) {

                Subject sub = null;
                try {
                    sub = ServiceSecurityConfig
                        .getSubject(TestConstants.SECURITY_SERVICE_PATH);
                } catch (ConfigException exp) {
                    throw new RemoteException("Error getting subject", exp);
                }
                if (sub != null)
                    throw new RemoteException("Inital subject is not null");

                SecurityManager man = SecurityManager.getManager();
                man.setServiceOwnerFromContext();
                Subject invokedSub = man.getServiceSubject();
                if (invokedSub == null)
                    throw new RemoteException("Invoked subject is null");
                logger.debug("Invoked subject" + invokedSub);

                logger.debug("Now test resource");
                SecurityTestResource res = getResource();
                res.alterSecurityDesc(authMethodName);
                return;
            }


        SecurityTestResource res = getResource();
        logger.debug("invoking alter sec");
        res.alterSecurityDesc(authMethodName);
    }

    public void setAuthz(String authz) throws RemoteException {

        logger.debug("Alter security desc " + authz);

        SecurityTestResource res = getResource();
        try {
            logger.debug("invoking alter sec");
            res.setAuthz(authz);
        } catch (Exception exp) {
            throw new RemoteException("Error setting security desc", exp);
        }
    }

    public SetAnonymousAuthzResponse setAnonymousAuthz(
        SetAnonymousAuthz setAnonymousAuthz) throws RemoteException {
        logger.debug("Changing to identity authz with anonymous authorized user");

        SecurityTestResource res = getResource();
        try {
            logger.debug("invoking alter sec");
            res.setAnonymousAuthz();
        } catch (Exception exp) {
            throw new RemoteException("Error setting security desc", exp);
        }
        return new SetAnonymousAuthzResponse();
    }

    public void setValue(int val) throws RemoteException {

        logger.debug("Set value " + val);
        SecurityTestResource res = getResource();
        res.setValue(val);
    }

    public int getValue(GetValue param) throws RemoteException {

        logger.debug("Get value ");
        SecurityTestResource res = getResource();
        return res.getValue();
    }

    public String getSecurityProperty(String argName) throws RemoteException {
        String value = null;
        try {
            if (argName.equals("replayWindow")) {
                value = SecurityPropertiesHelper
                    .getReplayAttackWindow(TestConstants.SECURITY_SERVICE_PATH,
                                           null);
            } else if (argName.equals("replayFilter")) {
                value = SecurityPropertiesHelper
                    .getReplayAttackFilter(TestConstants.SECURITY_SERVICE_PATH,
                                           null);
            }
        } catch (ConfigException exp) {
            throw new RemoteException("", exp);
        }
        return value;
    }

    public SetServiceAuthzOutput setServiceAuthz(String parameters)
        throws RemoteException {

        // this is set for authz to fail, if used!
        if ((parameters != null) && (parameters.equals("self"))) {
            try {
                String prop = (String)ContextUtils
                    .getServiceProperty(MessageContext.getCurrentContext(),
                                        TestConstants.SECURITY_SERVICE_PATH,
                                        Authorization.IDENTITY_PREFIX + "-"
                                        + Authorization.AUTHZ_IDENTITY);
                if ((prop != null) && (parameters != null)) {
                    parameters = "identity";
                }
            } catch (Exception exp) {
                throw new RemoteException("", exp);
            }
        }
        System.out.println("CHECK THIS " + parameters);
        try {
            ServiceSecurityDescriptor desc =
                ServiceSecurityConfig
                .getSecurityDescriptor(TestConstants.SECURITY_SERVICE_PATH);
            desc.setAuthzChain(PDPUtils
                               .getServiceAuthzChain(parameters,
                                                     TestConstants.SECURITY_SERVICE_PATH));
            if ((parameters != null) && (parameters.equals("gridmap"))) {
                GridMap gridMap = new GridMap();
                gridMap.map("Some DN", "some id");
                desc.setGridMap(gridMap);
            }
            ServiceSecurityConfig
                .setSecurityDescriptor(desc,
                                       TestConstants.SECURITY_SERVICE_PATH);
        } catch (ConfigException exp) {
            throw new RemoteException("", exp);
        }
        return new SetServiceAuthzOutput();
    }

    private SecurityTestResource getResource() throws RemoteException {

        Object obj = null;
        try {
            obj = ResourceContext.getResourceContext().getResource();
        } catch(Exception e) {
            throw new RemoteException("Error getting resource", e);
        }

        if (obj == null) {
            logger.debug("Resource is null");
            throw new RemoteException("bad key set, resource not found");
        }

        return (SecurityTestResource)obj;
    }
}
