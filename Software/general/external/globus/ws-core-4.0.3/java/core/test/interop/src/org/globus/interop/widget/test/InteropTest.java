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
package org.globus.interop.widget.test;

import java.net.URL;
import java.util.Calendar;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.message.MessageElement;
import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.message.addressing.ReferencePropertiesType;
import org.apache.axis.types.URI;

import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.GetCurrentMessage;
import org.oasis.wsn.GetCurrentMessageResponse;
import org.oasis.wsn.PauseSubscription;
import org.oasis.wsn.ResumeSubscription;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsrf.lifetime.SetTerminationTime;
import org.oasis.wsrf.lifetime.SetTerminationTimeResponse;
import org.oasis.wsrf.properties.DeleteType;
import org.oasis.wsrf.properties.InsertType;
import org.oasis.wsrf.properties.QueryExpressionType;
import org.oasis.wsrf.properties.UpdateType;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.SetResourceProperties_Element;
import org.w3c.dom.Node;

import org.globus.interop.widget.Widget;
import org.globus.interop.widget.WidgetNotificationService;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.core.notification.SubscriptionManager;
import org.globus.wsrf.core.notification.service.SubscriptionManagerServiceAddressingLocator;
import org.globus.wsrf.core.tests.interop.NotificationConsumerFactory;
import org.globus.wsrf.core.tests.interop.CreateNotificationConsumer;
import org.globus.wsrf.core.tests.interop.service.NotificationConsumerFactoryServiceAddressingLocator;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.test.GridTestCase;
import com.widgets.WidgetNotificationPortType;
import com.widgets.WidgetPortType;
import com.widgets.CreateWidget;
import com.widgets.CreateWidgetResponse;
import com.widgets.GenerateNotification;
import com.widgets.service.WidgetNotificationServiceAddressingLocator;
import com.widgets.service.WidgetServiceAddressingLocator;
import junit.framework.TestSuite;

public class InteropTest extends GridTestCase {

    private WidgetServiceAddressingLocator locator =
        new WidgetServiceAddressingLocator();

    private WidgetNotificationServiceAddressingLocator notificationLocator =
        new WidgetNotificationServiceAddressingLocator();

    private SubscriptionManagerServiceAddressingLocator subscriptionManagerLocator =
        new SubscriptionManagerServiceAddressingLocator();

    private static String serviceAddress = null;

    private static String notificationServiceAddress = null;

    private static String consumerFactoryServiceAddress = null;

    private static String queryString =
        "boolean(/*/*[namespace-uri()='" +
        WSRFConstants.LIFETIME_NS + "' and local-name()='TerminationTime'])";

    public InteropTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception
    {
    }

    public static void main(String [] args) {
        TestSuite testSuite = null;
        for (int i=0;i<args.length;i++) {
            if (args[i].equalsIgnoreCase("-service")) {
                serviceAddress = args[++i];
            } else if (args[i].equalsIgnoreCase("-notificationService")) {
                notificationServiceAddress = args[++i];
            } else if (args[i].equalsIgnoreCase("-consumerService")) {
                    consumerFactoryServiceAddress = args[++i];
            } else if (args[i].equalsIgnoreCase("-query")) {
                queryString = args[++i];
            } else {
                if (testSuite == null) {
                    testSuite = new TestSuite();
                }
                testSuite.addTest(new InteropTest(args[i]));
            }
        }

        // run all
        if (testSuite == null) {
            testSuite = new TestSuite(InteropTest.class);
        }

        junit.textui.TestRunner.run (testSuite);
    }



    private String getServiceAddress() {
        if (serviceAddress != null) {
            return serviceAddress;
        }
        if (TEST_CONTAINER == null) {
            return "http://127.0.0.1:8080/wsrf/services/WidgetService";
        } else {
            return TEST_CONTAINER.getBaseURL() + "WidgetService";
        }
    }

    private String getNotificationServiceAddress() {
        if (notificationServiceAddress != null) {
            return notificationServiceAddress;
        }
        if (TEST_CONTAINER == null) {
            return "http://127.0.0.1:8080/wsrf/services/WidgetNotificationService";
        } else {
            return TEST_CONTAINER.getBaseURL() + "WidgetNotificationService";
        }
    }

    private String getConsumerFactoryServiceAddress() {
        if (consumerFactoryServiceAddress != null) {
            return consumerFactoryServiceAddress;
        }
        if (TEST_CONTAINER == null) {
            return "http://127.0.0.1:8080/wsrf/services/NotificationConsumerFactoryService";
        } else {
            return TEST_CONTAINER.getBaseURL() + "NotificationConsumerFactoryService";
        }
    }

    private void destroySubscription(EndpointReferenceType subscriptionEPR)
        throws ServiceException, RemoteException
    {
        SubscriptionManager manager =
            this.subscriptionManagerLocator.getSubscriptionManagerPort(
                subscriptionEPR);
        manager.destroy(new Destroy());
    }


    private EndpointReferenceType createResource()
        throws Exception {
        URL endpoint = new URL(getServiceAddress());
        WidgetPortType port = locator.getWidgetPortTypePort(endpoint);

        CreateWidgetResponse response =
            port.createWidget(new CreateWidget());

        return response.getEndpointReference();
    }

    // create
    public void testScenario1() throws Exception {
        URL endpoint = new URL(getServiceAddress());
        EndpointReferenceType rs = createResource();
        assertTrue(rs != null);
        assertTrue(rs.getAddress() != null);
        String address = rs.getAddress().toString();
        assertTrue(address.endsWith(endpoint.getPath()));
        ReferencePropertiesType props = rs.getProperties();
        assertTrue(props.get_any() != null);
        assertTrue(props.get_any().length > 0);
    }

    // destroy
    public void testScenario2() throws Exception {
        EndpointReferenceType endpoint = createResource();

        WidgetPortType port = locator.getWidgetPortTypePort(endpoint);

        port.destroy(new Destroy());
    }

    // set termination time
    public void testScenario3() throws Exception {
        EndpointReferenceType endpoint = createResource();

        WidgetPortType port = locator.getWidgetPortTypePort(endpoint);

        Calendar termTime = Calendar.getInstance();
        termTime.add(Calendar.SECOND, 30);

        SetTerminationTime request = new SetTerminationTime();
        request.setRequestedTerminationTime(termTime);

        SetTerminationTimeResponse response =
            port.setTerminationTime(request);

        Calendar newTermTime = response.getNewTerminationTime();

        System.out.println("requested: " + termTime.getTime());
        System.out.println("scheduled: " + newTermTime.getTime());

        assertTrue(newTermTime.getTime().equals(termTime.getTime()) ||
                   newTermTime.getTime().after(termTime.getTime()));
    }


    // get resource property
    public void testScenario4() throws Exception {
        EndpointReferenceType endpoint = createResource();

        WidgetPortType port = locator.getWidgetPortTypePort(endpoint);

        Calendar termTime = Calendar.getInstance();
        termTime.add(Calendar.SECOND, 30);

        SetTerminationTime request = new SetTerminationTime();
        request.setRequestedTerminationTime(termTime);

        SetTerminationTimeResponse response =
            port.setTerminationTime(request);

        Calendar newTermTime = response.getNewTerminationTime();

        GetResourcePropertyResponse propResponse =
            port.getResourceProperty(WSRFConstants.TERMINATION_TIME);

        MessageElement [] any = propResponse.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);

        Object obj = ObjectDeserializer.toObject(any[0], Calendar.class);
        assertTrue(obj instanceof Calendar);
        assertEquals(newTermTime.getTime(), ((Calendar)obj).getTime());
    }

    // get multiple resource property
    public void testScenario5() throws Exception {
        EndpointReferenceType endpoint = createResource();

        WidgetPortType port = locator.getWidgetPortTypePort(endpoint);

        Calendar termTime = Calendar.getInstance();
        termTime.add(Calendar.SECOND, 30);

        SetTerminationTime request = new SetTerminationTime();
        request.setRequestedTerminationTime(termTime);

        SetTerminationTimeResponse response =
            port.setTerminationTime(request);

        Calendar newTermTime = response.getNewTerminationTime();

        QName [] propNames =
            new QName[] {WSRFConstants.TERMINATION_TIME,
                         WSRFConstants.CURRENT_TIME};

        GetMultipleResourceProperties_Element requestProp =
            new GetMultipleResourceProperties_Element();
        requestProp.setResourceProperty(propNames);

        GetMultipleResourcePropertiesResponse responseProp =
            port.getMultipleResourceProperties(requestProp);

        MessageElement [] any = responseProp.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 1);

        // TODO: need some function to figure out what's on the wire?

        Object obj1 = ObjectDeserializer.toObject(any[0], Calendar.class);
        assertTrue(obj1 instanceof Calendar);
        assertEquals(newTermTime.getTime(), ((Calendar)obj1).getTime());

        Object obj2 = ObjectDeserializer.toObject(any[1], Calendar.class);
        assertTrue(obj2 instanceof Calendar);
    }

    // query resource property
    public void testScenario6() throws Exception {
        EndpointReferenceType endpoint = createResource();

        WidgetPortType port = locator.getWidgetPortTypePort(endpoint);

        // TODO: this can be put in a helper function

        String dialect = WSRFConstants.XPATH_1_DIALECT;

        QueryExpressionType query = new QueryExpressionType();
        query.setDialect(dialect);
        query.setValue(queryString);

        QueryResourceProperties_Element request
            = new QueryResourceProperties_Element();
        request.setQueryExpression(query);

        QueryResourcePropertiesResponse response =
            port.queryResourceProperties(request);

        MessageElement [] any = response.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);
        assertEquals(Node.TEXT_NODE, any[0].getNodeType());
        assertEquals("true", any[0].getNodeValue());
    }

    // set resource property
    public void testScenario7() throws Exception {
        EndpointReferenceType endpoint = createResource();

        WidgetPortType port = locator.getWidgetPortTypePort(endpoint);

        scenario7Case1(port);
        scenario7Case2(port);
        scenario7Case3(port);
        scenario7Case4(port);
    }

    private MessageElement[] getFooProperty(WidgetPortType port)
       throws Exception {
        GetResourcePropertyResponse propResponse =
            port.getResourceProperty(Widget.FOO);
        MessageElement [] any = propResponse.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);
        return any;
    }

    private void scenario7Case1(WidgetPortType port) throws Exception {
        GetResourcePropertyResponse propResponse =
            port.getResourceProperty(Widget.FOO);
        MessageElement [] any = propResponse.get_any();
        assertTrue(any == null);
    }

    private void scenario7Case2(WidgetPortType port) throws Exception {
        String value1 = "string1";

        InsertType insert = new InsertType();
        MessageElement [] elements = new MessageElement[1];
        elements[0] =
            (MessageElement)ObjectSerializer.toSOAPElement(value1, Widget.FOO);
        insert.set_any(elements);

        SetResourceProperties_Element request = 
            new SetResourceProperties_Element();
        request.setInsert(insert);

        port.setResourceProperties(request);

        MessageElement [] any = getFooProperty(port);
        Object obj2 = ObjectDeserializer.toObject(any[0], String.class);
        assertEquals(value1, obj2.toString().trim());
    }

    private void scenario7Case3(WidgetPortType port) throws Exception {
        String value1 = "string2";
        String value2 = "string3";

        UpdateType update = new UpdateType();
        MessageElement [] elements = new MessageElement[2];
        elements[0] =
            (MessageElement)ObjectSerializer.toSOAPElement(value1, Widget.FOO);
        elements[1] =
            (MessageElement)ObjectSerializer.toSOAPElement(value2, Widget.FOO);
        update.set_any(elements);

        SetResourceProperties_Element request = 
            new SetResourceProperties_Element();
        request.setUpdate(update);

        port.setResourceProperties(request);

        // TODO: order not important?

        MessageElement [] any = getFooProperty(port);
        assertEquals(2, any.length);
        Object obj1 = ObjectDeserializer.toObject(any[0], String.class);
        assertEquals(value1, obj1.toString().trim());
        Object obj2 = ObjectDeserializer.toObject(any[1], String.class);
        assertEquals(value2, obj2.toString().trim());
    }

    private void scenario7Case4(WidgetPortType port) throws Exception {

        DeleteType delete = new DeleteType();
        delete.setResourceProperty(Widget.FOO);

        SetResourceProperties_Element request = 
            new SetResourceProperties_Element();
        request.setDelete(delete);

        port.setResourceProperties(request);

        GetResourcePropertyResponse propResponse =
            port.getResourceProperty(Widget.FOO);
        MessageElement [] any = propResponse.get_any();
        assertTrue(any == null);
    }

    // subscribe & send notify to service
    public void testScenario8() throws Exception {

        NotificationConsumerFactoryServiceAddressingLocator consumerFactoryLocator =
            new NotificationConsumerFactoryServiceAddressingLocator();

        EndpointReferenceType consumerEPR = new EndpointReferenceType();
        consumerEPR.setAddress(new Address(getConsumerFactoryServiceAddress()));
        NotificationConsumerFactory notificationConsumerFactoryPort =
            consumerFactoryLocator.getNotificationConsumerFactoryPort(consumerEPR);
        EndpointReferenceType consumer =
            notificationConsumerFactoryPort.createNotificationConsumer(
                            new CreateNotificationConsumer());
        EndpointReferenceType endpoint = new EndpointReferenceType(
            new URI(getNotificationServiceAddress()));
        WidgetNotificationPortType port =
            notificationLocator.getWidgetNotificationPortTypePort(
                endpoint);
        Subscribe request = new Subscribe();
        request.setUseNotify(Boolean.TRUE);
        request.setConsumerReference(consumer);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            WidgetNotificationService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        SubscribeResponse response = port.subscribe(request);
        port.generateNotification(new GenerateNotification());
        this.destroySubscription(response.getSubscriptionReference());
    }

    // subscribe & send notify to client
    public void testScenario8Client() throws Exception {
        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();
        consumer.startListening();
        EndpointReferenceType consumerEPR =
            consumer.createNotificationConsumer();

        EndpointReferenceType endpoint = new EndpointReferenceType(
            new URI(getNotificationServiceAddress()));
        WidgetNotificationPortType port =
            notificationLocator.getWidgetNotificationPortTypePort(
                endpoint);
        Subscribe request = new Subscribe();
        request.setUseNotify(Boolean.TRUE);
        request.setConsumerReference(consumerEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            WidgetNotificationService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        SubscribeResponse response = port.subscribe(request);
        port.generateNotification(new GenerateNotification());
        Thread.sleep(5000);
        this.destroySubscription(response.getSubscriptionReference());
    }

    // getCurrentMessage
    public void testScenario9GetCurrentMessage() throws Exception {
        EndpointReferenceType endpoint = new EndpointReferenceType(
            new URI(getNotificationServiceAddress()));
        WidgetNotificationPortType port =
            notificationLocator.getWidgetNotificationPortTypePort(
                endpoint);
        GetCurrentMessage request = new GetCurrentMessage();
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            WidgetNotificationService.TEST_TOPIC);
        request.setTopic(topicPath);
        GetCurrentMessageResponse response = port.getCurrentMessage(request);
        assertTrue(response.get_any()[0].getLocalName() == "TestNotification" &&
            response.get_any()[0].getNamespaceURI() == "http://widgets.com");
    }

    // pause/resume subscription

    public void testScenario9PauseResume() throws Exception {

        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();
        consumer.startListening();
        EndpointReferenceType consumerEPR =
            consumer.createNotificationConsumer();

        EndpointReferenceType endpoint = new EndpointReferenceType(
            new URI(getNotificationServiceAddress()));
        WidgetNotificationPortType port =
            notificationLocator.getWidgetNotificationPortTypePort(
                endpoint);
        Subscribe request = new Subscribe();
        request.setUseNotify(Boolean.TRUE);
        request.setConsumerReference(consumerEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            WidgetNotificationService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        SubscribeResponse response = port.subscribe(request);
        port.generateNotification(new GenerateNotification());
        Thread.sleep(2000);
        SubscriptionManager subscriptionManager = this.
            subscriptionManagerLocator.getSubscriptionManagerPort(
                response.getSubscriptionReference());
        subscriptionManager.pauseSubscription(new PauseSubscription());
        port.generateNotification(new GenerateNotification());
        subscriptionManager.resumeSubscription(new ResumeSubscription());
        port.generateNotification(new GenerateNotification());
        Thread.sleep(2000);
        subscriptionManager.destroy(new Destroy());
    }

}
