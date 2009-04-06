package gov.nih.nci.cagrid.portal.liferay.websso;

import org.junit.Test;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class WebSSOUserDetailsServiceTest {


    @Test
    public void loadUserByUsername() {
        WebSSOUserDetailsService service = new WebSSOUserDetailsService();
        service.loadUserByUsername(null);

    }
}
