package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.Workflows;

import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.hsqldb.lib.StringInputStream;
import org.junit.Test;

public class MyExperimentRegistryTest {

	private MyExperimentWorkflowRegistry reg = new MyExperimentWorkflowRegistry("kedzie", "Catch-22", "sandbox.myexperiment.org");
	
	@Test
	public void testJAXB() throws JAXBException {
		String xml = "<workflow uri=\"http://sandbox.myexperiment.org/workflow.xml?id=1288\" resource=\"http://sandbox.myexperiment.org/workflows/1288\" version=\"1\"><id>1288</id><title>A simple CQL query workflow in caGrid</title><description>&lt;p&gt;1. CQL is a language to query data from caGrid/caBIG services. This workflow is tested with Taverna 2.1.2 and the caGrid Workflow Suite downloadable from http://www.mcs.anl.gov/~wtan/t2/.&lt;/p&gt;&lt;p&gt;2.More information regarding CQL can be found from http://wiki.cagrid.org/display/dataservices.&lt;/p&gt;&lt;p&gt;3. Sample input (95) is provided in the workflow. It is to query all the hybridization data within a microarray experiment whose id is 95.&lt;/p&gt;</description><type uri=\"http://sandbox.myexperiment.org/type.xml?id=2\">Taverna 2</type><uploader resource=\"http://sandbox.myexperiment.org/users/1019\" uri=\"http://sandbox.myexperiment.org/user.xml?id=1019\">Wei Tan</uploader><created-at>Mon May 24 23:52:05 +0100 2010</created-at><preview>http://sandbox.myexperiment.org/workflow/image/1288/_untitled_.png</preview><svg>http://sandbox.myexperiment.org/workflow/svg/1288/_untitled_.svg.xml</svg><license-type uri=\"http://sandbox.myexperiment.org/license.xml?id=2\" resource=\"http://sandbox.myexperiment.org/licenses/2\">by-sa</license-type><content-uri>http://sandbox.myexperiment.org/workflows/1288/download/_untitled__34328.t2flow</content-uri><content-type>application/vnd.taverna.t2flow+xml</content-type><tags><tag uri=\"http://sandbox.myexperiment.org/tag.xml?id=1069\" resource=\"http://sandbox.myexperiment.org/tags/1069\">cagrid</tag><tag uri=\"http://sandbox.myexperiment.org/tag.xml?id=1068\" resource=\"http://sandbox.myexperiment.org/tags/1068\">cabig</tag><tag uri=\"http://sandbox.myexperiment.org/tag.xml?id=305\" resource=\"http://sandbox.myexperiment.org/tags/305\">microarray</tag><tag uri=\"http://sandbox.myexperiment.org/tag.xml?id=248\" resource=\"http://sandbox.myexperiment.org/tags/248\">hybridization</tag><tag uri=\"http://sandbox.myexperiment.org/tag.xml?id=1907\" resource=\"http://sandbox.myexperiment.org/tags/1907\">cql</tag></tags></workflow>";
		WorkflowDescription wd = Workflows.unmarshalWorkflow(new StringInputStream(xml));
		assertNotNull(wd);
		assertEquals("1288", wd.getWorkflowId());
		xml = "<workflows><workflow resource=\"http://sandbox.myexperiment.org/workflows/598\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=598\" version=\"1\">caDSR metadata query in caGrid</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/599\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=599\" version=\"1\">hierarchical microarray clustering</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/600\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=600\" version=\"1\">Using CQL to query protein sequence data</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/746\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=746\" version=\"7\"> Lymphoma type prediction based on microarray data </workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/752\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=752\" version=\"1\">Using CQL to query protein sequence data</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/963\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=963\" version=\"1\">caArray data retrieving</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/964\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=964\" version=\"2\">genePattern data preprocessing</workflow></workflows>";
		List<Workflows.WorkflowStub> workflows = Workflows.unmarshalWorkflows(new StringInputStream(xml)); 
		assertNotNull(workflows);
	}
	
	@Test
	public void testSessionCookie() {
		reg.getSessionCookie();
	}
	
	@Test
	public void testGetWorkflows() throws WorkflowException {
		Map<String, WorkflowDescription> workflows = reg.getWorkflows();
		assertNotNull(workflows);
	}
	
	@Test
	public void testGetWorkflow() throws WorkflowException {
		WorkflowDescription wd = reg.getWorkflow("1508");
		assertNotNull(wd);
		assertEquals("1508", wd.getWorkflowId());
	}
}
