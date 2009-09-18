package org.cagrid.tutorials.photosharing.tree;

import gov.nih.nci.cagrid.common.Runner;
import gov.nih.nci.cagrid.common.Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;

import org.cagrid.gaards.ui.gridgrouper.GridGrouperLookAndFeel;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.utils.ErrorDialog;
import org.cagrid.tutorials.photosharing.PhotoSharingHandle;


public class ServicesTreeNode extends GalleryBaseTreeNode {

    private static final long serialVersionUID = 1L;

    private Map services;


    public ServicesTreeNode(GalleryTree tree) {
        super(tree);
        this.services = new HashMap();
    }


    public synchronized void addService(PhotoSharingHandle handle) {
        if (services.containsKey(handle.getDisplayName())) {
            ErrorDialog
                .showError("The Photo Sharing service " + handle.getDisplayName() + " has already been added!!!");
        } else {
             getTree().startEvent("Loading service.... ");
            try {

                ServiceTreeNode node = new ServiceTreeNode(getTree(), handle);
                synchronized (getTree()) {
                    this.add(node);
                    getTree().reload(this);
                }
                node.loadService();
                getTree().stopEvent("");
                this.services.put(handle.getDisplayName(), node);
            } catch (Exception e) {
                ErrorDialog.showError(Utils.getExceptionMessage(e),e);
                getTree().stopEvent("");
            }

        }

    }


    public synchronized void removeAllServices() {
        this.services.clear();
        this.removeAllChildren();
    }


    public synchronized void refresh() {
        Map old = services;
        services = new HashMap();
        this.removeAllChildren();
        Iterator itr = old.values().iterator();
        while (itr.hasNext()) {
            final ServiceTreeNode node = (ServiceTreeNode) itr.next();
            Runner runner = new Runner() {
                public void execute() {
                    addService(node.getServiceHandle());
                }
            };
            try {
                GridApplication.getContext().executeInBackground(runner);
            } catch (Exception t) {
                t.getMessage();
            }

        }

    }


 


    public void removeSelectedService() {
        GalleryBaseTreeNode node = this.getTree().getCurrentNode();
        if (node == null) {
            ErrorDialog.showError("No service selected, please select a service!!!");
        } else {
            if (node instanceof ServiceTreeNode) {
                ServiceTreeNode stn = (ServiceTreeNode) node;
                synchronized (getTree()) {
                    stn.removeFromParent();
                    this.services.remove(stn.getServiceHandle().getDisplayName());
                    getTree().reload(this);
                }
            } else {
                ErrorDialog.showError("No service selected, please select a service!!!");
            }
        }

    }


    public ImageIcon getIcon() {
        return GridGrouperLookAndFeel.getGridGrouperServicesIcon16x16();
    }


    public String toString() {
        return "Photo Sharing Service(s)";
    }
}
