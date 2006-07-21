/*
 * Created on Apr 12, 2006
 */
package gov.nci.nih.cagrid.tests.core;

import gov.nci.nih.cagrid.tests.core.steps.AdvertiseServiceStep;
import gov.nci.nih.cagrid.tests.core.steps.CleanupTempGlobusStep;
import gov.nci.nih.cagrid.tests.core.steps.CreateTempGlobusStep;
import gov.nci.nih.cagrid.tests.core.steps.DeployGlobusServiceStep;
import gov.nci.nih.cagrid.tests.core.steps.ServiceDiscoveryStep;
import gov.nci.nih.cagrid.tests.core.steps.StartGlobusStep;
import gov.nci.nih.cagrid.tests.core.steps.StopGlobusStep;

import java.io.File;
import java.util.Vector;

import org.apache.axis.types.URI.MalformedURIException;

import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class IndexServiceTest
	extends AbstractServiceTest
{
	public IndexServiceTest()
	{
		super();
	}
	
	@SuppressWarnings("unchecked")
	protected Vector steps() 
	{
		super.init("BasicAnalyticalServiceWithMetadata");

		File indexServiceDir = new File(System.getProperty("index.dir",
			".." + File.separator + ".." + File.separator + ".." + File.separator + 
			"caGrid" + File.separator + "projects" + File.separator + "index"
		));

		Vector steps = new Vector();
		steps.add(createServiceStep);
		steps.add(new CreateTempGlobusStep(globus));
		steps.add(new DeployGlobusServiceStep(globus, createServiceStep.getServiceDir()));
		try {
			steps.add(new AdvertiseServiceStep(port, serviceDir));
		} catch (MalformedURIException e) {
			throw new IllegalArgumentException("could not add advertise steps", e);
		}
		steps.add(new DeployGlobusServiceStep(globus, indexServiceDir, "deployIndexGlobus"));		
		steps.add(new StartGlobusStep(globus, port));
		try {
			steps.add(new ServiceDiscoveryStep(port, super.endpoint, super.metadataFile));
		} catch (Exception e) {
			throw new IllegalArgumentException("could not add discovery step", e);
		}
		//steps.add(new CheckServiceMetadataStep(endpoint, metadataFile));
		steps.add(new StopGlobusStep(globus, port));
		steps.add(new CleanupTempGlobusStep(globus));
		return steps;
	}

	public String getDescription()
	{
		return "BasicAnalyticalServiceTest";
	}

	/**
	 * Convenience method for running all the Steps in this Story.
	 */
	public static void main(String args[]) {
		TestRunner runner = new TestRunner();
		TestResult result = runner.doRun(new TestSuite(IndexServiceTest.class));
		System.exit(result.errorCount() + result.failureCount());
	}
}
