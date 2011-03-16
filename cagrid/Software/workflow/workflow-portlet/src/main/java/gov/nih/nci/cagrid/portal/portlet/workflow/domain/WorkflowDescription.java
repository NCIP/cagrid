package gov.nih.nci.cagrid.portal.portlet.workflow.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents a generic workflow definition; not tied to a specific workflow technology.  
 * Contains the URL of the workflow definition file and relevent Metadata. 
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 */
@XmlRootElement(name="workflow")
@XmlType(name="workflow" )
@XmlAccessorType(XmlAccessType.NONE)
public class WorkflowDescription implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name="uri")
	private String uri;
	@XmlAttribute(name="resource")
	private String resource;
	@XmlAttribute(name="version")
	private int version;
	
	@XmlElement(name="id")
	private String id;
	@XmlElement(name="title")
	private String name;
	@XmlElement(name="description")
	private String description;
	@XmlElement(name="uploader")
	private String author;
	
	/** URI of workflow definition file, i.e. .tflow / .t2flow */
	@XmlElement(name="content-uri")
	private String contentURI;
	
	@XmlElement(name="components")
	private Components components;
	
	@XmlElement(name="thumbnail")
	private String thumbnailURI;
	@XmlElement(name="thumbnail-big")
	private String thumbnailBigURI;
	@XmlElement(name="preview")
	private String previewImageURI;
	/** Scalable Vector Graphics workflow image  */
	@XmlElement(name="svg")
	private String svgURI;
	
	@XmlTransient
	private List<Source> inputs;
	
	public String getThumbnailURI() {
		return thumbnailURI;
	}
	public void setThumbnailURI(String thumbnailURI) {
		this.thumbnailURI = thumbnailURI;
	}
	public String getThumbnailBigURI() {
		return thumbnailBigURI;
	}
	public void setThumbnailBigURI(String thumbnailMediumURI) {
		this.thumbnailBigURI = thumbnailMediumURI;
	}
	public String getPreviewImageURI() {
		return previewImageURI;
	}
	public void setPreviewImageURI(String previewImage) {
		this.previewImageURI = previewImage;
	}
	public String getSvgURI() {
		return svgURI;
	}
	public void setSvgURI(String svgURI) {
		this.svgURI = svgURI;
	}
	public String getContentURI() {
		return contentURI;
	}
	public void setContentURI(String contentURI) {
		this.contentURI = contentURI;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		if(this.inputs==null) this.inputs = new ArrayList<Source>();
		return inputs;
	}
	public void setInputs(List<Source> inputs) {
		this.inputs = inputs;
	}
	public String toString() {
		return "Workflow #" + getId() + " | " + getName();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		WorkflowDescription other = (WorkflowDescription) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}
	
	/**
	 * @author marek
	 */
	@XmlType(name="components")
	@XmlAccessorType(XmlAccessType.NONE)
	public static  class Components {
		@XmlElementWrapper(name="dataflows")
		@XmlElement(name="dataflow")
		private List<Dataflow> dataflow;
		
		public List<Dataflow> getDataflow() {
			return dataflow;
		}
		public void setDataflow(List<Dataflow> dataflow) {
			this.dataflow = dataflow;
		}
		public String toString() {
			return "Components [" + getDataflow() + "]";
		}
	}
	
	/**
	 * @author marek
	 */
	@XmlType(name="dataflow")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class Dataflow {
		@XmlAttribute(name="id")
		private String id;
		@XmlAttribute(name="role")
		private String role;
		@XmlElementWrapper(name="sources")
		@XmlElement(name="source")
		private List<Source> source;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public List<Source> getSource() {
			return source;
		}
		public void setSource(List<Source> source) {
			this.source = source;
		}
		public String toString() {
			return "Dataflow [" + this.getRole() + " - " + getSource() + "]";
		}
	}
	
	/**
	 * @author marek
	 */
	@XmlType(name="source")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class Source {
		@XmlElement(name="name")
		private String name;
		@XmlElementWrapper(name="descriptions")
		@XmlElement(name="description")
		private List<String> description;
		@XmlElementWrapper(name="examples")
		@XmlElement(name="example")
		private List<String> example;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<String> getDescription() {
			if(description==null) this.description = new ArrayList<String>();
			return description;
		}
		public void setDescription(List<String> description) {
			this.description = description;
		}
		public List<String> getExample() {
			if(example==null) this.example = new ArrayList<String>();
			return example;
		}
		public void setExample(List<String> example) {
			this.example = example;
		}
		public String getFirstDescription() { 
			return description.size()>0 ? description.get(0) : "No Description Available"; 
		};
		public String getFirstExample() { 
			return example.size()>0 ? example.get(0) : "No Example Input Available"; 
		};
		public String toString() {
			return "Source ["+this.getName() + " - " + getDescription()+"]";
		}
	}
}
