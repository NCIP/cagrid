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
package org.globus.ws.enumeration;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.message.addressing.ReferencePropertiesType;
import org.apache.axis.types.URI;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.test.GridTestCase;
import org.globus.wsrf.types.profiling.Timestamp;
import org.globus.wsrf.types.profiling.TimestampType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.DataSourceStart;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.service.EnumerationServiceAddressingLocator;

import com.enumeration.EnumerationPortType;
import com.enumeration.StartEnumeration;
import com.enumeration.StartEnumerationResponse;

public class EnumerationTestCase extends GridTestCase {

    public static final String INVALID_CONTEXT_MSG = 
        "Invalid enumeration context";

    public static final String CONTEXT_RELEASED_MSG =
        "Enumeration context is released";
    
    public static final String INVALID_EXPIRATION_MSG =
        "Invalid expiration time";

    public static final String TIMEOUT_MSG =
        "timeout";

    public static final int TOTAL_ENUM_ELEMENTS = 100;

    public static final String ENUM_ELEMENT = "foo";

    public static final QName ITEM_NAME = 
        new QName("http://www.globus.org", "Item");

    protected EnumerationServiceAddressingLocator locator = 
        new EnumerationServiceAddressingLocator();

    protected com.enumeration.service.EnumerationServiceAddressingLocator testLocator = new com.enumeration.service.EnumerationServiceAddressingLocator();
    
    public EnumerationTestCase(String name) {
        super(name);
    }

    protected EndpointReferenceType createEPR()
        throws Exception {
        return createEPR(null);
    }
    
    protected EndpointReferenceType createEPR(ResourceKey key)
        throws Exception {
        String address = 
            TEST_CONTAINER.getBaseURL() + "TestEnumService";
        EndpointReferenceType epr = 
            new EndpointReferenceType(new URI(address));

        if (key != null) {
            ReferencePropertiesType props = new ReferencePropertiesType();
            props.add(key.toSOAPElement());
            epr.setProperties(props);
        }
        
        return epr;
    }

    protected EndpointReferenceType getServiceAddress(ResourceKey key) 
        throws Exception {
        return createEPR(key);
    }

    protected EndpointReferenceType getServiceAddress() 
        throws Exception {
        return getServiceAddress(null);
    }

    protected EnumerationContextType createEnumeration() 
        throws Exception {
        return createEnumeration(null);
    }
    
    protected EnumerationContextType createEnumeration(ResourceKey key) 
        throws Exception {
        DataSourceStart port = 
            locator.getDataSourceStartPort(getServiceAddress(key));
        Enumerate enumRequest = new Enumerate();
        EnumerateResponse enumRespose = port.enumerateOp(enumRequest);
        EnumerationContextType context = 
            enumRespose.getEnumerationContext();
        return context;
    }

    protected EnumerationContextType startEnumeration(int iteratorType,
                                                      boolean persistent) 
        throws Exception {
        EnumerationPortType port = 
            testLocator.getEnumerationPortTypePort(getServiceAddress());
        
        StartEnumeration req = new StartEnumeration();
        req.setPersistent(persistent);
        req.setIteratorType(iteratorType);
        StartEnumerationResponse respose = port.startEnumeration(req);
        
        EnumerationContextType context = 
            respose.getEnumerationContext();
        return context;
    }

    /*
    protected static String toString(SOAPElement element) {
        return element.getFirstChild().toString();
    }
    */

    protected static String toString(SOAPElement element) 
        throws Exception {
        return (String)ObjectDeserializer.toObject(element, String.class);
    }

    protected static void checkIterationResult(IterationResult result,
                                               int index,
                                               int expectedItems,
                                               boolean expectedEndOfSequence) 
        throws Exception {
        assertTrue(result.getItems() != null);
        assertEquals(expectedItems, result.getItems().length);
        
        for (int i=0;i<expectedItems;i++) {
            SOAPElement elem = result.getItems()[i];
            assertEquals(ENUM_ELEMENT + (index + i), toString(elem));
        }
        
        assertEquals(expectedEndOfSequence, result.isEndOfSequence());
    }    

    protected static List getData(int n) {
        List list = new ArrayList();
        for (int i=0;i<n;i++) {
            list.add(ENUM_ELEMENT + i);
        }
        return list;
    }

    protected static List[] getSOAPElementData() 
        throws Exception {
        List list = new ArrayList();
        List actual = new ArrayList();
        List qnames = new ArrayList();

        QName itemName0 = 
            new QName("http://www.globus.org", "DiffItem1");

        String v1 = "foo1";
        actual.add(v1);
        qnames.add(itemName0);

        list.add(ObjectSerializer.toSOAPElement(v1, itemName0));

        QName itemName1 = 
            new QName("http://www.globus.org", "DiffItem1");
        
        Integer v2 = new Integer(12345);
        actual.add(v2);
        qnames.add(itemName1);

        list.add(ObjectSerializer.toSOAPElement(v2, itemName1));

        QName itemName2 = 
            new QName("http://www.globus.org", "DiffItem2");
        
        String v3 = "6785z";
        actual.add(v3);
        qnames.add(itemName2);

        list.add(ObjectSerializer.toSOAPElement(v3, itemName2));

        TimestampType timestampType = new TimestampType();
        Timestamp timestamp = new Timestamp();
        timestampType.setTimestamp(timestamp);
        timestamp.setThreadID("foo");
        timestamp.setMessageContextHash("bar");
        timestamp.setServiceURL("http://mismis");
        timestamp.setOperation("createCounter");

        QName itemName3 = 
            new QName("http://www.mcs.anl.gov", "DiffItem3");

        actual.add(timestampType);
        qnames.add(itemName3);

        list.add(ObjectSerializer.toSOAPElement(timestampType, itemName3));

        return new List[]{actual, qnames, list};
    }

}
