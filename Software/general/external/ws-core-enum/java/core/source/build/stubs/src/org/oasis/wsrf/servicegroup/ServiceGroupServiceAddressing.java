/**
 * ServiceGroupServiceAddressing.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.servicegroup;

public interface ServiceGroupServiceAddressing extends org.oasis.wsrf.servicegroup.ServiceGroupService {
    public org.oasis.wsrf.servicegroup.ServiceGroup getServiceGroupPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.servicegroup.ServiceGroupRegistration getServiceGroupRegistrationPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;

    public org.oasis.wsrf.servicegroup.ServiceGroupEntry getServiceGroupEntryPort(org.apache.axis.message.addressing.EndpointReferenceType reference) throws javax.xml.rpc.ServiceException;


}
