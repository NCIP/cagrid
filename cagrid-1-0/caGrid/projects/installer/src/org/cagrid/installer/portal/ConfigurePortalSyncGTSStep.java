/**
 * 
 */
package org.cagrid.installer.portal;

import javax.swing.Icon;

import org.cagrid.installer.syncgts.ConfigureSyncGTSStep;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class ConfigurePortalSyncGTSStep extends ConfigureSyncGTSStep {

	/**
	 * 
	 */
	public ConfigurePortalSyncGTSStep() {

	}

	/**
	 * @param name
	 * @param description
	 */
	public ConfigurePortalSyncGTSStep(String name, String description) {
		super(name, description);

	}

	/**
	 * @param name
	 * @param description
	 * @param icon
	 */
	public ConfigurePortalSyncGTSStep(String name, String description, Icon icon) {
		super(name, description, icon);

	}

}
