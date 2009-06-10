package gov.nih.nci.cagrid.portal.catalog;

import gov.nih.nci.cagrid.portal.aggr.catalog.GridServiceEndPointCatalogCreator;
import gov.nih.nci.cagrid.portal.dao.GridServiceDao;
import gov.nih.nci.cagrid.portal.dao.catalog.GridServiceEndPointCatalogEntryDao;
import gov.nih.nci.cagrid.portal.domain.GridService;
import gov.nih.nci.cagrid.portal.domain.catalog.GridServiceEndPointCatalogEntry;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class GridServiceEndPointCreatorMockTest {

    @Test
    public void testWithMocks() throws Exception {
        GridServiceEndPointCatalogCreator creator = new GridServiceEndPointCatalogCreator();

        GridServiceEndPointCatalogEntryDao mockCatalogDao = mock(GridServiceEndPointCatalogEntryDao.class);
        GridServiceDao mockDao = mock(GridServiceDao.class);

        GridService mockService = new GridService();
        List<GridService> services = new ArrayList<GridService>();
        services.add(mockService);

        doReturn(services).when(mockDao).getAll();

        creator.setGridServiceEndPointCatalogEntryDao(mockCatalogDao);
        creator.setGridServiceDao(mockDao);

        // call method
        creator.afterPropertiesSet();
        //verify catalog is created
        verify(mockCatalogDao).createCatalogAbout(mockService);


        // verify it doesn't save for existing catalogs
        doReturn(mock(GridServiceEndPointCatalogEntry.class)).when(mockCatalogDao).isAbout(mockService);
        creator.afterPropertiesSet();
        verify(mockCatalogDao, times(1)).createCatalogAbout(mockService);
    }


}
