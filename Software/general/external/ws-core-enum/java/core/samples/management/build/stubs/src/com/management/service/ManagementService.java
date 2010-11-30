/**
 * ManagementService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package com.management.service;

public interface ManagementService extends javax.xml.rpc.Service {
    public java.lang.String getManagementPortPortAddress();

    public com.management.ManagementPort getManagementPortPort() throws javax.xml.rpc.ServiceException;

    public com.management.ManagementPort getManagementPortPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
