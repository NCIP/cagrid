/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet;

import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 *
 */
public class UserModel {
	
	private PortalUser portalUser;
	private CatalogEntry currentCatalogEntry;

	/**
	 * 
	 */
	public UserModel() {

	}

	public PortalUser getPortalUser() {
		return portalUser;
	}

	public void setPortalUser(PortalUser portalUser) {
		this.portalUser = portalUser;
	}

	public CatalogEntry getCurrentCatalogEntry() {
		return currentCatalogEntry;
	}

	public void setCurrentCatalogEntry(CatalogEntry currentCatalogEntry) {
		this.currentCatalogEntry = currentCatalogEntry;
	}

}
