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

import javax.naming.Context;
import javax.security.auth.Subject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.axis.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.gsi.gssapi.jaas.GlobusPrincipal;
import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;
import org.globus.wsrf.impl.security.descriptor.ServiceSecurityConfig;
import org.globus.wsrf.jndi.JNDIUtils;
import org.globus.wsrf.security.authorization.PDP;

public class TestSelfAuthorization extends TestCase {
    static Log logger =
        LogFactory.getLog(TestSelfAuthorization.class.getName());

    private Subject anonSubject = new Subject();
    private String USER1 = "/CN=foo";
    private String USER2 = "/CN=bar";
    private String USER3 = "/CN=tmp";

    public TestSelfAuthorization(String name) 
	throws Exception {
        super(name);
	Context initialContext = JNDIUtils.initJNDI();
    }

    public static Test suite() {
        return new TestSuite(TestSelfAuthorization.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void testAnonymous() throws Exception {
        
	PDP pdp = SelfAuthorization.getInstance();
	// Service path
	String servicePath = "tempService1";
	// Create and store service subject
        Subject serviceSubject = new Subject();
        serviceSubject.getPrincipals().add(new GlobusPrincipal(USER1));
	storeServiceSubject(servicePath, serviceSubject);

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
        PDP pdp = SelfAuthorization.getInstance();

	String servicePath = "tempService2";
	// Create and store service subject
        Subject serviceSubject = new Subject();
        serviceSubject.getPrincipals().add(new GlobusPrincipal(USER1));
	storeServiceSubject(servicePath, serviceSubject);
	
        Subject callerSubject = new Subject();
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER2));

        pdp.initialize(null, null, servicePath);
        assertFalse(pdp.isPermitted(callerSubject, new MessageContext(null), 
                                    null));
    }

    public void testSuccess() throws Exception {
        PDP pdp = SelfAuthorization.getInstance();

	// trying to overwitre subject from previous case
	String servicePath = "tempService2";

        Subject serviceSubject = new Subject();
        serviceSubject.getPrincipals().add(new GlobusPrincipal(USER3));
	storeServiceSubject(servicePath, serviceSubject);

        Subject callerSubject = new Subject();
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER2));
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER3));

        pdp.initialize(null, null, servicePath);
        assertTrue(pdp.isPermitted(callerSubject, new MessageContext(null), 
                                    null));
    }

    private void storeServiceSubject(String servicePath, Subject subject)
	throws Exception {
	// This should create required JNDI context for security properties
	ServiceSecurityConfig.initialize(servicePath, null);
	// Store subject for service in JNDI
	ServiceSecurityConfig.setSubject(subject, servicePath);
    }
}
