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
package org.globus.net.test;

import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import org.globus.net.GlobusURLStreamHandlerFactory;
import org.globus.net.GSIURLConnection;
import org.globus.net.GSIHttpURLConnection;
import org.globus.gsi.GSIConstants;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

// Needs to be improved - parameters loaded from cfg file 
public class GSIHttpURLConnectionTest extends TestCase {
    
    static {
        URL.setURLStreamHandlerFactory(new GlobusURLStreamHandlerFactory());
    }

    public GSIHttpURLConnectionTest(String name) {
        super(name);
    }
    
    public static void main (String[] args) {
        junit.textui.TestRunner.run (suite());
    }
    
    public static Test suite() {
        return new TestSuite(GSIHttpURLConnectionTest.class);
    }
    
    public void test1() throws Exception {
        URL u = new URL("httpg://pitcairn.mcs.anl.gov:2119/jobmanager");
        
        URLConnection con = u.openConnection();
        
        assertTrue(con instanceof GSIURLConnection);
        
        try {
            InputStream in = con.getInputStream();
            fail("did not throw exception");
        } catch (IOException e) {
            // everything is cool
        } finally {
            ((GSIURLConnection)con).disconnect();
        }
        
    }
    
    public void test2() throws Exception {
        URL u = new URL("httpg://pitcairn.mcs.anl.gov:2119/jobmanager");
        
        URLConnection con = u.openConnection();
        
        assertTrue(con instanceof GSIURLConnection);
        
        ((GSIURLConnection)con).setDelegationType(GSIConstants.DELEGATION_FULL);
        
        try {
            InputStream in = con.getInputStream();
            fail("did not throw exception");
        } catch (IOException e) {
            // everything is cool
        } finally {
            ((GSIURLConnection)con).disconnect();
        }
        
    }
    
    /*
    public void testGET() throws Exception {
        URL url = new URL("https://localhost:8443/wsrf/services/ContainerRegistryService?wsdl");
        GSIHttpURLConnection con = new GSIHttpURLConnection(url);
        
        ((GSIURLConnection)con).setGSSMode(GSIConstants.MODE_SSL);
        
        InputStream in = con.getInputStream();
        int ch;
        StringBuffer buf = new StringBuffer();
        while( (ch = in.read()) != -1 ) {
            System.out.print((char)ch);
            buf.append((char)ch);
        }

        con.disconnect();

        assertTrue(buf.toString().indexOf("wsdl:import") != -1);
    }


    public void testPUT() throws Exception {
        URL url = new URL("https://localhost:8443/wsrf/services/ContainerRegistryService");
        GSIHttpURLConnection con = new GSIHttpURLConnection(url);
        
        ((GSIURLConnection)con).setGSSMode(GSIConstants.MODE_SSL);
        
        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><GetResourceProperty xmlns=\"http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd\" xmlns:ns1=\"http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd\">ns1:Entry</GetResourceProperty></soapenv:Body></soapenv:Envelope>";
        
        OutputStream out = con.getOutputStream();
        out.write(request.getBytes("UTF8"));
        out.flush();

        InputStream in = con.getInputStream();
        int ch;
        StringBuffer buf = new StringBuffer();
        while( (ch = in.read()) != -1 ) {
            System.out.print((char)ch);
            buf.append((char)ch);
        }   
        
        con.disconnect();

        assertTrue(buf.toString().indexOf("ServiceGroupEntryEPR") != -1);
    }
    */

}
