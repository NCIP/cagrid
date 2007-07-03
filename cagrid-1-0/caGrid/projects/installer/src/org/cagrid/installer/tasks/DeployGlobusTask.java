/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;
import java.util.Properties;

import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.Utils;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class DeployGlobusTask extends CaGridInstallerAntTask {

	/**
	 * 
	 */
	public DeployGlobusTask(String name, String description) {
		super(name, description, null);
	}

	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {

		boolean secure = "true".equals(state
				.get(Constants.USE_SECURE_CONTAINER));

		if (!secure) {
			setStepCount(2);
			new AntTask("", "", "globus-deploy-tomcat", env, sysProps)
					.execute(state);
			setLastStep(1);
			new AntTask("", "", "fix-web-xml", env, sysProps).execute(state);
			setLastStep(2);
		} else {
			setStepCount(6);
			new AntTask("", "", "globus-deploy-secure-tomcat", env, sysProps)
					.execute(state);
			setLastStep(1);
			new AntTask("", "", "insert-secure-connector", env, sysProps)
					.execute(state);
			setLastStep(2);
			new AntTask("", "", "insert-valve", env, sysProps).execute(state);
			setLastStep(3);
			new AntTask("", "", "set-global-cert-and-key-paths", env, sysProps)
					.execute(state);
			setLastStep(4);
			new AntTask("", "", "copy-global-cert-and-key").execute(state);
			setLastStep(5);
			new AntTask("", "", "fix-secure-web-xml", env, sysProps)
					.execute(state);
		}
		new AntTask("", "", "configure-tomcat-ports").execute(state);

		return null;
	}

}
