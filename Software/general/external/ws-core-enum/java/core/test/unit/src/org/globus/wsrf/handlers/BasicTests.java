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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Hashtable;

import javax.xml.rpc.Stub;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.globus.util.http.HTTPProtocol;
import org.globus.util.http.HTTPResponseParser;
import org.globus.wsrf.impl.TestService;
import org.globus.wsrf.test.GridTestCase;
import org.globus.wsrf.tests.basic.CreateResource;
import org.globus.wsrf.tests.basic.CreateResourceResponse;
import org.globus.wsrf.tests.basic.GetInstanceInfoResponse;
import org.globus.wsrf.tests.basic.NoPermissionFault;
import org.globus.wsrf.tests.basic.TestLocalInvocation;
import org.globus.wsrf.tests.basic.TestPortType;
import org.globus.wsrf.tests.basic.service.TestServiceAddressingLocator;
import org.oasis.wsrf.lifetime.Destroy;
import org.oasis.wsrf.lifetime.ImmediateResourceTermination;
import org.oasis.wsrf.lifetime.WSResourceLifetimeServiceAddressingLocator;

public class BasicTests extends GridTestCase {

    public BasicTests(String name) {
        super(name);
    }

    public void testServiceNotFound() throws Exception {
        assertTrue(TEST_CONTAINER.getBaseURL().endsWith("/"));

        runTest(TEST_CONTAINER.getBaseURL());
        runTest(TEST_CONTAINER.getBaseURL() + "F");

        String url = TEST_CONTAINER.getBaseURL();
        runTest(url.substring(0, url.length()-1));
        //runTest(url.substring(0, url.length()-2));
    }

    private void runTest(String strUrl) throws Exception {
        System.out.println(strUrl);

        WSResourceLifetimeServiceAddressingLocator locator =
            new WSResourceLifetimeServiceAddressingLocator();

        URL url = new URL(strUrl);

        try {

            ImmediateResourceTermination port =
                locator.getImmediateResourceTerminationPort(url);
            port.destroy(new Destroy());
        } catch (AxisFault e) {
            assertTrue(
                e.getFaultCode().getLocalPart().equals(
                    "Server.NoService"
                    ));
        }

    }

    public void testWrongWSDLCall() throws Exception {
        String address =
            TEST_CONTAINER.getBaseURL() + TestService.SERVICE_PATH + "WrongWSDL";

        WSResourceLifetimeServiceAddressingLocator locator =
            new WSResourceLifetimeServiceAddressingLocator();

        URL url = new URL(address);

        ImmediateResourceTermination port =
            locator.getImmediateResourceTerminationPort(url);

        try {
            port.destroy(new Destroy());
        } catch (AxisFault e) {
            // little hacky...
            assertTrue(e.getMessage().startsWith("WSDLException: faultCode=OTHER_ERROR: [CORE] wsdl binding information missing"));
        }
    }

    public void testWrongWSDLGet() throws Exception {
        String wsdlLocation = TEST_CONTAINER.getBaseURL() +
            TestService.SERVICE_PATH + "WrongWSDL?wsdl";

        URL url = new URL(wsdlLocation);

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            socket = new Socket(TEST_CONTAINER.getHost(),
                                TEST_CONTAINER.getPort());

            out = socket.getOutputStream();
            String msg = HTTPProtocol.createGETHeader(url.getFile(),
                                                      TEST_CONTAINER.getHost(),
                                                      "TestClient");
            out.write(msg.getBytes());
            out.flush();

            in = socket.getInputStream();

            HTTPResponseParser response =
                new HTTPResponseParser(in);

            byte [] buf = new byte[1024];
            ByteArrayOutputStream outb =
                new ByteArrayOutputStream();
            long len = response.getContentLength();
            int bytes = 0;
            if (len == -1) {
                while ( (bytes = in.read(buf)) != -1 ) {
                    outb.write(buf, 0, bytes);
                }
            } else {
                int totalBytes = 0;
                while ( totalBytes < len ) {
                    bytes = in.read(buf);
                    if (bytes == -1) {
                        break;
                    }
                    outb.write(buf, 0, bytes);
                    totalBytes += bytes;
                }
            }

            byte [] data = outb.toByteArray();

            System.out.println("Content type: " +
                               response.getContentType());
            System.out.println("Reply: " + new String(data));

            assertTrue(response.getStatusCode() >= 300);
            assertTrue(response.getContentType().startsWith("text/html"));

            // we could check for this also in development prop enabled
            // in tomcat.
            /*
          ("WSDLException: faultCode=OTHER_ERROR: [CORE] SOAP address not found"));
            */

        } catch (IOException e) {
            fail(e.getMessage());
        } finally {
            if (in != null) {
                try { in.close(); } catch (Exception e) {}
            }
            if (out != null) {
                try { out.close(); } catch (Exception e) {}
            }
            if (socket != null) {
                try { socket.close(); } catch (Exception e) {}
            }
        }
    }

    public void testHTTP11() throws Exception {
        URL url = new URL(TEST_CONTAINER.getBaseURL() +
                          TestService.SERVICE_PATH);

        TestServiceAddressingLocator locator =
            new TestServiceAddressingLocator();

        TestPortType port = locator.getTestPortTypePort(url);

        CreateResourceResponse response = null;

        // force HTTP 1.0 version
        ((Stub)port)._setProperty(MessageContext.HTTP_TRANSPORT_VERSION,
                                  HTTPConstants.HEADER_PROTOCOL_V10);

        response = port.createResource(new CreateResource());

        // force HTTP 1.1 version
        ((Stub)port)._setProperty(MessageContext.HTTP_TRANSPORT_VERSION,
                                  HTTPConstants.HEADER_PROTOCOL_V11);

        response = port.createResource(new CreateResource());

        // force HTTP 1.1 chunked encoding
        Hashtable p = new Hashtable();
        p.put(HTTPConstants.HEADER_TRANSFER_ENCODING,
              HTTPConstants.HEADER_TRANSFER_ENCODING_CHUNKED);
        ((Stub)port)._setProperty(HTTPConstants.REQUEST_HEADERS,
                                  p);

        response = port.createResource(new CreateResource());
    }

    public void testScopingAndServiceLifecycle() throws Exception {
        TestServiceAddressingLocator locator =
            new TestServiceAddressingLocator();

        URL url = null;
        TestPortType port = null;

        // Application scope tests - numInstances should not increase

        url = new URL(TEST_CONTAINER.getBaseURL() +
                      TestService.SERVICE_PATH);

        port = locator.getTestPortTypePort(url);

        GetInstanceInfoResponse response = null;

        response = port.getInstanceInfo(null);

        assertTrue(response.getInstances() > 0);
        assertTrue(response.getInitCalls() > 0);
        assertEquals(0, response.getDestroyCalls());
        assertTrue(response.isProviderInit());

        port.resetNumInstances(null);

        response = port.getInstanceInfo(null);
        assertEquals(0, response.getInstances());
        assertEquals(0, response.getInitCalls());
        assertEquals(0, response.getDestroyCalls());
        assertFalse(response.isProviderInit());

        response = port.getInstanceInfo(null);
        assertEquals(0, response.getInstances());
        assertEquals(0, response.getInitCalls());
        assertEquals(0, response.getDestroyCalls());
        assertFalse(response.isProviderInit());

        response = port.getInstanceInfo(null);
        assertEquals(0, response.getInstances());
        assertEquals(0, response.getInitCalls());
        assertEquals(0, response.getDestroyCalls());
        assertFalse(response.isProviderInit());

        // Request scope tests - numInstaces should increase

        url = new URL(TEST_CONTAINER.getBaseURL() +
                      TestService.SERVICE_PATH + "Request");

        port = locator.getTestPortTypePort(url);

        port.resetNumInstances(null);

        response = port.getInstanceInfo(null);
        assertEquals(1, response.getInstances());
        assertEquals(1, response.getInitCalls());
        assertEquals(1, response.getDestroyCalls());
        assertFalse(response.isProviderInit());

        response = port.getInstanceInfo(null);
        assertEquals(2, response.getInstances());
        assertEquals(2, response.getInitCalls());
        assertEquals(2, response.getDestroyCalls());
        assertFalse(response.isProviderInit());

        response = port.getInstanceInfo(null);
        assertEquals(3, response.getInstances());
        assertEquals(3, response.getInitCalls());
        assertEquals(3, response.getDestroyCalls());
        assertFalse(response.isProviderInit());
    }

    public void testLocalInvocation() throws Exception {
        URL url = new URL(TEST_CONTAINER.getBaseURL() +
                          TestService.SERVICE_PATH);

        TestServiceAddressingLocator locator =
            new TestServiceAddressingLocator();

        TestPortType port = locator.getTestPortTypePort(url);

        TestLocalInvocation request =
            new TestLocalInvocation();
        port.testLocalInvocation(request);
    }


    public void testSimpleFault() throws Exception {
        URL url = new URL(TEST_CONTAINER.getBaseURL() +
                          TestService.SERVICE_PATH);

        TestServiceAddressingLocator locator =
            new TestServiceAddressingLocator();

        TestPortType port = locator.getTestPortTypePort(url);

        try {
            CreateResourceResponse response = port.createResource(null);
            fail("did not throw an exception");
        } catch (NoPermissionFault e) {
            // that's what we want
        }
    }

}

