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
package org.globus.wsrf.container;

import junit.framework.TestCase;

public class LockManagerTest extends TestCase {

    public LockManagerTest(String name) {
        super(name);
    }

    public void testGetLocks() throws Exception {
        LockManager l = new LockManager();
        
        Lock l1 = l.getLock(new String("A"));
        Lock l2 = l.getLock(new String("A"));
        
        assertTrue(l1 == l2);
    }

}
