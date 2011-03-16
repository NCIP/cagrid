package gov.nih.nci.cagrid.portal.portlet.workflow.domain;


import java.util.Map;
/**
 * The command class that is used to store the Model used for the Views generated.
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 * Status: This class needs to split into multiple Model classes later when PortletModeParameterHandlerMappings will be used to 
 *   create multiple Controllers.
 */
public class SubmitWorkflowCommand {

    private String result;
    private String scuflDoc;
	private String serviceUrl;
	
	private WorkflowDescription theWorkflow;
	
	private String[] inputValues;
	
	private Map<String, WorkflowSubmitted> eprsMap;
	private String selectedUUID;
	
	private String[] outputs;

	public String[] getOutputs() {
		return outputs;
	}
	public void setOutputs(String[] outputs) {
		this.outputs = outputs;
	}
	public String getSelectedUUID() {
		return selectedUUID;
	}
	public void setSelectedUUID(String selectedUUID) {
		this.selectedUUID = selectedUUID;
	}

	public Map<String, WorkflowSubmitted> getEprsMap() {
		return eprsMap;
	}

	public void setEprsMap(Map<String, WorkflowSubmitted> eprsMap) {
		this.eprsMap = eprsMap;
	}

	public String[] getInputValues() {
		return inputValues;
	}

	public void setInputValues(String[] inputValues) {
		this.inputValues = inputValues;
	}

	public WorkflowDescription getTheWorkflow() {
		return theWorkflow;
	}

	public void setTheWorkflow(WorkflowDescription theWorkflow) {
		this.theWorkflow = theWorkflow;
	}

	public String getScuflDoc() {
		return scuflDoc;
	}

	public void setScuflDoc(String scuflDoc) {
		this.scuflDoc = scuflDoc;
	}
    

    public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
