/**
 * 
 */
package org.cagrid.installer.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.steps.RunTasksStep;
import org.cagrid.installer.util.InstallerUtils;
import org.pietschy.wizard.models.DynamicModel;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class CaGridInstallerModelImpl extends DynamicModel implements

CaGridInstallerModel {

	private static final Log logger = LogFactory
			.getLog(CaGridInstallerModelImpl.class);

	private PropertyChangeEventProviderMap state;

	private ResourceBundle messages;

	/**
	 * 
	 */
	public CaGridInstallerModelImpl() {
		this(null, null);
	}

	public CaGridInstallerModelImpl(Map<String, String> state) {
		this(state, null);
	}

	public CaGridInstallerModelImpl(Map<String, String> state,
			ResourceBundle messages) {

		if (state == null) {
			this.state = new PropertyChangeEventProviderMap(
					new HashMap<String, String>());
		} else {
			this.state = new PropertyChangeEventProviderMap(state);
		}
		this.messages = messages;
		if (this.messages == null) {
			// Load messages
			try {
				// TODO: support international messages
				this.messages = ResourceBundle.getBundle(Constants.MESSAGES,
						Locale.US);
			} catch (Exception ex) {
				throw new RuntimeException("Error loading messages: "
						+ ex.getMessage());
			}
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		super.addPropertyChangeListener(l);
		this.state.addPropertyChangeListener(l);
	}

	// public Map<String, String> getState() {
	// return state;
	// }

	public String getMessage(String key) {
		String message = null;
		if (this.messages != null) {
			message = this.messages.getString(key);
		}
		return message;
	}

	private class PropertyChangeEventProviderMap extends HashMap {
		private PropertyChangeSupport pcs = new PropertyChangeSupport(
				CaGridInstallerModelImpl.this);

		PropertyChangeEventProviderMap(Map<String, String> map) {
			super(map);
		}

		void addPropertyChangeListener(PropertyChangeListener l) {
			this.pcs.addPropertyChangeListener(l);
		}

		public Object put(Object key, Object newValue) {

			Object oldValue = get(key);
			if (oldValue != null) {
				this.pcs.firePropertyChange((String) oldValue, oldValue,
						newValue);
			}
			logger.info("Setting " + key + " = " + newValue);
			super.put(key, newValue);
			return oldValue;
		}

		public void putAll(Map m) {
			for (Iterator i = m.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				put(entry.getKey(), entry.getValue());
			}
		}
	}

	public boolean isTomcatConfigurationRequired() {
		return isTomcatContainer()
				&& (isTrue(Constants.REDEPLOY_GLOBUS) || !isTrue(Constants.GLOBUS_DEPLOYED));

	}

	public boolean isTrue(String propName) {
		return Constants.TRUE.equals(getProperty(propName));
	}

	public boolean isTomcatContainer() {
		return getMessage("container.type.tomcat").equals(
				getProperty(Constants.CONTAINER_TYPE));
	}

	public String getProperty(String propName) {
		return (String) this.state.get(propName);
	}

	public boolean isSecurityConfigurationRequired() {
		return isTrue(Constants.USE_SECURE_CONTAINER)
				&& (isTrue(Constants.RECONFIGURE_GLOBUS)
						|| isTrue(Constants.REDEPLOY_GLOBUS)
						|| isTomcatContainer()
						&& !isTrue(Constants.GLOBUS_DEPLOYED) || !isTomcatContainer()
						&& !isTrue(Constants.GLOBUS_CONFIGURED));
	}

	public boolean isSet(String propName) {
		return !isEmpty(getProperty(propName));
	}

	public boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	public boolean isCAGenerationRequired() {
		return isSecurityConfigurationRequired()
				&& !isTrue(Constants.INSTALL_DORIAN)
				&& !isTrue(Constants.SERVICE_CERT_PRESENT)
				&& !isTrue(Constants.CA_CERT_PRESENT);
	}

	public boolean isServiceCertGenerationRequired() {
		return isSecurityConfigurationRequired()
				&& !isTrue(Constants.INSTALL_DORIAN)
				&& !isTrue(Constants.SERVICE_CERT_PRESENT);

	}

	public boolean isAuthnSvcCAGenerationRequired() {
		return !isTrue(Constants.AUTHN_SVC_CA_PRESENT)
				&& !isTrue(Constants.AUTHN_SVC_USE_GEN_CA);

	}

	public boolean isEqual(String value, String propName) {
		return value.equals(getProperty(propName));
	}

	public void refreshModelState() {
		super.refreshModelState();
		if (getActiveStep() instanceof RunTasksStep) {
			RunTasksStep rts = (RunTasksStep) getActiveStep();
			setPreviousAvailable(!rts.isDeactivePrevious());
		}
	}

	public boolean isConfigureGlobusRequired() {
		return !isTomcatContainer()
				&& isTrue(Constants.USE_SECURE_CONTAINER)
				&& (isTrue(Constants.RECONFIGURE_GLOBUS) || !isTrue(Constants.GLOBUS_CONFIGURED));
	}

	public boolean isDeployGlobusRequired() {
		return isTomcatContainer()
				&& (isTrue(Constants.REDEPLOY_GLOBUS) || !isTrue(Constants.GLOBUS_DEPLOYED));
	}

	public void setDeactivatePrevious(boolean b) {
		setPreviousAvailable(!b);
	}

	public void unsetProperty(String propName) {
		this.state.remove(propName);
	}

	public void setProperty(String propName, String propValue) {
		this.state.put(propName, propValue);
	}

	public String getProperty(String propName, String defaultValue) {
		String value = (String) this.state.get(propName);
		return InstallerUtils.isEmpty(value) ? defaultValue : value;
	}

	public String getServiceDestDir() {
		return getProperty(Constants.TEMP_DIR_PATH) + "/services";
	}

	public boolean isSecureContainerRequired() {
		return isTrue(Constants.INSTALL_DORIAN)
				|| isTrue(Constants.INSTALL_GTS)
				|| isTrue(Constants.INSTALL_AUTHN_SVC)
				|| isTrue(Constants.INSTALL_GRID_GROUPER)
				|| isTrue(Constants.INSTALL_BROWSER);
	}

	public Map<String, String> getStateMap() {
		return new HashMap<String, String>(this.state);
	}

	public boolean isConfigureContainerSelected() {
		return isTrue(Constants.CONFIGURE_CONTAINER)
				|| isTrue(Constants.INSTALL_SERVICES)
				|| isTrue(Constants.INSTALL_PORTAL)
				|| isTrue(Constants.INSTALL_BROWSER);
	}

	public boolean isSyncGTSInstalled() {
		File syncDescFile = new File(
				getProperty(Constants.TOMCAT_HOME)
						+ "/webapps/wsrf/WEB-INF/etc/cagrid_SyncGTS/sync-description.xml");
		return syncDescFile.exists();
	}
}
