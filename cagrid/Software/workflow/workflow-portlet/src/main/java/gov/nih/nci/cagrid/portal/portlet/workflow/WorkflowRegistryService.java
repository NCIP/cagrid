package gov.nih.nci.cagrid.portal.portlet.workflow;

import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.util.Map;

/**
 * Workflow definition registry
 * @author marek
 */
public interface WorkflowRegistryService {
	
	/**
	 * Return a list of workflow definition stubs
	 * @return Workflow stubs
	 * @throws WorkflowException
	 */
	public Map<String, WorkflowDescription> getWorkflows() throws WorkflowException;
	
	/**
	 * Get Full workflow definition
	 * @param id workflow ID
	 * @return <code>WorkflowDescription</code> Workflow definition
	 * @throws WorkflowException
	 */
	public WorkflowDescription getWorkflow(String id) throws WorkflowException;
}
