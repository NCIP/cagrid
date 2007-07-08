/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;
import java.util.Properties;

import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.Utils;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class ConfigureGridGrouperTask extends CaGridAntTask {

	/**
	 * @param name
	 * @param description
	 * @param targetName
	 */
	public ConfigureGridGrouperTask(String name, String description
			) {
		super(name, description, null);

	}
	
	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {

		//Configure grouper.hibernate.properties
		state.put(Constants.BUILD_FILE_PATH, Utils.getScriptsBuildFilePath());
		new AntTask("", "", "configure-gridgrouper-hibernate", env, sysProps).execute(state);
		
		
		state.put(Constants.BUILD_FILE_PATH, Utils.getServiceDestDir(state) + "/gridgrouper/build.xml");
		
		//Run grouperInit
		new AntTask("", "", "grouperInit", env, sysProps).execute(state);
		
		//Run addAdmin
		sysProps.setProperty("gridId.input", (String)state.get(Constants.GRID_GROUPER_ADMIN_IDENT));
		new AntTask("", "", "addAdmin", env, sysProps).execute(state);

		return null;
	}

}
