/**
 * WSBaseNotificationServiceAddressing.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsn;

public interface WSBaseNotificationServiceAddressing extends org.oasis.wsn.WSBaseNotificationService {
    public org.oasis.wsn.NotificationProducer getNotificationProducerPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;

    public org.oasis.wsn.SubscriptionManager getSubscriptionManagerPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;

    public org.oasis.wsn.NotificationConsumer getNotificationConsumerPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;


}
