/**
 * SecurityTestService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.tests.security.service;

public interface SecurityTestService extends javax.xml.rpc.Service {
    public java.lang.String getSecurityTestPortTypePortAddress();

    public org.globus.wsrf.tests.security.SecurityTestPortType getSecurityTestPortTypePort() throws javax.xml.rpc.ServiceException;

    public org.globus.wsrf.tests.security.SecurityTestPortType getSecurityTestPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
