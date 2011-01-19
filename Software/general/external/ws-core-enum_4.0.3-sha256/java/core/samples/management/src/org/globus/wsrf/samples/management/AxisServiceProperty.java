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

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.axis.deployment.wsdd.WSDDConstants;

import org.globus.wsrf.ResourcePropertyMetaData;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.SerializationException;
import org.globus.wsrf.impl.SimpleResourcePropertyMetaData;
import org.globus.wsrf.impl.BaseResourceProperty;

import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

public class AxisServiceProperty extends BaseResourceProperty {

    private AxisService service;
    private String propertyName;
    private Map map;
    private String separator;

    public AxisServiceProperty(AxisService service,
                               String propertyName) {
        this(service, propertyName, null);
    }

    public AxisServiceProperty(AxisService service, 
                               String propertyName, 
                               String separator) {
        super(getMetaData(new QName(WSDDConstants.URI_WSDD, propertyName), 
                          separator));
        this.service = service;
        this.propertyName = propertyName;
        this.separator = separator;
        
        this.map = service.getWSDDService().getParametersTable();
    }

    private static ResourcePropertyMetaData getMetaData(QName rpName,
                                                        String separator) {
        return new SimpleResourcePropertyMetaData(
                          rpName,
                          0,
                          (separator == null) ? 1 : Integer.MAX_VALUE,
                          false,
                          String.class,
                          false);
    }

    public void add(Object value) {
        String currentValue = (String)this.map.get(this.propertyName);
        if (currentValue == null) {
            put(value);
            return;
        }
        if (this.separator == null) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            currentValue = currentValue + this.separator + convert(value);
            put(currentValue);
        }
    }
    
    public boolean remove(Object value) {
        String currentValue = (String)this.map.get(this.propertyName);
        if (currentValue == null) {
            return false;
        }
        value = convert(value);
        if (this.separator == null) {
            if (currentValue.equals(value)) {
                remove();
                return true;
            } else {
                return false;
            }
        } else {
            List values = toList(currentValue);
            if (values.remove(value)) {
                put(toString(values));
                return true;
            } else {
                return false;
            }
        }
    }
    
    public Object get(int index) {
        String currentValue = (String)this.map.get(this.propertyName);
        if (this.separator != null) {
            currentValue = (String)toList(currentValue).get(index);
        }
        return currentValue;
    }
    
    public void set(int index, Object value) {
        if (this.separator == null) {
            if (index != 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            put(value);
        } else {
            String currentValue = (String)this.map.get(this.propertyName);
            List values = toList(currentValue);
            values.set(index, convert(value));
            put(toString(values));
        }
    }

    public void clear() {
        remove();
    }

    public int size() {
        String currentValue = (String)this.map.get(this.propertyName);
        if (this.separator == null) {
            return (currentValue == null) ? 0 : 1;
        } else {
            if (currentValue == null) {
                return 0;
            } else {
                return toList(currentValue).size();
            }
        }
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public Iterator iterator() {
        List list = null;
        String currentValue = (String)this.map.get(this.propertyName);
        if (this.separator == null) {
            if (currentValue == null) {
                list = new ArrayList(0);
            } else {
                list = new ArrayList(1);
                list.add(currentValue);
            }
        } else {
            if (currentValue == null) {
                list = new ArrayList(0);
            } else {
                list = toList(currentValue);
            }
        }
        return list.iterator();
    }

    private void remove() {
        service.undeploy();
        this.map.remove(this.propertyName);
        service.deploy();
    }
    
    private void put(Object newValue) {
        service.undeploy();
        this.map.put(this.propertyName, convert(newValue));
        service.deploy();
    }

    private List toList(String value) {
        List values = new ArrayList();
        if (value != null) {
            StringTokenizer tokens = 
                new StringTokenizer(value, this.separator);
            while(tokens.hasMoreTokens()) {
                values.add(tokens.nextToken());
            }
        }
        return values;
    }
    
    private String toString(List values) {
        StringBuffer buf = new StringBuffer();
        for (int i=0;i<values.size();i++) {
            buf.append(values.get(i));
            if (i+1<values.size()) {
                buf.append(this.separator);
            }
        }
        return buf.toString();
    }
    
    public SOAPElement[] toSOAPElements()
        throws SerializationException {
        if (this.separator == null) {
            return toSOAPElementSingle();
        } else {
            return toSOAPElementMultiple();
        }
    }

    private SOAPElement[] toSOAPElementSingle()
        throws SerializationException {
        String currentValue = (String)this.map.get(this.propertyName);
        SOAPElement [] elements = null;
        boolean nillable = this.metaData.isNillable();
        QName name = this.metaData.getName();
        if (currentValue == null) {
            if (nillable) {
                elements = new SOAPElement[1];
                elements[0] = ObjectSerializer.toSOAPElement(currentValue,
                                                             name,
                                                             nillable);
            }
        } else {
            elements = new SOAPElement[1];
            elements[0] = ObjectSerializer.toSOAPElement(currentValue,
                                                         name,
                                                         nillable);
        }
        return elements;
    }

    private SOAPElement[] toSOAPElementMultiple()
        throws SerializationException {
        String currentValue = (String)this.map.get(this.propertyName);
        SOAPElement [] elements = null;
        boolean nillable = this.metaData.isNillable();
        QName name = this.metaData.getName();
        if (currentValue == null) {
            if (nillable) {
                elements = new SOAPElement[1];
                elements[0] = ObjectSerializer.toSOAPElement(null,
                                                             name,
                                                             nillable);
            }
        } else {
            List list = toList(currentValue);
            elements = new SOAPElement[list.size()];
            Iterator iter = list.iterator();
            int i = 0;
            while(iter.hasNext()) {
                Object value = iter.next();
                elements[i++] = ObjectSerializer.toSOAPElement(value, 
                                                               name,
                                                               nillable);
            }
        }
        return elements;
    }

    public Element[] toElements()
        throws SerializationException {
        if (this.separator == null) {
            return toElementSingle();
        } else {
            return toElementMultiple();
        }
    }

    private Element[] toElementSingle() 
        throws SerializationException {
        String currentValue = (String)this.map.get(this.propertyName);
        Element [] values = null;
        boolean nillable = this.metaData.isNillable();
        QName name = this.metaData.getName();
        if (currentValue == null) {
            if (nillable) {
                values[0] = ObjectSerializer.toElement(currentValue,
                                                       name,
                                                       nillable);
            }
        } else {
            values = new Element[1];
            values[0] = ObjectSerializer.toElement(currentValue,
                                                   name,
                                                   nillable);
        }
        return values;
    }
    
    private Element[] toElementMultiple() 
        throws SerializationException {
        String currentValue = (String)this.map.get(this.propertyName);
        Element [] values = null;
        boolean nillable = this.metaData.isNillable();
        QName name = this.metaData.getName();
        if (currentValue == null) {
            if (nillable) {
                values = new Element[1];
                values[0] = ObjectSerializer.toElement(null,
                                                       name,
                                                       nillable);
            }
        } else {
            List list = toList(currentValue);
            values = new Element[list.size()];
            Iterator iter = list.iterator();
            int i=0;
            while(iter.hasNext()) {
                Object value = iter.next();
                values[i++] = ObjectSerializer.toElement(value, 
                                                         name,
                                                         nillable);
            }
        }
        return values;
    }
    
}
