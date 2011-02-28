package gov.nih.nci.cagrid.portal.portlet.workflow;

import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;

import java.rmi.RemoteException;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

/**
 * Execute Workflows, get status updates, and output.
 * @author Marek Kedzierski
 */
public abstract class WorkflowService {

	public abstract String getStatus(EndpointReferenceType epr) throws MalformedURIException, RemoteException;
	public abstract String[] getOutput(EndpointReferenceType epr) throws MalformedURIException, RemoteException;
	public abstract EndpointReferenceType submitWorkflow(String workflowName, String scuflDoc, String[] inputArgs) throws Exception;
	
	/**
	 * Update status of submitted workflows.
	 */
    public void updateSession(SessionEprs eprs) throws MalformedURIException, RemoteException{
		for(WorkflowSubmitted wSub : eprs.getEprs().values()) {
			if("Done".equals(wSub.getStatus()) || "Failed".equals(wSub.getStatus())) continue; 
			wSub.setStatus(getStatus(wSub.getEpr()));
			if("Done".equals(wSub.getStatus())) wSub.setWorkflowOutput(getOutput(wSub.getEpr()));
		}
	}
}