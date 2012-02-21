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

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;

import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;

import org.globus.axis.gsi.GSIConstants;
import org.globus.axis.util.Util;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.NotifyCallback;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.container.ServiceContainer;
import org.globus.wsrf.core.notification.service.SubscriptionManagerServiceAddressingLocator;

import java.util.HashMap;
import java.util.List;

import javax.xml.rpc.Stub;

import com.counter.perf.CounterPortType;
import com.counter.perf.CreateCounterResponse;
import com.counter.perf.CreateCounter;
import com.counter.perf.service.CounterServiceAddressingLocator;

public class ScalabilityTests {

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
    private int delay = 1000;
    private String serviceUrl =
        "http://localhost:8080/wsrf/services/PerformanceCounterService";
    private int scenario = PLAIN;

    public void setService(String service) {
        this.serviceUrl = service;
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
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
            testCreateResource();
            break;
        case 2:
            testSubscription();
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
    
    public void testCreateResource() throws Exception {
        
        CounterPortType port = 
            locator.getCounterPortTypePort(getCounterEndpoint());
        
        setProperties((Stub)port);

        for (int i = 0; i < this.iterations; i++) {
            CreateCounterResponse response =
                port.createCounter(new CreateCounter());
            if (i % 100 == 0) {
                System.out.println("Resources created: " + i);
                Thread.sleep(this.delay);
            }
        }

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

    public void testSubscription() throws Exception {

        EndpointReferenceType epr = createResource();
        
        CounterPortType port = locator.getCounterPortTypePort(epr);
        
        setProperties((Stub)port);

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

        for (int i = 0; i < this.iterations; i++) {
            SubscribeResponse response = port.subscribe(request);
            if (i % 100 == 0) {
                Thread.sleep(this.delay);
                System.out.println("Subscriptions created: " + i);
            }
        }
    }

    private static class NotificationListener implements NotifyCallback {
        public synchronized void deliver(List topicPath,
                                         EndpointReferenceType producer,
                                         Object message) {
        }
    }

    public static void main(String[] args) 
        throws Exception {
        
        if (args.length < 2) {
            System.err.println("Usage: java ScalabilityTests scenarioNum testNum");
            System.exit(1);
        }
        
        int scenarioNum = Integer.parseInt(args[0]);
        int testNum = Integer.parseInt(args[1]);

        String base = (scenarioNum == TRANSPORT) ? 
            "https://localhost:8443" : "http://localhost:8080";
        String service = base + "/wsrf/services/PerformanceCounterService";

        int iterations = 1000;
        int delay = 1000;

        for (int i=2;i<args.length;i++) {
            if (args[i].equals("-service")) {
                service = args[++i];
            } else if (args[i].equals("-n")) {
                iterations = Integer.parseInt(args[++i]);
            } else if (args[i].equals("-d")) {
                delay = 1000 * Integer.parseInt(args[++i]);
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

        ScalabilityTests tests = new ScalabilityTests();
        tests.setIterations(iterations);
        tests.setService(service);
        tests.setScenario(scenarioNum);
        tests.setDelay(delay);

        tests.run(testNum);
    }

}
