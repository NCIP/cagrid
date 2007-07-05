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
import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.steps.DisplayMessageStep;
import org.cagrid.installer.steps.Negation;
import org.cagrid.installer.steps.PropertyConfigurationStep;
import org.cagrid.installer.steps.RunTasksStep;
import org.cagrid.installer.steps.TreeNodeStep;
import org.cagrid.installer.steps.options.BooleanPropertyConfigurationOption;
import org.cagrid.installer.steps.options.TextPropertyConfigurationOption;
import org.cagrid.installer.tasks.AntTask;
import org.cagrid.installer.tasks.ConditionalTask;
import org.cagrid.installer.validator.PathExistsValidator;
import org.cagrid.installer.validator.RequiredPropertyValidator;
import org.pietschy.wizard.Wizard;
import org.pietschy.wizard.WizardModel;
import org.pietschy.wizard.models.Condition;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class GenerateHostCertificate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Condition caPresentCondition = new Condition() {
			public boolean evaluate(WizardModel m) {
				if (!(m instanceof CaGridInstallerModel)) {
					throw new IllegalArgumentException(
							"An instance of StatefulWizardModel is required.");
				}
				try {
					String value = (String) ((CaGridInstallerModel) m).getState().get(
							Constants.CA_PRESENT);
					return Boolean.valueOf(value);
				} catch (Exception ex) {

				}
				return false;
			}
		};

		// Condition svcCrtPresentCondition = new Condition() {
		// public boolean evaluate(WizardModel m) {
		// if (!(m instanceof StatefulWizardModel)) {
		// throw new IllegalArgumentException(
		// "An instance of StatefulWizardModel is required.");
		// }
		// try {
		// String value = ((StatefulWizardModel) m).getState().get(
		// Constants.SERVICE_CERT_PRESENT);
		// return Boolean.valueOf(value);
		// } catch (Exception ex) {
		//
		// }
		// return false;
		// }
		// };

		List<DefaultMutableTreeNode> rootNodes = new ArrayList<DefaultMutableTreeNode>();

		Map<String, String> defaultState = new HashMap<String, String>();

		defaultState
				.put(Constants.TEMP_DIR_PATH,
						"/Users/joshua/dev3/cagrid_head/caGrid/projects/installer/temp");
		defaultState
				.put(Constants.BUILD_FILE_PATH,
						"/Users/joshua/dev3/cagrid_head/caGrid/projects/installer/deployer/build.xml");

		defaultState
				.put(Constants.CA_CERT_PATH,
						"/Users/joshua/dev3/cagrid_head/caGrid/projects/installer/temp/ca.cert");
		defaultState
				.put(Constants.CA_KEY_PATH,
						"/Users/joshua/dev3/cagrid_head/caGrid/projects/installer/temp/ca.key");

		defaultState
				.put(Constants.GRIDCA_BUILD_FILE_PATH,
						"/Users/joshua/dev3/cagrid_head/caGrid/projects/gridca/build.xml");

		defaultState.put(Constants.CA_DN, "O=someOrg,OU=someGroup,CN=someName");

		// PropertyConfigurationStep checkForSvcCertStep = new
		// PropertyConfigurationStep(
		// "Configure Certificates",
		// "Do you have a service certificate and key?");
		// checkForSvcCertStep.getOptions().add(
		// new
		// BooleanPropertyConfigurationOption(Constants.SERVICE_CERT_PRESENT,
		// "Yes", false));
		// rootNodes.add(new DefaultMutableTreeNode(new TreeNodeStep(
		// checkForSvcCertStep)));

		PropertyConfigurationStep checkForCAStep = new PropertyConfigurationStep(
				"Configure Certificates",
				"Do you have a CA certificate and key?");
		checkForCAStep.getOptions().add(
				new BooleanPropertyConfigurationOption(Constants.CA_PRESENT,
						"Yes", false));
		// rootNodes.add(new DefaultMutableTreeNode(new TreeNodeStep(
		// checkForCAStep, new Negation(svcCrtPresentCondition))));
		rootNodes.add(new DefaultMutableTreeNode(new TreeNodeStep(
				checkForCAStep)));

		PropertyConfigurationStep configureNewCAStep = new PropertyConfigurationStep(
				"Provide New CA Information", "");
		configureNewCAStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.CA_CERT_PATH,
						"Certificate Path", ""));
		configureNewCAStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.CA_KEY_PATH,
						"Key Path", ""));
		configureNewCAStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.CA_KEY_PWD,
						"Key Password", ""));
		configureNewCAStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.CA_DN,
						"Distinguished Name", ""));
		configureNewCAStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.CA_DAYS_VALID,
						"Days Valid", ""));
		configureNewCAStep.getValidators().add(
				new RequiredPropertyValidator(Constants.CA_CERT_PATH));
		configureNewCAStep.getValidators().add(
				new RequiredPropertyValidator(Constants.CA_KEY_PATH));
		configureNewCAStep.getValidators().add(
				new RequiredPropertyValidator(Constants.CA_KEY_PWD));
		rootNodes.add(new DefaultMutableTreeNode(new TreeNodeStep(
				configureNewCAStep, new Negation(caPresentCondition))));

		PropertyConfigurationStep selectCAPathStep = new PropertyConfigurationStep(
				"Provide Existing CA Information", "");
		selectCAPathStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.CA_CERT_PATH,
						"CA Certificate Path", ""));
		selectCAPathStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.CA_KEY_PATH,
						"CA Key Path", ""));
		selectCAPathStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.CA_KEY_PWD,
						"CA Key Password", ""));
		selectCAPathStep.getValidators().add(
				new RequiredPropertyValidator(Constants.CA_CERT_PATH));
		selectCAPathStep.getValidators().add(
				new PathExistsValidator(Constants.CA_CERT_PATH));
		selectCAPathStep.getValidators().add(
				new RequiredPropertyValidator(Constants.CA_KEY_PATH));
		selectCAPathStep.getValidators().add(
				new PathExistsValidator(Constants.CA_KEY_PATH));
		selectCAPathStep.getValidators().add(
				new RequiredPropertyValidator(Constants.CA_KEY_PWD));
		rootNodes.add(new DefaultMutableTreeNode(new TreeNodeStep(
				selectCAPathStep, caPresentCondition)));

		PropertyConfigurationStep configureHostCertStep = new PropertyConfigurationStep(
				"Provide Host Certificate and Key Information", "");
		selectCAPathStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.SERVICE_CERT_PATH, "Certificate Path", ""));
		selectCAPathStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.SERVICE_KEY_PATH,
						"Key Path", ""));
		selectCAPathStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.SERVICE_KEY_PWD,
						"Key Password", ""));
		selectCAPathStep.getValidators().add(
				new RequiredPropertyValidator(Constants.CA_CERT_PATH));

		RunTasksStep executeStep = new RunTasksStep("Generate Certificate", "");
		executeStep.getTasks().add(
				new ConditionalTask(
						new AntTask("Generating CA Certificate and Key", "",
								"generate-ca"),
						new Negation(caPresentCondition)));
		executeStep.getTasks().add(
				new AntTask("Generating Host Certificate and Key", "", ""));
		rootNodes
				.add(new DefaultMutableTreeNode(new TreeNodeStep(executeStep)));

		DynamicTreeModel model = new DynamicTreeModel(rootNodes,
				new DisplayMessageStep("Finished", "",
						"The certificate and key have been generated."));
		model.setState(defaultState);

		Wizard wizard = new Wizard(model);
		wizard.showInFrame("Wizard");

	}

}
