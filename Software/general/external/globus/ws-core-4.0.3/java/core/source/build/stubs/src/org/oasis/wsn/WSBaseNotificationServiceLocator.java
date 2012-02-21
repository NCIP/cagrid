/**
 * WSBaseNotificationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsn;

public class WSBaseNotificationServiceLocator extends org.apache.axis.client.Service implements org.oasis.wsn.WSBaseNotificationService {

    public WSBaseNotificationServiceLocator() {
    }


    public WSBaseNotificationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSBaseNotificationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NotificationProducerPort
    private java.lang.String NotificationProducerPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getNotificationProducerPortAddress() {
        return NotificationProducerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NotificationProducerPortWSDDServiceName = "NotificationProducerPort";

    public java.lang.String getNotificationProducerPortWSDDServiceName() {
        return NotificationProducerPortWSDDServiceName;
    }

    public void setNotificationProducerPortWSDDServiceName(java.lang.String name) {
        NotificationProducerPortWSDDServiceName = name;
    }

    public org.oasis.wsn.NotificationProducer getNotificationProducerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NotificationProducerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNotificationProducerPort(endpoint);
    }

    public org.oasis.wsn.NotificationProducer getNotificationProducerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsn.NotificationProducerSOAPBindingStub _stub = new org.oasis.wsn.NotificationProducerSOAPBindingStub(portAddress, this);
            _stub.setPortName(getNotificationProducerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNotificationProducerPortEndpointAddress(java.lang.String address) {
        NotificationProducerPort_address = address;
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

    public org.oasis.wsn.SubscriptionManager getSubscriptionManagerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SubscriptionManagerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSubscriptionManagerPort(endpoint);
    }

    public org.oasis.wsn.SubscriptionManager getSubscriptionManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsn.SubscriptionManagerSOAPBindingStub _stub = new org.oasis.wsn.SubscriptionManagerSOAPBindingStub(portAddress, this);
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


    // Use to get a proxy class for NotificationConsumerPort
    private java.lang.String NotificationConsumerPort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getNotificationConsumerPortAddress() {
        return NotificationConsumerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NotificationConsumerPortWSDDServiceName = "NotificationConsumerPort";

    public java.lang.String getNotificationConsumerPortWSDDServiceName() {
        return NotificationConsumerPortWSDDServiceName;
    }

    public void setNotificationConsumerPortWSDDServiceName(java.lang.String name) {
        NotificationConsumerPortWSDDServiceName = name;
    }

    public org.oasis.wsn.NotificationConsumer getNotificationConsumerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NotificationConsumerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNotificationConsumerPort(endpoint);
    }

    public org.oasis.wsn.NotificationConsumer getNotificationConsumerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.oasis.wsn.NotificationConsumerSOAPBindingStub _stub = new org.oasis.wsn.NotificationConsumerSOAPBindingStub(portAddress, this);
            _stub.setPortName(getNotificationConsumerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNotificationConsumerPortEndpointAddress(java.lang.String address) {
        NotificationConsumerPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.oasis.wsn.NotificationProducer.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsn.NotificationProducerSOAPBindingStub _stub = new org.oasis.wsn.NotificationProducerSOAPBindingStub(new java.net.URL(NotificationProducerPort_address), this);
                _stub.setPortName(getNotificationProducerPortWSDDServiceName());
                return _stub;
            }
            if (org.oasis.wsn.SubscriptionManager.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsn.SubscriptionManagerSOAPBindingStub _stub = new org.oasis.wsn.SubscriptionManagerSOAPBindingStub(new java.net.URL(SubscriptionManagerPort_address), this);
                _stub.setPortName(getSubscriptionManagerPortWSDDServiceName());
                return _stub;
            }
            if (org.oasis.wsn.NotificationConsumer.class.isAssignableFrom(serviceEndpointInterface)) {
                org.oasis.wsn.NotificationConsumerSOAPBindingStub _stub = new org.oasis.wsn.NotificationConsumerSOAPBindingStub(new java.net.URL(NotificationConsumerPort_address), this);
                _stub.setPortName(getNotificationConsumerPortWSDDServiceName());
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
        if ("NotificationProducerPort".equals(inputPortName)) {
            return getNotificationProducerPort();
        }
        else if ("SubscriptionManagerPort".equals(inputPortName)) {
            return getSubscriptionManagerPort();
        }
        else if ("NotificationConsumerPort".equals(inputPortName)) {
            return getNotificationConsumerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.wsdl/service", "WS-BaseNotificationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.wsdl/service", "NotificationProducerPort"));
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.wsdl/service", "SubscriptionManagerPort"));
            ports.add(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.wsdl/service", "NotificationConsumerPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("NotificationProducerPort".equals(portName)) {
            setNotificationProducerPortEndpointAddress(address);
        }
        if ("SubscriptionManagerPort".equals(portName)) {
            setSubscriptionManagerPortEndpointAddress(address);
        }
        if ("NotificationConsumerPort".equals(portName)) {
            setNotificationConsumerPortEndpointAddress(address);
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
