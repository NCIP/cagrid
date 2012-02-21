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
package org.globus.interop.notification.client;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;

import org.globus.interop.widget.WidgetNotificationService;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.core.notification.Consumer;
import org.globus.wsrf.core.notification.service.NotificationConsumerServiceAddressingLocator;
import org.globus.wsrf.core.tests.interop.NotificationConsumerFactory;
import org.globus.wsrf.core.tests.interop.CreateNotificationConsumer;
import org.globus.wsrf.core.tests.interop.service.NotificationConsumerFactoryServiceAddressingLocator;
import org.oasis.wsn.NotificationMessageHolderType;
import org.oasis.wsn.TopicExpressionType;

public class Notify
{
    public static void main(String [] args)
    {
        NotificationConsumerFactoryServiceAddressingLocator factoryLocator =
            new NotificationConsumerFactoryServiceAddressingLocator();

        NotificationConsumerServiceAddressingLocator consumerLocator =
            new NotificationConsumerServiceAddressingLocator();

        try
        {
            EndpointReferenceType endpoint = new EndpointReferenceType();
            endpoint.setAddress(new Address(args[0]));
            NotificationConsumerFactory factoryPort =
                factoryLocator.getNotificationConsumerFactoryPort(endpoint);
            EndpointReferenceType consumer =
                factoryPort.createNotificationConsumer(
                    new CreateNotificationConsumer());
            Consumer consumerPort =
                consumerLocator.getConsumerPort(consumer);

            org.oasis.wsn.Notify notification = new org.oasis.wsn.Notify();
            NotificationMessageHolderType[] message = {new NotificationMessageHolderType()};

            EndpointReferenceType producerEndpoint =
                new EndpointReferenceType();
            Address producerAddress = new Address("http://localhost/client");
            producerEndpoint.setAddress(producerAddress);

            TopicExpressionType topic = new TopicExpressionType(
                WSNConstants.SIMPLE_TOPIC_DIALECT,
                WidgetNotificationService.TEST_TOPIC);
            message[0].setProducerReference(producerEndpoint);
            message[0].setMessage("test");
            message[0].setTopic(topic);
            notification.setNotificationMessage(message);
            consumerPort.notify(notification);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
