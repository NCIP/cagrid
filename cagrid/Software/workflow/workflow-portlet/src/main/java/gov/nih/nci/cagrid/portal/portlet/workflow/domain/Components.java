package gov.nih.nci.cagrid.portal.portlet.workflow.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="components")
@XmlAccessorType(XmlAccessType.NONE)
public class Components {

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
	
	
}
