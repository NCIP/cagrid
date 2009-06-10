package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.DaoTestBase;
import gov.nih.nci.cagrid.portal.dao.GridServiceDao;
import gov.nih.nci.cagrid.portal.domain.GridService;
import gov.nih.nci.cagrid.portal.domain.catalog.GridServiceEndPointCatalogEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class GridServiceEndPointCatalogEntryDaoTest extends DaoTestBase<GridServiceEndPointCatalogEntryDao> {

    GridServiceDao pDao;
    GridService p;

    @Before
    public void setup() {
        pDao = (GridServiceDao) getApplicationContext().getBean("gridServiceDao");
        p = new GridService();

    }

    @Test
    public void createAbout() {
        pDao.save(p);

        GridServiceEndPointCatalogEntry catalog = new GridServiceEndPointCatalogEntry();
        catalog.setAbout(p);
        getDao().save(catalog);

        assertNotNull(getDao().isAbout(p));
    }


    // test to see if deleting the Participant deletes the catalog as well
    @Test
    public void delete() {

        pDao.save(p);

        GridServiceEndPointCatalogEntry catalog = new GridServiceEndPointCatalogEntry();
        catalog.setAbout(p);
        getDao().save(catalog);

        assertEquals(1, getDao().getAll().size());

        GridService loadedP = pDao.getById(1);
        pDao.delete(loadedP);

        interruptSession();
        assertEquals(0, getDao().getAll().size());

    }

}
