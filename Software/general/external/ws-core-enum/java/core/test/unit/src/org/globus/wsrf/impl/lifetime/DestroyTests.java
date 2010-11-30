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

import org.globus.wsrf.impl.TestHome;
import org.globus.wsrf.tests.basic.TestPortType;
import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsrf.lifetime.ResourceNotDestroyedFaultType;
import org.oasis.wsrf.lifetime.ResourceUnknownFaultType;

public class DestroyTests extends LifetimeTestCase {

    public DestroyTests(String name) {
        super(name);
    }

    public void testUnknownResource() throws Exception {
        TestPortType port = 
            locator.getTestPortTypePort(createEPR(TestHome.BAD_KEY));
        
        try {
            port.destroy(new Destroy());
            fail("Did not throw exception");
        } catch (ResourceUnknownFaultType e) {
            //it's ok
        }
    }

    public void testDestroy() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        // destroy resource
        port.destroy(new Destroy());

        try {
            port.destroy(new Destroy());
            fail("Did not throw exception");
        } catch (ResourceUnknownFaultType e) {
            //it's ok
        }      
    }

    // right now throw ResourceNotDestroyedFaultType, spec doesn't
    // define any better error at this point for this case
    public void testDestroyNoResourceKey() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        try {
            port.destroy(new Destroy());
            fail("Did not throw exception");
        } catch (ResourceNotDestroyedFaultType e) {
            //it's ok
        }      
    }
    
    // operation is supported but fails for whatever reason
    public void testUnsupportedDestroy() throws Exception {
        TestPortType port = 
            locator.getTestPortTypePort(createEPR(TestHome.TEST_KEY));
        
        try {
            port.destroy(new Destroy());
            fail("Did not throw exception");
        } catch (ResourceNotDestroyedFaultType e) {
            //it's ok
        }
    }
    
}
