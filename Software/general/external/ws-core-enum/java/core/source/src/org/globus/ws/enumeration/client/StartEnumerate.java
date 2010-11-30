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

import javax.xml.namespace.QName;
import javax.xml.rpc.Stub;

import org.apache.commons.cli.CommandLine;
import org.globus.wsrf.client.BaseClient;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.utils.FaultHelper;
import org.xmlsoap.schemas.ws._2004._09.enumeration.DataSourceStart;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.service.EnumerationServiceAddressingLocator;

public class StartEnumerate extends BaseClient {

    private static final QName NAME =
        new QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", 
                  "EnumerationContext");

    public static void main(String[] args) {

        StartEnumerate client = new StartEnumerate();
        
        try {
            CommandLine line = client.parse(args);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(COMMAND_LINE_ERROR);
        }
        
        EnumerationServiceAddressingLocator locator = 
            new EnumerationServiceAddressingLocator();
        
        try {
            DataSourceStart port = 
                locator.getDataSourceStartPort(client.getEPR());
            client.setOptions((Stub)port);
            
            Enumerate enumRequest = new Enumerate();
            EnumerateResponse enumRespose = port.enumerateOp(enumRequest);
            EnumerationContextType context = 
                enumRespose.getEnumerationContext();
            
            System.out.println(ObjectSerializer.toString(context, NAME));
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
