package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.AbstractWorkflowExecutionServiceTest;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowExecutionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class TavernaWorkflowServiceTest extends AbstractWorkflowExecutionServiceTest {

	@Autowired
	TavernaWorkflowService svc;
	
	@Override
	public WorkflowExecutionService getExecutionService() {
		return svc;
	}

}
