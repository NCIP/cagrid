package org.cagrid.tutorials.photosharing;

import javax.swing.ImageIcon;

import org.cagrid.grape.LookAndFeel;
import org.cagrid.grape.utils.IconUtils;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class GalleryLookAndFeel extends LookAndFeel {

    public final static ImageIcon getLoadIcon() {
        return IconUtils.loadIcon("/view-refresh.png");
    }


    public final static ImageIcon getCloseTab() {
        return IconUtils.loadIcon("/closeTab.gif");
    }
    
    public final static ImageIcon getcaGridLogo() {
        return IconUtils.loadIcon("/caGrid.png");
    }
    
    public final static ImageIcon getService16x16() {
        return IconUtils.loadIcon("/photo-sharing16x16.png");
    }
    
    public final static ImageIcon getGallery16x16() {
        return IconUtils.loadIcon("/gallery16x16.png");
    }
    
    public final static ImageIcon getService22x22() {
        return IconUtils.loadIcon("/photo-sharing22x22.png");
    }
    
    public final static ImageIcon getGallery22x22() {
        return IconUtils.loadIcon("/gallery22x22.png");
    }


}
