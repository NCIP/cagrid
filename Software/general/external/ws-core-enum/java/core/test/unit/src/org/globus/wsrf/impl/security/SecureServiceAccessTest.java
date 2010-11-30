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
package org.globus.wsrf.impl.security;

import javax.xml.rpc.Stub;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.axis.gsi.GSIConstants;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authorization.Authorization;
import org.globus.wsrf.impl.security.authorization.HostAuthorization;
import org.globus.wsrf.impl.security.authorization.NoAuthorization;
import org.globus.wsrf.impl.security.descriptor.ClientSecurityDescriptor;
import org.globus.wsrf.test.GridTestCase;
import org.globus.wsrf.tests.security.CreateResourceResponse;
import org.globus.wsrf.tests.security.GsiSec;
import org.globus.wsrf.tests.security.GsiSecConvDeleg;
import org.globus.wsrf.tests.security.GsiSecConvIntegrity;
import org.globus.wsrf.tests.security.GsiSecConvOnly;
import org.globus.wsrf.tests.security.GsiSecConvPrivacy;
import org.globus.wsrf.tests.security.GsiSecMsgIntegrity;
import org.globus.wsrf.tests.security.GsiSecMsgOnly;
import org.globus.wsrf.tests.security.GsiSecMsgPrivacy;
import org.globus.wsrf.tests.security.SecurityTestPortType;
import org.globus.wsrf.tests.security.service.SecurityTestServiceAddressingLocator;

public class SecureServiceAccessTest extends GridTestCase {

    static Log logger =
        LogFactory.getLog(SecureServiceAccessTest.class.getName());

    static EndpointReferenceType testServiceEPR = null;

    static String CLIENT_DESC_FILE =
        "org/globus/wsrf/impl/security/client-security-config.xml";

    static String HOST_CLIENT_DESC_FILE =
        "org/globus/wsrf/impl/security/host-client-security-config.xml";

    Authorization authz = null;
    public SecureServiceAccessTest(String name) {
        super(name);
        authz = TestConstants.getConfiguredClientAuthz();
        System.out.println("Authz is " + authz.getClass().getName());
    }

    public static Test suite() {
        return new TestSuite(SecureServiceAccessTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void testSecureAccess() throws Exception {
        assertTrue(TEST_CONTAINER != null);
        String testServiceAddrs = TEST_CONTAINER.getBaseURL() +
                                  TestConstants.SECURITY_SERVICE_PATH;
        EndpointReferenceType testServiceEPR =
            new EndpointReferenceType(new URI(testServiceAddrs));
        serviceSecurityTest(testServiceEPR);
        resourceSecurityTest(testServiceEPR);

        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType port =
            locator.getSecurityTestPortTypePort(testServiceEPR);

        // Set the authz setting in service's descriptor to be null
        port.setServiceAuthz(null);

        // test container descriptor
        containerSecDescTest(testServiceEPR);

        // Set the authz setting in service's descriptor to be null
        port.setServiceAuthz("self");

        // test setting credentials from context
        setCredsFromContextTest(testServiceEPR);

        // Set the authz setting in service's descriptor to be gridmap
        port.setServiceAuthz("gridmap");

        SecurityTestPortType testPort =
            locator.getSecurityTestPortTypePort(testServiceEPR);

        logger.debug("No Auth tests");
        // shld work.
        secureInvocations(testPort, false, false, false, false, false, false,
                          false, false);

        setGSISecMsg(testPort, true);

        // should work. Call is secure, but no auth is enforced.
        boolean exp = true;
        try {
            testPort.noAuth(null);
        } catch (Exception e) {
            logger.error(e);
            exp = false;
        }
        assertTrue(exp);

        // should fail.
        exp = false;
        try {
            testPort.gsiSecMsgOnly(new GsiSecMsgOnly());
        } catch (Exception e) {
            if (e.getMessage().indexOf("is not authorized") != -1) {
                exp = true;
            } else {
                logger.error(e);
            }
        }
        assertTrue(exp);

    }

    private void containerSecDescTest(EndpointReferenceType testEPR)
        throws Exception {

        // Create a resource
        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            locator.getSecurityTestPortTypePort(testEPR);
        CreateResourceResponse response =
            testPort.createResource(true);
        EndpointReferenceType res1EPR = response.getEndpointReference();
        // Alter security desc
        SecurityTestPortType res1Port =
            locator.getSecurityTestPortTypePort(res1EPR);

        // any call shld fail since container has gridmap authz
        setGSISecConv(res1Port, true, false);
        boolean expOccured = false;
        try {
            res1Port.alterSecurityDesc(null);
            fail("Did not fail as expected");
        } catch (Exception exp) {
            if (exp.getMessage().indexOf("No gridmap file") != -1)
                expOccured = true;
        }
        assertTrue(expOccured);
    }

    private void setCredsFromContextTest(EndpointReferenceType testEPR)
        throws Exception {
        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            locator.getSecurityTestPortTypePort(testEPR);
        CreateResourceResponse response =
            testPort.createResource(true);
        EndpointReferenceType res1EPR = response.getEndpointReference();
        // Alter security desc
        SecurityTestPortType res1Port =
            locator.getSecurityTestPortTypePort(res1EPR);
        setGSISecConv(res1Port, true, false);
        res1Port.alterSecurityDesc(SecurityTestService.CRED_FROM_CONTEXT);
    }

    public void serviceSecurityTest(EndpointReferenceType testEPR)
        throws Exception {

        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            locator.getSecurityTestPortTypePort(testEPR);

        logger.debug("No Auth tests");
        // only noAuth shld work
        secureInvocations(testPort, false, false, false, false, false, false, 
                          false, false);

        logger.debug("Sec Conv with integrity tests");
        // secure conv integrity with no delegation
        SecurityTestServiceAddressingLocator locator1 =
            new SecurityTestServiceAddressingLocator();
        testPort = locator1.getSecurityTestPortTypePort(testEPR);

        setGSISecConv(testPort, true, false);
        secureInvocations(testPort, true, true, false, true, false, false, 
                          false, true);

        logger.debug("Sec Conv with integrity and deleg tests");
        // secure conv integrity with delegation
        SecurityTestServiceAddressingLocator locator2 =
            new SecurityTestServiceAddressingLocator();
        testPort = locator2.getSecurityTestPortTypePort(testEPR);
        setGSISecConv(testPort, true, true);
        secureInvocations(testPort, true, true, false, true, false, false, 
                          false, true);

        logger.debug("Sec Conv with privacy and deleg tests");
        // secure conv privacy with deleg
        SecurityTestServiceAddressingLocator locator3 =
            new SecurityTestServiceAddressingLocator();
        testPort = locator3.getSecurityTestPortTypePort(testEPR);
        setGSISecConv(testPort, false, true);
        secureInvocations(testPort, true, false, true, true, false, false, 
                          false, true);

        logger.debug("Sec Msg tests");
        // secure msg signature
        SecurityTestServiceAddressingLocator locator4 =
            new SecurityTestServiceAddressingLocator();
        testPort = locator4.getSecurityTestPortTypePort(testEPR);
        setGSISecMsg(testPort, true);
        secureInvocations(testPort, false, false, false, false, true, false, 
                          true, true);

        // secure msg encryption
        SecurityTestServiceAddressingLocator locator5 =
            new SecurityTestServiceAddressingLocator();
        testPort = locator4.getSecurityTestPortTypePort(testEPR);
        setGSISecMsg(testPort, false);
        secureInvocations(testPort, false, false, false, false, true, true,
                          false, true);
    }

    public void resourceSecurityTest(EndpointReferenceType testEPR)
        throws Exception {

        // Create a resource
        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType testPort =
            locator.getSecurityTestPortTypePort(testEPR);
        CreateResourceResponse response =
            testPort.createResource(true);
        EndpointReferenceType res1EPR = response.getEndpointReference();
        // Alter security desc
        SecurityTestPortType res1Port =
            locator.getSecurityTestPortTypePort(res1EPR);
        // This shld fail, since the resource desc is null and service
        // desc required secure conv
        try {
            res1Port.alterSecurityDesc("GSISecureConv");
            fail("Did not fail as expected");
        } catch (Exception e) {
            logger.error(e);
        }
        // With secure conv set, it shld work
        setGSISecConv(res1Port, true, false);
        res1Port.alterSecurityDesc("GSISecureConv");

        // Now any method access with that resource shld need secure
        // conv. The following requires, gsiSecMSg according to
        // service, but now needs gsiSecConv
        res1Port.gsiSecMsgOnly(new GsiSecMsgOnly());

        // set authz to self
        res1Port.setAuthz("self");

        SecurityTestServiceAddressingLocator locator1 =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType res1Port2 =
            locator1.getSecurityTestPortTypePort(res1EPR);
        setAnonymous(res1Port2);

        boolean expOccured = false;
        try {
            res1Port2.gsiSecConvOnly(new GsiSecConvOnly());
            fail("Did not fail as expected");
        } catch (Exception exp) {
            expOccured = true;
        }
        assertTrue(expOccured);

        // set authz to null
        res1Port.setAuthz("none");

        // anonymous shld work.
        logger.debug("Anonymous invocation");
        res1Port2.gsiSecConvOnly(new GsiSecConvOnly());

        // Create a new resource and set it to use secure msg
        response = testPort.createResource(true);
        EndpointReferenceType res2EPR = response.getEndpointReference();
        SecurityTestPortType res2Port =
            locator.getSecurityTestPortTypePort(res2EPR);
        // With secure conv set, it shld work
        setGSISecConv(res2Port, true, false);
        res2Port.alterSecurityDesc("GSISecureMsg");

        SecurityTestPortType res2Port1 =
            locator.getSecurityTestPortTypePort(res2EPR);
        setGSISecMsg(res2Port1, true);
        // Now any method access with that resource shld need secure
        // msg. The following requires, gsiSecConv according to
        // service, but now needs gsiSecmsg
        res2Port1.gsiSecConvIntegrity(new GsiSecConvIntegrity());

        SecurityTestServiceAddressingLocator locator2 =
            new SecurityTestServiceAddressingLocator();
        SecurityTestPortType port =
            locator2.getSecurityTestPortTypePort(testEPR);
        // For resource, get replay attack proeprties, shld be whatever is
        // set in container security descriptor
        String replayWin = port.getSecurityProperty("replayWindow");
        assertTrue(replayWin != null);
        assertTrue(replayWin.equals("10000"));

        String replayFilter = port.getSecurityProperty("replayFilter");
        assertTrue(replayFilter.equals("randomValue"));

        // Reset security to be null, so service security descriptor
        // will be enforced.
        res1Port.alterSecurityDesc(null);

        // using secure conversation for gsiSecMsgOnly will fail.
        try {
            res1Port.gsiSecMsgOnly(new GsiSecMsgOnly());
            fail("Did not fail as expected");
        } catch (Exception e) {
            logger.error(e);
        }

        // service security descriptor test shld work.
        serviceSecurityTest(res1EPR);

    }

    // no auth always works.`
    private void
        secureInvocations(SecurityTestPortType testPort,
                          boolean gsiSecConvDeleg, boolean gsiSecConvIntegrity,
                          boolean gsiSecConvPrivacy, boolean gsiSecConvOnly,
                          boolean gsiSecMsgOnly, boolean gsiSecMsgPrivacy,
                          boolean gsiSecMsgIntegrity, boolean gsiSec)
        throws Exception {

        secureInvocations(testPort, true, gsiSecConvDeleg, gsiSecConvIntegrity,
                          gsiSecConvPrivacy, gsiSecConvOnly, gsiSecMsgOnly,
                          gsiSecMsgPrivacy, gsiSecMsgIntegrity, gsiSec);
    }

    private void
        secureInvocations(SecurityTestPortType testPort, boolean noAuth,
                          boolean gsiSecConvDeleg, boolean gsiSecConvIntegrity,
                          boolean gsiSecConvPrivacy, boolean gsiSecConvOnly,
                          boolean gsiSecMsgOnly, boolean gsiSecMsgPrivacy,
                          boolean gsiSecMsgIntegrity, boolean gsiSec)
        throws Exception {

        logger.debug("No auth");
        boolean exp = true;
        try {
            testPort.noAuth(null);
        } catch (Exception e) {
            logger.error(e);
            exp = false;
        }
        assertTrue(exp == noAuth);

        logger.debug("GSI Sec Conv Integrity");
        exp = true;
        try {
            testPort.gsiSecConvIntegrity(new GsiSecConvIntegrity());
        } catch (Exception e) {
            logger.error(e);
            exp = false;
        }
        assertTrue(exp == gsiSecConvIntegrity);

        logger.debug("GSI Sec Conv Privacy");
        exp = true;
        try {
            testPort.gsiSecConvPrivacy(new GsiSecConvPrivacy());
        } catch (Exception e) {
            logger.error(e);
            exp = false;
        }
        assertTrue(exp == gsiSecConvPrivacy);

        logger.debug("GSI Sec Conv Delegation");
        exp = true;
        try {
            testPort.gsiSecConvDeleg(new GsiSecConvDeleg());
        } catch (Exception e) {
            exp = false;
        }
        assertTrue(exp == gsiSecConvDeleg);

        logger.debug("GSI Sec Conv Only");
        exp = true;
        try {
            testPort.gsiSecConvOnly(new GsiSecConvOnly());
        } catch (Exception e) {
            logger.error(e);
            exp = false;
        }
        assertTrue(exp == gsiSecConvOnly);

        logger.debug("GSI Sec Msg");
        exp = true;
        try {
            testPort.gsiSecMsgOnly(new GsiSecMsgOnly());
        } catch (Exception e) {
            logger.error(e);
            exp = false;
        }
        assertTrue(exp == gsiSecMsgOnly);

        logger.debug("GSI Sec Msg privacy");
        exp = true;
        try {
            testPort.gsiSecMsgPrivacy(new GsiSecMsgPrivacy());
        } catch (Exception e) {
            logger.error(e);
            exp = false;
        }
        assertTrue(exp == gsiSecMsgPrivacy);

        logger.debug("GSI Sec Msg integrity");
        exp = true;
        try {
            testPort.gsiSecMsgIntegrity(new GsiSecMsgIntegrity());
        } catch (Exception e) {
            logger.error(e);
            exp = false;
        }
        assertTrue(exp == gsiSecMsgIntegrity);

        logger.debug("GSI Security");
        exp = true;
        try {
            testPort.gsiSec(new GsiSec());
        } catch (Exception e) {
            logger.error(e);
            exp = false;
        }
        assertTrue(exp == gsiSec);
    }

    private void setGSISecConv(SecurityTestPortType testPort,
                               boolean sig, boolean deleg) {
        // signature and delegation, use config file
        if ((sig) && (deleg)) {
            if (authz instanceof HostAuthorization) {
                ((Stub)testPort)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE,
                                              HOST_CLIENT_DESC_FILE);
            } else {
                ((Stub)testPort)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE,
                                              CLIENT_DESC_FILE);
            }
            return;
        }

        // if (signature and no delegation, construct desc here
        if ((sig) && (!deleg)) {
            ClientSecurityDescriptor desc = new ClientSecurityDescriptor();
            desc.setGSISecureConv(Constants.SIGNATURE);
            desc.setAuthz(authz);
            ((Stub)testPort)._setProperty(Constants.CLIENT_DESCRIPTOR, desc);
            return;
        }

        // Any other request, set stuff using the direct mdg properties.
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

    private void setAnonymous(SecurityTestPortType testPort) {
        ClientSecurityDescriptor desc = new ClientSecurityDescriptor();
        desc.setGSISecureConv(Constants.SIGNATURE);
        desc.setAnonymous();
        desc.setAuthz(new NoAuthorization());
        ((Stub)testPort)._setProperty(Constants.CLIENT_DESCRIPTOR, desc);
    }

    private void setGSISecMsg(SecurityTestPortType testPort, boolean sig) 
        throws Exception {
        if (sig) {
            ((Stub)testPort)._setProperty(Constants.GSI_SEC_MSG,
                                          Constants.SIGNATURE);
        } else {
            ((Stub)testPort)._setProperty(Constants.GSI_SEC_MSG,
                                          Constants.ENCRYPTION);
            if (authz instanceof HostAuthorization) {
                ((Stub)testPort)._setProperty(Constants.PEER_SUBJECT, 
                                  TestConstants
                                  .getPeerSubjectForClient(false));
            } else {
                ((Stub)testPort)._setProperty(Constants.PEER_SUBJECT, 
                                              TestConstants
                                              .getPeerSubjectForClient(true));
            }
        }
        ((Stub)testPort)._setProperty(Constants.AUTHORIZATION, authz);
    }
}
