package gov.nih.nci.cagrid.portal.portlet.sample;

import java.rmi.RemoteException;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import workflowmanagementfactoryservice.WorkflowOutputType;
import workflowmanagementfactoryservice.WorkflowStatusType;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.workflow.factory.client.TavernaWorkflowServiceClient;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class TavernaWorkflowServiceHelper {

    // default value.   
    private String tavernaWorkflowServiceUrl = "http://localhost:8081/wsrf/services/cagrid/TavernaWorkflowService";
    
    private SessionEprs sessionEprsRef;

	public SessionEprs getSessionEprsRef() {
		return sessionEprsRef;
	}

	public void setSessionEprsRef(SessionEprs sessionEprsRef) {
		this.sessionEprsRef = sessionEprsRef;
	}

    public TavernaWorkflowServiceHelper() {
    }

    public TavernaWorkflowServiceHelper(String tavernaWorkflowServiceUrl) {
        this.tavernaWorkflowServiceUrl = tavernaWorkflowServiceUrl;
    }
    
    public String getStatus(EndpointReferenceType epr) throws MalformedURIException, RemoteException
    {
    	return TavernaWorkflowServiceClient.getStatus(epr).getValue();
    }
    
    public  String[] getOutput(EndpointReferenceType epr) throws MalformedURIException, RemoteException
    {
    	if(!this.getStatus(epr).equals("Done"))
    	{
    		return null;
    	}
    	return TavernaWorkflowServiceClient.getOutput(epr).getOutputFile();
    }

    public String submitWorkflow(String workflowName, String scuflDoc, String[] inputArgs) throws Exception{
  
		//String scuflDoc = "/Users/sulakhe/Desktop/caGrid+gRAVI/Taverna-2/cadsr.t2flow";
		//String workflowName = "Test";
		//String[] inputArgs = {"hello", " there"};

    	
		EndpointReferenceType resourceEPR = TavernaWorkflowServiceClient.setupWorkflow(tavernaWorkflowServiceUrl, scuflDoc, workflowName);
		WorkflowStatusType workflowStatus =  TavernaWorkflowServiceClient.startWorkflow(inputArgs, resourceEPR);

		//TavernaWorkflowServiceClient.writeEprToFile(resourceEPR, workflowName);
		//TavernaWorkflowServiceClient.subscribeRP(resourceEPR, 20);
		
		int count = 0;
		while((workflowStatus.getValue() != "Done") && count < 60)
		{
			count++;
			Thread.sleep(5000);
			workflowStatus = TavernaWorkflowServiceClient.getStatus(resourceEPR);
		}

		System.out.println("\n4. Getting back the output file..");
		WorkflowOutputType workflowOutput = TavernaWorkflowServiceClient.getOutput(resourceEPR);
		
		String[] outputs = workflowOutput.getOutputFile();
		for (int i=0; i < outputs.length; i++)
		{
			String outputFile = System.getProperty("user.dir") + "/" + workflowName +"-output-" + i + ".xml";
			Utils.stringBufferToFile(new StringBuffer(outputs[i]), outputFile);
			System.out.println("Output file " + i + " : " + outputFile);
		}
		
		//return workflowStatus.getValue();
		return outputs[0];
		//return resourceEPR;
		
    }

    public String getTavernaWorkflowServiceUrl() {
        return tavernaWorkflowServiceUrl;
    }

    public void setTavernaWorkflowServiceUrl(String tavernaWorkflowServiceUrl) {
        this.tavernaWorkflowServiceUrl = tavernaWorkflowServiceUrl;
    }
}
