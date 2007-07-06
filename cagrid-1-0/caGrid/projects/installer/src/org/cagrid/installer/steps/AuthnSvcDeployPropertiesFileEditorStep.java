/**
 * 
 */
package org.cagrid.installer.steps;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;

import org.pietschy.wizard.InvalidStateException;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class AuthnSvcDeployPropertiesFileEditorStep extends
		DeployPropertiesFileEditorStep {

	private Properties myProps = new Properties();
	
	/**
	 * 
	 */
	public AuthnSvcDeployPropertiesFileEditorStep() {
	
	}

	/**
	 * @param serviceName
	 * @param name
	 * @param summary
	 * @param propertyNameColumnName
	 * @param propertyValueColumnValue
	 */
	public AuthnSvcDeployPropertiesFileEditorStep(String serviceName,
			String name, String summary, String propertyNameColumnName,
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
	public AuthnSvcDeployPropertiesFileEditorStep(String serviceName,
			String name, String summary, String propertyNameColumnName,
			String propertyValueColumnValue, Icon icon) {
		super(serviceName, name, summary, propertyNameColumnName,
				propertyValueColumnValue, icon);
	
	}
	
	protected Properties loadProperties(){
		Properties props = super.loadProperties();
		this.myProps.setProperty("csm.app.context", (String)props.remove("csm.app.context"));
		this.myProps.setProperty("saml.provider.crt", (String)props.remove("saml.provider.crt"));
		this.myProps.setProperty("saml.provider.key", (String)props.remove("saml.provider.key"));
		this.myProps.setProperty("saml.provider.pwd", (String)props.remove("saml.provider.pwd"));
		return props;
	}
	
	protected void storeProperties(Properties props) throws InvalidStateException{
		props.setProperty("csm.app.context", (String)this.myProps.getProperty("csm.app.context"));
		props.setProperty("saml.provider.crt", (String)this.myProps.getProperty("saml.provider.crt"));
		props.setProperty("saml.provider.key", (String)this.myProps.getProperty("saml.provider.key"));
		props.setProperty("saml.provider.pwd", (String)this.myProps.getProperty("saml.provider.pwd"));
		super.storeProperties(props);
	}
	

}
