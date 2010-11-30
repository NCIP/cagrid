/**
 * WSResourceLifetimeServiceAddressing.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.lifetime;

public interface WSResourceLifetimeServiceAddressing extends org.oasis.wsrf.lifetime.WSResourceLifetimeService {
    public org.oasis.wsrf.lifetime.ImmediateResourceTermination getImmediateResourceTerminationPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.lifetime.ScheduledResourceTermination getScheduledResourceTerminationPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;


}
