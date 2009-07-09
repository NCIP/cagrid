package org.cagrid.tutorials.photosharing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.cagrid.gaards.ui.common.ProgressPanel;
import org.cagrid.gaards.ui.common.TitlePanel;
import org.cagrid.grape.ApplicationComponent;
import org.cagrid.tutorials.photosharing.tree.GalleryTree;
import org.cagrid.tutorials.photosharing.tree.GalleryTreeEventListener;

import javax.swing.JTabbedPane;
import java.awt.Insets;


public class GalleryBrowser extends ApplicationComponent {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JSplitPane jSplitPane = null;
    private JPanel leftPanel = null;
    private JPanel rightPanel = null;
    private JScrollPane treeScrollPane = null;
    private GalleryTree galleryTree = null;
    private JPanel titlePanel = null;
    private ProgressPanel progress = null;
    private GalleryManager galleryManager = null;
    /**
     * This is the default constructor
     */
    public GalleryBrowser() {
        super();
        initialize();
    }


    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(300, 200);
        this.setContentPane(getJContentPane());
        this.setTitle("Gallery");
    }


    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.weightx = 1.0D;
            gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridy = 2;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.weightx = 1.0D;
            gridBagConstraints2.anchor = GridBagConstraints.WEST;
            gridBagConstraints2.gridy = 0;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.BOTH;
            gridBagConstraints1.gridy = 1;
            gridBagConstraints1.ipadx = 100;
            gridBagConstraints1.weightx = 1.0D;
            gridBagConstraints1.weighty = 1.0;
            gridBagConstraints1.gridx = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(getJSplitPane(), gridBagConstraints1);
            jContentPane.add(getTitlePanel(), gridBagConstraints2);
            jContentPane.add(getProgress(), gridBagConstraints3);
        }
        return jContentPane;
    }


    /**
     * This method initializes jSplitPane	
     * 	
     * @return javax.swing.JSplitPane	
     */
    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setRightComponent(getRightPanel());
            jSplitPane.setLeftComponent(getLeftPanel());
        }
        return jSplitPane;
    }


    /**
     * This method initializes leftPanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getLeftPanel() {
        if (leftPanel == null) {
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.gridx = 0;
            leftPanel = new JPanel();
            leftPanel.setLayout(new GridBagLayout());
            leftPanel.add(getTreeScrollPane(), gridBagConstraints);
        }
        return leftPanel;
    }


    /**
     * This method initializes rightPanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getRightPanel() {
        if (rightPanel == null) {
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = GridBagConstraints.BOTH;
            gridBagConstraints4.weighty = 1.0;
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.gridy = 0;
            gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints4.weightx = 1.0;
            rightPanel = new JPanel();
            rightPanel.setLayout(new GridBagLayout());
            rightPanel.add(getGalleryManager(), gridBagConstraints4);
        }
        return rightPanel;
    }


    /**
     * This method initializes treeScrollPane	
     * 	
     * @return javax.swing.JScrollPane	
     */
    private JScrollPane getTreeScrollPane() {
        if (treeScrollPane == null) {
            treeScrollPane = new JScrollPane();
            treeScrollPane.setViewportView(getGalleryTree());
        }
        return treeScrollPane;
    }


    /**
     * This method initializes galleryTree	
     * 	
     * @return javax.swing.JTree	
     */
    private GalleryTree getGalleryTree() {
        if (galleryTree == null) {
            galleryTree = new GalleryTree();
            galleryTree.setProgress(getProgress());
            galleryTree.addMouseListener(new GalleryTreeEventListener(galleryTree, getGalleryManager()));
        }
        return galleryTree;
    }


    /**
     * This method initializes titlePanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getTitlePanel() {
        if (titlePanel == null) {
            titlePanel = new TitlePanel("Gallery", "Create, view, and share photo galleries.");
        }
        return titlePanel;
    }


    /**
     * This method initializes progress	
     * 	
     * @return javax.swing.JPanel	
     */
    private ProgressPanel getProgress() {
        if (progress == null) {
            progress = new ProgressPanel();
        }
        return progress;
    }


    /**
     * This method initializes galleryManager	
     * 	
     * @return javax.swing.JTabbedPane	
     */
    private GalleryManager getGalleryManager() {
        if (galleryManager == null) {
            galleryManager = new GalleryManager(getProgress());
        }
        return galleryManager;
    }

}
