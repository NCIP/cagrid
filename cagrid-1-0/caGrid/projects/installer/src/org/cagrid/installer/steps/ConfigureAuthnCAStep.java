/**
 * 
 */
package org.cagrid.installer.steps;

import javax.swing.Icon;

import org.pietschy.wizard.InvalidStateException;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ConfigureAuthnCAStep extends ConfigureIdPServiceCAStep {

	/**
	 * 
	 */
	public ConfigureAuthnCAStep() {

	}

	/**
	 * @param name
	 * @param description
	 */
	public ConfigureAuthnCAStep(String name, String description) {
		super(name, description);
	}

	/**
	 * @param name
	 * @param description
	 * @param icon
	 */
	public ConfigureAuthnCAStep(String name, String description, Icon icon) {
		super(name, description, icon);

	}

	public void prepare() {
		this
				.prePopulateCommonCAFields(Constants.AUTHN_SVC_USE_GEN_CA,
						Constants.AUTHN_SVC_CA_CERT_PATH,
						Constants.AUTHN_SVC_CA_KEY_PATH,
						Constants.AUTHN_SVC_CA_KEY_PWD);
	}

	public void applyState() throws InvalidStateException {
		super.applyState();
		ensureAbsolutePaths(Constants.AUTHN_SVC_CA_CERT_PATH,
				Constants.AUTHN_SVC_CA_KEY_PATH);
	}

}
