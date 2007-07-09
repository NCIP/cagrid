package org.cagrid.installer.tasks;

import java.util.Map;

import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.util.InstallerUtils;

public class DeployWorkflowServiceTask extends DeployServiceTask{

	String serviceName;
	
	public DeployWorkflowServiceTask(String name, String description, String serviceName, CaGridInstallerModel model) {
		super(name, description, serviceName, model);
		this.serviceName=serviceName;
	}

	protected String getDeployTomcatTarget(){
		return "deployTomcat";
	}
	
	protected String getDeployGlobusTarget(){
		return "deployGlobus";
	}
	
	protected String getBuildFilePath(Map state) {
		return InstallerUtils.getServiceDestDir(state) + "/" + this.serviceName + "/WorkflowFactoryService"
				+ "/build.xml";
	}
}
