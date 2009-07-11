package gov.nih.nci.cagrid.portal.portlet.sample;

import org.apache.axis.message.addressing.EndpointReferenceType;

/**
 * This class with store the status of each workflow submitted during a session. This will added to the Map in the
 * SessionEprs class.
 *
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 * 
 */

public class WorkflowSubmitted {
	
	private EndpointReferenceType epr;
	private WorkflowDescription workflowDesc;
	private String status;
	private String[] workflowOutput;

	public WorkflowSubmitted(EndpointReferenceType epr, WorkflowDescription desc, String status, String[] outputs)
	{
		this.epr = epr;
		this.workflowDesc = desc;
		this.status = status;
		this.workflowOutput = outputs;
	}
	
	
	public EndpointReferenceType getEpr() {
		return epr;
	}

	public void setEpr(EndpointReferenceType epr) {
		this.epr = epr;
	}
	
	public WorkflowDescription getWorkflowDesc() {
		return workflowDesc;
	}
	public void setWorkflowDesc(WorkflowDescription workflowDesc) {
		this.workflowDesc = workflowDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getWorkflowOutput() {
		return workflowOutput;
	}
	public void setWorkflowOutput(String[] workflowOutput) {
		this.workflowOutput = workflowOutput;
	}

}
