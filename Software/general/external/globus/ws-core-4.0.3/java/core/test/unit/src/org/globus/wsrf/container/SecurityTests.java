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

import org.globus.wsrf.test.GridTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SecurityTests extends GridTestSuite
{
    public static int timeout =  1000 * 60 * 2;

    public SecurityTests(String name)
    {
        super(name, true);
    }

    public static Test suite() throws Exception
    {
        TestSuite suite = new SecurityTests("SecureServiceContainerTest");
        suite.addTestSuite(GSIServiceContainerTest.class);
        return suite;
    }
}
