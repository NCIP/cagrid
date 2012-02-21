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
package org.globus.wsrf.impl;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.globus.wsrf.test.TestContainer;
import org.globus.wsrf.test.GridTestCase;
import junit.framework.TestResult;
import org.globus.wsrf.test.GSITestContainer;

import org.globus.wsrf.impl.security.authentication.Constants;

public class SecurityTests extends TestSuite {

    protected TestContainer testContainer;

    public static void main(String args[]) throws Exception { 
	junit.textui.TestRunner.run(suite());
    }

    String mechanism = null;

    public SecurityTests(String name) {
        super(name);
        
        String securityType = (String)System.getProperty("securityType");
        Object protection = null;
        if (securityType != null) { 
            if (securityType.equalsIgnoreCase("message")) {
                mechanism = Constants.GSI_SEC_MSG;
            } else if (securityType.equalsIgnoreCase("conversation")) {
                mechanism = Constants.GSI_SEC_CONV;
            } else if (securityType.equalsIgnoreCase("transport")) {
                mechanism = Constants.GSI_TRANSPORT;
            } 
         
            String protType = (String)System.getProperty("protectionType");
            if ((protType != null) && 
                (protType.equalsIgnoreCase("signature"))) {
                protection = Constants.SIGNATURE;
            } else if ((protType != null) && 
                       (protType.equalsIgnoreCase("encryption"))) {
                protection = Constants.ENCRYPTION;
            } else {
                protection = Constants.SIGNATURE;
            }
        }

        BasicPerformanceTests.setSecurityReq(mechanism, protection);
    }

    public void run(TestResult result) {

        if (Constants.GSI_TRANSPORT.equals(mechanism)) {
            this.testContainer = new GSITestContainer();
        } else {
            this.testContainer = new TestContainer();
        }
            
        try {
            this.testContainer.start();
            GridTestCase.setTestServer(this.testContainer);
            super.run(result);
        }
        catch(Exception e) {
            result.addError(this, e);
        }
        finally {
            try {
                this.testContainer.stop();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Test suite() throws Exception {
        TestSuite suite = new SecurityTests("BasicSecurityPerformanceTests");
        suite.addTestSuite(BasicPerformanceTests.class);
        return suite;
    }
}
