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
package org.globus.wsrf.impl.security.descriptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.wsrf.config.ConfigException;

public class TestContainerSecurityConfig extends ContainerSecurityConfig {

    private static Log logger =
        LogFactory.getLog(TestContainerSecurityConfig.class.getName());

    protected TestContainerSecurityConfig(String descFile) {
        super(descFile);
    }

    public static void initContainerConfig(String fileName) 
        throws ConfigException {
        securityConfig = null;
        ContainerSecurityConfig.getConfig(fileName);
    }

}
