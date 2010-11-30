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

import org.apache.axis.message.addressing.EndpointReferenceType;

import com.counter.CounterPortType;
import com.counter.CreateCounterResponse;
import com.counter.CreateCounter;
import com.counter.service.CounterServiceAddressingLocator;

import javax.xml.rpc.Stub;

import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.utils.FaultHelper;
import org.globus.axis.util.Util;

import javax.xml.namespace.QName;

import org.globus.wsrf.client.BaseClient;

public class Create extends BaseClient {

    private static final QName NAME =
        new QName("http://counter.com", "CounterReference");

    static {
        Util.registerTransport();
    }

    public static void main(String[] args) {

        Create client = new Create();

        try {
            client.parse(args);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(COMMAND_LINE_ERROR);
        }

        CounterServiceAddressingLocator locator =
            new CounterServiceAddressingLocator();

        try {
            CounterPortType port = 
                locator.getCounterPortTypePort(client.getEPR());
            client.setOptions((Stub)port);

            CreateCounterResponse createResponse =
                port.createCounter(new CreateCounter());

            EndpointReferenceType epr =
                createResponse.getEndpointReference();

            System.out.println(ObjectSerializer.toString(epr, NAME));
            System.out.println();
        } catch(Exception e) {
            if (client.isDebugMode()) {
                FaultHelper.printStackTrace(e);
            } else {
                System.err.println("Error: " + FaultHelper.getMessage(e));
            }
            System.exit(APPLICATION_ERROR);
        }
    }

}
