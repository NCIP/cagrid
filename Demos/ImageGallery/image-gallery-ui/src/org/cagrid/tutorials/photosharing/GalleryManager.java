package org.cagrid.tutorials.photosharing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.cagrid.gaards.ui.common.ProgressPanel;
import org.cagrid.grape.utils.ErrorDialog;
import org.cagrid.tutorials.photosharing.tree.GalleryBaseTreeNode;
import org.cagrid.tutorials.photosharing.tree.GalleryTreeNode;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class GalleryManager extends JTabbedPane {

    private static final long serialVersionUID = 1L;

    private JPanel welcomePanel = null;

    private static final String WELCOME = "Gallery";

    private JLabel logo = null;

    private Map<String, GalleryPanel> galleries = new HashMap<String, GalleryPanel>(); // @jve:decl-index=0:

    private ProgressPanel progress;


    /**
     * This is the default constructor
     */
    public GalleryManager(ProgressPanel progress) {
        super();
        this.progress = progress;
        initialize();
    }


    /**
     * This method initializes this
     */
    private void initialize() {
        this.setSize(300, 200);
        this.addTab(WELCOME, GalleryLookAndFeel.getService22x22(), getWelcomePanel(), null);
    }


    public void addNode(GalleryBaseTreeNode node) {
        if (node instanceof GalleryTreeNode) {
            this.addGallery((GalleryTreeNode) node);
        } else {
            ErrorDialog.showError("Please select a gallery to view!!!");
        }
    }


    public void viewGallery(GalleryTreeNode node) {
        addNode(node);
    }


    public void removeNode(GalleryBaseTreeNode node) {
        if (node instanceof GalleryTreeNode) {
            this.removeGallery((GalleryTreeNode) node);
        } else {
            ErrorDialog.showError("Please a gallery to remove!!!");
        }
    }


    public void refreshGallery(GalleryTreeNode node) {
        String name = node.getGallery().getName();
        if (galleries.containsKey(name)) {
            GalleryPanel browse = galleries.get(name);
            for (int i = 0; i < getTabCount(); i++) {
                if (getComponentAt(i) == browse) {
                    this.setTitleAt(i, name);
                }
            }
            //browse.setGallery();
        }
    }


    public void addGallery(GalleryTreeNode node) {
        String name = node.getGallery().getName();
        this.removeGallery(node, true);
        GalleryPanel browser = new GalleryPanel(node);
        browser.setProgress(progress);
        galleries.put(name, browser);
        this.addTab(name,
            new CombinedIcon(new GalleryManagerTabCloseIcon(this), GalleryLookAndFeel.getGallery16x16()), browser,
            null);
        this.remove(getWelcomePanel());
        this.setSelectedComponent(browser);

    }


    public void removeSelectedNode() {
        Component c = this.getSelectedComponent();
        if (c instanceof GalleryPanel) {
            GalleryPanel sb = (GalleryPanel) c;
            removeGallery(sb.getGallery());
        }
    }


    public void removeGallery(GalleryTreeNode node) {
        this.removeGallery(node, false);
    }


    private void removeGallery(GalleryTreeNode node, boolean internal) {
        String name = node.getGallery().getName();
        if (galleries.containsKey(name)) {
            GalleryPanel sb = galleries.remove(name);
            this.remove(sb);
        }
        if (!internal) {
            if (galleries.size() == 0) {
                this.addTab(WELCOME, GalleryLookAndFeel.getService22x22(), getWelcomePanel(), null);
                this.setSelectedComponent(getWelcomePanel());
            }
        }
    }


  


    /**
     * This method initializes welcomePanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getWelcomePanel() {
        if (welcomePanel == null) {
            logo = new JLabel(GalleryLookAndFeel.getcaGridLogo());
            welcomePanel = new JPanel();
            welcomePanel.setLayout(new GridBagLayout());
            welcomePanel.add(logo, new GridBagConstraints());
        }
        return welcomePanel;
    }
}
