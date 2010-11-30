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

import javax.xml.namespace.QName;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.test.GridTestSuite;
import org.oasis.wsn.ResourceUnknownFaultType;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.TopicNotSupportedFaultType;
import org.oasis.wsn.TopicPathDialectUnknownFaultType;

public class SubscribeTests extends NotificationTestCase
{
    public SubscribeTests(String name)
    {
        super(name);
    }

    public void testSubscribe() throws Exception
    {
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
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);
        assertTrue(callback.waitForCount(expectedCount, GridTestSuite.timeout));
        assertEquals(expectedCount, callback.getNotifyCount());
        super.destroySubscription(response.getSubscriptionReference());
    }

    public void testSubscribeResourceUnknown() throws Exception
    {
        ResourceUnknownFaultType fault = null;
        Subscribe request = new Subscribe();
        request.setConsumerReference(badEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        try
        {
            badPort.subscribe(request);
        }
        catch(ResourceUnknownFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }

    public void testSubscribeTopicPathDialectUnknown() throws Exception
    {
        TopicPathDialectUnknownFaultType fault = null;
        Subscribe request = new Subscribe();
        request.setConsumerReference(super.testServiceEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.CONCRETE_TOPIC_DIALECT,
            NotificationTestService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        try
        {
            super.port.subscribe(request);
        }
        catch(TopicPathDialectUnknownFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }

    public void testSubscribeUnsupportedTopic() throws Exception
    {
        TopicNotSupportedFaultType fault = null;
        Subscribe request = new Subscribe();
        request.setConsumerReference(super.testServiceEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            new QName("foo"));
        request.setTopicExpression(topicPath);
        try
        {
            super.port.subscribe(request);
        }
        catch(TopicNotSupportedFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }
}
