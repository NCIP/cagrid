package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("Spring")
public class SpringWorkflowRegistry implements WorkflowRegistryService {
	private static Log log = LogFactory.getLog(SpringWorkflowRegistry.class);
	private Map<String, WorkflowDescription> workflows;
	
	public SpringWorkflowRegistry() {
		log.info("Created Spring Workflow Registry.");
	}
	
	public Map<String, WorkflowDescription> getWorkflows() {
		log.debug("Returning Spring Workflows");
		return this.workflows;
	}
	
	public void setWorkflowList(Map<String, WorkflowDescription> workflowList) {
		this.workflows = workflowList;
	}
	public WorkflowDescription getWorkflow(String id) {
		log.debug("Returning Spring Workflow #" + id);
		return workflows.get(id);
	}
}
