package org.cagrid.tutorials.photosharing;

import javax.swing.JButton;


public class Thumbnail extends JButton {
    private static final long serialVersionUID = 1L;
    
    private PhotoHandle handle;


    public Thumbnail(PhotoHandle handle) {
        this.handle = handle;
    }


    public PhotoHandle getPhoto() {
        return handle;
    }

}
