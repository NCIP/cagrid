package org.cagrid.tutorials.photosharing.tree;

import javax.swing.ImageIcon;

import org.cagrid.gaards.ui.gridgrouper.GridGrouperLookAndFeel;
import org.cagrid.grape.utils.ErrorDialog;
import org.cagrid.tutorials.photosharing.GalleryHandle;
import org.cagrid.tutorials.photosharing.GalleryLookAndFeel;


public class GalleryTreeNode extends GalleryBaseTreeNode {

    private static final long serialVersionUID = 1L;

    private GalleryHandle handle;


    public GalleryTreeNode(GalleryTree tree, GalleryHandle handle) {
        super(tree);
        this.handle = handle;
    }


    public void refresh() {
        getTree().startEvent("Refreshing " + toString() + ".... ");
        try {
            if (parent != null) {
                getTree().reload(parent);
            } else {
                getTree().reload();
            }
            getTree().stopEvent("Refreshed " + toString() + "!!!");
        } catch (Exception e) {
            getTree().stopEvent("Error refreshing " + toString() + "!!!");
            ErrorDialog.showError(e);
        }
    }


    public ImageIcon getIcon() {
        return GalleryLookAndFeel.getGallery16x16();
    }


    public String toString() {
        return handle.getName();
    }


    public GalleryHandle getGallery() {
        return handle;
    }

}
