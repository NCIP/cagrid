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

import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.impl.TestHome;
import org.globus.wsrf.tests.basic.TestPortType;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType;
import org.oasis.wsrf.properties.ResourceUnknownFaultType;

public class GetResourcePropertyTests extends PropertiesTestCase {

    public GetResourcePropertyTests(String name) {
        super(name);
    }

    public void testUnknownResource() throws Exception {
        TestPortType port = 
            locator.getTestPortTypePort(createEPR(TestHome.BAD_KEY));

        try {
            GetResourcePropertyResponse propResponse =
                port.getResourceProperty(WSRFConstants.TERMINATION_TIME);
            fail("Did not throw exception");
        } catch (ResourceUnknownFaultType e) {
            //it's ok
        }
    }

    public void testGetResourceProperty() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        GetResourcePropertyResponse propResponse =
            port.getResourceProperty(WSRFConstants.CURRENT_TIME);

        MessageElement [] any = propResponse.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);
        
        Object obj = 
            ObjectDeserializer.toObject(any[0], Calendar.class);
        assertTrue(obj instanceof Calendar);
    }

    public void testGetUnknownResourceProperty() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        GetResourcePropertyResponse propResponse = null;

        try {
            propResponse = port.getResourceProperty(null);
            fail("Did not throw exception");
        } catch (InvalidResourcePropertyQNameFaultType e) {
            // that's what we want
        }

        try {
            propResponse = port.getResourceProperty(new QName("http://foo",
                                                              "bar"));
            fail("Did not throw exception");
        } catch (InvalidResourcePropertyQNameFaultType e) {
            // that's what we want
        }
    }
    
}
