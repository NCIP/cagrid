/**
 * EnumerationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.ws._2004._09.enumeration.service;

public interface EnumerationService extends javax.xml.rpc.Service {
    public java.lang.String getDataSourcePortAddress();

    public org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource getDataSourcePort() throws javax.xml.rpc.ServiceException;

    public org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource getDataSourcePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getDataSourceStartPortAddress();

    public org.xmlsoap.schemas.ws._2004._09.enumeration.DataSourceStart getDataSourceStartPort() throws javax.xml.rpc.ServiceException;

    public org.xmlsoap.schemas.ws._2004._09.enumeration.DataSourceStart getDataSourceStartPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
