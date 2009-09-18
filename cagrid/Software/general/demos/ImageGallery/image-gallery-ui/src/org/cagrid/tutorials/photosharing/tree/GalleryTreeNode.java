package org.cagrid.tutorials.photosharing.tree;

import gov.nih.nci.cagrid.common.Utils;

import javax.swing.ImageIcon;

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
            getTree().stopEvent("");
        } catch (Exception e) {
            getTree().stopEvent("");
            ErrorDialog.showError(e);
        }
    }


    public void deleteGallery() {
        getTree().startEvent("Deleting " + toString() + ".... ");
        try {
            ServiceTreeNode parent = (ServiceTreeNode) getParent();
            getGallery().deleteGallery();
            parent.refresh();
        } catch (Exception e) {
            ErrorDialog.showError(Utils.getExceptionMessage(e), e);
        }
        getTree().stopEvent("");
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
