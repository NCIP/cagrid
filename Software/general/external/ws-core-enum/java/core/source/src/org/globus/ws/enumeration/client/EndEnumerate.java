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
import java.io.FileInputStream;

import org.globus.ws.enumeration.ClientEnumeration;

import org.xmlsoap.schemas.ws._2004._09.enumeration.service.EnumerationServiceAddressingLocator;
import org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;

import org.globus.wsrf.client.BaseClient;
import org.globus.wsrf.utils.FaultHelper;
import org.globus.wsrf.encoding.ObjectDeserializer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import org.xml.sax.InputSource;

import javax.xml.rpc.Stub;

public class EndEnumerate extends BaseClient {

    private static final String FOOTER =
        "Where:\n" +
        "  enumContextFile is a file containing the enumeration context\n";

    public static void main(String[] args) {

        EndEnumerate client = new EndEnumerate();
        client.setCustomUsage("enumContextFile");
        client.setHelpFooter(FOOTER);
        
        EnumerationContextType context = null;
        try {
            CommandLine line = client.parse(args);

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
        
        try {
            DataSource port = 
                locator.getDataSourcePort(client.getEPR());
            client.setOptions((Stub)port);

            ClientEnumeration enumeration = 
                new ClientEnumeration(port, context);
            
            enumeration.release();
            
            System.out.println("Enumeration was successfuly released");
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
