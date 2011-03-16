package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.junit.Test;

public class TavernaWorkflowServiceTest {
	
	@Test
	public void testSubmitWorkflow() throws WorkflowException {
		 TavernaWorkflowService svc = new TavernaWorkflowService();
		svc.setTavernaWorkflowServiceUrl("https://cabig-workflow.ci.uchicago.edu:5005/wsrf/services/cagrid/TavernaWorkflowService");
		EndpointReferenceType epr = svc.submitWorkflow("fishsoup", "/home/marek/java/work/workflow-portlet/src/test/resources/fishsoup.t2flow", null);
	}
}
