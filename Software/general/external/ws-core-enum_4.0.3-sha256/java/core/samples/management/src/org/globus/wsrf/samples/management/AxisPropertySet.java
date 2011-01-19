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
package org.globus.wsrf.samples.management;

import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertyMetaData;
import org.globus.wsrf.impl.SimpleResourcePropertySet;

import javax.xml.namespace.QName;

public class AxisPropertySet extends SimpleResourcePropertySet {

    private AxisService service;
    
    public AxisPropertySet(QName name, AxisService service) {
        super(name);
        this.service = service;
        setOpenContent(true);
    }

    public ResourceProperty create(ResourcePropertyMetaData rpMetaData) {
        // XXX: this sort of ignore all other settings
        QName name = rpMetaData.getName();
        return new AxisServiceProperty(this.service, name.getLocalPart());
    }
    
    public boolean add(ResourceProperty property) {
        QName name = property.getMetaData().getName();
        ResourceProperty rp = super.get(name);
        if (rp == null) {
            super.add(property);
            return true;
        } else {
            return false;
        }
    }
    
}
