package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowService;
import gov.nih.nci.cagrid.workflow.factory.client.TavernaWorkflowServiceClient;

import java.rmi.RemoteException;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Helper class that is used to hold some of the Spring injected dependencies and the class that submits the workflow 
 *  to a WorkflowService. It also updates the session information (status and output of submitted workflows).
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 */
public class TavernaWorkflowService implements WorkflowService {
	protected final Log log = LogFactory.getLog(getClass());

	private String tavernaWorkflowServiceUrl;
	
	public String getStatus(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
		return TavernaWorkflowServiceClient.getStatus(epr).getValue();
	}

	public  String[] getOutput(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
		if(!this.getStatus(epr).equals("Done")) return null;
		return TavernaWorkflowServiceClient.getOutput(epr).getOutputFile();
	}

	public EndpointReferenceType submitWorkflow(String workflowName, String scuflDoc, String[] inputArgs) throws Exception {
		log.info("Submitting Workflow: " + workflowName + ". scuflDoc size = " + scuflDoc.length() + " bytes");
		EndpointReferenceType resourceEPR = TavernaWorkflowServiceClient.setupWorkflow(tavernaWorkflowServiceUrl, scuflDoc, workflowName,null);
		TavernaWorkflowServiceClient.startWorkflow(inputArgs, resourceEPR);
		return resourceEPR;
	}

	public String getTavernaWorkflowServiceUrl() {return tavernaWorkflowServiceUrl;}
	public void setTavernaWorkflowServiceUrl(String tavernaWorkflowServiceUrl) {this.tavernaWorkflowServiceUrl = tavernaWorkflowServiceUrl;}
}