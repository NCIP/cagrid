package gov.nih.nci.cagrid.portal.portlet.sample;

import java.util.HashMap;
import java.util.Map;
import org.apache.axis.message.addressing.EndpointReferenceType;
/**
 * Class that is stored in the session. This holds all the submitted workflows and the EPR.
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 * 
 */
public class SessionEprs {
	
	private Map<String, WorkflowSubmitted> eprs = new HashMap<String, WorkflowSubmitted>();

	public Map<String, WorkflowSubmitted> getEprs() {
		return eprs;
	}

	public void setEprs(Map<String, WorkflowSubmitted> eprs) {
		this.eprs = eprs;
	}
	
	public void putEpr(String epr, WorkflowSubmitted status)
	{
		this.eprs.put(epr, status);
	}
	
	public Integer numberOfEprs()
	{
		return eprs.size();
	}
	
}
