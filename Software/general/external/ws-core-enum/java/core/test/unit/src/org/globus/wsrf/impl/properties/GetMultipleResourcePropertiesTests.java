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
package org.globus.wsrf.impl.properties;

import java.util.Calendar;

import org.globus.wsrf.impl.TestHome;

import org.apache.axis.message.MessageElement;

import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.Constants;
import org.globus.wsrf.tests.basic.TestPortType;

import org.oasis.wsrf.properties.ResourceUnknownFaultType;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType;
import org.oasis.wsrf.lifetime.SetTerminationTime;
import org.oasis.wsrf.lifetime.SetTerminationTimeResponse;

import javax.xml.namespace.QName;

public class GetMultipleResourcePropertiesTests extends PropertiesTestCase {

    private static final QName [] PROP_NAMES = 
        new QName[] {WSRFConstants.TERMINATION_TIME,
                     WSRFConstants.CURRENT_TIME};

    public GetMultipleResourcePropertiesTests(String name) {
        super(name);
    }

    public void testUnknownResource() throws Exception {
        TestPortType port = 
            locator.getTestPortTypePort(createEPR(TestHome.BAD_KEY));

        GetMultipleResourceProperties_Element request =
            new GetMultipleResourceProperties_Element();
        request.setResourceProperty(PROP_NAMES);

        try {
            GetMultipleResourcePropertiesResponse reponse = 
                port.getMultipleResourceProperties(request);
            fail("Did not throw exception");
        } catch (ResourceUnknownFaultType e) {
            //it's ok
        }
    }

    public void testGetMultipleResourceProperties() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        GetMultipleResourceProperties_Element propRequest = null;
        GetMultipleResourcePropertiesResponse propResponse = null;

        propRequest = new GetMultipleResourceProperties_Element();
        propRequest.setResourceProperty(PROP_NAMES);
        
        propResponse = port.getMultipleResourceProperties(propRequest);

        MessageElement [] any = null;

        any = propResponse.get_any();
        assertTrue(any != null);
        assertEquals(2, any.length);
        assertEquals(WSRFConstants.TERMINATION_TIME, any[0].getQName());
        assertEquals("true", any[0].getAttributeNS(Constants.XSI_NS, "nil"));
        assertEquals(WSRFConstants.CURRENT_TIME, any[1].getQName());

        // sets termination time
        Calendar termTime = Calendar.getInstance();
        termTime.add(Calendar.SECOND, 30);
        
        SetTerminationTime timeRequest = new SetTerminationTime();
        timeRequest.setRequestedTerminationTime(termTime);
        
        SetTerminationTimeResponse timeResponse =
            port.setTerminationTime(timeRequest);

        propResponse = port.getMultipleResourceProperties(propRequest);

        any = propResponse.get_any();
        assertTrue(any != null);
        assertEquals(2, any.length);
        
        boolean found = false;
        for (int i=0;i<PROP_NAMES.length;i++) {
            found = false;
            for (int j=0;j<any.length;j++) {
                if (PROP_NAMES[i].equals(any[j].getQName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("Did not return expected RP");
            }
        }
    }

    public void testGetUnknownResourceProperties() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        GetMultipleResourcePropertiesResponse propResponse = null;
        GetMultipleResourceProperties_Element propRequest = null;

        // test 1: null request
        try {
            propResponse = port.getMultipleResourceProperties(propRequest);
            fail("Did not throw exception");
        } catch (InvalidResourcePropertyQNameFaultType e) {
            // that's what we want
        }

        // test 2: empty request
        propRequest = new GetMultipleResourceProperties_Element();
        
        try {
            propResponse = port.getMultipleResourceProperties(propRequest);
            fail("Did not throw exception");
        } catch (InvalidResourcePropertyQNameFaultType e) {
            // that's what we want
        }

        // test 3: empty properties
        propRequest.setResourceProperty(new QName[] {});

        try {
            propResponse = port.getMultipleResourceProperties(propRequest);
            fail("Did not throw exception");
        } catch (InvalidResourcePropertyQNameFaultType e) {
            // that's what we want
        }

        QName badRP = new QName("http://foo", "bar");
            
        // test 4: one bad RP
        propRequest.setResourceProperty(new QName[] {badRP});
        
        try {
            propResponse = port.getMultipleResourceProperties(propRequest);
            fail("Did not throw exception");
        } catch (InvalidResourcePropertyQNameFaultType e) {
            // that's what we want
        }

        // test 4: one good, one bad RP
        propRequest.setResourceProperty(
             new QName[] {badRP, WSRFConstants.CURRENT_TIME}
        );
        
        try {
            propResponse = port.getMultipleResourceProperties(propRequest);
            fail("Did not throw exception");
        } catch (InvalidResourcePropertyQNameFaultType e) {
            // that's what we want
        }
    }
    
}
