/**
 * ServiceGroupService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.servicegroup;

public interface ServiceGroupService extends javax.xml.rpc.Service {
    public java.lang.String getServiceGroupPortAddress();

    public org.oasis.wsrf.servicegroup.ServiceGroup getServiceGroupPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.servicegroup.ServiceGroup getServiceGroupPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getServiceGroupRegistrationPortAddress();

    public org.oasis.wsrf.servicegroup.ServiceGroupRegistration getServiceGroupRegistrationPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.servicegroup.ServiceGroupRegistration getServiceGroupRegistrationPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getServiceGroupEntryPortAddress();

    public org.oasis.wsrf.servicegroup.ServiceGroupEntry getServiceGroupEntryPort() throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.servicegroup.ServiceGroupEntry getServiceGroupEntryPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
