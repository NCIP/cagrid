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

import java.net.URL;

import org.apache.axis.message.addressing.EndpointReferenceType;

import com.widgets.WidgetPortType;
import com.widgets.CreateWidget;
import com.widgets.CreateWidgetResponse;
import com.widgets.service.WidgetServiceAddressingLocator;

import org.globus.wsrf.encoding.ObjectSerializer;

import javax.xml.namespace.QName;

public class Create {

    private static final QName NAME = 
        new QName("http://widgets.com", "WidgetReference");

    public static void main(String [] args) {

        WidgetServiceAddressingLocator locator =
            new WidgetServiceAddressingLocator();

        String service = null;
        if (args.length == 0) {
            service = "http://127.0.0.1:8080/wsrf/services/WidgetService";
        } else {
            service = args[0];
        }

        try {
            URL endpoint = new URL(service);
            WidgetPortType port = locator.getWidgetPortTypePort(endpoint);

            CreateWidgetResponse response =
                port.createWidget(new CreateWidget());
            
            EndpointReferenceType epr = 
                response.getEndpointReference();

            System.out.println(ObjectSerializer.toString(epr, NAME));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
