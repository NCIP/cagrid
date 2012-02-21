/**
 * RegistryEntryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.core.registry;

public class RegistryEntryServiceLocator extends org.apache.axis.client.Service implements org.globus.core.registry.RegistryEntryService {

    public RegistryEntryServiceLocator() {
    }


    public RegistryEntryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RegistryEntryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RegistryEntryPortTypePort
    private java.lang.String RegistryEntryPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getRegistryEntryPortTypePortAddress() {
        return RegistryEntryPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RegistryEntryPortTypePortWSDDServiceName = "RegistryEntryPortTypePort";

    public java.lang.String getRegistryEntryPortTypePortWSDDServiceName() {
        return RegistryEntryPortTypePortWSDDServiceName;
    }

    public void setRegistryEntryPortTypePortWSDDServiceName(java.lang.String name) {
        RegistryEntryPortTypePortWSDDServiceName = name;
    }

    public org.globus.core.registry.RegistryEntryPortType getRegistryEntryPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RegistryEntryPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRegistryEntryPortTypePort(endpoint);
    }

    public org.globus.core.registry.RegistryEntryPortType getRegistryEntryPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.core.registry.RegistryEntryPortTypeSOAPBindingStub _stub = new org.globus.core.registry.RegistryEntryPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getRegistryEntryPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRegistryEntryPortTypePortEndpointAddress(java.lang.String address) {
        RegistryEntryPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.core.registry.RegistryEntryPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.core.registry.RegistryEntryPortTypeSOAPBindingStub _stub = new org.globus.core.registry.RegistryEntryPortTypeSOAPBindingStub(new java.net.URL(RegistryEntryPortTypePort_address), this);
                _stub.setPortName(getRegistryEntryPortTypePortWSDDServiceName());
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
        if ("RegistryEntryPortTypePort".equals(inputPortName)) {
            return getRegistryEntryPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.globus.org/namespaces/2004/06/registry/service", "RegistryEntryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.globus.org/namespaces/2004/06/registry/service", "RegistryEntryPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("RegistryEntryPortTypePort".equals(portName)) {
            setRegistryEntryPortTypePortEndpointAddress(address);
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
