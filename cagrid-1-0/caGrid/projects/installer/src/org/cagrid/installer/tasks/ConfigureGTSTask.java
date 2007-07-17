/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.InstallerUtils;

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

		m.put(Constants.BUILD_FILE_PATH, InstallerUtils.getScriptsBuildFilePath());
		new AntTask("", "", "configure-gts-conf", env, sysProps).execute(m);
		

		m.put(Constants.BUILD_FILE_PATH, InstallerUtils.getServiceDestDir(m) + "/gts/build.xml");
		m.put("gridId.input", state.get(Constants.GTS_ADMIN_IDENT));
		new AntTask("", "", "addAdmin", env, sysProps).execute(m);
		
		return null;
	}

}
