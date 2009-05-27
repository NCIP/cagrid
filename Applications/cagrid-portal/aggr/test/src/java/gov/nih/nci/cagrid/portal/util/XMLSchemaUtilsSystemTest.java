package gov.nih.nci.cagrid.portal.util;


public class XMLSchemaUtilsSystemTest extends
        PortalAggrIntegrationTestBase {


    public void testGme() {
        XMLSchemaUtils xmlSchemaUtils = (XMLSchemaUtils) getApplicationContext().getBean("xmlSchemaUtils");
        String xmlSchema = xmlSchemaUtils.getXmlSchemaContent("http://model1/model");
        assertNotNull(xmlSchema);
    }


}
