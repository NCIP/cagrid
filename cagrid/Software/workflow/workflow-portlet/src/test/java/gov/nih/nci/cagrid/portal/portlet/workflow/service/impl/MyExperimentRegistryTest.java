package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

public class MyExperimentRegistryTest {

	private MyExperimentWorkflowRegistry reg;
	
	public MyExperimentRegistryTest() {
		reg = new MyExperimentWorkflowRegistry();
		reg.setUsername("kedzie"); 
		reg.setPassword("Catch-22");
		reg.setServer("sandbox.myexperiment.org");
	}
	@Test
	public void testJAXB() throws WorkflowException {
		String xml = "<?xml version=\"1.0\" ?><workflows><workflow resource=\"http://sandbox.myexperiment.org/workflows/598\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=598\" version=\"1\">caDSR metadata query in caGrid</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/599\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=599\" version=\"1\">hierarchical microarray clustering</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/600\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=600\" version=\"1\">Using CQL to query protein sequence data</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/746\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=746\" version=\"7\"> Lymphoma type prediction based on microarray data </workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/752\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=752\" version=\"1\">Using CQL to query protein sequence data</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/963\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=963\" version=\"1\">caArray data retrieving</workflow><workflow resource=\"http://sandbox.myexperiment.org/workflows/964\" uri=\"http://sandbox.myexperiment.org/workflow.xml?id=964\" version=\"2\">genePattern data preprocessing</workflow></workflows>";
		List<MyExperimentWorkflows.WorkflowStub> workflows = MyExperimentWorkflowRegistry.unmarshalWorkflows(new ByteArrayInputStream(xml.getBytes())); 
		assertNotNull(workflows);
		WorkflowDescription workflow = MyExperimentWorkflowRegistry.unmarshalWorkflow(MyExperimentRegistryTest.class.getClassLoader().getResourceAsStream("workflow.xml"));
		assertNotNull(workflow);
	}
	@Test
	public void testSessionCookie() throws HttpException, IOException {
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
		assertEquals("1508", wd.getId());
	}
}
