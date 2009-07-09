package org.cagrid.tutorials.photosharing;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.cagrid.grape.ApplicationComponent;
import org.cagrid.tutorials.photosharing.tree.GalleryTree;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTree;


public class GalleryBrowser extends ApplicationComponent {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JSplitPane jSplitPane = null;
    private JPanel leftPanel = null;
    private JPanel rightPanel = null;
    private JScrollPane treeScrollPane = null;
    private GalleryTree galleryTree = null;
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
        this.setTitle("JFrame");
    }


    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.BOTH;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.ipadx = 100;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.weighty = 1.0;
            gridBagConstraints1.gridx = 0;
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(getJSplitPane(), gridBagConstraints1);
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
            jSplitPane.setLeftComponent(getLeftPanel());
            jSplitPane.setRightComponent(getRightPanel());
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
            rightPanel = new JPanel();
            rightPanel.setLayout(new GridBagLayout());
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
        }
        return galleryTree;
    }

}
