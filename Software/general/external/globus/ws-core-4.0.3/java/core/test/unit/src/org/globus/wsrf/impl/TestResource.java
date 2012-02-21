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
package org.globus.wsrf.impl;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.ResourceLifetime;

import java.util.Calendar;

import javax.xml.namespace.QName;

public class TestResource
    implements Resource,
               ResourceProperties,
               ResourceLifetime {

    public static final String TEST_NS =
        "http://wsrf.globus.org/tests/basic";
    
    public static final QName RP_SET =
        new QName(TEST_NS, "TestRP");

    public static final QName VALUE_RP = 
        new QName(TEST_NS, "Value");
    
    private ResourcePropertySet propSet;
    private Calendar terminationTime = null;
    private int[] values = {5};

    public TestResource() throws Exception {
        this.propSet = new SimpleResourcePropertySet(RP_SET);

        ResourceProperty prop = null;

        prop = new ReflectionResourceProperty(SimpleResourcePropertyMetaData.TERMINATION_TIME, this);
        this.propSet.add(prop);
        
        prop = new ReflectionResourceProperty(SimpleResourcePropertyMetaData.CURRENT_TIME, this);
        this.propSet.add(prop);

        prop = new ReflectionResourceProperty(VALUE_RP, this);
        this.propSet.add(prop);
    }

    public ResourcePropertySet getResourcePropertySet() {
        return this.propSet;
    }

    public void setTerminationTime(Calendar time) {
        this.terminationTime = time;
    }

    public Calendar getTerminationTime() {
        return this.terminationTime;
    }

    public Calendar getCurrentTime() {
        return Calendar.getInstance();
    }

    public int[] getValue() {
        return this.values;
    }

    public void setValue(int[] newValues) {
        this.values = newValues;
    }

}
