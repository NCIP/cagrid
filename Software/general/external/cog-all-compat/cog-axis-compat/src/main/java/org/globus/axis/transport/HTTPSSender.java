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

import java.io.IOException;
import org.apache.axis.MessageContext;
import org.apache.axis.components.net.BooleanHolder;
import org.apache.axis.transport.http.SocketHolder;
import org.apache.axis.transport.http.HTTPSender;

import org.globus.gsi.gssapi.net.GssSocket;

/**
 * This is meant to be used on a SOAP Client to call a SOAP server.
 * <BR><I>This code is based on Axis HTTPSender.java code.</I>
 */
public class HTTPSSender extends HTTPSender {

    protected void getSocket(SocketHolder sockHolder,
                             MessageContext msgContext,
                             String protocol,
                             String host, int port, int timeout, 
                             StringBuffer otherHeaders, 
                             BooleanHolder useFullURL)
        throws Exception {

        if (!protocol.equalsIgnoreCase("https")) {
	    throw new IOException("Invalid protocol");
	}

        int lport = (port == -1) ? 8443 : port;

        SSLContextHelper helper = new SSLContextHelper(msgContext,
                                                       host,
                                                       lport);
                                                       
        super.getSocket(sockHolder, msgContext, "http", host,
                        lport, timeout, otherHeaders, useFullURL);

        
        GssSocket gsiSocket = helper.wrapSocket(sockHolder.getSocket());

        sockHolder.setSocket(gsiSocket);
    }
    
}