package gov.nih.nci.cagrid.gme.service.globus.resource;

import javax.xml.namespace.QName;


public interface ResourceConstants {
	public static final String SERVICE_NS = "http://cagrid.nci.nih.gov/GlobalModelExchange";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "GlobalModelExchange");
	public static final QName RESOURCE_PROPERY_SET = new QName(SERVICE_NS, "GlobalModelExchangeResourceProperties");

	//Service level metadata (exposed as resouce properties)
	public static final QName COMMONSERVICEMETADATA_MD_RP = new QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata.common", "CommonServiceMetadata");
	
}
