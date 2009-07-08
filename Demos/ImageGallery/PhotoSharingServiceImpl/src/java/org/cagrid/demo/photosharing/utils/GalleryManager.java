package org.cagrid.demo.photosharing.utils;

import edu.internet2.middleware.grouper.GrantPrivilegeException;
import edu.internet2.middleware.grouper.GroupAddException;
import edu.internet2.middleware.grouper.InsufficientPrivilegeException;
import edu.internet2.middleware.grouper.MemberAddException;
import edu.internet2.middleware.grouper.SchemaException;
import edu.internet2.middleware.grouper.StemAddException;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouper;
import gov.nih.nci.cagrid.gridgrouper.grouper.GroupI;
import gov.nih.nci.cagrid.gridgrouper.grouper.StemI;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cagrid.demo.photosharing.utils.exceptions.AuthorizationException;
import org.cagrid.demo.photosharing.utils.exceptions.PhotoSharingException;

/**
 * This class creates new Galleries and performs the associated Grid Grouper management. The admin/owner of all
 * created entities is the same as the GlobusCredential used to create the GridGrouper instance.
 * @author jpermar
 *
 */
public class GalleryManager {

	public static final String VIEW_PERMISSION_GROUP_SYSTEM_NAME = "view";
	public static final String VIEW_PERMISSION_GROUP_DISPLAY_NAME = "View Photos";
	public static final String ADD_PERMISSION_GROUP_SYSTEM_NAME = "add";
	public static final String ADD_PERMISSION_GROUP_DISPLAY_NAME = "Add Photos";

	private StemI serviceStem;
	private GridGrouper grouper;
	private String grouperURL;

	/**
	 * Map gallery names to galleries
	 */
	private Map<String, GrouperGallery> galleryMap;
	private String adminIdentity;

	public GalleryManager(String adminIdentity, StemI serviceStem, GridGrouper grouper, String grouperURL) {
		//		this.galleryCreatorIdentity = galleryCreatorIdentity;
		this.serviceStem = serviceStem;
		this.grouper = grouper;
		this.grouperURL = grouperURL;
		this.adminIdentity = adminIdentity;
		this.galleryMap = new HashMap<String, GrouperGallery>();

	}

	public GrouperGallery createGallery(String galleryName, String galleryCreatorIdentity) throws AuthorizationException, PhotoSharingException  {

		//check if gallery already exists..
		GroupI viewPhotosGroup = null;
		GroupI addPhotosGroup = null;
		StemI galleryStem = null;
		galleryStem = GroupUtils.findChildStem(this.serviceStem, galleryName);
		if (galleryStem != null) {
			//try to locate child groups
			viewPhotosGroup = GroupUtils.findChildGroup(galleryStem, VIEW_PERMISSION_GROUP_SYSTEM_NAME);
			addPhotosGroup = GroupUtils.findChildGroup(galleryStem, ADD_PERMISSION_GROUP_SYSTEM_NAME);
		}

		if (galleryStem == null) {
			try {
			//create gallery stem
			galleryStem = this.serviceStem.addChildStem(GroupUtils.makeSystemName(galleryName), galleryName);
			//add create permission
			GroupUtils.addGroupPrivilege(adminIdentity, galleryStem);
			} catch (InsufficientPrivilegeException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new AuthorizationException(e.getMessage());
			} catch (StemAddException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new PhotoSharingException(e.getMessage());
			} catch (GrantPrivilegeException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new AuthorizationException(e.getMessage());
			} catch (SchemaException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new PhotoSharingException(e.getMessage());
			} catch (SubjectNotFoundException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new PhotoSharingException(e.getMessage());
			}
		}
		
		if (viewPhotosGroup == null) {
			try {
				viewPhotosGroup = GroupUtils.createGroup(galleryStem, VIEW_PERMISSION_GROUP_SYSTEM_NAME, VIEW_PERMISSION_GROUP_DISPLAY_NAME);
				//owner can always view and add photos
				GroupUtils.addGroupMember(viewPhotosGroup, galleryCreatorIdentity);
			} catch (InsufficientPrivilegeException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new AuthorizationException(e.getMessage());
			} catch (SubjectNotFoundException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new PhotoSharingException(e.getMessage());
			} catch (GroupAddException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new PhotoSharingException(e.getMessage());
			} catch (MemberAddException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new AuthorizationException(e.getMessage());
			}
		}
		
		if (addPhotosGroup == null) {
			try {
				addPhotosGroup = GroupUtils.createGroup(galleryStem, ADD_PERMISSION_GROUP_SYSTEM_NAME, ADD_PERMISSION_GROUP_DISPLAY_NAME);
				//owner can always view and add photos
				GroupUtils.addGroupMember(addPhotosGroup, galleryCreatorIdentity);
			} catch (InsufficientPrivilegeException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new AuthorizationException(e.getMessage());
			} catch (SubjectNotFoundException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new PhotoSharingException(e.getMessage());
			} catch (GroupAddException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new PhotoSharingException(e.getMessage());
			} catch (MemberAddException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new AuthorizationException(e.getMessage());
			}		}
		
		
		GalleryInfo info = new GalleryInfo(galleryCreatorIdentity, galleryStem, viewPhotosGroup, addPhotosGroup);

		GrouperGallery gallery = new GrouperGallery(this.grouperURL, this.grouper, info, galleryName);

		this.galleryMap.put(galleryName, gallery);
		return gallery;
	}


	public void deleteGallery(String callerIdentity, String galleryName) throws AuthorizationException, PhotoSharingException {
		this.galleryMap.get(galleryName).deleteGallery(callerIdentity);
		this.galleryMap.remove(galleryName);

	}

	public Collection<GrouperGallery> listGalleries() {
		return this.galleryMap.values();
	}


}
