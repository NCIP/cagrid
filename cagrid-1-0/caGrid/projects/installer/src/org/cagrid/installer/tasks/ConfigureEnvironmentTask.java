/**
 * 
 */
package org.cagrid.installer.tasks;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.steps.Constants;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ConfigureEnvironmentTask extends BasicTask {

	private static final Log logger = LogFactory
			.getLog(ConfigureEnvironmentTask.class);

	/**
	 * 
	 */
	public ConfigureEnvironmentTask(String name, String description) {
		super(name, description);
	}

	protected Object internalExecute(Map state) throws Exception {
		state.put(Constants.GLOBUS_DIR_PATH, state.get(Constants.GLOBUS_HOME));
		state.put(Constants.TOMCAT_DIR_PATH, state.get(Constants.TOMCAT_HOME));

		if ("true".equals(state.get(Constants.USE_SECURE_CONTAINER))) {

			state.put(Constants.TOMCAT_KEY, state
					.get(Constants.SERVICE_KEY_PATH));
			state.put(Constants.TOMCAT_CERT, state
					.get(Constants.SERVICE_CERT_PATH));
			state.put(Constants.TOMCAT_KEY_DEST, state
					.get(Constants.TOMCAT_HOME)
					+ "/conf/certs/server.key");
			state.put(Constants.TOMCAT_CERT_DEST, state
					.get(Constants.TOMCAT_HOME)
					+ "/conf/certs/server.cert");

		}

		logger.debug(state);

		return null;
	}

}
