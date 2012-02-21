/**
 * PersistenceTestSubscriptionManagerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.core.notification.service;

public class PersistenceTestSubscriptionManagerServiceLocator extends org.apache.axis.client.Service implements org.globus.wsrf.core.notification.service.PersistenceTestSubscriptionManagerService {

    public PersistenceTestSubscriptionManagerServiceLocator() {
    }


    public PersistenceTestSubscriptionManagerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PersistenceTestSubscriptionManagerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PersistenceTestSubscriptionManagerPort
    private java.lang.String PersistenceTestSubscriptionManagerPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getPersistenceTestSubscriptionManagerPortAddress() {
        return PersistenceTestSubscriptionManagerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PersistenceTestSubscriptionManagerPortWSDDServiceName = "PersistenceTestSubscriptionManagerPort";

    public java.lang.String getPersistenceTestSubscriptionManagerPortWSDDServiceName() {
        return PersistenceTestSubscriptionManagerPortWSDDServiceName;
    }

    public void setPersistenceTestSubscriptionManagerPortWSDDServiceName(java.lang.String name) {
        PersistenceTestSubscriptionManagerPortWSDDServiceName = name;
    }

    public org.globus.wsrf.core.notification.PersistenceTestSubscriptionManager getPersistenceTestSubscriptionManagerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PersistenceTestSubscriptionManagerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPersistenceTestSubscriptionManagerPort(endpoint);
    }

    public org.globus.wsrf.core.notification.PersistenceTestSubscriptionManager getPersistenceTestSubscriptionManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.wsrf.core.notification.bindings.PersistenceTestSubscriptionManagerSOAPBindingStub _stub = new org.globus.wsrf.core.notification.bindings.PersistenceTestSubscriptionManagerSOAPBindingStub(portAddress, this);
            _stub.setPortName(getPersistenceTestSubscriptionManagerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPersistenceTestSubscriptionManagerPortEndpointAddress(java.lang.String address) {
        PersistenceTestSubscriptionManagerPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.wsrf.core.notification.PersistenceTestSubscriptionManager.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.wsrf.core.notification.bindings.PersistenceTestSubscriptionManagerSOAPBindingStub _stub = new org.globus.wsrf.core.notification.bindings.PersistenceTestSubscriptionManagerSOAPBindingStub(new java.net.URL(PersistenceTestSubscriptionManagerPort_address), this);
                _stub.setPortName(getPersistenceTestSubscriptionManagerPortWSDDServiceName());
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
        if ("PersistenceTestSubscriptionManagerPort".equals(inputPortName)) {
            return getPersistenceTestSubscriptionManagerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsrf.globus.org/core/notification/service", "PersistenceTestSubscriptionManagerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsrf.globus.org/core/notification/service", "PersistenceTestSubscriptionManagerPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("PersistenceTestSubscriptionManagerPort".equals(portName)) {
            setPersistenceTestSubscriptionManagerPortEndpointAddress(address);
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
