/**
 * WidgetNotificationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package com.widgets.service;

public class WidgetNotificationServiceLocator extends org.apache.axis.client.Service implements com.widgets.service.WidgetNotificationService {

    public WidgetNotificationServiceLocator() {
    }


    public WidgetNotificationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WidgetNotificationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WidgetNotificationPortTypePort
    private java.lang.String WidgetNotificationPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getWidgetNotificationPortTypePortAddress() {
        return WidgetNotificationPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WidgetNotificationPortTypePortWSDDServiceName = "WidgetNotificationPortTypePort";

    public java.lang.String getWidgetNotificationPortTypePortWSDDServiceName() {
        return WidgetNotificationPortTypePortWSDDServiceName;
    }

    public void setWidgetNotificationPortTypePortWSDDServiceName(java.lang.String name) {
        WidgetNotificationPortTypePortWSDDServiceName = name;
    }

    public com.widgets.WidgetNotificationPortType getWidgetNotificationPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WidgetNotificationPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWidgetNotificationPortTypePort(endpoint);
    }

    public com.widgets.WidgetNotificationPortType getWidgetNotificationPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.widgets.bindings.WidgetNotificationPortTypeSOAPBindingStub _stub = new com.widgets.bindings.WidgetNotificationPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getWidgetNotificationPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWidgetNotificationPortTypePortEndpointAddress(java.lang.String address) {
        WidgetNotificationPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.widgets.WidgetNotificationPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.widgets.bindings.WidgetNotificationPortTypeSOAPBindingStub _stub = new com.widgets.bindings.WidgetNotificationPortTypeSOAPBindingStub(new java.net.URL(WidgetNotificationPortTypePort_address), this);
                _stub.setPortName(getWidgetNotificationPortTypePortWSDDServiceName());
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
        if ("WidgetNotificationPortTypePort".equals(inputPortName)) {
            return getWidgetNotificationPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://widgets.com/service", "WidgetNotificationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://widgets.com/service", "WidgetNotificationPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("WidgetNotificationPortTypePort".equals(portName)) {
            setWidgetNotificationPortTypePortEndpointAddress(address);
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
