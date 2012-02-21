/**
 * WSBaseNotificationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsn;

public interface WSBaseNotificationService extends javax.xml.rpc.Service {
    public java.lang.String getNotificationProducerPortAddress();

    public org.oasis.wsn.NotificationProducer getNotificationProducerPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsn.NotificationProducer getNotificationProducerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getSubscriptionManagerPortAddress();

    public org.oasis.wsn.SubscriptionManager getSubscriptionManagerPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsn.SubscriptionManager getSubscriptionManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getNotificationConsumerPortAddress();

    public org.oasis.wsn.NotificationConsumer getNotificationConsumerPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsn.NotificationConsumer getNotificationConsumerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
