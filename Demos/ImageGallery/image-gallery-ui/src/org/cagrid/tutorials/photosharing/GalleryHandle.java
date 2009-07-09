package org.cagrid.tutorials.photosharing;

import java.util.ArrayList;
import java.util.List;

import org.cagrid.demo.photosharing.domain.ImageDescription;
import org.cagrid.demo.photosharing.gallery.client.GalleryClient;


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


    public List<PhotoHandle> getPhotos() throws Exception {
        List<PhotoHandle> photos = new ArrayList<PhotoHandle>();
        ImageDescription[] images = this.client.listImages();
        if (images != null) {
            for (int i = 0; i < images.length; i++) {
                photos.add(new PhotoHandle(this, images[i]));
            }
        }
        return photos;
    }


    public GalleryClient getClient() {
        return client;
    }

}
