package gov.nih.nci.cagrid.portal.portlet.workflow;

import java.rmi.RemoteException;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

/**
 * Execute Workflows, get status updates, and output.
 * @author Marek Kedzierski
 */
public interface WorkflowService {

	public String getStatus(EndpointReferenceType epr) throws MalformedURIException, RemoteException;
	public  String[] getOutput(EndpointReferenceType epr) throws MalformedURIException, RemoteException;
	public EndpointReferenceType submitWorkflow(String workflowName, String scuflDoc, String[] inputArgs) throws Exception;
	public void updateSession() throws MalformedURIException, RemoteException;
	
}
