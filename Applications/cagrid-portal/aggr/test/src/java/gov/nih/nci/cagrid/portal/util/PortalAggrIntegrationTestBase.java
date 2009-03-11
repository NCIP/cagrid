package gov.nih.nci.cagrid.portal.util;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class PortalAggrIntegrationTestBase extends AbstractDependencyInjectionSpringContextTests {

    public PortalAggrIntegrationTestBase() {
        super();
        setPopulateProtectedVariables(true);
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                "classpath*:applicationContext-aggr.xml",
                "applicationContext-db.xml"
        };
    }
}
