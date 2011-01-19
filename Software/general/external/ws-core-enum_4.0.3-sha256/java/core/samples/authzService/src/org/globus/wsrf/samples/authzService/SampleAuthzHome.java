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
package org.globus.wsrf.samples.authzService;

import org.globus.wsrf.Resource;
import org.globus.wsrf.impl.SingletonResourceHome;

public class SampleAuthzHome extends SingletonResourceHome {

    protected Class resourceClass;
    private String declinedMethods = null;
    private Boolean signature = null;

    protected Resource findSingleton() {
        return new SampleAuthzResource(this.signature, 
                                       this.declinedMethods);
    }

    public void setDeclinedMethods(String declinedMethods) {
        this.declinedMethods = declinedMethods;
    }

    public String getDeclinedMethods() {
        return this.declinedMethods;
    }

    public void setSignature(String signature) {
        if (signature == null)
            this.signature = Boolean.FALSE;
        else 
            this.signature = new Boolean(signature);
    }

    public String getSignature() {
        return this.signature.toString();
    }

    public void setResourceClass(String clazz)
        throws ClassNotFoundException {
        this.resourceClass = Class.forName(clazz);
    }

    public String getResourceClass() {
        return this.resourceClass.getName();
    }
}
