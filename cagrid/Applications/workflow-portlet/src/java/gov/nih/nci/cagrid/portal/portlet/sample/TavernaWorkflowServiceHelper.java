package gov.nih.nci.cagrid.portal.portlet.sample;

import org.apache.axis.message.addressing.EndpointReferenceType;

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

    public TavernaWorkflowServiceHelper() {
    }

    public TavernaWorkflowServiceHelper(String tavernaWorkflowServiceUrl) {
        this.tavernaWorkflowServiceUrl = tavernaWorkflowServiceUrl;
    }

    public String submitWorkflow(String workflowName, String scuflDoc, String[] inputArgs) throws Exception{
  
		//String url = "http://128.135.125.17:51000/wsrf/services/cagrid/TavernaWorkflowService";
		//String scuflDoc = "/Users/sulakhe/Desktop/caGrid+gRAVI/Taverna-2/cadsr.t2flow";

		//String workflowName = "Test";
		//String[] inputArgs = {"hello", " there"};

    	//tavernaWorkflowServiceUrl = "http://localhost:8081/wsrf/services/cagrid/TavernaWorkflowService";
    	
		EndpointReferenceType resourceEPR = TavernaWorkflowServiceClient.setupWorkflow(tavernaWorkflowServiceUrl, scuflDoc, workflowName);
		WorkflowStatusType workflowStatus =  TavernaWorkflowServiceClient.startWorkflow(inputArgs, resourceEPR);

		TavernaWorkflowServiceClient.writeEprToFile(resourceEPR, workflowName);

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
		
    }

    public String getTavernaWorkflowServiceUrl() {
        return tavernaWorkflowServiceUrl;
    }

    public void setTavernaWorkflowServiceUrl(String tavernaWorkflowServiceUrl) {
        this.tavernaWorkflowServiceUrl = tavernaWorkflowServiceUrl;
    }
}
