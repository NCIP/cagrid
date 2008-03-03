package gov.nih.nci.cagrid.data.upgrades.from1pt1;

import gov.nih.nci.cagrid.data.creation.DataTestCaseInfo;
import gov.nih.nci.cagrid.data.creation.DeleteOldServiceStep;
import gov.nih.nci.cagrid.data.upgrades.from1pt0.BuildUpgradedServiceStep;
import gov.nih.nci.cagrid.data.upgrades.from1pt0.UnzipOldServiceStep;
import gov.nih.nci.cagrid.data.upgrades.from1pt0.UpgradeIntroduceServiceStep;
import gov.nih.nci.cagrid.introduce.test.TestCaseInfo;
import gov.nih.nci.cagrid.testing.system.haste.Step;
import gov.nih.nci.cagrid.testing.system.haste.Story;

import java.io.File;
import java.util.Vector;

import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/** 
 *  UpgradeTo1pt1Tests
 *  Tests to upgrade a data service from 1.1 to 1.2
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>  * 
 * @created Feb 20, 2007 
 * @version $Id: UpgradeTo1pt2Tests.java,v 1.2 2008-03-03 14:49:26 dervin Exp $ 
 */
public class UpgradeTo1pt2Tests extends Story {
    public static final String TEST_DIR = ".." + File.separator + "data" + File.separator + "test";
    public static final String SERVICE_ZIP_NAME = "BasicDataService_1-1.zip";
    
	public static final String SERVICE_DIR = TEST_DIR + File.separator + "BasicDataService_1-1";
    public static final String SERVICE_NAME = "BasicDataService";
    public static final String SERVICE_PACKAGE = "gov.nih.nci.cagrid.basic.data.service";
    public static final String SERVICE_NAMESPACE = "http://service.data.basic.cagrid.nci.nih.gov/BasicDataService";
    
	
	public String getDescription() {
		return "Tests upgrade of a data service from version 1.1 to 1.2";
	}
    
    
    public String getName() {
        return "Data Service 1_1 to 1_2 Upgrade Tests";
    }
	

	protected Vector steps() {
        TestCaseInfo info = new DataTestCaseInfo() {
        	public String getDir() {
    	        return UpgradeTo1pt2Tests.SERVICE_DIR;
    	    }


    	    public String getName() {
    	        return SERVICE_NAME;
    	    }


    	    public String getNamespace() {
    	        return SERVICE_NAMESPACE;
    	    }


    	    public String getPackageName() {
    	        return SERVICE_PACKAGE;
    	    }
        };
		Vector<Step> steps = new Vector<Step>();
		// steps to unpack and upgrade the old service
		steps.add(new DeleteOldServiceStep(info));
		steps.add(new UnzipOldServiceStep(TEST_DIR, SERVICE_ZIP_NAME));
		steps.add(new UpgradeIntroduceServiceStep(info.getDir()));
		steps.add(new BuildUpgradedServiceStep(info.getDir()));
		
		return steps;
	}
	
	
	// used to make sure that if we are going to use a junit testsuite to 
	// test this that the test suite will not error out 
	// looking for a single test......
	public void testDummy() throws Throwable {
	}


	public static void main(String[] args) {
		TestRunner runner = new TestRunner();
		TestResult result = runner.doRun(new TestSuite(UpgradeTo1pt2Tests.class));
		System.exit(result.errorCount() + result.failureCount());
	}
}
