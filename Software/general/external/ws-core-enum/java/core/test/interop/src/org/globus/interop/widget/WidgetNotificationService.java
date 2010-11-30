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
package org.globus.interop.widget;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.Topic;
import org.globus.wsrf.TopicList;
import org.globus.wsrf.TopicListAccessor;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.impl.SimpleTopic;
import org.globus.wsrf.impl.SimpleTopicList;
import org.globus.wsrf.utils.XmlUtils;
import org.oasis.wsn.ResourceUnknownFaultType;
import com.widgets.GenerateNotification;
import com.widgets.GenerateNotificationResponse;

public class WidgetNotificationService implements Resource,
                                                  ResourceProperties,
                                                  TopicListAccessor
{
    public static final QName RP_SET =
        new QName("http://widgets.com", "WidgetNotification");
    public static final QName TEST_TOPIC =
        new QName("http://widgets.com", "TestTopic");

    private TopicList topicList;
    private ResourcePropertySet propSet;

    public WidgetNotificationService()
    {
        this.propSet = new SimpleResourcePropertySet(RP_SET);
        this.topicList = new SimpleTopicList(this);
        this.topicList.addTopic(new SimpleTopic(TEST_TOPIC));
    }

    public GenerateNotificationResponse generateNotification(
        GenerateNotification request)
        throws RemoteException,
               ResourceUnknownFaultType
    {
        Object resource = null;
        try
        {
            resource = ResourceContext.getResourceContext().getResource();
        }
        catch(RemoteException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            throw new RemoteException("", e);
        }
        TopicList topicList;
        Element message;

        topicList = ((TopicListAccessor) resource).getTopicList();
        List topicPath = new LinkedList();
        topicPath.add(TEST_TOPIC);
        Topic topic = topicList.getTopic(topicPath);
        try
        {
            Document document = XmlUtils.newDocument();
            message = document.createElementNS("http://widgets.com",
                                               "widget:TestNotification");
            topic.notify(message);
        }
        catch(Exception e)
        {
            throw new RemoteException("", e);
        }
        return new GenerateNotificationResponse();
    }

    public ResourcePropertySet getResourcePropertySet()
    {
        return this.propSet;
    }

    public TopicList getTopicList()
    {
        return this.topicList;
    }
}
