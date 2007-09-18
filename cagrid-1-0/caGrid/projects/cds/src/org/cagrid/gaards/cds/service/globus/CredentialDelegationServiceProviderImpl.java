package org.cagrid.gaards.cds.service.globus;

import org.cagrid.gaards.cds.service.CredentialDelegationServiceImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the CredentialDelegationServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public class CredentialDelegationServiceProviderImpl{
	
	CredentialDelegationServiceImpl impl;
	
	public CredentialDelegationServiceProviderImpl() throws RemoteException {
		impl = new CredentialDelegationServiceImpl();
	}
	

    public org.cagrid.gaards.cds.stubs.DelegateCredentialResponse delegateCredential(org.cagrid.gaards.cds.stubs.DelegateCredentialRequest params) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.InvalidPolicyFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    org.cagrid.gaards.cds.stubs.DelegateCredentialResponse boxedResult = new org.cagrid.gaards.cds.stubs.DelegateCredentialResponse();
    impl.delegateCredential(params.getPolicy().getDelegationPolicy());
    return boxedResult;
  }

}
