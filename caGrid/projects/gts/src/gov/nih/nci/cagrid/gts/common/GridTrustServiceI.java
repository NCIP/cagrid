package gov.nih.nci.cagrid.gts.common;

import java.rmi.RemoteException;

/** 
 *   This class is autogenerated, DO NOT EDIT.
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public interface GridTrustServiceI {



































     public void removeTrustedAuthority(String trustedAuthorityName) throws RemoteException, gov.nih.nci.cagrid.gts.stubs.GTSInternalFault, gov.nih.nci.cagrid.gts.stubs.InvalidTrustedAuthorityFault ;
     public gov.nih.nci.cagrid.gts.bean.TrustedAuthority addTrustedAuthority(gov.nih.nci.cagrid.gts.bean.TrustedAuthority TrustedAuthority) throws RemoteException, gov.nih.nci.cagrid.gts.stubs.GTSInternalFault, gov.nih.nci.cagrid.gts.stubs.IllegalTrustedAuthorityFault, gov.nih.nci.cagrid.gts.stubs.PermissionDeniedFault ;
     public gov.nih.nci.cagrid.gts.bean.TrustedAuthority[] findTrustedAuthorities(gov.nih.nci.cagrid.gts.bean.TrustedAuthorityFilter TrustedAuthorityFilter) throws RemoteException, gov.nih.nci.cagrid.gts.stubs.GTSInternalFault ;
     public void addPermission(gov.nih.nci.cagrid.gts.bean.Permission Permission) throws RemoteException, gov.nih.nci.cagrid.gts.stubs.GTSInternalFault, gov.nih.nci.cagrid.gts.stubs.IllegalPermissionFault ;
     public gov.nih.nci.cagrid.gts.bean.Permission[] findPermissions(gov.nih.nci.cagrid.gts.bean.PermissionFilter PermissionFilter) throws RemoteException, gov.nih.nci.cagrid.gts.stubs.GTSInternalFault ;
     public void revokePermission(gov.nih.nci.cagrid.gts.bean.Permission Permission) throws RemoteException, gov.nih.nci.cagrid.gts.stubs.GTSInternalFault, gov.nih.nci.cagrid.gts.stubs.InvalidPermissionFault ;

}

