package gov.nih.nci.cagrid.portal.util;


public class XMLSchemaUtilsSystemTest extends
        PortalAggrIntegrationTestBase {


    public void testGme() {
        XMLSchemaUtils xmlSchemaUtils = (XMLSchemaUtils) getApplicationContext().getBean("xmlSchemaUtils");
        String xmlSchema = xmlSchemaUtils.getXmlSchemaContent("gme://b");
        assertNotNull(xmlSchema);
    }

    @Override
       protected String[] getConfigLocations() {
           return new String[]{
                   "classpath*:applicationContext-aggr.xml",
                   "classpath*:applicationContext-db.xml"

           };
       }

}
