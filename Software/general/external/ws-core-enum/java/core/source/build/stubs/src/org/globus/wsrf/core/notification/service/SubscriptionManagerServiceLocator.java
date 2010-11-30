/**
 * SubscriptionManagerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.core.notification.service;

public class SubscriptionManagerServiceLocator extends org.apache.axis.client.Service implements org.globus.wsrf.core.notification.service.SubscriptionManagerService {

    public SubscriptionManagerServiceLocator() {
    }


    public SubscriptionManagerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SubscriptionManagerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SubscriptionManagerPort
    private java.lang.String SubscriptionManagerPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getSubscriptionManagerPortAddress() {
        return SubscriptionManagerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SubscriptionManagerPortWSDDServiceName = "SubscriptionManagerPort";

    public java.lang.String getSubscriptionManagerPortWSDDServiceName() {
        return SubscriptionManagerPortWSDDServiceName;
    }

    public void setSubscriptionManagerPortWSDDServiceName(java.lang.String name) {
        SubscriptionManagerPortWSDDServiceName = name;
    }

    public org.globus.wsrf.core.notification.SubscriptionManager getSubscriptionManagerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SubscriptionManagerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSubscriptionManagerPort(endpoint);
    }

    public org.globus.wsrf.core.notification.SubscriptionManager getSubscriptionManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.wsrf.core.notification.bindings.SubscriptionManagerSOAPBindingStub _stub = new org.globus.wsrf.core.notification.bindings.SubscriptionManagerSOAPBindingStub(portAddress, this);
            _stub.setPortName(getSubscriptionManagerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSubscriptionManagerPortEndpointAddress(java.lang.String address) {
        SubscriptionManagerPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.wsrf.core.notification.SubscriptionManager.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.wsrf.core.notification.bindings.SubscriptionManagerSOAPBindingStub _stub = new org.globus.wsrf.core.notification.bindings.SubscriptionManagerSOAPBindingStub(new java.net.URL(SubscriptionManagerPort_address), this);
                _stub.setPortName(getSubscriptionManagerPortWSDDServiceName());
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
        if ("SubscriptionManagerPort".equals(inputPortName)) {
            return getSubscriptionManagerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsrf.globus.org/core/notification/service", "SubscriptionManagerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsrf.globus.org/core/notification/service", "SubscriptionManagerPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("SubscriptionManagerPort".equals(portName)) {
            setSubscriptionManagerPortEndpointAddress(address);
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
