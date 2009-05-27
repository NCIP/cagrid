package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.DaoTestBase;
import gov.nih.nci.cagrid.portal.dao.PortalUserDao;
import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class PersonCatalogEntryDaoTest extends DaoTestBase<PersonCatalogEntryDao> {

    PortalUserDao pDao;


    @Before
    public void setup() {
        pDao = (PortalUserDao) getApplicationContext().getBean("portalUserDao");
    }

    @Test
    public void testAbout() {
        PortalUser p = new PortalUser();
        pDao.save(p);

        PersonCatalogEntry catalog = new PersonCatalogEntry();
        catalog.setAbout(p);
        getDao().save(catalog);

        assertNotNull(getDao().isAbout(p));

    }


    // test to see if deleting the Participant deletes the catalog as well
    @Test
    public void delete() {
        PortalUser p = new PortalUser();
        pDao.save(p);

        PersonCatalogEntry catalog = new PersonCatalogEntry();
        catalog.setAbout(p);
        getDao().save(catalog);

        assertEquals(1, getDao().getAll().size());

        PortalUser loadedP = pDao.getById(1);
        pDao.delete(loadedP);

        interruptSession();
        assertEquals(0, getDao().getAll().size());

    }

}
