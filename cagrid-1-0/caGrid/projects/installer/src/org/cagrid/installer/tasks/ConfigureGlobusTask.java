/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;

import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.InstallerUtils;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ConfigureGlobusTask extends CaGridAntTask {

	/**
	 * @param name
	 * @param description
	 * @param targetName
	 */
	public ConfigureGlobusTask(String name, String description) {
		super(name, description, "configure-security-descriptor");
	}

	protected String getBuildFilePath(Map state) {
		return InstallerUtils.getScriptsBuildFilePath();
	}

}
