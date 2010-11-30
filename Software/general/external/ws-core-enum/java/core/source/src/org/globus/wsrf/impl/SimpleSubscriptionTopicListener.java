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
package org.globus.wsrf.impl;

import java.util.List;
import java.io.Serializable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.rpc.Stub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.axis.message.addressing.EndpointReferenceType;

import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceHome;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.Subscription;
import org.globus.wsrf.Topic;
import org.globus.wsrf.topicexpression.TopicExpressionEngine;
import org.globus.wsrf.topicexpression.TopicExpressionEvaluator;
import org.globus.wsrf.TopicListener;
import org.globus.wsrf.utils.Resources;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authorization.NoAuthorization;
import org.globus.wsrf.impl.security.descriptor.ClientSecurityDescriptor;
import org.globus.util.I18n;

import org.oasis.wsn.NotificationConsumer;
import org.oasis.wsn.NotificationMessageHolderType;
import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.WSBaseNotificationServiceAddressingLocator;
import org.oasis.wsn.Notify;

import org.apache.axis.client.AxisClient;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;

/**
 * Topic listener implementation that will trigger notifications when a topic
 * changes. To be used in conjunction with the SimpleSubscription class.
 *
 * @see org.globus.wsrf.impl.notification.SimpleSubscription
 */
public class SimpleSubscriptionTopicListener implements TopicListener,
                                                        Serializable
{

    private static Log logger =
        LogFactory.getLog(SimpleSubscriptionTopicListener.class.getName());

    private static I18n i18n = I18n.getI18n(Resources.class.getName());

    private ResourceKey key;
    private String homeLocation;
    private transient NotificationConsumer consumerPort = null;
    private transient WSBaseNotificationServiceAddressingLocator locator =
        null;

    /**
     * Construct a listener instance.
     *
     * @param key          The key for the subscription resource associated with
     *                     this listener.
     * @param homeLocation The JNDI location of the subscription resource home
     */
    public SimpleSubscriptionTopicListener(
        ResourceKey key,
        String homeLocation)
    {
        this.key = key;
        this.homeLocation = homeLocation;
    }

    public void topicChanged(Topic topic)
    {
        Subscription subscription = null;
        try
        {
            subscription = this.getSubscription();
        }
        catch(ResourceException e)
        {
            logger.warn(i18n.getMessage("subscriptionFindFailed",
                                        this.key.getValue()));
            logger.debug("", e);
        }

        if (subscription != null) {
            try
            {
                this.notify(subscription,
                            topic.getTopicPath(),
                            topic.getCurrentMessage());
            }
            catch(Exception e)
            {
                logger.warn(i18n.getMessage("notificationFailed",
                                            this.key.getValue()));
                logger.debug("", e);
            }
        }
    }

    public void topicAdded(Topic topic)
    {
    }

    public void topicRemoved(Topic topic)
    {
    }

    public Subscription getSubscription() throws ResourceException
    {
        ResourceHome home = null;
        try
        {
            Context initialContext = new InitialContext();
            home = (ResourceHome) initialContext.lookup(this.homeLocation);
        }
        catch(Exception e)
        {
            throw new ResourceException(
                i18n.getMessage("subscriptionFindFailed",
                                this.key.getValue()), e);
        }
        return (Subscription) home.find(key);
    }

    /**
     * @return String the JNDI location of the Home of the subscription
     */
    public String getSubscriptionHomeLocation() {
        return this.homeLocation;
    }

    /**
     * @return String the resource key of the subscription
     */
    public ResourceKey getSubscriptionResourceKey() {
        return this.key;
    }


    /**
     * Send a notification
     *
     * @param subscription The subscription for which to send the notification
     * @param topicPath    The topic path of the topic that caused the
     *                     notification
     * @param newValue     The new value of the topic
     * @throws Exception
     */
    protected void notify(
        Subscription subscription, List topicPath, Object newValue)
        throws Exception
    {

        synchronized(subscription)
        {
            if(!subscription.isPaused())
            {
                EndpointReferenceType consumerEPR =
                    subscription.getConsumerReference();
                if(subscription.getUseNotify())
                {
                    ClientSecurityDescriptor descriptor =
                        subscription.getSecurityProperties();
                    setPort(descriptor, consumerEPR);
                    Notify notification = new Notify();
                    EndpointReferenceType producerEndpoint =
                        subscription.getProducerReference();
                    TopicExpressionEngine engine =
                        TopicExpressionEngineImpl.getInstance();
                    TopicExpressionType tp = subscription.getTopicExpression();
                    String dialect = tp.getDialect().toString();
                    TopicExpressionEvaluator evaluator =
                        engine.getEvaluator(dialect);
                    TopicExpressionType topicExpression =
                        (TopicExpressionType) evaluator.toTopicExpression(
                            topicPath);
                    NotificationMessageHolderType[] message =
                        {new NotificationMessageHolderType()};
                    message[0].setProducerReference(producerEndpoint);
                    message[0].setMessage(newValue);
                    message[0].setTopic(topicExpression);
                    notification.setNotificationMessage(message);
                    this.consumerPort.notify(notification);
                }
                else
                {
                    // TODO: raw notifications
                }
            }
        }
    }

    //TODO: revisit this from perf angle, don't thing we need to regenerate
    // the stub quite as aggressively
    private void setPort(
        ClientSecurityDescriptor descriptor,
        EndpointReferenceType consumerEPR)
        throws Exception
    {
        if(descriptor != null)
        {
            if(descriptor.getGSISecureConv() != null)
            {
                logger.debug("Security properties not null: secure conv");
                // cannot reuse locator
                WSBaseNotificationServiceAddressingLocator
                    loc = new WSBaseNotificationServiceAddressingLocator();
                this.consumerPort =
                    loc.getNotificationConsumerPort(consumerEPR);
            }
            else
            {
                // resuse locator, not port
                logger.debug("Security properties not null: not secure conv");
                setPort(false, consumerEPR);
            }
            logger.debug("Setting security properties");
            Stub portStub = (Stub) this.consumerPort;
            // set authz to NoAuthz by default. Could be overridden if
            // authorization property is set in the security
            // descriptor
            if(descriptor.getAuthz() == null)
            {
                descriptor.setAuthz(NoAuthorization.getInstance());
            }
            portStub._setProperty(Constants.CLIENT_DESCRIPTOR, descriptor);
        }
        else
        {
            logger.debug("Security properties null");
            setPort(true, consumerEPR);
        }
    }

    private synchronized void setPort(
        boolean reuse,
        EndpointReferenceType consumerEPR)
        throws Exception
    {

        logger.debug("set port with " + reuse);
        if((reuse) && (this.consumerPort != null))
        {
            return;
        }

        if(this.locator == null)
        {
            this.locator = new SharedWSBaseNotificationServiceAddressingLocator();
        }

        if((this.consumerPort == null) || (!reuse))
        {
            this.consumerPort =
                this.locator.getNotificationConsumerPort(consumerEPR);
        }
    }

    /**
     * This is extended to force the resue of EngineConfiguration and
     * AxisClient engine. Otherwise, each new instance reads the
     * client-config.wsdd file, parses it (using DOM) creating huge
     * overhead.
     */
    private static class SharedWSBaseNotificationServiceAddressingLocator
        extends WSBaseNotificationServiceAddressingLocator {

        private static EngineConfiguration defaultConfig =
            EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
        private static AxisClient defaultEngine = 
            new AxisClient(defaultConfig);
        
        protected AxisClient getAxisClient() {
            return defaultEngine;
        }
        
    }

}
