package org.cagrid.installer.tasks;

import org.cagrid.installer.model.CaGridInstallerModel;

public class DeployWorkflowServiceTask extends DeployServiceTask{

	public DeployWorkflowServiceTask(String name, String description, String serviceName) {
		super(name, description, serviceName);
	}
	
	protected String getBuildFilePath(CaGridInstallerModel model) {
		return model.getServiceDestDir() + "/" + this.serviceName + "/WorkflowFactoryService"
				+ "/build.xml";
	}
}
