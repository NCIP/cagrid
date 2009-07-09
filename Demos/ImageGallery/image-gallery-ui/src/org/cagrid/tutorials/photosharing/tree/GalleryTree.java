
package org.cagrid.tutorials.photosharing.tree;

import gov.nih.nci.cagrid.common.FaultUtil;
import gov.nih.nci.cagrid.common.Runner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.cagrid.grape.GridApplication;
import org.cagrid.grape.utils.ErrorDialog;
import org.cagrid.grape.utils.MultiEventProgressBar;
import org.cagrid.tutorials.photosharing.PhotoSharingHandle;

/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class GalleryTree extends JTree {
	
	private static final long serialVersionUID = 1L;

	private ServicesTreeNode rootNode;

	private MultiEventProgressBar progress;

	public GalleryTree() {
		super();
		setLargeModel(true);
		this.rootNode = new ServicesTreeNode(this);
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		setModel(new DefaultTreeModel(this.rootNode));
		this.setCellRenderer(new TreeRenderer());
		loadServicesFromConfiguration();
	}
	
	   private void loadServicesFromConfiguration() {
	        try {
	            List<PhotoSharingHandle> services = org.cagrid.tutorials.photosharing.Utils.getPhotoSharingServices();
	            for (int i = 0; i < services.size(); i++) {
	                addService(services.get(i));
	            }
	        } catch (Exception e) {
	            ErrorDialog.showError("An unexpected error occurred loading the configured photo sharing services!!!", e);
	            FaultUtil.printFault(e);
	        }
	    }

	public ServiceTreeNode getSelectedService() {
		GalleryBaseTreeNode selected = getSelectedNode();
		if (selected != null) {
			if (selected instanceof ServiceTreeNode) {
				return (ServiceTreeNode) selected;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public GalleryTreeNode getSelectedGroup() {
		GalleryBaseTreeNode selected = getSelectedNode();
		if (selected != null) {
			if (selected instanceof GalleryTreeNode) {
				return (GalleryTreeNode) selected;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public GalleryBaseTreeNode getSelectedNode() {
		List list = getSelectedNodes();
		if (list == null) {
			return null;
		} else {
			if (list.size() > 0) {
				return (GalleryBaseTreeNode) list.get(0);
			} else {
				return null;
			}
		}
	}

	public int startEvent(String message) {
		if (this.progress != null) {
			return this.progress.startEvent(message);
		} else {
			return -1;
		}
	}

	public void stopEvent(int eventId, String message) {
		if (this.progress != null) {
			this.progress.stopEvent(eventId, message);
		}
	}

	public void setProgress(MultiEventProgressBar progess) {
		this.progress = progess;
	}

	public void addService(final PhotoSharingHandle handle) {
		Runner runner = new Runner() {
			public void execute() {
				try {
					rootNode.addService(handle);
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

	public ServicesTreeNode getRootNode() {
		return this.rootNode;
	}

	public List getGroupNodes() {
		List nodes = new ArrayList();
		this.getGroupNodes(getRootNode(), nodes);
		return nodes;
	}

	private void getGroupNodes(GalleryBaseTreeNode node, List nodes) {
		int count = node.getChildCount();
		for (int i = 0; i < count; i++) {
			GalleryBaseTreeNode child = (GalleryBaseTreeNode) node
					.getChildAt(i);
			if (child instanceof GalleryTreeNode) {
				nodes.add(child);
			} else if (child instanceof ServiceTreeNode) {
				getGroupNodes(child, nodes);
			}
		}
	}

	public GalleryBaseTreeNode getCurrentNode() {
		TreePath currentSelection = this.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentSelection
					.getLastPathComponent();
			return (GalleryBaseTreeNode) currentNode;
		}
		return null;
	}

	/**
	 * Get all the selected service nodes
	 * 
	 * @return A List of GridServiceTreeNodes
	 */
	public List getSelectedNodes() {
		List selected = new LinkedList();
		TreePath[] currentSelection = this.getSelectionPaths();
		if (currentSelection != null) {
			for (int i = 0; i < currentSelection.length; i++) {
				TreePath path = currentSelection[i];
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				if (currentNode != this.getRootNode()) {
					selected.add(currentNode);
				}
			}
		}
		return selected;
	}

	/**
	 * Reload a portion of the tree's view in a synchronized way
	 * 
	 * @param reloadPoint
	 *            The node from which to reload
	 */
	public synchronized void reload(TreeNode reloadPoint) {
		((DefaultTreeModel) this.getModel()).reload(reloadPoint);
	}

	/**
	 * Reload from the root
	 */
	public synchronized void reload() {
		this.reload(getRootNode());
	}

}
