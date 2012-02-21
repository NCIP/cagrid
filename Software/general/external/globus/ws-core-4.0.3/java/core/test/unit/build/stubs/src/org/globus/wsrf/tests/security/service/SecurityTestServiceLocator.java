/**
 * SecurityTestServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.tests.security.service;

public class SecurityTestServiceLocator extends org.apache.axis.client.Service implements org.globus.wsrf.tests.security.service.SecurityTestService {

    public SecurityTestServiceLocator() {
    }


    public SecurityTestServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SecurityTestServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SecurityTestPortTypePort
    private java.lang.String SecurityTestPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getSecurityTestPortTypePortAddress() {
        return SecurityTestPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SecurityTestPortTypePortWSDDServiceName = "SecurityTestPortTypePort";

    public java.lang.String getSecurityTestPortTypePortWSDDServiceName() {
        return SecurityTestPortTypePortWSDDServiceName;
    }

    public void setSecurityTestPortTypePortWSDDServiceName(java.lang.String name) {
        SecurityTestPortTypePortWSDDServiceName = name;
    }

    public org.globus.wsrf.tests.security.SecurityTestPortType getSecurityTestPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SecurityTestPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSecurityTestPortTypePort(endpoint);
    }

    public org.globus.wsrf.tests.security.SecurityTestPortType getSecurityTestPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.wsrf.tests.security.bindings.SecurityTestPortTypeSOAPBindingStub _stub = new org.globus.wsrf.tests.security.bindings.SecurityTestPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getSecurityTestPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSecurityTestPortTypePortEndpointAddress(java.lang.String address) {
        SecurityTestPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.wsrf.tests.security.SecurityTestPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.wsrf.tests.security.bindings.SecurityTestPortTypeSOAPBindingStub _stub = new org.globus.wsrf.tests.security.bindings.SecurityTestPortTypeSOAPBindingStub(new java.net.URL(SecurityTestPortTypePort_address), this);
                _stub.setPortName(getSecurityTestPortTypePortWSDDServiceName());
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
        if ("SecurityTestPortTypePort".equals(inputPortName)) {
            return getSecurityTestPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security/service", "SecurityTestService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security/service", "SecurityTestPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("SecurityTestPortTypePort".equals(portName)) {
            setSecurityTestPortTypePortEndpointAddress(address);
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
