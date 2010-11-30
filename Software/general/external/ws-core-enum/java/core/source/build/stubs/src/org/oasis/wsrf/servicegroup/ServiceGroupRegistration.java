/**
 * ServiceGroupRegistration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.servicegroup;

public interface ServiceGroupRegistration extends java.rmi.Remote {
    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.apache.axis.message.addressing.EndpointReferenceType add(org.oasis.wsrf.servicegroup.Add addRequest) throws java.rmi.RemoteException, org.oasis.wsrf.servicegroup.ContentCreationFailedFaultType, org.oasis.wsrf.servicegroup.AddRefusedFaultType, org.oasis.wsrf.servicegroup.UnsupportedMemberInterfaceFaultType;
}
