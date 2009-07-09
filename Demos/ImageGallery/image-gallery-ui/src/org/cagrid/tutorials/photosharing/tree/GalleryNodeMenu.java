package org.cagrid.tutorials.photosharing.tree;

import javax.swing.JMenuItem;

import org.cagrid.grape.LookAndFeel;
import org.cagrid.tutorials.photosharing.GalleryManager;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class GalleryNodeMenu extends TreeNodeMenu {

    private static final long serialVersionUID = 1L;

    private JMenuItem addImage = null;


    public GalleryNodeMenu(GalleryManager galleryManager, GalleryTree tree) {
        super(galleryManager, tree,true,true);
        initialize();
    }


    /**
     * This method initializes this
     */
    private void initialize() {
        this.add(getAddGallery());
    }



    public void removeNode() {
        
    }


    /**
     * This method initializes addStem
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getAddGallery() {
        if (addImage == null) {
            addImage = new JMenuItem();
            addImage.setIcon(LookAndFeel.getAddIcon());
            addImage.setText("Add Image");
            addImage.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    /*
                    GridApplication.getContext().addApplicationComponent(
                        new AddStemWindow((StemTreeNode) getGridGrouperTree().getCurrentNode()), 700, 300);
                        */
                }
            });
        }
        return addImage;
    }
}
