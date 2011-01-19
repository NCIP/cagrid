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

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.Constants;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.core.notification.ResourcePropertyValueChangeNotificationElementType;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.test.GridTestSuite;
import org.globus.wsrf.tests.notification.JobStatusType;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsrf.properties.InsertType;
import org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType;
import org.oasis.wsrf.properties.SetResourceProperties_Element;

public class ResourcePropertyNotificationTests extends NotificationTestCase
{
    private JobStatusType jobStatus = null;
    private Boolean oldAutoNotify = null;
    private Boolean autoNotify = null;

    public ResourcePropertyNotificationTests(String name)
    {
        super(name);
    }

    public void testResourcePropertyNotification() throws Exception
    {
        TestRPNotifyCallback callback = new TestRPNotifyCallback();
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
            NotificationTestService.JOB_STATUS_TOPIC_RP);
        request.setTopicExpression(topicPath);
        SubscribeResponse response = super.port.subscribe(request);
        super.port.generateNotification(
            NotificationTestService.JOB_STATUS_TOPIC_RP);

        assertTrue(callback.waitForCount(expectedCount, GridTestSuite.timeout));
        assertEquals(expectedCount, callback.getNotifyCount());
        assertTrue(this.jobStatus != null);
        assertEquals(this.jobStatus.getJobState(),
                     NotificationTestService.JOB_STATUS_DONE);
        super.destroySubscription(response.getSubscriptionReference());
    }

    public void testResourcePropertyAutoNotify() throws Exception
    {
        TestRPNotifyCallback callback = new TestRPNotifyCallback();
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
            NotificationTestService.AUTO_NOTIFY_TOPIC_RP);
        request.setTopicExpression(topicPath);
        SubscribeResponse response = super.port.subscribe(request);
        SetResourceProperties_Element setRPRequest = 
            new SetResourceProperties_Element();
        InsertType insert = new InsertType();
        MessageElement [] elements = new MessageElement[1];
        elements[0] = (MessageElement)
            ObjectSerializer.toSOAPElement(
                new Boolean(true),
                NotificationTestService.AUTO_NOTIFY_TOPIC_RP);
        insert.set_any(elements);
        setRPRequest.setInsert(insert);
        super.port.setResourceProperties(setRPRequest);

        assertTrue(callback.waitForCount(expectedCount, GridTestSuite.timeout));
        assertEquals(expectedCount, callback.getNotifyCount());
        assertTrue(this.autoNotify != null);
        assertTrue(this.autoNotify.booleanValue());
        assertEquals(this.oldAutoNotify, null);
        super.destroySubscription(response.getSubscriptionReference());
    }

/* The below does not work as expected */
/*
    public void testResourcePropertySendOld() throws Exception
    {
        int expectedCount = super.notifyCount + 1;
        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();
        consumer.startListening();
        EndpointReferenceType consumerEPR =
            consumer.createNotificationConsumer(this);
        Subscribe request = new Subscribe();
        request.setConsumerReference(consumerEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.AUTO_NOTIFY_TOPIC_RP);
        request.setTopicExpression(topicPath);
        SubscribeResponse response = super.port.subscribe(request);
        _SetResourceProperties setRPRequest = new _SetResourceProperties();
        UpdateType update = new UpdateType();
        MessageElement [] elements = new MessageElement[1];
        elements[0] = (MessageElement)
            ObjectSerializer.toSOAPElement(
                new Boolean(true),
                NotificationTestService.SEND_OLD_TOPIC_RP);
        update.set_any(elements);
        setRPRequest.setUpdate(update);
        super.port.setResourceProperties(setRPRequest);
        Boolean oldValue;
        if(this.autoNotify == null)
        {
            oldValue = new Boolean(false);
            InsertType insert = new InsertType();
            elements[0] = (MessageElement)
                ObjectSerializer.toSOAPElement(
                    oldValue,
                    NotificationTestService.AUTO_NOTIFY_TOPIC_RP);
            insert.set_any(elements);
            setRPRequest = new _SetResourceProperties();
            setRPRequest.setInsert(insert);
            port.setResourceProperties(setRPRequest);
            expectedCount++;
        }
        else
        {
            oldValue = this.autoNotify;
        }
        setRPRequest = new _SetResourceProperties();
        Boolean newValue = new Boolean(!oldValue.booleanValue());
        elements[0] = (MessageElement)
            ObjectSerializer.toSOAPElement(
                newValue,
                NotificationTestService.AUTO_NOTIFY_TOPIC_RP);
        update.set_any(elements);
        setRPRequest.setUpdate(update);
        port.setResourceProperties(setRPRequest);
        waitForCount(expectedCount, GridTestSuite.timeout);
        assertEquals(expectedCount, super.notifyCount);
        assertEquals(oldValue, this.oldAutoNotify);
        assertEquals(newValue, this.autoNotify);
        super.destroySubscription(response.getSubscriptionReference());
    }
*/

    private class TestRPNotifyCallback extends TestNotifyCallback
    {
        public synchronized void deliver(List topicPath,
                                         EndpointReferenceType producer,
                                         Object message)
        {
            ResourcePropertyValueChangeNotificationType changeMessage =
                ((ResourcePropertyValueChangeNotificationElementType) message).
                getResourcePropertyValueChangeNotification();

            if(changeMessage != null)
            {
                if(((QName)topicPath.get(0)).equals(
                    NotificationTestService.JOB_STATUS_TOPIC_RP))
                {
                    try
                    {
                        ResourcePropertyNotificationTests.this.jobStatus =
                            (JobStatusType) changeMessage.getNewValue().
                                get_any()[0].getValueAsType(
                                    new QName(
                                        NotificationTestService.TEST_NS,
                                        "JobStatusRP"),
                                    JobStatusType.class);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(((QName)topicPath.get(0)).equals(
                    NotificationTestService.AUTO_NOTIFY_TOPIC_RP))
                {
                    try
                    {
                        ResourcePropertyNotificationTests.this.autoNotify =
                            (Boolean) changeMessage.getNewValue().
                                get_any()[0].getValueAsType(
                                    Constants.XSD_BOOLEAN);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    if(changeMessage.getOldValue() != null)
                    {
                        try
                        {
                            ResourcePropertyNotificationTests.this.oldAutoNotify
                                = (Boolean) changeMessage.getOldValue().
                                    get_any()[0].getValueAsType(
                                        Constants.XSD_BOOLEAN);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
            super.deliver(topicPath, producer, message);
        }
    }
}
