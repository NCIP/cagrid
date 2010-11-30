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
package org.globus.wsrf.impl.properties;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;

import org.apache.axis.AxisFault;
import org.apache.axis.Constants;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.encoding.DialectDependentSerializer;
import org.globus.wsrf.impl.TestHome;
import org.globus.wsrf.impl.TestResource;
import org.globus.wsrf.tests.basic.TestPortType;
import org.globus.wsrf.utils.AnyHelper;
import org.oasis.wsrf.properties.InvalidQueryExpressionFaultType;
import org.oasis.wsrf.properties.QueryEvaluationErrorFaultType;
import org.oasis.wsrf.properties.QueryExpressionType;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;
import org.oasis.wsrf.properties.ResourceUnknownFaultType;
import org.oasis.wsrf.properties.UnknownQueryExpressionDialectFaultType;
import org.w3c.dom.Node;

public class QueryResourcePropertiesTests extends PropertiesTestCase {

    private final static String SIMPLE_QUERY = 
        "boolean(/*/*[namespace-uri()='" + WSRFConstants.LIFETIME_NS + "' and local-name()='TerminationTime'])";

    // return the entire RP document
    private final static String COMPLEX_QUERY =
        "/";

    public QueryResourcePropertiesTests(String name) {
        super(name);
    }

    private QueryExpressionType createQueryExpression(String dialect,
                                                      String queryString) 
        throws IOException {
        QueryExpressionType query = new QueryExpressionType();
        query.setDialect(dialect);
        
        if (queryString != null) {
            query.setValue(queryString);
        }

        return query;
    }
    
    public void testUnknownResource() throws Exception {
        TestPortType port = 
            locator.getTestPortTypePort(createEPR(TestHome.BAD_KEY));

        QueryResourceProperties_Element queryRequest = new QueryResourceProperties_Element();
        queryRequest.setQueryExpression(
                 createQueryExpression(WSRFConstants.XPATH_1_DIALECT, 
                                       COMPLEX_QUERY)
        );

        try {
            QueryResourcePropertiesResponse queryResponse =
                port.queryResourceProperties(queryRequest);
            fail("Did not throw exception");
        } catch (ResourceUnknownFaultType e) {
            //it's ok
        }
    }

    public void testUnknownQueryExpressionDialect() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        QueryResourceProperties_Element queryRequest = new QueryResourceProperties_Element();
        String dialect = "http://foobar";
        queryRequest.setQueryExpression(
                 createQueryExpression(dialect, COMPLEX_QUERY)
        );

        // register client mapping for fake dialect
        TypeMappingRegistry tmr = ((Service)locator).getTypeMappingRegistry();
        TypeMapping tm = tmr.getTypeMapping("");

        DialectDependentSerializer sf = 
            (DialectDependentSerializer)tm.getSerializer(
                                  QueryExpressionType.class, 
                                  null).getSerializerAs("");
        sf.registerSerializerFactory(new URI(dialect), 
                                     new SimpleSerializerFactory(
                                         String.class,
                                         Constants.XSD_STRING));

        try {
            QueryResourcePropertiesResponse queryResponse =
                port.queryResourceProperties(queryRequest);
            fail("Did not throw exception");
        } catch (UnknownQueryExpressionDialectFaultType e) {
            //it's ok
        }
    }

    public void testQueryEvaluationError() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        QueryResourceProperties_Element queryRequest = new QueryResourceProperties_Element();
        queryRequest.setQueryExpression(
                 createQueryExpression(WSRFConstants.XPATH_1_DIALECT, "\\")
        );

        try {
            QueryResourcePropertiesResponse queryResponse =
                port.queryResourceProperties(queryRequest);
            fail("Did not throw exception as expected");
        } catch (QueryEvaluationErrorFaultType e) {
            //it's ok
        }
    }

    public void testInvalidQueryExpression() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        QueryResourcePropertiesResponse queryResponse = null;

        QueryResourceProperties_Element queryRequest = new QueryResourceProperties_Element();
        queryRequest.setQueryExpression(
                 createQueryExpression(WSRFConstants.XPATH_1_DIALECT, "")
        );

        try {
            queryResponse = port.queryResourceProperties(queryRequest);
            fail("Did not throw exception as expected");
        } catch (InvalidQueryExpressionFaultType e) {
            //it's ok
        }

        queryRequest.setQueryExpression(
                 createQueryExpression(WSRFConstants.XPATH_1_DIALECT, null)
        );

        try {
            queryResponse = port.queryResourceProperties(queryRequest);
            fail("Did not throw exception as expected");
        } catch (InvalidQueryExpressionFaultType e) {
            //it's ok
        }
    }

    // these hopefully oneday be more specific in the spec
    public void testOtherErrors() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        QueryResourcePropertiesResponse queryResponse = null;

        QueryResourceProperties_Element queryRequest = null;

        // no request
        try {
            queryResponse = port.queryResourceProperties(queryRequest);
            fail("Did not throw exception as expected");
        } catch (AxisFault e) {
            // TODO: very unspecific
        }

        queryRequest = new QueryResourceProperties_Element();

        try {
            queryResponse = port.queryResourceProperties(queryRequest);
            fail("Did not throw exception as expected");
        } catch (AxisFault e) {
            // TODO: very unspecific
        }

        queryRequest.setQueryExpression(new QueryExpressionType());

        try {
            queryResponse = port.queryResourceProperties(queryRequest);
            fail("Did not throw exception as expected");
        } catch (AxisFault e) {
            // TODO: very unspecific
        }
    }

    public void testSimpleContentQuery() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));
        
        QueryResourceProperties_Element queryRequest = new QueryResourceProperties_Element();

        queryRequest.setQueryExpression(
                 createQueryExpression(WSRFConstants.XPATH_1_DIALECT,
                                       SIMPLE_QUERY)
        );

        QueryResourcePropertiesResponse queryResponse =
            port.queryResourceProperties(queryRequest);
        
        MessageElement [] any = queryResponse.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);
        assertEquals(Node.TEXT_NODE, any[0].getNodeType());
        assertEquals("true", any[0].getNodeValue());
    }
    
    public void testComplexContentQuery() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));
        
        QueryResourceProperties_Element queryRequest = new QueryResourceProperties_Element();

        queryRequest.setQueryExpression(
                 createQueryExpression(WSRFConstants.XPATH_1_DIALECT,
                                       COMPLEX_QUERY)
        );

        QueryResourcePropertiesResponse queryResponse =
            port.queryResourceProperties(queryRequest);
        
        MessageElement [] any = queryResponse.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);

        System.out.println(AnyHelper.toSingleString(any));

        QName [] propNames =
            new QName[] {TestResource.VALUE_RP,
                         WSRFConstants.CURRENT_TIME};

        boolean found = false;
        for (int i=0;i<propNames.length;i++) {
            found = false;
            Iterator iter = any[0].getChildElements();
            while(iter.hasNext()) {
                if (propNames[i].equals(((MessageElement)iter.next()).getQName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("Did not return expected RP: " + propNames[i]);
            }
        }
    }

    public void testCustomQueryDialect() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));
        
        QueryResourceProperties_Element queryRequest = 
            new QueryResourceProperties_Element();

        queryRequest.setQueryExpression(
                 createQueryExpression(TestExpressionEvaluator.DIALECTS[0],
                                       TestExpressionEvaluator.TEST_QUERY)
        );

        QueryResourcePropertiesResponse queryResponse =
            port.queryResourceProperties(queryRequest);
        
        MessageElement [] any = queryResponse.get_any();
        assertTrue(any != null);
        assertTrue(any.length > 0);
        assertEquals(Node.TEXT_NODE, any[0].getNodeType());
        assertEquals(TestExpressionEvaluator.TEST_QUERY_RESPONSE, 
                     any[0].getNodeValue());
    }    
}
