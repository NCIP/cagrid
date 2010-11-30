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
package org.globus.wsrf.jndi;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.naming.Context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.globus.wsrf.jndi.JNDIUtils;

public class JNDITest extends TestCase
{
    private static Context initialContext = null;

    private static final String TEST_CONFIG =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<jndiConfig xmlns=\"http://wsrf.globus.org/jndi/config\">\r\n" +
        "    <global>\r\n" +
        "        <environment name=\"test1\" \r\n" +
        "                     type=\"java.lang.String\"\r\n" +
        "                     value=\"testString\"/>\r\n" +
        "        <environment name=\"test3\" \r\n" +
        "                     type=\"java.lang.Byte\"\r\n" +
        "                     value=\"111\"/>\r\n" +
        "        <environment name=\"test4\" \r\n" +
        "                     type=\"java.lang.Character\"\r\n" +
        "                     value=\"q\"/>\r\n" +
        "        <environment name=\"test5\" \r\n" +
        "                     type=\"java.lang.Double\"\r\n" +
        "                     value=\"1.12345\"/>\r\n" +
        "        <environment name=\"test6\" \r\n" +
        "                     type=\"java.lang.Float\"\r\n" +
        "                     value=\"1.12345\"/>\r\n" +
        "        <environment name=\"test7\" \r\n" +
        "                     type=\"java.lang.Integer\"\r\n" +
        "                     value=\"12345\"/>\r\n" +
        "        <environment name=\"test8\" \r\n" +
        "                     type=\"java.lang.Long\"\r\n" +
        "                     value=\"12345\"/>\r\n" +
        "        <environment name=\"test9\" \r\n" +
        "                     type=\"java.lang.Short\"\r\n" +
        "                     value=\"12345\"/>\r\n" +
        "        <resource name=\"testResource1\" \r\n" +
        "                     type=\"java.lang.String\"\r\n" +
        "                     description=\"Produces test strings\">\r\n" +
        "            <resourceParams>\r\n" +
        "                <parameter>\r\n" +
        "                    <name>\r\n" +
        "                        factory\r\n" +
        "                    </name>\r\n" +
        "                    <value>\r\n" +
        "                        org.globus.wsrf.jndi.TestFactory\r\n" +
        "                    </value>\r\n" +
        "                </parameter>\r\n" +
        "                <parameter>\r\n" +
        "                    <name>\r\n" +
        "                        baseString\r\n" +
        "                    </name>\r\n" +
        "                    <value>\r\n" +
        "                        Global Test Nr: \r\n" +
        "                    </value>\r\n" +
        "                </parameter>\r\n" +
        "            </resourceParams>\r\n" +
        "        </resource>\r\n" +
        "    </global>\r\n" +
        "    <service name=\"TestService\">\r\n" +
        "        <environment name=\"test2\" \r\n" +
        "                     type=\"java.lang.Boolean\"\r\n" +
        "                     value=\"true\"/>\r\n" +
        "        <resource name=\"testResource2\" \r\n" +
        "                     type=\"java.lang.String\"\r\n" +
        "                     description=\"Produces test strings\">\r\n" +
        "            <resourceParams>\r\n" +
        "                <parameter>\r\n" +
        "                    <name>\r\n" +
        "                        factory\r\n" +
        "                    </name>\r\n" +
        "                    <value>\r\n" +
        "                        org.globus.wsrf.jndi.TestFactory\r\n" +
        "                    </value>\r\n" +
        "                </parameter>\r\n" +
        "                <parameter>\r\n" +
        "                    <name>\r\n" +
        "                        baseString\r\n" +
        "                    </name>\r\n" +
        "                    <value>\r\n" +
        "                        Service Test Nr: \r\n" +
        "                    </value>\r\n" +
        "                </parameter>\r\n" +
        "            </resourceParams>\r\n" +
        "        </resource>\r\n" +
        "        <resourceLink name=\"testResource1\" \r\n" +
        "                     target=\"java:comp/env/testResource1\"/>\r\n" +
        "    </service>\r\n" +
        "</jndiConfig>\r\n";

    public static Test suite()
    {
        return new TestSuite(JNDITest.class);
    }

    public void setUp() throws Exception 
    {
        if (initialContext == null)
        {
            InputStream input = 
                new ByteArrayInputStream(TEST_CONFIG.getBytes());
            initialContext = JNDIUtils.initJNDI();
            JNDIUtils.parseJNDIConfig(input);
        }
    }

    public void testJNDIConfigEnvironment() throws Exception
    {
        assertEquals(
            (String) initialContext.lookup("java:comp/env/test1"),
            "testString");
        assertEquals(
            (Boolean) initialContext.lookup("java:comp/env/services/TestService/test2"),
            Boolean.TRUE);
        assertEquals(
            (Byte) initialContext.lookup("java:comp/env/test3"),
            new Byte((byte) 111));
        assertEquals(
            (Character) initialContext.lookup("java:comp/env/test4"),
            new Character('q'));
        assertEquals(
            (Double) initialContext.lookup("java:comp/env/test5"),
            new Double(1.12345));
        assertEquals(
            (Float) initialContext.lookup("java:comp/env/test6"),
            new Float(1.12345));
        assertEquals(
            (Integer) initialContext.lookup("java:comp/env/test7"),
            new Integer(12345));
        assertEquals(
            (Long) initialContext.lookup("java:comp/env/test8"),
            new Long(12345));
        assertEquals(
            (Short) initialContext.lookup("java:comp/env/test9"),
            new Short((short) 12345));
    }

    public void testJNDIConfigResource() throws Exception
    {
        assertEquals(
            "Global Test Nr: 0",
            (String) initialContext.lookup("java:comp/env/testResource1"));

        assertEquals(
            "Service Test Nr: 1",
            (String) initialContext.lookup("java:comp/env/services/TestService/testResource2"));

    }

    public void testJNDIConfigResourceLink() throws Exception
    {
        // Looks like once the object is produced it stays
        // Ie another access to the same name returns the same object
        assertEquals(
            "Global Test Nr: 0",
            (String) initialContext.lookup("java:comp/env/services/TestService/testResource1"));

    }

}
