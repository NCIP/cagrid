package gov.nih.nci.cagrid.portal.aggr.status;

import gov.nih.nci.cagrid.portal.domain.ServiceStatus;
import gov.nih.nci.cagrid.portal.util.PortalAggrIntegrationTestBase;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class WSDLServiceStatusProviderSystemTest extends PortalAggrIntegrationTestBase {


    private String _nonCagridService = "http://www.google.com";
    private String gmeUrl;


    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        gmeUrl = (String) getApplicationContext().getBean("gmeUrl");
    }

    public void testStatusProvider() {


        WSDLServiceStatusProvider _provider = new WSDLServiceStatusProvider();
        assertEquals("Should not get wsdl from this url", ServiceStatus.INACTIVE, _provider.getStatus(_nonCagridService));
        assertEquals("Should get wsdl from URL", ServiceStatus.ACTIVE, _provider.getStatus(gmeUrl));

        assertEquals(ServiceStatus.INACTIVE, _provider.getStatus("http://wsdl"));
        assertEquals(ServiceStatus.INACTIVE, _provider.getStatus("http://"));
    }


}
