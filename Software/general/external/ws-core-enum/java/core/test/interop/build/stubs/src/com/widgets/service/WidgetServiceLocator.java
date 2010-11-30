/**
 * WidgetServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package com.widgets.service;

public class WidgetServiceLocator extends org.apache.axis.client.Service implements com.widgets.service.WidgetService {

    public WidgetServiceLocator() {
    }


    public WidgetServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WidgetServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WidgetPortTypePort
    private java.lang.String WidgetPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getWidgetPortTypePortAddress() {
        return WidgetPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WidgetPortTypePortWSDDServiceName = "WidgetPortTypePort";

    public java.lang.String getWidgetPortTypePortWSDDServiceName() {
        return WidgetPortTypePortWSDDServiceName;
    }

    public void setWidgetPortTypePortWSDDServiceName(java.lang.String name) {
        WidgetPortTypePortWSDDServiceName = name;
    }

    public com.widgets.WidgetPortType getWidgetPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WidgetPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWidgetPortTypePort(endpoint);
    }

    public com.widgets.WidgetPortType getWidgetPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.widgets.bindings.WidgetPortTypeSOAPBindingStub _stub = new com.widgets.bindings.WidgetPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getWidgetPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWidgetPortTypePortEndpointAddress(java.lang.String address) {
        WidgetPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.widgets.WidgetPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.widgets.bindings.WidgetPortTypeSOAPBindingStub _stub = new com.widgets.bindings.WidgetPortTypeSOAPBindingStub(new java.net.URL(WidgetPortTypePort_address), this);
                _stub.setPortName(getWidgetPortTypePortWSDDServiceName());
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
        if ("WidgetPortTypePort".equals(inputPortName)) {
            return getWidgetPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://widgets.com/service", "WidgetService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://widgets.com/service", "WidgetPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("WidgetPortTypePort".equals(portName)) {
            setWidgetPortTypePortEndpointAddress(address);
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
