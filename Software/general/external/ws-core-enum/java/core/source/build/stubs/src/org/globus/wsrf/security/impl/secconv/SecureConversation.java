/**
 * SecureConversation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.security.impl.secconv;

public interface SecureConversation extends java.rmi.Remote {
    public org.globus.ws.trust.RequestSecurityTokenResponseType requestSecurityToken(org.globus.ws.trust.RequestSecurityTokenType request) throws java.rmi.RemoteException, org.globus.wsrf.security.impl.secconv.MalformedMessageFaultType, org.globus.wsrf.security.impl.secconv.EncodingTypeNotSupportedFaultType, org.globus.wsrf.security.impl.secconv.BinaryExchangeFaultType, org.globus.wsrf.security.impl.secconv.TokenTypeNotSupportedFaultType, org.globus.wsrf.security.impl.secconv.ValueTypeNotSupportedFaultType, org.globus.wsrf.security.impl.secconv.RequestTypeNotSupportedFaultType;
    public void requestSecurityTokenResponse(org.globus.ws.trust.holders.RequestSecurityTokenResponseTypeHolder response) throws java.rmi.RemoteException, org.globus.wsrf.security.impl.secconv.MalformedMessageFaultType, org.globus.wsrf.security.impl.secconv.EncodingTypeNotSupportedFaultType, org.globus.wsrf.security.impl.secconv.InvalidContextIdFaultType, org.globus.wsrf.security.impl.secconv.BinaryExchangeFaultType, org.globus.wsrf.security.impl.secconv.TokenTypeNotSupportedFaultType, org.globus.wsrf.security.impl.secconv.ValueTypeNotSupportedFaultType, org.globus.wsrf.security.impl.secconv.RequestTypeNotSupportedFaultType;
}
