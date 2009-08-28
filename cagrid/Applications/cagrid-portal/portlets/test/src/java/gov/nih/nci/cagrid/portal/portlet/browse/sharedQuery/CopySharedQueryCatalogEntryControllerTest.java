package gov.nih.nci.cagrid.portal.portlet.browse.sharedQuery;

import gov.nih.nci.cagrid.portal.dao.catalog.CatalogEntryDao;
import gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryCatalogEntry;
import gov.nih.nci.cagrid.portal.domain.dataservice.Query;
import gov.nih.nci.cagrid.portal.portlet.UserModel;
import gov.nih.nci.cagrid.portal.portlet.browse.CatalogEntryFactory;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockActionResponse;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class CopySharedQueryCatalogEntryControllerTest {

    protected MockActionRequest request = new MockActionRequest();
    protected MockActionResponse response = new MockActionResponse();

    @Test
    public void create() throws Exception {
        Query mockQuery = mock(Query.class);
        when(mockQuery.getXml()).thenReturn("<xml/>");

        SharedQueryCatalogEntry ce = (SharedQueryCatalogEntry) Class.forName("gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryCatalogEntry").newInstance();
        ce.setAbout(mockQuery);

        CatalogEntryDao mockDao = mock(CatalogEntryDao.class);
        when(mockDao.getById(anyInt())).thenReturn(ce);

        CopySharedQueryCatalogEntryController controller = new CopySharedQueryCatalogEntryController();
        controller.setCatalogEntryDao(mockDao);

        CatalogEntryFactory mockFactory = mock(CatalogEntryFactory.class);
        when(mockFactory.newCatalogEntry(anyString())).thenReturn((SharedQueryCatalogEntry) Class.forName("gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryCatalogEntry").newInstance());

        controller.setCatalogEntryFactory(mockFactory);
        controller.setUserModel(mock(UserModel.class));
        request.setParameter("entryId", "1");
        controller.handleActionRequestInternal(request, response);


    }

}
