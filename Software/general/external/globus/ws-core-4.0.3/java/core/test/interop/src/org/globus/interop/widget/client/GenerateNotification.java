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
package org.globus.interop.widget.client;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;

import com.widgets.WidgetNotificationPortType;
import com.widgets.service.WidgetNotificationServiceAddressingLocator;

public class GenerateNotification
{
    private static WidgetNotificationServiceAddressingLocator locator =
        new WidgetNotificationServiceAddressingLocator();

    private static String serviceAddress =
        "http://127.0.0.1:8080/wsrf/services/WidgetNotificationService";

    public static void main(String [] args) {
        if (args.length > 0) {
            serviceAddress = args[0];
        }
        try {
            EndpointReferenceType endpoint = new EndpointReferenceType();
            endpoint.setAddress(new Address(serviceAddress));
            WidgetNotificationPortType port =
                locator.getWidgetNotificationPortTypePort(endpoint);
            port.generateNotification(new com.widgets.GenerateNotification());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
