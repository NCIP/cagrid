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
package org.globus.tomcat.coyote.valves;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import org.apache.catalina.valves.ValveBase;

import org.ietf.jgss.GSSContext;

import org.globus.tomcat.catalina.net.HTTPSSocket;
import org.globus.axis.gsi.GSIConstants;

import org.apache.coyote.InputBuffer;
import org.apache.coyote.http11.InternalInputBuffer;

public final class HTTPSValve55 extends ValveBase {

    protected static final String info =
        "org.globus.tomcat.catalina.valves.HTTPSTransportValve/1.0";
    
    public String getInfo() {
        return (info);
    }

    public void invoke(Request request, Response response) 
        throws IOException, ServletException {
        // Expose the security parameters
        expose(request);

        // Invoke the next Valve in our Pipeline
        getNext().invoke(request, response);
    }

    private HTTPSSocket getSocketFromInputStream(
                           org.apache.coyote.Request request) {
        InputBuffer inputBuffer = request.getInputBuffer();
        System.out.println("AT1 getSocketFromInputStream");
        if (inputBuffer instanceof InternalInputBuffer) {
            InternalInputBuffer internalInputBuffer = (InternalInputBuffer)inputBuffer;
            if (internalInputBuffer.getInputStream() 
                instanceof HTTPSSocket.SocketGSIGssInputStream) {
                HTTPSSocket.SocketGSIGssInputStream in = 
                    (HTTPSSocket.SocketGSIGssInputStream)internalInputBuffer.getInputStream();
                System.out.println("AT2 getSocketFromInputStream");
                return in.getSocket();
            }
        }
        System.out.println("AT3 getSocketFromInputStream");
        return null;
    }

    protected void expose(Request request) {
        HTTPSSocket socket = 
            getSocketFromInputStream(request.getCoyoteRequest());
        if (socket != null) {
            // fix scheme name
            request.getCoyoteRequest().scheme().setString("https");
            
            String globusID = socket.getUserDN();
            if (globusID != null) {
                request.getRequest().setAttribute(GSIConstants.GSI_USER_DN,
                                                  globusID);
            }
        
            GSSContext context = socket.getContext();
            request.getRequest().setAttribute(GSIConstants.GSI_CONTEXT, 
                                              context);
        }
    }


}
