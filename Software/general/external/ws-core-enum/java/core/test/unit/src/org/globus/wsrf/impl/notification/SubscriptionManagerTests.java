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

import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.apache.axis.message.addressing.EndpointReferenceType;

import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.Constants;
import org.globus.wsrf.core.notification.SubscriptionManager;
import org.globus.wsrf.core.notification.service.SubscriptionManagerServiceAddressingLocator;
import org.globus.wsrf.impl.SimpleResourceKey;
import org.globus.wsrf.test.GridTestSuite;
import org.globus.wsrf.utils.AddressingUtils;

import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsrf.lifetime.SetTerminationTime;
import org.oasis.wsn.ResourceUnknownFaultType;
import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsn.PauseSubscription;
import org.oasis.wsn.ResumeSubscription;
import org.oasis.wsrf.properties.QueryExpressionType;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;

public class SubscriptionManagerTests extends NotificationTestCase
{
    private SubscriptionManagerServiceAddressingLocator managerLocator =
        new SubscriptionManagerServiceAddressingLocator();

    private EndpointReferenceType consumerEPR = null;
    private TestNotifyCallback callback = null;
    private SubscriptionManager manager = null;
    private SubscriptionManager badManager = null;

    public SubscriptionManagerTests(String name)
    {
        super(name);
    }

    /* Not a very good test - not sure how to make it better */
    public void testPauseResume() throws Exception
    {
        int expectedCount = this.callback.getNotifyCount() + 1;
        this.manager.pauseSubscription(new PauseSubscription());
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);
        this.manager.resumeSubscription(new ResumeSubscription());
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);

        assertTrue(this.callback.waitForCount(expectedCount,
                                              GridTestSuite.timeout));
        assertEquals(expectedCount, this.callback.getNotifyCount());
    }

    public void testPauseResourceUnknown() throws Exception
    {
        ResourceUnknownFaultType fault = null;
        try
        {
            this.badManager.pauseSubscription(new PauseSubscription());
        }
        catch(ResourceUnknownFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }

    public void testResumeResourceUnknown() throws Exception
    {
        ResourceUnknownFaultType fault = null;
        try
        {
            this.badManager.resumeSubscription(new ResumeSubscription());
        }
        catch(ResourceUnknownFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }

    /* Core doesn't use PauseFailed and ResumeFailed, so can't test those */

    public void testDestroySubscription() throws Exception
    {
        ResourceUnknownFaultType fault = null;
        int expectedCount = 0;
        TestNotifyCallback callback = new TestNotifyCallback();
        EndpointReferenceType managerEPR = this.subscribe(callback);
        SubscriptionManager manager =
            this.managerLocator.getSubscriptionManagerPort(
                managerEPR);
        manager.destroy(new Destroy());
        try
        {
            manager.pauseSubscription(new PauseSubscription());
        }
        catch(ResourceUnknownFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
        super.port.generateNotification(NotificationTestService.TEST_TOPIC);

        // no notification should be generated
        Thread.sleep(15000);
        assertEquals(expectedCount, callback.getNotifyCount());
    }

    /* Sweeper runs every 60s right now */

    public void testTerminateSubscription() throws Exception
    {
        ResourceUnknownFaultType fault = null;
        int expectedCount = 0;
        TestNotifyCallback callback = new TestNotifyCallback();
        EndpointReferenceType managerEPR = this.subscribe(callback);
        SubscriptionManager manager =
            this.managerLocator.getSubscriptionManagerPort(
                managerEPR);
        SetTerminationTime terminationTimeRequest = new SetTerminationTime();
        Calendar terminationTime = Calendar.getInstance();
        terminationTime.add(Calendar.SECOND, 5);
        terminationTimeRequest.setRequestedTerminationTime(terminationTime);
        manager.setTerminationTime(terminationTimeRequest);

        int wait = 0;
        while(wait < GridTestSuite.timeout) {
            try
            {
                manager.resumeSubscription(new ResumeSubscription());
                wait += 1000 * 45;
                Thread.sleep(1000 * 45);
            }
            catch(ResourceUnknownFaultType e)
            {
                fault = e;
                break;
            }
        }

        assertTrue("timeout or exception not generated", fault != null);
        port.generateNotification(NotificationTestService.TEST_TOPIC);

        // no notification should be generated
        Thread.sleep(15000);
        assertEquals(expectedCount, callback.getNotifyCount());
    }

    public void testSubscriptionTimeRPs() throws Exception
    {
        GetResourcePropertyResponse response =
            this.manager.getResourceProperty(WSRFConstants.TERMINATION_TIME);
        MessageElement[] timeElements = response.get_any();
        assertTrue(timeElements.length == 1);
        assertEquals(timeElements[0].getQName(),
                     WSRFConstants.TERMINATION_TIME);
        Calendar terminationTime = (Calendar) timeElements[0].getValueAsType(
            org.apache.axis.Constants.XSD_DATETIME);
        response = this.manager.getResourceProperty(
            WSNConstants.CREATION_TIME);
        timeElements = response.get_any();
        assertTrue(timeElements.length == 1);
        assertEquals(timeElements[0].getQName(), WSNConstants.CREATION_TIME);
        Calendar creationTime = (Calendar) timeElements[0].getValueAsType(
            org.apache.axis.Constants.XSD_DATETIME);
        assertTrue(terminationTime.after(creationTime));
        response = this.manager.getResourceProperty(WSRFConstants.CURRENT_TIME);
        timeElements = response.get_any();
        assertTrue(timeElements.length == 1);
        assertEquals(timeElements[0].getQName(), WSRFConstants.CURRENT_TIME);
        Calendar currentTime = (Calendar) timeElements[0].getValueAsType(
            org.apache.axis.Constants.XSD_DATETIME);
        assertTrue(terminationTime.after(currentTime));
     }

    public void testConsumerReferenceRP() throws Exception
    {
        GetResourcePropertyResponse response =
            this.manager.getResourceProperty(WSNConstants.CONSUMER_REFERENCE);
        MessageElement[] responseElements = response.get_any();
        assertTrue(responseElements.length == 1);
        assertEquals(responseElements[0].getQName(),
                     WSNConstants.CONSUMER_REFERENCE);
        EndpointReferenceType consumerReference = (EndpointReferenceType)
            responseElements[0].getValueAsType(
                EndpointReferenceType.getTypeDesc().getXmlType());
        assertEquals(consumerReference.getAddress().toString(),
                     this.consumerEPR.getAddress().toString());
        MessageElement[] rpReferenceProps =
            consumerReference.getProperties().get_any();
        MessageElement[] referenceProps =
            this.consumerEPR.getProperties().get_any();
        assertEquals(rpReferenceProps.length, referenceProps.length);
        assertEquals(rpReferenceProps[0].getQName(),
                     referenceProps[0].getQName());
        assertEquals(rpReferenceProps[0].getValue(),
                     referenceProps[0].getValue());
    }

    public void testTopicExpressionRP() throws Exception
    {
        GetResourcePropertyResponse response =
            this.manager.getResourceProperty(WSNConstants.TOPIC_EXPRESSION);
        MessageElement[] responseElements = response.get_any();
        assertTrue(responseElements.length == 1);
        assertEquals(responseElements[0].getQName(),
                     WSNConstants.TOPIC_EXPRESSION);
        TopicExpressionType topic = (TopicExpressionType)
            responseElements[0].getValueAsType(
                TopicExpressionType.getTypeDesc().getXmlType());
        assertEquals(WSNConstants.SIMPLE_TOPIC_DIALECT, 
                     topic.getDialect().toString());
        assertEquals(NotificationTestService.TEST_TOPIC, 
                     topic.getValue());
    }

    public void testUseNotifyRP() throws Exception
    {
        GetResourcePropertyResponse response =
            this.manager.getResourceProperty(WSNConstants.USE_NOTIFY);
        MessageElement[] responseElements = response.get_any();
        assertTrue(responseElements.length == 1);
        assertEquals(responseElements[0].getQName(),
                     WSNConstants.USE_NOTIFY);
        assertEquals((Boolean) responseElements[0].getValueAsType(
            org.apache.axis.Constants.XSD_BOOLEAN), Boolean.TRUE);
    }

    public void testPreConditionRP() throws Exception
    {
        GetResourcePropertyResponse response =
            this.manager.getResourceProperty(WSNConstants.PRECONDITION);
        MessageElement[] responseElements = response.get_any();
        assertTrue(responseElements.length == 1);
        assertEquals(responseElements[0].getQName(),
                     WSNConstants.PRECONDITION);
        QueryExpressionType query =
            (QueryExpressionType) responseElements[0].getValueAsType(
                QueryExpressionType.getTypeDesc().getXmlType());
        assertEquals(WSRFConstants.XPATH_1_DIALECT,
                     query.getDialect().toString());
        assertEquals("/foo", query.getValue());
    }

    public void testSelectorRP() throws Exception
    {
        GetResourcePropertyResponse response =
            this.manager.getResourceProperty(WSNConstants.SELECTOR);
        MessageElement[] responseElements = response.get_any();
        assertTrue(responseElements.length == 1);
        assertEquals(responseElements[0].getQName(),
                     WSNConstants.SELECTOR);
        QueryExpressionType query =
            (QueryExpressionType) responseElements[0].getValueAsType(
                QueryExpressionType.getTypeDesc().getXmlType());
        assertEquals(WSRFConstants.XPATH_1_DIALECT,
                     query.getDialect().toString());
        assertEquals("/foo", query.getValue());
    }

    public void testPolicyRP() throws Exception
    {
        GetResourcePropertyResponse response =
            this.manager.getResourceProperty(WSNConstants.SUBSCRIPTION_POLICY);
        MessageElement[] responseElements = response.get_any();
        assertTrue(responseElements.length == 1);
        assertEquals(responseElements[0].getQName(),
                     WSNConstants.SUBSCRIPTION_POLICY);
        assertEquals("foo", responseElements[0].getValue());
    }

    private EndpointReferenceType subscribe(TestNotifyCallback callback)
        throws Exception
    {
        if(callback == null)
        {
            if(this.callback == null)
            {
                this.callback = new TestNotifyCallback();
            }
            callback = this.callback;
        }
        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();
        consumer.startListening();
        this.consumerEPR =
            consumer.createNotificationConsumer(callback);
        Subscribe request = new Subscribe();
        request.setConsumerReference(this.consumerEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        Calendar terminationTime = Calendar.getInstance();
        terminationTime.add(Calendar.SECOND, 200);
        request.setInitialTerminationTime(terminationTime);
        request.setSubscriptionPolicy("foo");
        QueryExpressionType query = new QueryExpressionType();
        query.setDialect(WSRFConstants.XPATH_1_DIALECT);
        query.setValue("/foo");
        request.setPrecondition(query);
        request.setSelector(query);
        SubscribeResponse response = super.port.subscribe(request);
        return response.getSubscriptionReference();
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        EndpointReferenceType managerEPR = this.subscribe(null);
        this.manager =
            this.managerLocator.getSubscriptionManagerPort(
                managerEPR);
        EndpointReferenceType badManagerEPR =
            AddressingUtils.createEndpointReference(
                managerEPR.getAddress().toString(),
                new SimpleResourceKey(new QName(Constants.CORE_NS,
                                                "SubscriptionKey"),
                                      "1"));
        this.badManager =
            this.managerLocator.getSubscriptionManagerPort(
                badManagerEPR);
    }

    protected void tearDown() throws Exception
    {
        this.manager.destroy(new Destroy());
        super.tearDown();
    }
}
