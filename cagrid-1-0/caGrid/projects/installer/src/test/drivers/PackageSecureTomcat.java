/**
 * 
 */
package test.drivers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import org.cagrid.installer.model.DynamicTreeModel;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.steps.DisplayMessageStep;
import org.cagrid.installer.steps.PropertyConfigurationStep;
import org.cagrid.installer.steps.RunTasksStep;
import org.cagrid.installer.steps.TreeNodeStep;
import org.cagrid.installer.steps.options.TextPropertyConfigurationOption;
import org.cagrid.installer.tasks.AntTask;
import org.cagrid.installer.tasks.FindDirectoryNameTask;
import org.cagrid.installer.validator.PathExistsValidator;
import org.cagrid.installer.validator.RequiredPropertyValidator;
import org.pietschy.wizard.Wizard;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class PackageSecureTomcat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<DefaultMutableTreeNode> rootNodes = new ArrayList<DefaultMutableTreeNode>();

		Map defaultState = new HashMap();
		defaultState.put(Constants.TOMCAT_ZIP_PATH, "/Users/joshua/dev3/cagrid_head/caGrid/projects/installer/deployer/software/jakarta-tomcat-5.0.28.zip");
		defaultState.put(Constants.GLOBUS_ZIP_PATH, "/Users/joshua/dev3/cagrid_head/caGrid/projects/installer/deployer/software/ws-core-4.0.3-bin.zip");
		defaultState.put(Constants.TEMP_DIR_PATH, "/Users/joshua/dev3/cagrid_head/caGrid/projects/installer/temp");
		defaultState.put(Constants.BUILD_FILE_PATH, "/Users/joshua/dev3/cagrid_head/caGrid/projects/installer/deployer/build.xml");
		defaultState.put(Constants.TOMCAT_SECURE_PORT, "8443");
		defaultState.put(Constants.TOMCAT_KEY_PATH, "conf/certs/server.key");
		defaultState.put(Constants.TOMCAT_CERT_PATH, "conf/certs/server.cert");


		PropertyConfigurationStep inputStep = new PropertyConfigurationStep("Configure", "Supply the following required information.");
		inputStep.getOptions().add(new TextPropertyConfigurationOption(Constants.GLOBUS_ZIP_PATH, "Path to Globus zip file", ""));
		inputStep.getOptions().add(new TextPropertyConfigurationOption(Constants.TOMCAT_ZIP_PATH, "Path to Tomcat zip file", ""));
		inputStep.getOptions().add(new TextPropertyConfigurationOption(Constants.TEMP_DIR_PATH, "Path to temporary directory", ""));
		
		inputStep.getValidators().add(new PathExistsValidator(Constants.TOMCAT_ZIP_PATH));
		inputStep.getValidators().add(new PathExistsValidator(Constants.GLOBUS_ZIP_PATH));
		inputStep.getValidators().add(new PathExistsValidator(Constants.TEMP_DIR_PATH));
		
		inputStep.getValidators().add(new RequiredPropertyValidator(Constants.TOMCAT_ZIP_PATH));
		inputStep.getValidators().add(new RequiredPropertyValidator(Constants.GLOBUS_ZIP_PATH));
		inputStep.getValidators().add(new RequiredPropertyValidator(Constants.TEMP_DIR_PATH));
		
		
		rootNodes.add(new DefaultMutableTreeNode(new TreeNodeStep(inputStep)));
		
		
		RunTasksStep executeStep = new RunTasksStep("Package Secure Tomcat", "");
//		executeStep.getTasks().add(new AntTask("Unzipping Tomcat", "Unzip Tomcat", "unzip-tomcat"));
//		executeStep.getTasks().add(new AntTask("Unzipping Globus", "Unzip Globus", "unzip-globus"));
		executeStep.getTasks().add(new FindDirectoryNameTask("Determining Tomcat path", "", Constants.TEMP_DIR_PATH, "jakarta-tomcat-5.0.28", Constants.TOMCAT_DIR_PATH));
		executeStep.getTasks().add(new FindDirectoryNameTask("Determining Globus path", "", Constants.TEMP_DIR_PATH, "ws-core-4.0.3", Constants.GLOBUS_DIR_PATH));
//		executeStep.getTasks().add(new AntTask("Deploying Globus to Tomcat", "", "globus-deploy-secure-tomcat"));
		executeStep.getTasks().add(new AntTask("Configuring Tomcat HTTPS Connector", "", "insert-secure-connector"));
		executeStep.getTasks().add(new AntTask("Configuring Tomcat HTTPS Valve", "", "insert-valve"));
		rootNodes.add(new DefaultMutableTreeNode(new TreeNodeStep(executeStep)));
		
		DynamicTreeModel model = new DynamicTreeModel(rootNodes,
				new DisplayMessageStep("Finished", "Finished",
						"Tomcat has been packaged."));
		model.setState(defaultState);

		Wizard wizard = new Wizard(model);
		wizard.showInFrame("Wizard");

	}

}
