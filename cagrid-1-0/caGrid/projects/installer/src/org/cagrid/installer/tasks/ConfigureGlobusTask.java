/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;
import java.util.Properties;

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

	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {
		new AntTask("", "", "configure-security-descriptor", env, sysProps)
				.execute(state);
		new AntTask("", "", "configure-globus-server-config", env, sysProps)
				.execute(state);

		return null;
	}

	protected String getBuildFilePath(Map state) {
		return InstallerUtils.getScriptsBuildFilePath();
	}

}
