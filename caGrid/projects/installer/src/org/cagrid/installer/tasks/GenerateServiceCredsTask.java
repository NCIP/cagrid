/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;

import org.cagrid.installer.steps.Constants;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class GenerateServiceCredsTask extends CaGridAntTask {

	/**
	 * @param name
	 * @param description
	 */
	public GenerateServiceCredsTask(String name, String description) {
		super(name, description, "generate-host-creds");
	}

	protected String getBuildFilePath(Map state) {
		return state.get(Constants.CAGRID_HOME)
				+ "/projects/installer/deployer/build.xml";
	}
}
