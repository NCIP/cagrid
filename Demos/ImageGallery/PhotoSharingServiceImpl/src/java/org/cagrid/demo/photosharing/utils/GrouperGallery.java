package org.cagrid.demo.photosharing.utils;

import edu.internet2.middleware.grouper.GroupAddException;
import edu.internet2.middleware.grouper.GroupDeleteException;
import edu.internet2.middleware.grouper.InsufficientPrivilegeException;
import edu.internet2.middleware.grouper.StemDeleteException;
import gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier;
import gov.nih.nci.cagrid.gridgrouper.bean.LogicalOperator;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipExpression;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipQuery;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipStatus;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouper;
import gov.nih.nci.cagrid.gridgrouper.grouper.GroupI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cagrid.demo.photosharing.utils.exceptions.AuthorizationException;
import org.cagrid.demo.photosharing.utils.exceptions.PhotoSharingException;

public class GrouperGallery {

	private GridGrouper grouper;
	Map<Long, GrouperImage> facadesMap;
	String galleryName;
	private MembershipExpression addMembershipExpression;
	private MembershipExpression viewMembershipExpression;
	private GalleryInfo info;
	private int imageIdCounter = 0;
	private String grouperURL;

	public GrouperGallery(String grouperURL, GridGrouper grouper,
			GalleryInfo info, String galleryName) {
		this.grouper = grouper;
		this.galleryName = galleryName;
		this.info = info;
		this.grouperURL = grouperURL;
		this.addMembershipExpression = createMembershipExpression(grouperURL,
				info.getAddPhotosGroup());
		this.viewMembershipExpression = createMembershipExpression(grouperURL,
				info.getViewPhotosGroup());
		this.facadesMap = new HashMap<Long, GrouperImage>();
	}

	private MembershipExpression createMembershipExpression(String grouperURL,
			GroupI group) {
		GroupIdentifier groupIdentifier = new GroupIdentifier(grouperURL, group
				.getName());
		MembershipQuery[] query = new MembershipQuery[] { new MembershipQuery(
				groupIdentifier, MembershipStatus.MEMBER_OF) };
		MembershipExpression expression = new MembershipExpression(
				LogicalOperator.AND, new MembershipExpression[0], query);
		return expression;
	}

	private GroupI createImageGroup(ImageDescription description)
	throws GroupAddException, InsufficientPrivilegeException {
		GroupI group = this.info.getGalleryStem().addChildGroup(
				GroupUtils.makeSystemName(description.getName()),
				description.getName());
		return group;
	}

	public Image getEncodedImage(Long id, String callerIdentity)
	throws AuthorizationException, PhotoSharingException {
		if (GroupUtils.memberCheck(this.grouper, callerIdentity,
				this.viewMembershipExpression)) {
			GrouperImage imageFacade = facadesMap.get(id);
			if (imageFacade == null) {
				throw new PhotoSharingException("Could not find image with id: " + id);
			}
			return imageFacade.getImage(callerIdentity);

		} else {
			throw new AuthorizationException(callerIdentity
					+ " not authorized to view images");
		}
	}

	public ImageDescription addImage(String callerIdentity, String imageName,
			String imageDescription, String imageType, String encodedImage)
	throws AuthorizationException, PhotoSharingException {
		// Image image, MembershipExpression expression, GridGrouper grouper,
		// GroupI group) throws AuthorizationException {
		// check add permission
		if (GroupUtils.memberCheck(this.grouper, callerIdentity,
				this.addMembershipExpression)) {
			// create Image with unique identity
			ImageDescription description = new ImageDescription(Long
					.valueOf(this.imageIdCounter++), imageName,
					imageDescription, imageType);

			Image image = new Image(description, encodedImage);
			GroupI imageGroup = null;
			try {
				//check if image group already exists
				Set<GroupI> existingGroups = this.info.getGalleryStem().getChildGroups();
				for (GroupI group : existingGroups) {
					if (group.getExtension().equals(GroupUtils.makeSystemName(description.getName()))) {
						imageGroup = group;
					}
				}
				if (imageGroup == null) {
					imageGroup = createImageGroup(description);
				}
				GrouperImage imageFacade = new GrouperImage(image,
						grouperURL, grouper, imageGroup);
				this.facadesMap
				.put(image.getDescription().getId(), imageFacade);
				return description;
			} catch (GroupAddException e) {
				e.printStackTrace();
				throw new PhotoSharingException("Could not add user to group");
			} catch (InsufficientPrivilegeException e) {
				e.printStackTrace();
				throw new AuthorizationException(
						"Don't have sufficient privileges to add user to group");
			}

		} else {
			throw new AuthorizationException(callerIdentity
					+ " not authorized to add images");
		}
	}

	public Collection<ImageDescription> listImages(String callerIdentity)
	throws AuthorizationException {
		// check view permission
		if (GroupUtils.memberCheck(this.grouper, callerIdentity,
				this.viewMembershipExpression)) {
			Collection<GrouperImage> imageFacades = this.facadesMap
			.values();
			List<ImageDescription> descriptions = new ArrayList<ImageDescription>();
			for (GrouperImage imageFacade : imageFacades) {
				try {
					ImageDescription desc = imageFacade
					.getDescription(callerIdentity);
					descriptions.add(desc);
				} catch (AuthorizationException e) {
					// silently filter
				}
			}
			return descriptions;
		} else {
			throw new AuthorizationException(callerIdentity
					+ " not authorized to view images");
		}
	}

	public void grantRetrieveImagePrivileges(String callerIdentity, Long id, String identity)
	throws PhotoSharingException, AuthorizationException {
		if (!(callerIdentity.equals(this.info.getGalleryOwnerIdentity()))) {
			throw new AuthorizationException("Only the gallery owner can grant image retrieval privileges");
		}
		GrouperImage imageFacade = facadesMap.get(id);
		if (imageFacade != null) {
			try {
				imageFacade.addAllowedUser(identity);
			} catch (Exception e) {
				e.printStackTrace();
				throw new AuthorizationException("Could not add allowed user: " + e.getMessage());
			}
		} else {
			throw new PhotoSharingException("Could not find image with id "
					+ id);
		}
	}

	public void revokeRetrieveImagePrivileges(String callerIdentity, Long id, String identity) throws AuthorizationException, PhotoSharingException {
		if (!(callerIdentity.equals(this.info.getGalleryOwnerIdentity()))) {
			throw new AuthorizationException("Only the gallery owner can revoke image retrieval privileges");
		}
		GrouperImage imageFacade = facadesMap.get(id);
		if (imageFacade != null) {
			try {
				imageFacade.removeAllowedUser(identity);
			} catch (Exception e) {
				e.printStackTrace();
				throw new AuthorizationException("Could not remove allowed user: " + e.getMessage());
			}
		} else {
			throw new PhotoSharingException("Could not find image with id "
					+ id);
		}
	}

	public void deleteGallery(String callerIdentity) throws AuthorizationException, PhotoSharingException {
		if (!(callerIdentity.equals(this.info.getGalleryOwnerIdentity()))) {
			throw new AuthorizationException("Only the gallery owner can delete the gallery");
		}
		try {
			GroupUtils.deleteStemHierarchy(this.info.getGalleryStem());
			GroupUtils.deleteStem(this.info.getGalleryStem());
			this.facadesMap.clear();
		} catch (GroupDeleteException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new PhotoSharingException("Could not delete group: " + e.getMessage());
		} catch (InsufficientPrivilegeException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new PhotoSharingException("Could not delete group: " + e.getMessage());
		} catch (StemDeleteException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new PhotoSharingException("Could not delete group: " + e.getMessage());
		}
		
	}

	public void grantGalleryViewingPrivileges(String callerIdentity, String userIdentity) throws PhotoSharingException, AuthorizationException {
		if (!(callerIdentity.equals(this.info.getGalleryOwnerIdentity()))) {
			throw new AuthorizationException("Only the gallery owner can grant gallery viewing privileges");
		}
		try {
			if (!(GroupUtils.hasMember(this.info.getAddPhotosGroup(), userIdentity))) {
				GroupUtils.addGroupMember(this.info.getViewPhotosGroup(), userIdentity);
			}
		} catch (Exception e) {
			throw new PhotoSharingException("Could not add user as viewer: " + e.getMessage());
		}
	}

	public void revokeGalleryViewingPrivileges(String callerIdentity, String userIdentity) throws PhotoSharingException, AuthorizationException {
		if (!(callerIdentity.equals(this.info.getGalleryOwnerIdentity()))) {
			throw new AuthorizationException("Only the gallery owner can revoke gallery viewing privileges");
		}
		
		try {
			if (GroupUtils.hasMember(this.info.getAddPhotosGroup(), userIdentity)) {
			GroupUtils.removeGroupMember(this.info.getViewPhotosGroup(), userIdentity);
			}
		} catch (Exception e) {
			throw new PhotoSharingException("Could not remove user from viewers: " + e.getMessage());
		}
	}

	public void grantGalleryAddPrivileges(String callerIdentity, String userIdentity) throws PhotoSharingException, AuthorizationException {
		if (!(callerIdentity.equals(this.info.getGalleryOwnerIdentity()))) {
			throw new AuthorizationException("Only the gallery owner can grant image addition privileges");
		}
		try {
			//add only if user doesn't exist.
			if (!(GroupUtils.hasMember(this.info.getAddPhotosGroup(), userIdentity))) {
				GroupUtils.addGroupMember(this.info.getAddPhotosGroup(), userIdentity);
			}
		} catch (Exception e) {
			throw new PhotoSharingException("Could not add user to add images group: " + e.getMessage());
		}
	}

	public void revokeGalleryAddPrivileges(String callerIdentity, String userIdentity) throws PhotoSharingException, AuthorizationException {
		if (!(callerIdentity.equals(this.info.getGalleryOwnerIdentity()))) {
			throw new AuthorizationException("Only the gallery owner can revoke image addition privileges");
		}
		try {
			if (GroupUtils.hasMember(this.info.getAddPhotosGroup(), userIdentity)) {
				GroupUtils.removeGroupMember(this.info.getAddPhotosGroup(), userIdentity);
			}
		} catch (Exception e) {
			throw new PhotoSharingException("Could not remove user from add images group: " + e.getMessage());
		}
	}
	
	public String getGalleryName() {
		return this.galleryName;
	}

}
