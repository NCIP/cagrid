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
package org.globus.wsrf.perf.counter;

import org.apache.axis.message.MessageElement;
import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;

import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsrf.properties.UpdateType;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.SetResourceProperties_Element;
import org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType;
import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;

import org.globus.axis.gsi.GSIConstants;
import org.globus.axis.util.Util;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.NotifyCallback;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.container.ServiceContainer;
import org.globus.wsrf.core.notification.ResourcePropertyValueChangeNotificationElementType;
import org.globus.wsrf.core.notification.SubscriptionManager;
import org.globus.wsrf.core.notification.service.SubscriptionManagerServiceAddressingLocator;

import java.util.HashMap;
import java.util.List;

import javax.xml.rpc.Stub;

import com.counter.perf.CounterPortType;
import com.counter.perf.CreateCounterResponse;
import com.counter.perf.CreateCounter;
import com.counter.perf.service.CounterServiceAddressingLocator;

public class PerformanceTests {

    static {
        Util.registerTransport();
    }

    public static final int PLAIN = 1;
    public static final int TRANSPORT = 2;
    public static final int MESSAGE = 3;

    private static final CounterServiceAddressingLocator locator =
        new CounterServiceAddressingLocator();

    private static final SubscriptionManagerServiceAddressingLocator sLocator =
        new SubscriptionManagerServiceAddressingLocator();

    private int iterations = 1000;
    private String serviceUrl =
        "http://localhost:8080/wsrf/services/PerformanceCounterService";
    private int scenario = PLAIN;

    public void setService(String service) {
        this.serviceUrl = service;
    }

    public void setIterations(int num) {
        this.iterations = num;
    }

    public void setScenario(int scenario) 
        throws Exception {
        if (scenario == TRANSPORT ||
            scenario == MESSAGE || 
            scenario == PLAIN) {
            this.scenario = scenario;
        } else {
            throw new Exception("Invalid scenario: " + scenario);
        }
    }

    private void setProperties(Stub stub) {
        if (this.scenario == TRANSPORT) {
            // set authorization to none
            stub._setProperty(GSIConstants.GSI_AUTHORIZATION,
                              org.globus.gsi.gssapi.auth.NoAuthorization.getInstance());
        } else if (this.scenario == MESSAGE) {
            // set authorization to none
            stub._setProperty(Constants.AUTHORIZATION, 
                              org.globus.wsrf.impl.security.authorization.NoAuthorization.getInstance());
            
            // enable WS-Security Signature
            stub._setProperty(Constants.GSI_SEC_MSG,
                              Constants.SIGNATURE);
        }
    }

    public void run(int testNum) throws Exception {
        System.out.println("Service    : " + this.serviceUrl);
        System.out.println("Scenario   : " + this.scenario);
        System.out.println("Test       : " + testNum);
        System.out.println("Iterations : " + this.iterations);
        System.out.println();
        
        switch(testNum) {
        case 1:
            testGetResourceProperty();
            break;
        case 2:
            testSetResourceProperty();
            break;
        case 3:
            testCreateResource();
            break;
        case 4:
            testDestroyResource();
            break;
        case 5:
            testNotification();
            break;
        default:
            throw new Exception("Invalid test number: " + testNum);
        }
    }

    private EndpointReferenceType getCounterEndpoint() 
        throws Exception {
        EndpointReferenceType epr = new EndpointReferenceType();
        epr.setAddress(new Address(this.serviceUrl));
        return epr;
    }

    private EndpointReferenceType createResource()
        throws Exception {
        CounterPortType port = 
            locator.getCounterPortTypePort(getCounterEndpoint());
        
        setProperties((Stub)port);

        CreateCounterResponse response =
            port.createCounter(new CreateCounter());
        
        return response.getEndpointReference();
    }

    private SetResourceProperties_Element createSetRPRequest(int value) 
        throws Exception {
        UpdateType update = new UpdateType();
        MessageElement [] elements = new MessageElement[1];
        elements[0] =
            (MessageElement)ObjectSerializer.toSOAPElement(new Integer(value),
                                                           Counter.VALUE);
        update.set_any(elements);
        
        SetResourceProperties_Element request = 
            new SetResourceProperties_Element();
        request.setUpdate(update);
        
        return request;
    }

    private static int getValue(GetResourcePropertyResponse response)
        throws Exception {
        return getValue(response.get_any());
    }

    private static int getValue(MessageElement[] any) 
        throws Exception {
        Integer value = 
            (Integer)ObjectDeserializer.toObject(any[0], Integer.class);
        return value.intValue();
    }

    public void testGetResourceProperty() throws Exception {
        EndpointReferenceType epr = createResource();

        CounterPortType port = locator.getCounterPortTypePort(epr);

        setProperties((Stub)port);

        // perform set request
        port.setResourceProperties(createSetRPRequest(100));
        
        long endValue = 0;
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < this.iterations; i++) {
            GetResourcePropertyResponse response =
                port.getResourceProperty(Counter.VALUE);

            endValue += getValue(response);
        }
        long stop = System.currentTimeMillis();

        System.out.println("GetResourceProperty time: " + 
                           ((double)(stop-start)/(double)iterations));
        
        // self verify
        long expected = this.iterations * 100;
        long actual = endValue;
        if (expected != actual) {
            throw new Exception("Self verify failed: " +
                                expected + " " + actual);
        }

        // clean up
        port.destroy(new Destroy());
    }

    public void testSetResourceProperty() throws Exception {
        EndpointReferenceType epr = createResource();
        
        CounterPortType port = locator.getCounterPortTypePort(epr);
        
        setProperties((Stub)port);

        // perform set request
        port.setResourceProperties(createSetRPRequest(100));
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < this.iterations; i++) {
            // perform set request
            port.setResourceProperties(createSetRPRequest(i));
        }
        long stop = System.currentTimeMillis();

        System.out.println("SetResourceProperty time: " + 
                           ((double)(stop-start)/(double)iterations));

        // self verify
        GetResourcePropertyResponse response =
            port.getResourceProperty(Counter.VALUE);

        long expected = this.iterations-1;
        long actual = getValue(response);
        if (expected != actual) {
            throw new Exception("Self verify failed: " + 
                                expected + " " + actual);
        }
        
        // clean up
        port.destroy(new Destroy());
    }
    
    public void testCreateResource() throws Exception {
        // This will just init the service
        EndpointReferenceType epr = createResource();
        
        CounterPortType port = 
            locator.getCounterPortTypePort(getCounterEndpoint());
        
        setProperties((Stub)port);

        EndpointReferenceType eprs[] = 
            new EndpointReferenceType[this.iterations];
        
        long start = System.currentTimeMillis();
        for (int i = 0; i < this.iterations; i++) {
            CreateCounterResponse response =
                port.createCounter(new CreateCounter());
            eprs[i] = response.getEndpointReference();
        }
        long stop = System.currentTimeMillis();

        System.out.println("CreateResource time: " + 
                           ((double)(stop-start)/(double)iterations));

        // clean up
        port = locator.getCounterPortTypePort(epr);
        setProperties((Stub)port);
        port.destroy(new Destroy());
        for (int i = 0; i < eprs.length; i++) {
            port = locator.getCounterPortTypePort(eprs[i]);
            setProperties((Stub)port);
            port.destroy(new Destroy());
        }
    }

    public void testDestroyResource() throws Exception {
        EndpointReferenceType eprs[] = 
            new EndpointReferenceType[this.iterations];
        
        CounterPortType port = 
            locator.getCounterPortTypePort(getCounterEndpoint());

        setProperties((Stub)port);

        for (int i = 0; i < this.iterations; i++) {
            CreateCounterResponse response =
                port.createCounter(new CreateCounter());
            eprs[i] = response.getEndpointReference();
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < this.iterations; i++) {
            port = locator.getCounterPortTypePort(eprs[i]);
            setProperties((Stub)port);
            port.destroy(new Destroy());
        }
        long stop = System.currentTimeMillis();

        System.out.println("DestroyResource time: " + 
                           ((double)(stop-start)/(double)iterations));
    }
    
    private NotificationConsumerManager getNotificationConsumer() {
        if (this.scenario == TRANSPORT) {
            HashMap properties = new HashMap();
            properties.put(ServiceContainer.CLASS,
                           "org.globus.wsrf.container.GSIServiceContainer");
            return NotificationConsumerManager.getInstance(properties);
        } else {
            return NotificationConsumerManager.getInstance();
        }
    }

    public void testNotification() throws Exception {
        EndpointReferenceType epr = createResource();
        
        CounterPortType port = locator.getCounterPortTypePort(epr);
        
        setProperties((Stub)port);

        // perform set request
        port.setResourceProperties(createSetRPRequest(100));
        
        // startup notification consumer
        NotificationConsumerManager consumer = getNotificationConsumer();
        consumer.startListening();

        NotificationListener listener = new NotificationListener();
        
        EndpointReferenceType consumerEPR =
            consumer.createNotificationConsumer(listener);

        System.out.println("Consumer address: " + consumerEPR.getAddress());

        TopicExpressionType topicExpression = new TopicExpressionType();
        topicExpression.setDialect(WSNConstants.SIMPLE_TOPIC_DIALECT);
        topicExpression.setValue(Counter.VALUE);

        Subscribe request = new Subscribe();
        request.setUseNotify(Boolean.TRUE);
        request.setConsumerReference(consumerEPR);
        request.setTopicExpression(topicExpression);

        // subscribe
        SubscribeResponse response = port.subscribe(request);

        // fire 1 notification to warm things up
        port.setResourceProperties(createSetRPRequest(0));
        // wait for notification
        listener.waitForNotification();

        long actual = 0;
        long expected = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < this.iterations; i++) {
            listener.reset();
            // perform set request
            port.setResourceProperties(createSetRPRequest(i));
            // wait for notification
            actual += listener.waitForNotification();
            expected += i;
        }
        long stop = System.currentTimeMillis();

        System.out.println("Notification time: " + 
                           ((double)(stop-start)/(double)iterations));

        // self verify
        if (expected != actual) {
            throw new Exception("Self verify failed: " + 
                                expected + " " + actual);
        }

        // clean up

        // clean up consumer stuff on client
        consumer.removeNotificationConsumer(consumerEPR);
        consumer.stopListening();

        // remove subscription resource
        SubscriptionManager manager = sLocator.getSubscriptionManagerPort(
                        response.getSubscriptionReference());
        setProperties((Stub)manager);
        manager.destroy(new Destroy());

        // remove resource
        port.destroy(new Destroy());
    }

    private static class NotificationListener implements NotifyCallback {
        
        private int value = -1;
        private boolean delivered = false;

        public synchronized void reset() {
            this.value = -1;
            this.delivered = false;
        }

        public synchronized void deliver(List topicPath,
                                         EndpointReferenceType producer,
                                         Object message) {
            if (message instanceof ResourcePropertyValueChangeNotificationElementType) {
                ResourcePropertyValueChangeNotificationType changeMessage =
                    ((ResourcePropertyValueChangeNotificationElementType) message).
                    getResourcePropertyValueChangeNotification();

                try {
                    this.value = getValue(changeMessage.getNewValue().get_any());
                } catch (Exception e) {
                    System.err.println("Failed to deserializer the value: " + 
                                       e.getMessage());
                }
            } else {
                System.err.println("Unexpected message type: " + 
                                   message.getClass().getName());
            }
            this.delivered = true;
            notify();
        }
        
        public synchronized int waitForNotification() 
            throws InterruptedException {
            while (!this.delivered) {
                wait();
            }
            return this.value;
        }
        
    }

    public static void main(String[] args) 
        throws Exception {
        
        if (args.length < 2) {
            System.err.println("Usage: java PerformanceTests scenarioNum testNum");
            System.exit(1);
        }
        
        int scenarioNum = Integer.parseInt(args[0]);
        int testNum = Integer.parseInt(args[1]);

        String base = (scenarioNum == TRANSPORT) ? 
            "https://localhost:8443" : "http://localhost:8080";
        String service = base + "/wsrf/services/PerformanceCounterService";

        int iterations = 100;
        
        for (int i=2;i<args.length;i++) {
            if (args[i].equals("-service")) {
                service = args[++i];
            } else if (args[i].equals("-n")) {
                iterations = Integer.parseInt(args[++i]);
            }
        }

        if (service.startsWith("https")) {
            if (scenarioNum != TRANSPORT) {
                throw new Exception(
                       "https url must be used with transport scenario");
            }
        } else {
            if (scenarioNum == TRANSPORT) {
                throw new Exception(
                       "https url is required for transport scenario");
            }
        }

        PerformanceTests tests = new PerformanceTests();
        tests.setIterations(iterations);
        tests.setService(service);
        tests.setScenario(scenarioNum);

        tests.run(testNum);
    }

}
