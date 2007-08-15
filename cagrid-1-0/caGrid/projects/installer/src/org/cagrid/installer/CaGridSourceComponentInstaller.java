/**
 * 
 */
package org.cagrid.installer;

import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.steps.RunTasksStep;
import org.cagrid.installer.tasks.CompileCaGridTask;
import org.cagrid.installer.tasks.ConditionalTask;
import org.cagrid.installer.tasks.ConfigureTargetGridTask;
import org.pietschy.wizard.WizardModel;
import org.pietschy.wizard.models.Condition;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class CaGridSourceComponentInstaller extends
		AbstractDownloadedComponentInstaller {

	/**
	 * 
	 */
	public CaGridSourceComponentInstaller() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cagrid.installer.AbstractDownloadedComponentInstaller#getComponentId()
	 */
	@Override
	protected String getComponentId() {
		return "cagrid";
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
				return !model.isCaGridInstalled()
						|| model.isTrue(Constants.INSTALL_CAGRID);
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
				return model.isCaGridInstalled();
			}
		};
	}

	public void addInstallDownloadedComponentTasks(CaGridInstallerModel model,
			RunTasksStep installStep) {

		super.addInstallDownloadedComponentTasks(model, installStep);

		installStep.getTasks().add(
				new ConditionalTask(new CompileCaGridTask(model
						.getMessage("compiling.cagrid.title"), ""),
						new Condition() {

							public boolean evaluate(WizardModel m) {
								CaGridInstallerModel model = (CaGridInstallerModel) m;
								return model.isTrue(Constants.INSTALL_CAGRID);
							}

						}));

		// TODO: figure out why this fails on Mac sometimes
		ConfigureTargetGridTask configTargetGridTask = new ConfigureTargetGridTask(
				model.getMessage("configuring.target.grid"), "");
		configTargetGridTask.setAbortOnError(false);
		installStep.getTasks().add(
				new ConditionalTask(configTargetGridTask, new Condition() {
					public boolean evaluate(WizardModel m) {
						CaGridInstallerModel model = (CaGridInstallerModel) m;
						return !model.isSet(Constants.TARGET_GRID)
								|| model.isTrue(Constants.RECONFIGURE_CAGRID)
								|| model.isTrue(Constants.INSTALL_CAGRID);
					}
				}));
	}

}
