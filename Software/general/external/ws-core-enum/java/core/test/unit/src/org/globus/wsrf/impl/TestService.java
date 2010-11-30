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

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import javax.xml.rpc.server.ServiceLifecycle;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.axis.transport.local.LocalTransportUtils;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.tests.basic.CreateResource;
import org.globus.wsrf.tests.basic.CreateResourceResponse;
import org.globus.wsrf.tests.basic.GetInstanceInfo;
import org.globus.wsrf.tests.basic.GetInstanceInfoResponse;
import org.globus.wsrf.tests.basic.NoPermissionFault;
import org.globus.wsrf.tests.basic.ResetNumInstances;
import org.globus.wsrf.tests.basic.ResetNumInstancesResponse;
import org.globus.wsrf.tests.basic.TestLocalInvocation;
import org.globus.wsrf.tests.basic.TestLocalInvocationResponse;
import org.globus.wsrf.tests.basic.TestPortType;
import org.globus.wsrf.tests.basic.service.TestServiceAddressingLocator;
import org.globus.wsrf.utils.AddressingUtils;
import org.oasis.wsrf.lifetime.Destroy;

public class TestService implements ServiceLifecycle {

    public static final String SERVICE_PATH = "TestService";

    private static int numInstances = 0;
    private static int initCalls = 0;
    private static int destroyCalls = 0;

    public TestService() {
        synchronized(TestService.class) {
            numInstances++;
        }
    }

    public void init(Object context)
        throws ServiceException {
        initCalls++;
    }

    public void destroy() {
        destroyCalls++;
    }

    public CreateResourceResponse createResource(CreateResource request)
        throws RemoteException, NoPermissionFault {

        if (request == null) {
            throw new NoPermissionFault();
        }

        ResourceContext ctx = null;
        TestHome home = null;
        ResourceKey key = null;

        try {
            ctx = ResourceContext.getResourceContext();
            home = (TestHome) ctx.getResourceHome();
            key = home.create();
        } catch(RemoteException e) {
            throw e;
        } catch(Exception e) {
            throw new RemoteException("", e);
        }

        EndpointReferenceType epr = null;
        try {
            epr = AddressingUtils.createEndpointReference(ctx, key);
        } catch(Exception e) {
            throw new RemoteException("", e);
        }

        CreateResourceResponse response = new CreateResourceResponse();
        response.setEndpointReference(epr);

        return response;
    }

    public ResetNumInstancesResponse resetNumInstances(ResetNumInstances r)
        throws RemoteException {
        synchronized(TestService.class) {
            numInstances = 0;
            initCalls = 0;
            destroyCalls = 0;
            TestProvider.initialized = false;
        }
        return null;
    }

    public GetInstanceInfoResponse getInstanceInfo(GetInstanceInfo request)
        throws RemoteException {
        GetInstanceInfoResponse response = new GetInstanceInfoResponse();
        response.setInstances(numInstances);
        response.setInitCalls(initCalls);
        response.setDestroyCalls(destroyCalls);
        response.setProviderInit(TestProvider.initialized);
        return response;
    }

    public TestLocalInvocationResponse testLocalInvocation(TestLocalInvocation request) 
        throws RemoteException {
        TestServiceAddressingLocator locator = 
            new TestServiceAddressingLocator();

        URL url = null;
        TestPortType port = null;

        LocalTransportUtils.init();

        try {
            url = new URL("local:///wsrf/services/" + TestService.SERVICE_PATH);
            port = locator.getTestPortTypePort(url);

            LocalTransportUtils.enableLocalTransport((Stub)port);

            CreateResourceResponse createResponse =
                port.createResource(new CreateResource());

            EndpointReferenceType epr = createResponse.getEndpointReference();
            System.out.println(epr);

            port = locator.getTestPortTypePort(epr);

            LocalTransportUtils.enableLocalTransport((Stub)port);
             
            port.destroy(new Destroy());
        } catch (Exception e) {
            throw new RemoteException("invocation failed", e);
        }
        
        TestLocalInvocationResponse response = 
            new TestLocalInvocationResponse();
        return response;
    }

}
