package gov.nih.nci.cagrid.portal.portlet.workflow;

import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;

import org.apache.axis.message.addressing.EndpointReferenceType;

/**
 * Execute Workflows, get status updates, and output.
 * @author Marek Kedzierski
 */
public abstract class WorkflowExecutionService {

	public abstract String getStatus(EndpointReferenceType epr) throws WorkflowException;
	public abstract String[] getOutput(EndpointReferenceType epr) throws WorkflowException;
	public abstract EndpointReferenceType submitWorkflow(String workflowName, String scuflDoc, String[] inputArgs) throws WorkflowException;
	
	/**
	 * Update status of submitted workflows.
	 * @param eprs  Submitted Workflows
	 * @return  whether any pending instances exist.  false if all instances are finished.
	 */
    public  boolean  updateSession(SessionEprs eprs) throws WorkflowException {
    	boolean isAllDone = true;
		for(WorkflowSubmitted wSub : eprs.getEprs().values()) {
			if("Done".equals(wSub.getStatus()) || "Failed".equals(wSub.getStatus())) continue; 
			isAllDone = false;
			wSub.setStatus(getStatus(wSub.getEpr()));
			if("Done".equals(wSub.getStatus())) wSub.setWorkflowOutput(getOutput(wSub.getEpr()));
		}
		return isAllDone;
	}
}
