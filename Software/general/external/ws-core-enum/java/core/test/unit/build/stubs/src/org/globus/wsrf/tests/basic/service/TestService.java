/**
 * TestService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.tests.basic.service;

public interface TestService extends javax.xml.rpc.Service {
    public java.lang.String getTestPortTypePortAddress();

    public org.globus.wsrf.tests.basic.TestPortType getTestPortTypePort() throws javax.xml.rpc.ServiceException;

    public org.globus.wsrf.tests.basic.TestPortType getTestPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
