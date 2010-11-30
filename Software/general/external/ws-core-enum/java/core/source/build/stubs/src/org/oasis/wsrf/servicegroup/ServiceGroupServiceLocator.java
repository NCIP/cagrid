/**
 * ServiceGroupServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.servicegroup;

public class ServiceGroupServiceLocator extends org.apache.axis.client.Service implements org.oasis.wsrf.servicegroup.ServiceGroupService {

    public ServiceGroupServiceLocator() {
    }


    public ServiceGroupServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceGroupServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServiceGroupPort
    private java.lang.String ServiceGroupPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getServiceGroupPortAddress() {
        return ServiceGroupPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceGroupPortWSDDServiceName = "ServiceGroupPort";

    public java.lang.String getServiceGroupPortWSDDServiceName() {
        return ServiceGroupPortWSDDServiceName;
    }

    public void setServiceGroupPortWSDDServiceName(java.lang.String name) {
        ServiceGroupPortWSDDServiceName = name;
    }

    public org.oasis.wsrf.servicegroup.ServiceGroup getServiceGroupPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceGroupPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceGroupPort(endpoint);
    }

    public org.oasis.wsrf.servicegroup.ServiceGroup getServiceGroupPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsrf.servicegroup.ServiceGroupSOAPBindingStub _stub = new org.oasis.wsrf.servicegroup.ServiceGroupSOAPBindingStub(portAddress, this);
            _stub.setPortName(getServiceGroupPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceGroupPortEndpointAddress(java.lang.String address) {
        ServiceGroupPort_address = address;
    }


    // Use to get a proxy class for ServiceGroupRegistrationPort
    private java.lang.String ServiceGroupRegistrationPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getServiceGroupRegistrationPortAddress() {
        return ServiceGroupRegistrationPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceGroupRegistrationPortWSDDServiceName = "ServiceGroupRegistrationPort";

    public java.lang.String getServiceGroupRegistrationPortWSDDServiceName() {
        return ServiceGroupRegistrationPortWSDDServiceName;
    }

    public void setServiceGroupRegistrationPortWSDDServiceName(java.lang.String name) {
        ServiceGroupRegistrationPortWSDDServiceName = name;
    }

    public org.oasis.wsrf.servicegroup.ServiceGroupRegistration getServiceGroupRegistrationPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceGroupRegistrationPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceGroupRegistrationPort(endpoint);
    }

    public org.oasis.wsrf.servicegroup.ServiceGroupRegistration getServiceGroupRegistrationPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsrf.servicegroup.ServiceGroupRegistrationSOAPBindingStub _stub = new org.oasis.wsrf.servicegroup.ServiceGroupRegistrationSOAPBindingStub(portAddress, this);
            _stub.setPortName(getServiceGroupRegistrationPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceGroupRegistrationPortEndpointAddress(java.lang.String address) {
        ServiceGroupRegistrationPort_address = address;
    }


    // Use to get a proxy class for ServiceGroupEntryPort
    private java.lang.String ServiceGroupEntryPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getServiceGroupEntryPortAddress() {
        return ServiceGroupEntryPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceGroupEntryPortWSDDServiceName = "ServiceGroupEntryPort";

    public java.lang.String getServiceGroupEntryPortWSDDServiceName() {
        return ServiceGroupEntryPortWSDDServiceName;
    }

    public void setServiceGroupEntryPortWSDDServiceName(java.lang.String name) {
        ServiceGroupEntryPortWSDDServiceName = name;
    }

    public org.oasis.wsrf.servicegroup.ServiceGroupEntry getServiceGroupEntryPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceGroupEntryPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceGroupEntryPort(endpoint);
    }

    public org.oasis.wsrf.servicegroup.ServiceGroupEntry getServiceGroupEntryPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsrf.servicegroup.ServiceGroupEntrySOAPBindingStub _stub = new org.oasis.wsrf.servicegroup.ServiceGroupEntrySOAPBindingStub(portAddress, this);
            _stub.setPortName(getServiceGroupEntryPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceGroupEntryPortEndpointAddress(java.lang.String address) {
        ServiceGroupEntryPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.oasis.wsrf.servicegroup.ServiceGroup.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsrf.servicegroup.ServiceGroupSOAPBindingStub _stub = new org.oasis.wsrf.servicegroup.ServiceGroupSOAPBindingStub(new java.net.URL(ServiceGroupPort_address), this);
                _stub.setPortName(getServiceGroupPortWSDDServiceName());
                return _stub;
            }
            if (org.oasis.wsrf.servicegroup.ServiceGroupRegistration.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsrf.servicegroup.ServiceGroupRegistrationSOAPBindingStub _stub = new org.oasis.wsrf.servicegroup.ServiceGroupRegistrationSOAPBindingStub(new java.net.URL(ServiceGroupRegistrationPort_address), this);
                _stub.setPortName(getServiceGroupRegistrationPortWSDDServiceName());
                return _stub;
            }
            if (org.oasis.wsrf.servicegroup.ServiceGroupEntry.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsrf.servicegroup.ServiceGroupEntrySOAPBindingStub _stub = new org.oasis.wsrf.servicegroup.ServiceGroupEntrySOAPBindingStub(new java.net.URL(ServiceGroupEntryPort_address), this);
                _stub.setPortName(getServiceGroupEntryPortWSDDServiceName());
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
        if ("ServiceGroupPort".equals(inputPortName)) {
            return getServiceGroupPort();
        }
        else if ("ServiceGroupRegistrationPort".equals(inputPortName)) {
            return getServiceGroupRegistrationPort();
        }
        else if ("ServiceGroupEntryPort".equals(inputPortName)) {
            return getServiceGroupEntryPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.wsdl/service", "ServiceGroupService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.wsdl/service", "ServiceGroupPort"));
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.wsdl/service", "ServiceGroupRegistrationPort"));
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.wsdl/service", "ServiceGroupEntryPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("ServiceGroupPort".equals(portName)) {
            setServiceGroupPortEndpointAddress(address);
        }
        if ("ServiceGroupRegistrationPort".equals(portName)) {
            setServiceGroupRegistrationPortEndpointAddress(address);
        }
        if ("ServiceGroupEntryPort".equals(portName)) {
            setServiceGroupEntryPortEndpointAddress(address);
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
