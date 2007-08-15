/**
 * 
 */
package org.cagrid.installer;

import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.steps.RunTasksStep;
import org.cagrid.installer.tasks.ConditionalTask;
import org.cagrid.installer.tasks.DownloadFileTask;
import org.cagrid.installer.tasks.UnTarInstallTask;
import org.cagrid.installer.workflow.DeployActiveBPELTask;
import org.pietschy.wizard.WizardModel;
import org.pietschy.wizard.models.Condition;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ActiveBPELComponentInstaller extends
		AbstractDownloadedComponentInstaller {

	/**
	 * 
	 */
	public ActiveBPELComponentInstaller() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cagrid.installer.AbstractDownloadedComponentInstaller#getComponentId()
	 */
	@Override
	protected String getComponentId() {
		return "activebpel";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cagrid.installer.AbstractDownloadedComponentInstaller#getShouldCheckCondition()
	 */
	@Override
	protected Condition getShouldCheckCondition() {
		return new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return model.isTrue(Constants.INSTALL_WORKFLOW)
						&& model.isActiveBPELInstalled();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cagrid.installer.AbstractDownloadedComponentInstaller#getShouldInstallCondition()
	 */
	@Override
	protected Condition getShouldInstallCondition() {
		return new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return model.isTrue(Constants.INSTALL_WORKFLOW)
						&& (!model.isActiveBPELInstalled() || model
								.isTrue(Constants.INSTALL_ACTIVEBPEL));
			}
		};
	}

	public void addInstallDownloadedComponentTasks(CaGridInstallerModel model,
			RunTasksStep installStep) {

		addUnTarInstallTask(installStep, model
				.getMessage("downloading.activebpel.title"), model
				.getMessage("installing.activebpel.title"), "",
				Constants.ACTIVEBPEL_DOWNLOAD_URL,
				Constants.ACTIVEBPEL_TEMP_FILE_NAME,
				Constants.ACTIVEBPEL_INSTALL_DIR_PATH,
				Constants.ACTIVEBPEL_DIR_NAME, Constants.ACTIVEBPEL_HOME,
				Constants.INSTALL_ACTIVEBPEL, Integer.parseInt(model
						.getProperty("activebpel.bytes")));

		installStep.getTasks().add(
				new ConditionalTask(new DeployActiveBPELTask(model
						.getMessage("installing.activebpel.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return model
										.isTrue(Constants.INSTALL_ACTIVEBPEL)
										&& model
												.isTrue(Constants.INSTALL_WORKFLOW);
							}

						}));

	}

	private void addUnTarInstallTask(RunTasksStep installStep,
			String downloadMsg, String installMsg, String desc,
			String downloadUrlProp, String tempFileNameProp,
			String installDirPathProp, String dirNameProp, String homeProp,
			final String installProp, int totalBytes) {

		Condition c = new Condition() {
			public boolean evaluate(WizardModel m) {
				CaGridInstallerModel model = (CaGridInstallerModel) m;
				return model.isTrue(installProp)
						&& model.isTrue(Constants.INSTALL_WORKFLOW);
			}
		};
		installStep.getTasks().add(
				new ConditionalTask(new DownloadFileTask(downloadMsg, desc,
						downloadUrlProp, tempFileNameProp,
						Constants.CONNECT_TIMEOUT, totalBytes), c));
		installStep.getTasks().add(
				new ConditionalTask(new UnTarInstallTask(installMsg, desc,
						tempFileNameProp, installDirPathProp, dirNameProp,
						homeProp), c));
	}

}
