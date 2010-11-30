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

import java.rmi.RemoteException;
import java.util.Vector;

import javax.security.auth.Subject;
import javax.xml.namespace.QName;

import org.apache.axis.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.security.gridmap.GridMap;
import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.Topic;
import org.globus.wsrf.TopicList;
import org.globus.wsrf.TopicListAccessor;
import org.globus.wsrf.config.ConfigException;
import org.globus.wsrf.impl.ResourcePropertyTopic;
import org.globus.wsrf.impl.SimpleResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.impl.SimpleTopicList;
import org.globus.wsrf.impl.security.authorization.Authorization;
import org.globus.wsrf.impl.security.authorization.ResourcePDPConfig;
import org.globus.wsrf.impl.security.authorization.ServiceAuthorizationChain;
import org.globus.wsrf.impl.security.descriptor.GSISecureConvAuthMethod;
import org.globus.wsrf.impl.security.descriptor.GSISecureMsgAuthMethod;
import org.globus.wsrf.impl.security.descriptor.GSITransportAuthMethod;
import org.globus.wsrf.impl.security.descriptor.NoneAuthMethod;
import org.globus.wsrf.impl.security.descriptor.ResourceSecurityDescriptor;
import org.globus.wsrf.impl.security.descriptor.SecurityDescriptorException;
import org.globus.wsrf.impl.security.util.PDPUtils;
import org.globus.wsrf.security.SecureResource;
import org.globus.wsrf.security.SecurityManager;
import org.globus.wsrf.utils.ContextUtils;

public class SecurityTestResource implements SecureResource,
                                             Resource,
                                             ResourceProperties,
                                             TopicListAccessor {

    ResourceSecurityDescriptor desc = null;

    static Log logger =
        LogFactory.getLog(SecurityTestResource.class.getName());

    public static final QName RP_SET =
        new QName("http://security.test", "TestRP");

    public static final QName VALUE =
        new QName("http://security.test", "Value");

    private ResourcePropertySet propSet;
    private TopicList topicList;
    protected ResourceProperty value;

    public SecurityTestResource() {
        this.propSet = new SimpleResourcePropertySet(RP_SET);
        this.topicList = new SimpleTopicList(this);

        try {
            this.value = new ResourcePropertyTopic(
                new SimpleResourceProperty(VALUE));
            this.propSet.add(this.value);
            this.topicList.addTopic((Topic) this.value);
            this.value.add(new Integer(0));
        } catch (Exception exp) {
            throw new RuntimeException(exp.getMessage());
        }
    }

    public TopicList getTopicList() {
        return this.topicList;
    }

    public ResourcePropertySet getResourcePropertySet() {
        return this.propSet;
    }

    public void setValue(int value) {
        this.value.set(0, new Integer(value));
    }

    public int getValue() {
        return ((Integer) this.value.get(0)).intValue();
    }

    public ResourceSecurityDescriptor getSecurityDescriptor() {
        if (this.desc != null) {
            logger.debug("Authz at get is " + this.desc.getAuthz());
        } else {
            logger.debug("Authz is null");
        }
        return this.desc;
    }

    public void alterSecurityDesc(String authType)
        throws RemoteException {

        logger.debug("Called " + authType);
        // reset the descriptor for service descriptor to be
        // enforced.
        if (authType == null) {
            logger.debug("Auth type is null");
            this.desc = null;
            return;
        }

        // set credentials from invocation
        if (authType.equals(SecurityTestService.CRED_FROM_CONTEXT)) {
            if (this.desc == null) {
                this.desc = new ResourceSecurityDescriptor();
            }

            Subject sub = this.desc.getSubject();
            if (sub != null) {
                throw new RemoteException("Initial subject is not null");
            }

            SecurityManager man = SecurityManager.getManager();
            try {
                man.setResourceOwnerFromContext(this.desc);
            } catch (Exception exp) {
                throw new RemoteException("error setting owner", exp);
            }
            logger.error("Subject " + this.desc.getSubject());
            if (this.desc.getSubject() == null) {
                throw new RemoteException("Subject null after set from "
                                          + "context");
            }
        }

        Vector authMethods = new Vector();
        logger.debug("Auth type is " + authType);
        if (this.desc == null) {
            this.desc = new ResourceSecurityDescriptor();
        }
        if (authType.equals("GSISecureConv")) {
            authMethods.add(GSISecureConvAuthMethod.BOTH);
        } else if (authType.equals("GSISecureMsg")) {
            authMethods.add(GSISecureMsgAuthMethod.BOTH);
        } else if (authType.equals("None")) {
            authMethods.add(NoneAuthMethod.getInstance());
        } else {
            return;
        }
        try {
            this.desc.setAuthMethods(authMethods, true);
        } catch (Exception exp) {
            throw new RemoteException("error altering sec desc", exp);
        }

        logger.debug("Altered");
    }

    public void setAuthz(String authz) throws SecurityDescriptorException {

        logger.debug("Called " + authz);

        if (authz == null) {
            // reset the descriptor for service descriptor to be
            // enforced.
            logger.debug("Auth type is null");
            this.desc = null;
            return;
        }

        if (this.desc == null) {
            this.desc = new ResourceSecurityDescriptor();
        }

        // check if service was set with identity authz
        try {
            String prop = (String)ContextUtils
                .getServiceProperty(MessageContext.getCurrentContext(),
                                    TestConstants.SECURITY_SERVICE_PATH,
                                    Authorization.IDENTITY_PREFIX + "-"
                                    + Authorization.AUTHZ_IDENTITY);
            if ((prop != null) && (!authz.equals("none"))) {
                ResourcePDPConfig config =
                    new ResourcePDPConfig(Authorization.AUTHZ_IDENTITY);
                config.setProperty(Authorization.IDENTITY_PREFIX,
                                   Authorization.AUTHZ_IDENTITY, prop);
                ServiceAuthorizationChain chain =
                    PDPUtils.getServiceAuthzChain(config, "some id");
                this.desc.setAuthzChain(chain);
            } else {
                this.desc.setAuthz(authz);
                // Force initialization
                this.desc.setInitialized(false);
            }
        } catch (Exception exp) {
            throw new SecurityDescriptorException("", exp);
        }
        logger.debug("Authz is set to " + this.desc.getAuthz());
    }

    public void setAnonymousAuthz() throws SecurityDescriptorException,
                                           ConfigException {
        if (this.desc == null) {
            this.desc = new ResourceSecurityDescriptor();
        }
        this.desc.setInitialized(false);
        Vector authMethod = new Vector();
        authMethod.add(GSITransportAuthMethod.BOTH);
        this.desc.setAuthMethods(authMethod);
        this.desc.setAuthz(Authorization.AUTHZ_GRIDMAP);
        GridMap map = new GridMap();
        map.map("<anonymous>", "userid");
        desc.setGridMap(map);
    }
}
