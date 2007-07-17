/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;
import java.util.Properties;

import org.cagrid.installer.steps.Constants;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class ConfigureTomcatTask extends CaGridInstallerAntTask {

	/**
	 * @param name
	 * @param description
	 * @param targetName
	 */
	public ConfigureTomcatTask(String name, String description) {
		super(name, description, null);
	}
	
	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {

		boolean secure = "true".equals(state
				.get(Constants.USE_SECURE_CONTAINER));

		if (!secure) {
			setStepCount(2);
			new AntTask("", "", "fix-web-xml", env, sysProps).execute(state);
			setLastStep(1);
		} else {
			setStepCount(5);
			new AntTask("", "", "insert-secure-connector", env, sysProps)
					.execute(state);
			setLastStep(1);
			new AntTask("", "", "insert-valve", env, sysProps).execute(state);
			setLastStep(2);
			new AntTask("", "", "set-global-cert-and-key-paths", env, sysProps)
					.execute(state);
			setLastStep(3);
			new AntTask("", "", "fix-secure-web-xml", env, sysProps)
					.execute(state);
			setLastStep(4);
			new AntTask("", "", "configure-tomcat-server-config", env, sysProps)
					.execute(state);
		}
		
		return null;
	}

}
