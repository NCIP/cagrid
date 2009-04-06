package gov.nih.nci.cagrid.portal.liferay.websso;

import org.acegisecurity.context.SecurityContextHolder;
import org.cagrid.websso.client.acegi.WebSSOUser;
import org.cagrid.websso.common.WebSSOConstants;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * Duplicating functionality of
 * org.cagrid.websso.client.acegi.logout.SingleSignoutHelper because
 * we don't want to connect to GAARDS due to Axis incompatiblity
 * between caGrid and Liferay
 * <p/>
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class SingleSignoutHelper {


    private Resource casClientResource;

    public SingleSignoutHelper(Resource casClientResource) {
        this.casClientResource = casClientResource;
    }

    public String getLogoutURL() {
        Properties properties = new Properties();
        try {
            properties.load(casClientResource.getInputStream());
            WebSSOUser webssoUser = (WebSSOUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String delegationEPR = webssoUser.getDelegatedEPR();

            String logoutURL = properties.getProperty("cas.server.url") + "/logout";
            String logoutLandingURL = properties.getProperty("logout.landing.url");
            logoutURL = logoutURL + "?service=" + logoutLandingURL;
            logoutURL = logoutURL + "&" + WebSSOConstants.CAGRID_SSO_DELEGATION_SERVICE_EPR + "=" + delegationEPR;
            return logoutURL;
        } catch (IOException e) {
            throw new RuntimeException("error occured handling logout " + e);
        }
    }


}


