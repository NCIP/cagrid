package org.cagrid.tutorials.photosharing.tree;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import org.cagrid.gaards.ui.gridgrouper.GridGrouperLookAndFeel;
import org.cagrid.grape.utils.ErrorDialog;
import org.cagrid.tutorials.photosharing.GalleryHandle;
import org.cagrid.tutorials.photosharing.GalleryLookAndFeel;
import org.cagrid.tutorials.photosharing.PhotoSharingHandle;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class ServiceTreeNode extends GalleryBaseTreeNode {

    private static final long serialVersionUID = 1L;

    private PhotoSharingHandle handle = null;


    public ServiceTreeNode(GalleryTree tree, PhotoSharingHandle handle) {
        super(tree);
        this.handle = handle;
    }


    public void loadService() throws Exception {
        this.removeAllChildren();
        List<GalleryHandle> galleries = handle.getGalleries();
        for (int i = 0; i < galleries.size(); i++) {
            GalleryTreeNode node = new GalleryTreeNode(getTree(), galleries.get(i));
            synchronized (getTree()) {
                this.add(node);
                TreeNode parentNode = this.getParent();
                if (parentNode != null) {
                    getTree().reload(parentNode);
                } else {
                    getTree().reload();
                }
            }
        }
    }


    public void refresh() {
       getTree().startEvent("Refreshing " + toString() + ".... ");
        try {
            if (parent != null) {
                getTree().reload(parent);
            } else {
                getTree().reload();
            }
            loadService();
            getTree().stopEvent("Refreshed " + toString() + "!!!");
        } catch (Exception e) {
            e.printStackTrace();
            getTree().stopEvent("Error refreshing " + toString() + "!!!");
            ErrorDialog.showError(e);
        }
    }


    public ImageIcon getIcon() {
        return GalleryLookAndFeel.getService16x16();
    }


    public String toString() {
        return handle.getDisplayName();
    }


    public PhotoSharingHandle getServiceHandle() {
        return handle;
    }

}
