package org.cagrid.tutorials.photosharing;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import org.cagrid.demo.photosharing.domain.ImageDescription;
import org.castor.util.Base64Decoder;


public class PhotoHandle {

    public static final int THUMBNAIL_WIDTH = 128;
    public static final int THUMBNAIL_HEIGHT = 128;

    private GalleryHandle gallery;
    private ImageDescription des;
    private ImageIcon photo;
    private ImageIcon thumbnail;


    public PhotoHandle(GalleryHandle gallery, ImageDescription des) {
        this.gallery = gallery;
        this.des = des;
    }


    public String getName() {
        return des.getName();
    }


    public String getDescription() {
        return des.getDescription();
    }


    public String getType() {
        return des.getType();
    }


    public ImageIcon getPhoto() throws Exception {
        if (photo == null) {
            org.cagrid.demo.photosharing.domain.Image image = gallery.getClient().getImage(des);
            byte[] imageData = Base64Decoder.decode(image.getData());
            photo = new ImageIcon(Toolkit.getDefaultToolkit().createImage(imageData));
        }
        return photo;
    }


    public String getGalleryName() {
        return this.gallery.getName();
    }


    public ImageIcon getThumbnail() throws Exception {
        if (thumbnail == null) {
            int widthConstraint = THUMBNAIL_WIDTH;
            int heightConstraint = THUMBNAIL_HEIGHT;
            ImageIcon fromStream = getPhoto();
            int imgWidth = fromStream.getIconWidth();
            int imgHeight = fromStream.getIconHeight();

            if (imgWidth > widthConstraint | imgHeight > heightConstraint) {
                if (imgWidth > imgHeight) {
                    // Create a resizing ratio.
                    double ratio = (double) imgWidth / (double) widthConstraint;
                    int newHeight = (int) ((double) imgHeight / ratio);

                    // use Image.getScaledInstance( w, h,
                    // constant), where constant is a constant
                    // pulled from the Image class indicating how
                    // process the image; smooth image, fast
                    // processing, etc.
                    thumbnail = new ImageIcon(fromStream.getImage().getScaledInstance(widthConstraint, newHeight,
                        Image.SCALE_SMOOTH));
                } else {
                    // Create a resizing ratio.
                    double ratio = (double) imgHeight / (double) heightConstraint;
                    int newWidth = (int) ((double) imgWidth / ratio);
                    thumbnail = new ImageIcon(fromStream.getImage().getScaledInstance(newWidth, heightConstraint,
                        Image.SCALE_SMOOTH));
                }
            } else {
                // Assure the resources from the adjustedImg object
                // are released and then return the original ImageIcon
                // object if the submitted image's width and height
                // already fell within the given constraints.

                thumbnail = fromStream;
            }
        }
        return thumbnail;
    }
}
