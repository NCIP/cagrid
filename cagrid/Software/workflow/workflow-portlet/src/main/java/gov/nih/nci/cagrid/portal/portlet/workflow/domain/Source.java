package gov.nih.nci.cagrid.portal.portlet.workflow.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="source")
@XmlAccessorType(XmlAccessType.NONE)
public class Source {
	
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
		return description;
	}
	public void setDescription(List<String> description) {
		this.description = description;
	}
	public List<String> getExample() {
		return example;
	}
	public void setExample(List<String> example) {
		this.example = example;
	}
	public String toString() {
		return "Source ["+this.getName() + " - " + getDescription()+"]";
	}
}
