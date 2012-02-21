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
package org.globus.wsrf.container;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;

public class ServiceContainerTest extends TestCase {

    static Log logger =
        LogFactory.getLog(ServiceContainerTest.class.getName());

    public ServiceContainerTest(String name) {
        super(name);
    }

    public void testContainerReuse() throws Exception {
        ServiceContainer container1 = 
            ServiceContainer.createContainer(false);
        
        ServiceContainer container2 = 
            ServiceContainer.createContainer(false);
        
        assertEquals(container1, container2);
        
        container2.stop();
        container1.stop();

        ServiceContainer container3 = 
            ServiceContainer.createContainer(false);
        
        assertTrue(container2 != container3);

        container3.stop();
    }

    public void testSeparateContainers() throws Exception {
        ServiceContainer container1 = 
            ServiceContainer.createContainer(false);
        
        ServiceContainer container2 = 
            ServiceContainer.createContainer(true);
        
        assertTrue(container1 != container2);

        assertTrue(ServiceContainerCollection.get(container1.getURLString()) != null);
        assertTrue(ServiceContainerCollection.get(container2.getURLString()) != null);
        
        container1.stop();
        container2.stop();
        
        assertTrue(ServiceContainerCollection.get(container1.getURLString()) == null);
        assertTrue(ServiceContainerCollection.get(container2.getURLString()) == null);
    }
    
    public void testReferences() throws Exception {
        ServiceContainer container1 = 
            ServiceContainer.createContainer(false);
        
        ServiceContainer container2 = 
            ServiceContainer.createContainer(false);
        
        assertTrue(container1 == container2);
        
        assertTrue(ServiceContainerCollection.get(container1.getURLString()) != null);
        
        container1.stop();
        
        assertTrue(ServiceContainerCollection.get(container1.getURLString()) != null);
        
        container2.stop();
        
        assertTrue(ServiceContainerCollection.get(container1.getURLString()) == null);
    }
    
    public void testIsRunning() throws Exception {
        ServiceContainer container1 = 
            ServiceContainer.createContainer(false);
        
        assertTrue(container1.isRunning());
        
        container1.stop();
        
        assertTrue(!container1.isRunning());
    }

}
