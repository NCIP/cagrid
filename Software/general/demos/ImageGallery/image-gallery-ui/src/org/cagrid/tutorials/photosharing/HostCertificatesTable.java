package org.cagrid.tutorials.photosharing;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.cagrid.gaards.dorian.federation.HostCertificateRecord;
import org.cagrid.grape.table.GrapeBaseTable;

/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: HostCertificatesTable.java,v 1.3 2008-11-20 15:29:42 langella Exp $
 */
public class HostCertificatesTable extends GrapeBaseTable {
	
	private static final long serialVersionUID = 1L;
	
	public final static String HOST_CERTIFICATE_RECORD = "record";

	public final static String HOST = "Host";
	

	public HostCertificatesTable() {
		super(createTableModel());
		TableColumn c = this.getColumn(HOST_CERTIFICATE_RECORD);
		c.setMaxWidth(0);
		c.setMinWidth(0);
		c.setPreferredWidth(0);
		c.setResizable(false);
		this.clearTable();

	}

	public static DefaultTableModel createTableModel() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(HOST_CERTIFICATE_RECORD);
		model.addColumn(HOST);
		return model;

	}

	public void addHostCertificate(final HostCertificateRecord record) {
		Vector v = new Vector();
		v.add(record);
		v.add(record.getHost());
		addRow(v);
	}

	public synchronized HostCertificateRecord getSelectedHostCertificate()
			throws Exception {
		int row = getSelectedRow();
		if ((row >= 0) && (row < getRowCount())) {
			return (HostCertificateRecord) getValueAt(row, 0);
		} else {
			throw new Exception("Please select a host certificate!!!");
		}
	}


	public void doubleClick() throws Exception {
	}

	public void singleClick() throws Exception {
		// TODO Auto-generated method stub

	}

}