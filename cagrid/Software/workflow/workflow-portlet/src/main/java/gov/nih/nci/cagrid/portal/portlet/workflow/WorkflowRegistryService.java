package gov.nih.nci.cagrid.portal.portlet.workflow;

import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.util.Map;

/**
 * Workflow definition registry
 * @author marek
 */
public interface WorkflowRegistryService {
	
	public Map<String, WorkflowDescription> getWorkflows() throws WorkflowException ;
	
	public WorkflowDescription getWorkflow(String id) throws WorkflowException;
	
}
