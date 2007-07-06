/**
 * 
 */
package org.cagrid.installer.steps;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.util.Utils;
import org.pietschy.wizard.InvalidStateException;
import org.pietschy.wizard.PanelWizardStep;
import org.pietschy.wizard.WizardModel;
import org.pietschy.wizard.models.Condition;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class DeployPropertiesGMEFileEditorStep extends PanelWizardStep
		implements Condition {

	private static final Log logger = LogFactory
			.getLog(DeployPropertiesGMEFileEditorStep.class);

	protected CaGridInstallerModel model;
	
	protected String serviceName;

	private String propertyNameColumnName;

	private String propertyValueColumnValue;

	private JTable table;

	/**
	 * 
	 */
	public DeployPropertiesGMEFileEditorStep() {

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DeployPropertiesGMEFileEditorStep(String serviceName,String name, String summary,
			String propertyNameColumnName, String propertyValueColumnValue) {
		super(name, summary);
		this.propertyNameColumnName = propertyNameColumnName;
		this.propertyValueColumnValue = propertyValueColumnValue;
		this.serviceName = serviceName;	
	}

	
	
	
	
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public DeployPropertiesGMEFileEditorStep(String serviceName,String name, String summary,
			String propertyNameColumnName, String propertyValueColumnValue,
			Icon icon) {
		super(name, summary, icon);
		this.propertyNameColumnName = propertyNameColumnName;
		this.propertyValueColumnValue = propertyValueColumnValue;
		this.serviceName = serviceName;
	}

	public void init(WizardModel m) {

		this.model = (CaGridInstallerModel) m;

		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.weightx = 1.0D;
		gridBagConstraints1.weighty = 1.0D;
		gridBagConstraints1.gridy = 1;
		this.setLayout(new GridBagLayout());
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new GridBagLayout());
		this.add(tablePanel, gridBagConstraints1);
		this.table = new JTable();
		this.table.setPreferredScrollableViewportSize(new Dimension(500, 200));		
		tablePanel.add(new JScrollPane(this.table));
	}

	public void prepare() {

		Properties props = new Properties();
		try {
			props.load(new FileInputStream(getPropertyFilePath()));
		} catch (Exception ex) {
			throw new RuntimeException("Error loading props", ex);
		}

		Enumeration propNames = props.propertyNames();
		boolean isServiceDeployHost = false;

		while (propNames.hasMoreElements()) {
			String propName = (String) propNames.nextElement();
			//Check if service.deployment.host property is available.
			if(propName.equalsIgnoreCase("service.deployment.host")){
				isServiceDeployHost=true;
			}
		}
		propNames = props.propertyNames();
		Object[][] data;
		if(isServiceDeployHost){
			data= new Object[props.size()][2];
		}else{
			data= new Object[props.size()+1][2];
		}
		int idx = 0;
		while (propNames.hasMoreElements()) {
			String propName = (String) propNames.nextElement();
			data[idx][0] = propName;
			data[idx][1] = props.getProperty(propName);
			idx++;
		}
		if(!isServiceDeployHost){
			data[idx][0]="service.deployment.host";
			data[idx][1]="localhost";
		}

		this.table.setModel(new DefaultTableModel(data, new String[] {
				this.propertyNameColumnName, this.propertyValueColumnValue }));

		checkComplete();
	}

	protected void checkComplete() {
		setComplete(true);
	}

	protected  String getPropertyFilePath(){
		return Utils.getServiceDestDir(this.model.getState()) + "/"
		+ this.serviceName + "/deploy.properties";
	}

	public void applyState() throws InvalidStateException {
		Properties props = new Properties();
		int rowCount = this.table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			props.setProperty((String) table.getValueAt(i, 0), (String) table
					.getValueAt(i, 1));
		}
		try {
			props.store(new FileOutputStream(getPropertyFilePath()), "");
		} catch (Exception ex) {
			throw new InvalidStateException("Error saving properties to '"
					+ getPropertyFilePath() + "': " + ex.getMessage(), ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pietschy.wizard.models.Condition#evaluate(org.pietschy.wizard.WizardModel)
	 */
	public boolean evaluate(WizardModel m) {
		boolean b = false;
		String path = getPropertyFilePath();
		logger.info("Checking if '" + path
				+ "' exists and contains properties.");
		File f = new File(getPropertyFilePath());
		if (f.exists()) {
			try {
				Properties p = new Properties();
				p.load(new FileInputStream(f));
				logger.info("file contains " + p.size() + " properties");
				b = p.size() > 0;
			} catch (Exception ex) {
				throw new RuntimeException("Error loading properties file '"
						+ f.getAbsolutePath() + "': " + ex.getMessage(), ex);
			}
		} else {
			logger.info("'" + path + "' does not exist.");
		}
		if (!b) {
			logger.debug(this.getName() + " will not be displayed");
		} else {
			logger.debug(this.getName() + " will be displayed");
		}
		return b;
	}

}
