package gov.nih.nci.cagrid.data.ui.types;

import gov.nih.nci.cagrid.introduce.beans.namespace.NamespaceType;
import gov.nih.nci.cagrid.introduce.beans.namespace.SchemaElementType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.tree.DefaultTreeModel;


/** 
 *  DomainTreeNode
 *  Node in the TargetTypesTree to represent a domain model.  
 *  Children are all TypeTreeNodes
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * 
 * @created Apr 20, 2006 
 * @version $Id$ 
 */
public class DomainTreeNode extends CheckBoxTreeNode {
	
	private NamespaceType namespace;
	private TargetTypesTree parentTree;
	private Map checkBoxTypes;
	private Map typeCheckBoxes;

	public DomainTreeNode(TargetTypesTree tree, NamespaceType namespace) {
		super(namespace.getNamespace());
		this.parentTree = tree;
		this.namespace = namespace;
		this.checkBoxTypes = new HashMap();
		this.typeCheckBoxes = new HashMap();
		
		// add child nodes
		SchemaElementType[] types = namespace.getSchemaElement();
		if (types != null) {
			ActionListener childListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (allChildrenChecked()) {
						getCheckBox().setSelected(true);
					} else if (noChildrenChecked()) {
						getCheckBox().setSelected(false);
					}
				}
			};
			ItemListener childItemListener = new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					// tell everybody that the type selection has been changed
					JCheckBox checkBox = (JCheckBox) e.getSource();
					if (checkBox.isSelected()) {
						parentTree.fireTypeSelectionAdded(getNamespace(), (SchemaElementType) checkBoxTypes.get(checkBox));
					} else {
						parentTree.fireTypeSelectionRemoved(getNamespace(), (SchemaElementType) checkBoxTypes.get(checkBox));
					}
				}
			};
			// add the nodes
			for (int i = 0; i < types.length; i++) {
				TypeTreeNode node = new TypeTreeNode(types[i]);
				node.getCheckBox().addActionListener(childListener);
				node.getCheckBox().addItemListener(childItemListener);
				checkBoxTypes.put(node.getCheckBox(), node.getType());
				typeCheckBoxes.put(node.getType(), node.getCheckBox());
				add(node);
			}
		}
		// add listener to turn all children's check boxes on / off
		getCheckBox().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int childCount = getChildCount();
				for (int i = 0; i < childCount; i++) {
					TypeTreeNode node = (TypeTreeNode) getChildAt(i);
					node.getCheckBox().setSelected(isChecked());
					((DefaultTreeModel) parentTree.getModel()).nodeChanged(node);
				}
			}
		});
		// repaint the node when it changes
		getCheckBox().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				((DefaultTreeModel) parentTree.getModel()).nodeChanged(DomainTreeNode.this);
			}
		});
	}
	
	
	public NamespaceType getNamespace() {
		return this.namespace;
	}
	
	
	public void checkTypeNodes(SchemaElementType[] types) {
		for (int i = 0; i < types.length; i++) {
			JCheckBox check = (JCheckBox) typeCheckBoxes.get(types[i]);
			if (check != null) {
				check.setSelected(true);
			}
		}
	}
	
	
	public boolean allChildrenChecked() {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			TypeTreeNode node = (TypeTreeNode) getChildAt(i);
			if (!node.isChecked()) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean noChildrenChecked() {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			TypeTreeNode node = (TypeTreeNode) getChildAt(i);
			if (node.isChecked()) {
				return false;
			}
		}
		return true;
	}
}
