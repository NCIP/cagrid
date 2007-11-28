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
	

    public org.cagrid.gaards.cds.stubs.FindDelegatedCredentialsResponse findDelegatedCredentials(org.cagrid.gaards.cds.stubs.FindDelegatedCredentialsRequest params) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    org.cagrid.gaards.cds.stubs.FindDelegatedCredentialsResponse boxedResult = new org.cagrid.gaards.cds.stubs.FindDelegatedCredentialsResponse();
    boxedResult.setDelegationRecord(impl.findDelegatedCredentials(params.getFilter().getDelegationRecordFilter()));
    return boxedResult;
  }

    public org.cagrid.gaards.cds.stubs.InitiateDelegationResponse initiateDelegation(org.cagrid.gaards.cds.stubs.InitiateDelegationRequest params) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.InvalidPolicyFault, org.cagrid.gaards.cds.stubs.types.DelegationFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    org.cagrid.gaards.cds.stubs.InitiateDelegationResponse boxedResult = new org.cagrid.gaards.cds.stubs.InitiateDelegationResponse();
    boxedResult.setDelegationSigningRequest(impl.initiateDelegation(params.getReq().getDelegationRequest()));
    return boxedResult;
  }

    public org.cagrid.gaards.cds.stubs.ApproveDelegationResponse approveDelegation(org.cagrid.gaards.cds.stubs.ApproveDelegationRequest params) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.DelegationFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    org.cagrid.gaards.cds.stubs.ApproveDelegationResponse boxedResult = new org.cagrid.gaards.cds.stubs.ApproveDelegationResponse();
    boxedResult.setDelegatedCredentialReference(impl.approveDelegation(params.getDelegationSigningResponse().getDelegationSigningResponse()));
    return boxedResult;
  }

}
