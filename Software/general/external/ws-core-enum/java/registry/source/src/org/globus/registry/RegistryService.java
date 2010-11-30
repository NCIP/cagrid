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

import javax.xml.namespace.QName;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.oasis.wsrf.servicegroup.EntryType;
import org.oasis.wsrf.servicegroup.MembershipContentRule;

public class RegistryService implements Resource, ResourceProperties {

    // must match what it says in WSDL
    public static final QName RP_SET =
        new QName("http://www.globus.org/namespaces/2004/06/registry", 
                  "RegistryRP");

    private ResourcePropertySet propSet;
    private AxisRegistryHome registry;

    public RegistryService() {
        // init resource properties

        this.propSet = new SimpleResourcePropertySet(RP_SET);
        ResourceProperty prop = null;
        try {
            prop = new ReflectionResourceProperty(
                         WSRFConstants.MEMBERSHIP_CONTENT_RULE, this);
            this.propSet.add(prop);

            prop = new ReflectionResourceProperty(
                         WSRFConstants.ENTRY, this);
            this.propSet.add(prop);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected void initialize(AxisRegistryHome registry) {
        this.registry = registry;
    }

    public ResourcePropertySet getResourcePropertySet() {
        return this.propSet;
    }

    public MembershipContentRule[] getMembershipContentRule() {
        return null;
    }
    
    public EntryType[] getEntry() {
        return this.registry.getEntries();
    }
    
}
