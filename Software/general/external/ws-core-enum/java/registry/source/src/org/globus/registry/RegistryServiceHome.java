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
package org.globus.registry;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.globus.wsrf.impl.ServiceResourceHome;
import org.globus.wsrf.jndi.JNDIUtils;

public class RegistryServiceHome extends ServiceResourceHome {

    private String registryLocation;

    public void setRegistryLocation(String location) {
        this.registryLocation = location;
    }
    
    public String getRegistryLocation() {
        return this.registryLocation;
    }

    public synchronized void initialize() throws Exception {
        super.initialize();
        
        RegistryService registryService = (RegistryService)findSingleton();
        Context initialContext = new InitialContext();
        AxisRegistryHome home = 
            (AxisRegistryHome)JNDIUtils.lookup(initialContext,
                                               this.registryLocation,
                                               AxisRegistryHome.class);
        registryService.initialize(home);
    }
    
}
