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
package org.globus.interop.widget;

import java.util.Calendar;

import javax.xml.namespace.QName;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceIdentifier;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.ResourceLifetime;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.impl.SimpleResourcePropertyMetaData;

/**
 * Resource Implementation
 */
public class Widget
    implements Resource,
               ResourceProperties,
               ResourceIdentifier,
               ResourceLifetime {

    public static final QName RP_SET =
        new QName("http://widgets.com", "Widget");

    public static final QName FOO =
        new QName("http://widgets.com", "foo");

    private ResourcePropertySet propSet;

    // this is the only persistent field
    private Calendar terminationTime = null;
    private String [] strings = null;
    private Object key = null;
    
    protected void initialize(Object key) {
        if (key== null) {
            throw new IllegalArgumentException("id is requried");
        }
        this.key = key;
        this.propSet = new SimpleResourcePropertySet(RP_SET);
        ResourceProperty prop = null;

        try {
            prop = new ReflectionResourceProperty(SimpleResourcePropertyMetaData.TERMINATION_TIME, this);
            this.propSet.add(prop);

            prop =
                new ReflectionResourceProperty(SimpleResourcePropertyMetaData.CURRENT_TIME,
                                               this);
            this.propSet.add(prop);

            prop =
                new ReflectionResourceProperty(FOO, "Foo", this);
            this.propSet.add(prop);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Object create() {
        // this will work only in memory
        initialize(String.valueOf(hashCode()));
        return this.key;
    }
    
    public Object getID() {
        return this.key;
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

    public String[] getFoo() {
        return this.strings;
    }

    public void setFoo(String [] values) {
        this.strings = values;
    }
}
