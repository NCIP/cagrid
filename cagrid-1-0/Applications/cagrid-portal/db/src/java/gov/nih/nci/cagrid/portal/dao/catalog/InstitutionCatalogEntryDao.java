package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.domain.Participant;
import gov.nih.nci.cagrid.portal.domain.catalog.InstitutionCatalogEntry;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class InstitutionCatalogEntryDao extends AboutCatalogEntryDao<InstitutionCatalogEntry, Participant> {

    public InstitutionCatalogEntryDao() {
    }

    /* (non-Javadoc)
    * @see gov.nih.nci.cagrid.portal.dao.AbstractDao#domainClass()
    */
    @Override
    public Class domainClass() {
        return InstitutionCatalogEntry.class;
    }

    public void createCatalogAbout(Participant participant) {
        InstitutionCatalogEntry entry = isAbout(participant);
        if (entry == null) {
            entry = new InstitutionCatalogEntry();
            entry.setAbout(participant);
        } else
            logger.debug("Catalog entry already exists. Will not create a new one");
        save(entry);
    }


}