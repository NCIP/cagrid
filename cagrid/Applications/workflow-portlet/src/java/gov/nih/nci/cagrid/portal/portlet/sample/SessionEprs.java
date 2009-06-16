package gov.nih.nci.cagrid.portal.portlet.sample;

import java.util.HashMap;
import java.util.Map;

import org.apache.axis.message.addressing.EndpointReferenceType;

public class SessionEprs {
	
	private Map<EndpointReferenceType, String> eprs = new HashMap<EndpointReferenceType, String>();

	public Map<EndpointReferenceType, String> getEprs() {
		return eprs;
	}

	public void setEprs(Map<EndpointReferenceType, String> eprs) {
		this.eprs = eprs;
	}
	
	public void putEpr(EndpointReferenceType epr, String status)
	{
		this.eprs.put(epr, status);
	}
	
	public Integer numberOfEprs()
	{
		return eprs.size();
	}
	
}
