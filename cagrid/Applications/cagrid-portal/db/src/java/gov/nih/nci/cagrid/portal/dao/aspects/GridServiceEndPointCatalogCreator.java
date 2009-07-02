package gov.nih.nci.cagrid.portal.dao.aspects;

import gov.nih.nci.cagrid.portal.dao.GridServiceDao;
import gov.nih.nci.cagrid.portal.dao.catalog.GridServiceEndPointCatalogEntryDao;
import gov.nih.nci.cagrid.portal.domain.GridService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
@Aspect
public class GridServiceEndPointCatalogCreator {

    Log logger = LogFactory.getLog(getClass());
    GridServiceDao gridServiceDao;
    GridServiceEndPointCatalogEntryDao gridServiceEndPointCatalogEntryDao;


    /**
     * Aspect will create catalog item when new service is regsitered
     *
     * @param service
     */
    @AfterReturning("execution(* gov.nih.nci.cagrid.portal.dao.GridServiceDao.save*(gov.nih.nci.cagrid.portal.domain.GridService))  && args(service)")
    public void onSave(GridService service) {

//        logger.debug("A service is being saved. Will create catalog");
//        gridServiceEndPointCatalogEntryDao.createCatalogAbout(service);
    }

    public GridServiceDao getGridServiceDao() {
        return gridServiceDao;
    }

    public void setGridServiceDao(GridServiceDao gridServiceDao) {
        this.gridServiceDao = gridServiceDao;
    }

    public GridServiceEndPointCatalogEntryDao getGridServiceEndPointCatalogEntryDao() {
        return gridServiceEndPointCatalogEntryDao;
    }

    public void setGridServiceEndPointCatalogEntryDao(GridServiceEndPointCatalogEntryDao gridServiceEndPointCatalogEntryDao) {
        this.gridServiceEndPointCatalogEntryDao = gridServiceEndPointCatalogEntryDao;
    }
}
    
 
