package org.cagrid.tutorials.photosharing;

import gov.nih.nci.cagrid.common.Runner;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.cagrid.gaards.ui.common.ProgressPanel;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.utils.ErrorDialog;
import org.cagrid.tutorials.photosharing.tree.GalleryTreeNode;


public class GalleryPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private GalleryTreeNode gallery = null;
    private ProgressPanel progress;

    private JTabbedPane jTabbedPane = null;

    private JScrollPane thumbnailScrollPane = null;

    private JPanel thumbnails = null;

    private JSplitPane jSplitPane = null;

    private JScrollPane photoScrollpane = null;

    private JPanel photo = null;


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
        Runner runner = new Runner() {
            public void execute() {
                try {
                    loadGallery();
                } catch (Exception e) {
                    ErrorDialog.showError(e);
                }
            }
        };
        try {
            GridApplication.getContext().executeInBackground(runner);
        } catch (Exception t) {
            t.getMessage();
        }
    }


    public void loadGallery() {
        GalleryHandle handle = this.gallery.getGallery();
        try {
            List<PhotoHandle> photos = handle.getPhotos();
            List<Thumbnail> thumbnails = new ArrayList<Thumbnail>();
            for (int i = 0; i < photos.size(); i++) {
                PhotoHandle photo = photos.get(i);
                GridBagConstraints image = new GridBagConstraints();
                image.fill = GridBagConstraints.NONE;
                image.weighty = 1.0;
                image.gridx = i;
                image.gridy = 0;
                image.weightx = 1.0;
                image.weighty = 0.0;
                image.anchor = GridBagConstraints.CENTER;
                GridBagConstraints label = new GridBagConstraints();
                label.fill = GridBagConstraints.NONE;
                label.weighty = 1.0;
                label.gridx = i;
                label.gridy = 1;
                label.weightx = 1.0;
                label.weighty = 0.0;
                label.anchor = GridBagConstraints.CENTER;
                JLabel name = new JLabel(photo.getName());
                final Thumbnail pic = new Thumbnail(photo);
                final GalleryPanel gp = this;
                pic.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        Runner runner = new Runner() {
                            public void execute() {
                                PhotoHandle ph = pic.getPhoto();
                                try {
                                   
                                    GridBagConstraints image = new GridBagConstraints();
                                    image.fill = GridBagConstraints.BOTH;
                                    image.weighty = 1.0;
                                    image.gridx = 0;
                                    image.gridy = 0;
                                    image.weightx = 1.0;
                                    image.weighty = 0.0;
                                    image.anchor = GridBagConstraints.CENTER;
                                    GridBagConstraints des = new GridBagConstraints();
                                    image.fill = GridBagConstraints.NONE;
                                    des.weighty = 1.0;
                                    des.gridx = 0;
                                    des.gridy = 1;
                                    des.weightx = 1.0;
                                    des.weighty = 0.0;
                                    des.anchor = GridBagConstraints.CENTER;
                                    getPhoto().removeAll();
                                    getPhoto().add(new JLabel(ph.getPhoto()), image);
                                    getPhoto().add(new JLabel(ph.getDescription()), des);
                                    pic.setIcon(ph.getThumbnail());
                                    getPhoto().setBorder(javax.swing.BorderFactory.createTitledBorder(null, ph.getName(),
                                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                        javax.swing.border.TitledBorder.DEFAULT_POSITION, null, org.cagrid.grape.LookAndFeel
                                            .getPanelLabelColor()));
                                    gp.invalidate();
                                    gp.repaint();
                                } catch (Exception e) {
                                    ErrorDialog.showError("Error retrieving the photo " + ph.getName()
                                        + " from the gallery " + ph.getGalleryName() + ".", e);
                                }
                            }
                        };
                        try {
                            GridApplication.getContext().executeInBackground(runner);
                        } catch (Exception t) {
                            t.getMessage();
                        }

                    }
                });
                pic.setIcon(GalleryLookAndFeel.getPhotoLoadIcon());
                getThumbnails().add(pic, image);
                getThumbnails().add(name, label);
                thumbnails.add(pic);
                this.invalidate();
                this.repaint();
                
            }
            this.invalidate();
            this.repaint();
            for (int i = 0; i < thumbnails.size(); i++) {
                Thumbnail pic = thumbnails.get(i);
                try {

                    pic.setIcon(pic.getPhoto().getThumbnail());

                    this.invalidate();
                    this.repaint();
                    this.validate();
                } catch (Exception e) {
                    ErrorDialog.showError("Error retrieving the photo " + pic.getPhoto().getName()
                        + " from the gallery " + handle.getName() + ".", e);
                }
            }

        } catch (Exception e) {
            ErrorDialog.showError("Error retrieving photos for the gallery " + handle.getName() + ".", e);
        }
    }


    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
        this.add(getJTabbedPane(), gridBagConstraints);
    }


    public GalleryTreeNode getGallery() {
        return this.gallery;
    }


    /**
     * This method initializes jTabbedPane
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.addTab("Photos", null, getJSplitPane(), null);
        }
        return jTabbedPane;
    }


    /**
     * This method initializes thumbnailScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getThumbnailScrollPane() {
        if (thumbnailScrollPane == null) {
            thumbnailScrollPane = new JScrollPane();
            thumbnailScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thumbnails",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, null, org.cagrid.grape.LookAndFeel
                    .getPanelLabelColor()));
            thumbnailScrollPane.setViewportView(getThumbnails());
        }
        return thumbnailScrollPane;
    }


    /**
     * This method initializes thumbnails
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getThumbnails() {
        if (thumbnails == null) {
            thumbnails = new JPanel();
            thumbnails.setLayout(new GridBagLayout());
        }
        return thumbnails;
    }


    /**
     * This method initializes jSplitPane
     * 
     * @return javax.swing.JSplitPane
     */
    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            jSplitPane.setDividerLocation(200);
            jSplitPane.setBottomComponent(getPhotoScrollpane());
            jSplitPane.setTopComponent(getThumbnailScrollPane());
        }
        return jSplitPane;
    }


    /**
     * This method initializes photoScrollpane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getPhotoScrollpane() {
        if (photoScrollpane == null) {
            photoScrollpane = new JScrollPane();
            photoScrollpane.setViewportView(getPhoto());
        }
        return photoScrollpane;
    }


    /**
     * This method initializes photo
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPhoto() {
        if (photo == null) {
            photo = new JPanel();
            photo.setLayout(new GridBagLayout());
            photo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Photo",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, null, org.cagrid.grape.LookAndFeel
                    .getPanelLabelColor()));
        }
        return photo;
    }
}
