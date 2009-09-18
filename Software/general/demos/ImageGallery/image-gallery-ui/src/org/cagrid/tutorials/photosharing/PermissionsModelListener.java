package org.cagrid.tutorials.photosharing;

import gov.nih.nci.cagrid.common.Utils;

import java.rmi.RemoteException;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.cagrid.demo.photosharing.domain.User;
import org.cagrid.demo.photosharing.gallery.client.GalleryClient;
import org.cagrid.demo.photosharing.gallery.stubs.types.AuthorizationException;
import org.cagrid.demo.photosharing.stubs.types.PhotoSharingException;
import org.cagrid.grape.utils.ErrorDialog;
import org.cagrid.tutorials.photosharing.AccessControlPanel.MyTableModel;

public class PermissionsModelListener implements TableModelListener {

	private GalleryClient client;
	public PermissionsModelListener(GalleryClient client) {
		this.client = client;
	}
	public void tableChanged(TableModelEvent e) {
		if (e.getType() == TableModelEvent.UPDATE) {
			//user clicked a view or add checkbox most likely.. check for it.
			if (e.getColumn() == 1) {
				//they clicked view
				int column = 1;
				//get identity from column 1
				MyTableModel model = (MyTableModel)e.getSource();
				
				int row = e.getFirstRow();
				
				//get the identity from first column
				String identity = (String)model.getValueAt(row, 0);
				User user = new User();
				user.setUserIdentity(identity);
				
				//if the cell is false, remove permissions
				Boolean viewable = (Boolean)model.getValueAt(row, column);
				if (viewable.booleanValue()) {
					try {
						this.client.grantViewGalleryPrivileges(user);
					} catch (AuthorizationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (PhotoSharingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						this.client.revokeViewGalleryPrivileges(user);
					} catch (AuthorizationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (PhotoSharingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else if (e.getColumn() == 2) {
				//get identity from column 1
				int column = 2;
				MyTableModel model = (MyTableModel)e.getSource();
				
				int row = e.getFirstRow();
				
				//get the identity from first column
				String identity = (String)model.getValueAt(row, 0);
				User user = new User();
				user.setUserIdentity(identity);
				
				//if the cell is false, remove permissions
				Boolean viewable = (Boolean)model.getValueAt(row, column);
				if (viewable.booleanValue()) {
					try {
						this.client.grantAddImagePrivileges(user);
					} catch (Exception e1) {
					    ErrorDialog.showError(Utils.getExceptionMessage(e1),e1);
					}
				} else {
					try {
						this.client.revokeAddImagePrivileges(user);
					} catch (Exception e1) {
					    ErrorDialog.showError(Utils.getExceptionMessage(e1),e1);
					}
				}
			}
		}

	}

}
