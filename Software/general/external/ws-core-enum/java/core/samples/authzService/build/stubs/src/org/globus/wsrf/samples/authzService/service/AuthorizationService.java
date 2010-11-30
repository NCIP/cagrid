/**
 * AuthorizationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.samples.authzService.service;

public interface AuthorizationService extends javax.xml.rpc.Service {
    public java.lang.String getAuthzServicePortTypePortAddress();

    public org.globus.wsrf.samples.authzService.AuthzServicePortType getAuthzServicePortTypePort() throws javax.xml.rpc.ServiceException;

    public org.globus.wsrf.samples.authzService.AuthzServicePortType getAuthzServicePortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
