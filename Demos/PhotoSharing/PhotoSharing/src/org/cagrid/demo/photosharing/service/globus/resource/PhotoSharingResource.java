package org.cagrid.demo.photosharing.service.globus.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.cagrid.demo.photosharing.gallery.stubs.types.GalleryReference;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceKey;


/** 
 * The implementation of this PhotoSharingResource type.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class PhotoSharingResource extends PhotoSharingResourceBase {

	/**
	 * Map of galleryName (String) to the GalleryReference
	 */
	private Map<String, GalleryReference> galleryMap;
	
	public PhotoSharingResource() {
		this.galleryMap = new ConcurrentHashMap<String, GalleryReference>();
	}

	/*
	 * Implementing our method like this solves two problems.
	 * 1) If we just return this.galleryMap.values(), then someone could
	 * (erroneously) try to add something to the Collection and get an
	 * error (can't add a value directly to the values list of a Map.. no key)
	 * 2) If we return this.galleryMap.values.toArray() when the map has
	 * nothing in it, null is returned. What we want is an empty array returned
	 * instead. We fix that problem by creating a separate List and adding
	 * references to it as we find them in the map
	 */
	public GalleryReference[] listGalleries() {
		Collection<GalleryReference> mapGalleries = this.galleryMap.values();
		List<GalleryReference> galleryRefList = new ArrayList<GalleryReference>();
		for (GalleryReference ref : mapGalleries) {
			galleryRefList.add(ref);
		}
		return galleryRefList.toArray(new GalleryReference[0]);
	}
	
	public void addGallery(String galleryName, GalleryReference gallery) {
		this.galleryMap.put(galleryName, gallery);
	}
	
	public void removeGallery(String galleryName) {
		this.galleryMap.remove(galleryName);
	}

}
