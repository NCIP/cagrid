/**
 * NotificationConsumerFactoryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.core.tests.interop.service;

public class NotificationConsumerFactoryServiceLocator extends org.apache.axis.client.Service implements org.globus.wsrf.core.tests.interop.service.NotificationConsumerFactoryService {

    public NotificationConsumerFactoryServiceLocator() {
    }


    public NotificationConsumerFactoryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NotificationConsumerFactoryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NotificationConsumerFactoryPort
    private java.lang.String NotificationConsumerFactoryPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getNotificationConsumerFactoryPortAddress() {
        return NotificationConsumerFactoryPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NotificationConsumerFactoryPortWSDDServiceName = "NotificationConsumerFactoryPort";

    public java.lang.String getNotificationConsumerFactoryPortWSDDServiceName() {
        return NotificationConsumerFactoryPortWSDDServiceName;
    }

    public void setNotificationConsumerFactoryPortWSDDServiceName(java.lang.String name) {
        NotificationConsumerFactoryPortWSDDServiceName = name;
    }

    public org.globus.wsrf.core.tests.interop.NotificationConsumerFactory getNotificationConsumerFactoryPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NotificationConsumerFactoryPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNotificationConsumerFactoryPort(endpoint);
    }

    public org.globus.wsrf.core.tests.interop.NotificationConsumerFactory getNotificationConsumerFactoryPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.wsrf.core.tests.interop.bindings.NotificationConsumerFactorySOAPBindingStub _stub = new org.globus.wsrf.core.tests.interop.bindings.NotificationConsumerFactorySOAPBindingStub(portAddress, this);
            _stub.setPortName(getNotificationConsumerFactoryPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNotificationConsumerFactoryPortEndpointAddress(java.lang.String address) {
        NotificationConsumerFactoryPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.wsrf.core.tests.interop.NotificationConsumerFactory.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.wsrf.core.tests.interop.bindings.NotificationConsumerFactorySOAPBindingStub _stub = new org.globus.wsrf.core.tests.interop.bindings.NotificationConsumerFactorySOAPBindingStub(new java.net.URL(NotificationConsumerFactoryPort_address), this);
                _stub.setPortName(getNotificationConsumerFactoryPortWSDDServiceName());
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
        if ("NotificationConsumerFactoryPort".equals(inputPortName)) {
            return getNotificationConsumerFactoryPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsrf.globus.org/core/tests/interop/service", "NotificationConsumerFactoryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsrf.globus.org/core/tests/interop/service", "NotificationConsumerFactoryPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("NotificationConsumerFactoryPort".equals(portName)) {
            setNotificationConsumerFactoryPortEndpointAddress(address);
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
