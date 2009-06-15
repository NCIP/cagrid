/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet;

import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRelationshipType;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class UserModel {

	private PortalUser portalUser;
	private CatalogEntry currentCatalogEntry;
	private CatalogEntryRelationshipType currentRelationshipType;

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

	public CatalogEntryRelationshipType getCurrentRelationshipType() {
		return currentRelationshipType;
	}

	public void setCurrentRelationshipType(
			CatalogEntryRelationshipType currentRelationshipType) {
		this.currentRelationshipType = currentRelationshipType;
	}

}
