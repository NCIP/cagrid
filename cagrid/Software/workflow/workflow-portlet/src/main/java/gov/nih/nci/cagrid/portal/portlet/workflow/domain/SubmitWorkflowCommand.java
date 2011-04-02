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

	private WorkflowDescription theWorkflow;
	private String[] inputValues;
	private String result;
	
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
	public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
}
