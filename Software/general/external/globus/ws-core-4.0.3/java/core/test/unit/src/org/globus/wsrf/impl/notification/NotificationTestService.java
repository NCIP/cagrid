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

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.message.addressing.EndpointReferenceType;

import org.oasis.wsn.ResourceUnknownFaultType;
import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsrf.lifetime.Destroy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.Topic;
import org.globus.wsrf.TopicList;
import org.globus.wsrf.TopicListAccessor;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.container.ContainerException;
import org.globus.wsrf.container.ServiceHost;
import org.globus.wsrf.core.notification.SubscriptionManager;
import org.globus.wsrf.core.notification.service.SubscriptionManagerServiceAddressingLocator;
import org.globus.wsrf.test.GridTestSuite;
import org.globus.wsrf.tests.notification.NotificationTestPortType;
import org.globus.wsrf.tests.notification.SelfSubscribeRequest;
import org.globus.wsrf.tests.notification.SelfSubscribeResponse;
import org.globus.wsrf.tests.notification.GenerateNotificationResponse;
import org.globus.wsrf.tests.notification.service.NotificationTestServiceAddressingLocator;
import org.globus.wsrf.utils.AddressingUtils;
import org.globus.wsrf.utils.XmlUtils;

public class NotificationTestService
{
    public static final String SERVICE_PATH =
        "NotificationTestService";
    public static final String TEST_NS =
        "http://wsrf.globus.org/tests/notification";
    public static final QName RP_SET =
        new QName(TEST_NS, "NotificationTestRP");
    public static final QName TEST_TOPIC =
        new QName(TEST_NS, "TestTopic");
    public static final QName EMPTY_TOPIC =
        new QName(TEST_NS, "EmptyTopic");
    public static final QName THIRD_TOPIC =
        new QName(TEST_NS, "ThirdTopic");
    public static final QName FOURTH_TOPIC =
        new QName(TEST_NS, "FourthTopic");
    public static final QName JOB_STATUS_TOPIC_RP =
        new QName(TEST_NS, "JobStatusRP");
    public static final QName AUTO_NOTIFY_TOPIC_RP =
        new QName(TEST_NS, "AutoNotifyTopicRP");
    public static final QName SEND_OLD_TOPIC_RP =
        new QName(TEST_NS, "SendOldTopicRP");
    public static final String JOB_STATUS_ACTIVE =
        "Active";
    public static final String JOB_STATUS_DONE =
        "Done";

    public GenerateNotificationResponse generateNotification(
        QName request)
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
        NotificationTestResource r = (NotificationTestResource)resource;

        topicList = ((TopicListAccessor) resource).getTopicList();
        //TODO: Work around Axis bug
        if(request.getNamespaceURI().equals(""))
        {
            request = new QName(TEST_NS, request.getLocalPart());
        }
        List topicPath = new LinkedList();
        topicPath.add(request);
        Topic topic = topicList.getTopic(topicPath);
        try
        {
            Document document = XmlUtils.newDocument();
            if(topic.equals(r.getJobStatusTopic()))
            {
                r.getJobStatus().setJobState(JOB_STATUS_DONE);
            }
            message = document.createElementNS(
                TEST_NS,
                "test:TestNotification");
            topic.notify(message);
        }
        catch(Exception e)
        {
            throw new RemoteException("", e);
        }
        return new GenerateNotificationResponse();
    }

    public SelfSubscribeResponse selfSubscribe(SelfSubscribeRequest req)
        throws RemoteException
    {

        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();

        if (!(consumer instanceof ServerNotificationConsumerManager))
        {
            throw new RemoteException("Invalid notification consumer manager");
        }

        try
        {
            consumer.startListening();
        }
        catch (Exception e)
        {
            throw new RemoteException("Failed to start consumer manager", e);
        }

        NotificationTestServiceAddressingLocator notificationTestLocator =
            new NotificationTestServiceAddressingLocator();

        SubscriptionManagerServiceAddressingLocator managerLocator =
            new SubscriptionManagerServiceAddressingLocator();

        try
        {
            TestNotifyCallback testCallback = new TestNotifyCallback();
            EndpointReferenceType consumerEPR =
                consumer.createNotificationConsumer(testCallback);
            EndpointReferenceType epr =
                AddressingUtils.createEndpointReference(
                      ServiceHost.getBaseURL() + SERVICE_PATH,
                      NotificationTestHome.GOOD_KEY
            );
            NotificationTestPortType port =
                notificationTestLocator.getNotificationTestPortTypePort(epr);
            Subscribe request = new Subscribe();
            request.setConsumerReference(consumerEPR);
            TopicExpressionType topicPath =
                new TopicExpressionType(WSNConstants.SIMPLE_TOPIC_DIALECT,
                                        FOURTH_TOPIC);
            request.setTopicExpression(topicPath);
            SubscribeResponse response = port.subscribe(request);

            if (!testCallback.waitForCount(1, GridTestSuite.timeout))
            {
                throw new Exception("timeout");
            }
            if (testCallback.getNotifyCount() != 1)
            {
                throw new Exception("expected one notification but got: " +
                                    testCallback.getNotifyCount());
            }

            SubscriptionManager manager =
                managerLocator.getSubscriptionManagerPort(
                    response.getSubscriptionReference());
            manager.destroy(new Destroy());
        }
        catch (Exception e)
        {
            throw new RemoteException("Subscribe failed", e);
        }
        finally
        {
            try
            {
                consumer.stopListening();
            }
            catch (ContainerException e)
            {
                throw new RemoteException("Failed to stop consumer manager", e);
            }
        }

        return new SelfSubscribeResponse();
    }

}
