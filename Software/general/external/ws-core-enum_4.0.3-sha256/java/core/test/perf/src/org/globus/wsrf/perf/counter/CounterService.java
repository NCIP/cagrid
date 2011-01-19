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
package org.globus.wsrf.perf.counter;

import java.rmi.RemoteException;

import org.apache.axis.message.addressing.EndpointReferenceType;

import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.utils.AddressingUtils;

import com.counter.perf.CreateCounter;
import com.counter.perf.CreateCounterResponse;

/**
 * Counter service implementation.
 */
public class CounterService
{

    public CreateCounterResponse createCounter(CreateCounter request)
        throws RemoteException
    {
        ResourceContext ctx = null;
        CounterHome home = null;
        ResourceKey key = null;

        try
        {
            ctx = ResourceContext.getResourceContext();
            home = (CounterHome) ctx.getResourceHome();
            key = home.create();
        }
        catch(RemoteException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            throw new RemoteException("", e);
        }

        EndpointReferenceType epr = null;
        try
        {
            epr = AddressingUtils.createEndpointReference(ctx, key);
        }
        catch(Exception e)
        {
            throw new RemoteException("", e);
        }

        CreateCounterResponse response = new CreateCounterResponse();
        response.setEndpointReference(epr);

        return response;
    }

    public int add(int arg0) throws RemoteException
    {
        Object resource = null;
        try
        {
            resource = ResourceContext.getResourceContext().getResource();
        }
        catch(RemoteException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            throw new RemoteException("", e);
        }
        Counter counter = (Counter) resource;
        synchronized(counter) 
        {
            int result = counter.getValue();
            result += arg0;
            counter.setValue(result);
            return result;
        }
    }
}
