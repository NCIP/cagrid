/**
 * 
 */
package org.cagrid.installer.steps;

import java.util.Properties;

import javax.swing.Icon;

import org.cagrid.installer.util.InstallerUtils;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class DeployPropertiesGMEFileEditorStep extends DeployPropertiesFileEditorStep {


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
		this(serviceName, name, summary, propertyNameColumnName, propertyValueColumnValue, null);
	}

	
	
	
	
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public DeployPropertiesGMEFileEditorStep(String serviceName,String name, String summary,
			String propertyNameColumnName, String propertyValueColumnValue,
			Icon icon) {
		super(serviceName, name, summary, propertyNameColumnName, propertyValueColumnValue, icon);
	}

	protected Properties loadProperties(){
		Properties props = super.loadProperties();
		String defaultHost = (String)props.remove("service.deployment.host.default");
		if(InstallerUtils.isEmpty(defaultHost)){
			defaultHost = props.getProperty("service.deployment.host", "localhost");
		}
		props.setProperty("service.deployment.host", defaultHost);
		return props;
	}
	
}
