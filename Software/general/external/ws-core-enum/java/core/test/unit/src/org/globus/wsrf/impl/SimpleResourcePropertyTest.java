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

import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;

import junit.framework.TestCase;

import org.globus.wsrf.encoding.ObjectSerializer;
import org.w3c.dom.Element;

public class SimpleResourcePropertyTest extends TestCase {
    
    public void testConverstion() throws Exception {
        QName propName = new QName("http:/foo", "IntArray");

        SimpleResourcePropertyMetaData metaData = 
            new SimpleResourcePropertyMetaData(propName,
                                               1, Integer.MAX_VALUE, false, 
                                               Integer.class, false);
        SimpleResourceProperty prop =
            new SimpleResourceProperty(metaData);
        
        assertEquals(0, prop.size());

        Integer testValue1 = new Integer(2);
        Integer testValue2 = new Integer(3);
        Integer testValue3 = new Integer(4);

        // test add
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));
        
        // test get
        assertEquals(3, prop.size());
        assertEquals(testValue1, prop.get(0));
        assertEquals(testValue2, prop.get(1));
        assertEquals(testValue3, prop.get(2));
    }

    public void testNoConverstion() throws Exception {
        QName propName = new QName("http:/foo", "IntArray");

        SimpleResourceProperty prop =
            new SimpleResourceProperty(propName);
        
        assertEquals(0, prop.size());

        Integer testValue1 = new Integer(2);
        Integer testValue2 = new Integer(3);
        Integer testValue3 = new Integer(4);

        // test add
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));
        
        // test get
        assertEquals(3, prop.size());
        assertEquals(testValue1, prop.get(0));
        assertTrue(prop.get(1) instanceof Element);
        assertTrue(prop.get(2) instanceof SOAPElement);
    }

     public void testReadOnly() throws Exception {
        QName propName = new QName("http:/foo", "IntArray");

        SimpleResourcePropertyMetaData metaData = 
            new SimpleResourcePropertyMetaData(propName,
                                               1, Integer.MAX_VALUE, false, 
                                               Integer.class, true);
        SimpleResourceProperty prop =
            new SimpleResourceProperty(metaData);
        
        Integer testValue1 = new Integer(2);
        prop.add(testValue1);

        prop.setEnableValidation(true);
        
        try {
            prop.add(testValue1);
            fail("did not throw expected exception");
        } catch (IllegalStateException e) {
            // that's what we want
        }

        try {
            prop.clear();
            fail("did not throw expected exception");
        } catch (IllegalStateException e) {
            // that's what we want
        }

        try {
            prop.remove(new Integer(1));
            fail("did not throw expected exception");
        } catch (IllegalStateException e) {
            // that's what we want
        }

        try {
            prop.set(0, new Integer(1));
            fail("did not throw expected exception");
        } catch (IllegalStateException e) {
            // that's what we want
        }

        prop.setEnableValidation(false);

        prop.add(new Integer(5));
    }

    public void testMinNillable() throws Exception {

        QName propBarName = new QName("http:/foo", "Bar");
        SimpleResourcePropertyMetaData metaData = null;
        SimpleResourceProperty propBar = null;
        Element [] elements = null;
        SOAPElement [] soapElements = null;
        
        // case 1: returns null if minOccurs == 0
        metaData = 
            new SimpleResourcePropertyMetaData(propBarName, 
                                               0, 
                                               Integer.MAX_VALUE, 
                                               false, 
                                               Object.class, 
                                               false);
        propBar = new SimpleResourceProperty(metaData);
        
        elements = propBar.toElements();
        assertTrue(elements == null);
        soapElements = propBar.toSOAPElements();
        assertTrue(soapElements == null);


        // case 2: returns null even if minOccurs > 0
        metaData = 
            new SimpleResourcePropertyMetaData(propBarName,
                                               1, 
                                               Integer.MAX_VALUE, 
                                               false, 
                                               Object.class, 
                                               false);
        propBar = new SimpleResourceProperty(metaData);

        elements = propBar.toElements();
        assertTrue(elements == null);
        soapElements = propBar.toSOAPElements();
        assertTrue(soapElements == null);


        // case 3: returns <Bar xsi:nil="true"/>
        metaData = 
            new SimpleResourcePropertyMetaData(propBarName,
                                               1, 
                                               Integer.MAX_VALUE, 
                                               true,
                                               Object.class, 
                                               false);
        propBar = new SimpleResourceProperty(metaData);
        
        elements = propBar.toElements();
        assertTrue(elements != null);
        assertEquals(1, elements.length);
        assertEquals("true", elements[0].getAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "nil"));
        
        soapElements = propBar.toSOAPElements();
        assertTrue(soapElements != null);
        assertEquals(1, soapElements.length);
        Iterator iter = soapElements[0].getAllAttributes();
        assertTrue(iter.hasNext());
        assertEquals("true", 
                     soapElements[0].getAttributeValue((Name)iter.next()));
    }

}
