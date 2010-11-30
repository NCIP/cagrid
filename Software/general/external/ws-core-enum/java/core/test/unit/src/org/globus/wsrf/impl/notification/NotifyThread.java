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
package org.globus.wsrf.impl.notification;

import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsn.TopicExpressionType;

import org.apache.axis.message.addressing.EndpointReferenceType;

import javax.xml.rpc.Stub;

import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.core.notification.SubscriptionManager;
import org.globus.wsrf.security.Constants;
import org.globus.wsrf.impl.security.descriptor.ClientSecurityDescriptor;
import org.globus.wsrf.tests.notification.NotificationTestPortType;
import org.globus.wsrf.tests.notification.service.NotificationTestServiceAddressingLocator;
import org.globus.wsrf.core.notification.service.SubscriptionManagerServiceAddressingLocator;
import org.globus.wsrf.test.GridTestSuite;

public class NotifyThread extends Thread {

    SubscriptionManagerServiceAddressingLocator managerLocator =
        new SubscriptionManagerServiceAddressingLocator();

    EndpointReferenceType epr;
    NotificationConsumerManager consumer;
    ClientSecurityDescriptor secDesc;
    Object lock;

    Throwable exception;
    boolean subscribed = false;

    public NotifyThread(EndpointReferenceType epr,
                        NotificationConsumerManager consumer,
                        Object lock,
                        ClientSecurityDescriptor secDesc) {
        this.consumer = consumer;
        this.lock = lock;
        this.secDesc = secDesc;
        this.epr = epr;
    }
        
    public boolean isSubscribed() {
        return this.subscribed;
    }

    public Throwable getException() {
        return this.exception;
    }

    public void run() {
        try {
            TestNotifyCallback callback = new TestNotifyCallback();
            EndpointReferenceType consumerEPR =
                consumer.createNotificationConsumer(callback);

            Subscribe request = new Subscribe();
            request.setConsumerReference(consumerEPR);
            TopicExpressionType topicPath = 
                new TopicExpressionType(
                                        WSNConstants.SIMPLE_TOPIC_DIALECT,
                                        NotificationTestService.TEST_TOPIC);
            request.setTopicExpression(topicPath);
            
            NotificationTestServiceAddressingLocator locator =
                new NotificationTestServiceAddressingLocator();
            NotificationTestPortType port = 
                locator.getNotificationTestPortTypePort(epr);
            if (this.secDesc != null) {
                ((Stub) port)._setProperty(Constants.CLIENT_DESCRIPTOR, 
                                           this.secDesc);
            }
            SubscribeResponse response = port.subscribe(request);
            
            this.subscribed = true;
            synchronized (lock) {
                lock.wait();
            }
            
            int expectedCount = 1;
            if (!callback.waitForCount(expectedCount, 
                                       GridTestSuite.timeout)) {
                throw new Exception("Timeout waiting for notifcations");
            }
            
            if (expectedCount != callback.getNotifyCount()) {
                throw new Exception("Expected " + expectedCount + 
                                    " notifications but received " + 
                                    callback.getNotifyCount());
            }
            
            SubscriptionManager manager =
                managerLocator.getSubscriptionManagerPort(response.getSubscriptionReference());
            if (this.secDesc != null) {
                ((Stub) manager)._setProperty(Constants.CLIENT_DESCRIPTOR, 
                                              this.secDesc);
            }
            manager.destroy(new Destroy());
        } catch (Throwable e) {
            this.exception = e;
        }
    }
    
}
