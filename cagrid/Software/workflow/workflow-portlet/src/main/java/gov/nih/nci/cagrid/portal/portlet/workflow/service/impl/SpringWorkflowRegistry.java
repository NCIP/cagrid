package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import java.util.Map;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

public class SpringWorkflowRegistry implements WorkflowRegistryService {

	private Map<String, WorkflowDescription> workflows;
	
	@Override
	public Map<String, WorkflowDescription> getWorkflows() {
		return this.workflows;
	}
	public void setWorkflowList(Map<String, WorkflowDescription> workflowList) {
		this.workflows = workflowList;
	}
	@Override
	public WorkflowDescription getWorkflow(String id) {
		return this.workflows.get(id);
	}
}
