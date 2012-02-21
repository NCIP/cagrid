/**
 * EnumerationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.ws._2004._09.enumeration.service;

public class EnumerationServiceLocator extends org.apache.axis.client.Service implements org.xmlsoap.schemas.ws._2004._09.enumeration.service.EnumerationService {

    public EnumerationServiceLocator() {
    }


    public EnumerationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EnumerationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DataSourcePort
    private java.lang.String DataSourcePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getDataSourcePortAddress() {
        return DataSourcePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataSourcePortWSDDServiceName = "DataSourcePort";

    public java.lang.String getDataSourcePortWSDDServiceName() {
        return DataSourcePortWSDDServiceName;
    }

    public void setDataSourcePortWSDDServiceName(java.lang.String name) {
        DataSourcePortWSDDServiceName = name;
    }

    public org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource getDataSourcePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataSourcePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataSourcePort(endpoint);
    }

    public org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource getDataSourcePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.xmlsoap.schemas.ws._2004._09.enumeration.bindings.DataSourceSOAPBindingStub _stub = new org.xmlsoap.schemas.ws._2004._09.enumeration.bindings.DataSourceSOAPBindingStub(portAddress, this);
            _stub.setPortName(getDataSourcePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataSourcePortEndpointAddress(java.lang.String address) {
        DataSourcePort_address = address;
    }


    // Use to get a proxy class for DataSourceStartPort
    private java.lang.String DataSourceStartPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getDataSourceStartPortAddress() {
        return DataSourceStartPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataSourceStartPortWSDDServiceName = "DataSourceStartPort";

    public java.lang.String getDataSourceStartPortWSDDServiceName() {
        return DataSourceStartPortWSDDServiceName;
    }

    public void setDataSourceStartPortWSDDServiceName(java.lang.String name) {
        DataSourceStartPortWSDDServiceName = name;
    }

    public org.xmlsoap.schemas.ws._2004._09.enumeration.DataSourceStart getDataSourceStartPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataSourceStartPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataSourceStartPort(endpoint);
    }

    public org.xmlsoap.schemas.ws._2004._09.enumeration.DataSourceStart getDataSourceStartPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.xmlsoap.schemas.ws._2004._09.enumeration.bindings.DataSourceStartSOAPBindingStub _stub = new org.xmlsoap.schemas.ws._2004._09.enumeration.bindings.DataSourceStartSOAPBindingStub(portAddress, this);
            _stub.setPortName(getDataSourceStartPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataSourceStartPortEndpointAddress(java.lang.String address) {
        DataSourceStartPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource.class.isAssignableFrom(serviceEndpointInterface)) {
                org.xmlsoap.schemas.ws._2004._09.enumeration.bindings.DataSourceSOAPBindingStub _stub = new org.xmlsoap.schemas.ws._2004._09.enumeration.bindings.DataSourceSOAPBindingStub(new java.net.URL(DataSourcePort_address), this);
                _stub.setPortName(getDataSourcePortWSDDServiceName());
                return _stub;
            }
            if (org.xmlsoap.schemas.ws._2004._09.enumeration.DataSourceStart.class.isAssignableFrom(serviceEndpointInterface)) {
                org.xmlsoap.schemas.ws._2004._09.enumeration.bindings.DataSourceStartSOAPBindingStub _stub = new org.xmlsoap.schemas.ws._2004._09.enumeration.bindings.DataSourceStartSOAPBindingStub(new java.net.URL(DataSourceStartPort_address), this);
                _stub.setPortName(getDataSourceStartPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("DataSourcePort".equals(inputPortName)) {
            return getDataSourcePort();
        }
        else if ("DataSourceStartPort".equals(inputPortName)) {
            return getDataSourceStartPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration/service", "enumerationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration/service", "DataSourcePort"));
            ports.add(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration/service", "DataSourceStartPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("DataSourcePort".equals(portName)) {
            setDataSourcePortEndpointAddress(address);
        }
        if ("DataSourceStartPort".equals(portName)) {
            setDataSourceStartPortEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
