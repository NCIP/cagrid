/**
 * 
 */
package org.cagrid.installer.steps;

import javax.swing.Icon;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class CheckReInstallStep extends PropertyConfigurationStep {

	private String homeProp;

	/**
	 * 
	 */
	public CheckReInstallStep() {

	}

	/**
	 * @param name
	 * @param description
	 */
	public CheckReInstallStep(String name, String description, String homeProp) {
		this(name, description, homeProp, null);
	}

	/**
	 * @param name
	 * @param description
	 * @param icon
	 */
	public CheckReInstallStep(String name, String description, String homeProp,
			Icon icon) {
		super(name, description, icon);
		this.homeProp = homeProp;
	}

	public void prepare() {
		setSummary(getSummary() + " (" + this.model.getMessage("installed.at")
				+ " '" + this.model.getProperty(this.homeProp) + "')");
	}

}
