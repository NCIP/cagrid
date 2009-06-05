package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.domain.GridService;
import gov.nih.nci.cagrid.portal.domain.catalog.GridServiceEndPointCatalogEntry;
import gov.nih.nci.cagrid.portal.util.BeanUtils;

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
            logger.debug("Catalog entry already exists. Will update the existing one");
        if (!entry.isPublished()) {
            logger.debug("Catalog entry has not been published. Will sync with domain object");
            entry.setName(BeanUtils.traverse(service, "serviceMetadata.serviceDescription.name"));
            entry.setDescription(BeanUtils.traverse(service, "serviceMetadata.serviceDescription.description"));
        }
        save(entry);
    }


}
