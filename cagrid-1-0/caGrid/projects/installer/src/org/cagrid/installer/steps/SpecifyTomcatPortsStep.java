/**
 * 
 */
package org.cagrid.installer.steps;

import javax.swing.Icon;
import javax.swing.JTextField;

import org.pietschy.wizard.InvalidStateException;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class SpecifyTomcatPortsStep extends PropertyConfigurationStep {

	/**
	 * 
	 */
	public SpecifyTomcatPortsStep() {

	}

	/**
	 * @param name
	 * @param description
	 */
	public SpecifyTomcatPortsStep(String name, String description) {
		super(name, description);
	}

	/**
	 * @param name
	 * @param description
	 * @param icon
	 */
	public SpecifyTomcatPortsStep(String name, String description, Icon icon) {
		super(name, description, icon);
	}
	
	public void prepare(){
		JTextField httpsPortField = (JTextField)getOption(Constants.TOMCAT_HTTPS_PORT);
		if(!"true".equals(this.model.getState().get(Constants.USE_SECURE_CONTAINER))){
			httpsPortField.setVisible(false);
		}else{
			httpsPortField.setVisible(true);
		}
	}
	
	public void applyState() throws InvalidStateException {
		
		String oldTomcatHttpPort = (String)this.model.getState().get(Constants.TOMCAT_HTTP_PORT);
		if(oldTomcatHttpPort == null){
			oldTomcatHttpPort = "8080";
		}
		this.model.getState().put(Constants.TOMCAT_OLD_HTTP_PORT, oldTomcatHttpPort);
		
		String oldTomcatHttpsPort = (String)this.model.getState().get(Constants.TOMCAT_HTTPS_PORT);
		if(oldTomcatHttpsPort == null){
			oldTomcatHttpsPort = "8443";
		}
		this.model.getState().put(Constants.TOMCAT_OLD_HTTPS_PORT, oldTomcatHttpsPort);
		
		super.applyState();
		
	}

}
