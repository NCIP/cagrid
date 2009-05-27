package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.dao.AbstractDao;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;

public class CatalogEntryDao extends AbstractDao<CatalogEntry> {
    public CatalogEntryDao() {
    }/* (non-Javadoc)
    * @see gov.nih.nci.cagrid.portal.dao.AbstractDao#domainClass()
    */

    @Override
    public Class domainClass() {
        return CatalogEntry.class;
    }
}