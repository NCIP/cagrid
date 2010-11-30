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
package org.globus.wsrf.tools.wsdd;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.StringTokenizer;

import org.xml.sax.InputSource;

import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;

import org.globus.wsrf.utils.XmlUtils;

public class TestWSDDEditor extends TestCase {

    static final String testServerConfig1 =
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
	+ "<deployment xmlns=\"http://xml.apache.org/axis/wsdd/\" "
	+ "xmlns:java=\"http://xml.apache.org/axis/wsdd/providers/java\">"
	+ " <globalConfiguration>   <parameter name=\"sendMultiRefs\""
	+ " value=\"true\"/> <parameter name=\"adminPassword\" "
	+ "value=\"admin\"/> <parameter name=\"sweeperThreads\" "
	+ "value=\"3\"/> </globalConfiguration> "
	+ " <service name=\"core/logging/OgsiLoggingManagementService\""
	+ " provider=\"Handler\" style=\"wrapped\" use=\"literal\">"
	+ " <parameter name=\"logBufferSize\" value=\"12\"/> "
	+ " <parameter name=\"persistent\" value=\"true\"/> "
	+ "  <parameter name=\"allowedMethods\" value=\"*\"/> </service>"
	+ " <service name=\"core/admin/AdminService\""
	+ " provider=\"Handler\" style=\"wrapped\" use=\"literal\">"
	+ " <parameter name=\"persistent\" value=\"false\"/> "
	+ "  <parameter name=\"allowedMethods\" value=\"random\"/> </service>"
	+ " </deployment>";

    public TestWSDDEditor(String name){
	super(name);
    }

    public static Test suite() {
        return new TestSuite(TestWSDDEditor.class);
    }

    public void testWSDDEditor() throws Exception {
        InputSource is =
            new InputSource(new ByteArrayInputStream(
				testServerConfig1.getBytes()));
        Document doc = XmlUtils.newDocument(is);
	WSDDEditor testEditor = new WSDDEditor(doc);

	// get on a good global param
	String value = testEditor.getGlobalParameter("adminPassword");
	assertTrue(value.equals("admin"));
	
	// get on bad global param.
	assertTrue(testEditor.getGlobalParameter("dummyparam") == null);
	
	// get all global parameters.
	String globalProperties[] = testEditor.getGlobalProperties();
	assertTrue(globalProperties != null);
	assertTrue(globalProperties.length == 3);
	assertTrue(validateProperty(globalProperties[2], "sendMultiRefs",
				    "true"));
	assertTrue(validateProperty(globalProperties[1], "sweeperThreads",
				    "3"));
	assertTrue(validateProperty(globalProperties[0], "adminPassword",
				    "admin"));

	// set on existing global param.
	testEditor.setGlobalParameter("adminPassword", "testPass");
	String val = testEditor.getGlobalParameter("adminPassword");
	assertTrue(val.equals("testPass"));

	// set on new global param
	testEditor.setGlobalParameter("newDummyParam", "testPass");

	// get on the new global param
	String newTestVal = testEditor.getGlobalParameter("newDummyParam");
	assertTrue(newTestVal.equals("testPass"));

	// add to the new param
	testEditor.addGlobalParameter("newDummyParam", "testPass2");
	String appendedVal = testEditor.getGlobalParameter("newDummyParam");
	assertTrue(appendedVal.equals("testPass,testPass2"));

	// get good param from a service
	String serviceName1 = "core/logging/OgsiLoggingManagementService";
	val = testEditor.getServiceParameter(serviceName1, "logBufferSize");
	assertTrue(val.equals("12"));
	
	// get bad param from service.
	val = testEditor.getServiceParameter(serviceName1, "dummyNewParam");
	assertTrue(val == null);

	// properties on service.
	String serviceProps[] = testEditor.getServiceProperties(serviceName1);
	assertTrue(serviceProps != null);
	assertTrue(serviceProps.length == 3);
	assertTrue(validateProperty(serviceProps[0], "allowedMethods",
				    "*"));
	assertTrue(validateProperty(serviceProps[1], "persistent",
				    "true"));
	assertTrue(validateProperty(serviceProps[2], "logBufferSize",
				    "12"));

	// set on existing service param
	testEditor.setServiceParameter(serviceName1, "logBufferSize", "100");
	val = testEditor.getServiceParameter(serviceName1, "logBufferSize");
	assertTrue(val.equals("100"));
	
	// set on new service param
	testEditor.setServiceParameter(serviceName1, "logNewParam","dummyVal");
	
	// get on new service param
	String newServiceVal = 
	    testEditor.getServiceParameter(serviceName1, "logNewParam");
	assertTrue(newServiceVal.equals("dummyVal"));

	// add to the new param
	testEditor.addServiceParameter(serviceName1, "logNewParam",
				       "dummyVal2");
	String newServiceVal1 = 
	    testEditor.getServiceParameter(serviceName1, "logNewParam");
	assertTrue(newServiceVal1.equals("dummyVal,dummyVal2"));
    }

    private boolean validateProperty(String val, String name, String value) {
	if (val == null)
	    return false;
	try {
	    StringTokenizer strtok = new StringTokenizer(val, "=");
	    if (strtok.countTokens() != 2)
		return false;
	    if (!name.equals(strtok.nextToken()))
		return false;
	    if (!value.equals(strtok.nextToken()))
		return false;
	} catch (Exception exp) {
	    fail("Unable to parse string" + exp);
	}
	return true;
    }
}
