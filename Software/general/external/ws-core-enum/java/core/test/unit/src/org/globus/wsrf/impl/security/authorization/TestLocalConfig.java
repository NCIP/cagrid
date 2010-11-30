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
package org.globus.wsrf.impl.security.authorization;

import java.io.File;

import javax.security.auth.Subject;
import javax.xml.namespace.QName;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.axis.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.gsi.gssapi.jaas.GlobusPrincipal;
import org.globus.wsrf.security.authorization.PDP;

public class TestLocalConfig extends TestCase {
    static Log logger =
        LogFactory.getLog(TestLocalConfig.class.getName());

    String PDP_NAME = "testConfig";
    private Subject anonSubject = new Subject();
    private QName readQName = new QName("http://org.test", "read");
    private QName writeQName = new QName("http://org.test", "write");
    private QName execQName = new QName("http://org.test", "exec");
    private ResourcePDPConfig resourceConfig = null;
    private String USER1 = "/CN=foo";
    private String USER2 = "/CN=bar";
    private String USER3 = "/CN=tmp";

    public TestLocalConfig(String name) 
	throws Exception {
        super(name);
        String globusLocation = System.getProperty("GLOBUS_LOCATION");
        // XXX: Hardcoded gar id namez
        String testConf = globusLocation + File.separator + "etc"
            + File.separator + "globus_wsrf_test_unit" + File.separator + 
            "local-config-authz-test.conf";
        resourceConfig = new ResourcePDPConfig("test");
        resourceConfig.setProperty(PDP_NAME, 
                                   LocalConfigPDP.SECURITY_CONFIG_FILE, 
                                   testConf);
    }

    public static Test suite() {
        return new TestSuite(TestLocalConfig.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void testAnonymous() throws Exception {
        
	PDP pdp = new LocalConfigPDP();
        pdp.initialize(resourceConfig, PDP_NAME, "dummyService");
        assertFalse(pdp.isPermitted(anonSubject, new MessageContext(null), 
                                    readQName));
    }

    public void testSubject() throws Exception {

        Subject callerSubject = new Subject();
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER2));

	PDP pdp = new LocalConfigPDP();
        pdp.initialize(resourceConfig, PDP_NAME, "dummyService");
        assertTrue(pdp.isPermitted(callerSubject, new MessageContext(null), 
                                   readQName));
        assertFalse(pdp.isPermitted(callerSubject, new MessageContext(null), 
                                   writeQName));

        callerSubject = new Subject();
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER3));
        assertFalse(pdp.isPermitted(callerSubject, new MessageContext(null), 
                                   writeQName));

        callerSubject = new Subject();
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER1));
        assertTrue(pdp.isPermitted(callerSubject, new MessageContext(null), 
                                   execQName));
    }
}
