/**
 * RegistryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.core.registry;

public class RegistryServiceLocator extends org.apache.axis.client.Service implements org.globus.core.registry.RegistryService {

    public RegistryServiceLocator() {
    }


    public RegistryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RegistryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RegistryPortTypePort
    private java.lang.String RegistryPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getRegistryPortTypePortAddress() {
        return RegistryPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RegistryPortTypePortWSDDServiceName = "RegistryPortTypePort";

    public java.lang.String getRegistryPortTypePortWSDDServiceName() {
        return RegistryPortTypePortWSDDServiceName;
    }

    public void setRegistryPortTypePortWSDDServiceName(java.lang.String name) {
        RegistryPortTypePortWSDDServiceName = name;
    }

    public org.globus.core.registry.RegistryPortType getRegistryPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RegistryPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRegistryPortTypePort(endpoint);
    }

    public org.globus.core.registry.RegistryPortType getRegistryPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.core.registry.RegistryPortTypeSOAPBindingStub _stub = new org.globus.core.registry.RegistryPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getRegistryPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRegistryPortTypePortEndpointAddress(java.lang.String address) {
        RegistryPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.core.registry.RegistryPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.core.registry.RegistryPortTypeSOAPBindingStub _stub = new org.globus.core.registry.RegistryPortTypeSOAPBindingStub(new java.net.URL(RegistryPortTypePort_address), this);
                _stub.setPortName(getRegistryPortTypePortWSDDServiceName());
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
        if ("RegistryPortTypePort".equals(inputPortName)) {
            return getRegistryPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.globus.org/namespaces/2004/06/registry/service", "RegistryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.globus.org/namespaces/2004/06/registry/service", "RegistryPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("RegistryPortTypePort".equals(portName)) {
            setRegistryPortTypePortEndpointAddress(address);
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
