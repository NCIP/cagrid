/**
 * 
 */
package org.cagrid.installer.steps;

import javax.swing.Icon;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class ConfigureIdPServiceCAStep extends ConfigureCAStep {

	private static final Log logger = LogFactory.getLog(ConfigureIdPServiceCAStep.class);
	
	/**
	 * 
	 */
	public ConfigureIdPServiceCAStep() {

	}

	/**
	 * @param name
	 * @param description
	 */
	public ConfigureIdPServiceCAStep(String name, String description) {
		super(name, description);

	}

	/**
	 * @param name
	 * @param description
	 * @param icon
	 */
	public ConfigureIdPServiceCAStep(String name, String description, Icon icon) {
		super(name, description, icon);

	}
	
	protected void prePopulateCommonCAFields(String testProp, String caCertPathProp, String caKeyPathProp, String caKeyPwdProp){
		if ("true".equals(this.model.getState()
				.get(testProp))) {
			logger.debug("Setting default cert path and key path to predefined values");

			JTextField certPathField = (JTextField) getOption(caCertPathProp);
			String certPath = (String) this.model.getState().get(
					Constants.CA_CERT_PATH);
			logger.debug("Setting default for " + caCertPathProp + " to " + certPath);
			certPathField.setText(certPath);
			
			JTextField keyPathField = (JTextField) getOption(caKeyPathProp);
			String keyPath = (String) this.model.getState().get(
					Constants.CA_KEY_PATH);
			logger.debug("Setting default for " + caKeyPathProp + " to " + keyPath);
			keyPathField.setText(keyPath);
			
			JTextField keyPwdField = (JTextField) getOption(caKeyPwdProp);
			String keyPwd = (String) this.model.getState().get(
					Constants.CA_KEY_PWD);
			logger.debug("Setting default for " + caKeyPwdProp + " to " + keyPwd);
			keyPwdField.setText(keyPwd);

		}
	}

}
