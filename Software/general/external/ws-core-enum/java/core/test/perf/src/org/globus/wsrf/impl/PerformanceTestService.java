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

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI;

import org.globus.wsrf.tests.performance.basic.BaseType;
import org.globus.wsrf.tests.performance.basic.TestPortType;
import org.globus.wsrf.tests.performance.basic.BaseResponse;
import org.globus.wsrf.tests.performance.basic.ResourceDispatchResponse;
import org.globus.wsrf.ResourceContext;

public class PerformanceTestService implements TestPortType

{

    public ResourceDispatchResponse resourceDispatch(BaseType request)
        throws RemoteException
    {
        ResourceContext context = ResourceContext.getResourceContext();        
        context.getResource();

        ResourceDispatchResponse response = new ResourceDispatchResponse();
        try
        {
            response.setEndpointReference(
                new EndpointReferenceType(
                    new URI("http://foo.bar.com/wsrf/services")));
        }
        catch(URI.MalformedURIException e)
        {
            throw new RemoteException("", e);
        }
        return response;
    }

    public BaseResponse baseline(BaseType request) throws RemoteException
    {
        BaseResponse response = new BaseResponse();
        try
        {
            response.setEndpointReference(
                new EndpointReferenceType(
                    new URI("http://foo.bar.com/wsrf/services")));
        }
        catch(URI.MalformedURIException e)
        {
            throw new RemoteException("", e);
        }
        return response;
    }
}
