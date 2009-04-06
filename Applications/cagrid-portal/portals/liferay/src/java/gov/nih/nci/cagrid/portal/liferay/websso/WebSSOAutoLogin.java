package gov.nih.nci.cagrid.portal.liferay.websso;


import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLoginException;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import gov.nih.nci.cagrid.portal.domain.Person;
import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.liferay.security.AbstractAutoLogin;
import org.acegisecurity.context.SecurityContextHolder;
import org.cagrid.websso.client.acegi.WebSSOUser;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p/>
 * Liferay portal provides a seperate hooking mechanism to fetch valid credentials from
 * any third party security system.Inside this hook method ,we have to populate current user
 * info into liferay if that's user information doesn't exist before.
 * <p/>
 * <p/>
 * Modified com.cagrid.liferay.websso.client.CaGridLiferayCASAutoLoginHook
 * for Liferay 5.x
 */
public class WebSSOAutoLogin extends AbstractAutoLogin {

    public String[] login(HttpServletRequest request, HttpServletResponse response)
            throws AutoLoginException {

        String[] credentials = null;
        long companyId = PortalUtil.getCompanyId(request);
        try {

            if (!PrefsPropsUtil.getBoolean(companyId, PropsUtil.CAS_AUTH_ENABLED)) {
                return credentials;
            }
            if (SecurityContextHolder
                    .getContext().getAuthentication() != null) {
                WebSSOUser webssoUser = (WebSSOUser) SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal();

                User user = null;
                PortalUser portalUser = getPortalUser(webssoUser);
                if (portalUser.getPortalId() == null) {
                    logger.debug("creating new User for "
                            + portalUser.getGridIdentity());

                    user = addLiferayUser(portalUser);

                    portalUser.setPortalId(String.valueOf(user.getCompanyId())
                            + ":" + String.valueOf(user.getUserId()));
                    getPortalUserDao().save(portalUser);
                } else {
                    String[] portalId = portalUser.getPortalId().split(":");
                    user = UserLocalServiceUtil.getUserById(Integer
                            .parseInt(portalId[0]), Integer
                            .parseInt(portalId[1]));
                    if (user == null) {
                        throw new AutoLoginException(
                                "No User found for portalId = " + portalId);
                    }
                }

                request.setAttribute(getUserIdAttributeName(), portalUser
                        .getId());

                credentials = new String[3];
                credentials[0] = String.valueOf(user.getUserId());
                credentials[1] = user.getPassword();
                credentials[2] = Boolean.TRUE.toString();
            }
        } catch (Exception e) {
            logger.error("Unexpected error while authenticating: "
                    + e.getMessage(), e);
            request
                    .setAttribute(
                            getAuthnErrorMessageAttributeName(),
                            "An error was encountered during authentication. Please contact the adminstrator (" + getPortalAdminEmailAddress() + ").");
            throw new AutoLoginException(e);
        }

        return credentials;

    }

    @Transactional
    private PortalUser getPortalUser(WebSSOUser webSSOUser) {
        PortalUser portalUser = new PortalUser();
        portalUser.setGridIdentity(webSSOUser.getGridId());
        portalUser = getPortalUserDao().getByExample(portalUser);
        if (portalUser == null) {
            portalUser = new PortalUser();
            portalUser.setGridIdentity(webSSOUser.getGridId());
            Person person = new Person();
            person.setEmailAddress(webSSOUser.getEmailId());
            person.setFirstName(webSSOUser.getFirstName());
            person.setLastName(webSSOUser.getLastName());
            getPersonDao().save(person);
            portalUser.setPerson(person);
        }
        portalUser.setDelegatedEPR(webSSOUser.getDelegatedEPR());
        portalUser.setGridCredential(null);
        getPortalUserDao().save(portalUser);
        return portalUser;

    }

}