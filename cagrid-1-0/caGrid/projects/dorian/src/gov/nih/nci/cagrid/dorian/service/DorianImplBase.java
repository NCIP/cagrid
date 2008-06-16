package gov.nih.nci.cagrid.dorian.service;

import java.rmi.RemoteException;

import javax.naming.InitialContext;

import org.apache.axis.MessageContext;
import org.globus.wsrf.Constants;
import org.globus.wsrf.ResourceHome;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * Provides some simple accessors for the Impl.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public abstract class DorianImplBase {
	
	public DorianImplBase() throws RemoteException {
	
	}
	
	public DorianConfiguration getConfiguration() throws Exception {
		return DorianConfiguration.getConfiguration();
	}
	
	
	public gov.nih.nci.cagrid.dorian.service.globus.resource.DorianResourceHome getResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("home");
		return (gov.nih.nci.cagrid.dorian.service.globus.resource.DorianResourceHome)resource;
	}

	
	
	
	
	protected ResourceHome getResourceHome(String resourceKey) throws Exception {
		MessageContext ctx = MessageContext.getCurrentContext();

		ResourceHome resourceHome = null;
		
		String servicePath = ctx.getTargetService();

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/" + resourceKey;
		try {
			javax.naming.Context initialContext = new InitialContext();
			resourceHome = (ResourceHome) initialContext.lookup(jndiName);
		} catch (Exception e) {
			throw new Exception("Unable to instantiate resource home. : " + resourceKey, e);
		}

		return resourceHome;
	}


}

