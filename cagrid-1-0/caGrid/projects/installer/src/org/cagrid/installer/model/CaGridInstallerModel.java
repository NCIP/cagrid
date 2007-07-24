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

	Map getState();
	
	String getMessage(String key);

	boolean isTomcatConfigurationRequired();

	boolean isSecurityConfigurationRequired();

	boolean isTrue(String propName);

	boolean isTomcatContainer();

	boolean isSet(String propName);

	boolean isCAGenerationRequired();

	boolean isServiceCertGenerationRequired();

	boolean isAuthnSvcCAGenerationRequired();

	String getProperty(String propName);

	boolean isEqual(String value, String propName2);

	boolean isConfigureGlobusRequired();

	boolean isDeployGlobusRequired();

	void setDeactivatePrevious(boolean b);

	
}
