package org.cagrid.demo.photosharing.gallery.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.cagrid.demo.photosharing.gallery.service.globus.resource.GalleryResource;
import org.cagrid.demo.photosharing.utils.Image;
import org.cagrid.demo.photosharing.utils.ImageDescription;
import org.cagrid.demo.photosharing.utils.exceptions.AuthorizationException;
import org.cagrid.demo.photosharing.utils.exceptions.PhotoSharingException;
import org.oasis.wsrf.faults.BaseFaultTypeDescription;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class GalleryImpl extends GalleryImplBase {

	
	public GalleryImpl() throws RemoteException {
		super();
	}
	
  public org.cagrid.demo.photosharing.domain.ImageDescription addImage(org.cagrid.demo.photosharing.domain.Image image) throws RemoteException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException, org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException {
		GalleryResource resource = null;
		try {
			resource = getResourceHome().getAddressedResource();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		String userDN = null;
		try {
			userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		String imageName = image.getImageDescription().getName();
		String imageDescription = image.getImageDescription().getDescription();
		String imageType = image.getImageDescription().getType();
		String imageData = image.getData();
		ImageDescription desc;
		try {
			desc = resource.getGallery().addImage(userDN, imageName, imageDescription, imageType, imageData);
		} catch (AuthorizationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException ae = new org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			ae.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw ae;
		} catch (PhotoSharingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		org.cagrid.demo.photosharing.domain.ImageDescription beanDescription = new org.cagrid.demo.photosharing.domain.ImageDescription();
		beanDescription.setDescription(desc.getDescription());
		beanDescription.setId(desc.getId());
		beanDescription.setName(desc.getName());
		beanDescription.setType(desc.getType());

		return beanDescription;
  }

  public org.cagrid.demo.photosharing.domain.ImageDescription[] listImages() throws RemoteException, org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException {
		GalleryResource resource = null;
		try {
			resource = getResourceHome().getAddressedResource();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		String userDN = null;
		try {
			userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}
		Collection<ImageDescription> images;
		try {
			images = resource.getGallery().listImages(userDN);
		} catch (AuthorizationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException ae = new org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			ae.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw ae;
		}

		//convert to beans
		Collection<org.cagrid.demo.photosharing.domain.ImageDescription> beanDescriptions = new ArrayList<org.cagrid.demo.photosharing.domain.ImageDescription>();
		for (ImageDescription desc : images) {
			//create ImageDescription bean
			org.cagrid.demo.photosharing.domain.ImageDescription beanDescription = new org.cagrid.demo.photosharing.domain.ImageDescription();
			beanDescription.setDescription(desc.getDescription());
			beanDescription.setId(desc.getId());
			beanDescription.setName(desc.getName());
			beanDescription.setType(desc.getType());
			beanDescriptions.add(beanDescription);
		}
		return beanDescriptions.toArray(new org.cagrid.demo.photosharing.domain.ImageDescription[0]);
  }

  public org.cagrid.demo.photosharing.domain.Image getImage(org.cagrid.demo.photosharing.domain.ImageDescription imageDescription) throws RemoteException, org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		GalleryResource resource = null;
		String userDN = null;
		try {
			resource = getResourceHome().getAddressedResource();
			userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		Image image = null;
		try {
			image = resource.getGallery().getEncodedImage(imageDescription.getId(), userDN);
		} catch(PhotoSharingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		} catch(AuthorizationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		//convert to beans

		//create ImageDescription bean
		org.cagrid.demo.photosharing.domain.ImageDescription beanDescription = new org.cagrid.demo.photosharing.domain.ImageDescription();
		beanDescription.setDescription(image.getDescription().getDescription());
		beanDescription.setId(image.getDescription().getId());
		beanDescription.setName(image.getDescription().getName());
		beanDescription.setType(image.getDescription().getType());

		//set description
		org.cagrid.demo.photosharing.domain.Image beanImage = new org.cagrid.demo.photosharing.domain.Image();
		beanImage.setData(image.getImageData());
		beanImage.setId(image.getDescription().getId());

		beanImage.setImageDescription(imageDescription);
		return beanImage;
  }

  public void grantViewGalleryPrivileges(org.cagrid.demo.photosharing.domain.User user) throws RemoteException, org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		GalleryResource resource = null;
		try {
			resource = getResourceHome().getAddressedResource();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		String userDN = null;
		try {
			userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}
		try {
			resource.getGallery().grantGalleryViewingPrivileges(userDN, user.getUserIdentity());
		} catch (PhotoSharingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		} catch (AuthorizationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException ae = new org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			ae.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw ae;
		}
  }

  public void revokeViewGalleryPrivileges(org.cagrid.demo.photosharing.domain.User user) throws RemoteException, org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		GalleryResource resource = null;
		try {
			resource = getResourceHome().getAddressedResource();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		String userDN = null;
		try {
			userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}
		try {
			resource.getGallery().revokeGalleryViewingPrivileges(userDN, user.getUserIdentity());
		} catch (PhotoSharingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		} catch (AuthorizationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException ae = new org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			ae.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw ae;
		}
  }

  public void grantAddImagePrivileges(org.cagrid.demo.photosharing.domain.User user) throws RemoteException, org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		GalleryResource resource = null;
		try {
			resource = getResourceHome().getAddressedResource();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		String userDN = null;
		try {
			userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}
		try {
			resource.getGallery().grantGalleryAddPrivileges(userDN, user.getUserIdentity());
		} catch (PhotoSharingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		} catch (AuthorizationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException ae = new org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			ae.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw ae;
		}
  }

  public void revokeAddImagePrivileges(org.cagrid.demo.photosharing.domain.User user) throws RemoteException, org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		GalleryResource resource = null;
		try {
			resource = getResourceHome().getAddressedResource();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		String userDN = null;
		try {
			userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}
		try {
			resource.getGallery().revokeGalleryAddPrivileges(userDN, user.getUserIdentity());
		} catch (PhotoSharingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		} catch (AuthorizationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException ae = new org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			ae.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw ae;
		}
  }

  public void grantImageRetrievalPrivileges(org.cagrid.demo.photosharing.domain.ImageDescription imageDescription,org.cagrid.demo.photosharing.domain.User user) throws RemoteException, org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		GalleryResource resource = null;
		try {
			resource = getResourceHome().getAddressedResource();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		String userDN = null;
		try {
			userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}
		try {
			resource.getGallery().grantRetrieveImagePrivileges(userDN, imageDescription.getId(), user.getUserIdentity());
		} catch (PhotoSharingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		} catch (AuthorizationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException ae = new org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			ae.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw ae;
		}
  }

  public void revokeImageRetrievalPrivileges(org.cagrid.demo.photosharing.domain.ImageDescription imageDescription,org.cagrid.demo.photosharing.domain.User user) throws RemoteException, org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		GalleryResource resource = null;
		try {
			resource = getResourceHome().getAddressedResource();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}

		String userDN = null;
		try {
			userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}
		try {
			resource.getGallery().revokeRetrieveImagePrivileges(userDN, imageDescription.getId(), user.getUserIdentity());
		} catch (AuthorizationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException ae = new org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			ae.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw ae;
		} catch (PhotoSharingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}
  }

  public java.lang.String getGalleryName() throws RemoteException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		GalleryResource resource = null;
		try {
			resource = getResourceHome().getAddressedResource();
			return resource.getGallery().getGalleryName();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demo.photosharing.stubs.types.PhotoSharingException pse = new org.cagrid.demo.photosharing.stubs.types.PhotoSharingException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			pse.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw pse;
		}
  }

}

