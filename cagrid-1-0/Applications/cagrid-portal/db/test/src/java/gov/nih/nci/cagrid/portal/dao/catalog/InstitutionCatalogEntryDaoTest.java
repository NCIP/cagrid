package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.DaoTestBase;
import gov.nih.nci.cagrid.portal.dao.ParticipantDao;
import gov.nih.nci.cagrid.portal.domain.Participant;
import gov.nih.nci.cagrid.portal.domain.catalog.InstitutionCatalogEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class InstitutionCatalogEntryDaoTest extends DaoTestBase<InstitutionCatalogEntryDao> {


    ParticipantDao pDao;


    @Before
    public void setup() {
        pDao = (ParticipantDao) getApplicationContext().getBean("participantDao");
    }

    @Test
    public void testAbout() {
        Participant p = new Participant();
        pDao.save(p);

        InstitutionCatalogEntry catalog = new InstitutionCatalogEntry();
        catalog.setAbout(p);
        getDao().save(catalog);

        assertNotNull(getDao().isAbout(p));

    }


    // test to see if deleting the Participant deletes the catalog as well
    @Test
    public void delete() {
        Participant p = new Participant();
        pDao.save(p);

        InstitutionCatalogEntry catalog = new InstitutionCatalogEntry();
        catalog.setAbout(p);
        getDao().save(catalog);

        assertEquals(1, getDao().getAll().size());

        Participant loadedP = pDao.getById(1);
        pDao.delete(loadedP);

        interruptSession();
        assertEquals(0, getDao().getAll().size());

    }

}
