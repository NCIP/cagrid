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
package org.globus.wsrf.providers;

import javax.crypto.SecretKey;

import org.ietf.jgss.GSSContext;

public class GSSKey implements SecretKey
{
    private byte[] value;
    private GSSContext context = null;

    public GSSKey(String contextId, GSSContext context)
    {
        this.value = contextId.getBytes();
        this.context = context;
    }

    public GSSContext getContext()
    {
        return context;
    }

    public void setContext(GSSContext context)
    {
        this.context = context;
    }

    public byte[] getEncoded()
    {
        return this.value;
    }

    public String getAlgorithm()
    {
        return null;
    }

    public String getFormat()
    {
        return null;
    }
}
