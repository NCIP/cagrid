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
package org.globus.axis.transport;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Call;
import org.apache.axis.client.Transport;

import org.apache.axis.transport.http.HTTPConstants;

import org.globus.axis.gsi.GSIConstants;

/**
 * Extends Transport by implementing the setupMessageContext function to
 * set HTTP-specific message context fields and transport chains.
 * May not even be necessary if we arrange things differently somehow.
 * Can hold state relating to URL properties.
 * <BR><I>This code is based on Axis HTTPTransport.java code.</I>
 */
public class GSIHTTPTransport extends Transport implements GSIConstants
{
    public static final String DEFAULT_TRANSPORT_NAME = "httpg";
    
    /**
     * HTTP properties
     */
    public static final String URL = MessageContext.TRANS_URL;

    private String cookie;
    private String cookie2;
    private String action;
    
    public GSIHTTPTransport () {
        transportName = DEFAULT_TRANSPORT_NAME;
    }
    
    /**
     * helper constructor
     */
    public GSIHTTPTransport (String url, String action)
    {
        transportName = DEFAULT_TRANSPORT_NAME;
        this.url = url;
        this.action = action;
    }
    
    /**
     * Set up any transport-specific derived properties in the message context.
     * @param mc the context to set up
     * @param call the client service instance
     * @param engine the engine containing the registries
     * @throws AxisFault if service cannot be found
     */
    public void setupMessageContextImpl(MessageContext mc,
                                        Call call,
                                        AxisEngine engine)
        throws AxisFault
    {
        if (action != null) {
            mc.setUseSOAPAction(true);
            mc.setSOAPActionURI(action);
        }

        // Set up any cookies we know about
        if (cookie != null)
            mc.setProperty(HTTPConstants.HEADER_COOKIE, cookie);
        if (cookie2 != null)
            mc.setProperty(HTTPConstants.HEADER_COOKIE2, cookie2);

        // Allow the SOAPAction to determine the service, if the service
        // (a) has not already been determined, and (b) if a service matching
        // the soap action has been deployed.
        if (mc.getService() == null) {
            mc.setTargetService( (String)mc.getSOAPActionURI() );
        }
    }

    public void processReturnedMessageContext(MessageContext context) {
        cookie = context.getStrProp(HTTPConstants.HEADER_COOKIE);
        cookie2 = context.getStrProp(HTTPConstants.HEADER_COOKIE2);
    }
}
