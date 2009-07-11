package gov.nih.nci.cagrid.portal.portlet.sample;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * This Class represents a Workflow uploaded in the Portlet. This is used to initialize the workflows Objects using Spring.
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 */

public class WorkflowDescription implements ResourceLoaderAware, InitializingBean{

	private String workflowId;
	private String name;
	private String description;
	private String author;
	private Integer inputPorts;		
	private String filePath;

	private String scuflLocation;
	private ResourceLoader resourceLoader;
	

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	

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

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		
	}

	//This method gets executed after setting all the setters from taverna-portlet.xml
	// It is used to initialize the actual location of the scufl file, after it is deployed in the app server. (jboss).
	public void afterPropertiesSet() throws Exception {
		if (this.resourceLoader == null)
			throw new IllegalArgumentException("A ResourceLoader is required");
		Resource resource = resourceLoader.getResource(filePath);
		this.setScuflLocation(resource.getFile().getAbsolutePath());
		
	}
}
