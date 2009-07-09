package org.cagrid.tutorials.photosharing;

import gov.nih.nci.cagrid.common.security.ProxyUtil;

import org.cagrid.demo.photosharing.gallery.client.GalleryClient;
import org.globus.gsi.GlobusCredential;


public class GalleryHandle {

    private GalleryClient client;
    private String name;


    public GalleryHandle(GalleryClient client) throws Exception {
        this.client = client;
        name = client.getGalleryName();
    }


    public String getName() {
        return name;
    }

}
