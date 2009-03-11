package gov.nih.nci.cagrid.portal.util;

import org.cagrid.gme.client.GlobalModelExchangeClient;
import org.cagrid.gme.domain.XMLSchemaNamespace;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class PortalUtilsIntegrationTest extends PortalAggrIntegrationTestBase {


    protected String gmeUrl;
    private XMLSchemaNamespace ns;

    public PortalUtilsIntegrationTest() throws Exception {
        super();
        ns = new XMLSchemaNamespace("http://model1/model");


    }

    public void testGetXmlSchemaContent() throws Exception {
        System.out.println("Runnin tests");

        String content = null;
        try {
            System.out.println(gmeUrl);
            GlobalModelExchangeClient client = new GlobalModelExchangeClient(gmeUrl);
            org.cagrid.gme.domain.XMLSchema schema = client.getXMLSchema(ns);
            content = schema.getRootDocument().getSchemaText();
            assertNotNull(content);
        } catch (Exception e) {
            fail("GME Client throw an unexpected exception" + e.getMessage());
        }

        String xmlSchemaContent = PortalUtils.getXmlSchemaContent(ns.toString(), gmeUrl);
        assertEquals(xmlSchemaContent, content);
    }


}
