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
package org.globus.wsrf.samples.counter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.ResourceHomeImpl;
import org.globus.wsrf.impl.SimpleResourceKey;

public class CounterHome extends ResourceHomeImpl {

    static Log logger =
        LogFactory.getLog(CounterHome.class.getName());

    public ResourceKey create() throws Exception {
        Counter counter = (Counter)createNewInstance();
        counter.create();
        ResourceKey key = new SimpleResourceKey(keyTypeName,
                                                counter.getID());
        add(key, counter);
        return key;
    }

}
