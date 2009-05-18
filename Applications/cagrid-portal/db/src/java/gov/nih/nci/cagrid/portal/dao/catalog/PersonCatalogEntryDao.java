package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.dao.AbstractDao;
import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class PersonCatalogEntryDao extends AbstractDao<PersonCatalogEntry> {

    public PersonCatalogEntryDao() {
    }

    /* (non-Javadoc)
    * @see gov.nih.nci.cagrid.portal.dao.AbstractDao#domainClass()
    */
    @Override
    public Class domainClass() {
        return PersonCatalogEntry.class;
    }

    public PersonCatalogEntry getByUser(PortalUser user){
        PersonCatalogEntry entry = new PersonCatalogEntry();
        entry.setAbout(user);
        return getByExample(entry);
    }
}