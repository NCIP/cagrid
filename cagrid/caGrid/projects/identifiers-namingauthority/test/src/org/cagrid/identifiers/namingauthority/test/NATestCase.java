package org.cagrid.identifiers.namingauthority.test;

/*
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.identifiers.namingauthority.NamingAuthority;
import org.cagrid.identifiers.namingauthority.NamingAuthorityLoader;
import org.cagrid.identifiers.namingauthority.impl.IdentifierValuesImpl;
//import org.cagrid.identifiers.resolver.ResolverUtil;

import junit.framework.TestCase;

public class NATestCase extends TestCase {
    private static Log log = LogFactory.getLog(NATestCase.class);
    
	private static String url1 = "http://cagrid.org";
	private static String url2 = "http://www.osu.edu";
	private static String epr1 = "end point reference 1";
    
    private IdentifierValuesImpl getIdenfierValues() {

    	IdentifierValuesImpl values = new IdentifierValuesImpl();
    	values.add("URL", url1);
    	values.add("URL", url2);
    	values.add("EPR", epr1);
    	
    	return values;
    }
    
    public void testIdenfierValues() {

    	
    	IdentifierValuesImpl values = getIdenfierValues();

    	
    	////////////////////////////////////////////////////////////////////
    	// Test getTypes()
    	////////////////////////////////////////////////////////////////////
    	String[] types = values.getTypes();
    	if (types.length != 2) {
    		fail("Expected two data types (URL, EPR). Got " + types.length);
    	}
    	
    	List<String> typeList = Arrays.asList(types);
    	if (!typeList.contains("URL")) {
    		fail("No URL found in getTypes() list");
    	}
    	if (!typeList.contains("EPR")) {
    		fail("No EPR found in getTypes() list");
    	}

    	
    	////////////////////////////////////////////////////////////////////
    	// Test getValues()
    	////////////////////////////////////////////////////////////////////
  	   	HashMap<String, ArrayList<String>> map = values.getValues();
      	if (!map.containsKey("URL")) {
    		fail("No URL key found in map");
    	}
    	
    	if (!map.containsKey("EPR")) {
    		fail("No EPR key found in map");
    	}
    	
    	////////////////////////////////////////////////////////////////////
    	// Test getValues(String type)
    	////////////////////////////////////////////////////////////////////
    	String[] data = values.getValues("URL");
    	if (data.length != 2) {
    		fail("Expected 2 URLs, found " + data.length);
    	}
    	List<String> dataList = Arrays.asList(data);
    	if (!dataList.contains(url1)) {
    		fail( url1 + " not found in the data list");
    	}
    	if (!dataList.contains(url2)) {
    		fail( url2 + " not found in the data list");
    	}
    	
    	data = values.getValues( "EPR" );
    	if (data.length != 1){
    		fail("Expected 1 EPR, found " + data.length);
    	}
    	if (!data[0].equals(epr1)) {
    		fail(epr1 + " not found in the data list");
    	}
    	
    	log.info( "testIdenfierValues passed");
    }
    
//	public void testNA() {
//		NamingAuthority na = new NamingAuthorityLoader().getNamingAuthority();
//		
//		IdentifierValuesImpl values = new IdentifierValuesImpl();
//		values.
//		na.createIdentifier(values)
//		try {
//			IdentifierValuesImpl ivs = ResolverUtil.resolveGrid(purl);
//			System.out.println(ivs.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}

   public static void main(String[] args) {
      junit.textui.TestRunner.run(NATestCase.class);
   }
}

