package org.cagrid.demo.photosharing.utils;

import edu.internet2.middleware.grouper.GrantPrivilegeException;
import edu.internet2.middleware.grouper.GroupAddException;
import edu.internet2.middleware.grouper.GroupDeleteException;
import edu.internet2.middleware.grouper.InsufficientPrivilegeException;
import edu.internet2.middleware.grouper.MemberAddException;
import edu.internet2.middleware.grouper.MemberDeleteException;
import edu.internet2.middleware.grouper.SchemaException;
import edu.internet2.middleware.grouper.StemAddException;
import edu.internet2.middleware.grouper.StemDeleteException;
import edu.internet2.middleware.grouper.StemNotFoundException;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouper;
import gov.nih.nci.cagrid.gridgrouper.grouper.GroupI;
import gov.nih.nci.cagrid.gridgrouper.grouper.StemI;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manage photo users. Note that the credential used to conect to grid grouper is used to create all stems (and is also admin
 * of all groups and stems that are created while managing this user)
 * @author jpermar
 *
 */
public class PhotoSharingUserManager {

	/**
	 * This is a map containing mappings from user identity to their Stem in grid grouper
	 */
	private Map<String, StemI> userStems;
	private GridGrouper grouper;
	private String photoSharingServiceStemSystemName;
	
	public PhotoSharingUserManager(GridGrouper grouper, String photoSharingServiceStemSystemName) {
		userStems = new HashMap<String, StemI>();
		this.grouper = grouper;
		this.photoSharingServiceStemSystemName = photoSharingServiceStemSystemName;
	}
	
	public void createUser(String userIdentity, String adminIdentity) throws StemNotFoundException, InsufficientPrivilegeException, StemAddException, SubjectNotFoundException, GrantPrivilegeException, SchemaException {
		String stemDisplayName = userIdentity + " Photos";
		String stemSystemName = GroupUtils.makeSystemName(stemDisplayName);
		StemI photoSharingStem = this.grouper.findStem(photoSharingServiceStemSystemName);
		//check that stem doesn't exist yet
		Set<StemI> existingStems = (Set<StemI>)photoSharingStem.getChildStems();
		StemI myStem = null;
		for (StemI stem : existingStems) {
			if (stem.getExtension().equals(stemSystemName)) {
				myStem = stem;
			}
		}
		
				
		if (myStem == null) {
			myStem = photoSharingStem.addChildStem(stemSystemName, stemDisplayName);
		}
//		GroupUtils.addStemPrivilege(adminIdentity, myStem);
		userStems.put(userIdentity, myStem);
	}
	
	public boolean userExists(String userIdentity) {
		StemI stem = userStems.get(userIdentity);
		return (!(stem == null));
	}
	
	public StemI getUserStem(String userIdentity) {
		return this.userStems.get(userIdentity);
	}
	
	public void deleteUser(String userIdentity) throws GroupDeleteException, InsufficientPrivilegeException, StemDeleteException {
		GroupUtils.deleteStemHierarchy(userStems.get(userIdentity));
		userStems.remove(userIdentity);
	}
	
	public void addSharingGroup(String userIdentity, String groupDisplayName) throws GroupAddException, InsufficientPrivilegeException {
		String systemName = GroupUtils.makeSystemName(groupDisplayName);
		StemI userStem = userStems.get(userIdentity);
		//multiple groups with same name can't exist because we have only one
		//heuristic for converting from display name to systemname... and grid
		//grouper doesn't allow multiple groups with same system name
		GroupUtils.createGroup(userStem, systemName, groupDisplayName);
		//group admin is set to default as the same owner/admin of the user stem
	}
	
	public void deleteSharingGroup(String userIdentity, String groupDisplayName) throws GroupDeleteException, InsufficientPrivilegeException {
		StemI userStem = userStems.get(userIdentity);
		Set<GroupI> groups = userStem.getChildGroups();
		//NOTE: theoretically can delete multiple matching groups with this loop...
		//group creation ensures that multiple groups with same name don't exist tho
		for (GroupI group : groups) {
			//find matching group
			if (group.getDisplayName().equals(groupDisplayName)) {
				GroupUtils.deleteGroup(group);
			}
		}
	}
	
	/**
	 * 
	 * @param friendIdentity the identity of the person to add to the group
	 * @param userIdentity the photo sharing user that is requesting the group add
	 * @param groupDisplayName the group display name of the group to add friendIdentity to
	 * @throws MemberAddException 
	 * @throws InsufficientPrivilegeException 
	 * @throws SubjectNotFoundException 
	 */
	public void addFriendToSharingGroup(String friendIdentity, String userIdentity, String groupDisplayName) throws SubjectNotFoundException, InsufficientPrivilegeException, MemberAddException {
		StemI userStem = userStems.get(userIdentity);
		Set<GroupI> groups = userStem.getChildGroups();
		//NOTE: theoretically can delete multiple matching groups with this loop...
		//group creation ensures that multiple groups with same name don't exist tho
		for (GroupI group : groups) {
			//find matching group
			if (group.getDisplayName().equals(groupDisplayName)) {
				GroupUtils.addGroupMember(group, friendIdentity);
			}
		}
	}
	
	public void removeFriendFromSharingGroup(String friendIdentity, String userIdentity, String groupDisplayName) throws SubjectNotFoundException, InsufficientPrivilegeException, MemberDeleteException {
		StemI userStem = userStems.get(userIdentity);
		Set<GroupI> groups = userStem.getChildGroups();
		//NOTE: theoretically can delete multiple matching groups with this loop...
		//group creation ensures that multiple groups with same name don't exist tho
		for (GroupI group : groups) {
			//find matching group
			if (group.getDisplayName().equals(groupDisplayName)) {
				GroupUtils.removeGroupMember(group, friendIdentity);
			}
		}
	}
	
}
