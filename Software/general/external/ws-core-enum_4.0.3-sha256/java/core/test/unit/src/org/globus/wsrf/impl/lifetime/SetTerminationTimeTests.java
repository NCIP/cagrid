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
package org.globus.wsrf.impl.lifetime;

import java.util.Calendar;

import org.apache.axis.message.MessageElement;
import org.globus.wsrf.Constants;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.impl.TestHome;
import org.globus.wsrf.test.GridTestSuite;
import org.globus.wsrf.tests.basic.TestPortType;
import org.oasis.wsrf.lifetime.ResourceUnknownFaultType;
import org.oasis.wsrf.lifetime.SetTerminationTime;
import org.oasis.wsrf.lifetime.SetTerminationTimeResponse;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;

public class SetTerminationTimeTests extends LifetimeTestCase {

    public SetTerminationTimeTests(String name) {
        super(name);
    }

    public void testUnknownResource() throws Exception {
        TestPortType port = 
            locator.getTestPortTypePort(createEPR(TestHome.BAD_KEY));

        Calendar termTime = Calendar.getInstance();
        termTime.add(Calendar.SECOND, 30);
        
        SetTerminationTime request = new SetTerminationTime();
        request.setRequestedTerminationTime(termTime);

        try {
            SetTerminationTimeResponse response =
                port.setTerminationTime(request);

            fail("Did not throw exception");
        } catch (ResourceUnknownFaultType e) {
            //it's ok
        }
    }

    public void testTimeInPast() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        Calendar termTime = Calendar.getInstance();
        termTime.add(Calendar.SECOND, -30);

        SetTerminationTime request = new SetTerminationTime();
        request.setRequestedTerminationTime(termTime);

        SetTerminationTimeResponse response =
            port.setTerminationTime(request);

        Calendar newTermTime = response.getNewTerminationTime();
        Calendar currentTime = response.getCurrentTime();

        // in our impl we newTermTime == currentTime if requested
        // time was in the past
        assertTrue(newTermTime != null);
        assertTrue(currentTime != null);
        assertTrue(newTermTime.getTime().equals(currentTime.getTime()));
        
        try {
            port.setTerminationTime(request);
            fail("Did not throw exception");
        } catch (ResourceUnknownFaultType e) {
            //it's ok
        }
    }

    public void testTimeInFuture() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        Calendar termTime = Calendar.getInstance();
        termTime.add(Calendar.SECOND, 30);

        SetTerminationTime request = new SetTerminationTime();
        request.setRequestedTerminationTime(termTime);

        SetTerminationTimeResponse response =
            port.setTerminationTime(request);

        Calendar newTermTime = response.getNewTerminationTime();
        Calendar currentTime = response.getCurrentTime();

        // in our impl we newTermTime == currentTime if requested
        // time was in the past
        assertTrue(newTermTime != null);
        assertTrue(currentTime != null);
        assertTrue(newTermTime.getTime().equals(termTime.getTime()));
        
        org.oasis.wsrf.properties.ResourceUnknownFaultType fault = null;
        int wait = 0;
        while(wait < GridTestSuite.timeout) {
            try
            {
                assertEquals(newTermTime.getTime(), 
                             getTerminationTimeRP(port).getTime());

                wait += 1000 * 45;
                Thread.sleep(1000 * 45);
            }
            catch(org.oasis.wsrf.properties.ResourceUnknownFaultType e)
            {
                fault = e;
                break;
            }
        }

        assertTrue("timeout or exception not generated", fault != null);
    }

    private Calendar getTerminationTimeRP(TestPortType port) 
        throws Exception {
        GetResourcePropertyResponse propResponse =
            port.getResourceProperty(WSRFConstants.TERMINATION_TIME);
        
        MessageElement [] any = propResponse.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);
        
        Object obj = 
            ObjectDeserializer.toObject(any[0], Calendar.class);
        assertTrue(obj instanceof Calendar);
        
        return (Calendar)obj;
    }

    public void testChangeTime() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        Calendar termTime = Calendar.getInstance();
        termTime.add(Calendar.SECOND, 30);
        
        SetTerminationTime request = null;
        SetTerminationTimeResponse response = null;
        
        request = new SetTerminationTime();
        request.setRequestedTerminationTime(termTime);
        
        response = port.setTerminationTime(request);

        Calendar newTermTime = null;
        Calendar currentTime = null;

        newTermTime = response.getNewTerminationTime();
        currentTime = response.getCurrentTime();
        
        // in our impl we newTermTime == currentTime if requested
        // time was in the past
        assertTrue(newTermTime != null);
        assertTrue(currentTime != null);
        assertTrue(newTermTime.getTime().equals(termTime.getTime()));

        assertEquals(termTime.getTime(), getTerminationTimeRP(port).getTime());

        // send nill request - no term time
        
        request = new SetTerminationTime();
        response = port.setTerminationTime(request);
        
        newTermTime = response.getNewTerminationTime();
        currentTime = response.getCurrentTime();
        
        assertTrue(newTermTime == null);
        assertTrue(currentTime != null);
        
        GetResourcePropertyResponse propResponse =
            port.getResourceProperty(WSRFConstants.TERMINATION_TIME);
        
        MessageElement [] any = propResponse.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);
        assertEquals(WSRFConstants.TERMINATION_TIME, any[0].getQName());
        assertEquals("true", any[0].getAttributeNS(Constants.XSI_NS, "nil"));
    }
    
}
