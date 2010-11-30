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

import java.util.Calendar;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.Stub;

import org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;

import com.enumeration.EnumerationPortType;

import org.globus.wsrf.ResourceKey;
import org.globus.axis.utils.DurationUtils;
import org.globus.wsrf.utils.AnyHelper;
import org.globus.wsrf.encoding.ObjectSerializer;

import org.apache.axis.AxisFault;
import org.apache.axis.types.Duration;

import org.globus.wsrf.test.GridTestSuite;

public class ClientEnumerationTests extends EnumerationTestCase {

    public ClientEnumerationTests(String name) {
        super(name);
    }

    public void testStub() throws Exception {
        EnumerationContextType context = startEnumeration(1, false);
        
        EnumerationPortType port = 
            testLocator.getEnumerationPortTypePort(getServiceAddress());
        
        ClientEnumeration client = new ClientEnumeration((Stub)port, context);
        
        IterationResult result;
        for (int i=0;i<3;i++) {
            result = client.pull(IterationConstraints.DEFAULT_CONSTRAINTS);
            checkIterationResult(result, i, 1, false);
        }
        
        client.release();
    }

    public void testInvalidEnumerationContext() throws Exception {
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());

        ClientEnumeration client = null;
        EnumerationContextType context = null;

        context = new EnumerationContextType();
        client = new ClientEnumeration(port, context);
        
        checkInvalidContext(client);

        // create enumeration context with some wrong element key
        AnyHelper.setAny(context, ObjectSerializer.toSOAPElement(
                                    "foobar", 
                                     new QName("http://foo.bar", "id")));

        client = new ClientEnumeration(port, context);
     
        checkInvalidContext(client);
    }

    private void checkInvalidContext(ClientEnumeration client)
        throws Exception {
        try {
            client.getStatus();
            fail("Did not throw exception");
        } catch (RemoteException e) {
            if (e.getMessage().indexOf(INVALID_CONTEXT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            }
        }
    }

    public void testVisibility() throws Exception {
        EnumerationContextType e0 = createEnumeration();
        
        ResourceKey k1 = TestEnumHome.getKey(1);
        EnumerationContextType e1 = createEnumeration(k1);
        
        ResourceKey k2 = TestEnumHome.getKey(2);
        EnumerationContextType e2 = createEnumeration(k2);
        
        DataSource port;
        ClientEnumeration client;
        
        // access context e1 without resource key
        port = locator.getDataSourcePort(getServiceAddress());
        client = new ClientEnumeration(port, e1);
        checkInvalidContext(client);

        // access context e2 without resource key
        port = locator.getDataSourcePort(getServiceAddress());
        client = new ClientEnumeration(port, e2);
        checkInvalidContext(client);
        
        // access context e1 with resource key 2
        port = locator.getDataSourcePort(getServiceAddress(k2));
        client = new ClientEnumeration(port, e1);
        checkInvalidContext(client);
        
        // access context e2 with resource key 1
        port = locator.getDataSourcePort(getServiceAddress(k1));
        client = new ClientEnumeration(port, e2);
        checkInvalidContext(client);

        // access context e0 with resource key 1
        port = locator.getDataSourcePort(getServiceAddress(k1));
        client = new ClientEnumeration(port, e0);
        checkInvalidContext(client);

        // access context e0 with resource key 2
        port = locator.getDataSourcePort(getServiceAddress(k2));
        client = new ClientEnumeration(port, e0);
        checkInvalidContext(client);

        // release contexts
        port = locator.getDataSourcePort(getServiceAddress());
        client = new ClientEnumeration(port, e0);
        client.release();

        port = locator.getDataSourcePort(getServiceAddress(k1));
        client = new ClientEnumeration(port, e1);
        client.release();

        port = locator.getDataSourcePort(getServiceAddress(k2));
        client = new ClientEnumeration(port, e2);
        client.release();
    }

    public void testExpirationDate() throws Exception {
        EnumerationContextType e1 = createEnumeration();

        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());

        ClientEnumeration client = new ClientEnumeration(port, e1);

        // when created it should not have expiration set
        EnumExpiration expiration = client.getStatus();
        assertTrue(expiration == null);

        EnumExpiration newExpiration;
        Calendar cal;

        // set ok expiration
        cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 1);
        newExpiration = new EnumExpiration(cal);
        
        expiration = client.renew(newExpiration);
        checkCalendar(expiration, newExpiration);
        expiration = client.getStatus();
        checkCalendar(expiration, newExpiration);

        // set expiration in the past
        cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        try {
            client.renew(new EnumExpiration(cal));
            fail("Did now throw exception");
        } catch (AxisFault e) {
            if (e.getFaultString().indexOf(INVALID_EXPIRATION_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            } 
        }

        expiration = client.getStatus();
        checkCalendar(expiration, newExpiration);

        // check if can switch to duration now
        Duration duration = new Duration();
        duration.setHours(1);
        newExpiration = new EnumExpiration(duration);
        expiration = client.renew(newExpiration);
        checkDuration(expiration, newExpiration);
        expiration = client.getStatus();
        checkDuration(expiration, newExpiration);

        // reset expiration time
        expiration = client.renew(null);
        assertTrue(expiration == null);
        expiration = client.getStatus();
        assertTrue(expiration == null);

        // release enumeration
        client.release();
    }

    public void testExpirationDateLifetime() throws Exception {
        EnumerationContextType e1 = createEnumeration();
        
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumeration client = new ClientEnumeration(port, e1);

        EnumExpiration expiration, newExpiration;
        Calendar cal;

        // set short expiration
        cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 30);
        newExpiration = new EnumExpiration(cal);
        
        expiration = client.renew(newExpiration);
        checkCalendar(expiration, newExpiration);
        expiration = client.getStatus();
        checkCalendar(expiration, newExpiration);

        checkContext(client);
    }

    public void testExpirationDuration() throws Exception {
        EnumerationContextType e1 = createEnumeration();

        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());

        ClientEnumeration client = new ClientEnumeration(port, e1);

        // when created it should not have expiration set
        EnumExpiration expiration = client.getStatus();
        assertTrue(expiration == null);

        EnumExpiration newExpiration;
        Duration duration;
        
        // set ok expiration
        duration = new Duration();
        duration.setHours(1);
        newExpiration = new EnumExpiration(duration);
        expiration = client.renew(newExpiration);
        checkDuration(expiration, newExpiration);
        expiration = client.getStatus();
        checkDuration(expiration, newExpiration);

        // set expiration in the past
        duration = new Duration();
        duration.setNegative(true);
        duration.setHours(1);
        try {
            expiration = client.renew(new EnumExpiration(duration));
            fail("Did now throw exception");
        } catch (AxisFault e) {
            if (e.getFaultString().indexOf(INVALID_EXPIRATION_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            } 
        }

        expiration = client.getStatus();
        checkDuration(expiration, newExpiration);
        
        // check if can switch to date now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 1);
        newExpiration = new EnumExpiration(cal);
        expiration = client.renew(newExpiration);
        checkCalendar(expiration, newExpiration);
        expiration = client.getStatus();
        checkCalendar(expiration, newExpiration);
        
        // reset expiration time
        expiration = client.renew(null);
        assertTrue(expiration == null);
        expiration = client.getStatus();
        assertTrue(expiration == null);

        // release enumeration
        client.release();
    }

    public void testExpirationDurationLifetime() throws Exception {
        EnumerationContextType e1 = createEnumeration();
        
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumeration client = new ClientEnumeration(port, e1);

        EnumExpiration expiration, newExpiration;
        Duration duration;
        
        // set short expiration
        duration = new Duration();
        duration.setSeconds(30D);
        newExpiration = new EnumExpiration(duration);
        expiration = client.renew(newExpiration);
        checkDuration(expiration, newExpiration);
        expiration = client.getStatus();
        checkDuration(expiration, newExpiration);
        
        checkContext(client);
    }

    private void checkCalendar(EnumExpiration expiration,
                               EnumExpiration newExpiration) {
        assertTrue(expiration != null);
        assertTrue(expiration.getCalendar() != null);
        assertTrue(expiration.getDuration() == null);
        assertEquals(newExpiration.getCalendar().getTime(),
                     expiration.getCalendar().getTime());
    }

    private void checkDuration(EnumExpiration expiration,
                               EnumExpiration newExpiration) {
        assertTrue(expiration != null);
        assertTrue(expiration.getCalendar() == null);
        assertTrue(expiration.getDuration() != null);
        long expected = 
            DurationUtils.toMilliseconds(newExpiration.getDuration());
        long actual = 
            DurationUtils.toMilliseconds(expiration.getDuration());
        assertTrue( actual + 1000*60*2 > expected );
    }

    private void checkContext(ClientEnumeration client) {
        try {
            int wait = 0;
            while(wait < GridTestSuite.timeout) {
                assertTrue( client.getStatus() != null );
                wait += 1000 * 45;
                Thread.sleep(1000 * 45);
            }
            fail("Did not throw exception");
        } catch (AxisFault e) {
            if (e.getMessage().indexOf(INVALID_CONTEXT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Did not throw appropriate exception");
        }
    }

    public void testLocalRelease() throws Exception {
        EnumerationContextType e1 = createEnumeration();

        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());

        ClientEnumeration client = new ClientEnumeration(port, e1);
        
        client.release();
        
        assertTrue(client.getContext() == null);
        
        try {
            client.release();
            fail("Did not throw exception");
        } catch (RemoteException e) {
            if (e.getMessage().indexOf(CONTEXT_RELEASED_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            }
        }
        
        try {
            client.getStatus();
            fail("Did not throw exception");
        } catch (RemoteException e) {
            if (e.getMessage().indexOf(CONTEXT_RELEASED_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            }
        }
        
        try {
            client.pull();
            fail("Did not throw exception");
        } catch (RemoteException e) {
            if (e.getMessage().indexOf(CONTEXT_RELEASED_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            }
        }
        
        try {
            client.renew(null);
            fail("Did not throw exception");
        } catch (RemoteException e) {
            if (e.getMessage().indexOf(CONTEXT_RELEASED_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            }
        }
        
        try {
            client.getStatus();
            fail("Did not throw exception");
        } catch (RemoteException e) {
            if (e.getMessage().indexOf(CONTEXT_RELEASED_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            }
        }
    }

    public void testRemoteRelease() throws Exception {
        EnumerationContextType e1 = createEnumeration();

        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());

        ClientEnumeration client = new ClientEnumeration(port, e1);
        
        client.release();
        
        // re-create the client with released enumeration context
        client = new ClientEnumeration(port, e1);
        
        try {
            client.release();
            fail("Did not throw exception");
        } catch (AxisFault e) {
            if (e.getFaultString().indexOf(INVALID_CONTEXT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            } 
        }

        try {
            client.getStatus();
            fail("Did not throw exception");
        } catch (AxisFault e) {
            if (e.getFaultString().indexOf(INVALID_CONTEXT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            } 
        }
        
        try {
            client.pull();
            fail("Did not throw exception");
        } catch (AxisFault e) {
            if (e.getFaultString().indexOf(INVALID_CONTEXT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            } 
        }
        
        try {
            client.renew(null);
            fail("Did not throw exception");
        } catch (AxisFault e) {
            if (e.getFaultString().indexOf(INVALID_CONTEXT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            } 
        }
        
        try {
            client.getStatus();
            fail("Did not throw exception");
        } catch (AxisFault e) {
            if (e.getFaultString().indexOf(INVALID_CONTEXT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            } 
        }
    }
    
    public void testPullMixedSize() throws Exception {
        EnumerationContextType e1 = createEnumeration();
        
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumeration client = new ClientEnumeration(port, e1);

        IterationResult result;

        int index = 0;
        result = client.pull();
        checkIterationResult(result, index, 1, false);
        index++;

        int n = 2;
        for (int i=0;i<5;i++) {
            result = 
                client.pull(new IterationConstraints(n, -1, null));
            checkIterationResult(result, index, n, false);
            index+=n;
            n+=2;
        }

        for (int i=0;i<5;i++) {
            result = 
                client.pull(new IterationConstraints(n, -1, null));
            checkIterationResult(result, index, n, false);
            index+=n;
            n-=2;
        }

        int rest = TOTAL_ENUM_ELEMENTS - index;

        result = 
            client.pull(new IterationConstraints(rest, -1, null));
        checkIterationResult(result, index, rest, true);

        checkInvalidContext(client);
    }

    public void testPullMultiple() throws Exception {

        ClientEnumeration [] clients = new ClientEnumeration[3];
        int indexes [] = new int[clients.length];

        for (int i=0;i<clients.length;i++) {
            EnumerationContextType e1 = createEnumeration();
            
            DataSource port = 
                locator.getDataSourcePort(getServiceAddress());
            
            clients[i] = new ClientEnumeration(port, e1);
        }

        int n = TOTAL_ENUM_ELEMENTS;

        IterationResult result;

        
        for(int i=0;i<clients.length;i++) {
            int req = (i+1)*3;
            result = 
                clients[i].pull(new IterationConstraints(req, -1, null));
            
            if (indexes[i] + req < n) {
                checkIterationResult(result, indexes[i], req, false);
            } else {
                checkIterationResult(result, indexes[i], req, true);
            }
            
            indexes[i]+=req;
        }

        System.out.println("Starting indexes:");
        for(int i=0;i<clients.length;i++) {
            System.out.println(" " + indexes[i]);
        }
        
        for (int j=0;j<5;j++) {
            for(int i=0;i<clients.length;i++) {
                result = 
                    clients[i].pull(new IterationConstraints(5, -1, null));

                if (indexes[i] + 5 < n) {
                    checkIterationResult(result, indexes[i], 5, false);
                } else {
                    checkIterationResult(result, indexes[i], 5, true);
                }
                
                indexes[i]+=5;
            }
        }
        
        System.out.println("Ending indexes:");
        for(int i=0;i<clients.length;i++) {
            System.out.println(" " + indexes[i]);
        }

        for(int i=0;i<clients.length;i++) {
            clients[i].release();
        }
    }

    public void testPullWithConstraints() throws Exception {
        EnumerationContextType e1 = createEnumeration();
        
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumeration client = new ClientEnumeration(port, e1);

        IterationResult result;
        int index = 0;

        // the maxTime and maxCharacters are not enforced
        // so this will just test serialization and deserialization
        
        result = client.pull(new IterationConstraints(1, 5000, null));
        checkIterationResult(result, index, 1, false);
        index++;
        
        Duration dur = new Duration();
        dur.setYears(1);

        result = client.pull(new IterationConstraints(1, -1, dur));
        checkIterationResult(result, index, 1, false);
        index++;

        result = client.pull(new IterationConstraints(1, 5000, dur));
        checkIterationResult(result, index, 1, false);
        index++;

        client.release();
    }


    public void testPullMemoryTransient() throws Exception {
        doIterations(1, false);
    }

    public void testPullFileTransient() throws Exception {
        doIterations(2, false);
    }
    
    public void testPullFilePersistent() throws Exception {
        doIterations(2, true);
    }

    private void doIterations(int iteratorType, boolean persistent) 
        throws Exception {
        doIteration(1, startEnumeration(iteratorType, persistent));
        doIteration(2, startEnumeration(iteratorType, persistent));
        doIteration(3, startEnumeration(iteratorType, persistent));
        doIteration(5, startEnumeration(iteratorType, persistent));
        doIteration(7, startEnumeration(iteratorType, persistent));
        doIteration(TOTAL_ENUM_ELEMENTS,
                    startEnumeration(iteratorType, persistent));
        doIteration(Integer.MAX_VALUE,
                    startEnumeration(iteratorType, persistent));
    }

    private void doIteration(int reqElement,
                             EnumerationContextType context)
        throws Exception {
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumeration client = new ClientEnumeration(port, context);

        IterationResult result;

        int n = TOTAL_ENUM_ELEMENTS;

        for (int i=0;i<n;i+=reqElement) {
            result = 
                client.pull(new IterationConstraints(reqElement, -1, null));

            if (i+reqElement < n) {
                checkIterationResult(result, i, reqElement, false);
            } else if (i+reqElement == n) {
                checkIterationResult(result, i, reqElement, true);
            } else {
                int maxElements = n - i;
                checkIterationResult(result, i, maxElements, true);
            }
        }

        checkInvalidContext(client);
    }

    public void testTimeout() throws Exception {
        EnumerationContextType e1 = startEnumeration(3, false);
        
        DataSource port = 
            locator.getDataSourcePort(getServiceAddress());
        
        ClientEnumeration client = new ClientEnumeration(port, e1);

        IterationResult result;
        
        result = client.pull(IterationConstraints.DEFAULT_CONSTRAINTS);
        checkIterationResult(result, 0, 1, false);
        
        Duration dur = new Duration();
        dur.setYears(5);

        try {
            result = client.pull(new IterationConstraints(1, -1, dur));
            fail("Did not throw exception");
        } catch (RemoteException e) {
            if (e.getMessage().indexOf(TIMEOUT_MSG) == -1) {
                e.printStackTrace();
                fail("invalid exception");
            }
        }

        result = client.pull(IterationConstraints.DEFAULT_CONSTRAINTS);
        checkIterationResult(result, 1, 1, false);
    }
    
}
