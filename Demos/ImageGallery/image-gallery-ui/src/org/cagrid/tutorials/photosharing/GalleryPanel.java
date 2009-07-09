package org.cagrid.tutorials.photosharing;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.cagrid.gaards.ui.common.ProgressPanel;
import org.cagrid.tutorials.photosharing.tree.GalleryTreeNode;


public class GalleryPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private GalleryTreeNode gallery = null;
    private ProgressPanel progress;


    public ProgressPanel getProgress() {
        return progress;
    }


    public void setProgress(ProgressPanel progress) {
        this.progress = progress;
    }


    /**
     * This is the default constructor
     */
    public GalleryPanel(GalleryTreeNode node) {
        super();
        this.gallery = node;
        initialize();
    }


    public void setGallery() {

    }


    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
    }


    public GalleryTreeNode getGallery() {
        return this.gallery;
    }

}
