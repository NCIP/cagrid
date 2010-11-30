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
package org.globus.wsrf.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.globus.axis.util.Util;
import org.globus.wsrf.container.ServiceContainer;

public class GSITestContainer extends TestContainer {
    static Log logger = LogFactory.getLog(GSITestContainer.class.getName());

    protected static final String SECURE_WEB_SERVER_URL = 
        "secure.web.server.url";

    static {
        Util.registerTransport();
    }

    protected String getPropertySeverUrl() {
        return System.getProperty(SECURE_WEB_SERVER_URL);
    }

    public GSITestContainer()
    {
        super();
        this.properties.put(ServiceContainer.CLASS,
                            "org.globus.wsrf.container.GSIServiceContainer");
    }

    public GSITestContainer(String containerConfigFile)
    {
        super(containerConfigFile);
        this.properties.put(ServiceContainer.CLASS,
                            "org.globus.wsrf.container.GSIServiceContainer");
    }

}
