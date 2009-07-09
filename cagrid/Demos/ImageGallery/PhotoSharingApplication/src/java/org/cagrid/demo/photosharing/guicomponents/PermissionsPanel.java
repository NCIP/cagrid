package org.cagrid.demo.photosharing.guicomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.cagrid.demo.photosharing.domain.User;
import org.cagrid.demo.photosharing.gallery.client.GalleryClient;
import org.cagrid.demo.photosharing.stubs.types.PhotoSharingException;

public class PermissionsPanel {

	private GalleryClient client;
	public PermissionsPanel(GalleryClient client) {
		this.client = client;
	}
	
	public JPanel getPermissionsPanel() {
		JPanel panel = new JPanel();
		
		MyTableModel model = new MyTableModel();
		JTable table = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(table);

		panel.add(scrollPane);
		JButton refreshButton = new JButton("refresh");
		refreshButton.addActionListener(new RefreshButtonActionListener(model, this.client));
		panel.add(refreshButton);
		
		return panel;
	}
	
	static class RefreshButtonActionListener implements ActionListener {
		
		private MyTableModel model;
		private GalleryClient client;
		public RefreshButtonActionListener(MyTableModel model, GalleryClient client) {
			this.model = model;
			this.client = client;
		}
		public void actionPerformed(ActionEvent e) {
			//update the model
			//retrieve permissions from service
			try {
				User[] viewers = this.client.listAllUsersWithViewPrivileges();
				User[] adders = this.client.listUsersWithAddPrivileges();
				
				Set<UserPermission> permissionSet = new HashSet<UserPermission>();
				
				for (User viewer : viewers) {
					UserPermission cur = new UserPermission();
					cur.setIdentity(viewer.getUserIdentity());
					if (!(permissionSet.contains(cur))) {
						//UserPermission to list
						cur.setView(Boolean.TRUE);
						permissionSet.add(cur);
					} else {
						//set view permission on existing UserPermission object to true
						Iterator i = permissionSet.iterator();
						while (i.hasNext()) {
							UserPermission p = (UserPermission)i.next();
							if (p.equals(cur)) {
								p.setView(Boolean.TRUE);
							}
						}
					}
				}

				for (User adder : adders) {
					UserPermission cur = new UserPermission();
					cur.setIdentity(adder.getUserIdentity());
					if (!(permissionSet.contains(cur))) {
						//UserPermission to list
						cur.setAdd(Boolean.TRUE);
						permissionSet.add(cur);
					} else {
						//set view permission on existing UserPermission object to true
						Iterator i = permissionSet.iterator();
						while (i.hasNext()) {
							UserPermission p = (UserPermission)i.next();
							if (p.equals(cur)) {
								p.setAdd(Boolean.TRUE);
							}
						}
					}
				}

				this.model.setUserPermissions(new ArrayList(permissionSet));
			} catch (PhotoSharingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	static class UserPermission {
		
		public UserPermission() {
			this.identity = null;
			this.view = Boolean.FALSE;
			this.add = Boolean.FALSE;
		}
		public String getIdentity() {
			return identity;
		}
		public void setIdentity(String identity) {
			this.identity = identity;
		}
		public Boolean isView() {
			return view;
		}
		public void setView(Boolean view) {
			this.view = view;
		}
		public Boolean isAdd() {
			return add;
		}
		public void setAdd(Boolean add) {
			this.add = add;
		}
		private String identity;
		private Boolean view;
		private Boolean add;
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof UserPermission)) {
				return false;
			}
			
			UserPermission permission = (UserPermission)obj;
			
			return this.identity.equals(permission.identity);
		}
		
		@Override
		public int hashCode() {
			return this.identity.hashCode();
		}
	}
	class MyTableModel extends AbstractTableModel {
		
		public MyTableModel() {
			this.userPermissions = new ArrayList<UserPermission>();
		}
	    private String[] columnNames = new String[] { "Identity", "View Images", "Add Images" };
	    private List<UserPermission> userPermissions;
	    
	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return userPermissions.size();
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	    	if (col == 0) {
	    		return userPermissions.get(row).getIdentity();
	    	} else if (col == 1) {
	    		return userPermissions.get(row).isView();
	    	} else { //  if (col == 2) {      NOTE: only have 3 columns total
	    		return userPermissions.get(row).isAdd();
	    	}
	    }

	    public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }

	    /*
	     * Don't need to implement this method unless your table's
	     * editable.
	     */
	    public boolean isCellEditable(int row, int col) {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.
	        if (col < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }

	    /*
	     * Don't need to implement this method unless your table's
	     * data can change.
	     */
	    public void setValueAt(Object value, int row, int col) {
	        if (col == 0) {
	        	this.userPermissions.get(row).setIdentity((String)value);
	        } else if (col == 1) {
	        	this.userPermissions.get(row).setView((Boolean)value);
	        } else { //if (col == 2)
	        	this.userPermissions.get(row).setView((Boolean)value);
	        }
	        fireTableCellUpdated(row, col);
	    }
	    
	    //CALL THIS ONLY FROM SWING THREAD
	    public void addUserPermission(UserPermission userPermission) {
	    	this.userPermissions.add(userPermission);
	    }
	    
	    //CALL THIS ONLY FROM SWING THREAD
	    public void removeUserPermission(UserPermission userPermission) {
	    	this.userPermissions.remove(userPermission);
	    }
	    
	    public List<UserPermission> getUserPermissions() {
	    	return this.userPermissions;
	    }
	    
	    //CALL THIS ONLY FROM SWING THREAD
	    public void setUserPermissions(List<UserPermission> newPermissions) {
	    	this.userPermissions = newPermissions;
	    	this.fireTableStructureChanged();
	    }
	}

}
