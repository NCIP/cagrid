package gov.nih.nci.cagrid.portal.util;

import gov.nih.nci.cagrid.portal.AbstractTimeSensitiveTest;
import gov.nih.nci.cagrid.portal.domain.metadata.dataservice.DomainModel;
import gov.nih.nci.cagrid.portal.domain.metadata.dataservice.XMLSchema;
import org.cagrid.gme.client.GlobalModelExchangeClient;
import org.cagrid.gme.domain.XMLSchemaNamespace;
import static org.mockito.Mockito.mock;

import java.util.List;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class PortalUtilsTest extends AbstractTimeSensitiveTest {
    private String badUrl;
    private XMLSchemaNamespace ns;


    public PortalUtilsTest() throws Exception {
        badUrl = "http://www.yahoo.com";
        ns = new XMLSchemaNamespace("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata.common");

    }

    protected Long getAcceptableTime() {
        return new Long("120000");
    }


    public void testGME() {
        try {

            GlobalModelExchangeClient client = new GlobalModelExchangeClient(badUrl);
            org.cagrid.gme.domain.XMLSchema schema = client.getXMLSchema(ns);
            String content = schema.getRootDocument().getSchemaText();
            fail("Schema should be null");
        } catch (Exception e) {
            assertNotNull(e);
        }


    }

    public void testGetXMLSchemas() throws Exception {
        DomainModel _model = mock(DomainModel.class);
        List<XMLSchema> _schemas = null;

        _schemas = PortalUtils.getXMLSchemas(_model, badUrl, badUrl);
        assertEquals("Schemas returned for bad URL", _schemas.size(), 0);

    }


    public void testBadGetXmlSchemaContent() {
        PortalUtils.getXmlSchemaContent(ns.toString(), badUrl);
    }
}
