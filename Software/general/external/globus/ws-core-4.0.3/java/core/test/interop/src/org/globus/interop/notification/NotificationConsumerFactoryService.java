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
package org.globus.interop.notification;

import java.rmi.RemoteException;

import org.apache.axis.message.addressing.EndpointReferenceType;

import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.core.tests.interop.CreateNotificationConsumer;

public class NotificationConsumerFactoryService
{

    public EndpointReferenceType createNotificationConsumer(
        CreateNotificationConsumer arg0) throws RemoteException
    {
        EndpointReferenceType consumerReference = null;
        NotificationConsumerManager manager =
            NotificationConsumerManager.getInstance();

        try
        {
            manager.startListening();
            consumerReference =
                manager.createNotificationConsumer(new NotificationCallback());
        }
        catch(Exception e)
        {
            throw new RemoteException("", e);
        }

        return consumerReference;
    }

}
