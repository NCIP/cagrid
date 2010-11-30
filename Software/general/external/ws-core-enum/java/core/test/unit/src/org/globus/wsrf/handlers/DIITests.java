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

import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.wsrf.impl.TestService;
import org.globus.wsrf.test.GridTestCase;
import org.globus.wsrf.tests.basic.CreateResourceResponse;

public class DIITests extends GridTestCase {

    public DIITests(String name) {
        super(name);
    }

    public void testDIINoWSDLService() throws Exception {
        String base = TEST_CONTAINER.getBaseURL() + "Version";
        String wsdlLocation = base + "?wsdl";

        ServiceFactory factory = ServiceFactory.newInstance();
        Service service = factory.createService(
                          new URL(wsdlLocation),
                          new QName(base, "VersionService"));

        Call[] calls = service.getCalls(new QName(base, "Version"));
        assertTrue(calls != null);
        assertEquals(1, calls.length);

        ((org.apache.axis.client.Call)calls[0]).setSOAPActionURI(null);

        Object ret = calls[0].invoke(new Object[] {});

        System.out.println("Version: " + ret);

        assertTrue(ret != null);
        assertTrue( ret.toString().indexOf("Apache Axis") != -1);
    }

    /**
     * Test with JAXRPC DII. Also tests wsdl parsing and retrival via
     * ?wsdl HTTP GET call.
     */
    public void testDIIWSDLService() throws Exception {
        String wsdlLocation = TEST_CONTAINER.getBaseURL() + 
            TestService.SERVICE_PATH + "?wsdl";
        diiTest(wsdlLocation, null);
    }

    public void diiTest(String wsdlLocation, Map props) throws Exception {
        String portTypeNS = "http://wsrf.globus.org/tests/basic";
        String serviceNS = portTypeNS + "/service";
        String serviceName = "TestService";
        String portType = "TestPortTypePort";

        ServiceFactory factory = ServiceFactory.newInstance();
        Service service = factory.createService(new URL(wsdlLocation),
                                                new QName(serviceNS, 
                                                          serviceName));

        TypeMappingRegistry registry = service.getTypeMappingRegistry();
        TypeMapping map = registry.getDefaultTypeMapping();

        QName qname = 
            new QName("http://wsrf.globus.org/tests/basic", 
                      "createResourceResponse");
        Class clazz = CreateResourceResponse.class;
                                
        map.register(clazz,
                     qname,
                     new org.apache.axis.encoding.ser.BeanSerializerFactory(clazz, qname),
                     new org.apache.axis.encoding.ser.BeanDeserializerFactory(clazz, qname));

        Call[] calls = service.getCalls(new QName(portTypeNS + "/bindings", 
                                                  portType));
        assertTrue(calls != null);
        assertEquals(10, calls.length);

        boolean found = false;
        int i = 0;
        for (i=0;i<calls.length;i++) {
            System.out.println("A: " + calls[i].getOperationName());
            if (calls[i].getOperationName().getLocalPart().equals("createResource")) {
                found = true;
                break;
            }
        }

        assertTrue(found);

        if (props != null) {
            Iterator iter = props.keySet().iterator();
            while(iter.hasNext()) {
                String key = (String)iter.next();
                Object value = props.get(key);
                calls[i].setProperty(key, value);
            }
        }

        // Shouldn't have to do that....
        ((org.apache.axis.client.Call)calls[i]).setOperationName(new QName(portTypeNS, "createResource"));
        ((org.apache.axis.client.Call)calls[i]).setReturnClass(clazz);

        Object ret = calls[i].invoke(new Object[] {});

        System.out.println(ret);

        assertTrue(ret != null);
        assertTrue(ret instanceof CreateResourceResponse);
        EndpointReferenceType epr = 
            ((CreateResourceResponse)ret).getEndpointReference();
        assertTrue(epr != null);
        assertTrue("Address field", epr.getAddress() != null);
    }


    public void testRPCService() throws Exception {
        String base = TEST_CONTAINER.getBaseURL() + "TestRPCService";
        String wsdlLocation = base + "?wsdl";

        String ns = 
            "http://129.215.30.251:8080/wsrf/services/TestRPCService";

        ServiceFactory factory = ServiceFactory.newInstance();
        Service service = factory.createService(
                          new URL(wsdlLocation),
                          new QName(ns, "RPCServiceService"));

        Call[] calls = service.getCalls(new QName(ns, "TestRPCService"));
        assertTrue(calls != null);
        assertEquals(1, calls.length);

        ((org.apache.axis.client.Call)calls[0]).setSOAPActionURI(null);

        Object ret = calls[0].invoke(new Object[] {"abc", "def"});

        System.out.println("Version: " + ret);

        assertTrue(ret != null);
        assertTrue( ret.toString().indexOf("abcdef") != -1);
    }
}
