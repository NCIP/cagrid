/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;
import java.util.Properties;

import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.InstallerUtils;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class DeployAuthenticationServiceTask extends DeployServiceTask {

	/**
	 * @param name
	 * @param description
	 * @param serviceName
	 * @param model
	 */
	public DeployAuthenticationServiceTask(String name, String description,
			CaGridInstallerModel model) {
		super(name, description, "authentication-service", model);
	}

	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {

		String svcBuildFilePath = getBuildFilePath(state);
		String installerBuildFilePath = InstallerUtils.getScriptsBuildFilePath();

		// Modify deploy.properties
		state.put(Constants.BUILD_FILE_PATH, installerBuildFilePath);
		new AntTask("", "", "set-authn-service-deploy-properties", env,
				sysProps).execute(state);

		// Deploy the service
		state.put(Constants.BUILD_FILE_PATH, svcBuildFilePath);
		super.runAntTask(state, target, env, sysProps);

		// Copy driver
		state.put(Constants.BUILD_FILE_PATH, installerBuildFilePath);
		if (isDeployTomcat()) {
			new AntTask("", "", "copy-jdbc-driver-to-tomcat", env, sysProps)
					.execute(state);
		} else {
			new AntTask("", "", "copy-jdbc-driver-to-globus", env, sysProps)
					.execute(state);
		}

		// Generate JAAS config
		sysProps.setProperty("jaas.config.path", System
				.getProperty("user.home")
				+ "/.java.login.config");
		sysProps
				.getProperty(
						"authn.svc.rdbms.encryption.enabled",
						"true"
								.equals(state
										.get(Constants.AUTHN_SVC_RDBMS_ENCRYPTION_ENABLED)) ? "YES"
								: "NO");
		new AntTask("", "", "create-jaas-config", env, sysProps).execute(state);

		return null;
	}

}
