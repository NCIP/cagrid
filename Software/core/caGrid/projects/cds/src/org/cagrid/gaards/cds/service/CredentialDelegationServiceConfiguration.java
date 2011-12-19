package org.cagrid.gaards.cds.service;

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
 * @created by Introduce Toolkit version 1.4.1
 * 
 */
public class CredentialDelegationServiceConfiguration implements ServiceConfiguration {

	public static CredentialDelegationServiceConfiguration  configuration = null;
    public String etcDirectoryPath;
    	
	public static CredentialDelegationServiceConfiguration getConfiguration() throws Exception {
		if (CredentialDelegationServiceConfiguration.configuration != null) {
			return CredentialDelegationServiceConfiguration.configuration;
		}
		MessageContext ctx = MessageContext.getCurrentContext();

		String servicePath = ctx.getTargetService();

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/serviceconfiguration";
		try {
			javax.naming.Context initialContext = new InitialContext();
			CredentialDelegationServiceConfiguration.configuration = (CredentialDelegationServiceConfiguration) initialContext.lookup(jndiName);
		} catch (Exception e) {
			throw new Exception("Unable to instantiate service configuration.", e);
		}

		return CredentialDelegationServiceConfiguration.configuration;
	}
	

	
	private String cdsConfiguration;
	
	private String cdsProperties;
	
	
    public String getEtcDirectoryPath() {
		return ContainerConfig.getBaseDirectory() + File.separator + etcDirectoryPath;
	}
	
	public void setEtcDirectoryPath(String etcDirectoryPath) {
		this.etcDirectoryPath = etcDirectoryPath;
	}


	
	public String getCdsConfiguration() {
		return ContainerConfig.getBaseDirectory() + File.separator + cdsConfiguration;
	}
	
	
	public void setCdsConfiguration(String cdsConfiguration) {
		this.cdsConfiguration = cdsConfiguration;
	}

	
	public String getCdsProperties() {
		return ContainerConfig.getBaseDirectory() + File.separator + cdsProperties;
	}
	
	
	public void setCdsProperties(String cdsProperties) {
		this.cdsProperties = cdsProperties;
	}

	
}
