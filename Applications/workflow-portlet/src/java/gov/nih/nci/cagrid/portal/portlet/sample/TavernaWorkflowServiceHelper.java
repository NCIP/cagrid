package gov.nih.nci.cagrid.portal.portlet.sample;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import workflowmanagementfactoryservice.WorkflowOutputType;
import workflowmanagementfactoryservice.WorkflowStatusType;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.workflow.factory.client.TavernaWorkflowServiceClient;

/**
 * Helper class that is used to hold some of the Spring injected dependencies and the class that submits the workflow 
 *  to a WorkflowService. It also updates the session information (status and output of submitted workflows).
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 */
public class TavernaWorkflowServiceHelper {

    // default value.   
	protected final Log logger = LogFactory.getLog(getClass());

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

    public EndpointReferenceType submitWorkflow(String workflowName, String scuflDoc, String[] inputArgs) throws Exception{
  

    	logger.info("Submitting Workflow to : " + tavernaWorkflowServiceUrl);
		EndpointReferenceType resourceEPR = TavernaWorkflowServiceClient.setupWorkflow(tavernaWorkflowServiceUrl, scuflDoc, workflowName);
		WorkflowStatusType workflowStatus =  TavernaWorkflowServiceClient.startWorkflow(inputArgs, resourceEPR);
		
		return resourceEPR;
	
    }
    
    public void updateSession() throws MalformedURIException, RemoteException{
    	
    	SessionEprs sessEprs = new SessionEprs();
    	
		Iterator it = this.getSessionEprsRef().getEprs().entrySet().iterator();
		logger.info("Size of Session Map: " + this.getSessionEprsRef().getEprs().size());
		while(it.hasNext())
		{
	        Map.Entry pairs = (Map.Entry)it.next();
			String uuid = (String) pairs.getKey();
			WorkflowSubmitted wSub = (WorkflowSubmitted) pairs.getValue();
			if(this.getStatus(wSub.getEpr()).equals("Done"))
			{
				wSub.setWorkflowOutput(this.getOutput(wSub.getEpr()));
			}
			wSub.setStatus(this.getStatus(wSub.getEpr()));
			sessEprs.putEpr(uuid, wSub);
		}
		
		this.setSessionEprsRef(sessEprs);
    }

    
    
    /*	int count = 0;
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
		//return outputs[0];
		return resourceEPR;
		
    }
    */

    public String getTavernaWorkflowServiceUrl() {
        return tavernaWorkflowServiceUrl;
    }

    public void setTavernaWorkflowServiceUrl(String tavernaWorkflowServiceUrl) {
        this.tavernaWorkflowServiceUrl = tavernaWorkflowServiceUrl;
    }
    
    public String getLymphomaResultViewer(String output){
    	
    	String outputTable = "<TABLE border=2>";
    	String[] array = output.split("\\n");
    	List<String> list = new ArrayList<String> (Arrays.asList(array));
    	list.remove(0);
    	list.remove(list.size() - 1);
    	array = (String[]) list.toArray(new String[list.size()]);
    	for(String row : array){
    		outputTable = outputTable + "<TR>";
    		String[] cols = row.split("\\s+");
    		for(String col : cols){
        		outputTable = outputTable + "<TD>"+ col + "</TD>";
    		}
    		outputTable = outputTable + "</TR>";
    	}
    	outputTable = outputTable + "</TABLE>";
    	return outputTable;
    	
    }
    
    
}
