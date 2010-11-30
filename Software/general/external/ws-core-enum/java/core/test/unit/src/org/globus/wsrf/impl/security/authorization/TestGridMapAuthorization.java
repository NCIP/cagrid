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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.globus.wsrf.impl.security.descriptor.ServiceSecurityConfig;
import org.globus.wsrf.jndi.JNDIUtils;

import org.apache.axis.MessageContext;

import org.globus.gsi.jaas.GlobusPrincipal;
import org.globus.security.gridmap.GridMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.Context;

import javax.security.auth.Subject;

import org.globus.wsrf.security.authorization.PDP;
import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;

public class TestGridMapAuthorization extends TestCase {
    static Log logger =
        LogFactory.getLog(TestGridMapAuthorization.class.getName());
    private Subject anonSubject = new Subject();
    private String USER1 = "/CN=foo";
    private String USER2 = "/CN=bar";

    public TestGridMapAuthorization(String name) throws Exception {
        super(name);
	Context initialContext = JNDIUtils.initJNDI();
    }

    public static Test suite() {
        return new TestSuite(TestGridMapAuthorization.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void testAnonymous() throws Exception {
        PDP pdp = GridMapAuthorization.getInstance();

	String servicePath = "service1";
        GridMap map = new GridMap();
        map.map(USER2, "nobody");
	storeServiceGridMap(servicePath, map);

	boolean exp = false;
        try {
            pdp.initialize(null, null, servicePath);
            pdp.isPermitted(anonSubject, new MessageContext(null), null);
            fail("Failed to throw exception");
        } catch (AuthorizationException e) {
	    if (e.getMessage().indexOf("anonymous") != -1)
		exp = true;
            e.printStackTrace();
        }
	assertTrue(exp);
    }

    public void testFail() throws Exception {
        PDP pdp = GridMapAuthorization.getInstance();

	String servicePath = "service2";
        GridMap map = new GridMap();
        map.map(USER2, "nobody");
	storeServiceGridMap(servicePath, map);

        Subject callerSubject = new Subject();
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER1));

        pdp.initialize(null, null, servicePath);
        assertFalse(pdp.isPermitted(callerSubject, new MessageContext(null), 
                                    null));
    }

    public void testSuccess() throws Exception {
        PDP pdp = GridMapAuthorization.getInstance();

	String servicePath = "service3";
        GridMap map = new GridMap();
        map.map(USER2, "nobody");
        map.map(USER1, "nobody");
	storeServiceGridMap(servicePath, map);

        Subject callerSubject = new Subject();
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER1));

        pdp.initialize(null, null, servicePath);
	assertTrue(pdp.isPermitted(callerSubject, new MessageContext(null), 
                                   null));
    }

    private void storeServiceGridMap(String servicePath, GridMap map)
	throws Exception {
	// This should create required JNDI context for security proeprties
	ServiceSecurityConfig.initialize(servicePath, null);
	// Store subject for service in JNDI
	ServiceSecurityConfig.setGridMap(map, servicePath);	
    }
}
