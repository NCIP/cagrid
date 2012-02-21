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
package org.globus.wsrf.container;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.axis.MessageContext;

import junit.framework.TestCase;

public class ServiceHostTest extends TestCase {

    static Log logger =
        LogFactory.getLog(ServiceHostTest.class.getName());

    public ServiceHostTest(String name) {
        super(name);
    }

    public void testPort() throws Exception {
        String url;
        MessageContext ctx;

        url = "http://localhost:843/ogsa/services/core/registry/handle";
        ctx = getContext(url);
        assertEquals(843, ServiceHost.getPort(ctx));

        url = "http://localhost/ogsa/services/core/registry/handle";
        ctx = getContext(url);
        assertEquals(80, ServiceHost.getPort(ctx));
    }

    public void testProtocol() throws Exception {
        String url;
        MessageContext ctx;

        url = "http://localhost:843/ogsa/services/core/registry/handle";
        ctx = getContext(url);
        assertEquals("http", ServiceHost.getProtocol(ctx));
    }

    private MessageContext getContext(String url) {
        MessageContext ctx = new MessageContext(null);
        ctx.setProperty(MessageContext.TRANS_URL, url);
        return ctx;
    }
}
