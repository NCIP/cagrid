/**
 * EnumerationPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package com.enumeration;

public interface EnumerationPortType extends java.rmi.Remote {
    public com.enumeration.StartEnumerationResponse startEnumeration(com.enumeration.StartEnumeration request) throws java.rmi.RemoteException;
    public org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse pullOp(org.xmlsoap.schemas.ws._2004._09.enumeration.Pull body) throws java.rmi.RemoteException;
    public org.xmlsoap.schemas.ws._2004._09.enumeration.RenewResponse renewOp(org.xmlsoap.schemas.ws._2004._09.enumeration.Renew body) throws java.rmi.RemoteException;
    public org.xmlsoap.schemas.ws._2004._09.enumeration.GetStatusResponse getStatusOp(org.xmlsoap.schemas.ws._2004._09.enumeration.GetStatus body) throws java.rmi.RemoteException;
    public void releaseOp(org.xmlsoap.schemas.ws._2004._09.enumeration.Release body) throws java.rmi.RemoteException;
    public org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse enumerateOp(org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate body) throws java.rmi.RemoteException;
}
