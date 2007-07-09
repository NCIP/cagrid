/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;

import org.cagrid.installer.util.InstallerUtils;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class CaGridInstallerAntTask extends CaGridAntTask {

	/**
	 * @param name
	 * @param description
	 * @param targetName
	 */
	public CaGridInstallerAntTask(String name, String description,
			String targetName) {
		super(name, description, targetName);
	}

	
	protected String getBuildFilePath(Map state){
		return InstallerUtils.getScriptsBuildFilePath();
	}
}
