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

import org.globus.wsrf.RemoveNotSupportedException;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceKey;

public class TestHome extends ResourceHomeImpl {

    public static QName KEY_TYPE_NAME =
        new QName(TestResource.TEST_NS, "TestKey");

    public static ResourceKey BAD_KEY =
        new SimpleResourceKey(KEY_TYPE_NAME, "111111111111111111");

    public static ResourceKey TEST_KEY =
        new SimpleResourceKey(KEY_TYPE_NAME, "9999");

    public TestHome() {
        this.keyTypeName = KEY_TYPE_NAME;
    }

    public ResourceKey create() throws Exception {
        TestResource t = new TestResource();
        ResourceKey key = new SimpleResourceKey(keyTypeName,
                                                String.valueOf(t.hashCode()));
        this.add(key, t);
        return key;
    }

    public void remove(ResourceKey key)
        throws ResourceException {
        if (TEST_KEY.getValue().equals(key.getValue())) {
            throw new RemoveNotSupportedException();
        } else {
            super.remove(key);
        }
    }
}
