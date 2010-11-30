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
import javax.xml.namespace.QName;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.axis.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.gsi.jaas.GlobusPrincipal;
import org.globus.security.gridmap.GridMap;
import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;
import org.globus.wsrf.impl.security.authorization.exceptions.CloseException;
import org.globus.wsrf.impl.security.authorization.exceptions.InitializeException;
import org.globus.wsrf.impl.security.authorization.exceptions.InvalidPolicyException;
import org.globus.wsrf.impl.security.descriptor.ServiceSecurityConfig;
import org.globus.wsrf.jndi.JNDIUtils;
import org.globus.wsrf.security.authorization.Interceptor;
import org.globus.wsrf.security.authorization.PDP;
import org.globus.wsrf.security.authorization.PDPConfig;
import org.globus.wsrf.utils.XmlUtils;
import org.w3c.dom.Node;

public class TestServiceAuthzChain extends TestCase {

    static Log logger =
        LogFactory.getLog(TestServiceAuthzChain.class.getName());

    private Subject anonSubject = new Subject();
    private String USER1 = "/CN=foo";
    private String USER2 = "/CN=bar";
    public class SetPolicyPDP implements PDP {
        public void initialize(PDPConfig config, String name, String id) 
            throws InitializeException {
        }
        public String[] getPolicyNames() {
             return null;
        }
        public Node getPolicy(Node query) throws InvalidPolicyException {
             return null;
        }
        public Node setPolicy(Node policy) throws InvalidPolicyException {
            try {
             return XmlUtils.newDocument().createElement("test");
            } catch (Exception e) {
                throw new InvalidPolicyException("",e);
            }
        }
        public boolean isPermitted(Subject peerSubject,
                               javax.xml.rpc.handler.MessageContext context,
                               QName operation) throws AuthorizationException {
             return true;
        }
        public void close() throws CloseException {
        }
    }
    public TestServiceAuthzChain(String name) 
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

    public void nottestAnonymous() throws Exception {
        

	// Service path
	String servicePath = "tempService1";
	// This should create required JNDI context for security proeprties
	ServiceSecurityConfig.initialize(servicePath, null);

	// Create and store service subject
        Subject serviceSubject = new Subject();
        serviceSubject.getPrincipals().add(new GlobusPrincipal(USER1));
	storeServiceSubject(servicePath, serviceSubject);

        // Create and store gridmap
        GridMap map = new GridMap();
        map.map(USER2, "nobody");
	storeServiceGridMap(servicePath, map);

	PDP pdpSelf = SelfAuthorization.getInstance();
        PDP pdpGridmap = GridMapAuthorization.getInstance();

        Interceptor interceptors[] = new Interceptor[] { pdpSelf, pdpGridmap };
        TestAuthorizationChain testChain = 
            new TestAuthorizationChain(interceptors, servicePath);

	boolean exp = false;
        try {
            testChain.authorize(anonSubject, new MessageContext(null), 
                                servicePath);
            fail("Failed to throw exception");
        } catch (AuthorizationException e) {
	    if (e.getMessage().indexOf("anonymous") != -1)
		exp = true;
            e.printStackTrace();
        }
	assertTrue(exp);
    }

    public void testSetPolicy() throws Exception {
        Interceptor interceptors[] = new Interceptor[] { new SetPolicyPDP() };
        TestAuthorizationChain testChain = 
            new TestAuthorizationChain(interceptors, "");
        testChain.setPolicy(null);
    }

    public void testMultiplePDPs() throws Exception {


	String servicePath = "tempService2";
	ServiceSecurityConfig.initialize(servicePath, null);

	// Create and store service subject
        Subject serviceSubject = new Subject();
        serviceSubject.getPrincipals().add(new GlobusPrincipal(USER1));
	storeServiceSubject(servicePath, serviceSubject);
	
        // Create and store gridmap
        GridMap map = new GridMap();
        map.map(USER1, "nobody");
        map.map(USER2, "nobody2");
	storeServiceGridMap(servicePath, map);

        Subject callerSubject = new Subject();
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER2));

        PDP pdpSelf = SelfAuthorization.getInstance();
        PDP pdpGridmap = GridMapAuthorization.getInstance();

        Interceptor interceptors[] = new Interceptor[] { pdpSelf, pdpGridmap };
        TestAuthorizationChain testChain = 
            new TestAuthorizationChain(interceptors, servicePath);

        boolean exp = false;
        try {
            testChain.authorize(callerSubject, new MessageContext(null), 
                                servicePath);
            fail("Failed to throw exception");
        } catch (AuthorizationException e) {
	    if (e.getMessage().indexOf("not authorized") != -1)
		exp = true;
            e.printStackTrace();
        }
        assertTrue(exp);

        callerSubject = new Subject();
        callerSubject.getPrincipals().add(new GlobusPrincipal(USER1));

        // Should go thro'
        testChain.authorize(callerSubject, new MessageContext(null), 
                            servicePath);

        
        PDP pdpIden = new IdentityAuthorization(USER2);

        interceptors = new Interceptor[] { pdpSelf, pdpGridmap,  pdpIden};
        testChain = 
            new TestAuthorizationChain(interceptors, servicePath);

        exp = false;
        try {
            testChain.authorize(callerSubject, new MessageContext(null), 
                                servicePath);
            fail("Failed to throw exception");
        } catch (AuthorizationException e) {
	    if (e.getMessage().indexOf("not authorized") != -1)
		exp = true;
            e.printStackTrace();
        }
        assertTrue(exp);

    }

    private void storeServiceSubject(String servicePath, Subject subject)
	throws Exception {
	// Store subject for service in JNDI
	ServiceSecurityConfig.setSubject(subject, servicePath);
    }

    private void storeServiceGridMap(String servicePath, GridMap map)
	throws Exception {
	// Store subject for service in JNDI
	ServiceSecurityConfig.setGridMap(map, servicePath);	
    }

}


