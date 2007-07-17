/**
 * 
 */
package org.cagrid.installer.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.InstallerUtils;
import org.pietschy.wizard.models.DynamicModel;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class DynamicStatefulWizardModel extends DynamicModel implements

CaGridInstallerModel {

	private static final Log logger = LogFactory
			.getLog(DynamicStatefulWizardModel.class);

	private PropertyChangeEventProviderMap state;

	private ResourceBundle messages;

	/**
	 * 
	 */
	public DynamicStatefulWizardModel() {
		this(null, null);
	}

	public DynamicStatefulWizardModel(Map state) {
		this(state, null);
	}

	public DynamicStatefulWizardModel(Map state, ResourceBundle messages) {

		if (state == null) {
			this.state = new PropertyChangeEventProviderMap(new HashMap());
		} else {
			this.state = new PropertyChangeEventProviderMap(state);
		}
		this.messages = messages;
		if (this.messages == null) {
			// Load messages
			try {
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

	public Map<String, String> getState() {
		return state;
	}

	public String getMessage(String key) {
		String message = null;
		if (this.messages != null) {
			message = this.messages.getString(key);
		}
		return message;
	}

	private class PropertyChangeEventProviderMap extends HashMap {
		private PropertyChangeSupport pcs = new PropertyChangeSupport(
				DynamicStatefulWizardModel.this);

		PropertyChangeEventProviderMap(Map map) {
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
		return "true".equals(getProperty(propName));
	}

	public boolean isTomcatContainer() {
		return getMessage("container.type.tomcat").equals(
				getProperty(Constants.CONTAINER_TYPE));
	}

	public String getProperty(String propName) {
		return (String) getState().get(propName);
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

	public void setPreviousActive(boolean b) {
		setPreviousAvailable(b);
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

}
