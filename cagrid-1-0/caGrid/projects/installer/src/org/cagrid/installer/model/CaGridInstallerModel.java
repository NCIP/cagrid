/**
 * 
 */
package org.cagrid.installer.model;

import java.util.Map;

import org.pietschy.wizard.WizardModel;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public interface CaGridInstallerModel extends WizardModel {

//	Map getState();
	
	Map<String,String> getStateMap();
	
	void unsetProperty(String propName);
	
	void setProperty(String propName, String propValue);
	
	String getProperty(String propName);
	
	String getProperty(String propName, String defaultValue);	
	
	String getMessage(String key);

	boolean isTomcatConfigurationRequired();

	boolean isSecurityConfigurationRequired();

	boolean isTrue(String propName);

	boolean isTomcatContainer();

	boolean isSet(String propName);

	boolean isCAGenerationRequired();

	boolean isServiceCertGenerationRequired();

	boolean isAuthnSvcCAGenerationRequired();



	boolean isEqual(String value, String propName2);

	boolean isConfigureGlobusRequired();

	boolean isDeployGlobusRequired();

	void setDeactivatePrevious(boolean b);
	
	String getServiceDestDir();

	boolean isSecureContainerRequired();

	boolean isConfigureContainerSelected();
}
