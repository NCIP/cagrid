package gov.nih.nci.cagrid.portal.portlet.workflow.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * This Class represents a Workflow uploaded in the Portlet. This is used to initialize the workflows Objects using Spring.
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 */
@XmlRootElement(name="workflow")
@XmlType(name="workflow" )
@XmlAccessorType(XmlAccessType.NONE)
public class WorkflowDescription implements ResourceLoaderAware, InitializingBean{
	@XmlAttribute(name="uri")
	private String uri;
	@XmlAttribute(name="resource")
	private String resource;
	@XmlAttribute(name="version")
	private int version;

	@XmlElement(name="id", required=true, nillable=false)
	private String workflowId;
	
	@XmlElement(name="title", required=true, nillable=false)
	private String name;
	
	@XmlElement(name="description", required=true, nillable=false)
	private String description;
	
	@XmlElement(name="uploader", required=true, nillable=false)
	private String author;
	
	@XmlElement(name="content-uri", required=true, nillable=false)
	private String filePath;
	
	@XmlElement(name="preview", required=true, nillable=false)
	private String imageFile;
	
	@XmlElement(name="components", required=true)
	private Components components;
	
	private String scuflLocation;
	
	@XmlTransient
	private List<Source> inputs = new ArrayList<Source>();
	@XmlTransient
	private int inputPorts=0;		
	private String sampleInputs;
	
	private String viewResolver = "output";
	private ResourceLoader resourceLoader;

	public String getViewResolver() {
		return viewResolver;
	}
	public void setViewResolver(String viewResolver) {
		this.viewResolver = viewResolver;
	}
	public String getImageFile() {
		return imageFile;
	}
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}
	public String getSampleInputs() {
		return sampleInputs;
	}
	public void setSampleInputs(String sampleInputs) {
		this.sampleInputs = sampleInputs;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getInputPorts() {
		return inputPorts;
	}
	public void setInputPorts(int inputPorts) {
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

	 /* Initialize the actual (deployed) location of the scufl file after deployment.
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if (this.resourceLoader == null) throw new IllegalArgumentException("A ResourceLoader is required");
		Resource resource = resourceLoader.getResource(filePath);
		this.setScuflLocation(resource.getFile().getAbsolutePath());
	}
	
	public String toString() {
		return "Workflow #" + getWorkflowId() + " | " + getName() + " | " + getFilePath();
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Components getComponents() {
		return components;
	}
	public void setComponents(Components components) {
		this.components = components;
	}
	public List<Source> getInputs() {
		return inputs;
	}
	public void setInputs(List<Source> inputs) {
		this.inputs = inputs;
	}
}
