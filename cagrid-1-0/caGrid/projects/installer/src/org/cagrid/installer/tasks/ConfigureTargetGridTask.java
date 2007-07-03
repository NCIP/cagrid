/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;

import org.cagrid.installer.steps.Constants;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class ConfigureTargetGridTask extends CaGridAntTask {

	/**
	 * @param name
	 * @param description
	 * @param targetName
	 */
	public ConfigureTargetGridTask(String name, String description) {
		super(name, description, "configure");
	}
	
	protected String getBuildFilePath(Map state){
		return (String)state.get(Constants.CAGRID_HOME) + "/build.xml";
	}

}
