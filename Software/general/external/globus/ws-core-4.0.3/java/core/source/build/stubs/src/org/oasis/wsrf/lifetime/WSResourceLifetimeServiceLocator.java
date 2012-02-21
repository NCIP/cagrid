/**
 * WSResourceLifetimeServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.lifetime;

public class WSResourceLifetimeServiceLocator extends org.apache.axis.client.Service implements org.oasis.wsrf.lifetime.WSResourceLifetimeService {

    public WSResourceLifetimeServiceLocator() {
    }


    public WSResourceLifetimeServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSResourceLifetimeServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ImmediateResourceTerminationPort
    private java.lang.String ImmediateResourceTerminationPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getImmediateResourceTerminationPortAddress() {
        return ImmediateResourceTerminationPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ImmediateResourceTerminationPortWSDDServiceName = "ImmediateResourceTerminationPort";

    public java.lang.String getImmediateResourceTerminationPortWSDDServiceName() {
        return ImmediateResourceTerminationPortWSDDServiceName;
    }

    public void setImmediateResourceTerminationPortWSDDServiceName(java.lang.String name) {
        ImmediateResourceTerminationPortWSDDServiceName = name;
    }

    public org.oasis.wsrf.lifetime.ImmediateResourceTermination getImmediateResourceTerminationPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ImmediateResourceTerminationPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getImmediateResourceTerminationPort(endpoint);
    }

    public org.oasis.wsrf.lifetime.ImmediateResourceTermination getImmediateResourceTerminationPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsrf.lifetime.ImmediateResourceTerminationSOAPBindingStub _stub = new org.oasis.wsrf.lifetime.ImmediateResourceTerminationSOAPBindingStub(portAddress, this);
            _stub.setPortName(getImmediateResourceTerminationPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setImmediateResourceTerminationPortEndpointAddress(java.lang.String address) {
        ImmediateResourceTerminationPort_address = address;
    }


    // Use to get a proxy class for ScheduledResourceTerminationPort
    private java.lang.String ScheduledResourceTerminationPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getScheduledResourceTerminationPortAddress() {
        return ScheduledResourceTerminationPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ScheduledResourceTerminationPortWSDDServiceName = "ScheduledResourceTerminationPort";

    public java.lang.String getScheduledResourceTerminationPortWSDDServiceName() {
        return ScheduledResourceTerminationPortWSDDServiceName;
    }

    public void setScheduledResourceTerminationPortWSDDServiceName(java.lang.String name) {
        ScheduledResourceTerminationPortWSDDServiceName = name;
    }

    public org.oasis.wsrf.lifetime.ScheduledResourceTermination getScheduledResourceTerminationPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ScheduledResourceTerminationPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getScheduledResourceTerminationPort(endpoint);
    }

    public org.oasis.wsrf.lifetime.ScheduledResourceTermination getScheduledResourceTerminationPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsrf.lifetime.ScheduledResourceTerminationSOAPBindingStub _stub = new org.oasis.wsrf.lifetime.ScheduledResourceTerminationSOAPBindingStub(portAddress, this);
            _stub.setPortName(getScheduledResourceTerminationPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setScheduledResourceTerminationPortEndpointAddress(java.lang.String address) {
        ScheduledResourceTerminationPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.oasis.wsrf.lifetime.ImmediateResourceTermination.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsrf.lifetime.ImmediateResourceTerminationSOAPBindingStub _stub = new org.oasis.wsrf.lifetime.ImmediateResourceTerminationSOAPBindingStub(new java.net.URL(ImmediateResourceTerminationPort_address), this);
                _stub.setPortName(getImmediateResourceTerminationPortWSDDServiceName());
                return _stub;
            }
            if (org.oasis.wsrf.lifetime.ScheduledResourceTermination.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsrf.lifetime.ScheduledResourceTerminationSOAPBindingStub _stub = new org.oasis.wsrf.lifetime.ScheduledResourceTerminationSOAPBindingStub(new java.net.URL(ScheduledResourceTerminationPort_address), this);
                _stub.setPortName(getScheduledResourceTerminationPortWSDDServiceName());
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
        if ("ImmediateResourceTerminationPort".equals(inputPortName)) {
            return getImmediateResourceTerminationPort();
        }
        else if ("ScheduledResourceTerminationPort".equals(inputPortName)) {
            return getScheduledResourceTerminationPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.wsdl/service", "WS-ResourceLifetimeService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.wsdl/service", "ImmediateResourceTerminationPort"));
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.wsdl/service", "ScheduledResourceTerminationPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("ImmediateResourceTerminationPort".equals(portName)) {
            setImmediateResourceTerminationPortEndpointAddress(address);
        }
        if ("ScheduledResourceTerminationPort".equals(portName)) {
            setScheduledResourceTerminationPortEndpointAddress(address);
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
