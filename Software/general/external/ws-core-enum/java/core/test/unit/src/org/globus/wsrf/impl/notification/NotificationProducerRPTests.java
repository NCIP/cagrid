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

import org.apache.axis.Constants;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.globus.wsrf.WSNConstants;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;

public class NotificationProducerRPTests extends NotificationTestCase
{
    public NotificationProducerRPTests(String name)
    {
        super(name);
    }

    public void testGetTopics() throws Exception
    {
        boolean foundEmptyTopic = false;
        boolean foundTestTopic = false;
        boolean foundThirdTopic = false;
        GetResourcePropertyResponse response =
            super.port.getResourceProperty(WSNConstants.TOPIC);
        MessageElement[] topics = response.get_any();
        assertEquals(7, topics.length);
        for(int i = 0; i < topics.length; i++)
        {
            MessageElement topic = topics[i];
            assertEquals(topic.getQName(), WSNConstants.TOPIC);
            if(topic.getValueAsType(Constants.XSD_QNAME).equals(
                NotificationTestService.EMPTY_TOPIC))
            {
                foundEmptyTopic = true;
            }
            if(topic.getValueAsType(Constants.XSD_QNAME).equals(
                NotificationTestService.TEST_TOPIC))
            {
                foundTestTopic = true;
            }
            if(topic.getValueAsType(Constants.XSD_QNAME).equals(
                NotificationTestService.THIRD_TOPIC))
            {
                foundThirdTopic = true;
            }
        }
        assertTrue(foundEmptyTopic);
        assertTrue(foundTestTopic);
        assertTrue(foundThirdTopic);
    }

    public void testGetFixedTopicSet() throws Exception
    {
        GetResourcePropertyResponse response =
            super.port.getResourceProperty(WSNConstants.FIXED_TOPIC_SET);
        MessageElement[] topics = response.get_any();
        assertEquals(1, topics.length);
        MessageElement topic = topics[0];
        assertEquals(WSNConstants.FIXED_TOPIC_SET, topic.getQName());
        assertEquals(true, ((Boolean) topic.getValueAsType(
            Constants.XSD_BOOLEAN)).booleanValue());
    }

    public void testGetDialects() throws Exception
    {
        GetResourcePropertyResponse response =
            super.port.getResourceProperty(
                WSNConstants.TOPIC_EXPRESSION_DIALECTS);
        MessageElement[] topics = response.get_any();
        assertEquals(1, topics.length);
        MessageElement topic = topics[0];
        assertEquals(WSNConstants.TOPIC_EXPRESSION_DIALECTS, topic.getQName());
        assertEquals(WSNConstants.SIMPLE_TOPIC_DIALECT, 
                     ((URI)topic.getValueAsType(Constants.XSD_ANYURI)).toString());
    }
}
