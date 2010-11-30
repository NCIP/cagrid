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

import org.apache.axis.message.MessageElement;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.tests.notification.JobStatusType;
import org.oasis.wsn.GetCurrentMessage;
import org.oasis.wsn.GetCurrentMessageResponse;
import org.oasis.wsn.NoCurrentMessageOnTopicFaultType;
import org.oasis.wsn.ResourceUnknownFaultType;
import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.TopicNotSupportedFaultType;
import org.oasis.wsn.TopicPathDialectUnknownFaultType;
import org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType;

public class GetCurrentMessageTests extends NotificationTestCase
{
    public GetCurrentMessageTests(String name)
    {
        super(name);
    }

    public void testGetCurrentMessageElementType() throws Exception
    {
        port2.generateNotification(NotificationTestService.TEST_TOPIC);
        GetCurrentMessage request = new GetCurrentMessage();
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.TEST_TOPIC);
        request.setTopic(topicPath);
        GetCurrentMessageResponse response = port2.getCurrentMessage(request);
        assertTrue(response.get_any()[0].getLocalName() == "TestNotification" &&
                   response.get_any()[0].getNamespaceURI() ==
                   NotificationTestService.TEST_NS);
    }

    public void testGetCurrentMessageRPChangeType() throws Exception
    {
        port2.generateNotification(
                     NotificationTestService.JOB_STATUS_TOPIC_RP);
        GetCurrentMessage request = new GetCurrentMessage();
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.JOB_STATUS_TOPIC_RP);
        request.setTopic(topicPath);
        GetCurrentMessageResponse response = port2.getCurrentMessage(request);
        MessageElement [] any = response.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);
        ResourcePropertyValueChangeNotificationType changeType =
            (ResourcePropertyValueChangeNotificationType)
                ObjectDeserializer.toObject(
                    any[0], 
                    ResourcePropertyValueChangeNotificationType.class);
        any = changeType.getNewValue().get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);
        JobStatusType jobStatus = 
            (JobStatusType)
                ObjectDeserializer.toObject(
                    any[0],
                    JobStatusType.class);
        assertEquals(NotificationTestService.JOB_STATUS_DONE,
                     jobStatus.getJobState());
    }

    public void testResourceUnknown() throws Exception
    {
        ResourceUnknownFaultType fault = null;
        GetCurrentMessage request = new GetCurrentMessage();
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            new QName("foo"));
        request.setTopic(topicPath);
        try
        {
            super.badPort.getCurrentMessage(request);
        }
        catch(ResourceUnknownFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }

    public void testUnsupportedTopic() throws Exception
    {
        TopicNotSupportedFaultType fault = null;
        GetCurrentMessage request = new GetCurrentMessage();
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            new QName("foo"));
        request.setTopic(topicPath);
        try
        {
            super.port.getCurrentMessage(request);
        }
        catch(TopicNotSupportedFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }

    public void testNoCurrentMessage() throws Exception
    {
        NoCurrentMessageOnTopicFaultType fault = null;
        GetCurrentMessage request = new GetCurrentMessage();
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.EMPTY_TOPIC);
        request.setTopic(topicPath);
        try
        {
            super.port.getCurrentMessage(request);
        }
        catch(NoCurrentMessageOnTopicFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }
/*
    public void testInvalidTopicExpression() throws Exception
    {
        InvalidTopicExpressionFaultType fault = null;
        GetCurrentMessage request = new GetCurrentMessage();
        TopicExpressionType topicPath = new TopicExpressionType(
            new URI(""),
            NotificationTestService.EMPTY_TOPIC);
        request.setTopic(topicPath);
        try
        {
            super.port.getCurrentMessage(request);
        }
        catch(InvalidTopicExpressionFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }
*/
    public void testTopicPathDialectUnknown() throws Exception
    {
        TopicPathDialectUnknownFaultType fault = null;
        GetCurrentMessage request = new GetCurrentMessage();
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.CONCRETE_TOPIC_DIALECT,
            NotificationTestService.EMPTY_TOPIC);
        request.setTopic(topicPath);
        try
        {
            super.port.getCurrentMessage(request);
        }
        catch(TopicPathDialectUnknownFaultType e)
        {
            fault = e;
        }
        assertTrue(fault != null);
    }
}
