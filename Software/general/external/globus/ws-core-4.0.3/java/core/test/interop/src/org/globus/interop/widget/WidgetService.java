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
package org.globus.interop.widget;

import java.rmi.RemoteException;

import org.apache.axis.message.addressing.EndpointReferenceType;

import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.SimpleResourceKey;
import org.globus.wsrf.utils.AddressingUtils;

import com.widgets.CreateWidget;
import com.widgets.CreateWidgetResponse;

/**
 * Widget service implementation.
 */
public class WidgetService {

    public CreateWidgetResponse createWidget(CreateWidget request)
        throws RemoteException {
        CreateWidgetResponse response = new CreateWidgetResponse();

        EndpointReferenceType epr = null;

        ResourceContext ctx = null;
        WidgetHome home = null;
        Widget resource = null;

        try {
            ctx = ResourceContext.getResourceContext();
            home = (WidgetHome)ctx.getResourceHome();
            resource = home.create();
        } catch (Exception e) {
            throw new RemoteException("", e);
        }

        ResourceKey key = new SimpleResourceKey(home.getKeyTypeName(),
                                                resource.getID());
        try
        {
            epr = AddressingUtils.createEndpointReference(ctx, key);
        }
        catch(Exception e1)
        {
            throw new RemoteException("", e1);
        }

        response.setEndpointReference(epr);
        return response;
    }

}
