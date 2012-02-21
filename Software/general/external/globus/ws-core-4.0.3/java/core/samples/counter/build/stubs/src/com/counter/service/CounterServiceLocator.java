/**
 * CounterServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package com.counter.service;

public class CounterServiceLocator extends org.apache.axis.client.Service implements com.counter.service.CounterService {

    public CounterServiceLocator() {
    }


    public CounterServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CounterServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CounterPortTypePort
    private java.lang.String CounterPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getCounterPortTypePortAddress() {
        return CounterPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CounterPortTypePortWSDDServiceName = "CounterPortTypePort";

    public java.lang.String getCounterPortTypePortWSDDServiceName() {
        return CounterPortTypePortWSDDServiceName;
    }

    public void setCounterPortTypePortWSDDServiceName(java.lang.String name) {
        CounterPortTypePortWSDDServiceName = name;
    }

    public com.counter.CounterPortType getCounterPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CounterPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCounterPortTypePort(endpoint);
    }

    public com.counter.CounterPortType getCounterPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.counter.bindings.CounterPortTypeSOAPBindingStub _stub = new com.counter.bindings.CounterPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getCounterPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCounterPortTypePortEndpointAddress(java.lang.String address) {
        CounterPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.counter.CounterPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.counter.bindings.CounterPortTypeSOAPBindingStub _stub = new com.counter.bindings.CounterPortTypeSOAPBindingStub(new java.net.URL(CounterPortTypePort_address), this);
                _stub.setPortName(getCounterPortTypePortWSDDServiceName());
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
        if ("CounterPortTypePort".equals(inputPortName)) {
            return getCounterPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://counter.com/service", "CounterService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://counter.com/service", "CounterPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("CounterPortTypePort".equals(portName)) {
            setCounterPortTypePortEndpointAddress(address);
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
