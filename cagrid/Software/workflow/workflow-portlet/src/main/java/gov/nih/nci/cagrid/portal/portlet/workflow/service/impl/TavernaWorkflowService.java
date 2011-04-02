package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowExecutionService;
import gov.nih.nci.cagrid.workflow.factory.client.TavernaWorkflowServiceClient;

import java.rmi.RemoteException;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Helper class that is used to hold some of the Spring injected dependencies and the class that submits the workflow 
 *  to a WorkflowService. It also updates the session information (status and output of submitted workflows).
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 */
@Service
public class TavernaWorkflowService extends WorkflowExecutionService {
	protected final Log log = LogFactory.getLog(getClass());

	private String tavernaWorkflowServiceUrl;
	
	public String getStatus(EndpointReferenceType epr) throws WorkflowException {
		try {
			return TavernaWorkflowServiceClient.getStatus(epr).getValue();
		} catch (MalformedURIException e) {
			throw new WorkflowException(e.getMessage(), e);
		} catch (RemoteException e) {
			throw new WorkflowException(e.getMessage(), e);
		}
	}

	public  String[] getOutput(EndpointReferenceType epr)  throws WorkflowException {
		if(!this.getStatus(epr).equals("Done")) return null;
		try {
			return TavernaWorkflowServiceClient.getOutput(epr).getOutputFile();
		} catch (MalformedURIException e) {
			throw new WorkflowException(e.getMessage(),e);
		} catch (RemoteException e) {
			throw new WorkflowException(e.getMessage(), e);
		} 
	}

	@SuppressWarnings("deprecation")
	public EndpointReferenceType submitWorkflow(String workflowName, String scuflDoc, String[] inputArgs) throws WorkflowException {
		log.info("Submitting Workflow: " + workflowName + ". scuflDoc size = " + scuflDoc.length() + " bytes");
		try {
			EndpointReferenceType resourceEPR = TavernaWorkflowServiceClient.setupWorkflow(tavernaWorkflowServiceUrl, scuflDoc, workflowName,null);
			TavernaWorkflowServiceClient.startWorkflow(inputArgs, resourceEPR);
			return resourceEPR;
		} catch (Exception e) {
			throw new WorkflowException(e.getMessage(), e);
		}
	}

	public String getTavernaWorkflowServiceUrl() {return tavernaWorkflowServiceUrl;}
	public void setTavernaWorkflowServiceUrl(String tavernaWorkflowServiceUrl) {this.tavernaWorkflowServiceUrl = tavernaWorkflowServiceUrl;}
}
