/**
 * 
 */
package org.cagrid.installer.steps;

import javax.swing.Icon;

import org.cagrid.installer.util.InstallerUtils;


/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class ServicePropertiesWorkflowFileEditorStep extends
		DeployPropertiesFileEditorStep {

	/**
	 * 
	 */
	public ServicePropertiesWorkflowFileEditorStep() {

	}

	/**
	 * @param serviceName
	 * @param name
	 * @param summary
	 * @param propertyNameColumnName
	 * @param propertyValueColumnValue
	 */
	public ServicePropertiesWorkflowFileEditorStep(String serviceName, String name,
			String summary, String propertyNameColumnName,
			String propertyValueColumnValue) {
		super(serviceName, name, summary, propertyNameColumnName,
				propertyValueColumnValue);

	}

	/**
	 * @param serviceName
	 * @param name
	 * @param summary
	 * @param propertyNameColumnName
	 * @param propertyValueColumnValue
	 * @param icon
	 */
	public ServicePropertiesWorkflowFileEditorStep(String serviceName, String name,
			String summary, String propertyNameColumnName,
			String propertyValueColumnValue, Icon icon) {
		super(serviceName, name, summary, propertyNameColumnName,
				propertyValueColumnValue, icon);

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cagrid.installer.steps.AbstractPropertiesFileEditorStep#getPropertyFilePath()
	 */
	@Override
	protected String getPropertyFilePath() {
		return InstallerUtils.getServiceDestDir(this.model.getState()) + "/"
				+ this.serviceName + "/WorkflowFactoryService" +"/service.properties";
	}

}
