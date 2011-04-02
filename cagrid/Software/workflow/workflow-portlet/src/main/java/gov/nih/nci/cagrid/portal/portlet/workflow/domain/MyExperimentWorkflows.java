package gov.nih.nci.cagrid.portal.portlet.workflow.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="workflows")
@XmlAccessorType(XmlAccessType.NONE)
public class MyExperimentWorkflows {
	@XmlElement(name="workflow")
	public List<WorkflowStub> workflow;
	
	@XmlType(name="workflow")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class WorkflowStub {
		
		@XmlAttribute(name="uri")
		private String uri;
		@XmlAttribute(name="resource")
		private String resource;
		@XmlAttribute(name="version")
		private int version;
		
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
		public String toString() {
			return "Workflow Stub [" + getUri() + " | " + getResource() + " | " + getVersion() + "]";
		}
	}
}
