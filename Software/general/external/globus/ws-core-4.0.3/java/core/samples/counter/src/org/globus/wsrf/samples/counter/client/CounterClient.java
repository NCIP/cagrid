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
package org.globus.wsrf.samples.counter.client;

import java.util.List;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

import javax.xml.rpc.Stub;

import org.apache.axis.message.addressing.EndpointReferenceType;

import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.Subscribe;
import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.NotifyCallback;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.core.notification.ResourcePropertyValueChangeNotificationElementType;
import org.globus.wsrf.core.notification.SubscriptionManager;
import org.globus.wsrf.core.notification.service.SubscriptionManagerServiceAddressingLocator;
import org.globus.wsrf.utils.FaultHelper;
import org.globus.axis.util.Util;

import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authorization.Authorization;
import org.globus.wsrf.impl.security.authorization.IdentityAuthorization;
import org.globus.wsrf.impl.security.authorization.SelfAuthorization;
import org.globus.wsrf.impl.security.descriptor.GSISecureConvAuthMethod;
import org.globus.wsrf.impl.security.descriptor.GSISecureMsgAuthMethod;
import org.globus.wsrf.impl.security.descriptor.GSITransportAuthMethod;
import org.globus.wsrf.impl.security.descriptor.ResourceSecurityDescriptor;
import org.globus.wsrf.samples.counter.Counter;

import org.globus.wsrf.client.BaseClient;

import org.globus.security.gridmap.GridMap;

import com.counter.CounterPortType;
import com.counter.CreateCounter;
import com.counter.CreateCounterResponse;
import com.counter.service.CounterServiceAddressingLocator;

import org.globus.wsrf.container.ServiceContainer;

public class CounterClient extends BaseClient implements NotifyCallback {

    static {
        Util.registerTransport();
    }

    public static void main(String[] args) {

        CounterClient client = new CounterClient();

        try {
            client.parse(args);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(COMMAND_LINE_ERROR);
        }

        CounterServiceAddressingLocator locator =
            new CounterServiceAddressingLocator();

        NotificationConsumerManager consumer = null;

        try {

            // Create counter resource
            CounterPortType port = 
                locator.getCounterPortTypePort(client.getEPR());
            client.setOptions((Stub)port);
            CreateCounterResponse createResponse =
                port.createCounter(new CreateCounter());

            // Process EPR
            EndpointReferenceType counterEPR = 
                createResponse.getEndpointReference();
            counterEPR.getProperties().get_any()[0].getValue();

            // Counter resource reference
            CounterPortType counterPort =
                locator.getCounterPortTypePort(counterEPR);
            client.setOptions((Stub)counterPort);

            // Create client side notification consumer
            
            String scheme = client.getEPR().getAddress().getScheme();
            if (scheme.equals("https")) {
                Map properties = new HashMap();
                properties
                    .put(ServiceContainer.CLASS,
                         "org.globus.wsrf.container.GSIServiceContainer");
                consumer = NotificationConsumerManager.getInstance(properties);
            } else {
                consumer = NotificationConsumerManager.getInstance();
            }
            consumer.startListening();

            EndpointReferenceType consumerEPR = null;
            Authorization authorization = 
                (Authorization)((Stub)counterPort)
                ._getProperty(Constants.AUTHORIZATION);
            if (authorization != null) {
                ResourceSecurityDescriptor resDesc =
                    new ResourceSecurityDescriptor();
                String authz = Authorization.AUTHZ_NONE;
                if (authorization instanceof SelfAuthorization) {
                    authz = Authorization.AUTHZ_SELF;
                }
                else if (authorization instanceof IdentityAuthorization) {
                    GridMap gridMap = new GridMap();
                    gridMap.map(
                        ( (IdentityAuthorization) authorization).getIdentity(),
                        "temp");
                    resDesc.setGridMap(gridMap);
                    authz = Authorization.AUTHZ_GRIDMAP;
                }
                resDesc.setAuthz(authz);

                Vector authMethod = new Vector();
                authMethod.add(GSISecureMsgAuthMethod.BOTH);
                authMethod.add(GSISecureConvAuthMethod.BOTH);
                authMethod.add(GSITransportAuthMethod.BOTH);
                resDesc.setAuthMethods(authMethod, true);

                consumerEPR =
                    consumer.createNotificationConsumer(client, resDesc);
            } else {
                consumerEPR =
                    consumer.createNotificationConsumer(client);
            }

            // Subscribe request
            Subscribe request = new Subscribe();
            request.setUseNotify(Boolean.TRUE);
            request.setConsumerReference(consumerEPR);
            TopicExpressionType topicExpression = new TopicExpressionType();
            topicExpression.setDialect(WSNConstants.SIMPLE_TOPIC_DIALECT);
            topicExpression.setValue(Counter.VALUE);
            request.setTopicExpression(topicExpression);

            EndpointReferenceType subscriptionEPR =
                counterPort.subscribe(request).getSubscriptionReference();
            SubscriptionManagerServiceAddressingLocator 
                subscriptionManagerLocator =
                new SubscriptionManagerServiceAddressingLocator();
            SubscriptionManager subscriptionPort =
                subscriptionManagerLocator.getSubscriptionManagerPort(
                    subscriptionEPR);

            // Add
            CounterPortType addPort = 
                locator.getCounterPortTypePort(counterEPR);
            client.setOptions((Stub)addPort);
            // Add 3
            addPort.add(3);

            synchronized (client) {
                client.wait(1000 * 30);
                if (!client.called) {
                    System.err.println("Did not receive notification in time!");
                }
            }

            // Get the value RP
            GetResourcePropertyResponse getRPResponse =
                counterPort.getResourceProperty(Counter.VALUE);
            System.out.println("Counter has value: " +
                               getRPResponse.get_any()[0].getValue());
            // same security setting
            // some line
            addPort.add(10);

            // Destroy the subscription
            client.setOptions((Stub)subscriptionPort);
            subscriptionPort.destroy(new Destroy());

            CounterPortType destroyPort =
                locator.getCounterPortTypePort(counterEPR);
            client.setOptions((Stub)destroyPort);
            // Destroy the counter resource
            destroyPort.destroy(new Destroy());
        } catch(Exception e) {
            if (client.isDebugMode()) {
                FaultHelper.printStackTrace(e);
            } else {
                System.err.println("Error: " + FaultHelper.getMessage(e));
            }
            System.exit(APPLICATION_ERROR);
        } finally {
            if (consumer != null) {
                try { consumer.stopListening(); } catch (Exception ee) {}
            }
        }

    }

    boolean called = false;

    // Notification callback
    public void deliver(List topicPath,
                        EndpointReferenceType producer,
                        Object message) {
        ResourcePropertyValueChangeNotificationType changeMessage =
            ((ResourcePropertyValueChangeNotificationElementType) message).
            getResourcePropertyValueChangeNotification();

        if(changeMessage != null) {
            System.out.println("Got notification with value: " +
                               changeMessage.getNewValue().get_any()[0].getValue());
        }
        synchronized (this) {
            called = true;
            notify();
        }
    }

}
