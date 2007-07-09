/**
 * 
 */
package org.cagrid.installer;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.model.DynamicStatefulWizardModel;
import org.cagrid.installer.steps.AndCondition;
import org.cagrid.installer.steps.AuthnSvcDeployPropertiesFileEditorStep;
import org.cagrid.installer.steps.CheckSecureContainerStep;
import org.cagrid.installer.steps.ConfigureAuthnCAStep;
import org.cagrid.installer.steps.ConfigureCAStep;
import org.cagrid.installer.steps.ConfigureDorianCAStep;
import org.cagrid.installer.steps.ConfigureDorianDBStep;
import org.cagrid.installer.steps.ConfigureGTSDBStep;
import org.cagrid.installer.steps.ConfigureServiceCertStep;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.steps.DeployPropertiesFileEditorStep;
import org.cagrid.installer.steps.DeployPropertiesGMEFileEditorStep;
import org.cagrid.installer.steps.InstallationCompleteStep;
import org.cagrid.installer.steps.PropertyConfigurationStep;
import org.cagrid.installer.steps.RunTasksStep;
import org.cagrid.installer.steps.SelectComponentStep;
import org.cagrid.installer.steps.SelectInstallationTypeStep;
import org.cagrid.installer.steps.ServicePropertiesFileEditorStep;
import org.cagrid.installer.steps.SpecifyTomcatPortsStep;
import org.cagrid.installer.steps.options.BooleanPropertyConfigurationOption;
import org.cagrid.installer.steps.options.FilePropertyConfigurationOption;
import org.cagrid.installer.steps.options.ListPropertyConfigurationOption;
import org.cagrid.installer.steps.options.PasswordPropertyConfigurationOption;
import org.cagrid.installer.steps.options.TextPropertyConfigurationOption;
import org.cagrid.installer.steps.options.ListPropertyConfigurationOption.LabelValuePair;
import org.cagrid.installer.tasks.CompileCaGridTask;
import org.cagrid.installer.tasks.ConditionalTask;
import org.cagrid.installer.tasks.ConfigureDorianTask;
import org.cagrid.installer.tasks.ConfigureEnvironmentTask;
import org.cagrid.installer.tasks.ConfigureGTSTask;
import org.cagrid.installer.tasks.ConfigureGlobusTask;
import org.cagrid.installer.tasks.ConfigureGridGrouperTask;
import org.cagrid.installer.tasks.ConfigureTargetGridTask;
import org.cagrid.installer.tasks.CopySelectedServicesToTempDirTask;
import org.cagrid.installer.tasks.DeployAuthenticationServiceTask;
import org.cagrid.installer.tasks.DeployDorianTask;
import org.cagrid.installer.tasks.DeployGlobusTask;
import org.cagrid.installer.tasks.DeployIndexServiceTask;
import org.cagrid.installer.tasks.DeployServiceTask;
import org.cagrid.installer.tasks.DownloadFileTask;
import org.cagrid.installer.tasks.GenerateCATask;
import org.cagrid.installer.tasks.GenerateServiceCredsTask;
import org.cagrid.installer.tasks.PauseTask;
import org.cagrid.installer.tasks.SaveSettingsTask;
import org.cagrid.installer.tasks.UnzipInstallTask;
import org.cagrid.installer.util.Utils;
import org.cagrid.installer.validator.CreateFilePermissionValidator;
import org.cagrid.installer.validator.DBConnectionValidator;
import org.cagrid.installer.validator.DorianIdpInfoValidator;
import org.cagrid.installer.validator.GenericDBConnectionValidator;
import org.cagrid.installer.validator.KeyAccessValidator;
import org.cagrid.installer.validator.MySqlDBConnectionValidator;
import org.cagrid.installer.validator.PathExistsValidator;
import org.pietschy.wizard.Wizard;
import org.pietschy.wizard.WizardModel;
import org.pietschy.wizard.models.Condition;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class Installer {

	private static final Log logger = LogFactory.getLog(Installer.class);

	private DynamicStatefulWizardModel model;

	private SplashScreen screen;

	private final int TOTAL_INIT_STEPS = 100;

	private int initProgress = 0;

	public Installer() {

	}

	public static void main(String[] args) {
		Installer installer = new Installer();
		installer.initialize();
		installer.run();
	}

	private void splashScreenDestruct() {
		screen.setScreenVisible(false);
		screen.dispose();
	}

	private void splashScreenInit() {
		ImageIcon myImage = new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource("images/installer.gif"));
		screen = new SplashScreen(myImage);
		screen.setLocationRelativeTo(null);
		screen.setProgressMax(TOTAL_INIT_STEPS);
		screen.setScreenVisible(true);
	}

	public void initialize() {

		try {
			// Show the splash screen
			splashScreenInit();

			incrementProgress();

			Map defaultState = new HashMap();

			// Set up temp dir
			String tempDir = System.getProperty("user.home")
					+ "/.cagrid/installer/tmp";
			defaultState.put(Constants.TEMP_DIR_PATH, tempDir);
			File tempDirFile = new File(tempDir);
			if (tempDirFile.exists()) {
				tempDirFile.delete();
			}
			logger.info("Creating temporary directory '" + tempDir + "'");
			try {
				tempDirFile.mkdirs();
			} catch (Exception ex) {
				String msg = "Error creating temporary directory '" + tempDir
						+ "': " + ex.getMessage();
				logger.error(msg, ex);
				throw new RuntimeException(msg, ex);
			}

			incrementProgress();

			// Load default properties
			String downloadUrl = null;
			try {
				Properties props = new Properties();
				props.load(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("download.properties"));
				downloadUrl = props.getProperty(Constants.DOWNLOAD_URL);
			} catch (Exception ex) {
				handleException("Error getting default properties", ex);
			}
			incrementProgress();
			logger.info("Downloading default properties from: " + downloadUrl);

			String toFile = tempDir + "/downloaded.properties";
			DownloadPropsThread dpt = new DownloadPropsThread(downloadUrl,
					toFile);
			dpt.start();
			try {
				dpt.join(5000);
			} catch (InterruptedException ex) {
				handleException("Download thread interrupted", ex);
			}

			if (dpt.getException() != null) {
				Exception ex = dpt.getException();
				String msg = "Error loading default properties: "
						+ ex.getMessage();
				handleException(msg, ex);
			}

			incrementProgress();

			try {
				Properties props = new Properties();
				props.load(new FileInputStream(toFile));
				defaultState.putAll(props);
			} catch (Exception ex) {
				handleException("Error loading default properties", ex);
			}
			incrementProgress();

			initSteps(defaultState);

			while (this.initProgress < TOTAL_INIT_STEPS) {
				incrementProgress();
				try {
					Thread.sleep(50);
				} catch (Exception ex) {

				}
			}

		} finally {

			splashScreenDestruct();
		}
	}

	private void incrementProgress() {
		screen.setProgress("Initializing installer...", ++this.initProgress);
	}

	private void handleException(String msg, Exception ex) {
		logger.error(msg, ex);
		JOptionPane.showMessageDialog(null, msg, "Error",
				JOptionPane.ERROR_MESSAGE);
		throw new RuntimeException(msg, ex);
	}

	private void initSteps(Map defaultState) {

		// Check for presence of .cagrid.installer file
		String cagridInstallerFileName = System
				.getProperty(Constants.CAGRID_INSTALLER_PROPERTIES);
		if (cagridInstallerFileName != null) {
			logger.info("Custom installer properties file specified: '"
					+ cagridInstallerFileName + "'");
		} else {
			cagridInstallerFileName = System.getProperty("user.home")
					+ "/.cagrid/installer/"
					+ Constants.CAGRID_INSTALLER_PROPERTIES;
			logger.info("Using default properties file: '"
					+ cagridInstallerFileName + "'");
		}
		defaultState.put(Constants.CAGRID_INSTALLER_PROPERTIES,
				cagridInstallerFileName);
		File cagridInstallerFile = new File(cagridInstallerFileName);

		// If .cagrid.installer found, load properties.
		logger.info("Looking for '" + cagridInstallerFileName + "'");
		if (cagridInstallerFile.exists()) {
			try {
				logger.info("Loading '" + cagridInstallerFileName + "'");
				Properties props = new Properties();
				props.load(new FileInputStream(cagridInstallerFile));
				defaultState.putAll(props);
			} catch (Exception ex) {
				String msg = "Could not load '" + cagridInstallerFileName
						+ "': " + ex.getMessage();
				logger.error(msg, ex);
				throw new RuntimeException(msg, ex);
			}
		} else {
			logger.info("Did not find '" + cagridInstallerFileName + "'");
		}

		incrementProgress();

		this.model = new DynamicStatefulWizardModel(defaultState);

		// Clear some flags
		this.model.getState().remove(Constants.DORIAN_USE_GEN_CA);
		this.model.getState().remove(Constants.DORIAN_CA_PRESENT);
		// this.model.getState().remove(Constants.GENERATE_CA_CERT);
		// this.model.getState().remove(Constants.GENERATE_SERVICE_CERT);
		this.model.getState().remove(Constants.INSTALL_AUTHN_SVC);
		this.model.getState().remove(Constants.INSTALL_CADSR);
		this.model.getState().remove(Constants.INSTALL_DORIAN);
		this.model.getState().remove(Constants.INSTALL_EVS);
		this.model.getState().remove(Constants.INSTALL_FQP);
		this.model.getState().remove(Constants.INSTALL_GME);
		this.model.getState().remove(Constants.INSTALL_GRID_GROUPER);
		this.model.getState().remove(Constants.INSTALL_GTS);
		this.model.getState().remove(Constants.INSTALL_IDENT_SVC);
		this.model.getState().remove(Constants.INSTALL_INDEX_SVC);
		this.model.getState().remove(Constants.INSTALL_SERVICES);
		this.model.getState().remove(Constants.INSTALL_WORKFLOW);
		this.model.getState().remove(Constants.USE_SECURE_CONTAINER);

		incrementProgress();

		// Check for the presence of Ant
		checkInstalled("Ant", "ANT_HOME", Constants.ANT_HOME,
				Constants.ANT_INSTALLED, Constants.INSTALL_ANT, this.model
						.getState());
		incrementProgress();
		// TODO: check Ant version

		// Check for presence of Tomcat
		checkInstalled("Tomcat", "CATALINA_HOME", Constants.TOMCAT_HOME,
				Constants.TOMCAT_INSTALLED, Constants.INSTALL_TOMCAT,
				this.model.getState());
		incrementProgress();
		// TODO: check Tomcat version

		// Check for presence of Globus
		checkInstalled("Globus", "GLOBUS_LOCATION", Constants.GLOBUS_HOME,
				Constants.GLOBUS_INSTALLED, Constants.INSTALL_GLOBUS,
				this.model.getState());
		incrementProgress();
		// TODO: check Globus version

		// Check for presence of caGrid
		checkInstalled("caGrid", null, Constants.CAGRID_HOME,
				Constants.CAGRID_INSTALLED, Constants.INSTALL_CAGRID,
				this.model.getState());
		incrementProgress();
		// TODO: check caGrid version

		checkGlobusDeployed(this.model.getState());
		incrementProgress();

		// Initialize steps

		// Gives user choice to install caGrid, or one or more services, or
		// both.
		SelectInstallationTypeStep selectInstallStep = new SelectInstallationTypeStep(
				this.model.getMessage("select.install.title"), this.model
						.getMessage("select.install.desc"));
		selectInstallStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.INSTALL_CAGRID, this.model
								.getMessage("select.install.install.cagrid"),
						true, true));
		selectInstallStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.INSTALL_SERVICES, this.model
								.getMessage("select.install.install.services"),
						true, true));
		this.model.add(selectInstallStep);
		incrementProgress();

		// Presents list of available target grids
		String[] targetGrids = ((String) this.model.getState().get(
				Constants.AVAILABLE_TARGET_GRIDS)).split(",");
		PropertyConfigurationStep selectTargetGridStep = new PropertyConfigurationStep(
				this.model.getMessage("select.target.grid.title"), this.model
						.getMessage("select.target.grid.desc"));
		LabelValuePair[] targetGridPairs = new LabelValuePair[targetGrids.length];
		for (int i = 0; i < targetGrids.length; i++) {
			targetGridPairs[i] = new LabelValuePair(this.model
					.getMessage("target.grid." + targetGrids[i] + ".label"),
					targetGrids[i]);
		}
		selectTargetGridStep.getOptions().add(
				new ListPropertyConfigurationOption(Constants.TARGET_GRID,
						this.model.getMessage("target.grid"), targetGridPairs,
						true));
		this.model.add(selectTargetGridStep);
		incrementProgress();

		// Presents list of services that can be installed
		SelectComponentStep selectServicesStep = new SelectComponentStep(
				this.model.getMessage("select.services.title"), this.model
						.getMessage("select.services.desc"));
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.INSTALL_DORIAN, "Dorian", false, true));
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(Constants.INSTALL_GTS,
						"GTS", false, true));
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.INSTALL_AUTHN_SVC, "AuthenticationService",
						false, true));
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.INSTALL_GRID_GROUPER, "GridGrouper", false,
						true));
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(Constants.INSTALL_INDEX_SVC,
						"Index Service", false, true));		
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(Constants.INSTALL_GME,
						"GME", false, true));
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(Constants.INSTALL_EVS,
						"EVS", false, true));
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(Constants.INSTALL_CADSR,
						"caDSR", false, true));
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(Constants.INSTALL_FQP,
						"FQP", false, true));
		selectServicesStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.INSTALL_WORKFLOW, "Workflow", false, true));
		this.model.add(selectServicesStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_SERVICES));
			}

		});
		incrementProgress();

		// Select which container to use (Tomcat or Globus)
		PropertyConfigurationStep selectContainerStep = new PropertyConfigurationStep(
				this.model.getMessage("select.container.title"), this.model
						.getMessage("select.container.desc"));
		selectContainerStep
				.getOptions()
				.add(
						new ListPropertyConfigurationOption(
								Constants.CONTAINER_TYPE,
								this.model.getMessage("container.type"),
								new String[] {
										this.model
												.getMessage("container.type.tomcat"),
										this.model
												.getMessage("container.type.globus") },
								true));
		this.model.add(selectContainerStep);
		incrementProgress();

		// Asks user if Ant should be installed.
		addCheckInstallStep(this.model, "ant.check.reinstall.title",
				"ant.check.reinstall.desc", Constants.INSTALL_ANT,
				Constants.ANT_INSTALLED);
		incrementProgress();

		// Allows user to specify where Ant should be installed
		addInstallInfoStep(this.model, Constants.ANT_HOME, "ant",
				"ant.home.title", "ant.home.desc",
				Constants.ANT_INSTALL_DIR_PATH, Constants.INSTALL_ANT);
		incrementProgress();

		// Asks user if Tomcat should be installed
		addCheckInstallStep(this.model, "tomcat.check.reinstall.title",
				"tomcat.check.reinstall.desc", Constants.INSTALL_TOMCAT,
				Constants.TOMCAT_INSTALLED, new Condition() {

					public boolean evaluate(WizardModel m) {
						CaGridInstallerModel model = (CaGridInstallerModel) m;
						return model.getMessage("container.type.tomcat")
								.equals(
										model.getState().get(
												Constants.CONTAINER_TYPE));
					}

				});
		incrementProgress();

		// Allows user to specify where Tomcat should be installed
		addInstallInfoStep(this.model, Constants.TOMCAT_HOME, "tomcat",
				"tomcat.home.title", "tomcat.home.desc",
				Constants.TOMCAT_INSTALL_DIR_PATH, Constants.INSTALL_TOMCAT);
		incrementProgress();

		// Asks user if Globus should be installed
		addCheckInstallStep(this.model, "globus.check.reinstall.title",
				"globus.check.reinstall.desc", Constants.INSTALL_GLOBUS,
				Constants.GLOBUS_INSTALLED);
		incrementProgress();

		// Allows user to specify where Globus should be installed
		addInstallInfoStep(this.model, Constants.GLOBUS_HOME, "globus",
				"globus.home.title", "globus.home.desc",
				Constants.GLOBUS_INSTALL_DIR_PATH, Constants.INSTALL_GLOBUS);
		incrementProgress();

		// Asks user if caGrid should be installed
		addCheckInstallStep(this.model, "cagrid.check.reinstall.title",
				"cagrid.check.reinstall.desc", Constants.INSTALL_CAGRID,
				Constants.CAGRID_INSTALLED);
		incrementProgress();

		// Allows user to specify where caGrid should be installed
		addInstallInfoStep(this.model, Constants.CAGRID_HOME, "cagrid",
				"cagrid.home.title", "cagrid.home.desc",
				Constants.CAGRID_INSTALL_DIR_PATH, Constants.INSTALL_CAGRID);
		incrementProgress();

		// If Globus has already been deployed, allow user to specify whether
		// it should be redployed.
		// PropertyConfigurationStep checkRedeployGlobusStep = new
		// PropertyConfigurationStep(
		// this.model.getMessage("globus.check.redeploy.title"),
		// this.model.getMessage("globus.check.redeploy.desc"));
		// checkRedeployGlobusStep.getOptions().add(
		// new BooleanPropertyConfigurationOption(Constants.DEPLOY_GLOBUS,
		// this.model.getMessage("yes"), false, false));
		// this.model.add(checkRedeployGlobusStep, new Condition() {
		//
		// public boolean evaluate(WizardModel m) {
		// CaGridInstallerModel model = (CaGridInstallerModel) m;
		// return "true".equals(model.getState().get(
		// Constants.GLOBUS_DEPLOYED))
		// && model.getMessage("container.type.tomcat").equals(
		// model.getState().get(Constants.CONTAINER_TYPE));
		// }
		//
		// });
		incrementProgress();

		// Downloads and installs the dependencies
		RunTasksStep installDependenciesStep = new RunTasksStep(this.model
				.getMessage("install.dependencies.title"), this.model
				.getMessage("install.dependencies.desc"));
		addUnzipInstallTask(installDependenciesStep, this.model
				.getMessage("downloading.ant.title"), this.model
				.getMessage("installing.ant.title"), "",
				Constants.ANT_DOWNLOAD_URL, Constants.ANT_TEMP_FILE_NAME,
				Constants.ANT_INSTALL_DIR_PATH, Constants.ANT_DIR_NAME,
				Constants.ANT_HOME, Constants.INSTALL_ANT);
		addUnzipInstallTask(installDependenciesStep, this.model
				.getMessage("downloading.tomcat.title"), this.model
				.getMessage("installing.tomcat.title"), "",
				Constants.TOMCAT_DOWNLOAD_URL, Constants.TOMCAT_TEMP_FILE_NAME,
				Constants.TOMCAT_INSTALL_DIR_PATH, Constants.TOMCAT_DIR_NAME,
				Constants.TOMCAT_HOME, Constants.INSTALL_TOMCAT);
		addUnzipInstallTask(installDependenciesStep, this.model
				.getMessage("downloading.globus.title"), this.model
				.getMessage("installing.globus.title"), "",
				Constants.GLOBUS_DOWNLOAD_URL, Constants.GLOBUS_TEMP_FILE_NAME,
				Constants.GLOBUS_INSTALL_DIR_PATH, Constants.GLOBUS_DIR_NAME,
				Constants.GLOBUS_HOME, Constants.INSTALL_GLOBUS);
		addUnzipInstallTask(installDependenciesStep, this.model
				.getMessage("downloading.cagrid.title"), this.model
				.getMessage("installing.cagrid.title"), "",
				Constants.CAGRID_DOWNLOAD_URL, Constants.CAGRID_TEMP_FILE_NAME,
				Constants.CAGRID_INSTALL_DIR_PATH, Constants.CAGRID_DIR_NAME,
				Constants.CAGRID_HOME, Constants.INSTALL_CAGRID);

		installDependenciesStep.getTasks().add(
				new ConditionalTask(new CompileCaGridTask(this.model
						.getMessage("compiling.cagrid.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_CAGRID));
							}

						}));

		/**
		 * JAP: I guess the Progress.waitFor() method doesn't work as
		 * advertised. Without this pause, the following ConfigureTargetGridTask
		 * will sometimes fail, with Ant-related exceptions thrown.
		 */
		// installDependenciesStep.getTasks().add(
		// new ConditionalTask(new PauseTask("Pausing for 10 seconds...",
		// "", 10000), new Condition() {
		//
		// public boolean evaluate(WizardModel m) {
		// CaGridInstallerModel model = (CaGridInstallerModel) m;
		// return "true".equals(model.getState().get(
		// Constants.INSTALL_CAGRID));
		// }
		//
		// }));
		// TODO: figure out why this fails on Mac sometimes
		ConfigureTargetGridTask configTargetGridTask = new ConfigureTargetGridTask(
				this.model.getMessage("configuring.target.grid"), "");
		configTargetGridTask.setAbortOnError(false);
		installDependenciesStep.getTasks().add(
				new ConditionalTask(configTargetGridTask, new Condition() {

					public boolean evaluate(WizardModel m) {
						CaGridInstallerModel model = (CaGridInstallerModel) m;
						return "true".equals(model.getState().get(
								Constants.INSTALL_CAGRID));
					}

				}));

		installDependenciesStep.getTasks().add(
				new ConditionalTask(
						new CopySelectedServicesToTempDirTask(this.model
								.getMessage("copying.selected.services"), ""),
						new Condition() {
							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_SERVICES));
							}
						}));
		this.model.add(installDependenciesStep);

		// Checks if globus should be deployed to secure tomcat
		CheckSecureContainerStep checkDeployGlobusSecureStep = new CheckSecureContainerStep(
				this.model.getMessage("globus.check.secure.title"), this.model
						.getMessage("globus.check.secure.desc"));
		checkDeployGlobusSecureStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.USE_SECURE_CONTAINER, this.model
								.getMessage("yes"), false, false));
		this.model.add(checkDeployGlobusSecureStep);
		incrementProgress();

		// Allows user to specify Tomcat ports
		SpecifyTomcatPortsStep tomcatPortsStep = new SpecifyTomcatPortsStep(
				this.model.getMessage("tomcat.specify.ports.title"), this.model
						.getMessage("tomcat.specify.ports.desc"));
		tomcatPortsStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.TOMCAT_SHUTDOWN_PORT, this.model
								.getMessage("tomcat.shutdown.port"),
						getProperty(this.model.getState(),
								Constants.TOMCAT_SHUTDOWN_PORT, "8005"), true));
		tomcatPortsStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.TOMCAT_HTTP_PORT,
						this.model.getMessage("tomcat.http.port"), getProperty(
								this.model.getState(),
								Constants.TOMCAT_HTTP_PORT, "8080"), true));
		tomcatPortsStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.TOMCAT_HTTPS_PORT, this.model
								.getMessage("tomcat.https.port"), getProperty(
								this.model.getState(),
								Constants.TOMCAT_HTTPS_PORT, "8443"), true));
		// TODO: add validation
		this.model.add(tomcatPortsStep, new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return model.getMessage("container.type.tomcat").equals(
						model.getState().get(Constants.CONTAINER_TYPE));
			}
		});
		incrementProgress();

		// Checks if service cert is present
		PropertyConfigurationStep checkServiceCertPresentStep = new PropertyConfigurationStep(
				this.model.getMessage("svc.cert.check.present.title"),
				this.model.getMessage("svc.cert.check.present.desc"));
		checkServiceCertPresentStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.SERVICE_CERT_PRESENT, this.model
								.getMessage("yes"), false, false));
		this.model.add(checkServiceCertPresentStep, new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.USE_SECURE_CONTAINER));
			}
		});
		incrementProgress();

		// Checks if CA cert is present
		PropertyConfigurationStep checkCAPresentStep = new PropertyConfigurationStep(
				this.model.getMessage("ca.cert.check.present.title"),
				this.model.getMessage("ca.cert.check.present.desc"));
		checkCAPresentStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.CA_CERT_PRESENT,
						this.model.getMessage("yes"), false, false));
		this.model.add(checkCAPresentStep, new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.USE_SECURE_CONTAINER))
						&& !"true".equals(model.getState().get(
								Constants.SERVICE_CERT_PRESENT));
			}
		});

		// Allows user to enter existing CA cert info
		ConfigureCAStep caCertInfoStep = new ConfigureCAStep(this.model
				.getMessage("ca.cert.info.title"), this.model
				.getMessage("ca.cert.info.desc"));
		addCommonCACertFields(caCertInfoStep, Constants.CA_CERT_PATH,
				Constants.CA_KEY_PATH, Constants.CA_KEY_PWD, true);
		this.model.add(caCertInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.USE_SECURE_CONTAINER))
						&& !"true".equals(model.getState().get(
								Constants.SERVICE_CERT_PRESENT))
						&& "true".equals(model.getState().get(
								Constants.CA_CERT_PRESENT));
			}
		});
		incrementProgress();

		// Allows user to enter info necessary to gen new CA cert
		ConfigureCAStep newCaCertInfoStep = new ConfigureCAStep(this.model
				.getMessage("ca.cert.new.info.title"), this.model
				.getMessage("ca.cert.new.info.desc"));
		addCommonNewCACertFields(newCaCertInfoStep, Constants.CA_CERT_PATH,
				Constants.CA_KEY_PATH, Constants.CA_KEY_PWD, Constants.CA_DN,
				Constants.CA_DAYS_VALID);
		this.model.add(newCaCertInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.USE_SECURE_CONTAINER))
						&& !"true".equals(model.getState().get(
								Constants.SERVICE_CERT_PRESENT))
						&& !"true".equals(model.getState().get(
								Constants.CA_CERT_PRESENT));
			}
		});
		incrementProgress();

		// Allows user to enter info necessary to gen new service cert
		ConfigureServiceCertStep newServiceCertInfoStep = new ConfigureServiceCertStep(
				this.model.getMessage("service.cert.new.info.title"),
				this.model.getMessage("service.cert.new.info.desc"));
		newServiceCertInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.SERVICE_CERT_PATH, this.model
								.getMessage("service.cert.info.cert.path"),
						getProperty(this.model.getState(),
								Constants.SERVICE_CERT_PATH,
								"temp/certs/service.cert"), true));
		newServiceCertInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.SERVICE_KEY_PATH,
						this.model.getMessage("service.cert.info.key.path"),
						getProperty(this.model.getState(),
								Constants.SERVICE_KEY_PATH,
								"temp/certs/servce.key"), true));
		newServiceCertInfoStep.getOptions().add(
				new PasswordPropertyConfigurationOption(
						Constants.SERVICE_KEY_PWD, this.model
								.getMessage("service.cert.info.key.pwd"),
						this.model.getState().get(Constants.SERVICE_KEY_PWD),
						true));
		newServiceCertInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.SERVICE_HOSTNAME,
								this.model
										.getMessage("service.cert.info.hostname"),
								getProperty(this.model.getState(),
										Constants.SERVICE_HOSTNAME, "localhost"),
								true));
		// TODO: add validation
		this.model.add(newServiceCertInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.USE_SECURE_CONTAINER))
						&& !"true".equals(model.getState().get(
								Constants.SERVICE_CERT_PRESENT));
			}
		});
		incrementProgress();

		ConfigureServiceCertStep serviceCertInfoStep = new ConfigureServiceCertStep(
				this.model.getMessage("service.cert.info.title"), this.model
						.getMessage("service.cert.info.desc"));
		serviceCertInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.SERVICE_CERT_PATH, this.model
								.getMessage("service.cert.info.cert.path"),
						getProperty(this.model.getState(),
								Constants.SERVICE_CERT_PATH,
								"temp/certs/service.cert"), true));
		serviceCertInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(Constants.SERVICE_KEY_PATH,
						this.model.getMessage("service.cert.info.key.path"),
						getProperty(this.model.getState(),
								Constants.SERVICE_KEY_PATH,
								"temp/certs/service.key"), true));
		serviceCertInfoStep.getOptions().add(
				new PasswordPropertyConfigurationOption(
						Constants.SERVICE_KEY_PWD, this.model
								.getMessage("service.cert.info.key.pwd"),
						this.model.getState().get(Constants.SERVICE_KEY_PWD),
						true));
		serviceCertInfoStep.getValidators().add(
				new PathExistsValidator(Constants.SERVICE_CERT_PATH, this.model
						.getMessage("error.cert.file.not.found")));
		serviceCertInfoStep.getValidators().add(
				new PathExistsValidator(Constants.SERVICE_KEY_PATH, this.model
						.getMessage("error.key.file.not.found")));
		serviceCertInfoStep.getValidators().add(
				new KeyAccessValidator(Constants.SERVICE_KEY_PATH,
						Constants.SERVICE_KEY_PWD, this.model
								.getMessage("error.key.no.access")));
		this.model.add(serviceCertInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.USE_SECURE_CONTAINER))
						&& "true".equals(model.getState().get(
								Constants.SERVICE_CERT_PRESENT));
			}
		});
		incrementProgress();

		// Generate credentials, if necessary
		final RunTasksStep generateCredsStep = new RunTasksStep(this.model
				.getMessage("generate.credentials.title"), this.model
				.getMessage("generate.credentials.desc"));

		generateCredsStep.getTasks().add(
				new ConditionalTask(new GenerateCATask(this.model
						.getMessage("generating.ca.cert.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.USE_SECURE_CONTAINER))
										&& !"true"
												.equals(model
														.getState()
														.get(
																Constants.SERVICE_CERT_PRESENT))
										&& !"true".equals(model.getState().get(
												Constants.CA_CERT_PRESENT));
							}
						}));
		generateCredsStep.getTasks().add(
				new ConditionalTask(new GenerateServiceCredsTask(this.model
						.getMessage("generating.service.cert.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.USE_SECURE_CONTAINER))
										&& !"true"
												.equals(model
														.getState()
														.get(
																Constants.SERVICE_CERT_PRESENT));
							}
						}));
		this.model.add(generateCredsStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return generateCredsStep.getTasksCount(model) > 0;
			}
		});
		incrementProgress();

		// Allows user to edit Dorian deploy.properties
		DeployPropertiesFileEditorStep editDorianDeployPropertiesStep = new DeployPropertiesFileEditorStep(
				"dorian", this.model
						.getMessage("dorian.edit.deploy.properties.title"),
				this.model.getMessage("dorian.edit.deploy.properties.desc"),
				this.model.getMessage("edit.properties.property.name"),
				this.model.getMessage("edit.properties.property.value"));
		this.model.add(editDorianDeployPropertiesStep, new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_DORIAN));
			}
		});

		// Allows user to specify Dorian DB information.
		ConfigureDorianDBStep dorianDbInfoStep = new ConfigureDorianDBStep(
				this.model.getMessage("dorian.db.config.title"), this.model
						.getMessage("dorian.db.config.desc"));
		addDBConfigPropertyOptions(dorianDbInfoStep, "dorian.", "dorian");
		this.model.add(dorianDbInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_DORIAN));
			}
		});
		incrementProgress();

		// Dorian IdP config
		PropertyConfigurationStep dorianIdpInfoStep = new PropertyConfigurationStep(
				this.model.getMessage("dorian.idp.config.title"), this.model
						.getMessage("dorian.idp.config.desc"));
		dorianIdpInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_IDP_NAME,
								this.model.getMessage("dorian.idp.name"),
								getProperty(this.model.getState(),
										Constants.DORIAN_IDP_NAME, "Dorian IdP"),
								true));
		dorianIdpInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IDP_UID_MIN, this.model
								.getMessage("dorian.idp.uid.min"), getProperty(
								this.model.getState(),
								Constants.DORIAN_IDP_UID_MIN, "4"), true));
		dorianIdpInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IDP_UID_MAX, this.model
								.getMessage("dorian.idp.uid.max"), getProperty(
								this.model.getState(),
								Constants.DORIAN_IDP_UID_MAX, "10"), true));
		dorianIdpInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IDP_PWD_MIN, this.model
								.getMessage("dorian.idp.pwd.min"), getProperty(
								this.model.getState(),
								Constants.DORIAN_IDP_PWD_MIN, "6"), true));
		dorianIdpInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IDP_PWD_MAX, this.model
								.getMessage("dorian.idp.pwd.max"), getProperty(
								this.model.getState(),
								Constants.DORIAN_IDP_PWD_MAX, "10"), true));
		dorianIdpInfoStep
				.getOptions()
				.add(
						new ListPropertyConfigurationOption(
								Constants.DORIAN_IDP_REGPOLICY,
								this.model.getMessage("dorian.idp.regpolicy"),
								new String[] {
										this.model
												.getMessage("dorian.idp.regpolicy.manual"),
										this.model
												.getMessage("dorian.idp.regpolicy.auto") },
								true));
		dorianIdpInfoStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.DORIAN_IDP_SAML_AUTORENEW, this.model
								.getMessage("dorian.idp.saml.autorenew"), true,
						false));
		dorianIdpInfoStep.getOptions().add(
				new PasswordPropertyConfigurationOption(
						Constants.DORIAN_IDP_SAML_KEYPWD, this.model
								.getMessage("dorian.idp.saml.keypwd"),
						this.model.getState().get(
								Constants.DORIAN_IDP_SAML_KEYPWD), true));
		dorianIdpInfoStep.getValidators().add(
				new DorianIdpInfoValidator(this.model, this.model
						.getMessage("error.nan")));

		this.model.add(dorianIdpInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_DORIAN));
			}
		});
		incrementProgress();

		// Dorian Ifs Config
		PropertyConfigurationStep dorianIfsInfoStep = new PropertyConfigurationStep(
				this.model.getMessage("dorian.ifs.config.title"), this.model
						.getMessage("dorian.ifs.config.desc"));
		dorianIfsInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IFS_IDPNAME_MIN, this.model
								.getMessage("dorian.ifs.idpname.min"),
						getProperty(this.model.getState(),
								Constants.DORIAN_IFS_IDPNAME_MIN, "3"), true));
		dorianIfsInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IFS_IDPNAME_MAX, this.model
								.getMessage("dorian.ifs.idpname.max"),
						getProperty(this.model.getState(),
								Constants.DORIAN_IFS_IDPNAME_MAX, "50"), true));
		dorianIfsInfoStep
				.getOptions()
				.add(
						new ListPropertyConfigurationOption(
								Constants.DORIAN_IFS_IDPOLICY,
								this.model.getMessage("dorian.ifs.idpolicy"),
								new String[] {
										this.model
												.getMessage("dorian.ifs.idpolicy.name"),
										this.model
												.getMessage("dorian.ifs.idpolicy.id") },
								true));
		dorianIfsInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IFS_CREDLIFETIME_YEARS, this.model
								.getMessage("dorian.ifs.credlifetime.years"),
						getProperty(this.model.getState(),
								Constants.DORIAN_IFS_CREDLIFETIME_YEARS, "1"),
						true));
		dorianIfsInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IFS_CREDLIFETIME_MONTHS, this.model
								.getMessage("dorian.ifs.credlifetime.months"),
						getProperty(this.model.getState(),
								Constants.DORIAN_IFS_CREDLIFETIME_MONTHS, "0"),
						true));
		dorianIfsInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IFS_CREDLIFETIME_DAYS, this.model
								.getMessage("dorian.ifs.credlifetime.days"),
						getProperty(this.model.getState(),
								Constants.DORIAN_IFS_CREDLIFETIME_DAYS, "0"),
						true));
		dorianIfsInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_IFS_CREDLIFETIME_HOURS, this.model
								.getMessage("dorian.ifs.credlifetime.hours"),
						getProperty(this.model.getState(),
								Constants.DORIAN_IFS_CREDLIFETIME_HOURS, "0"),
						true));
		dorianIfsInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_IFS_CREDLIFETIME_MINUTES,
								this.model
										.getMessage("dorian.ifs.credlifetime.minutes"),
								getProperty(
										this.model.getState(),
										Constants.DORIAN_IFS_CREDLIFETIME_MINUTES,
										"0"), true));
		dorianIfsInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_IFS_CREDLIFETIME_SECONDS,
								this.model
										.getMessage("dorian.ifs.credlifetime.seconds"),
								getProperty(
										this.model.getState(),
										Constants.DORIAN_IFS_CREDLIFETIME_SECONDS,
										"0"), true));
		dorianIfsInfoStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.DORIAN_IFS_HOSTCERT_AUTOAPPROVE, this.model
								.getMessage("dorian.ifs.hostcert.autoapprove"),
						true, false));

		dorianIfsInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_IFS_PROXYLIFETIME_HOURS,
								this.model
										.getMessage("dorian.ifs.proxylifetime.hours"),
								getProperty(
										this.model.getState(),
										Constants.DORIAN_IFS_PROXYLIFETIME_HOURS,
										"12"), true));
		dorianIfsInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_IFS_PROXYLIFETIME_MINUTES,
								this.model
										.getMessage("dorian.ifs.proxylifetime.minutes"),
								getProperty(
										this.model.getState(),
										Constants.DORIAN_IFS_PROXYLIFETIME_MINUTES,
										"0"), true));
		dorianIfsInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_IFS_PROXYLIFETIME_SECONDS,
								this.model
										.getMessage("dorian.ifs.proxylifetime.seconds"),
								getProperty(
										this.model.getState(),
										Constants.DORIAN_IFS_PROXYLIFETIME_SECONDS,
										"0"), true));

		dorianIfsInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_IFS_GTS_URL,
								this.model.getMessage("dorian.ifs.gts.url"),
								getProperty(this.model.getState(),
										Constants.DORIAN_IFS_GTS_URL,
										"https://cagrid01.bmi.ohio-state.edu:8442/wsrf/services/cagrid/GTS"),
								true));
		// TODO: figure out why this hangs
		// dorianIfsInfoStep.getValidators().add(
		// new DorianIfsInfoValidator(this.model, this.model
		// .getMessage("error.nan"), this.model
		// .getMessage("error.bad.url")));
		this.model.add(dorianIfsInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_DORIAN));
			}
		});
		incrementProgress();

		PropertyConfigurationStep checkDorianUseGeneratedCAStep = new PropertyConfigurationStep(
				this.model.getMessage("dorian.check.use.gen.ca.title"),
				this.model.getMessage("dorian.check.use.gen.ca.desc"));
		checkDorianUseGeneratedCAStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.DORIAN_USE_GEN_CA, this.model
								.getMessage("yes"), false, false));
		this.model.add(checkDorianUseGeneratedCAStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;

				return (Utils.checkGenerateCA(model) || "true".equals(model
						.getState().get(Constants.CA_CERT_PRESENT)))
						&& "true".equals(model.getState().get(
								Constants.INSTALL_DORIAN));
			}

		});
		incrementProgress();

		// Checks if user will supply Dorian CA
		PropertyConfigurationStep checkDorianCAPresentStep = new PropertyConfigurationStep(
				this.model.getMessage("dorian.check.ca.present.title"),
				this.model.getMessage("dorian.check.ca.present.desc"));
		checkDorianCAPresentStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.DORIAN_CA_PRESENT, this.model
								.getMessage("yes"), false, false));
		this.model.add(checkDorianCAPresentStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;

				return "true".equals(model.getState().get(
						Constants.INSTALL_DORIAN))
						&& !"true".equals(model.getState().get(
								Constants.DORIAN_USE_GEN_CA));
			}

		});
		incrementProgress();

		// Configure existing Dorian CA
		ConfigureDorianCAStep dorianCaCertInfoStep = new ConfigureDorianCAStep(
				this.model.getMessage("dorian.ca.cert.info.title"), this.model
						.getMessage("dorian.ca.cert.info.desc"));
		dorianCaCertInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.DORIAN_CA_CERT_PATH, this.model
								.getMessage("dorian.ca.cert.info.cert.path"),
						this.model.getState()
								.get(Constants.DORIAN_CA_CERT_PATH), true));
		dorianCaCertInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_KEY_PATH,
								this.model
										.getMessage("dorian.ca.cert.info.key.path"),
								this.model.getState().get(
										Constants.DORIAN_CA_KEY_PATH), true));
		addCommonDorianCAConfigFields(dorianCaCertInfoStep);
		dorianCaCertInfoStep.getValidators().add(
				new PathExistsValidator(Constants.DORIAN_CA_CERT_PATH,
						this.model.getMessage("error.cert.file.not.found")));
		dorianCaCertInfoStep.getValidators().add(
				new PathExistsValidator(Constants.DORIAN_CA_KEY_PATH,
						this.model.getMessage("error.key.file.not.found")));
		dorianCaCertInfoStep.getValidators().add(
				new KeyAccessValidator(Constants.DORIAN_CA_KEY_PATH,
						Constants.DORIAN_CA_KEY_PWD, this.model
								.getMessage("error.key.no.access")));
		this.model.add(dorianCaCertInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_DORIAN))
						&& ("true".equals(model.getState().get(
								Constants.DORIAN_CA_PRESENT)) || "true"
								.equals(model.getState().get(
										Constants.DORIAN_USE_GEN_CA)));
			}
		});
		incrementProgress();

		// Configure new Dorian CA
		PropertyConfigurationStep dorianCaNewCertInfoStep = new PropertyConfigurationStep(
				this.model.getMessage("dorian.ca.new.cert.info.title"),
				this.model.getMessage("dorian.ca.new.cert.info.desc"));

		addCommonDorianCAConfigFields(dorianCaNewCertInfoStep);

		dorianCaNewCertInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_SUBJECT,
								this.model
										.getMessage("dorian.ca.cert.info.subject"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_SUBJECT,
										"C=US,O=abc,OU=xyz,OU=caGrid,OU=Users,CN=caGrid Dorian CA"),
								true));

		dorianCaNewCertInfoStep.getOptions().add(
				new ListPropertyConfigurationOption(
						Constants.DORIAN_CA_CAKEY_SIZE, this.model
								.getMessage("dorian.ca.cert.info.cakey.size"),
						new String[] { String.valueOf(2048),
								String.valueOf(1024), String.valueOf(512) },
						true));
		dorianCaNewCertInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_LIFETIME_YEARS,
								this.model
										.getMessage("dorian.ca.cert.info.lifetime.years"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_LIFETIME_YEARS,
										"10"), true));
		dorianCaNewCertInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_LIFETIME_MONTHS,
								this.model
										.getMessage("dorian.ca.cert.info.lifetime.months"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_LIFETIME_MONTHS,
										"0"), true));
		dorianCaNewCertInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_LIFETIME_DAYS,
								this.model
										.getMessage("dorian.ca.cert.info.lifetime.days"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_LIFETIME_DAYS, "0"),
								true));
		dorianCaNewCertInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_LIFETIME_HOURS,
								this.model
										.getMessage("dorian.ca.cert.info.lifetime.hours"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_LIFETIME_HOURS, "0"),
								true));
		dorianCaNewCertInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_LIFETIME_MINUTES,
								this.model
										.getMessage("dorian.ca.cert.info.lifetime.minutes"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_LIFETIME_MINUTES,
										"0"), true));
		dorianCaNewCertInfoStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_LIFETIME_SECONDS,
								this.model
										.getMessage("dorian.ca.cert.info.lifetime.seconds"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_LIFETIME_SECONDS,
										"0"), true));
		// TODO: add validator
		this.model.add(dorianCaNewCertInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_DORIAN))
						&& !"true".equals(model.getState().get(
								Constants.DORIAN_CA_PRESENT))
						&& !"true".equals(model.getState().get(
								Constants.DORIAN_USE_GEN_CA));
			}
		});
		incrementProgress();
		
		
		DeployPropertiesGMEFileEditorStep editGMEDeployPropertiesStep = new DeployPropertiesGMEFileEditorStep(
				"gme", this.model
						.getMessage("gme.edit.deploy.properties.title"),
				this.model.getMessage("gme.edit.deploy.properties.desc"),
				this.model.getMessage("edit.properties.property.name"),
				this.model.getMessage("edit.properties.property.value"));
		this.model.add(editGMEDeployPropertiesStep,new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_GME));
			}
		});

		incrementProgress();
		
		DeployPropertiesFileEditorStep editEVSDeployPropertiesStep = new DeployPropertiesFileEditorStep(
				"evs", this.model
						.getMessage("evs.edit.deploy.properties.title"),
				this.model.getMessage("evs.edit.deploy.properties.desc"),
				this.model.getMessage("edit.properties.property.name"),
				this.model.getMessage("edit.properties.property.value"));
		this.model.add(editEVSDeployPropertiesStep,new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_EVS));
			}
		});
						
		DeployPropertiesFileEditorStep editcaDSRDeployPropertiesStep = new DeployPropertiesFileEditorStep(
				"caDSR", this.model
						.getMessage("caDSR.edit.deploy.properties.title"),
				this.model.getMessage("caDSR.edit.deploy.properties.desc"),
				this.model.getMessage("edit.properties.property.name"),
				this.model.getMessage("edit.properties.property.value"));
		this.model.add(editcaDSRDeployPropertiesStep,new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_CADSR));
			}
		});
		
		ServicePropertiesFileEditorStep editFQPServicePropertiesStep = new ServicePropertiesFileEditorStep(
				"fqp", this.model
						.getMessage("fqp.edit.service.properties.title"),
				this.model.getMessage("fqp.edit.service.properties.desc"),
				this.model.getMessage("edit.properties.property.name"),
				this.model.getMessage("edit.properties.property.value"));
		this.model.add(editFQPServicePropertiesStep,new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_FQP));
			}
		});
		
		DeployPropertiesFileEditorStep editFQPDeployPropertiesStep = new DeployPropertiesFileEditorStep(
				"fqp", this.model
						.getMessage("fqp.edit.deploy.properties.title"),
				this.model.getMessage("fqp.edit.deploy.properties.desc"),
				this.model.getMessage("edit.properties.property.name"),
				this.model.getMessage("edit.properties.property.value"));
		this.model.add(editFQPDeployPropertiesStep,new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_FQP));
			}
		});


		DeployPropertiesFileEditorStep editGTSDeployPropertiesStep = new DeployPropertiesFileEditorStep(
				"gts", this.model
						.getMessage("gts.edit.deploy.properties.title"),
				this.model.getMessage("gts.edit.deploy.properties.desc"),
				this.model.getMessage("edit.properties.property.name"),
				this.model.getMessage("edit.properties.property.value"));
		this.model.add(editGTSDeployPropertiesStep);

		// Configures the GTS database
		ConfigureGTSDBStep gtsDbInfoStep = new ConfigureGTSDBStep(this.model
				.getMessage("gts.db.config.title"), this.model
				.getMessage("gts.db.config.desc"));
		addDBConfigPropertyOptions(gtsDbInfoStep, "gts.", "gts");
		this.model.add(gtsDbInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState()
						.get(Constants.INSTALL_GTS));
			}
		});
		incrementProgress();

		PropertyConfigurationStep gtsAddAdminStep = new PropertyConfigurationStep(
				this.model.getMessage("gts.add.admin.title"), this.model
						.getMessage("gts.add.admin.desc"));
		gtsAddAdminStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.GTS_ADMIN_IDENT,
								this.model.getMessage("gts.admin.ident"),
								getProperty(this.model.getState(),
										Constants.GTS_ADMIN_IDENT,
										"/O=org/OU=unit/OU=User Group/OU=Dorian IdP/CN=manager"),
								true));
		this.model.add(gtsAddAdminStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState()
						.get(Constants.INSTALL_GTS));
			}
		});
		incrementProgress();

		PropertyConfigurationStep checkAuthnUseGeneratedCAStep = new PropertyConfigurationStep(
				this.model.getMessage("authn.svc.check.use.gen.ca.title"),
				this.model.getMessage("authn.svc.check.use.gen.ca.desc"));
		checkAuthnUseGeneratedCAStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.AUTHN_SVC_USE_GEN_CA, this.model
								.getMessage("yes"), false, false));
		this.model.add(checkAuthnUseGeneratedCAStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;

				return (Utils.checkGenerateCA(model) || "true".equals(model
						.getState().get(Constants.CA_CERT_PRESENT)))
						&& "true".equals(model.getState().get(
								Constants.INSTALL_AUTHN_SVC));
			}

		});
		incrementProgress();

		// Checks if user will supply Authn Svc CA
		PropertyConfigurationStep checkAuthnCAPresentStep = new PropertyConfigurationStep(
				this.model.getMessage("authn.svc.check.ca.present.title"),
				this.model.getMessage("authn.svc.check.ca.present.desc"));
		checkAuthnCAPresentStep.getOptions().add(
				new BooleanPropertyConfigurationOption(
						Constants.AUTHN_SVC_CA_PRESENT, this.model
								.getMessage("yes"), false, false));
		this.model.add(checkAuthnCAPresentStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;

				return "true".equals(model.getState().get(
						Constants.INSTALL_AUTHN_SVC))
						&& !"true".equals(model.getState().get(
								Constants.AUTHN_SVC_USE_GEN_CA));
			}

		});
		incrementProgress();

		// Authentication Service Existing CA Config
		ConfigureAuthnCAStep authnCaCertInfoStep = new ConfigureAuthnCAStep(
				this.model.getMessage("authn.svc.ca.cert.info.title"),
				this.model.getMessage("authn.svc.ca.cert.info.desc"));
		addCommonCACertFields(authnCaCertInfoStep,
				Constants.AUTHN_SVC_CA_CERT_PATH,
				Constants.AUTHN_SVC_CA_KEY_PATH,
				Constants.AUTHN_SVC_CA_KEY_PWD, true);
		this.model.add(authnCaCertInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_AUTHN_SVC))
						&& ("true".equals(model.getState().get(
								Constants.AUTHN_SVC_CA_PRESENT)) || "true"
								.equals(model.getState().get(
										Constants.AUTHN_SVC_USE_GEN_CA)));
			}
		});
		incrementProgress();

		// AuthenticationService New CA
		PropertyConfigurationStep authnCaNewCertInfoStep = new PropertyConfigurationStep(
				this.model.getMessage("authn.svc.ca.new.cert.info.title"),
				this.model.getMessage("authn.svc.ca.new.cert.info.desc"));
		addCommonNewCACertFields(authnCaNewCertInfoStep,
				Constants.AUTHN_SVC_CA_CERT_PATH,
				Constants.AUTHN_SVC_CA_KEY_PATH,
				Constants.AUTHN_SVC_CA_KEY_PWD, Constants.AUTHN_SVC_CA_DN,
				Constants.AUTHN_SVC_CA_DAYS_VALID);
		this.model.add(authnCaNewCertInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_AUTHN_SVC))
						&& !"true".equals(model.getState().get(
								Constants.AUTHN_SVC_CA_PRESENT))
						&& !"true".equals(model.getState().get(
								Constants.AUTHN_SVC_USE_GEN_CA));
			}
		});
		incrementProgress();

		PropertyConfigurationStep overwriteJaasStep = new PropertyConfigurationStep(
				this.model.getMessage("authn.svc.overwrite.jaas.title"),
				this.model.getMessage("authn.svc.overwrite.jaas.desc"));
		overwriteJaasStep
				.getOptions()
				.add(
						new ListPropertyConfigurationOption(
								Constants.AUTHN_SVC_OVERWRITE_JAAS,
								this.model
										.getMessage("authn.svc.overwrite.jaas"),
								new LabelValuePair[] {
										new LabelValuePair(
												this.model
														.getMessage("authn.svc.overwrite.jaas.yes"),
												Constants.AUTHN_SVC_OVERWRITE_JAAS_YES),
										new LabelValuePair(
												this.model
														.getMessage("authn.svc.overwrite.jaas.no"),
												Constants.AUTHN_SVC_OVERWRITE_JAAS_NO) }));
		this.model.add(overwriteJaasStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return new File(System.getProperty("user.home")
						+ "/.java.login.config").exists()
						&& "true".equals(model.getState().get(
								Constants.INSTALL_AUTHN_SVC));
			}

		});

		// Let's user select the type of credential provider
		PropertyConfigurationStep selectCredentialProviderStep = new PropertyConfigurationStep(
				this.model
						.getMessage("authn.svc.select.cred.provider.type.title"),
				this.model
						.getMessage("authn.svc.select.cred.provider.type.desc"));
		selectCredentialProviderStep
				.getOptions()
				.add(
						new ListPropertyConfigurationOption(
								Constants.AUTHN_SVC_CRED_PROVIDER_TYPE,
								this.model
										.getMessage("authn.svc.cred.provider.type"),
								new LabelValuePair[] {
										new LabelValuePair(
												this.model
														.getMessage("authn.svc.cred.provider.type.rdbms"),
												Constants.AUTHN_SVC_CRED_PROVIDER_TYPE_RDBMS),
										new LabelValuePair(
												this.model
														.getMessage("authn.svc.cred.provider.type.ldap"),
												Constants.AUTHN_SVC_CRED_PROVIDER_TYPE_LDAP) }));
		this.model.add(selectCredentialProviderStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_AUTHN_SVC));
			}

		});

		// AuthenticationService RDBMS step
		PropertyConfigurationStep authnSvcRdbmsStep = new PropertyConfigurationStep(
				this.model.getMessage("authn.svc.rdbms.title"), this.model
						.getMessage("authn.svc.rdbms.desc"));
		authnSvcRdbmsStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.AUTHN_SVC_CSM_CTX,
								this.model.getMessage("authn.svc.csm.ctx"),
								getProperty(this.model.getState(),
										Constants.AUTHN_SVC_CSM_CTX, "AUTHNSVC"),
								true));
		authnSvcRdbmsStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.AUTHN_SVC_RDBMS_URL, this.model
								.getMessage("authn.svc.rdbms.url"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_RDBMS_URL,
								"jdbc:mysql://localhost:3306/authnsvc"), true));

		FilePropertyConfigurationOption driverJar = new FilePropertyConfigurationOption(
				Constants.AUTHN_SVC_RDBMS_DRIVER_JAR,
				this.model.getMessage("authn.svc.rdbms.driver.jar"),
				getProperty(this.model.getState(),
						Constants.AUTHN_SVC_RDBMS_DRIVER_JAR, System
								.getProperty("user.dir")
								+ "/lib/mysql-connector-java-3.0.16-ga-bin.jar"),
				true);
		driverJar.setBrowseLabel(this.model.getMessage("browse"));
		driverJar.setExtensions(new String[] { ".jar" });
		authnSvcRdbmsStep.getOptions().add(driverJar);

		authnSvcRdbmsStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.AUTHN_SVC_RDBMS_DRIVER, this.model
								.getMessage("authn.svc.rdbms.driver"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_RDBMS_DRIVER,
								"org.gjt.mm.mysql.Driver"), true));
		authnSvcRdbmsStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.AUTHN_SVC_RDBMS_USERNAME, this.model
								.getMessage("authn.svc.rdbms.username"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_RDBMS_USERNAME, "root"),
						true));
		authnSvcRdbmsStep.getOptions().add(
				new PasswordPropertyConfigurationOption(
						Constants.AUTHN_SVC_RDBMS_PASSWORD, this.model
								.getMessage("authn.svc.rdbms.password"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_RDBMS_PASSWORD, ""), true));
		authnSvcRdbmsStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.AUTHN_SVC_RDBMS_TABLE_NAME, this.model
								.getMessage("authn.svc.rdbms.table.name"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_RDBMS_TABLE_NAME,
								"CSM_USER"), true));
		authnSvcRdbmsStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.AUTHN_SVC_RDBMS_LOGIN_ID_COLUMN, this.model
								.getMessage("authn.svc.rdbms.login.id.column"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_RDBMS_LOGIN_ID_COLUMN,
								"LOGIN_NAME"), true));
		authnSvcRdbmsStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.AUTHN_SVC_RDBMS_PASSWORD_COLUMN, this.model
								.getMessage("authn.svc.rdbms.password.column"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_RDBMS_PASSWORD_COLUMN,
								"PASSWORD"), true));
		authnSvcRdbmsStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.AUTHN_SVC_RDBMS_FIRST_NAME_COLUMN,
								this.model
										.getMessage("authn.svc.rdbms.first.name.column"),
								getProperty(
										this.model.getState(),
										Constants.AUTHN_SVC_RDBMS_FIRST_NAME_COLUMN,
										"FIRST_NAME"), true));
		authnSvcRdbmsStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.AUTHN_SVC_RDBMS_LAST_NAME_COLUMN,
								this.model
										.getMessage("authn.svc.rdbms.last.name.column"),
								getProperty(
										this.model.getState(),
										Constants.AUTHN_SVC_RDBMS_LAST_NAME_COLUMN,
										"LAST_NAME"), true));

		authnSvcRdbmsStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.AUTHN_SVC_RDBMS_EMAIL_ID_COLUMN, this.model
								.getMessage("authn.svc.rdbms.email.id.column"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_RDBMS_EMAIL_ID_COLUMN,
								"EMAIL_ID"), true));
		authnSvcRdbmsStep
				.getOptions()
				.add(
						new BooleanPropertyConfigurationOption(
								Constants.AUTHN_SVC_RDBMS_ENCRYPTION_ENABLED,
								this.model
										.getMessage("authn.svc.rdbms.encryption.enabled"),
								false, false));
		authnSvcRdbmsStep
				.getValidators()
				.add(
						new PathExistsValidator(
								Constants.AUTHN_SVC_RDBMS_DRIVER_JAR,
								this.model
										.getMessage("authn.svc.rdbms.driver.jar.not.found")));
		authnSvcRdbmsStep.getValidators().add(
				new GenericDBConnectionValidator(
						Constants.AUTHN_SVC_RDBMS_USERNAME,
						Constants.AUTHN_SVC_RDBMS_PASSWORD, "select 1",
						this.model.getMessage("db.validation.failed"),
						Constants.AUTHN_SVC_RDBMS_DRIVER_JAR,
						Constants.AUTHN_SVC_RDBMS_URL,
						Constants.AUTHN_SVC_RDBMS_DRIVER));
		this.model.add(authnSvcRdbmsStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_AUTHN_SVC))
						&& Constants.AUTHN_SVC_CRED_PROVIDER_TYPE_RDBMS
								.equals(model.getState().get(
										Constants.AUTHN_SVC_CRED_PROVIDER_TYPE));
			}
		});
		incrementProgress();

		// AuthenticationService LDAP Setup
		PropertyConfigurationStep authnSvcLdapStep = new PropertyConfigurationStep(
				this.model.getMessage("authn.svc.ldap.title"), this.model
						.getMessage("authn.svc.ldap.desc"));
		authnSvcLdapStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.AUTHN_SVC_CSM_CTX,
								this.model.getMessage("authn.svc.csm.ctx"),
								getProperty(this.model.getState(),
										Constants.AUTHN_SVC_CSM_CTX, "AUTHNSVC"),
								true));
		authnSvcLdapStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.AUTHN_SVC_LDAP_HOSTNAME, this.model
								.getMessage("authn.svc.ldap.hostname"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_LDAP_HOSTNAME,
								"ldaps://cbioweb.nci.nih.gov:636"), true));
		authnSvcLdapStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.AUTHN_SVC_LDAP_SEARCH_BASE, this.model
								.getMessage("authn.svc.ldap.search.base"),
						getProperty(this.model.getState(),
								Constants.AUTHN_SVC_LDAP_SEARCH_BASE,
								"ou=nci,o=nih"), true));
		authnSvcLdapStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.AUTHN_SVC_LDAP_LOGIN_ID_ATTRIBUTE,
								this.model
										.getMessage("authn.svc.ldap.login.id.attribute"),
								getProperty(
										this.model.getState(),
										Constants.AUTHN_SVC_LDAP_LOGIN_ID_ATTRIBUTE,
										"cn"), true));
		authnSvcLdapStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.AUTHN_SVC_LDAP_FIRST_NAME_ATTRIBUTE,
								this.model
										.getMessage("authn.svc.ldap.first.name.attribute"),
								getProperty(
										this.model.getState(),
										Constants.AUTHN_SVC_LDAP_FIRST_NAME_ATTRIBUTE,
										"givenName"), true));
		authnSvcLdapStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.AUTHN_SVC_LDAP_LAST_NAME_ATTRIBUTE,
								this.model
										.getMessage("authn.svc.ldap.last.name.attribute"),
								getProperty(
										this.model.getState(),
										Constants.AUTHN_SVC_LDAP_LAST_NAME_ATTRIBUTE,
										"sn"), true));
		// TODO: add validation
		this.model.add(authnSvcLdapStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_AUTHN_SVC))
						&& Constants.AUTHN_SVC_CRED_PROVIDER_TYPE_LDAP
								.equals(model.getState().get(
										Constants.AUTHN_SVC_CRED_PROVIDER_TYPE));
			}
		});
		incrementProgress();

		// Configure AuthenticationService deploy.properties
		AuthnSvcDeployPropertiesFileEditorStep editAuthnDeployPropertiesStep = new AuthnSvcDeployPropertiesFileEditorStep(
				"authentication-service", this.model
						.getMessage("authn.svc.edit.deploy.properties.title"),
				this.model.getMessage("authn.svc.edit.deploy.properties.desc"),
				this.model.getMessage("edit.properties.property.name"),
				this.model.getMessage("edit.properties.property.value"));
		this.model.add(editAuthnDeployPropertiesStep, new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_AUTHN_SVC));
			}
		});

		PropertyConfigurationStep gridGrouperConfigStep = new PropertyConfigurationStep(
				this.model.getMessage("grid.grouper.config.title"), this.model
						.getMessage("grid.grouper.config.desc"));
		gridGrouperConfigStep
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.GRID_GROUPER_ADMIN_IDENT,
								this.model
										.getMessage("grid.grouper.admin.ident"),
								getProperty(this.model.getState(),
										Constants.GRID_GROUPER_ADMIN_IDENT,
										"/O=org/OU=unit/OU=User Group/OU=Dorian IdP/CN=manager"),
								true));
		gridGrouperConfigStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.GRID_GROUPER_DB_URL, this.model
								.getMessage("grid.grouper.db.url"),
						getProperty(this.model.getState(),
								Constants.GRID_GROUPER_DB_URL,
								"jdbc:mysql://localhost:3306/grouper"), true));
		gridGrouperConfigStep.getOptions().add(
				new TextPropertyConfigurationOption(
						Constants.GRID_GROUPER_DB_USERNAME, this.model
								.getMessage("grid.grouper.db.username"),
						getProperty(this.model.getState(),
								Constants.GRID_GROUPER_DB_USERNAME, "root"),
						true));
		gridGrouperConfigStep.getOptions().add(
				new PasswordPropertyConfigurationOption(
						Constants.GRID_GROUPER_DB_PASSWORD, this.model
								.getMessage("grid.grouper.db.password"),
						getProperty(this.model.getState(),
								Constants.GRID_GROUPER_DB_PASSWORD, ""), true));
		this.model.add(gridGrouperConfigStep, new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_GRID_GROUPER));
			}
		});

		DeployPropertiesFileEditorStep editGridGrouperDeployPropertiesStep = new DeployPropertiesFileEditorStep(
				"gridgrouper",
				this.model
						.getMessage("grid.grouper.edit.deploy.properties.title"),
				this.model
						.getMessage("grid.grouper.edit.deploy.properties.desc"),
				this.model.getMessage("edit.properties.property.name"),
				this.model.getMessage("edit.properties.property.value"));
		this.model.add(editGridGrouperDeployPropertiesStep, new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(
						Constants.INSTALL_GRID_GROUPER));
			}
		});

		// Performs the installation
		RunTasksStep installStep = new RunTasksStep(this.model
				.getMessage("install.title"), this.model
				.getMessage("install.desc"));

		installStep.getTasks().add(
				new ConfigureEnvironmentTask(this.model
						.getMessage("configuring.environment.title"), ""));

		installStep.getTasks().add(
				new ConditionalTask(new DeployGlobusTask(this.model
						.getMessage("deploying.globus.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return model
										.getMessage("container.type.tomcat")
										.equals(
												model
														.getState()
														.get(
																Constants.CONTAINER_TYPE));
							}

						}));

		installStep.getTasks().add(
				new ConditionalTask(new ConfigureGlobusTask(this.model
						.getMessage("configuring.globus.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return model
										.getMessage("container.type.globus")
										.equals(
												model
														.getState()
														.get(
																Constants.CONTAINER_TYPE));
							}

						}));

		installStep.getTasks().add(
				new ConditionalTask(new ConfigureDorianTask(this.model
						.getMessage("configuring.dorian.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_DORIAN));
							}

						}));

		installStep.getTasks().add(
				new ConditionalTask(
						new DeployDorianTask(this.model
								.getMessage("installing.dorian.title"), "",
								this.model), new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_DORIAN));
							}

						}));
		
		
		installStep.getTasks().add(
				new ConditionalTask(
						new DeployServiceTask(this.model.getMessage("installing.gme.title"), "","gme", this.model), new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_GME));
							}

						}));
		
		installStep.getTasks().add(
				new ConditionalTask(
						new DeployServiceTask(this.model.getMessage("installing.evs.title"), "","evs", this.model), new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_EVS));
							}

						}));

		installStep.getTasks().add(
				new ConditionalTask(
						new DeployServiceTask(this.model.getMessage("installing.caDSR.title"), "","caDSR", this.model), new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_CADSR));
							}

						}));

		installStep.getTasks().add(
				new ConditionalTask(
						new DeployServiceTask(this.model.getMessage("installing.fqp.title"), "","fqp", this.model), new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_FQP));
							}

						}));

		installStep.getTasks().add(
				new ConditionalTask(
						new DeployIndexServiceTask(this.model.getMessage("installing.index.title"), "","index", this.model), new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_INDEX_SVC));
							}

						}));

		installStep.getTasks().add(
				new ConditionalTask(new ConfigureGTSTask(this.model
						.getMessage("configuring.gts.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_GTS));
							}

						}));
		installStep.getTasks().add(
				new ConditionalTask(new DeployServiceTask(this.model
						.getMessage("installing.gts.title"), "", "gts",
						this.model), new Condition() {

					public boolean evaluate(WizardModel m) {
						CaGridInstallerModel model = (CaGridInstallerModel) m;
						return "true".equals(model.getState().get(
								Constants.INSTALL_GTS));
					}

				}));

		installStep.getTasks().add(
				new ConditionalTask(new DeployAuthenticationServiceTask(
						this.model.getMessage("deploying.authn.svc.title"), "",
						this.model), new Condition() {

					public boolean evaluate(WizardModel m) {
						CaGridInstallerModel model = (CaGridInstallerModel) m;
						return "true".equals(model.getState().get(
								Constants.INSTALL_AUTHN_SVC));
					}

				}));

		installStep.getTasks().add(
				new ConditionalTask(new ConfigureGridGrouperTask(this.model
						.getMessage("configuring.grid.grouper.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return "true".equals(model.getState().get(
										Constants.INSTALL_GRID_GROUPER));
							}

						}));
		installStep.getTasks().add(
				new ConditionalTask(new DeployServiceTask(this.model
						.getMessage("deploying.grid.grouper.title"), "",
						"gridgrouper", this.model), new Condition() {

					public boolean evaluate(WizardModel m) {
						CaGridInstallerModel model = (CaGridInstallerModel) m;
						return "true".equals(model.getState().get(
								Constants.INSTALL_GRID_GROUPER));
					}

				}));

		installStep.getTasks().add(
				new SaveSettingsTask(this.model
						.getMessage("saving.settings.title"), ""));

		incrementProgress();

		this.model.add(installStep);

		this.model.add(new InstallationCompleteStep(this.model
				.getMessage("installation.complete.title"), ""));

	}

	private void addCommonNewCACertFields(PropertyConfigurationStep step,
			String caCertPathProp, String caKeyPathProp, String caKeyPwdProp,
			String caDnProp, String caDaysValidProp) {

		addCommonCACertFields(step, caCertPathProp, caKeyPathProp,
				caKeyPwdProp, false);

		step.getOptions().add(
				new TextPropertyConfigurationOption(caDnProp, this.model
						.getMessage("ca.cert.info.dn"), getProperty(this.model
						.getState(), caDnProp, "O=org,OU=unit,CN=name"), true));
		step.getOptions().add(
				new TextPropertyConfigurationOption(caDaysValidProp, this.model
						.getMessage("ca.cert.info.days.valid"), getProperty(
						this.model.getState(), caDaysValidProp, "1000"), true));
	}

	private void addCommonCACertFields(PropertyConfigurationStep step,
			String caCertPathProp, String caKeyPathProp, String caKeyPwdProp,
			boolean validate) {

		step.getOptions().add(
				new TextPropertyConfigurationOption(caCertPathProp, this.model
						.getMessage("ca.cert.info.cert.path"), getProperty(
						this.model.getState(), caCertPathProp,
						"temp/certs/ca.cert"), true));
		step.getOptions().add(
				new TextPropertyConfigurationOption(caKeyPathProp, this.model
						.getMessage("ca.cert.info.key.path"), getProperty(
						this.model.getState(), caKeyPathProp,
						"temp/certs/ca.key"), true));
		step.getOptions().add(
				new PasswordPropertyConfigurationOption(caKeyPwdProp,
						this.model.getMessage("ca.cert.info.key.pwd"),
						this.model.getState().get(caKeyPwdProp), true));

		if (validate) {
			step.getValidators().add(
					new PathExistsValidator(caCertPathProp, this.model
							.getMessage("error.cert.file.not.found")));
			step.getValidators().add(
					new PathExistsValidator(caKeyPathProp, this.model
							.getMessage("error.key.file.not.found")));
			step.getValidators().add(
					new KeyAccessValidator(caKeyPathProp, caKeyPwdProp,
							this.model.getMessage("error.key.no.access")));
		}
	}

	private void addDBConfigPropertyOptions(PropertyConfigurationStep step,
			String propPrefix, String dbIdDefault) {
		step.getOptions().add(
				new TextPropertyConfigurationOption(propPrefix + "db.host",
						this.model.getMessage(propPrefix + "db.host"),
						getProperty(this.model.getState(), propPrefix
								+ "db.host", "localhost"), true));
		step.getOptions().add(
				new TextPropertyConfigurationOption(propPrefix + "db.port",
						this.model.getMessage(propPrefix + "db.port"),
						getProperty(this.model.getState(), propPrefix
								+ "db.port", "3306"), true));
		step.getOptions().add(
				new TextPropertyConfigurationOption(propPrefix + "db.id",
						this.model.getMessage(propPrefix + "db.id"),
						getProperty(this.model.getState(),
								propPrefix + "db.id", dbIdDefault), true));
		step.getOptions().add(
				new TextPropertyConfigurationOption(propPrefix + "db.username",
						this.model.getMessage(propPrefix + "db.username"),
						getProperty(this.model.getState(), propPrefix
								+ "db.username", "root"), true));
		step.getOptions().add(
				new PasswordPropertyConfigurationOption(propPrefix
						+ "db.password", this.model.getMessage(propPrefix
						+ "db.password"), this.model.getState().get(
						propPrefix + "db.password"), true));
		step.getValidators().add(
				new MySqlDBConnectionValidator(propPrefix + "db.host",
						propPrefix + "db.port", propPrefix + "db.username",
						propPrefix + "db.password", "select 1", this.model
								.getMessage("db.validation.failed")));
	}

	private void addCommonDorianCAConfigFields(PropertyConfigurationStep step) {

		step.getOptions().add(
				new PasswordPropertyConfigurationOption(
						Constants.DORIAN_CA_KEY_PWD, this.model
								.getMessage("dorian.ca.cert.info.key.pwd"),
						this.model.getState().get(Constants.DORIAN_CA_KEY_PWD),
						true));
		step.getOptions().add(
				new TextPropertyConfigurationOption(Constants.DORIAN_CA_OID,
						this.model.getMessage("dorian.ca.cert.info.oid"),
						this.model.getState().get(Constants.DORIAN_CA_OID),
						false));
		step
				.getOptions()
				.add(
						new ListPropertyConfigurationOption(
								Constants.DORIAN_CA_USERKEY_SIZE,
								this.model
										.getMessage("dorian.ca.cert.info.userkey.size"),
								new String[] { String.valueOf(2048),
										String.valueOf(1024),
										String.valueOf(512) }, true));
		step
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_AUTORENEW_YEARS,
								this.model
										.getMessage("dorian.ca.cert.info.autorenew.years"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_AUTORENEW_YEARS,
										"1"), true));
		step
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_AUTORENEW_MONTHS,
								this.model
										.getMessage("dorian.ca.cert.info.autorenew.months"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_AUTORENEW_MONTHS,
										"0"), true));
		step
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_AUTORENEW_DAYS,
								this.model
										.getMessage("dorian.ca.cert.info.autorenew.days"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_AUTORENEW_DAYS, "0"),
								true));
		step
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_AUTORENEW_HOURS,
								this.model
										.getMessage("dorian.ca.cert.info.autorenew.hours"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_AUTORENEW_HOURS,
										"0"), true));
		step
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_AUTORENEW_MINUTES,
								this.model
										.getMessage("dorian.ca.cert.info.autorenew.minutes"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_AUTORENEW_MINUTES,
										"0"), true));
		step
				.getOptions()
				.add(
						new TextPropertyConfigurationOption(
								Constants.DORIAN_CA_AUTORENEW_SECONDS,
								this.model
										.getMessage("dorian.ca.cert.info.autorenew.seconds"),
								getProperty(this.model.getState(),
										Constants.DORIAN_CA_AUTORENEW_SECONDS,
										"0"), true));

	}

	private String getProperty(Map state, String propName, String defaultValue) {
		String value = (String) state.get(propName);
		if (value == null || value.trim().length() == 0) {
			value = defaultValue;
		}
		return value;
	}

	private void checkGlobusDeployed(Map state) {
		String globusDeployed = "false";
		boolean tomcatInstalled = "true".equals(state
				.get(Constants.TOMCAT_INSTALLED));
		if (tomcatInstalled) {
			File wsrfDir = new File((String) state.get(Constants.TOMCAT_HOME)
					+ "/webapps/wsrf");
			globusDeployed = wsrfDir.exists() ? "true" : "false";
		}
		// if ("false".equals(globusDeployed)) {
		// state.put(Constants.DEPLOY_GLOBUS, "true");
		// }
		state.put(Constants.GLOBUS_DEPLOYED, globusDeployed);
	}

	private void addUnzipInstallTask(RunTasksStep installStep,
			String downloadMsg, String installMsg, String desc,
			String downloadUrlProp, String tempFileNameProp,
			String installDirPathProp, String dirNameProp, String homeProp,
			final String installProp) {

		Condition c = new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(installProp));
			}
		};
		installStep.getTasks().add(
				new ConditionalTask(new DownloadFileTask(downloadMsg, desc,
						downloadUrlProp, tempFileNameProp,
						Constants.CONNECT_TIMEOUT), c));
		installStep.getTasks().add(
				new ConditionalTask(new UnzipInstallTask(installMsg, desc,
						tempFileNameProp, installDirPathProp, dirNameProp,
						homeProp), c));
	}

	private void addInstallInfoStep(DynamicStatefulWizardModel m,
			String homeProp, String defaultDirName, String titleProp,
			String descProp, String installDirPath, final String installProp) {

		File homeFile = null;
		String home = (String) m.getState().get(homeProp);
		if (home != null) {
			homeFile = new File(home);
		} else {
			homeFile = new File(System.getProperty("user.home")
					+ File.separator + "packages" + File.separator
					+ defaultDirName);
		}
		PropertyConfigurationStep installInfoStep = new PropertyConfigurationStep(
				m.getMessage(titleProp), m.getMessage(descProp));
		installInfoStep.getOptions().add(
				new TextPropertyConfigurationOption(installDirPath, m
						.getMessage("directory"), homeFile.getParentFile()
						.getAbsolutePath(), true));
		installInfoStep.getValidators().add(
				new CreateFilePermissionValidator(homeProp, m
						.getMessage("error.permission.directory.create")));
		m.add(installInfoStep, new Condition() {

			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(installProp));
			}

		});
	}

	private void addCheckInstallStep(DynamicStatefulWizardModel m,
			String titleProp, String descProp, String configProp,
			final String installedProp) {

		Condition c = new Condition() {
			public boolean evaluate(WizardModel m) {
				return true;
			}
		};
		addCheckInstallStep(m, titleProp, descProp, configProp, installedProp,
				c);
	}

	private void addCheckInstallStep(DynamicStatefulWizardModel m,
			String titleProp, String descProp, String configProp,
			final String installedProp, Condition c1) {

		Condition c = new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return "true".equals(model.getState().get(installedProp));
			}

		};
		addCheckInstallStep(m, titleProp, descProp, configProp,
				new AndCondition(c, c1));
	}

	private void addCheckInstallStep(DynamicStatefulWizardModel m,
			String titleProp, String descProp, String configProp, Condition c) {
		PropertyConfigurationStep checkInstallStep = new PropertyConfigurationStep(
				m.getMessage(titleProp), m.getMessage(descProp));
		checkInstallStep.getOptions().add(
				new BooleanPropertyConfigurationOption(configProp, m
						.getMessage("yes"), false, false));
		m.add(checkInstallStep, c);
	}

	public void run() {
		Wizard wizard = new Wizard(this.model);
		wizard.showInFrame("caGrid Installation Wizard");
	}

	public static boolean checkInstalled(String progName, String envName,
			String propName, String propInstalledName, String propInstall,
			Map props) {
		boolean installed = false;
		logger.info("Checking if " + progName + " is installed");
		String home = (String) props.get(propName);
		if (home == null) {
			if (envName != null) {
				logger
						.info(progName
								+ " was not found in initial properties. Checking environment variable: "
								+ envName);
				home = System.getenv(envName);
			}
		} else {
			logger.info(progName + " found at '" + home + "'");
		}
		if (home != null) {
			File f = new File(home);
			if (!f.exists()) {
				logger.info(home + " does not exist");
				home = null;
			}
		}
		if (home != null) {
			logger.info(progName + " found at '" + home + "'");
			logger.info("Setting " + propName + " = " + home);
			props.put(propName, home);
			props.put(propInstalledName, "true");
			installed = true;
		} else {
			logger.info(progName + " is not installed");
			props.put(propInstalledName, "false");
			props.put(propInstall, "true");
		}
		return installed;
	}

	private class DownloadPropsThread extends Thread {

		private Exception ex;

		private String fromUrl;

		private String toFile;

		DownloadPropsThread(String fromUrl, String toFile) {
			this.fromUrl = fromUrl;
			;
			this.toFile = toFile;
		}

		Exception getException() {
			return this.ex;
		}

		public void run() {
			try {
				File to = new File(this.toFile);
				URL from = new URL(this.fromUrl);
				Utils.downloadFile(from, to);

			} catch (Exception ex) {
				this.ex = ex;
			}
		}

	}

}
