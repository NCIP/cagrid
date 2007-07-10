/**
 * 
 */
package org.cagrid.installer.tasks;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.InstallerUtils;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ConfigureDorianTask extends CaGridAntTask {

	private static final Log logger = LogFactory
			.getLog(ConfigureDorianTask.class);

	/**
	 * @param name
	 * @param description
	 */
	public ConfigureDorianTask(String name, String description) {
		super(name, description, null);
	}

	protected Object runAntTask(Map state, String target, Map env,
			Properties sysProps) throws Exception {

		Map m = new HashMap(state);
		m.put(Constants.BUILD_FILE_PATH, InstallerUtils
				.getScriptsBuildFilePath());
		String serviceDestDir = InstallerUtils.getServiceDestDir(state);
		new AntTask("", "", "configure-dorian-conf", env, sysProps).execute(m);

		if ("true".equals(state.get(Constants.DORIAN_CA_PRESENT))) {
			m.put(Constants.BUILD_FILE_PATH, serviceDestDir
					+ "/dorian/build.xml");
			sysProps.setProperty("cacert.input", (String) state
					.get(Constants.DORIAN_CA_CERT_PATH));
			sysProps.setProperty("cakey.input", (String) state
					.get(Constants.DORIAN_CA_KEY_PATH));
			sysProps.setProperty("password.input", (String) state
					.get(Constants.DORIAN_CA_KEY_PWD));
			new AntTask("", "", "importCA", env, sysProps).execute(m);
		}
		m.put(Constants.BUILD_FILE_PATH, serviceDestDir + "/dorian/build.xml");
		sysProps.setProperty("host.input", (String) state
				.get(Constants.SERVICE_HOSTNAME));
		File dorianHostCredDir = new File((String) state
				.get(Constants.DORIAN_HOST_CRED_DIR));
		sysProps.setProperty("dir.input", dorianHostCredDir.getAbsolutePath());
		new AntTask("", "", "createDorianHostCredentials", env, sysProps)
				.execute(m);
		new AntTask("", "", "configureGlobusToTrustDorian", env, sysProps)
				.execute(m);

		return null;
	}
}
