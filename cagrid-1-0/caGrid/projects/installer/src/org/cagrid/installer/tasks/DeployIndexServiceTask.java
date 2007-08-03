package org.cagrid.installer.tasks;


public class DeployIndexServiceTask extends DeployServiceTask{

	
	
	public DeployIndexServiceTask(String name, String description, String serviceName) {
		super(name, description, serviceName);
	}

	protected String getDeployTomcatTarget(){
		return "deployIndexTomcat";
	}
	
	protected String getDeployGlobusTarget(){
		return "deployIndexGlobus";
	}
}
