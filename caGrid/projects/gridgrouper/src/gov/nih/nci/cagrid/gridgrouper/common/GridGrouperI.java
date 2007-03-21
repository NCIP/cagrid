package gov.nih.nci.cagrid.gridgrouper.common;

import java.rmi.RemoteException;


/**
 * This class is autogenerated, DO NOT EDIT.
 * 
 * @created by Introduce Toolkit version 1.0
 */
public interface GridGrouperI {

	public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata()
		throws RemoteException;


	public gov.nih.nci.cagrid.gridgrouper.bean.StemDescriptor getStem(
		gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.StemDescriptor[] getChildStems(
		gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier parentStem) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.StemDescriptor getParentStem(
		gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier childStem) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.StemDescriptor updateStem(
		gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem, gov.nih.nci.cagrid.gridgrouper.bean.StemUpdate update)
		throws RemoteException, gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemModifyFault;


	public java.lang.String[] getSubjectsWithStemPrivilege(gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem,
		gov.nih.nci.cagrid.gridgrouper.bean.StemPrivilegeType privilege) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.StemPrivilege[] getStemPrivileges(
		gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem, java.lang.String subject) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;


	public boolean hasStemPrivilege(gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem, java.lang.String subject,
		gov.nih.nci.cagrid.gridgrouper.bean.StemPrivilegeType privilege) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;


	public void grantStemPrivilege(gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem, java.lang.String subject,
		gov.nih.nci.cagrid.gridgrouper.bean.StemPrivilegeType privilege) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GrantPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.SchemaFault;


	public void revokeStemPrivilege(gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem, java.lang.String subject,
		gov.nih.nci.cagrid.gridgrouper.bean.StemPrivilegeType privilege) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.RevokePrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.SchemaFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.StemDescriptor addChildStem(
		gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem, java.lang.String extension,
		java.lang.String displayExtension) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemAddFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;


	public void deleteStem(gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemDeleteFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.GroupDescriptor getGroup(
		gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.GroupDescriptor[] getChildGroups(
		gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.GroupDescriptor addChildGroup(
		gov.nih.nci.cagrid.gridgrouper.bean.StemIdentifier stem, java.lang.String extension,
		java.lang.String displayExtension) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupAddFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault;


	public void deleteGroup(gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupDeleteFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.GroupDescriptor updateGroup(
		gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group,
		gov.nih.nci.cagrid.gridgrouper.bean.GroupUpdate update) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupModifyFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault;


	public void addMember(gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group, java.lang.String subject)
		throws RemoteException, gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.MemberAddFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.MemberDescriptor[] getMembers(
		gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group,
		gov.nih.nci.cagrid.gridgrouper.bean.MemberFilter filter) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault;


	public boolean isMemberOf(gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group, java.lang.String member,
		gov.nih.nci.cagrid.gridgrouper.bean.MemberFilter filter) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.MembershipDescriptor[] getMemberships(
		gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group,
		gov.nih.nci.cagrid.gridgrouper.bean.MemberFilter filter) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault;


	public void deleteMember(gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group, java.lang.String member)
		throws RemoteException, gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.MemberDeleteFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.GroupDescriptor addCompositeMember(
		gov.nih.nci.cagrid.gridgrouper.bean.GroupCompositeType type,
		gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier composite,
		gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier left,
		gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier right) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.MemberAddFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.GroupDescriptor deleteCompositeMember(
		gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.MemberDeleteFault;


	public void grantGroupPrivilege(gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group,
		java.lang.String subject, gov.nih.nci.cagrid.gridgrouper.bean.GroupPrivilegeType privilege)
		throws RemoteException, gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GrantPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault;


	public void revokeGroupPrivilege(gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group,
		java.lang.String subject, gov.nih.nci.cagrid.gridgrouper.bean.GroupPrivilegeType privilege)
		throws RemoteException, gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.RevokePrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.SchemaFault;


	public java.lang.String[] getSubjectsWithGroupPrivilege(gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group,
		gov.nih.nci.cagrid.gridgrouper.bean.GroupPrivilegeType privilege) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault;


	public gov.nih.nci.cagrid.gridgrouper.bean.GroupPrivilege[] getGroupPrivileges(
		gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group, java.lang.String subject) throws RemoteException,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault;


	public boolean hasGroupPrivilege(gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier group,
		java.lang.String subject, gov.nih.nci.cagrid.gridgrouper.bean.GroupPrivilegeType privilege)
		throws RemoteException, gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault,
		gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault;


	public boolean isMember(java.lang.String member, gov.nih.nci.cagrid.gridgrouper.bean.MembershipExpression expression)
		throws RemoteException, gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault;

}
