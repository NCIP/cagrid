package gov.nih.nci.cagrid.dorian.ifs.portal;

import gov.nih.nci.cagrid.common.portal.PortalBaseTable;
import gov.nih.nci.cagrid.dorian.ifs.bean.IFSUser;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: UsersTable.java,v 1.7 2006-03-02 17:56:52 langella Exp $
 */
public class UsersTable extends PortalBaseTable {
	public final static String USER = "user";

	public final static String IDP = "IdP Id";
	
	public final static String UID = "User Id";

	public final static String GRID_IDENTITY = "Grid Identity";

	public final static String EMAIL = "Email";

	public final static String STATUS = "Status";

	public final static String ROLE = "Role";
	
	UserManagerWindow window;

	public UsersTable(UserManagerWindow window) {
		super(createTableModel());
		this.window = window;
		TableColumn c = this.getColumn(USER);
		c.setMaxWidth(0);
		c.setMinWidth(0);
		c.setPreferredWidth(0);
		c.setResizable(false);
		
		c = this.getColumn(IDP);
		c.setMaxWidth(35);
		c.setMinWidth(35);
		c.setPreferredWidth(0);
		
		c = this.getColumn(GRID_IDENTITY);
		c.setMinWidth(350);
		c.setPreferredWidth(0);

		this.clearTable();

	}

	public static DefaultTableModel createTableModel() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(USER);
		model.addColumn(IDP);
		model.addColumn(UID);
		model.addColumn(GRID_IDENTITY);
		model.addColumn(EMAIL);
		model.addColumn(STATUS);
		model.addColumn(ROLE);
		return model;

	}

	public void addUser(final IFSUser u) {
		Vector v = new Vector();
		v.add(u);
		v.add(String.valueOf(u.getIdPId()));
		v.add(String.valueOf(u.getUID()));
		v.add(u.getGridId());
		v.add(u.getEmail());
		v.add(u.getUserStatus().getValue());
		v.add(u.getUserRole().getValue());
		addRow(v);
	}

	public synchronized IFSUser getSelectedUser() throws Exception{
		int row = getSelectedRow();
		if ((row >= 0) && (row < getRowCount())) {
			return (IFSUser) getValueAt(row, 0);
		} else {
			throw new Exception("Please select a user!!!");
		}
	}
	
	public synchronized void removeSelectedUser() throws Exception{
		int row = getSelectedRow();
		if ((row >= 0) && (row < getRowCount())) {
			removeRow(row);
		} else {
			throw new Exception("Please select a user!!!");
		}
	}

	public void doubleClick() throws Exception {
		int row = getSelectedRow();
		if ((row >= 0) && (row < getRowCount())) {
			window.showUser();
		} else {
			throw new Exception(
					"No user selected, please select a user!!!");
		}

	}

	public void singleClick() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	

}