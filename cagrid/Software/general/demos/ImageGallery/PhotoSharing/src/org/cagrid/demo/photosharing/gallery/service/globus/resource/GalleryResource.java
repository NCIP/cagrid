package org.cagrid.demo.photosharing.gallery.service.globus.resource;

import org.cagrid.demo.photosharing.service.globus.resource.PhotoSharingResource;
import org.cagrid.demo.photosharing.service.globus.resource.PhotoSharingResourceHome;
import org.cagrid.demo.photosharing.utils.GrouperGallery;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceKey;


/** 
 * The implementation of this GalleryResource type.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class GalleryResource extends GalleryResourceBase {

	private GrouperGallery gallery;

	public void setGallery(GrouperGallery gallery) {
		this.gallery = gallery;
	}

	public GrouperGallery getGallery() {
		return this.gallery;
	}


	@Override
	public void remove() throws ResourceException {
		//this is called when the gallery is deleted. we need to clean up grid grouper and also
		//remove this gallery from the PhotoSharing service list
		try {
			//delete the gallery (which cleans up grid grouper)
			String userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();

			this.gallery.deleteGallery(userDN);

			//delete the gallery from the photo sharing service context galleryList
			org.apache.axis.MessageContext ctx = org.apache.axis.MessageContext.getCurrentContext();
	        String servicePath = ctx.getTargetService();String homeName = org.globus.wsrf.Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/" + "photoSharingHome";
			javax.naming.Context initialContext = new javax.naming.InitialContext();
			PhotoSharingResourceHome home = (PhotoSharingResourceHome) initialContext.lookup(homeName);

			PhotoSharingResource photoSharingResource = (PhotoSharingResource)home.find(null);

			photoSharingResource.removeGallery(this.gallery.getGalleryName());

		} catch(Exception e) {
			ResourceException re = new ResourceException(e.getMessage(), e);
			throw re;
		}
	}

}
