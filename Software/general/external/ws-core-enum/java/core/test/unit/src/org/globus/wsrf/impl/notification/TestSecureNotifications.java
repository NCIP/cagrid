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

import java.util.Vector;

import javax.xml.rpc.Stub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI;

import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType;

import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.core.notification.ResourcePropertyValueChangeNotificationElementType;
import org.globus.wsrf.impl.security.SecurityTestResource;
import org.globus.wsrf.impl.security.TestConstants;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authorization.Authorization;
import org.globus.wsrf.impl.security.descriptor.ClientSecurityDescriptor;
import org.globus.wsrf.impl.security.descriptor.GSISecureConvAuthMethod;
import org.globus.wsrf.impl.security.descriptor.GSISecureMsgAuthMethod;
import org.globus.wsrf.impl.security.descriptor.ResourceSecurityDescriptor;
import org.globus.wsrf.test.GridTestSuite;
import org.globus.wsrf.tests.security.SecurityTestPortType;
import org.globus.wsrf.tests.security.CreateResourceResponse;
import org.globus.wsrf.tests.security.service.SecurityTestServiceAddressingLocator;

public class TestSecureNotifications extends NotificationTestCase {

    static Log logger =
        LogFactory.getLog(TestSecureNotifications.class.getName());
    Authorization authz = null;

    public TestSecureNotifications(String name) {
        super(name);
        authz = TestConstants.getConfiguredClientAuthz();
    }

    public void testSubscribeSecMsg() throws Exception {
        EndpointReferenceType res1EPR = createResource();
        EndpointReferenceType subscribeEPR =
            subscribeTest(res1EPR, Constants.GSI_SEC_MSG, 10);
        super.destroySubscription(subscribeEPR, Constants.GSI_SEC_MSG);
    }

    public void testSubscribeSecConv() throws Exception {
        EndpointReferenceType res1EPR = createResource();
        EndpointReferenceType subscribeEPR =
            subscribeTest(res1EPR, Constants.GSI_SEC_CONV, 20);
        super.destroySubscription(subscribeEPR, Constants.GSI_SEC_CONV);
    }

    public void testSubscribeResourceSecurity() throws Exception {
        EndpointReferenceType res1EPR = createResource();
        EndpointReferenceType subscribeEPR =
            subscribeTest(res1EPR, Constants.GSI_SEC_CONV, 20);
        String errorMessage = null;
        try
        {
            super.destroySubscription(subscribeEPR);
        }
        catch(Exception e)
        {
            logger.error("Exception ", e);
            errorMessage = e.getMessage();
        }
        assertEquals(
            "GSI Secure Message or GSI Secure Conversation " +
            "authentication required for " +
            "\"{http://wsrf.globus.org/core/notification}destroy\" operation.",
            errorMessage);
    }

    public void testNotificationSecConv() throws Exception {
        EndpointReferenceType res2EPR = createResource();
        multipleNotificationsTest(res2EPR, Constants.GSI_SEC_CONV);
    }

    public void testNotificationSecMsg() throws Exception {
        EndpointReferenceType res2EPR = createResource();
        multipleNotificationsTest(res2EPR, Constants.GSI_SEC_MSG);
    }

    private EndpointReferenceType createResource() throws Exception {

        String testServiceAddrs = TEST_CONTAINER.getBaseURL() +
                                  TestConstants.SECURITY_SERVICE_PATH;
        EndpointReferenceType testServiceEPR =
            new EndpointReferenceType(new URI(testServiceAddrs));

        // Create a resource
        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            locator.getSecurityTestPortTypePort(testServiceEPR);
        CreateResourceResponse response =
            testPort.createResource(false);
        EndpointReferenceType res1EPR = response.getEndpointReference();
        logger.debug("Created a resource");
        return res1EPR;
    }

    private EndpointReferenceType subscribeTest(
        EndpointReferenceType res1EPR, String msgType, int setVal)
        throws Exception {

        TestNotifyCallback callback = new TestNotifyCallback();
        int expectedCount = 1;
        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();
        consumer.startListening();

        ResourceSecurityDescriptor resDesc =
            new ResourceSecurityDescriptor();
	// FIXME
	//        resDesc.setAuthz("self");
        resDesc.setAuthz("none");
        Vector authMethod = new Vector();
        if (msgType.equals(Constants.GSI_SEC_CONV)) {
            authMethod.add(GSISecureConvAuthMethod.BOTH);
        } else if (msgType.equals(Constants.GSI_SEC_MSG)) {
            authMethod.add(GSISecureMsgAuthMethod.BOTH);
        }
        resDesc.setAuthMethods(authMethod, true);

        EndpointReferenceType consumerEPR =
            consumer.createNotificationConsumer(callback, resDesc);
        logger.debug("Created notification consumer");

        Subscribe request = new Subscribe();
        request.setConsumerReference(consumerEPR);
        TopicExpressionType topicExpression = new TopicExpressionType();
        topicExpression.setDialect(WSNConstants.SIMPLE_TOPIC_DIALECT);
        topicExpression.setValue(SecurityTestResource.VALUE);
        request.setTopicExpression(topicExpression);
        logger.debug("Created subscribe request");

        SecurityTestServiceAddressingLocator resLocator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            resLocator.getSecurityTestPortTypePort(res1EPR);
        ((Stub)testPort)._setProperty(msgType,
                                      Constants.SIGNATURE);
        ((Stub)testPort)._setProperty(Constants.AUTHORIZATION,
                                      authz);
        SubscribeResponse response = testPort.subscribe(request);
        logger.debug("Subscribe called");

        // set value which should trigger notification
        testPort.setValue(setVal);

        assertTrue(callback.waitForCount(1, GridTestSuite.timeout));
        assertEquals(expectedCount, callback.getNotifyCount());
        Object message = callback.getMessage();
        ResourcePropertyValueChangeNotificationType changeMessage =
            ((ResourcePropertyValueChangeNotificationElementType) message).
            getResourcePropertyValueChangeNotification();

        assertTrue(changeMessage != null);
        String val = changeMessage.getNewValue().get_any()[0].getValue();
        logger.debug("Value is " + val);
        assertTrue(Integer.toString(setVal).equals(val));
        return response.getSubscriptionReference();
    }

    public void multipleNotificationsTest(EndpointReferenceType res1EPR,
                                          String msgType) throws Exception {

        System.out.println("Msg type " + msgType);
        TestNotifyCallback callback = new TestNotifyCallback();
        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance();
        consumer.startListening();

        ResourceSecurityDescriptor resDesc =
            new ResourceSecurityDescriptor();
	// FIXME
	//        resDesc.setAuthz("self");
        resDesc.setAuthz("none");
        ClientSecurityDescriptor desc = new ClientSecurityDescriptor();
        desc.setAuthz(authz);
        Vector authMethod = new Vector();
        if (msgType.equals(Constants.GSI_SEC_CONV)) {
            authMethod.add(GSISecureConvAuthMethod.BOTH);
            desc.setGSISecureConv(Constants.SIGNATURE);
        } else if (msgType.equals(Constants.GSI_SEC_MSG)) {
            authMethod.add(GSISecureMsgAuthMethod.BOTH);
            desc.setGSISecureMsg(Constants.SIGNATURE);
        }
        resDesc.setAuthMethods(authMethod, true);
        EndpointReferenceType consumerEPR =
            consumer.createNotificationConsumer(callback, resDesc);

        Subscribe request = new Subscribe();
        request.setConsumerReference(consumerEPR);
        TopicExpressionType topicExpression = new TopicExpressionType();
        topicExpression.setDialect(WSNConstants.SIMPLE_TOPIC_DIALECT);
        topicExpression.setValue(SecurityTestResource.VALUE);
        request.setTopicExpression(topicExpression);

        SecurityTestServiceAddressingLocator resLocator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            resLocator.getSecurityTestPortTypePort(res1EPR);
        ((Stub)testPort)._setProperty(msgType,
                                      Constants.SIGNATURE);
        ((Stub)testPort)._setProperty(Constants.AUTHORIZATION, authz);
        SubscribeResponse response = testPort.subscribe(request);

        int numClients = 2;
        int numIters = 3;
        Sem sem = new Sem(numClients);
        for (int i=0; i<numClients; i++) {
            NotificationTestClient client =
                new NotificationTestClient(res1EPR, sem, numIters, msgType);
            client.start();
        }

        Exception ex = null;

        try {
            ex = sem.waitFor(GridTestSuite.timeout);
        } catch (Exception e) {
            throw e;
        }


        if (ex != null) {
            throw ex;
        }

        assertTrue(callback.waitForCount(numClients * numIters,
                                         GridTestSuite.timeout));

        // just to give the axis a chance to return soap responses
        Thread.sleep(5000);
        super.destroySubscription(response.getSubscriptionReference(), desc);
    }

    public class NotificationTestClient extends Thread {

        EndpointReferenceType epr;
        Sem sem;
        int numOfCalls;
        String msgSecType;

        public NotificationTestClient(EndpointReferenceType epr,
                                      Sem sem,
                                      int numOfCalls,
                                      String msgSecType) {
            this.epr = epr;
            this.sem = sem;
            this.numOfCalls = numOfCalls;
            this.msgSecType = msgSecType;
        }

        public void run() {
            Exception exception = null;

            try {
                SecurityTestServiceAddressingLocator resLocator =
                    new SecurityTestServiceAddressingLocator();
                SecurityTestPortType testPort =
                    resLocator.getSecurityTestPortTypePort(this.epr);

                ((Stub)testPort)._setProperty(this.msgSecType,
                                              Constants.SIGNATURE);
                ((Stub)testPort)._setProperty(Constants.AUTHORIZATION,
                                              authz);

                for (int i = 0; i < numOfCalls; i++) {
                    testPort.setValue(i);
                }
            } catch (Exception e) {
                exception = e;
                e.printStackTrace();
            } finally {
                sem.signal(exception);
            }
        }
    }

    class Sem {
        int clients;
        int totalClients;
        Exception exception;

        public Sem(int totalClients) {
            this.totalClients = totalClients;
            this.clients = 0;
        }

        public synchronized Exception waitFor(int timeout)
            throws Exception {
            while (totalClients != clients) {
                int old = clients;
                wait(timeout);

                if (old == clients) {
                    throw new Exception(
                        "Timeout. Received " + clients + "/" + totalClients +
                        " signals."
                    );
                }
            }

            return exception;
        }

        public synchronized void signal(Exception e) {
            if (exception == null) {
                exception = e;
            }

            clients++;
            notify();
        }
    }
}
