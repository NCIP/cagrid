/**
 * WSResourcePropertiesServiceAddressing.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.properties;

public interface WSResourcePropertiesServiceAddressing extends org.oasis.wsrf.properties.WSResourcePropertiesService {
    public org.oasis.wsrf.properties.GetResourceProperty getGetResourcePropertyPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.properties.SetResourceProperties_PortType getSetResourcePropertiesPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.properties.GetMultipleResourceProperties_PortType getGetMultipleResourcePropertiesPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.properties.QueryResourceProperties_PortType getQueryResourcePropertiesPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;


}
