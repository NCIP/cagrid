/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;

import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.steps.Constants;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class DeployMyServiceTask extends DeployServiceTask {

	/**
	 * @param name
	 * @param description
	 * @param serviceName
	 * @param model
	 */
	public DeployMyServiceTask(String name, String description,
			String serviceName, CaGridInstallerModel model) {
		super(name, description, serviceName, model);
	}

	protected String getBuildFilePath(Map state) {
		return this.model.getProperty(Constants.MY_SERVICE_DIR) + "/build.xml";
	}

}
