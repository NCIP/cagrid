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
package org.globus.wsrf.handlers;

import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.axis.gsi.GSIConstants;
import org.globus.gsi.gssapi.auth.NoAuthorization;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.gsi.GlobusCredential;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.wsrf.impl.TestService;
import org.globus.wsrf.test.GridTestCase;
import org.globus.wsrf.tests.basic.CreateResource;
import org.globus.wsrf.tests.basic.CreateResourceResponse;
import org.globus.wsrf.tests.basic.TestPortType;
import org.globus.wsrf.tests.basic.service.TestServiceAddressingLocator;
import org.globus.wsrf.tests.security.SecurityTestPortType;
import org.globus.wsrf.tests.security.service.SecurityTestServiceAddressingLocator;
import org.globus.wsrf.tests.security.GsiSecConvIntegrity;

import java.io.InputStream;

import javax.xml.namespace.QName;

import org.apache.axis.message.addressing.Constants;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.message.addressing.AddressingHeaders;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.types.URI;
import org.apache.axis.message.SOAPEnvelope;

import javax.xml.rpc.Stub;

import org.ietf.jgss.GSSCredential;

public class WSATests extends GridTestCase {

    private TestServiceAddressingLocator locator =
        new TestServiceAddressingLocator();

    public WSATests(String name) {
        super(name);
    }

    // BROKEN
    public void testFaultAtPivotHandler() throws Exception {
        String address = TEST_CONTAINER.getBaseURL() +
            TestService.SERVICE_PATH + "foobar";
        
        EndpointReferenceType epr = 
            new EndpointReferenceType(new URI(address));

        TestPortType port = this.locator.getTestPortTypePort(epr);
        setStubProperties((Stub)port);

        try {
            port.createResource(new CreateResource());
            fail("did not throw right exception");
        } catch (AxisFault e) {
            if (e.getMessage().indexOf("The AXIS engine could not find a target service to invoke") == -1) {
                e.printStackTrace();
                fail("did not get the right exception: " + e.getMessage());
            }
        }

        // checkHeaders(locator.getCall(), TestService.SERVICE_PATH);
    }

    public void testFaultInService() throws Exception {
        String address = TEST_CONTAINER.getBaseURL() +
            TestService.SERVICE_PATH;

        EndpointReferenceType epr = 
            new EndpointReferenceType(new URI(address));

        TestPortType port = this.locator.getTestPortTypePort(epr);
        setStubProperties((Stub)port);
        
        try {
            port.getResourceProperty(WSRFConstants.CURRENT_TIME);
            fail("did not throw right exception");
        } catch (AxisFault e) {
            if (e.getMessage().indexOf("Failed to acquire resource") == -1) {
                e.printStackTrace();
                fail("did not get the right exception: " + e.getMessage());
            }
        }

        checkHeaders(locator.getCall(), TestService.SERVICE_PATH);
    }

    // BROKEN
    public void testFaultAtAddressingHandler() throws Exception {
        String address = TEST_CONTAINER.getBaseURL() +
            TestService.SERVICE_PATH;

        EndpointReferenceType epr = 
            new EndpointReferenceType(new URI(address));
        epr.getProperties().add(ObjectSerializer.toSOAPElement("BadKeyValue",
                                                               new QName(Constants.NS_URI_ADDRESSING, "BadKey")));

        TestPortType port = this.locator.getTestPortTypePort(epr);
        setStubProperties((Stub)port);

        try {
            port.getResourceProperty(WSRFConstants.CURRENT_TIME);
            fail("did not throw right exception");
        } catch (AxisFault e) {
            if (e.getMessage().indexOf("Unsupported addressing header") == -1) {
                e.printStackTrace();
                fail("did not get the right exception: " + e.getMessage());
            }
        }

        // checkHeaders(locator.getCall(), TestService.SERVICE_PATH);
    }

    public void testFaultAtJAXRPCHandler() throws Exception {

        ClassLoader loader = WSATests.class.getClassLoader();
        InputStream in = loader.getResourceAsStream("org/globus/wsrf/handlers/badcred.pem");
        assertTrue("Unable to obtain cred", in != null);

        String address = TEST_CONTAINER.getBaseURL() +
            TestService.SERVICE_PATH;

        EndpointReferenceType epr = 
            new EndpointReferenceType(new URI(address));

        TestPortType port = this.locator.getTestPortTypePort(epr);
        setStubProperties((Stub)port);

        GSSCredential cred = 
            new GlobusGSSCredentialImpl(new GlobusCredential(in),
                                        GSSCredential.INITIATE_ONLY);
        
        ((Stub)port)._setProperty(GSIConstants.GSI_CREDENTIALS,
                                  cred);
        ((Stub)port)._setProperty(
                        org.globus.wsrf.security.Constants.GSI_SEC_MSG,
                        org.globus.wsrf.security.Constants.SIGNATURE);

        try {
            port.createResource(new CreateResource());
            fail("did not throw right exception");
        } catch (AxisFault e) {
            if (e.getMessage().indexOf("Unknown CA") == -1) {
                e.printStackTrace();
                fail("did not get the right exception: " + e.getMessage());
            }
        }

        checkHeaders(locator.getCall(), TestService.SERVICE_PATH);
    }
    
    public void testFaultAtAxisHandler() throws Exception {
        
        SecurityTestServiceAddressingLocator locator =
            new SecurityTestServiceAddressingLocator();
        
        String address = TEST_CONTAINER.getBaseURL() +
            "SecurityTestService";
        
        EndpointReferenceType epr = 
            new EndpointReferenceType(new URI(address));

        SecurityTestPortType port =
            locator.getSecurityTestPortTypePort(epr);

        setStubProperties((Stub)port);

        try {
            port.gsiSecConvIntegrity(new GsiSecConvIntegrity());
            fail("did not throw right exception");
        } catch (AxisFault e) {
            if (e.getMessage().indexOf("GSI Secure Conversation (signature only) authentication required") == -1) {
                e.printStackTrace();
                fail("did not get the right exception: " + e.getMessage());
            }
        }
        
        checkHeaders(locator.getCall(), "SecurityTestService");
    }

    private void checkHeaders(Call call, String service) throws Exception {
        SOAPEnvelope env = 
            (SOAPEnvelope)call.getResponseMessage().getSOAPPart().getEnvelope();
        
        AddressingHeaders headers = new AddressingHeaders(env);
        
        assertTrue(headers.getFrom() != null);
        assertTrue(headers.getFrom().getAddress().toString().indexOf(service) != -1);
        
        assertTrue(headers.getAction() != null);
        assertEquals(Constants.FAULT_ACTION,
                     headers.getAction().toString());
    }

    private void setStubProperties(Stub s) {
        s._setProperty(GSIConstants.GSI_AUTHORIZATION,
                       NoAuthorization.getInstance());
    }

}
