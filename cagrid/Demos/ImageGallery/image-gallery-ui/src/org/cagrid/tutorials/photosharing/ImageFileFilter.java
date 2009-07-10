package org.cagrid.tutorials.photosharing;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class ImageFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return false;
        } else {

            String ext = getExtension(f);
            if (ext == null) {
                return false;
            } else {
                if (ext.equalsIgnoreCase("JPG") || ext.equalsIgnoreCase("GIF") || ext.equalsIgnoreCase("PNG")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }


 


    public static String getExtension(File f) {
        String name = f.getName();

        int index = name.lastIndexOf(".");
        if (index == -1) {
            return null;
        } else {
            return name.substring(index + 1);
        }

    }


    @Override
    public String getDescription() {
        return "Allows JPG, GIF and PNG images.";
    }

}
