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
package org.globus.wsrf.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class TestFactory implements ObjectFactory
{
    private String baseString = null;
    private static int counter = 0;
    
    /* (non-Javadoc)
     * @see javax.naming.spi.ObjectFactory#getObjectInstance(java.lang.Object, javax.naming.Name, javax.naming.Context, java.util.Hashtable)
     */
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
        Hashtable environment) throws Exception
    {
        Reference reference = (Reference) obj;
        RefAddr baseStringAddr = reference.get("baseString");
        this.baseString = baseStringAddr.getContent().toString();
        return this.baseString + " " + String.valueOf(counter++);
    }

    /**
     * @return Returns the baseString.
     */
    public String getBaseString()
    {
        return baseString;
    }

    /**
     * @param baseString The baseString to set.
     */
    public void setBaseString(String baseString)
    {
        this.baseString = baseString;
    }
}
