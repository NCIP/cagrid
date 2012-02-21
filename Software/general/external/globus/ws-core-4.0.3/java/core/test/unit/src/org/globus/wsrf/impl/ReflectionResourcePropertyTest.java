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

import java.util.Vector;
import java.util.Iterator;
import java.util.Arrays;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.Name;

import org.w3c.dom.Element;

import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertyMetaData;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;

import org.apache.axis.Constants;

import junit.framework.TestCase;

// o add test with beans
public class ReflectionResourcePropertyTest extends TestCase {

    public void testPrimitivesIntArray() throws Exception {
        JavaPrimitivesTestClass t = new JavaPrimitivesTestClass();
        QName propName = new QName("http:/foo", "IntArray");

        ReflectionResourceProperty prop =
            new ReflectionResourceProperty(propName, t);
        
        assertEquals(null, t.getIntArray());
        assertEquals(0, prop.size());

        Integer testValue1 = new Integer(2);
        Integer testValue2 = new Integer(3);
        Integer testValue3 = new Integer(4);

        // test add
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));
        
        assertEquals(3, t.getIntArray().length);
        assertEquals(testValue1.intValue(), t.getIntArray()[0]);
        assertEquals(testValue2.intValue(), t.getIntArray()[1]);
        assertEquals(testValue3.intValue(), t.getIntArray()[2]);

        // test get
        assertEquals(3, prop.size());
        assertEquals(testValue1, prop.get(0));
        assertEquals(testValue2, prop.get(1));
        assertEquals(testValue3, prop.get(2));

        // test set
        Integer testValue4 = new Integer(5);
        prop.set(1, testValue4);
        assertEquals(testValue4.intValue(), t.getIntArray()[1]);
        assertEquals(testValue4, prop.get(1));

        // test remove

        prop.remove(testValue4);
        assertEquals(2, prop.size());
        assertEquals(testValue3.intValue(), t.getIntArray()[1]);
        assertEquals(testValue3, prop.get(1));

        prop.remove(testValue1);
        prop.remove(testValue3);
        assertEquals(0, prop.size());

        // test clear
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));
        prop.clear();
        assertEquals(0, prop.size());
    }

    public void testPrimitivesByteArray() throws Exception {
        JavaPrimitivesTestClass t = new JavaPrimitivesTestClass();
        QName propName = new QName("http:/foo", "ByteArray");
        
        ReflectionResourceProperty prop =
            new ReflectionResourceProperty(propName, t);
        
        assertEquals(null, t.getByteArray());
        assertEquals(0, prop.size());

        Byte testValue1 = new Byte((byte)2);
        Byte testValue2 = new Byte((byte)3);
        Byte testValue3 = new Byte((byte)4);
        
        // test add
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));
        
        assertEquals(3, t.getByteArray().length);
        assertEquals(testValue1.byteValue(), t.getByteArray()[0]);
        assertEquals(testValue2.byteValue(), t.getByteArray()[1]);
        assertEquals(testValue3.byteValue(), t.getByteArray()[2]);

        // test get
        assertEquals(3, prop.size());
        assertEquals(testValue1, prop.get(0));
        assertEquals(testValue2, prop.get(1));
        assertEquals(testValue3, prop.get(2));

        // test set
        Byte testValue4 = new Byte((byte)5);
        prop.set(1, testValue4);
        assertEquals(testValue4.byteValue(), t.getByteArray()[1]);
        assertEquals(testValue4, prop.get(1));

        // test remove

        prop.remove(testValue4);
        assertEquals(2, prop.size());
        assertEquals(testValue3.byteValue(), t.getByteArray()[1]);
        assertEquals(testValue3, prop.get(1));

        prop.remove(testValue1);
        prop.remove(testValue3);
        assertEquals(0, prop.size());

        // test clear
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));
        prop.clear();
        assertEquals(0, prop.size());
    }

    public void testPrimitivesByteArray64() throws Exception {
        JavaPrimitivesTestClass t = new JavaPrimitivesTestClass();
        QName propName = new QName("http:/foo", "ByteArray64");
        
        SimpleResourcePropertyMetaData metaData = 
            new SimpleResourcePropertyMetaData(propName, 
                                               0, 1,
                                               false, 
                                               byte[].class, 
                                               false,
                                               Constants.XSD_BASE64);
        
        ReflectionResourceProperty prop =
            new ReflectionResourceProperty(metaData, t);
        
        assertEquals(null, t.getByteArray64());
        assertEquals(0, prop.size());

        byte [] testValue1 = new byte[]{'1', '2'};
        
        // test add plain 
        prop.add(testValue1);
        
        assertEquals(testValue1.length, t.getByteArray64().length);
        assertTrue(Arrays.equals(testValue1, t.getByteArray64()));

        // test get plain
        assertEquals(1, prop.size());
        assertTrue(Arrays.equals(testValue1, (byte[])prop.get(0)));
                             
        prop.clear();
        assertEquals(0, prop.size());

        byte [] testValue2 = new byte[]{'1', '2', '3'};

        // test add element
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        
        assertEquals(testValue2.length, t.getByteArray64().length);
        assertTrue(Arrays.equals(testValue2, t.getByteArray64()));
        
        // test get element
        assertEquals(1, prop.size());
        assertTrue(Arrays.equals(testValue2, (byte[])prop.get(0)));

        prop.clear();
        assertEquals(0, prop.size());

        byte [] testValue3 = new byte[]{'1', '2', '3', '4'};

        // test add soap element
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));
        
        assertEquals(testValue3.length, t.getByteArray64().length);
        assertTrue(Arrays.equals(testValue3, t.getByteArray64()));
        
        // test get soap element
        assertEquals(1, prop.size());
        assertTrue(Arrays.equals(testValue3, (byte[])prop.get(0)));

        prop.clear();
        assertEquals(0, prop.size());

        // test set
        byte[] testValue4 = new byte[] {'a','b','c'};
        prop.set(0, testValue4);
        assertTrue(Arrays.equals(testValue4, t.getByteArray64()));
        assertTrue(Arrays.equals(testValue4, (byte[])prop.get(0)));

        // test remove
        assertEquals(1, prop.size());
        prop.remove(new byte[]{'b'});
        assertEquals(1, prop.size());
        prop.remove(testValue4);
        assertEquals(0, prop.size());

        prop.clear();
        assertEquals(0, prop.size());
    }

    public void testPrimitives() throws Exception {
        JavaPrimitivesTestClass t = new JavaPrimitivesTestClass();
        QName qname;
        ReflectionResourceProperty prop;
        Object testObject;

        //======== boolean ==========
        qname = new QName("http:/foo", "Boolean");
        prop = new ReflectionResourceProperty(qname, t);

        assertEquals(Boolean.FALSE, prop.get(0));

        boolean testValue1 = true;
        testObject = new Boolean(testValue1);
        t.setBoolean(testValue1);
        assertEquals(testObject, prop.get(0));

        prop.set(0, testObject);
        assertEquals(new Boolean(t.isBoolean()), prop.get(0));

        //======== char ==========
        qname = new QName("http:/foo", "Char");
        prop = new ReflectionResourceProperty(qname, t);

        char testValue2 = 'a';
        testObject = new Character(testValue2);
        t.setChar(testValue2);
        assertEquals(testObject, prop.get(0));

        prop.set(0, testObject);
        assertEquals(new Character(t.getChar()), prop.get(0));


        //======== byte ==========
        qname = new QName("http:/foo", "Byte");
        prop = new ReflectionResourceProperty(qname, t);

        byte testValue3 = 20;
        testObject = new Byte(testValue3);
        t.setByte(testValue3);
        assertEquals(testObject, prop.get(0));

        prop.set(0, testObject);
        assertEquals(new Byte(t.getByte()), prop.get(0));


        //======== short ==========
        qname = new QName("http:/foo", "Short");
        prop = new ReflectionResourceProperty(qname, t);

        short testValue4 = 5;
        testObject = new Short(testValue4);
        t.setShort(testValue4);
        assertEquals(testObject, prop.get(0));

        prop.set(0, testObject);
        assertEquals(new Short(t.getShort()), prop.get(0));


        //======== int ==========
        qname = new QName("http:/foo", "Int");
        prop = new ReflectionResourceProperty(qname, t);

        int testValue5 = 5;
        testObject = new Integer(testValue5);
        t.setInt(testValue5);
        assertEquals(testObject, prop.get(0));

        prop.set(0, testObject);
        assertEquals(new Integer(t.getInt()), prop.get(0));


        //======== long ==========
        qname = new QName("http:/foo", "Long");
        prop = new ReflectionResourceProperty(qname, t);

        long testValue6 = 5;
        testObject = new Long(testValue6);
        t.setLong(testValue6);
        assertEquals(testObject, prop.get(0));

        prop.set(0, testObject);
        assertEquals(new Long(t.getLong()), prop.get(0));


        //======== float ==========
        qname = new QName("http:/foo", "Float");
        prop = new ReflectionResourceProperty(qname, t);

        float testValue7 = 5;
        testObject = new Float(testValue7);
        t.setFloat(testValue7);
        assertEquals(testObject, prop.get(0));

        prop.set(0, testObject);
        assertEquals(new Float(t.getFloat()), prop.get(0));


        //======== double ==========
        qname = new QName("http:/foo", "Double");
        prop = new ReflectionResourceProperty(qname, t);

        double testValue = 5;
        testObject = new Double(testValue);
        t.setDouble(testValue);
        assertEquals(testObject, prop.get(0));

        prop.set(0, testObject);
        assertEquals(new Double(t.getDouble()), prop.get(0));


    }

    public void testSimple() throws Exception {
        TestClass t = new TestClass();
        boolean result;
        QName propBarName = new QName("http:/foo", "Bar");
        ReflectionResourceProperty propBar =
            new ReflectionResourceProperty(propBarName, t);

        assertEquals(5, t.getBar());
        assertEquals(new Integer(5), propBar.get(0));
        assertEquals(1, propBar.size());

        try {
            propBar.set(0, new Integer(10));
            fail("Expected exception");
        } catch (UnsupportedOperationException e) {
            // that's cool
        }

        QName propFooName = new QName("http:/foo", "Foo");
        ReflectionResourceProperty propFoo =
            new ReflectionResourceProperty(propFooName, t);

        assertEquals(null, propFoo.get(0));
        assertEquals(0, propFoo.size());

        String testValue = "abcdef";

        propFoo.set(0, testValue);

        assertEquals(testValue, t.getFoo());
        assertEquals(testValue, propFoo.get(0));
        assertEquals(1, propFoo.size());

        propFoo.clear();
        assertEquals(null, t.getFoo());

        propFoo.set(0, testValue);
        result =
            propFoo.remove(testValue);
        assertEquals(true, result);
        assertEquals(null, t.getFoo());
        assertEquals(null, propFoo.get(0));

        result =
            propFoo.remove(testValue);
        assertEquals(false, result);

        // test data conversion from Element to String
        String testValue2 = "123456";

        propFoo.set(0, ObjectSerializer.toElement(testValue2, propFooName));
        assertEquals(testValue2, t.getFoo());
        assertEquals(testValue2, propFoo.get(0));

        result =
            propFoo.remove(
                ObjectSerializer.toElement(testValue2, propFooName));
        assertEquals(true, result);
        assertEquals(null, t.getFoo());
        assertEquals(null, propFoo.get(0));

        // test data conversion from SOAPElement to String
        String testValue3 = "123456abcdef";

        propFoo.set(0, ObjectSerializer.toSOAPElement(testValue3, propFooName));
        assertEquals(testValue3, t.getFoo());
        assertEquals(testValue3, propFoo.get(0));

        result =
            propFoo.remove(
                ObjectSerializer.toSOAPElement(testValue3, propFooName));
        assertEquals(true, result);
        assertEquals(null, t.getFoo());
        assertEquals(null, propFoo.get(0));
    }

    public void testArray() throws Exception {
        TestClass t = new TestClass();

        QName propName = new QName("http:/foo", "A");
        ReflectionResourceProperty prop =
            new ReflectionResourceProperty(propName, t);

        assertEquals(null, t.getA());
        assertEquals(0, prop.size());

        String testValue1 = "ABCDE";
        String testValue2 = "12345";
        String testValue3 = "45677";

        // test add
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));

        assertEquals(3, t.getA().length);
        assertEquals(testValue1, t.getA()[0]);
        assertEquals(testValue2, t.getA()[1]);
        assertEquals(testValue3, t.getA()[2]);

        // test get
        assertEquals(3, prop.size());
        assertEquals(testValue1, prop.get(0));
        assertEquals(testValue2, prop.get(1));
        assertEquals(testValue3, prop.get(2));

        // test set
        String testValue4 = "BLAH";
        prop.set(1, testValue4);
        assertEquals(testValue4, t.getA()[1]);
        assertEquals(testValue4, prop.get(1));

        // test remove

        prop.remove(testValue4);
        assertEquals(2, prop.size());
        assertEquals(testValue3, t.getA()[1]);
        assertEquals(testValue3, prop.get(1));

        // remove single value at a time
        prop.add(testValue1);
        assertEquals(testValue1, t.getA()[0]);
        assertEquals(testValue1, t.getA()[2]);
        assertEquals(3, prop.size());
        assertTrue(prop.remove(testValue1));
        assertEquals(2, prop.size());
        assertTrue(prop.remove(testValue1));
        assertEquals(1, prop.size());
        // does not exist test case
        assertFalse(prop.remove(testValue1));
        assertEquals(1, prop.size());
        
        // remove at the end case
        prop.remove(testValue3);
        assertEquals(0, prop.size());

        // test clear
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));
        prop.clear();
        assertEquals(0, prop.size());

    }

    public void testList() throws Exception {
        TestClass t = new TestClass();

        QName propName = new QName("http:/foo", "B");
        SimpleResourcePropertyMetaData metaData = 
            new SimpleResourcePropertyMetaData(propName, 
                                               0, Integer.MAX_VALUE,
                                               false, 
                                               String.class, 
                                               false);
        ReflectionResourceProperty prop =
            new ReflectionResourceProperty(metaData, t);

        assertEquals(null, t.getB());
        assertEquals(0, prop.size());

        String testValue1 = "ABCDE";
        String testValue2 = "12345";
        String testValue3 = "45677";

        // test add
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));

        assertEquals(3, t.getB().size());
        assertEquals(testValue1, t.getB().get(0));
        assertEquals(testValue2, t.getB().get(1));
        assertEquals(testValue3, t.getB().get(2));

        // test get
        assertEquals(3, prop.size());
        assertEquals(testValue1, prop.get(0));
        assertEquals(testValue2, prop.get(1));
        assertEquals(testValue3, prop.get(2));

        // test set
        String testValue4 = "BLAH";
        prop.set(1, testValue4);
        assertEquals(testValue4, t.getB().get(1));
        assertEquals(testValue4, prop.get(1));

        // test remove

        prop.remove(testValue4);
        assertEquals(2, prop.size());
        assertEquals(testValue3, t.getB().get(1));
        assertEquals(testValue3, prop.get(1));

        prop.remove(testValue1);
        prop.remove(testValue3);
        assertEquals(0, prop.size());

        // test clear
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));
        prop.clear();
        assertEquals(0, prop.size());
    }

    public void testListMixed() throws Exception {
        TestClass t = new TestClass();

        QName propName = new QName("http:/foo", "B");
        ReflectionResourceProperty prop =
            new ReflectionResourceProperty(propName, t);

        assertEquals(null, t.getB());
        assertEquals(0, prop.size());

        String testValue1 = "ABCDE";
        String testValue2 = "12345";
        String testValue3 = "45677";

        // test add
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));

        assertEquals(3, t.getB().size());
        assertEquals(testValue1, t.getB().get(0));
        assertTrue(t.getB().get(1) instanceof Element);
        assertEquals(testValue2,
                     ObjectDeserializer.toObject((Element)t.getB().get(1),
                                                 String.class));
        assertTrue(t.getB().get(2) instanceof SOAPElement);
        assertEquals(testValue3,
                     ObjectDeserializer.toObject((SOAPElement)t.getB().get(2),
                                                 String.class));

        assertEquals(testValue1, prop.get(0));
        assertTrue(prop.get(1) instanceof Element);
        assertEquals(testValue2,
                     ObjectDeserializer.toObject((Element)prop.get(1),
                                                 String.class));
        assertTrue(prop.get(2) instanceof SOAPElement);
        assertEquals(testValue3,
                     ObjectDeserializer.toObject((SOAPElement)prop.get(2),
                                                 String.class));

        // test clear
        prop.clear();
        assertEquals(0, prop.size());
    }

    public void testElementArray() throws Exception {
        TestClass t = new TestClass();

        QName propName = new QName("http:/foo", "C");
        ReflectionResourceProperty prop =
            new ReflectionResourceProperty(propName, t);

        String testValue1 = "ABCDE";
        String testValue2 = "12345";
        String testValue3 = "45677";

        // test add
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));

        Element [] el = t.getC();
        assertEquals(3, el.length);
        assertEquals(testValue1, el[0].getFirstChild().getNodeValue());
        assertEquals(testValue2, el[1].getFirstChild().getNodeValue());
        assertEquals(testValue3, el[2].getFirstChild().getNodeValue());
    }

    public void testSOAPElementArray() throws Exception {
        TestClass t = new TestClass();

        QName propName = new QName("http:/foo", "D");
        ReflectionResourceProperty prop =
            new ReflectionResourceProperty(propName, t);

        String testValue1 = "ABCDE";
        String testValue2 = "12345";
        String testValue3 = "45677";

        // test add
        prop.add(testValue1);
        prop.add(ObjectSerializer.toElement(testValue2, propName));
        prop.add(ObjectSerializer.toSOAPElement(testValue3, propName));

        Element [] el = t.getD();
        assertEquals(3, el.length);

        for (int i=0;i<el.length;i++) {
            System.out.println(el[i]);
        }

        assertTrue(el[0].toString().indexOf(testValue1) != -1);
        assertTrue(el[1].toString().indexOf(testValue2) != -1);
        assertTrue(el[2].toString().indexOf(testValue3) != -1);
    }

    public void testEmptyString() throws Exception {
        TestClass t = new TestClass();

        QName prop1Name = new QName("http:/foo", "Foo");
        ReflectionResourceProperty prop1 =
            new ReflectionResourceProperty(prop1Name, t);

        // test empty string
        t.setFoo("");
        Element [] elements = prop1.toElements();
        assertTrue(elements != null);
        assertEquals(1, elements.length);
        assertEquals(0, elements[0].getChildNodes().getLength());
        SOAPElement [] soapElements = prop1.toSOAPElements();
        assertTrue(soapElements != null);
        assertEquals(1, soapElements.length);
    }

    public void testNoAccessor() throws Exception {
        TestClass t = new TestClass();
        
        QName prop1Name = new QName("http:/foo", "FooBar");
        
        try {
            ReflectionResourceProperty prop1 =
                new ReflectionResourceProperty(prop1Name, t);
            fail("Expected exception");
        } catch (Exception e) {
            // that's cool - probably need something more specific
        }
    }
    
    public void testMinNillable() throws Exception {
        QName prop1Name = new QName("http:/foo", "Foo");
        testMinNillable(prop1Name);
        QName prop2Name = new QName("http:/foo", "A");
        testMinNillable(prop2Name);
        QName prop3Name = new QName("http:/foo", "B");
        testMinNillable(prop3Name);
    }

    private void testMinNillable(QName propName) 
        throws Exception {
        ResourcePropertyMetaData metaData = null;
        ResourceProperty prop = null;
        Element [] elements = null;
        SOAPElement [] soapElements = null;
        
        TestClass t = new TestClass();
        
        // case 1: returns null if minOccurs == 0
        metaData = new SimpleResourcePropertyMetaData(propName,
                                                      0, 
                                                      Integer.MAX_VALUE, 
                                                      false, 
                                                      Object.class, 
                                                      false);
        prop = new ReflectionResourceProperty(metaData, t);

        elements = prop.toElements();
        assertTrue(elements == null);
        soapElements = prop.toSOAPElements();
        assertTrue(soapElements == null);

        // case 2: returns null when minOccurs > 0
        metaData = new SimpleResourcePropertyMetaData(propName,
                                                      1, 
                                                      Integer.MAX_VALUE, 
                                                      false, 
                                                      Object.class, 
                                                      false);
        prop = new ReflectionResourceProperty(metaData, t);

        elements = prop.toElements();
        assertTrue(elements == null);
        soapElements = prop.toSOAPElements();
        assertTrue(soapElements == null);
        
        // case 3: returns <Bar xsi:nil="true"/>
        metaData = new SimpleResourcePropertyMetaData(propName,
                                                      1, 
                                                      Integer.MAX_VALUE, 
                                                      true,
                                                      Object.class, 
                                                      false);
        prop = new ReflectionResourceProperty(metaData, t);

        elements = prop.toElements();
        assertTrue(elements != null);
        assertEquals(1, elements.length);
        assertEquals("true", elements[0].getAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "nil"));

        soapElements = prop.toSOAPElements();
        assertTrue(soapElements != null);
        assertEquals(1, soapElements.length);
        Iterator iter = soapElements[0].getAllAttributes();
        assertTrue(iter.hasNext());
        assertEquals("true",
                     soapElements[0].getAttributeValue((Name)iter.next()));
    }

    public void testSelfContained() throws Exception {
        QName name = new QName("http:/foo", "Bar");
        RP1 propFoo = new RP1(name);
        
        assertEquals(null, propFoo.get(0));
        assertEquals(0, propFoo.size());

        String testValue = "abcdef";

        propFoo.set(0, testValue);

        assertEquals(testValue, propFoo.getFoo());
        assertEquals(testValue, propFoo.get(0));
        assertEquals(1, propFoo.size());

        propFoo.clear();
        assertEquals(null, propFoo.getFoo());

        propFoo.set(0, testValue);

        boolean result;

        result = propFoo.remove(testValue);
        assertEquals(true, result);
        assertEquals(null, propFoo.getFoo());
        assertEquals(null, propFoo.get(0));

        result = propFoo.remove(testValue);
        assertEquals(false, result);
    }

    class RP1 extends ReflectionResourceProperty {
        
        private String foo;

        public RP1(QName name) throws Exception {
            super(new SimpleResourcePropertyMetaData(name));
            // all of these must be called
            setObject(this);
            setPropertyName("Foo");
            initialize();
        }
        
        public String getFoo() {
            return this.foo;
        }
        
        public void setFoo(String value) {
            this.foo = value;
        }
        
    }

    class TestClass {

        private String foo;
        private String [] strArray;
        private Vector strList;
        private Element [] elements;
        private SOAPElement [] soapElements;

        public String getFoo() {
            return this.foo;
        }

        public void setFoo(String value) {
            this.foo = value;
        }

        public int getBar() {
            return 5;
        }

        public String[] getA() {
            return this.strArray;
        }

        public void setA(String [] value) {
            this.strArray = value;
        }

        public Vector  getB() {
            return this.strList;
        }

        public void setB(Vector list) {
            this.strList = list;
        }

        public Element[] getC() {
            return this.elements;
        }

        public void setC(Element [] value) {
            this.elements = value;
        }

        public SOAPElement[] getD() {
            return this.soapElements;
        }

        public void setD(SOAPElement [] value) {
            this.soapElements = value;
        }
    }

    class JavaPrimitivesTestClass {

        private boolean booleanP = Boolean.FALSE.booleanValue();
        private short shortP;
        private long longP;
        private int intP;
        private double doubleP;
        private float floatP;
        private byte byteP;
        private char charP;
        private int[] intArray;
        private byte[] byteArray;
        private byte[] byteArray64;

        public boolean isBoolean() {
            return this.booleanP;
        }

        public void setBoolean(boolean value) {
            this.booleanP = value;
        }

        public short getShort() {
            return this.shortP;
        }

        public void setShort(short value) {
            this.shortP = value;
        }

        public long getLong() {
            return this.longP;
        }

        public void setLong(long value) {
            this.longP = value;
        }

        public int getInt() {
            return this.intP;
        }

        public void setInt(int value) {
            this.intP = value;
        }

        public double getDouble() {
            return this.doubleP;
        }

        public void setDouble(double value) {
            this.doubleP = value;
        }

        public float getFloat() {
            return this.floatP;
        }

        public void setFloat(float value) {
            this.floatP = value;
        }

        public byte getByte() {
            return this.byteP;
        }

        public void setByte(byte value) {
            this.byteP = value;
        }

        public char getChar() {
            return this.charP;
        }

        public void setChar(char value) {
            this.charP = value;
        }

        public int[] getIntArray() {
            return this.intArray;
        }
        
        public void setIntArray(int[] values) {
            this.intArray = values;
        }

        public byte[] getByteArray() {
            return this.byteArray;
        }

        public void setByteArray(byte[] values) {
            this.byteArray = values;
        }

        public byte[] getByteArray64() {
            return this.byteArray64;
        }
        
        public void setByteArray64(byte[] values) {
            this.byteArray64 = values;
        }
        
    }


}
