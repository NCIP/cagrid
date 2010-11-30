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

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.wsrf.core.notification.SubscriptionManager;
import org.globus.wsrf.core.notification.service.SubscriptionManagerServiceAddressingLocator;
import org.globus.wsrf.impl.security.TestConstants;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.descriptor.ClientSecurityDescriptor;
import org.globus.wsrf.test.GridTestCase;
import org.globus.wsrf.tests.notification.NotificationTestPortType;
import org.globus.wsrf.tests.notification.service.NotificationTestServiceAddressingLocator;
import org.globus.wsrf.utils.AddressingUtils;
import org.oasis.wsrf.lifetime.Destroy;

public class NotificationTestCase extends GridTestCase
{
    protected EndpointReferenceType testServiceEPR = null;
    protected EndpointReferenceType testServiceEPR2 = null;
    protected EndpointReferenceType badEPR = null;
    protected NotificationTestServiceAddressingLocator notificationTestLocator =
        new NotificationTestServiceAddressingLocator();
    protected NotificationTestPortType port = null;
    protected NotificationTestPortType port2 = null;
    protected NotificationTestPortType badPort = null;
    protected SubscriptionManagerServiceAddressingLocator managerLocator =
        new SubscriptionManagerServiceAddressingLocator();

    public NotificationTestCase(String name)
    {
        super(name);
    }

    protected void setAddress() throws Exception
    {
        String notificationTestServiceAddress =
            TEST_CONTAINER.getBaseURL() +
            NotificationTestService.SERVICE_PATH;
        this.testServiceEPR = AddressingUtils.createEndpointReference(
            notificationTestServiceAddress,
            NotificationTestHome.GOOD_KEY
        );

        this.testServiceEPR2 = AddressingUtils.createEndpointReference(
            notificationTestServiceAddress,
            NotificationTestHome.GOOD_KEY_2
        );

        this.badEPR = AddressingUtils.createEndpointReference(
            notificationTestServiceAddress,
            NotificationTestHome.BAD_KEY
        );
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        setAddress();
        this.port =
            notificationTestLocator.getNotificationTestPortTypePort(
                this.testServiceEPR);
        this.port2 =
            notificationTestLocator.getNotificationTestPortTypePort(
                this.testServiceEPR2);
        this.badPort =
            notificationTestLocator.getNotificationTestPortTypePort(
                this.badEPR);
    }

    protected void destroySubscription(
        EndpointReferenceType subscriptionEPR, ClientSecurityDescriptor desc)
        throws ServiceException, RemoteException
    {
        SubscriptionManager manager =
            this.managerLocator.getSubscriptionManagerPort(subscriptionEPR);
        if(desc != null)
        {
            ((Stub) manager)._setProperty(Constants.CLIENT_DESCRIPTOR, desc);
        }
        manager.destroy(new Destroy());
    }

    protected void destroySubscription(EndpointReferenceType subscriptionEPR)
        throws ServiceException, RemoteException
    {
        destroySubscription(subscriptionEPR, (ClientSecurityDescriptor) null);
    }

    protected void destroySubscription(EndpointReferenceType subscriptionEPR,
                                       String msgType)
        throws ServiceException, RemoteException
    {
        ClientSecurityDescriptor desc = new ClientSecurityDescriptor();
        desc.setAuthz(TestConstants.getConfiguredClientAuthz());
        if(msgType.equals(Constants.GSI_SEC_CONV))
        {
            desc.setGSISecureConv(Constants.SIGNATURE);
        }
        else if(msgType.equals(Constants.GSI_SEC_MSG))
        {
            desc.setGSISecureMsg(Constants.SIGNATURE);
        }

        destroySubscription(subscriptionEPR, desc);
    }

}
