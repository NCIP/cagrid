/**
 * NotificationConsumerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.core.notification.service;

public class NotificationConsumerServiceLocator extends org.apache.axis.client.Service implements org.globus.wsrf.core.notification.service.NotificationConsumerService {

    public NotificationConsumerServiceLocator() {
    }


    public NotificationConsumerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NotificationConsumerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ConsumerPort
    private java.lang.String ConsumerPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getConsumerPortAddress() {
        return ConsumerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ConsumerPortWSDDServiceName = "ConsumerPort";

    public java.lang.String getConsumerPortWSDDServiceName() {
        return ConsumerPortWSDDServiceName;
    }

    public void setConsumerPortWSDDServiceName(java.lang.String name) {
        ConsumerPortWSDDServiceName = name;
    }

    public org.globus.wsrf.core.notification.Consumer getConsumerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ConsumerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getConsumerPort(endpoint);
    }

    public org.globus.wsrf.core.notification.Consumer getConsumerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.wsrf.core.notification.bindings.ConsumerSOAPBindingStub _stub = new org.globus.wsrf.core.notification.bindings.ConsumerSOAPBindingStub(portAddress, this);
            _stub.setPortName(getConsumerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setConsumerPortEndpointAddress(java.lang.String address) {
        ConsumerPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.wsrf.core.notification.Consumer.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.wsrf.core.notification.bindings.ConsumerSOAPBindingStub _stub = new org.globus.wsrf.core.notification.bindings.ConsumerSOAPBindingStub(new java.net.URL(ConsumerPort_address), this);
                _stub.setPortName(getConsumerPortWSDDServiceName());
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
        if ("ConsumerPort".equals(inputPortName)) {
            return getConsumerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsrf.globus.org/core/notification/service", "NotificationConsumerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsrf.globus.org/core/notification/service", "ConsumerPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("ConsumerPort".equals(portName)) {
            setConsumerPortEndpointAddress(address);
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
