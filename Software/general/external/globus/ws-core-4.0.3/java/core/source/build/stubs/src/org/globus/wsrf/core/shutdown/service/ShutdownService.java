/**
 * ShutdownService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.core.shutdown.service;

public interface ShutdownService extends javax.xml.rpc.Service {
    public java.lang.String getShutdownPortTypePortAddress();

    public org.globus.wsrf.core.shutdown.ShutdownPortType getShutdownPortTypePort() throws javax.xml.rpc.ServiceException;

    public org.globus.wsrf.core.shutdown.ShutdownPortType getShutdownPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
