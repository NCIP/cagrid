package org.cagrid.tutorials.photosharing.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import org.cagrid.tutorials.photosharing.GalleryManager;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class GalleryTreeEventListener extends MouseAdapter {

    private GalleryTree tree;

    private GalleryManager galleryManager;

    private Map<Class, TreeNodeMenu> popupMappings;


    public GalleryTreeEventListener(GalleryTree tree, GalleryManager galleryManager) {
        this.tree = tree;
        this.popupMappings = new HashMap<Class, TreeNodeMenu>();
        this.galleryManager = galleryManager;
        this.associatePopup(ServiceTreeNode.class, new ServiceNodeMenu(this.galleryManager, this.tree));
        this.associatePopup(GalleryTreeNode.class, new GalleryNodeMenu(this.galleryManager, this.tree));
    }


    /**
     * Associate a GridServiceTreeNode type with a popup menu
     * 
     * @param nodeType
     * @param popup
     */
    public void associatePopup(Class nodeType, TreeNodeMenu popup) {
        this.popupMappings.put(nodeType, popup);
    }


    public void mouseEntered(MouseEvent e) {
        maybeShowPopup(e);
    }


    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }


    private void maybeShowPopup(MouseEvent e) {
        if ((e.isPopupTrigger()) || (SwingUtilities.isRightMouseButton(e))) {
            DefaultMutableTreeNode currentNode = this.tree.getCurrentNode();
            TreeNodeMenu popup = null;
            if (currentNode != null) {
                popup = (TreeNodeMenu) popupMappings.get(currentNode.getClass());
            }
            if (popup != null) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }

        } else if (e.getClickCount() == 2) {
            galleryManager.addNode(this.tree.getCurrentNode());
        }
    }
}
