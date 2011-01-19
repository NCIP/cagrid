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

import com.counter.CounterPortType;
import com.counter.service.CounterServiceAddressingLocator;

import org.globus.wsrf.client.BaseClient;
import org.globus.wsrf.utils.FaultHelper;
import org.globus.axis.util.Util;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;

import javax.xml.rpc.Stub;

public class Add extends BaseClient {

    static {
        Util.registerTransport();
    }

    public static void main(String[] args) {
        Add client = new Add();
        client.setCustomUsage("value");

        int value = 0;

        try {
            CommandLine line = client.parse(args);
            
            List options = line.getArgList();
            if (options == null || options.isEmpty()) {
                throw new ParseException("Expected value argument");
            }
            value = Integer.parseInt((String)options.get(0));
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
            System.out.println(port.add(value));
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
