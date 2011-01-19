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

import org.globus.wsrf.ResourceKey;

public class PerformanceTestHome extends ResourceHomeImpl
{
    public static ResourceKey TEST_KEY = new SimpleResourceKey(
        new QName("http://wsrf.globus.org/tests/performance/basic",
                  "PerformanceTestKey"), 
        "foo");

    public synchronized void initialize() throws Exception
    {
        super.initialize();
        this.add(TEST_KEY, new PerformanceTestResource());
    }
}
