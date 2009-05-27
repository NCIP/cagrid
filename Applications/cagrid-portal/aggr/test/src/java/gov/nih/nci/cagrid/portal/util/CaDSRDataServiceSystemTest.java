package gov.nih.nci.cagrid.portal.util;

import gov.nih.nci.cagrid.portal.domain.metadata.dataservice.DomainModel;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class CaDSRDataServiceSystemTest {

    @Test
    public void basic() throws Exception {
        DomainModel model = new DomainModel();
        model.setProjectShortName("caBIO");
        model.setProjectVersion("3.2");
        CaDSRDataServiceClient client = new CaDSRDataServiceClient();
        client.setCadsrUrl("http://cadsr-dataservice.nci.nih.gov:80/wsrf/services/cagrid/CaDSRDataService");
        try {
            assertNotNull(client.getContext(model));
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}
 
