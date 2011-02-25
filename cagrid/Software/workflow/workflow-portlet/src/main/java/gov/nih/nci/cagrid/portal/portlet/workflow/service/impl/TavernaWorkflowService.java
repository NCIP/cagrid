package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;
import gov.nih.nci.cagrid.workflow.factory.client.TavernaWorkflowServiceClient;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Map.Entry;

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
	protected final Log logger = LogFactory.getLog(getClass());

	private String tavernaWorkflowServiceUrl;
	private SessionEprs sessionEprsRef;
	
	public String getStatus(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
		return TavernaWorkflowServiceClient.getStatus(epr).getValue();
	}

	public  String[] getOutput(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
		if(!this.getStatus(epr).equals("Done")) return null;
		return TavernaWorkflowServiceClient.getOutput(epr).getOutputFile();
	}

	public EndpointReferenceType submitWorkflow(String workflowName, String scuflDoc, String[] inputArgs) throws Exception {
		logger.info("Submitting Workflow to : " + tavernaWorkflowServiceUrl);
		EndpointReferenceType resourceEPR = TavernaWorkflowServiceClient.setupWorkflow(tavernaWorkflowServiceUrl, scuflDoc, workflowName,null);
		TavernaWorkflowServiceClient.startWorkflow(inputArgs, resourceEPR);
		return resourceEPR;
	}

	public void updateSession() throws MalformedURIException, RemoteException{
		if(this.getSessionEprsRef() == null || this.getSessionEprsRef().getEprs().isEmpty()) { logger.info("The sessions object is EMPTY."); return; }
		SessionEprs sessEprs = this.getSessionEprsRef();
		logger.info("Size of Session Map: " + sessEprs.getEprs().size());
		for(Entry<String,WorkflowSubmitted> pairs : sessEprs.getEprs().entrySet()) {
			EndpointReferenceType wEpr = pairs.getValue().getEpr();
			pairs.getValue().setStatus(this.getStatus(wEpr));
			if(pairs.getValue().getStatus().equals("Done"))
				pairs.getValue().setWorkflowOutput(this.getOutput(wEpr));
			
			sessEprs.putEpr(pairs.getKey(), pairs.getValue());
			if(pairs.getValue().getStatus().equals("Pending"))	{
				Map<String, WorkflowSubmitted> newMap = sessEprs.getEprs();
				newMap.remove(pairs.getKey());
				sessEprs.setEprs(newMap);
			}
		}
		this.setSessionEprsRef(sessEprs);
	}

	public String getTavernaWorkflowServiceUrl() {return tavernaWorkflowServiceUrl;}
	public void setTavernaWorkflowServiceUrl(String tavernaWorkflowServiceUrl) {this.tavernaWorkflowServiceUrl = tavernaWorkflowServiceUrl;}
	public SessionEprs getSessionEprsRef() { return sessionEprsRef; }
	public void setSessionEprsRef(SessionEprs sessionEprsRef) { this.sessionEprsRef = sessionEprsRef; }
}