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
package org.globus.wsrf.container;

import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.Stub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI;

import org.oasis.wsn.TopicExpressionType;
import org.oasis.wsn.Subscribe;
import org.oasis.wsn.SubscribeResponse;
import org.oasis.wsrf.lifetime.Destroy;

import org.globus.axis.gsi.GSIConstants;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.core.notification.SubscriptionManager;
import org.globus.wsrf.handlers.DIITests;
import org.globus.wsrf.impl.TestService;
import org.globus.wsrf.impl.notification.NotificationTestCase;
import org.globus.wsrf.impl.notification.NotificationTestService;
import org.globus.wsrf.impl.notification.NotifyThread;
import org.globus.wsrf.impl.notification.TestNotifyCallback;
import org.globus.wsrf.impl.security.TestConstants;
import org.globus.wsrf.impl.security.descriptor.ClientSecurityDescriptor;
import org.globus.wsrf.security.Constants;
import org.globus.wsrf.test.GridTestSuite;
import org.globus.wsrf.tests.basic.TestPortType;
import org.globus.wsrf.tests.basic.CreateResource;
import org.globus.wsrf.tests.basic.CreateResourceResponse;
import org.globus.wsrf.tests.basic.service.TestServiceAddressingLocator;
import org.globus.wsrf.tests.security.SecurityTestPortType;
import org.globus.wsrf.tests.security.SetAnonymousAuthz;
import org.globus.wsrf.tests.security.service.SecurityTestServiceAddressingLocator;
import org.globus.wsrf.impl.security.authorization.Authorization;
import org.globus.wsrf.impl.security.authorization.NoAuthorization;

public class GSIServiceContainerTest extends NotificationTestCase {

    static Log logger =
        LogFactory.getLog(GSIServiceContainerTest.class.getName());

    protected TestServiceAddressingLocator locator =
        new TestServiceAddressingLocator();

    Authorization authz = null;
    org.globus.gsi.gssapi.auth.Authorization gsiAuthz = null;

    public GSIServiceContainerTest(String name) {
        super(name);
        authz = TestConstants.getConfiguredClientAuthz();
        gsiAuthz = TestConstants.getConfiguredClientGSIAuthz();
    }

    public void testBasic() throws Exception {
        String address =
            TEST_CONTAINER.getBaseURL() + TestService.SERVICE_PATH;
        URI u = new URI(address);
        assertEquals("https", u.getScheme());
        EndpointReferenceType epr = new EndpointReferenceType(u);
        TestPortType port = locator.getTestPortTypePort(epr);
        ClientSecurityDescriptor secDesc = new ClientSecurityDescriptor();
        secDesc.setAuthz(authz);
        ((Stub) port)._setProperty(Constants.CLIENT_DESCRIPTOR, secDesc);
        CreateResourceResponse response =
            port.createResource(new CreateResource());
        assertEquals("https",
                     response.getEndpointReference().getAddress().getScheme());
        port = locator.getTestPortTypePort(response.getEndpointReference());
        ((Stub) port)._setProperty(Constants.CLIENT_DESCRIPTOR, secDesc);
        port.destroy(new Destroy());
    }

    public void testAnonymous() throws Exception {
        String address =
            TEST_CONTAINER.getBaseURL() + TestConstants.SECURITY_SERVICE_PATH;
        URI u = new URI(address);
        assertEquals("https", u.getScheme());
        EndpointReferenceType epr = new EndpointReferenceType(u);
        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType port = locator.getSecurityTestPortTypePort(epr);
        ClientSecurityDescriptor secDesc = new ClientSecurityDescriptor();
        // Setting to no authz, self will not work since its anon
        secDesc.setAuthz(new NoAuthorization());
        secDesc.setAnonymous();
        ((Stub) port)._setProperty(Constants.CLIENT_DESCRIPTOR, secDesc);
        org.globus.wsrf.tests.security.CreateResourceResponse response =
            port.createResource(true);
        assertEquals("https",
                     response.getEndpointReference().getAddress().getScheme());
        port = locator.getSecurityTestPortTypePort(
            response.getEndpointReference());
        ((Stub) port)._setProperty(Constants.CLIENT_DESCRIPTOR, secDesc);
        port.setAnonymousAuthz(new SetAnonymousAuthz());
        port.setValue(20);
    }

    public void testDII() throws Exception {
        String address =
            TEST_CONTAINER.getBaseURL() + TestService.SERVICE_PATH + "?wsdl";
        DIITests test = new DIITests("diiTest");
        Map props = new HashMap();
        ClientSecurityDescriptor secDesc = new ClientSecurityDescriptor();
        secDesc.setAuthz(authz);
        props.put(Constants.CLIENT_DESCRIPTOR, secDesc);
        test.diiTest(address, props);
    }

    public void testSecurityPolicy() throws Exception {

        assertTrue(TEST_CONTAINER != null);
        String testServiceAddrs = TEST_CONTAINER.getBaseURL() +
                                  TestConstants.SECURITY_SERVICE_PATH;
        EndpointReferenceType testServiceEPR =
            new EndpointReferenceType(new URI(testServiceAddrs));

        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            locator.getSecurityTestPortTypePort(testServiceEPR);
        ((Stub)testPort)._setProperty(Constants.GSI_TRANSPORT,
                                      Constants.SIGNATURE);
        ((Stub)testPort)._setProperty(GSIConstants.GSI_AUTHORIZATION,
                                      gsiAuthz);
        verifySecureAccess(testPort, true, true, false);

        testPort = locator.getSecurityTestPortTypePort(testServiceEPR);
        ((Stub)testPort)._setProperty(Constants.GSI_TRANSPORT,
                                      Constants.ENCRYPTION);
        ((Stub)testPort)._setProperty(GSIConstants.GSI_AUTHORIZATION,
                                      gsiAuthz);
        verifySecureAccess(testPort, true, false, true);

        SecurityTestServiceAddressingLocator locator1 =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort1 =
            locator1.getSecurityTestPortTypePort(testServiceEPR);
        ((Stub)testPort1)._setProperty(Constants.GSI_SEC_MSG,
                                       Constants.SIGNATURE);
        ((Stub)testPort1)._setProperty(Constants.AUTHORIZATION, authz);
        ((Stub)testPort1)._setProperty(GSIConstants.GSI_AUTHORIZATION,
                                       gsiAuthz);

        // Signaure is always set
        verifySecureAccess(testPort1, true, true, false);

    }

    private void verifySecureAccess(SecurityTestPortType testPort,
                                    boolean transportOnly,
                                    boolean transportIntegrity,
                                    boolean transportPrivacy)
        throws Exception {

        boolean exp = true;
        try {
            testPort.gsiTransportOnly(null);
        } catch (Exception e) {
            if ((e.getMessage().indexOf("authentication require")) != -1) {
                exp = false;
            } else {
                logger.error(e);
            }
        }
        assertTrue(exp == transportOnly);

        exp = true;
        try {
            testPort.gsiTransportIntegrity(null);
        } catch (Exception e) {
            if ((e.getMessage().indexOf("authentication require")) != -1) {
                exp = false;
            } else {
                logger.error(e);
            }
        }
        assertTrue(exp == transportIntegrity);

        exp = true;
        try {
            testPort.gsiTransportPrivacy(null);
        } catch (Exception e) {
            if ((e.getMessage().indexOf("authentication require")) != -1) {
                exp = false;
            } else {
                logger.error(e);
            }
        }
        assertTrue(exp == transportPrivacy);
    }

    public void testMultipleNotifications() throws Exception {
        TestNotifyCallback callback = new TestNotifyCallback();
        int expectedCount = 1;
        Map properties = new HashMap();
        ClientSecurityDescriptor secDesc = new ClientSecurityDescriptor();
        secDesc.setAuthz(authz);
        properties.put(ServiceContainer.CLASS,
                       "org.globus.wsrf.container.GSIServiceContainer");
        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance(properties);
        consumer.startListening();
        EndpointReferenceType consumerEPR =
            consumer.createNotificationConsumer(callback);
        assertEquals("https",
                     consumerEPR.getAddress().getScheme());
        Subscribe request = new Subscribe();
        request.setConsumerReference(consumerEPR);
        TopicExpressionType topicPath = new TopicExpressionType(
            WSNConstants.SIMPLE_TOPIC_DIALECT,
            NotificationTestService.TEST_TOPIC);
        request.setTopicExpression(topicPath);
        ((Stub) super.port)._setProperty(Constants.CLIENT_DESCRIPTOR, secDesc);
        SubscribeResponse response = super.port.subscribe(request);

        for(int i = 0; i < 10; i++) {
            super.port.generateNotification(NotificationTestService.TEST_TOPIC);
            assertTrue("timed out waiting for notification #" + expectedCount,
                       callback.waitForCount(expectedCount, GridTestSuite.timeout));
            assertEquals(expectedCount, callback.getNotifyCount());
            expectedCount++;
        }
        SubscriptionManager manager =
            this.managerLocator.getSubscriptionManagerPort(
                response.getSubscriptionReference());
        ((Stub) manager)._setProperty(Constants.CLIENT_DESCRIPTOR, secDesc);
        manager.destroy(new Destroy());
    }

    public void testMultithreadedNotifications() throws Throwable {

        Map properties = new HashMap();
        ClientSecurityDescriptor secDesc = new ClientSecurityDescriptor();
        secDesc.setAuthz(authz);
        properties.put(ServiceContainer.CLASS,
                       "org.globus.wsrf.container.GSIServiceContainer");
        NotificationConsumerManager consumer =
            NotificationConsumerManager.getInstance(properties);
        consumer.startListening();

        ((Stub) super.port)._setProperty(Constants.CLIENT_DESCRIPTOR, secDesc);

        Object lock = new Object();

        int size = 10;
        NotifyThread [] threads = new NotifyThread[size];
        for (int i=0;i<size;i++) {
            threads[i] = new NotifyThread(testServiceEPR, consumer,
                                          lock, secDesc);
            threads[i].start();
        }

        for (int i=0;i<size;i++) {
            while( !threads[i].isSubscribed() ) {
                Throwable e = threads[i].getException();
                if (e != null) {
                    throw e;
                }
                Thread.sleep(100);
            }
        }

        synchronized(lock) {
            lock.notifyAll();
        }

        super.port.generateNotification(NotificationTestService.TEST_TOPIC);

        Throwable e = null;

        for (int i=0;i<size;i++) {
            threads[i].join();
            if (threads[i].getException() != null) {
                threads[i].getException().printStackTrace();
                if (e == null) {
                    e = threads[i].getException();
                }
            }
        }

        if (e != null) {
            throw e;
        }
    }

}
