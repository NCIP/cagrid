/**
 * SubscriptionManager.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.core.notification;

public interface SubscriptionManager extends java.rmi.Remote {
    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy destroyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.lifetime.ResourceNotDestroyedFaultType, org.oasis.wsrf.lifetime.ResourceUnknownFaultType;
    public org.oasis.wsrf.lifetime.SetTerminationTimeResponse setTerminationTime(org.oasis.wsrf.lifetime.SetTerminationTime setTerminationTimeRequest) throws java.rmi.RemoteException, org.oasis.wsrf.lifetime.UnableToSetTerminationTimeFaultType, org.oasis.wsrf.lifetime.ResourceUnknownFaultType, org.oasis.wsrf.lifetime.TerminationTimeChangeRejectedFaultType;
    public org.oasis.wsn.PauseSubscriptionResponse pauseSubscription(org.oasis.wsn.PauseSubscription pauseSubscriptionRequest) throws java.rmi.RemoteException, org.oasis.wsn.PauseFailedFaultType, org.oasis.wsn.ResourceUnknownFaultType;
    public org.oasis.wsn.ResumeSubscriptionResponse resumeSubscription(org.oasis.wsn.ResumeSubscription resumeSubscriptionRequest) throws java.rmi.RemoteException, org.oasis.wsn.ResumeFailedFaultType, org.oasis.wsn.ResourceUnknownFaultType;
}
