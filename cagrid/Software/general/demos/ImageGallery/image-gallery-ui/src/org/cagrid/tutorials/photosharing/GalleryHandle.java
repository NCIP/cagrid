package org.cagrid.tutorials.photosharing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.cagrid.demo.photosharing.domain.ImageDescription;
import org.cagrid.demo.photosharing.gallery.client.GalleryClient;
import org.cagrid.demo.photosharing.utils.ImageUtils;
import org.castor.util.Base64Encoder;


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


    public void deleteGallery() throws Exception {
        getClient().destroy();
    }


    public void addPhoto(String imageName, String imageDescription, File f) throws Exception {

        byte[] imageBytes = ImageUtils.loadImageAsBytes(f.getAbsolutePath());
        // Note: only the castor Base64Encoder encodes
        // properly... the Sun one doesn't (corrupts image)
        String encoded = new String(Base64Encoder.encode(imageBytes));

        org.cagrid.demo.photosharing.domain.ImageDescription beanDesc = new org.cagrid.demo.photosharing.domain.ImageDescription();
        beanDesc.setId(Long.valueOf(0)); // doesn't matter what
        // this is set to
        beanDesc.setDescription(imageDescription);
        beanDesc.setName(imageName);
        beanDesc.setType(ImageFileFilter.getExtension(f));

        org.cagrid.demo.photosharing.domain.Image beanImage = new org.cagrid.demo.photosharing.domain.Image();
        beanImage.setId(Long.valueOf(0)); // doesn't matter what
        // this is set to

        beanImage.setImageDescription(beanDesc);
        beanImage.setData(encoded);
        getClient().addImage(beanImage);

    }


    public GalleryClient getClient() {
        return client;
    }

}
