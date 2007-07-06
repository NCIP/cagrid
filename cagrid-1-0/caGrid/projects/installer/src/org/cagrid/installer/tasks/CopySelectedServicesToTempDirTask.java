/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.Utils;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class CopySelectedServicesToTempDirTask extends CaGridAntTask {

	/**
	 * @param name
	 * @param description
	 * @param targetName
	 */
	public CopySelectedServicesToTempDirTask(String name, String description) {
		super(name, description, "copy-selected-services");
	}

	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {
		List<String> selectedServices = new ArrayList<String>();
		
		if("true".equals(state.get(Constants.INSTALL_DORIAN))){
			selectedServices.add("dorian");
		}
		if("true".equals(state.get(Constants.INSTALL_GTS))){
			selectedServices.add("gts");
		}
		if("true".equals(state.get(Constants.INSTALL_AUTHN_SVC))){
			selectedServices.add("authentication-service");
		}
		if("true".equals(state.get(Constants.INSTALL_GRID_GROUPER))){
			selectedServices.add("gridgrouper");
		}
		if("true".equals(state.get(Constants.INSTALL_INDEX_SVC))){
			selectedServices.add("index");
		}
		if("true".equals(state.get(Constants.INSTALL_GME))){
			selectedServices.add("gme");
		}
		if("true".equals(state.get(Constants.INSTALL_EVS))){
			selectedServices.add("evs");
		}
		if("true".equals(state.get(Constants.INSTALL_FQP))){
			selectedServices.add("fqp");
		}
		if("true".equals(state.get(Constants.INSTALL_CADSR))){
			selectedServices.add("cadsr");
		}
		if("true".equals(state.get(Constants.INSTALL_WORKFLOW))){
			selectedServices.add("workflow");
		}
		
		StringBuilder sb = new StringBuilder();
		for(Iterator i = selectedServices.iterator(); i.hasNext();){
			sb.append((String)i.next());
			if(i.hasNext()){
				sb.append(",");
			}
		}
		sysProps.setProperty("selected.services", sb.toString());

		new AntTask("", "", target, env, sysProps).execute(state);

		return null;
	}

	protected String getBuildFilePath(Map state) {
		return Utils.getScriptsBuildFilePath();
	}

}
