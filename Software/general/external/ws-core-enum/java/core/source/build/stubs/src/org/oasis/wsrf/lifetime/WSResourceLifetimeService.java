/**
 * WSResourceLifetimeService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.lifetime;

public interface WSResourceLifetimeService extends javax.xml.rpc.Service {
    public java.lang.String getImmediateResourceTerminationPortAddress();

    public org.oasis.wsrf.lifetime.ImmediateResourceTermination getImmediateResourceTerminationPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.lifetime.ImmediateResourceTermination getImmediateResourceTerminationPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getScheduledResourceTerminationPortAddress();

    public org.oasis.wsrf.lifetime.ScheduledResourceTermination getScheduledResourceTerminationPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.lifetime.ScheduledResourceTermination getScheduledResourceTerminationPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
