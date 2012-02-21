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

import javax.xml.namespace.QName;

import junit.framework.TestCase;

public class SimpleResourceKeyTest extends TestCase {

    public void testHashCodeAndEquals() throws Exception {

        QName name1 = new QName("http://foo", "bar");
        QName name2 = new QName("http://foo", "bar");

        SimpleResourceKey key1 = new SimpleResourceKey(name1, new Integer(5));
        SimpleResourceKey key2 = new SimpleResourceKey(name2, new Integer(5));
        SimpleResourceKey key3 = new SimpleResourceKey(name2, new Integer(6));

        assertTrue(key1.equals(key2));
        assertTrue(key2.equals(key1));
        assertEquals(key1.hashCode(), key2.hashCode());

        assertFalse(key2.equals(key3));
        assertTrue(key2.hashCode() != key3.hashCode());
    }
}
