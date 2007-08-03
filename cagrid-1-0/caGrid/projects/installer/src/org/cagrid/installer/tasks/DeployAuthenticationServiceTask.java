/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.InstallerUtils;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class DeployAuthenticationServiceTask extends DeployServiceTask {

	/**
	 * @param name
	 * @param description
	 * @param serviceName
	 * @param model
	 */
	public DeployAuthenticationServiceTask(String name, String description) {
		super(name, description, "authentication-service");
	}

	protected Object runAntTask(CaGridInstallerModel model, String target, Map<String,String> env,
			Properties sysProps) throws Exception {

		String svcBuildFilePath = getBuildFilePath(model);
		String installerBuildFilePath = InstallerUtils
				.getScriptsBuildFilePath();

		model.setProperty(Constants.BUILD_FILE_PATH, installerBuildFilePath);

		// Generate the AuthnSvc CA
		if (model.isAuthnSvcCAGenerationRequired()) {
			new AntTask("", "", "generate-authn-service-ca", env, sysProps)
					.execute(model);
			InstallerUtils.copyCACertToTrustStore(model
					.getProperty(Constants.AUTHN_SVC_CA_CERT_PATH),
					"AUTHNSVC_CA.0");
		}

		// Modify deploy.properties
		new AntTask("", "", "set-authn-service-idp-properties", env,
				sysProps).execute(model);

		// Deploy the service
		// TODO: setting these properties shouldn't be necessary, but the build
		// file
		// is not including deploy.properties at the right time.
//		sysProps.setProperty("csm.app.context", (String) state
//				.get(Constants.AUTHN_SVC_CSM_CTX));
//		sysProps.setProperty("saml.provider.crt", (String) state
//				.get(Constants.AUTHN_SVC_CA_CERT_PATH));
//		sysProps.setProperty("saml.provider.key", (String) state
//				.get(Constants.AUTHN_SVC_CA_KEY_PATH));
//		sysProps.setProperty("saml.provider.pwd", (String) state
//				.get(Constants.AUTHN_SVC_CA_KEY_PWD));
		model.setProperty(Constants.BUILD_FILE_PATH, svcBuildFilePath);
		super.runAntTask(model, target, env, sysProps);

		model.setProperty(Constants.BUILD_FILE_PATH, installerBuildFilePath);

		String antTarget = "deployTomcatEndorsedJars";
		if (model.getMessage("container.type.globus").equals(
				model.getProperty(Constants.CONTAINER_TYPE))) {
			antTarget = "deployGlobusEndorsedJars";
		}
		sysProps.setProperty("service.name", "authentication-service");
		new AntTask("", "", antTarget, env, sysProps).execute(model);

		if (model.isEqual(Constants.AUTHN_SVC_CRED_PROVIDER_TYPE_RDBMS,
				Constants.AUTHN_SVC_CRED_PROVIDER_TYPE)) {
			// Copy driver
			if (model.isTomcatContainer()) {
				new AntTask("", "", "copy-jdbc-driver-to-tomcat", env, sysProps)
						.execute(model);
			} else {
				new AntTask("", "", "copy-jdbc-driver-to-globus", env, sysProps)
						.execute(model);
			}
		}

		// Generate JAAS config
		sysProps.setProperty("jaas.config.path", System
				.getProperty("user.home")
				+ "/.java.login.config");
		model.setProperty("authn.svc.rdbms.encryption.enabled", model
				.isTrue(Constants.AUTHN_SVC_RDBMS_ENCRYPTION_ENABLED) ? "YES"
				: "NO");
		new AntTask("", "", "create-jaas-config", env, sysProps).execute(model);

		return null;
	}

}
