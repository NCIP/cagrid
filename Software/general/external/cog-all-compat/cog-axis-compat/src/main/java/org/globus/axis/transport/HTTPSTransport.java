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

/**
 * Extends Transport by implementing the setupMessageContext function to
 * set HTTP-specific message context fields and transport chains.
 * May not even be necessary if we arrange things differently somehow.
 * Can hold state relating to URL properties.
 * <BR><I>This code is based on Axis HTTPTransport.java code.</I>
 */
public class HTTPSTransport extends GSIHTTPTransport
{
    public static final String DEFAULT_TRANSPORT_NAME = "https";

    public HTTPSTransport () {
        transportName = DEFAULT_TRANSPORT_NAME;
    }
    
    /**
     * helper constructor
     */
    public HTTPSTransport (String url, String action) {
        super(url, action);
        transportName = DEFAULT_TRANSPORT_NAME;
    }
}
