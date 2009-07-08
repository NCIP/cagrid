package org.cagrid.demo.photosharing.utils;

import edu.internet2.middleware.grouper.AccessPrivilege;
import edu.internet2.middleware.grouper.GrantPrivilegeException;
import edu.internet2.middleware.grouper.GroupAddException;
import edu.internet2.middleware.grouper.GroupDeleteException;
import edu.internet2.middleware.grouper.GroupNotFoundException;
import edu.internet2.middleware.grouper.InsufficientPrivilegeException;
import edu.internet2.middleware.grouper.Member;
import edu.internet2.middleware.grouper.MemberAddException;
import edu.internet2.middleware.grouper.MemberDeleteException;
import edu.internet2.middleware.grouper.SchemaException;
import edu.internet2.middleware.grouper.StemAddException;
import edu.internet2.middleware.grouper.StemDeleteException;
import edu.internet2.middleware.grouper.StemNotFoundException;
import edu.internet2.middleware.subject.Subject;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipExpression;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouper;
import gov.nih.nci.cagrid.gridgrouper.common.SubjectUtils;
import gov.nih.nci.cagrid.gridgrouper.grouper.GroupI;
import gov.nih.nci.cagrid.gridgrouper.grouper.StemI;

import java.util.Set;

public class GroupUtils {

	/**
	 * Return a stem with matching display extension, or null if that stem doesn't exist
	 * @return
	 */
	public static StemI findChildStem(StemI parentStem, String displayExtension) {
		Set<StemI> existingStems = parentStem.getChildStems();
		for (StemI stem : existingStems) {
			if (stem.getDisplayExtension().equals(displayExtension)) {
				return stem;
			}
		}
		return null;
	}
	
	/**
	 * Return a stem with matching group (sytem) extension, or null if that group doesn't exist
	 * @param groupExtension the extension of the group to find
	 * @return
	 */
	public static GroupI findChildGroup(StemI stem, String groupExtension) {
		Set<GroupI> groups = stem.getChildGroups();
		for (GroupI group : groups) {
			if (group.getExtension().equals(groupExtension)) {
				return group;
			}
		}
		return null;
	}

	public static String makeSystemName(String displayName) {
		//remove whitespace and make all lowercase
		return displayName.toLowerCase().replaceAll("\\s+", "");
	}

	public static boolean isMember(String identity, GroupI group) throws SubjectNotFoundException {
		Subject subject = SubjectUtils.getSubject(identity);
		return group.hasMember(subject);
	}

	public static boolean memberCheck(GridGrouper grouper, String caller, MembershipExpression expression) {
		return grouper.isMember(caller, expression);
	}

	public static boolean isEmptyGroup(GroupI group) {
		Set<Member> members = group.getMembers();
		return members.isEmpty();
	}
	public static GroupI createGroup(StemI parent, String systemName, String displayName) throws GroupAddException, InsufficientPrivilegeException {
		return parent.addChildGroup(systemName, displayName);
	}

	public static void deleteGroup(GroupI group) throws GroupDeleteException, InsufficientPrivilegeException {
		group.delete();
	}

	public static void addStem(StemI parent, String systemName, String displayName) throws InsufficientPrivilegeException, StemAddException {
		parent.addChildStem(systemName, displayName);
	}

	public static void deleteStem(StemI stem) throws InsufficientPrivilegeException, StemDeleteException {
		stem.delete();
	}

	public static void addGroupMember(GroupI group, String identity) throws SubjectNotFoundException, InsufficientPrivilegeException, MemberAddException {
		Subject subject = SubjectUtils.getSubject(identity);
		group.addMember(subject);
	}

	public static void removeGroupMember(GroupI group, String identity) throws SubjectNotFoundException, InsufficientPrivilegeException, MemberDeleteException {
		Subject subject = SubjectUtils.getSubject(identity);
		group.deleteMember(subject);
	}

	/**
	 * WARNING: this will remove all sub-groups and sub-stems recursively. The stem itself is left unchanged.
	 * @param stem
	 * @throws InsufficientPrivilegeException 
	 * @throws GroupDeleteException 
	 * @throws StemDeleteException 
	 */
	public static void deleteStemHierarchy(StemI curStem) throws GroupDeleteException, InsufficientPrivilegeException, StemDeleteException {
		Set<StemI> subStems = (Set<StemI>) curStem.getChildStems();
		for (StemI stem: subStems) {
			deleteStemHierarchy(stem);
			stem.delete();
		}
		Set<GroupI> groups = (Set<GroupI>) curStem.getChildGroups();
		for (GroupI group : groups) {
			deleteGroup(group);
		}
	}

	public static GroupI findGroup(GridGrouper grouper, String systemName) throws GroupNotFoundException {
		return grouper.findGroup(systemName);
	}

	public static StemI findStem(GridGrouper grouper, String systemName) throws StemNotFoundException {
		return grouper.findStem(systemName);
	}

	public static void addStemPrivilege(String identity, StemI stem) throws SubjectNotFoundException, GrantPrivilegeException, InsufficientPrivilegeException, SchemaException {
		Subject subject = SubjectUtils.getSubject(identity);
		stem.grantPriv(subject, edu.internet2.middleware.grouper.NamingPrivilege.STEM);
	}

	public static void addGroupPrivilege(String identity, StemI stem) throws GrantPrivilegeException, InsufficientPrivilegeException, SchemaException, SubjectNotFoundException {
		Subject subject = SubjectUtils.getSubject(identity);
		stem.grantPriv(subject, edu.internet2.middleware.grouper.NamingPrivilege.CREATE);
	}

	public static void addGroupAdmin(GroupI group, Subject subject) throws GrantPrivilegeException, InsufficientPrivilegeException, SchemaException {
		group.grantPriv(subject, AccessPrivilege.ADMIN);
		group.grantPriv(subject, AccessPrivilege.VIEW);
		group.grantPriv(subject, AccessPrivilege.READ);
		group.grantPriv(subject, AccessPrivilege.UPDATE);
	}

	public static boolean subgroupExists(StemI stem, String systemNameOfGroup) {
		Set<GroupI> existingGroups = stem.getChildGroups();
		for (GroupI group : existingGroups) {
			if (group.getExtension().equals(systemNameOfGroup)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasMember(GroupI group, String identity) throws SubjectNotFoundException {
		Subject subj = SubjectUtils.getSubject(identity);
		return group.hasMember(subj);
	}

}
