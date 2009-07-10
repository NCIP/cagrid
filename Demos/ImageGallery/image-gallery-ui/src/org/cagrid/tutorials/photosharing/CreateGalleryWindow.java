package org.cagrid.tutorials.photosharing;

import gov.nih.nci.cagrid.common.Runner;
import gov.nih.nci.cagrid.common.Utils;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cagrid.gaards.ui.common.ProgressPanel;
import org.cagrid.gaards.ui.common.TitlePanel;
import org.cagrid.grape.ApplicationComponent;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.utils.ErrorDialog;
import org.cagrid.tutorials.photosharing.tree.ServiceTreeNode;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class CreateGalleryWindow extends ApplicationComponent {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private JPanel mainPanel = null;

    private JPanel treePanel = null;

    private JLabel jLabel = null;

    private JButton create = null;

    private ServiceTreeNode root;

    private JPanel titlePanel = null;

    private JTextField galleryNane = null;

    private ProgressPanel progress = null;


    /**
     * This is the default constructor
     */
    public CreateGalleryWindow(ServiceTreeNode node) {
        super();
        this.root = node;
        initialize();
    }


    /**
     * This method initializes this
     */
    private void initialize() {
        this.setSize(400, 150);
        this.setContentPane(getJContentPane());
        this.setTitle("Create Gallery");
        this.setFrameIcon(GalleryLookAndFeel.getGallery22x22());
    }


    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getMainPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }


    /**
     * This method initializes mainPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getMainPanel() {
        if (mainPanel == null) {
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.gridx = 0;
            gridBagConstraints12.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints12.weightx = 1.0D;
            gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints12.anchor = GridBagConstraints.EAST;
            gridBagConstraints12.gridy = 2;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.weightx = 1.0D;
            gridBagConstraints11.gridy = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0D;
            gridBagConstraints.weighty = 1.0D;
            gridBagConstraints.gridx = 0;
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.add(getTreePanel(), gridBagConstraints);
            mainPanel.add(getTitlePanel(), gridBagConstraints11);
            mainPanel.add(getProgress(), gridBagConstraints12);
        }
        return mainPanel;
    }


    /**
     * This method initializes treePanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getTreePanel() {
        if (treePanel == null) {
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridx = 1;
            gridBagConstraints2.gridy = 0;
            gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints2.weightx = 1.0;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints8.gridwidth = 2;
            gridBagConstraints8.anchor = GridBagConstraints.SOUTH;
            gridBagConstraints8.gridy = 1;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints1.anchor = GridBagConstraints.WEST;
            gridBagConstraints1.gridy = 0;
            jLabel = new JLabel();
            jLabel.setText("Gallery Name");
            jLabel.setFont(new Font("Dialog", Font.BOLD, 12));
            treePanel = new JPanel();
            treePanel.setLayout(new GridBagLayout());
            treePanel.add(jLabel, gridBagConstraints1);
            treePanel.add(getCreate(), gridBagConstraints8);
            treePanel.add(getGalleryNane(), gridBagConstraints2);
        }
        return treePanel;
    }


    /**
     * This method initializes create
     * 
     * @return javax.swing.JButton
     */
    private JButton getCreate() {
        if (create == null) {
            create = new JButton();
            create.setText("Create");
            getRootPane().setDefaultButton(create);
            create.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Runner runner = new Runner() {
                        public void execute() {
                            createGallery();
                        }
                    };
                    try {
                        GridApplication.getContext().executeInBackground(runner);
                    } catch (Exception t) {
                        t.getMessage();
                    }

                }
            });
        }
        return create;
    }


    private void createGallery() {
        String name = this.getGalleryNane().getText();
        if(Utils.clean(name)==null){
            ErrorDialog.showError("You must specify a gallery name.");
            return;
        }
        try {
            getProgress().showProgress("Creating gallery...");
            PhotoSharingHandle handle = this.root.getServiceHandle();
            handle.createGallery(name);
            this.root.refresh();
            getProgress().stopProgress();
            dispose();
        } catch (Exception e) {
            ErrorDialog.showError(Utils.getExceptionMessage(e),e);
        }

    }


    /**
     * This method initializes titlePanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getTitlePanel() {
        if (titlePanel == null) {
            titlePanel = new TitlePanel("Create Gallery","Create a gallery for organizing and sharing your photos.");
        }
        return titlePanel;
    }


    /**
     * This method initializes galleryNane	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getGalleryNane() {
        if (galleryNane == null) {
            galleryNane = new JTextField();
        }
        return galleryNane;
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
}
