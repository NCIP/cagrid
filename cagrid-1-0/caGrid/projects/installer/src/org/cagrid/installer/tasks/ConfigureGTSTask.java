/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.Utils;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ConfigureGTSTask extends CaGridAntTask {

	/**
	 * @param name
	 * @param description
	 * @param targetName
	 */
	public ConfigureGTSTask(String name, String description) {
		super(name, description, null);
	}

	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {

		Map m = new HashMap(state);

		m.put(Constants.BUILD_FILE_PATH, state.get(Constants.CAGRID_HOME)
				+ "/projects/installer/deployer/build.xml");
		
		new AntTask("", "", "configure-gts-conf", env, sysProps).execute(m);
		Utils.copyCACertToTrustStore((String) state.get(Constants.CA_CERT_PATH));
		
		
		return null;
	}

}
