/**
 * NotificationTestService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.tests.notification.service;

public interface NotificationTestService extends javax.xml.rpc.Service {
    public java.lang.String getNotificationTestPortTypePortAddress();

    public org.globus.wsrf.tests.notification.NotificationTestPortType getNotificationTestPortTypePort() throws javax.xml.rpc.ServiceException;

    public org.globus.wsrf.tests.notification.NotificationTestPortType getNotificationTestPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
