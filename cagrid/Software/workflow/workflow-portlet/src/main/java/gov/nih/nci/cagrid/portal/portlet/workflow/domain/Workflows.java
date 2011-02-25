package gov.nih.nci.cagrid.portal.portlet.workflow.domain;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="workflows")
@XmlAccessorType(XmlAccessType.NONE)
public class Workflows {
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
	
	/**
	 * Unmarshall a list of workflows from xml
	 * @param is <code>InputStream</code> of xml
	 * @return	<code>Workflow</code> representation of workflow list xml
	 * @throws JAXBException
	 */
	public static List<WorkflowStub> unmarshalWorkflows(InputStream is) throws JAXBException {
		return ((Workflows)JAXBContext.newInstance(Workflows.class, WorkflowStub.class).createUnmarshaller().unmarshal(is)).workflow;
	}
	
	/**
	 * Unmarshall a single workflow from xml
	 * @param is	<code>InputStream</code> of xml
	 * @return	<code>WorkfowDescription</code> representation of workflow xml
	 * @throws JAXBException
	 */
	public static WorkflowDescription unmarshalWorkflow(InputStream is) throws JAXBException {
		return (WorkflowDescription)JAXBContext.newInstance(WorkflowDescription.class).createUnmarshaller().unmarshal(is);
	}
}
