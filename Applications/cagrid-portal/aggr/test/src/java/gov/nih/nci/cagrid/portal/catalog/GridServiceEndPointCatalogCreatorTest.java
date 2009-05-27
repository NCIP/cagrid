package gov.nih.nci.cagrid.portal.catalog;

import gov.nih.nci.cagrid.portal.TestDB;
import gov.nih.nci.cagrid.portal.dao.catalog.GridServiceEndPointCatalogEntryDao;
import gov.nih.nci.cagrid.portal.util.PortalAggrIntegrationTestBase;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class GridServiceEndPointCatalogCreatorTest extends PortalAggrIntegrationTestBase {

    GridServiceEndPointCatalogEntryDao gridServiceEndPointCatalogEntryDao;

    // need to load data so initializing beans can do their job
    //Be careful. Only the first test will have sample data
    public GridServiceEndPointCatalogCreatorTest() throws Exception {
        super();
        TestDB.create();
        TestDB.loadData(getDataSet());
    }

    public void testWithSpringContainer() {
        assertTrue(gridServiceEndPointCatalogEntryDao.getAll().size() > 0);
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                "classpath*:applicationContext-aggr-catalog.xml",
                "applicationContext-db.xml"
        };
    }

    protected String getDataSet() throws Exception {
        return "test/data/GridServiceEndPointCatalog.xml";
    }

    public GridServiceEndPointCatalogEntryDao getGridServiceEndPointCatalogEntryDao() {
        return gridServiceEndPointCatalogEntryDao;
    }

    public void setGridServiceEndPointCatalogEntryDao(GridServiceEndPointCatalogEntryDao gridServiceEndPointCatalogEntryDao) {
        this.gridServiceEndPointCatalogEntryDao = gridServiceEndPointCatalogEntryDao;
    }


}


