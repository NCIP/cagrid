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

import java.util.NoSuchElementException;

import javax.xml.soap.SOAPElement;

import org.apache.axis.AxisFault;
import org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;

public class ClientEnumIteratorTests extends EnumerationTestCase {

    public ClientEnumIteratorTests(String name) {
        super(name);
    }

    public void testLocalRelease() throws Exception {
        EnumerationContextType e1 = createEnumeration();

        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());

        ClientEnumIterator client = new ClientEnumIterator(port, e1);
        
        assertTrue(client.hasNext());
        assertTrue(client.next() != null);

        client.release();
        client.release();
        
        assertFalse(client.hasNext());

        try {
            client.next();
            fail("Did not throw exception");
        } catch (NoSuchElementException e) {
        }
        
        try {
            client.remove();
            fail("Did not throw exception");
        } catch (UnsupportedOperationException e) {
        }
    }

    public void testRemoteRelease() throws Exception {
        EnumerationContextType e1 = createEnumeration();

        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());

        ClientEnumIterator client = new ClientEnumIterator(port, e1);
        
        assertTrue(client.hasNext());
        assertTrue(client.next() != null);

        client.release();
        
        // re-create the client with released enumeration context
        client = new ClientEnumIterator(port, e1);
        
        /***
         *** The following is a violation of the Iterator specification.
         *** This is a limitation of the WS-Enum specification.
         ***/
        
        // hasNext() returns true but next() returns errors out
        assertTrue(client.hasNext());
        
        try {
            client.next();
            fail("Did not throw exception");
        } catch (NoSuchElementException e) {
        }

        try {
            client.remove();
            fail("Did not throw exception");
        } catch (UnsupportedOperationException e) {
        }
    }

    public void testPull() throws Exception {
        doIteration(1);
        doIteration(2);
        doIteration(3);
        doIteration(5);
        doIteration(7);
        doIteration(TOTAL_ENUM_ELEMENTS);
        doIteration(Integer.MAX_VALUE);
    }
    
    public void testPullMixedSize() throws Exception {
        EnumerationContextType e1 = createEnumeration();

        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());

        ClientEnumIterator client = new ClientEnumIterator(port, e1);

        int index = 0;
        int n = 2;
        for (int i=0;i<5;i++) {
            client.setIterationConstraints(
                              new IterationConstraints(n, -1, null));
            checkIterationResult(client, index, n);
            index+=n;
            n+=2;
        }

        for (int i=0;i<5;i++) {
            client.setIterationConstraints(
                              new IterationConstraints(n, -1, null));
            checkIterationResult(client, index, n);
            index+=n;
            n-=2;
        }

        int rest = TOTAL_ENUM_ELEMENTS - index;

        client.setIterationConstraints(
                              new IterationConstraints(rest, -1, null));
        checkIterationResult(client, index, rest);

        try {
            client.getClientEnumeration().getStatus();
            fail("Did not throw exception");
        } catch (AxisFault e) {
            if (e.getFaultString().indexOf(INVALID_CONTEXT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            } 
        }
    }

    private void doIteration(int reqElement) throws Exception {
        EnumerationContextType e1 = createEnumeration();
        
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumIterator client = new ClientEnumIterator(port, e1);

        client.setIterationConstraints(
                     new IterationConstraints(reqElement, -1, null));

        checkIterationResult(client, 0, TOTAL_ENUM_ELEMENTS);

        assertFalse(client.hasNext());

        try {
            client.getClientEnumeration().getStatus();
            fail("Did not throw exception");
        } catch (AxisFault e) {
            if (e.getFaultString().indexOf(INVALID_CONTEXT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            } 
        }
    }

    private void checkIterationResult(ClientEnumIterator client, 
                                      int index,
                                      int expectedItems) 
        throws Exception {
        for (int i=0;i<expectedItems;i++) {
            assertTrue(client.hasNext());
            SOAPElement elem = (SOAPElement)client.next();
            assertEquals(ENUM_ELEMENT + (index+i), toString(elem));
        }
    }
             
    public void testDeserialization() throws Exception {
        EnumerationContextType e1 = createEnumeration();
        
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumIterator client = new ClientEnumIterator(port, e1);

        Object obj;

        assertTrue(client.getItemType() == null);
        assertTrue(client.hasNext());
        obj = client.next();
        assertTrue(obj != null);
        assertTrue(obj instanceof SOAPElement);
        assertEquals(ENUM_ELEMENT + 0, toString((SOAPElement)obj));
        
        // set to String
        client.setItemType(String.class);

        assertTrue(client.getItemType() == String.class);
        assertTrue(client.hasNext());
        obj = client.next();
        assertTrue(obj != null);
        assertTrue(obj instanceof String);
        assertEquals(ENUM_ELEMENT + 1, obj);

        // set to Integer
        client.setItemType(Integer.class);

        assertTrue(client.getItemType() == Integer.class);
        assertTrue(client.hasNext());
        try {
            obj = client.next();
        } catch (RuntimeException e) {
            if (e.getMessage().indexOf("convert") == -1) {
                e.printStackTrace();
                fail("Unexpected exception");
            }
        }

        client.setItemType(null);

        assertTrue(client.getItemType() == null);
        assertTrue(client.hasNext());
        obj = client.next();
        assertTrue(obj != null);
        assertTrue(obj instanceof SOAPElement);
        assertEquals(ENUM_ELEMENT + 2, toString((SOAPElement)obj));
        
        client.release();
    }

    public void testBasic() throws Exception {
        EnumerationContextType e1 = createEnumeration();
        
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumIterator client = new ClientEnumIterator(port, e1);

        for (int i=0;i<TOTAL_ENUM_ELEMENTS*2;i++) {
            assertTrue(client.hasNext());
        }
        
        int i = 0;
        while(client.hasNext()) {
            SOAPElement elem = (SOAPElement)client.next();
            assertEquals(ENUM_ELEMENT + i, toString(elem));
            i++;
        }
        
        assertEquals(i, TOTAL_ENUM_ELEMENTS);
        assertFalse(client.hasNext());
    }

    public void testCaching() throws Exception {
        EnumerationContextType e1 = createEnumeration();
        
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumIterator client = new ClientEnumIterator(port, e1);

        client.setIterationConstraints(
                      new IterationConstraints(5, -1, null));

        SOAPElement elem;

        assertTrue(client.hasNext());
        elem = (SOAPElement)client.next();
        assertEquals(ENUM_ELEMENT + 0, toString(elem));

        // create a new iterator
        client = new ClientEnumIterator(port, e1);
        assertTrue(client.hasNext());
        elem = (SOAPElement)client.next();
        assertEquals(ENUM_ELEMENT + 5, toString(elem));
    }
        
}
