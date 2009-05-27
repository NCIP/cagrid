package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.domain.GridService;
import gov.nih.nci.cagrid.portal.domain.catalog.GridServiceEndPointCatalogEntry;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class GridServiceEndPointCatalogEntryDao extends AboutCatalogEntryDao<GridServiceEndPointCatalogEntry, GridService> {

    public GridServiceEndPointCatalogEntryDao() {
    }

    /* (non-Javadoc)
    * @see gov.nih.nci.cagrid.portal.dao.AbstractDao#domainClass()
    */
    @Override
    public Class domainClass() {
        return GridServiceEndPointCatalogEntry.class;
    }


    public void createCatalogAbout(GridService service) {
        GridServiceEndPointCatalogEntry entry = isAbout(service);
        if (entry == null) {
            entry = new GridServiceEndPointCatalogEntry();
            entry.setAbout(service);
        } else
            logger.debug("Catalog entry already exists. Will not create a new one");
        save(entry);
    }


}
