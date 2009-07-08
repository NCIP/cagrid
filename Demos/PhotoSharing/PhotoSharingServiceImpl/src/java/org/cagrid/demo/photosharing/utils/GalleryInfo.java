/**
 * 
 */
package org.cagrid.demo.photosharing.utils;

import gov.nih.nci.cagrid.gridgrouper.grouper.GroupI;
import gov.nih.nci.cagrid.gridgrouper.grouper.StemI;

class GalleryInfo {
	
	public StemI getGalleryStem() {
		return galleryStem;
	}

	public GroupI getViewPhotosGroup() {
		return viewPhotosGroup;
	}

	public GroupI getAddPhotosGroup() {
		return addPhotosGroup;
	}

	private StemI galleryStem;
	
	private GroupI viewPhotosGroup;
	
	private GroupI addPhotosGroup;

	private String galleryOwnerIdentity;
	
	public String getGalleryOwnerIdentity() {
		return galleryOwnerIdentity;
	}

	public GalleryInfo(String galleryOwnerIdentity, StemI galleryStem, GroupI viewPhotosGroup, GroupI addPhotosGroup) {
		this.galleryOwnerIdentity = galleryOwnerIdentity;
		this.galleryStem = galleryStem;
		this.viewPhotosGroup = viewPhotosGroup;
		this.addPhotosGroup = addPhotosGroup;
		
	}
}