/*
 * Copyright 1999-2006 University of Chicago
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.globus.ws.enumeration.client;

import java.util.List;
import java.util.Properties;
import java.io.FileInputStream;

import org.globus.ws.enumeration.ClientEnumeration;
import org.globus.ws.enumeration.IterationConstraints;
import org.globus.ws.enumeration.IterationResult;

import org.xmlsoap.schemas.ws._2004._09.enumeration.service.EnumerationServiceAddressingLocator;
import org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;

import org.apache.axis.types.Duration;

import org.globus.wsrf.client.BaseClient;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.utils.FaultHelper;
import org.globus.axis.utils.DurationUtils;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

import org.xml.sax.InputSource;

import javax.xml.rpc.Stub;

public class Enumerate extends BaseClient {

    private static final Option FETCH = 
        OptionBuilder.withArgName( "number" )
        .hasArg()
        .withDescription("Total number of items to fetch. Can be 'all' to retrieve all the enumeration data")
        .withLongOpt("items")
        .create("i");

    private static final Option MAX_ELEMENTS = 
        OptionBuilder.withArgName( "number" )
        .hasArg()
        .withDescription("Maximum number of items to fetch at a time")
        .withLongOpt("maxElements")
        .create("n");

    private static final Option MAX_CHARACTERS = 
        OptionBuilder.withArgName( "number" )
        .hasArg()
        .withDescription("Maximum number of characters (in Unicode) of enumeration data that can be accepted at a time (Default no limit)")
        .withLongOpt("maxCharacters")
        .create("r");

    private static final Option MAX_TIME = 
        OptionBuilder.withArgName( "number" )
        .hasArg()
        .withDescription("Maximum amount of time (in milliseconds) in which the enumeration data must be assembled (Default not limit)")
        .withLongOpt("maxTime")
        .create("o");
    
    private static final String FOOTER =
        "Where:\n" +
        "  enumContextFile is a file containing the enumeration context\n";

    public Enumerate() {
        options.addOption(MAX_ELEMENTS);
        options.addOption(MAX_CHARACTERS);
        options.addOption(MAX_TIME);
        options.addOption(FETCH);
    }

    public static void main(String[] args) {

        Properties defaultOptions = new Properties();
        defaultOptions.put(MAX_ELEMENTS.getOpt(), "1");
        defaultOptions.put(FETCH.getOpt(), "1");

        Enumerate client = new Enumerate();
        client.setCustomUsage("enumContextFile");
        client.setHelpFooter(FOOTER);

        EnumerationContextType context = null;
        CommandLine line = null;
        
        try {
            line = client.parse(args, defaultOptions);

            List options = line.getArgList();
            if (options == null || options.isEmpty()) {
                throw new ParseException("Expected enumeration context file");
            }
            FileInputStream in = null;
            try {
                in = new FileInputStream((String)options.get(0));
                context =
                    (EnumerationContextType)ObjectDeserializer.deserialize(
                                               new InputSource(in),
                                               EnumerationContextType.class);
            } finally {
                if (in != null) {
                    try { in.close(); } catch (Exception ee) {}
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(COMMAND_LINE_ERROR);
        }
      
        EnumerationServiceAddressingLocator locator = 
            new EnumerationServiceAddressingLocator();

        int maxElements = 1;
        int maxCharacters = -1;
        int fetchNum = 1;
        Duration maxTime = null;

        try {
            DataSource port = 
                locator.getDataSourcePort(client.getEPR());
            client.setOptions((Stub)port);
                        
            if (line.hasOption(MAX_ELEMENTS.getOpt())) {
                String value = line.getOptionValue(MAX_ELEMENTS.getOpt());
                maxElements = Integer.parseInt(value);
            }

            if (line.hasOption(MAX_CHARACTERS.getOpt())) {
                String value = line.getOptionValue(MAX_CHARACTERS.getOpt());
                maxCharacters = Integer.parseInt(value);
            }

            if (line.hasOption(MAX_TIME.getOpt())) {
                String value = line.getOptionValue(MAX_TIME.getOpt());
                long timeout = Long.parseLong(value);
                maxTime = DurationUtils.toDuration(timeout);
            }
            
            if (line.hasOption(FETCH.getOpt())) {
                String value = line.getOptionValue(FETCH.getOpt());
                if ("all".equalsIgnoreCase(value)) {
                    fetchNum = Integer.MAX_VALUE;
                } else {
                    fetchNum = Integer.parseInt(value);
                }
            }

            int fetched = 0;
            int retrieved = 0;

            IterationConstraints constraints = 
                new IterationConstraints(maxElements, maxCharacters, maxTime);

            ClientEnumeration enumeration = 
                new ClientEnumeration((Stub)port, context);
            
            IterationResult iterResult;
            do {
                iterResult = enumeration.pull(constraints);
                Object [] items = iterResult.getItems();
                if (items == null || items.length == 0) {
                    // must not meet maxCharacters constraint, exit
                    break;
                } else {
                    for (int i=0;
                         i < items.length && fetched < fetchNum;
                         i++, fetched++) {
                        System.out.println(items[i]);
                    }
                    retrieved += items.length;
                }
            } while (!iterResult.isEndOfSequence() && fetched < fetchNum);

            System.out.println();
            System.out.println("Number of items displayed: " + fetched);
            System.out.println("Total items retrieved: " + retrieved);
            
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
