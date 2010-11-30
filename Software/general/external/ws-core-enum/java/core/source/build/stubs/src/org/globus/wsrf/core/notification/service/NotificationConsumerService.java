/**
 * NotificationConsumerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.core.notification.service;

public interface NotificationConsumerService extends javax.xml.rpc.Service {
    public java.lang.String getConsumerPortAddress();

    public org.globus.wsrf.core.notification.Consumer getConsumerPort() throws javax.xml.rpc.ServiceException;

    public org.globus.wsrf.core.notification.Consumer getConsumerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
