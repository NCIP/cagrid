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

import org.apache.axis.message.addressing.EndpointReferenceType;

import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.SubscribeResponse;

import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.core.notification.SubscriptionManager;
import org.globus.wsrf.test.GridTestSuite;

public class NotificationTests extends NotificationTestCase {

    public NotificationTests(String name) {
        super(name);
    }

   public void testMultipleNotifications() throws Exception {
        TestNotifyCallback callback = new TestNotifyCallback();
        int expectedCount = 1;
        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();
        consumer.startListening();
        EndpointReferenceType consumerEPR =
            consumer.createNotificationConsumer(callback);
        Subscribe request = new Subscribe();
        request.setConsumerReference(consumerEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        SubscribeResponse response = super.port.subscribe(request);
        for(int i = 0; i < 10; i++) {
            super.port.generateNotification(NotificationTestService.TEST_TOPIC);
            assertTrue("timed out waiting for notification #" + expectedCount,
                       callback.waitForCount(expectedCount, GridTestSuite.timeout));
            assertEquals(expectedCount, callback.getNotifyCount());
            expectedCount++;
        }
        SubscriptionManager manager =
            this.managerLocator.getSubscriptionManagerPort(
                response.getSubscriptionReference());
        manager.destroy(new Destroy());
    }

    public void testMultithreadedNotifications() throws Throwable {

        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();
        consumer.startListening();

        Object lock = new Object();

        int size = 10;
        NotifyThread [] threads = new NotifyThread[size];
        for (int i=0;i<size;i++) {
            threads[i] = new NotifyThread(testServiceEPR, consumer, 
                                          lock, null);
            threads[i].start();
        }

        for (int i=0;i<size;i++) {
            while( !threads[i].isSubscribed() ) {
                Throwable e = threads[i].getException();
                if (e != null) {
                    throw e;
                }
                Thread.sleep(100);
            }
        }
            
        synchronized(lock) {
            lock.notifyAll();
        }
        
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);

        Throwable e = null;

        for (int i=0;i<size;i++) {
            threads[i].join();
            if (threads[i].getException() != null) {
                threads[i].getException().printStackTrace();
                if (e == null) {
                    e = threads[i].getException();
                }
            }
        }

        if (e != null) {
            throw e;
        }
    }

}
