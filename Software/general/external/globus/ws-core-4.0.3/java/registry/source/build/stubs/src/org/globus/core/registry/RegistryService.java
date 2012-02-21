/**
 * RegistryService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.core.registry;

public interface RegistryService extends javax.xml.rpc.Service {
    public java.lang.String getRegistryPortTypePortAddress();

    public org.globus.core.registry.RegistryPortType getRegistryPortTypePort() throws javax.xml.rpc.ServiceException;

    public org.globus.core.registry.RegistryPortType getRegistryPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
