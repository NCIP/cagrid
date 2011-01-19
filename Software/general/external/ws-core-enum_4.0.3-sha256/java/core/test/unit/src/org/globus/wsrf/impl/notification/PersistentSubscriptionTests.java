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

import java.util.Calendar;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.core.notification.PersistenceTestSubscriptionManager;
import org.globus.wsrf.core.notification.service.PersistenceTestSubscriptionManagerServiceAddressingLocator;
import org.globus.wsrf.test.GridTestSuite;
import org.globus.wsrf.tests.invalidate.InvalidateRequest;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsrf.lifetime.ResourceUnknownFaultType;

public class PersistentSubscriptionTests extends NotificationTestCase
{
    private PersistenceTestSubscriptionManagerServiceAddressingLocator managerLocator =
        new PersistenceTestSubscriptionManagerServiceAddressingLocator();

    public PersistentSubscriptionTests(String name)
    {
        super(name);
    }

    public void testSubscriptionResourcePersistence() throws Exception
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
        PersistenceTestSubscriptionManager manager =
            this.managerLocator.getPersistenceTestSubscriptionManagerPort(
                response.getSubscriptionReference());
        manager.invalidate(new InvalidateRequest());
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);
        assertTrue(callback.waitForCount(expectedCount, GridTestSuite.timeout));
        assertEquals(expectedCount, callback.getNotifyCount());
        manager.destroy(new Destroy());
    }

    public void testSubscriptionPersistence() throws Exception
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
        PersistenceTestSubscriptionManager manager =
            this.managerLocator.getPersistenceTestSubscriptionManagerPort(
                response.getSubscriptionReference());
        manager.invalidate(new InvalidateRequest());
        super.port.invalidate(new InvalidateRequest());
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);
        assertTrue(callback.waitForCount(expectedCount, GridTestSuite.timeout));
        assertEquals(expectedCount, callback.getNotifyCount());
        manager.destroy(new Destroy());
    }

    public void testSubscriptionPersistenceTimeout() throws Exception
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

        Calendar termTime = Calendar.getInstance();
        termTime.add(Calendar.SECOND, 30);
        request.setInitialTerminationTime(termTime);

        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        SubscribeResponse response = super.port.subscribe(request);
        PersistenceTestSubscriptionManager manager =
            this.managerLocator.getPersistenceTestSubscriptionManagerPort(
                response.getSubscriptionReference());
        manager.invalidate(new InvalidateRequest());

        // sleep 1 min
        Thread.sleep(60 * 1000);

        try 
        {
            manager.destroy(new Destroy());
        } 
        catch (ResourceUnknownFaultType e) 
        {
            //it's ok
        }
    }

    public void testSubscriptionListenerPersistence() throws Exception
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
        PersistenceTestSubscriptionManager manager =
            this.managerLocator.getPersistenceTestSubscriptionManagerPort(
                response.getSubscriptionReference());
        super.port.invalidate(new InvalidateRequest());
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);
        assertTrue(callback.waitForCount(expectedCount, GridTestSuite.timeout));
        assertEquals(expectedCount, callback.getNotifyCount());
        manager.destroy(new Destroy());
    }

}
