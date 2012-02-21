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

import org.globus.wsrf.impl.TestService;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.tests.basic.service.TestServiceAddressingLocator;
import org.globus.wsrf.tests.basic.TestPortType;

import org.globus.wsrf.tests.basic.CreateResource;
import org.globus.wsrf.tests.basic.CreateResourceResponse;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.message.addressing.ReferencePropertiesType;
import org.apache.axis.types.URI;

import org.globus.wsrf.test.GridTestCase;

public class PropertiesTestCase extends GridTestCase {

    public TestServiceAddressingLocator locator = 
        new TestServiceAddressingLocator();

    private EndpointReferenceType testServiceEPR = null;

    public PropertiesTestCase(String name) {
        super(name);
    }

    protected EndpointReferenceType createResource(TestPortType port)
        throws Exception {
        CreateResourceResponse response = 
            port.createResource(new CreateResource());
        EndpointReferenceType epr = response.getEndpointReference();
        assertTrue(epr != null);
        return epr;
    }

    protected EndpointReferenceType createEPR()
        throws Exception {
        return createEPR(null);
    }
    
    protected EndpointReferenceType createEPR(ResourceKey key)
        throws Exception {
        String address = 
            TEST_CONTAINER.getBaseURL() + TestService.SERVICE_PATH;
        EndpointReferenceType epr = 
            new EndpointReferenceType(new URI(address));

        if (key != null) {
            ReferencePropertiesType props = new ReferencePropertiesType();
            props.add(key.toSOAPElement());
            epr.setProperties(props);
        }
        
        return epr;
    }

    public EndpointReferenceType getServiceAddress() throws Exception {
        if (this.testServiceEPR == null) {
            String address = 
                TEST_CONTAINER.getBaseURL() + TestService.SERVICE_PATH;
            this.testServiceEPR = 
                new EndpointReferenceType(new URI(address));
        }
        return this.testServiceEPR;
    }
    
}
