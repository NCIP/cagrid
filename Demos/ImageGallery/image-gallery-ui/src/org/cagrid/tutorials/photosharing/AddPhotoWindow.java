package org.cagrid.tutorials.photosharing;

import gov.nih.nci.cagrid.common.Runner;
import gov.nih.nci.cagrid.common.Utils;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cagrid.gaards.ui.common.ProgressPanel;
import org.cagrid.gaards.ui.common.TitlePanel;
import org.cagrid.grape.ApplicationComponent;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.utils.ErrorDialog;
import org.cagrid.tutorials.photosharing.tree.GalleryTreeNode;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class AddPhotoWindow extends ApplicationComponent {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private JPanel mainPanel = null;

    private JPanel treePanel = null;

    private JLabel jLabel = null;

    private JButton add = null;

    private GalleryTreeNode root;

    private JPanel titlePanel = null;

    private JTextField path = null;

    private ProgressPanel progress = null;

    private JButton browse = null;

    private JLabel jLabel1 = null;

    private JTextField photoName = null;

    private JLabel jLabel2 = null;

    private JTextField description = null;


    /**
     * This is the default constructor
     */
    public AddPhotoWindow(GalleryTreeNode node) {
        super();
        this.root = node;
        initialize();
    }


    /**
     * This method initializes this
     */
    private void initialize() {
        this.setSize(500, 300);
        this.setContentPane(getJContentPane());
        this.setTitle("Add Photo");
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
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.gridy = 1;
            gridBagConstraints7.weightx = 1.0;
            gridBagConstraints7.anchor = GridBagConstraints.WEST;
            gridBagConstraints7.gridwidth = 2;
            gridBagConstraints7.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints7.gridx = 1;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 0;
            gridBagConstraints6.anchor = GridBagConstraints.WEST;
            gridBagConstraints6.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints6.gridy = 1;
            jLabel2 = new JLabel();
            jLabel2.setText("Description");
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.anchor = GridBagConstraints.WEST;
            gridBagConstraints5.gridx = 1;
            gridBagConstraints5.gridwidth = 2;
            gridBagConstraints5.gridy = 0;
            gridBagConstraints5.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints5.weightx = 1.0;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.anchor = GridBagConstraints.WEST;
            gridBagConstraints4.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints4.gridy = 0;
            jLabel1 = new JLabel();
            jLabel1.setText("Name");
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 2;
            gridBagConstraints3.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints3.anchor = GridBagConstraints.WEST;
            gridBagConstraints3.gridy = 2;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridx = 1;
            gridBagConstraints2.gridy = 2;
            gridBagConstraints2.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints2.anchor = GridBagConstraints.WEST;
            gridBagConstraints2.weightx = 1.0;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints8.gridwidth = 3;
            gridBagConstraints8.anchor = GridBagConstraints.SOUTH;
            gridBagConstraints8.weightx = 1.0D;
            gridBagConstraints8.weighty = 1.0D;
            gridBagConstraints8.gridy = 3;
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
            gridBagConstraints1.anchor = GridBagConstraints.WEST;
            gridBagConstraints1.gridy = 2;
            jLabel = new JLabel();
            jLabel.setText("File");
            treePanel = new JPanel();
            treePanel.setLayout(new GridBagLayout());
            treePanel.add(jLabel, gridBagConstraints1);
            treePanel.add(getAdd(), gridBagConstraints8);
            treePanel.add(getPath(), gridBagConstraints2);
            treePanel.add(getBrowse(), gridBagConstraints3);
            treePanel.add(jLabel1, gridBagConstraints4);
            treePanel.add(getPhotoName(), gridBagConstraints5);
            treePanel.add(jLabel2, gridBagConstraints6);
            treePanel.add(getDescription(), gridBagConstraints7);
        }
        return treePanel;
    }


    /**
     * This method initializes add
     * 
     * @return javax.swing.JButton
     */
    private JButton getAdd() {
        if (add == null) {
            add = new JButton();
            add.setText("Add Photo");
            getRootPane().setDefaultButton(add);
            add.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Runner runner = new Runner() {
                        public void execute() {
                            addPhoto();
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
        return add;
    }


    private void addPhoto() {
        String name = getPhotoName().getText();
        if (Utils.clean(name) == null) {
            ErrorDialog.showError("You must specify name for the photo you wish to add..");
            return;
        }

        String des = getDescription().getText();
        if (Utils.clean(des) == null) {
            ErrorDialog.showError("You must specify a description for the photo you wish to add..");
            return;
        }

        String file = getPath().getText();
        if (Utils.clean(file) == null) {
            ErrorDialog.showError("You must specify the file in which the photo you wish to add is contained.");
            return;
        }
        try {
            getProgress().showProgress("Uploading the photo " + name + "....");
            this.root.getGallery().addPhoto(name, des, new File(file));
            getProgress().stopProgress();
            dispose();

        } catch (Exception e) {
            ErrorDialog.showError(Utils.getExceptionMessage(e), e);
            getProgress().stopProgress();
        }

    }




    /**
     * This method initializes titlePanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getTitlePanel() {
        if (titlePanel == null) {
            titlePanel = new TitlePanel("Add Photo to " + this.root.getGallery().getName(), "Add a photo the gallery "
                + this.root.getGallery().getName() + " for sharing.");
        }
        return titlePanel;
    }


    /**
     * This method initializes path
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getPath() {
        if (path == null) {
            path = new JTextField();
            path.setEditable(false);
        }
        return path;
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
     * This method initializes browse
     * 
     * @return javax.swing.JButton
     */
    private JButton getBrowse() {
        if (browse == null) {
            browse = new JButton();
            browse.setText("Browse");
            final AddPhotoWindow window = this;
            browse.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    JFileChooser f = new JFileChooser();
                    f.setFileFilter(new ImageFileFilter());
                    f.setMultiSelectionEnabled(false);
                    int returnVal = f.showOpenDialog(window);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        f.getSelectedFile().getName();
                        getPath().setText(f.getSelectedFile().getAbsolutePath());
                    }

                }
            });
        }
        return browse;
    }


    /**
     * This method initializes photoName
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getPhotoName() {
        if (photoName == null) {
            photoName = new JTextField();
        }
        return photoName;
    }


    /**
     * This method initializes description
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getDescription() {
        if (description == null) {
            description = new JTextField();
        }
        return description;
    }
}
