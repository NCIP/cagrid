/**
 * SecurityTestPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.tests.security;

public interface SecurityTestPortType extends java.rmi.Remote {
    public org.globus.wsrf.tests.security.NoAuthResponse noAuth(org.globus.wsrf.tests.security.NoAuthRequest parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiTransportOnlyResponse gsiTransportOnly(org.globus.wsrf.tests.security.GsiTransportOnly parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiTransportIntegrityResponse gsiTransportIntegrity(org.globus.wsrf.tests.security.GsiTransportIntegrity parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiTransportPrivacyResponse gsiTransportPrivacy(org.globus.wsrf.tests.security.GsiTransportPrivacy parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiSecConvOnlyResponse gsiSecConvOnly(org.globus.wsrf.tests.security.GsiSecConvOnly parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiSecConvIntegrityResponse gsiSecConvIntegrity(org.globus.wsrf.tests.security.GsiSecConvIntegrity parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiSecConvPrivacyResponse gsiSecConvPrivacy(org.globus.wsrf.tests.security.GsiSecConvPrivacy parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiSecConvDelegResponse gsiSecConvDeleg(org.globus.wsrf.tests.security.GsiSecConvDeleg parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiSecMsgOnlyResponse gsiSecMsgOnly(org.globus.wsrf.tests.security.GsiSecMsgOnly parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiSecMsgPrivacyResponse gsiSecMsgPrivacy(org.globus.wsrf.tests.security.GsiSecMsgPrivacy parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiSecMsgIntegrityResponse gsiSecMsgIntegrity(org.globus.wsrf.tests.security.GsiSecMsgIntegrity parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.GsiSecResponse gsiSec(org.globus.wsrf.tests.security.GsiSec parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.CreateResourceResponse createResource(boolean parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.AlterSecurityDescResponse alterSecurityDesc(java.lang.String parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.SetAuthzResponse setAuthz(java.lang.String parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.SetAnonymousAuthzResponse setAnonymousAuthz(org.globus.wsrf.tests.security.SetAnonymousAuthz parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.SetValueResponse setValue(int parameters) throws java.rmi.RemoteException;
    public int getValue(org.globus.wsrf.tests.security.GetValue parameters) throws java.rmi.RemoteException;
    public org.globus.wsrf.tests.security.SetServiceAuthzOutput setServiceAuthz(java.lang.String parameters) throws java.rmi.RemoteException;
    public java.lang.String getSecurityProperty(java.lang.String parameters) throws java.rmi.RemoteException;
    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsn.SubscribeResponse subscribe(org.oasis.wsn.Subscribe subscribeRequest) throws java.rmi.RemoteException, org.oasis.wsn.TopicNotSupportedFaultType, org.oasis.wsn.InvalidTopicExpressionFaultType, org.oasis.wsn.SubscribeCreationFailedFaultType, org.oasis.wsn.ResourceUnknownFaultType, org.oasis.wsn.TopicPathDialectUnknownFaultType;
    public org.oasis.wsn.GetCurrentMessageResponse getCurrentMessage(org.oasis.wsn.GetCurrentMessage getCurrentMessageRequest) throws java.rmi.RemoteException, org.oasis.wsn.TopicNotSupportedFaultType, org.oasis.wsn.InvalidTopicExpressionFaultType, org.oasis.wsn.NoCurrentMessageOnTopicFaultType, org.oasis.wsn.ResourceUnknownFaultType;
}
