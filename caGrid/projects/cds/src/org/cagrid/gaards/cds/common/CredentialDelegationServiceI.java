package org.cagrid.gaards.cds.common;

import java.rmi.RemoteException;

import org.cagrid.gaards.cds.stubs.types.CDSInternalFault;
import org.cagrid.gaards.cds.stubs.types.DelegationFault;
import org.cagrid.gaards.cds.stubs.types.InvalidPolicyFault;
import org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public interface CredentialDelegationServiceI {

  /**
   * This method allows an entity to find delegation records meeting a specified search criteria.
   *
   * @param filter
   * @throws CDSInternalFault
   *	
   */
  public org.cagrid.gaards.cds.common.DelegationRecord[] findMyDelegationRecords(org.cagrid.gaards.cds.common.DelegationRecordFilter filter) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault ;

  /**
   * Allows a party to initate the delegation of their credential such that other parties may access their credential to act on their behalf.
   *
   * @param req
   * @throws CDSInternalFault
   *	
   * @throws InvalidPolicyFault
   *	
   * @throws DelegationFault
   *	
   * @throws PermissionDeniedFault
   *	
   */
  public org.cagrid.gaards.cds.common.DelegationSigningRequest initiateDelegation(org.cagrid.gaards.cds.common.DelegationRequest req) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.InvalidPolicyFault, org.cagrid.gaards.cds.stubs.types.DelegationFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault ;

  /**
   * Allows the party whom initiated the delegation to approve the delegation.
   *
   * @param delegationSigningResponse
   * @throws CDSInternalFault
   *	
   * @throws DelegationFault
   *	
   * @throws PermissionDeniedFault
   *	
   */
  public org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference approveDelegation(org.cagrid.gaards.cds.common.DelegationSigningResponse delegationSigningResponse) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.DelegationFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault ;

}

