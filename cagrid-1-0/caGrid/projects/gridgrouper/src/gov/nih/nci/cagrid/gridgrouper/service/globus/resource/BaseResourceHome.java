package gov.nih.nci.cagrid.gridgrouper.service.globus.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.SingletonResourceHome;
import org.globus.wsrf.jndi.Initializable;


/**
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella</A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster</A>
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings</A>
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * 
 * @version $Id: GridGrouperBaseTreeNode.java,v 1.1 2006/08/04 03:49:26 langella
 *          Exp $
 */
public class BaseResourceHome extends SingletonResourceHome implements Initializable {

	static final Log logger = LogFactory.getLog(BaseResourceHome.class);


	public Resource findSingleton() {
		logger.info("Creating a single resource.");
		try {
			BaseResource resource = new BaseResource();
			resource.initialize();
			return resource;
		} catch (Exception e) {
			logger.error("Exception when creating the resource: " + e);
			return null;
		}
	}


	public Resource find(ResourceKey key) throws ResourceException {
		BaseResource resource = (BaseResource) super.find(key);
		// each time the resource is looked up, do a lazy refreash of
		// registration.
		resource.refreshRegistration(false);
		return resource;
	}


	/**
	 * Initialze the singleton resource, when the home is initialized.
	 */
	public void initialize() throws Exception {
		logger.info("Attempting to initialize resource.");
		Resource resource = find(null);
		if (resource == null) {
			logger.error("Unable to initialize resource!");
		} else {
			logger.info("Successfully initialized resource.");
		}
	}
}