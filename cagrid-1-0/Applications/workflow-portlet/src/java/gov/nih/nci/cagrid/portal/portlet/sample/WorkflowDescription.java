package gov.nih.nci.cagrid.portal.portlet.sample;

public class WorkflowDescription {

	private String workflowId;
	private String name;
	private String description;
	private String scuflLocation;
	private String author;
	private Integer inputPorts;

	public Integer getInputPorts() {
		return inputPorts;
	}

	public void setInputPorts(Integer inputPorts) {
		this.inputPorts = inputPorts;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getScuflLocation() {
		return  scuflLocation;
	}

	public void setScuflLocation(String scuflLocation) throws Exception {
		this.scuflLocation = scuflLocation;

		
	}
}
