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

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertySet;

public class AxisServiceGroupEntry implements Resource, ResourceProperties {

    // must match what it says in WSDL
    public static final QName RP_SET =
        new QName("http://www.globus.org/namespaces/2004/06/registry", 
                  "RegistryEntryRP");

    private ResourcePropertySet propSet;

    private EndpointReferenceType serviceGroupEPR;
    private EndpointReferenceType memberEPR;
    private EndpointReferenceType serviceGroupEntryEPR;
    private Object content;
    
    public AxisServiceGroupEntry(EndpointReferenceType serviceGroupEntryEPR,
                                 EndpointReferenceType serviceGroupEPR,
                                 EndpointReferenceType memberEPR) {
        this.serviceGroupEntryEPR = serviceGroupEntryEPR;
        this.serviceGroupEPR = serviceGroupEPR;
        this.memberEPR = memberEPR;
        this.content = null;

        // init resource properties

        this.propSet = new SimpleResourcePropertySet(RP_SET);
        ResourceProperty prop = null;
        try {
            prop = new ReflectionResourceProperty(
                         WSRFConstants.SERVICEGROUP_EPR, this);
            this.propSet.add(prop);

            prop = new ReflectionResourceProperty(
                         WSRFConstants.MEMBER_EPR, this);
            this.propSet.add(prop);

            prop = new ReflectionResourceProperty(
                         WSRFConstants.CONTENT, this);
            this.propSet.add(prop);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResourcePropertySet getResourcePropertySet() {
        return this.propSet;
    }

    public EndpointReferenceType getServiceGroupEPR() {
        return this.serviceGroupEPR;
    }

    public EndpointReferenceType getMemberEPR() {
        return this.memberEPR;
    }

    public EndpointReferenceType getServiceGroupEntryEPR() {
        return this.serviceGroupEntryEPR;
    }

    public Object getContent() {
        return this.content;
    }
    
}
