package org.cagrid.tutorials.photosharing;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class GalleryManagerTabCloseIcon implements Icon {
    private final Icon mIcon;

    private GalleryManager mTabbedPane = null;

    private transient Rectangle mPosition = null;
    private boolean isInited;


    /**
     * Creates a new instance of TabCloseIcon.
     */
    public GalleryManagerTabCloseIcon(GalleryManager manager, Icon icon) {
        mIcon = icon;
        this.isInited = false;
        this.mTabbedPane = manager;
    }


    /**
     * Creates a new instance of TabCloseIcon.
     */
    public GalleryManagerTabCloseIcon(GalleryManager manager) {
        this(manager,

        GalleryLookAndFeel.getCloseTab());
    }


    /**
     * when painting, remember last position painted.
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (!isInited) {
        mTabbedPane.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    // asking for isConsumed is *very* important, otherwise more
                    // than one tab might get closed!
                    if (!e.isConsumed() && mPosition.contains(e.getX(), e.getY())) {
                        mTabbedPane.removeSelectedNode();
                        mTabbedPane.removeMouseListener(this);
                    }
                }
            });
            isInited = true;
        }

        mPosition = new Rectangle(x, y, getIconWidth(), getIconHeight());
        mIcon.paintIcon(c, g, x, y);
    }


    /**
     * just delegate
     */
    public int getIconWidth() {
        return mIcon.getIconWidth();
    }


    /**
     * just delegate
     */
    public int getIconHeight() {
        return mIcon.getIconHeight();
    }

}
