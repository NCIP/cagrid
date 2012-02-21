/**
 * AuthorizationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.security.authorization;

public class AuthorizationServiceLocator extends org.apache.axis.client.Service implements org.globus.wsrf.security.authorization.AuthorizationService {

    public AuthorizationServiceLocator() {
    }


    public AuthorizationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AuthorizationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SAMLRequestPortTypePort
    private java.lang.String SAMLRequestPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getSAMLRequestPortTypePortAddress() {
        return SAMLRequestPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SAMLRequestPortTypePortWSDDServiceName = "SAMLRequestPortTypePort";

    public java.lang.String getSAMLRequestPortTypePortWSDDServiceName() {
        return SAMLRequestPortTypePortWSDDServiceName;
    }

    public void setSAMLRequestPortTypePortWSDDServiceName(java.lang.String name) {
        SAMLRequestPortTypePortWSDDServiceName = name;
    }

    public org.globus.wsrf.security.authorization.SAMLRequestPortType getSAMLRequestPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SAMLRequestPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSAMLRequestPortTypePort(endpoint);
    }

    public org.globus.wsrf.security.authorization.SAMLRequestPortType getSAMLRequestPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.wsrf.security.authorization.SAMLRequestPortTypeSOAPBindingStub _stub = new org.globus.wsrf.security.authorization.SAMLRequestPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getSAMLRequestPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSAMLRequestPortTypePortEndpointAddress(java.lang.String address) {
        SAMLRequestPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.wsrf.security.authorization.SAMLRequestPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.wsrf.security.authorization.SAMLRequestPortTypeSOAPBindingStub _stub = new org.globus.wsrf.security.authorization.SAMLRequestPortTypeSOAPBindingStub(new java.net.URL(SAMLRequestPortTypePort_address), this);
                _stub.setPortName(getSAMLRequestPortTypePortWSDDServiceName());
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
        if ("SAMLRequestPortTypePort".equals(inputPortName)) {
            return getSAMLRequestPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.gridforum.org/namespaces/2004/03/ogsa-authz/saml/service", "AuthorizationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.gridforum.org/namespaces/2004/03/ogsa-authz/saml/service", "SAMLRequestPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("SAMLRequestPortTypePort".equals(portName)) {
            setSAMLRequestPortTypePortEndpointAddress(address);
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
