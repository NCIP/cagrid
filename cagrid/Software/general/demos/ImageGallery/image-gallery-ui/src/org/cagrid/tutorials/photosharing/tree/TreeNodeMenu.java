package org.cagrid.tutorials.photosharing.tree;

import gov.nih.nci.cagrid.common.Runner;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.cagrid.gaards.ui.common.ProgressPanel;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.LookAndFeel;
import org.cagrid.tutorials.photosharing.GalleryLookAndFeel;
import org.cagrid.tutorials.photosharing.GalleryManager;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public abstract class TreeNodeMenu extends JPopupMenu {

    private static final long serialVersionUID = 1L;

    private GalleryManager galleryManager;

    private GalleryTree tree;

    private JMenuItem view = null;

    private JMenuItem refresh = null;

    private JMenuItem remove = null;

    private ProgressPanel progress;

    private boolean allowRemove;

    private boolean allowView;


    public TreeNodeMenu(GalleryManager galleryManager, GalleryTree tree, boolean allowRemove, boolean allowView) {
        super("");
        this.galleryManager = galleryManager;
        this.tree = tree;
        this.progress = this.tree.getProgress();
        this.allowRemove = allowRemove;
        this.allowView = allowView;
        initialize();

    }


    /**
     * This method initializes this
     */
    private void initialize() {
        if (allowView) {
            this.add(getView());
        }
        this.add(getRefresh());
        if (allowRemove) {
            this.add(getRemove());
        }

    }


    /**
     * This method initializes view
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getView() {
        if (view == null) {
            view = new JMenuItem();
            view.setText("View");
            view.setIcon(LookAndFeel.getQueryIcon());
            view.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Runner runner = new Runner() {
                        public void execute() {
                            galleryManager.addNode(getTree().getCurrentNode());
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
        return view;
    }


    public GalleryManager getGalleryManager() {
        return galleryManager;
    }


    public GalleryTree getTree() {
        return tree;
    }


    /**
     * This method initializes refresh
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getRefresh() {
        if (refresh == null) {
            refresh = new JMenuItem();
            refresh.setText("Refresh");
            refresh.setIcon(GalleryLookAndFeel.getLoadIcon());
            refresh.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Runner runner = new Runner() {
                        public void execute() {
                            getTree().getCurrentNode().refresh();
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
        return refresh;
    }


    protected void toggleRemove(boolean toggle) {
        this.getRemove().setEnabled(toggle);
    }


    /**
     * This method initializes remove
     * 
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getRemove() {
        if (remove == null) {
            remove = new JMenuItem();
            remove.setText("Remove");
            remove.setIcon(LookAndFeel.getRemoveIcon());
            remove.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Runner runner = new Runner() {
                        public void execute() {
                            removeNode();
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
        return remove;
    }


    public ProgressPanel getProgress() {
        return progress;
    }


    public abstract void removeNode();

}
