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

import javax.xml.rpc.Stub;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.axis.gsi.GSIConstants;
import org.globus.wsrf.impl.security.TestConstants;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.test.GSITestContainer;
import org.globus.wsrf.test.GridTestCase;
import org.globus.wsrf.test.TestContainer;
import org.globus.wsrf.tests.security.CreateResourceResponse;
import org.globus.wsrf.tests.security.GsiSecConvDeleg;
import org.globus.wsrf.tests.security.GsiSecConvIntegrity;
import org.globus.wsrf.tests.security.NoAuthRequest;
import org.globus.wsrf.tests.security.SecurityTestPortType;
import org.globus.wsrf.tests.security.service.SecurityTestServiceAddressingLocator;

public class TestAuthorizationCallout extends GridTestCase {

    static Log logger =
        LogFactory.getLog(TestAuthorizationCallout.class.getName());
    Authorization authz = null;

    private TestContainer testContainer;

    public TestAuthorizationCallout(String name) {
        super(name);
        authz = TestConstants.getConfiguredClientAuthz();
        logger.debug("Authz is " + authz.getClass().getName());
    }

    public static Test suite() {
        return new TestSuite(TestAuthorizationCallout.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void testSecureAccess() throws Exception {
        assertTrue(this.testContainer != null);
        String testServiceAddrs = this.testContainer.getBaseURL() +
            TestConstants.CUSTOM_AUTHZ_SERVICE_PATH;
        logger.debug("Test " + testServiceAddrs);

        EndpointReferenceType testServiceEPR =
            new EndpointReferenceType(new URI(testServiceAddrs));

        SecurityTestServiceAddressingLocator locator1 =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort1 =
            locator1.getSecurityTestPortTypePort(testServiceEPR);

        // This should be the first call on the service for the authz
        // to be set to custom authz and point to the configured test
        // authz  service. The service is configured to decline any
        // methods with subject name being wildcard and access to
        // methods : gsiSecConvOnly, gsiSecConvIntegrity, gsiSec
        String authzServiceUrl = this.testContainer.getBaseURL()
            + TestConstants.TEST_AUTHZ_SERVICE;
        ((Stub)testPort1)._setProperty(Constants.AUTHORIZATION, authz);
        NoAuthRequest noAuthRequest = new NoAuthRequest();
        noAuthRequest.setAuthzService(authzServiceUrl);
        // Check if authz service identity has been configured.
        String authzServiceIdentity =
            System.getProperty(TestConstants.TEST_AUTHZ_SERVICE_IDENTITY);
        logger.debug("Authz service identity " + authzServiceIdentity);
        noAuthRequest.setAuthzServiceIdentity(authzServiceIdentity);

        testPort1.noAuth(noAuthRequest);

        serviceSecurityTest(testServiceEPR);

        //Reset
        testPort1.noAuth(noAuthRequest);
        // Create a resource
        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            locator.getSecurityTestPortTypePort(testServiceEPR);
        setGSISecConv(testPort, true, true);
        CreateResourceResponse response =
            testPort.createResource(false);
        EndpointReferenceType res1EPR = response.getEndpointReference();
        serviceSecurityTest(res1EPR);
    }

    public void serviceSecurityTest(EndpointReferenceType testEPR)
        throws Exception {

        logger.debug("Sec Conv with integrity tests");
        // secure conv integrity with no delegation
        SecurityTestServiceAddressingLocator locator1 =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            locator1.getSecurityTestPortTypePort(testEPR);

        // secure conversation, signature, no delegation
        setGSISecConv(testPort, true, false);

        logger.debug("GSI Sec Conv Integrity");

        boolean exceptionOccured = false;
        try {
            testPort.gsiSecConvIntegrity(new GsiSecConvIntegrity());
        } catch (Exception e) {
            if (e.getMessage().indexOf("authorization") != -1) {
                exceptionOccured = true;
            } else {
                logger.error("Unexcepted error: ",e);
            }
        }
        assertTrue(exceptionOccured == true);

        logger.debug("GSI Sec Conv Delegation");
        boolean noExceptionOccured = true;
        try {
            testPort.gsiSecConvDeleg(new GsiSecConvDeleg());
        } catch (Exception e) {
            logger.error("Unexcepted error: ",e);
            noExceptionOccured = false;
        }
        assertTrue(noExceptionOccured == true);

        logger.debug("No auth");
        noExceptionOccured = true;
        NoAuthRequest noAuthRequest = new NoAuthRequest();
        noAuthRequest.setAuthzService(this.testContainer.getBaseURL()
                                      + TestConstants.TEST_AUTHZ_SERVICE);
        noAuthRequest.setAuthzServiceIdentity(
            "/C=US/O=Globus Alliance/OU=Service/CN=host/foobar.com");
        try {
            testPort.noAuth(noAuthRequest);
        } catch (Exception e) {
            logger.error("Unexcepted error: ",e);
            noExceptionOccured = false;
        }
        assertTrue(noExceptionOccured == true);

        logger.debug("GSI Sec Conv Delegation - 2");
        exceptionOccured = false;
        try {
            testPort.gsiSecConvDeleg(new GsiSecConvDeleg());
        } catch (Exception e) {
            if (e.getMessage().indexOf(
                "Error accessing authorization service") != -1) {
                exceptionOccured = true;
            } else {
                logger.error("Unexcepted error: ",e);
            }
        }
        assertTrue(exceptionOccured == true);
    }

    private void setGSISecConv(SecurityTestPortType testPort,
                               boolean sig, boolean deleg) {

        if (sig) {
            ((Stub)testPort)._setProperty(Constants.GSI_SEC_CONV,
                                          Constants.SIGNATURE);
        } else {
            ((Stub)testPort)._setProperty(Constants.GSI_SEC_CONV,
                                          Constants.ENCRYPTION);
        }

        if (deleg) {
            ((Stub)testPort)._setProperty(GSIConstants.GSI_MODE,
                                          GSIConstants.GSI_MODE_FULL_DELEG);
        } else {
            ((Stub)testPort)._setProperty(GSIConstants.GSI_MODE,
                                          GSIConstants.GSI_MODE_NO_DELEG);
        }
        ((Stub)testPort)._setProperty(Constants.AUTHORIZATION, authz);
    }

    protected void setUp() throws Exception {
        this.testContainer = new GSITestContainer();
        try {
            this.testContainer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void tearDown() throws Exception {
        this.testContainer.stop();
        super.tearDown();
    }
}
