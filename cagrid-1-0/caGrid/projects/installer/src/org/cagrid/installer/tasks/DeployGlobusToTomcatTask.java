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
public class DeployGlobusToTomcatTask extends CaGridInstallerAntTask {

	/**
	 * 
	 */
	public DeployGlobusToTomcatTask(String name, String description) {
		super(name, description, null);
	}

	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {

		boolean secure = "true".equals(state
				.get(Constants.USE_SECURE_CONTAINER));

		setStepCount(1);
		if (!secure) {
			new AntTask("", "", "globus-deploy-tomcat", env, sysProps)
					.execute(state);
		} else {
			new AntTask("", "", "globus-deploy-secure-tomcat", env, sysProps)
					.execute(state);
		}
		

		return null;
	}

}
