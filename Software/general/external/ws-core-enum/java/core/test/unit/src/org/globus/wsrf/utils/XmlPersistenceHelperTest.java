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
package org.globus.wsrf.utils;

import java.util.List;
import java.util.Iterator;
import java.util.HashMap;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.SimpleResourceKey;
import org.globus.wsrf.impl.ReflectionResource;

import org.globus.wsrf.tests.basic.TestRP;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

public class XmlPersistenceHelperTest extends TestCase {

    private static final QName KEY = 
        new QName("http://globus.org", "TestKey");
    
    private static final QName ELEM =
        new QName("http://globus.org", "TestElement");
    
    private static final ResourceKey KEY_1 = 
        new SimpleResourceKey(KEY, "1");

    private static final ResourceKey KEY_2 = 
        new SimpleResourceKey(KEY, "2");

    public void testStore() throws Exception {
        
        TestRP rp = new TestRP();
        rp.setValue(5);
        
        XmlPersistenceHelper helper =
            new XmlPersistenceHelper(TestRP.class);
        
        helper.store(KEY_1, rp, ELEM);
        
        TestRP rp2 = (TestRP)helper.load(KEY_1);
        
        assertEquals(5, rp2.getValue());
    }

    public void testReflectionResource() throws Exception {
        TestRP rp = new TestRP();
        rp.setValue(5);
        
        XmlPersistenceHelper helper =
            new XmlPersistenceHelper(TestRP.class);

        helper.store(KEY_2, rp, ELEM);

        ReflectionResource resource = new ReflectionResource();

        helper.load(KEY_2, resource);
        
        assertTrue(resource.getResourceBean() != null);
        assertTrue(resource.getResourceBean() instanceof TestRP);
        assertEquals(5, ((TestRP)resource.getResourceBean()).getValue());
        
        assertTrue(resource.getResourcePropertySet() != null);
        assertEquals(ELEM, resource.getResourcePropertySet().getName());
    }
    
    public void testList() throws Exception {

        XmlPersistenceHelper helper =
            new XmlPersistenceHelper(TestRP.class);

        ResourceKey [] keys = new ResourceKey[]{KEY_1, KEY_2};

        listTest(helper, keys);
    }

    public void testDelete() throws Exception {

        XmlPersistenceHelper helper =
            new XmlPersistenceHelper(TestRP.class);

        listTest(helper, new ResourceKey[]{KEY_1, KEY_2});
        
        helper.remove(KEY_1);
        
        listTest(helper, new ResourceKey[]{KEY_2});
    }

    public void testRemoveAll() throws Exception {

        XmlPersistenceHelper helper =
            new XmlPersistenceHelper(TestRP.class);

        listTest(helper, new ResourceKey[]{KEY_1, KEY_2});
        
        helper.removeAll();
        
        List list = helper.list();
        assertTrue(list != null);
        assertTrue(list.isEmpty());
    }

    private  void listTest(XmlPersistenceHelper helper, 
                           ResourceKey[] keys) throws Exception {
        TestRP rp = new TestRP(10);
        
        for (int i=0;i<keys.length;i++) {
            helper.store(keys[i], rp, ELEM);
        }
        
        List list = helper.list();
        assertTrue(list != null);
        assertEquals(keys.length, list.size());
        HashMap listKeys = new HashMap();
        Iterator iter = list.iterator();
        while(iter.hasNext()) {
            listKeys.put(iter.next(), "");
        }
        assertEquals(keys.length, listKeys.size());
        for (int i=0;i<keys.length;i++) {
            assertTrue( listKeys.get(keys[i].getValue()) != null );
        }
    }

}
