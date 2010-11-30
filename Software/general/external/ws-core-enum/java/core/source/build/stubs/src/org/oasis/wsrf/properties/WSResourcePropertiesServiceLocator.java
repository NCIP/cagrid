/**
 * WSResourcePropertiesServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.properties;

public class WSResourcePropertiesServiceLocator extends org.apache.axis.client.Service implements org.oasis.wsrf.properties.WSResourcePropertiesService {

    public WSResourcePropertiesServiceLocator() {
    }


    public WSResourcePropertiesServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSResourcePropertiesServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GetResourcePropertyPort
    private java.lang.String GetResourcePropertyPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getGetResourcePropertyPortAddress() {
        return GetResourcePropertyPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GetResourcePropertyPortWSDDServiceName = "GetResourcePropertyPort";

    public java.lang.String getGetResourcePropertyPortWSDDServiceName() {
        return GetResourcePropertyPortWSDDServiceName;
    }

    public void setGetResourcePropertyPortWSDDServiceName(java.lang.String name) {
        GetResourcePropertyPortWSDDServiceName = name;
    }

    public org.oasis.wsrf.properties.GetResourceProperty getGetResourcePropertyPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GetResourcePropertyPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGetResourcePropertyPort(endpoint);
    }

    public org.oasis.wsrf.properties.GetResourceProperty getGetResourcePropertyPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsrf.properties.GetResourcePropertySOAPBindingStub _stub = new org.oasis.wsrf.properties.GetResourcePropertySOAPBindingStub(portAddress, this);
            _stub.setPortName(getGetResourcePropertyPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGetResourcePropertyPortEndpointAddress(java.lang.String address) {
        GetResourcePropertyPort_address = address;
    }


    // Use to get a proxy class for SetResourcePropertiesPort
    private java.lang.String SetResourcePropertiesPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getSetResourcePropertiesPortAddress() {
        return SetResourcePropertiesPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SetResourcePropertiesPortWSDDServiceName = "SetResourcePropertiesPort";

    public java.lang.String getSetResourcePropertiesPortWSDDServiceName() {
        return SetResourcePropertiesPortWSDDServiceName;
    }

    public void setSetResourcePropertiesPortWSDDServiceName(java.lang.String name) {
        SetResourcePropertiesPortWSDDServiceName = name;
    }

    public org.oasis.wsrf.properties.SetResourceProperties_PortType getSetResourcePropertiesPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SetResourcePropertiesPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSetResourcePropertiesPort(endpoint);
    }

    public org.oasis.wsrf.properties.SetResourceProperties_PortType getSetResourcePropertiesPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsrf.properties.SetResourcePropertiesSOAPBindingStub _stub = new org.oasis.wsrf.properties.SetResourcePropertiesSOAPBindingStub(portAddress, this);
            _stub.setPortName(getSetResourcePropertiesPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSetResourcePropertiesPortEndpointAddress(java.lang.String address) {
        SetResourcePropertiesPort_address = address;
    }


    // Use to get a proxy class for GetMultipleResourcePropertiesPort
    private java.lang.String GetMultipleResourcePropertiesPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getGetMultipleResourcePropertiesPortAddress() {
        return GetMultipleResourcePropertiesPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GetMultipleResourcePropertiesPortWSDDServiceName = "GetMultipleResourcePropertiesPort";

    public java.lang.String getGetMultipleResourcePropertiesPortWSDDServiceName() {
        return GetMultipleResourcePropertiesPortWSDDServiceName;
    }

    public void setGetMultipleResourcePropertiesPortWSDDServiceName(java.lang.String name) {
        GetMultipleResourcePropertiesPortWSDDServiceName = name;
    }

    public org.oasis.wsrf.properties.GetMultipleResourceProperties_PortType getGetMultipleResourcePropertiesPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GetMultipleResourcePropertiesPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGetMultipleResourcePropertiesPort(endpoint);
    }

    public org.oasis.wsrf.properties.GetMultipleResourceProperties_PortType getGetMultipleResourcePropertiesPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsrf.properties.GetMultipleResourcePropertiesSOAPBindingStub _stub = new org.oasis.wsrf.properties.GetMultipleResourcePropertiesSOAPBindingStub(portAddress, this);
            _stub.setPortName(getGetMultipleResourcePropertiesPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGetMultipleResourcePropertiesPortEndpointAddress(java.lang.String address) {
        GetMultipleResourcePropertiesPort_address = address;
    }


    // Use to get a proxy class for QueryResourcePropertiesPort
    private java.lang.String QueryResourcePropertiesPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getQueryResourcePropertiesPortAddress() {
        return QueryResourcePropertiesPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String QueryResourcePropertiesPortWSDDServiceName = "QueryResourcePropertiesPort";

    public java.lang.String getQueryResourcePropertiesPortWSDDServiceName() {
        return QueryResourcePropertiesPortWSDDServiceName;
    }

    public void setQueryResourcePropertiesPortWSDDServiceName(java.lang.String name) {
        QueryResourcePropertiesPortWSDDServiceName = name;
    }

    public org.oasis.wsrf.properties.QueryResourceProperties_PortType getQueryResourcePropertiesPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(QueryResourcePropertiesPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getQueryResourcePropertiesPort(endpoint);
    }

    public org.oasis.wsrf.properties.QueryResourceProperties_PortType getQueryResourcePropertiesPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsrf.properties.QueryResourcePropertiesSOAPBindingStub _stub = new org.oasis.wsrf.properties.QueryResourcePropertiesSOAPBindingStub(portAddress, this);
            _stub.setPortName(getQueryResourcePropertiesPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setQueryResourcePropertiesPortEndpointAddress(java.lang.String address) {
        QueryResourcePropertiesPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.oasis.wsrf.properties.GetResourceProperty.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsrf.properties.GetResourcePropertySOAPBindingStub _stub = new org.oasis.wsrf.properties.GetResourcePropertySOAPBindingStub(new java.net.URL(GetResourcePropertyPort_address), this);
                _stub.setPortName(getGetResourcePropertyPortWSDDServiceName());
                return _stub;
            }
            if (org.oasis.wsrf.properties.SetResourceProperties_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsrf.properties.SetResourcePropertiesSOAPBindingStub _stub = new org.oasis.wsrf.properties.SetResourcePropertiesSOAPBindingStub(new java.net.URL(SetResourcePropertiesPort_address), this);
                _stub.setPortName(getSetResourcePropertiesPortWSDDServiceName());
                return _stub;
            }
            if (org.oasis.wsrf.properties.GetMultipleResourceProperties_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsrf.properties.GetMultipleResourcePropertiesSOAPBindingStub _stub = new org.oasis.wsrf.properties.GetMultipleResourcePropertiesSOAPBindingStub(new java.net.URL(GetMultipleResourcePropertiesPort_address), this);
                _stub.setPortName(getGetMultipleResourcePropertiesPortWSDDServiceName());
                return _stub;
            }
            if (org.oasis.wsrf.properties.QueryResourceProperties_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsrf.properties.QueryResourcePropertiesSOAPBindingStub _stub = new org.oasis.wsrf.properties.QueryResourcePropertiesSOAPBindingStub(new java.net.URL(QueryResourcePropertiesPort_address), this);
                _stub.setPortName(getQueryResourcePropertiesPortWSDDServiceName());
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
        if ("GetResourcePropertyPort".equals(inputPortName)) {
            return getGetResourcePropertyPort();
        }
        else if ("SetResourcePropertiesPort".equals(inputPortName)) {
            return getSetResourcePropertiesPort();
        }
        else if ("GetMultipleResourcePropertiesPort".equals(inputPortName)) {
            return getGetMultipleResourcePropertiesPort();
        }
        else if ("QueryResourcePropertiesPort".equals(inputPortName)) {
            return getQueryResourcePropertiesPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.wsdl/service", "WS-ResourcePropertiesService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.wsdl/service", "GetResourcePropertyPort"));
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.wsdl/service", "SetResourcePropertiesPort"));
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.wsdl/service", "GetMultipleResourcePropertiesPort"));
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.wsdl/service", "QueryResourcePropertiesPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("GetResourcePropertyPort".equals(portName)) {
            setGetResourcePropertyPortEndpointAddress(address);
        }
        if ("SetResourcePropertiesPort".equals(portName)) {
            setSetResourcePropertiesPortEndpointAddress(address);
        }
        if ("GetMultipleResourcePropertiesPort".equals(portName)) {
            setGetMultipleResourcePropertiesPortEndpointAddress(address);
        }
        if ("QueryResourcePropertiesPort".equals(portName)) {
            setQueryResourcePropertiesPortEndpointAddress(address);
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
