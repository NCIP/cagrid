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
package org.globus.wsrf.impl.security;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.ResourceHomeImpl;
import org.globus.wsrf.impl.SimpleResourceKey;

public class SecurityTestResourceHome extends ResourceHomeImpl {

    public ResourceKey create() throws Exception {

        SecurityTestResource res = new SecurityTestResource();
        ResourceKey key =
            new SimpleResourceKey(keyTypeName,
                                  Integer.toString(res.hashCode()));
        this.add(key, res);
        return key;
    }
}
