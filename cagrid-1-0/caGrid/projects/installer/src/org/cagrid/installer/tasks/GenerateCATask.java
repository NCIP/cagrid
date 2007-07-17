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
public class GenerateCATask extends CaGridAntTask {

	/**
	 * @param name
	 * @param description
	 */
	public GenerateCATask(String name, String description) {
		super(name, description, "generate-ca");
	}
	
	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {

		new AntTask("", "", target, env, sysProps).execute(state);
		
		InstallerUtils.copyCACertToTrustStore((String) state.get(Constants.CA_CERT_PATH));

		return null;
	}

	protected String getBuildFilePath(Map state) {
		return InstallerUtils.getScriptsBuildFilePath();
	}
}
