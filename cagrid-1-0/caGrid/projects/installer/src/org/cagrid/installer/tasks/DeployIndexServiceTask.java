package org.cagrid.installer.tasks;

import org.cagrid.installer.model.CaGridInstallerModel;

public class DeployIndexServiceTask extends DeployServiceTask{

	
	
	public DeployIndexServiceTask(String name, String description, String serviceName, CaGridInstallerModel model) {
		super(name, description, serviceName, model);
		// TODO Auto-generated constructor stub
	}

	protected String getDeployTomcatTarget(){
		return "deployIndexTomcat";
	}
	
	protected String getDeployGlobusTarget(){
		return "deployIndexGlobus";
	}
}
