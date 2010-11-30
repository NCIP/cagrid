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

import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.test.GridTestSuite;
import org.globus.wsrf.tests.notification.NotificationTestPortType;
import org.globus.wsrf.tests.notification.SelfSubscribeRequest;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsn.TopicExpressionType;
import org.w3c.dom.Element;

public class NotificationConsumerTests extends NotificationTestCase
{
    public NotificationConsumerTests(String name)
    {
        super(name);
    }

    class TestThread extends Thread
    {

        private NotificationTestPortType port;
        private boolean stop = false;

        public TestThread(NotificationTestPortType port)
        {
            this.port = port;
        }

        public void kill()
        {
            stop = true;
        }

        public void run()
        {
            try
            {
                while(!stop)
                {
                    Thread.sleep(1000 * 10);
                    port.generateNotification(
                        NotificationTestService.FOURTH_TOPIC);
                }
            }
            catch (Exception e)
            {
                // ignore errors
            }
        }
    }


    public void testServerNotificationConsumerManager() throws Exception
    {
         TestThread t = new TestThread(super.port);
         t.start();
        SelfSubscribeRequest request = new SelfSubscribeRequest();
        try
        {
            super.port.selfSubscribe(request);
        }
        finally
        {
            t.kill();
        }
    }

    public void testAllCallback() throws Exception
    {
        TestNotifyCallback callback = new TestNotifyCallback();
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
        SubscribeResponse subscribeResponse1 = super.port.subscribe(request);
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);

        assertTrue(callback.waitForCount(1, GridTestSuite.timeout));
        assertEquals(1, callback.getNotifyCount());
        assertEquals(NotificationTestService.TEST_TOPIC,
                     callback.getNotificationTopic());
        assertEquals(new QName(NotificationTestService.TEST_NS,
                               "TestNotification"),
                     new QName(
                         ((Element) callback.getMessage()).getNamespaceURI(),
                         ((Element) callback.getMessage()).getLocalName()));

        topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.THIRD_TOPIC);
        request.setTopicExpression(topicPath);
        SubscribeResponse subscribeResponse2 = super.port.subscribe(request);
        super.port.generateNotification(NotificationTestService.THIRD_TOPIC);

        assertTrue(callback.waitForCount(2, GridTestSuite.timeout));
        assertEquals(2, callback.getNotifyCount());
        assertEquals(NotificationTestService.THIRD_TOPIC,
                     callback.getNotificationTopic());
        assertEquals(new QName(NotificationTestService.TEST_NS,
                               "TestNotification"),
                     new QName(
                         ((Element) callback.getMessage()).getNamespaceURI(),
                         ((Element) callback.getMessage()).getLocalName()));
        super.destroySubscription(
            subscribeResponse1.getSubscriptionReference());
        super.destroySubscription(
            subscribeResponse2.getSubscriptionReference());
    }

    public void testTopicCallback() throws Exception
    {
        TestNotifyCallback testCallback = new TestNotifyCallback();
        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();
        consumer.startListening();
        List testTopicPath = new LinkedList();
        testTopicPath.add(NotificationTestService.TEST_TOPIC);
        EndpointReferenceType consumerEPR =
            consumer.createNotificationConsumer(testTopicPath,
                                                testCallback);
        Subscribe request = new Subscribe();
        request.setConsumerReference(consumerEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        SubscribeResponse subscribeResponse1 = super.port.subscribe(request);
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);

        assertTrue(testCallback.waitForCount(1, GridTestSuite.timeout));
        assertEquals(1, testCallback.getNotifyCount());
        assertEquals(NotificationTestService.TEST_TOPIC,
                     testCallback.getNotificationTopic());
        assertEquals(
            new QName(NotificationTestService.TEST_NS,
                      "TestNotification"),
            new QName(
                ((Element) testCallback.getMessage()).getNamespaceURI(),
                ((Element) testCallback.getMessage()).getLocalName()));

        List thirdTopicPath = new LinkedList();
        thirdTopicPath.add(NotificationTestService.THIRD_TOPIC);
        TestNotifyCallback thirdCallback = new TestNotifyCallback();
        consumerEPR =
            consumer.createNotificationConsumer(thirdTopicPath,
                                                thirdCallback);
        request.setConsumerReference(consumerEPR);
        topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.THIRD_TOPIC);
        request.setTopicExpression(topicPath);
        SubscribeResponse subscribeResponse2 = super.port.subscribe(request);
        super.port.generateNotification(NotificationTestService.THIRD_TOPIC);

        assertTrue(thirdCallback.waitForCount(1, GridTestSuite.timeout));
        assertEquals(1, thirdCallback.getNotifyCount());
        assertEquals(NotificationTestService.THIRD_TOPIC,
                     thirdCallback.getNotificationTopic());
        assertEquals(
            new QName(NotificationTestService.TEST_NS,
                      "TestNotification"),
            new QName(
                ((Element) thirdCallback.getMessage()).getNamespaceURI(),
                ((Element) thirdCallback.getMessage()).getLocalName()));
        super.destroySubscription(
            subscribeResponse1.getSubscriptionReference());
        super.destroySubscription(
            subscribeResponse2.getSubscriptionReference());
    }
}
