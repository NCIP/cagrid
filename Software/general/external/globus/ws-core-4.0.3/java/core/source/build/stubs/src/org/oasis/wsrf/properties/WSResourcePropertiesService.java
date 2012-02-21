/**
 * WSResourcePropertiesService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.properties;

public interface WSResourcePropertiesService extends javax.xml.rpc.Service {
    public java.lang.String getGetResourcePropertyPortAddress();

    public org.oasis.wsrf.properties.GetResourceProperty getGetResourcePropertyPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.properties.GetResourceProperty getGetResourcePropertyPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getSetResourcePropertiesPortAddress();

    public org.oasis.wsrf.properties.SetResourceProperties_PortType getSetResourcePropertiesPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.properties.SetResourceProperties_PortType getSetResourcePropertiesPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getGetMultipleResourcePropertiesPortAddress();

    public org.oasis.wsrf.properties.GetMultipleResourceProperties_PortType getGetMultipleResourcePropertiesPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.properties.GetMultipleResourceProperties_PortType getGetMultipleResourcePropertiesPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getQueryResourcePropertiesPortAddress();

    public org.oasis.wsrf.properties.QueryResourceProperties_PortType getQueryResourcePropertiesPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.properties.QueryResourceProperties_PortType getQueryResourcePropertiesPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
