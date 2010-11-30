/**
 * EnumerationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package com.enumeration.service;

public class EnumerationServiceLocator extends org.apache.axis.client.Service implements com.enumeration.service.EnumerationService {

    public EnumerationServiceLocator() {
    }


    public EnumerationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EnumerationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EnumerationPortTypePort
    private java.lang.String EnumerationPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getEnumerationPortTypePortAddress() {
        return EnumerationPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EnumerationPortTypePortWSDDServiceName = "EnumerationPortTypePort";

    public java.lang.String getEnumerationPortTypePortWSDDServiceName() {
        return EnumerationPortTypePortWSDDServiceName;
    }

    public void setEnumerationPortTypePortWSDDServiceName(java.lang.String name) {
        EnumerationPortTypePortWSDDServiceName = name;
    }

    public com.enumeration.EnumerationPortType getEnumerationPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EnumerationPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEnumerationPortTypePort(endpoint);
    }

    public com.enumeration.EnumerationPortType getEnumerationPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.enumeration.bindings.EnumerationPortTypeSOAPBindingStub _stub = new com.enumeration.bindings.EnumerationPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getEnumerationPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEnumerationPortTypePortEndpointAddress(java.lang.String address) {
        EnumerationPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.enumeration.EnumerationPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.enumeration.bindings.EnumerationPortTypeSOAPBindingStub _stub = new com.enumeration.bindings.EnumerationPortTypeSOAPBindingStub(new java.net.URL(EnumerationPortTypePort_address), this);
                _stub.setPortName(getEnumerationPortTypePortWSDDServiceName());
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
        if ("EnumerationPortTypePort".equals(inputPortName)) {
            return getEnumerationPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://enumeration.com/service", "EnumerationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://enumeration.com/service", "EnumerationPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("EnumerationPortTypePort".equals(portName)) {
            setEnumerationPortTypePortEndpointAddress(address);
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
