package gov.nih.nci.cagrid.fqp.service;

import gov.nih.nci.cagrid.introduce.servicetools.ServiceConfiguration;

import org.globus.wsrf.config.ContainerConfig;
import java.io.File;
import javax.naming.InitialContext;

import org.apache.axis.MessageContext;
import org.globus.wsrf.Constants;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 * 
 * This class holds all service properties which were defined for the service to have
 * access to.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class FederatedQueryProcessorConfiguration implements ServiceConfiguration {

	public static FederatedQueryProcessorConfiguration  configuration = null;

	public static FederatedQueryProcessorConfiguration getConfiguration() throws Exception {
		if (FederatedQueryProcessorConfiguration.configuration != null) {
			return FederatedQueryProcessorConfiguration.configuration;
		}
		MessageContext ctx = MessageContext.getCurrentContext();

		String servicePath = ctx.getTargetService();

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/serviceconfiguration";
		try {
			javax.naming.Context initialContext = new InitialContext();
			FederatedQueryProcessorConfiguration.configuration = (FederatedQueryProcessorConfiguration) initialContext.lookup(jndiName);
		} catch (Exception e) {
			throw new Exception("Unable to instantiate service configuration.", e);
		}

		return FederatedQueryProcessorConfiguration.configuration;
	}
	
	private String etcDirectoryPath;
	
	
	private String initialResultLeaseInMinutes;
	
	private String threadPoolSize;
	
	private String maxRetries;
	
	private String maxRetryTimeout;
	
	private String maxTargetServicesPerQuery;
	
	
	public String getEtcDirectoryPath() {
		return ContainerConfig.getBaseDirectory() + File.separator + etcDirectoryPath;
	}
	
	public void setEtcDirectoryPath(String etcDirectoryPath) {
		this.etcDirectoryPath = etcDirectoryPath;
	}

	
	public String getInitialResultLeaseInMinutes() {
		return initialResultLeaseInMinutes;
	}
	
	
	public void setInitialResultLeaseInMinutes(String initialResultLeaseInMinutes) {
		this.initialResultLeaseInMinutes = initialResultLeaseInMinutes;
	}

	
	public String getThreadPoolSize() {
		return threadPoolSize;
	}
	
	
	public void setThreadPoolSize(String threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	
	public String getMaxRetries() {
		return maxRetries;
	}
	
	
	public void setMaxRetries(String maxRetries) {
		this.maxRetries = maxRetries;
	}

	
	public String getMaxRetryTimeout() {
		return maxRetryTimeout;
	}
	
	
	public void setMaxRetryTimeout(String maxRetryTimeout) {
		this.maxRetryTimeout = maxRetryTimeout;
	}

	
	public String getMaxTargetServicesPerQuery() {
		return maxTargetServicesPerQuery;
	}
	
	
	public void setMaxTargetServicesPerQuery(String maxTargetServicesPerQuery) {
		this.maxTargetServicesPerQuery = maxTargetServicesPerQuery;
	}

	
}
