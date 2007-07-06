/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;
import java.util.Properties;

import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.Utils;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class DeployServiceTask extends CaGridAntTask {

	private String serviceName;

	protected CaGridInstallerModel model;

	/**
	 * @param name
	 * @param description
	 */
	public DeployServiceTask(String name, String description,
			String serviceName, CaGridInstallerModel model) {
		super(name, description, null);
		this.model = model;
		this.serviceName = serviceName;
	}

	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {
		String antTarget = getDeployTomcatTarget();
		if (!isDeployTomcat()) {
			antTarget = getDeployGlobusTarget();
		}
		new AntTask("", "", antTarget, env, sysProps).execute(state);

		return null;
	}

	protected boolean isDeployTomcat() {
		return this.model.getMessage("container.type.tomcat").equals(
				this.model.getState().get(Constants.CONTAINER_TYPE));
	}

	protected String getDeployTomcatTarget() {
		return "deployTomcat";
	}

	protected String getDeployGlobusTarget() {
		return "deployGlobus";
	}

	protected String getBuildFilePath(Map state) {
		return Utils.getServiceDestDir(state) + "/" + this.serviceName
				+ "/build.xml";
	}

}
