package gov.nih.nci.cagrid.portal.portlet.sample;

import java.util.Map;

/**
 * User: sulakhe
 *
 * @author sulakhe@mcs.anl.gov
 */
public class WorkflowBean {

    private String keyword;
    private String result;
    private String scuflDoc;
	private String serviceUrl;
	private WorkflowDescription[] allWorkflows;
	
	private String workflowId;
	private WorkflowDescription theWorkflow;
	private String formState;
	
	private String[] inputValues;
	
    
	
	public String[] getInputValues() {
		return inputValues;
	}

	public void setInputValues(String[] inputValues) {
		this.inputValues = inputValues;
	}

	public String getFormState() {
		return formState;
	}

	public void setFormState(String formState) {
		this.formState = formState;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public WorkflowDescription getTheWorkflow() {
		return theWorkflow;
	}

	public void setTheWorkflow(WorkflowDescription theWorkflow) {
		this.theWorkflow = theWorkflow;
	}

	public WorkflowDescription[] getAllWorkflows() {
		return allWorkflows;
	}

	public void setAllWorkflows(WorkflowDescription[] allWorkflows) {
		this.allWorkflows = allWorkflows;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
