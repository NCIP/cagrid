package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class PersonCatalogEntryDao extends AboutCatalogEntryDao<PersonCatalogEntry, PortalUser> {

    public PersonCatalogEntryDao() {
    }

    /* (non-Javadoc)
    * @see gov.nih.nci.cagrid.portal.dao.AbstractDao#domainClass()
    */
    @Override
    public Class domainClass() {
        return PersonCatalogEntry.class;
    }

    public void createCatalogAbout(PortalUser user) {
        PersonCatalogEntry entry = isAbout(user);
        if (entry == null) {
            entry = new PersonCatalogEntry();
            entry.setAbout(user);
        } else
            logger.debug("Catalog entry already exists. Will not create a new one");
        save(entry);
    }


}