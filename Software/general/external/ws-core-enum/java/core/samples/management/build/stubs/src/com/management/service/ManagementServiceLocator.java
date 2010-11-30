/**
 * ManagementServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package com.management.service;

public class ManagementServiceLocator extends org.apache.axis.client.Service implements com.management.service.ManagementService {

    public ManagementServiceLocator() {
    }


    public ManagementServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ManagementServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ManagementPortPort
    private java.lang.String ManagementPortPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getManagementPortPortAddress() {
        return ManagementPortPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ManagementPortPortWSDDServiceName = "ManagementPortPort";

    public java.lang.String getManagementPortPortWSDDServiceName() {
        return ManagementPortPortWSDDServiceName;
    }

    public void setManagementPortPortWSDDServiceName(java.lang.String name) {
        ManagementPortPortWSDDServiceName = name;
    }

    public com.management.ManagementPort getManagementPortPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ManagementPortPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getManagementPortPort(endpoint);
    }

    public com.management.ManagementPort getManagementPortPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.management.bindings.ManagementPortSOAPBindingStub _stub = new com.management.bindings.ManagementPortSOAPBindingStub(portAddress, this);
            _stub.setPortName(getManagementPortPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setManagementPortPortEndpointAddress(java.lang.String address) {
        ManagementPortPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.management.ManagementPort.class.isAssignableFrom(serviceEndpointInterface)) {
                com.management.bindings.ManagementPortSOAPBindingStub _stub = new com.management.bindings.ManagementPortSOAPBindingStub(new java.net.URL(ManagementPortPort_address), this);
                _stub.setPortName(getManagementPortPortWSDDServiceName());
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
        if ("ManagementPortPort".equals(inputPortName)) {
            return getManagementPortPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://management.com/service", "ManagementService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://management.com/service", "ManagementPortPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("ManagementPortPort".equals(portName)) {
            setManagementPortPortEndpointAddress(address);
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
