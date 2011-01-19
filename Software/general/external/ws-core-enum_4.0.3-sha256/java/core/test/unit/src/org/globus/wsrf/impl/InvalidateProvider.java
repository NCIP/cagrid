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

import java.rmi.RemoteException;

import org.globus.wsrf.InvalidateResourceMapping;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceHome;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.tests.invalidate.InvalidateRequest;
import org.globus.wsrf.tests.invalidate.InvalidateResponse;

public class InvalidateProvider
{
    public InvalidateResponse invalidate(InvalidateRequest request)
        throws RemoteException
    {
        ResourceHome home = null;
        ResourceKey key = null;

        try
        {
            ResourceContext ctx = ResourceContext.getResourceContext();
            home = ctx.getResourceHome();
            key = ctx.getResourceKey();
        }
        catch(RemoteException re)
        {
            throw re;
        }
        catch(Exception e)
        {
            throw new RemoteException("", e);
        }

        if(home instanceof InvalidateResourceMapping)
        {
            try 
            {
                ((InvalidateResourceMapping) home).invalidate(key);
            } 
            catch (Exception e) 
            {
                throw new RemoteException("Invalidate failed", e);
            }
        }
        else
        {
            throw new RemoteException(
                "ResourceHome does not implement invalidate method");
        }
        
        return new InvalidateResponse();
    }
}
