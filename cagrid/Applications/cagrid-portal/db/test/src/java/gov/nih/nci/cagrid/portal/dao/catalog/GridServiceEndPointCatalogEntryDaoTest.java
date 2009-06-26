package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.DaoTestBase;
import gov.nih.nci.cagrid.portal.dao.GridServiceDao;
import gov.nih.nci.cagrid.portal.domain.GridService;
import gov.nih.nci.cagrid.portal.domain.catalog.GridServiceEndPointCatalogEntry;
import static org.junit.Assert.*;
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
    public void createWithNoAbout() {
        GridServiceEndPointCatalogEntry entry = new GridServiceEndPointCatalogEntry();
        getDao().save(entry);
        GridServiceEndPointCatalogEntry loaded = getDao().getById(1);
        assertNotNull(loaded);
        assertNull(loaded.getAbout());
    }


    @Test
    public void createAbout() {
        pDao.save(p);

        GridServiceEndPointCatalogEntry catalog = new GridServiceEndPointCatalogEntry();
        catalog.setAbout(p);
        p.setCatalog(catalog);
        pDao.save(p);
        getDao().save(catalog);

        assertNotNull(getDao().isAbout(p));
    }


    // test to see if deleting the Participant deletes the catalog as well
    @Test
    public void delete() {

        pDao.save(p);

        GridServiceEndPointCatalogEntry catalog = new GridServiceEndPointCatalogEntry();
        catalog.setAbout(p);
        p.setCatalog(catalog);
        pDao.save(p);
        getDao().save(catalog);

        assertEquals(1, getDao().getAll().size());

        GridService loadedP = pDao.getById(1);
        pDao.delete(loadedP);

        interruptSession();
        assertEquals(0, getDao().getAll().size());

    }

    @Test
    public void getByPartialUrl() {
        p.setUrl("http://complete.url");
        pDao.save(p);

        GridServiceEndPointCatalogEntry catalog = new GridServiceEndPointCatalogEntry();
        catalog.setAbout(p);
        p.setCatalog(catalog);
        getDao().save(catalog);

        assertEquals(1, getDao().getByPartialUrl("http://").size());
        assertEquals(1, getDao().getByPartialUrl("url").size());
        assertEquals(1, getDao().getByPartialUrl("complete").size());
        assertEquals(0, getDao().getByPartialUrl("someother.url").size());


    }

}
