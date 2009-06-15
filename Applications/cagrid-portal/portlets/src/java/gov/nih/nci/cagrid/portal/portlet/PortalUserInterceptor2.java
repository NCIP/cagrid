/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet;

import gov.nih.nci.cagrid.portal.dao.PortalUserDao;
import gov.nih.nci.cagrid.portal.domain.PortalUser;

import javax.portlet.PortletSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.portlet.context.PortletWebRequest;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class PortalUserInterceptor2 implements WebRequestInterceptor {

	private static final Log logger = LogFactory.getLog(PortalUserInterceptor2.class);
	private UserModel userModel;
	private String portalUserSessionAttributeName;
	private PortalUserDao portalUserDao;

	/**
	 * 
	 */
	public PortalUserInterceptor2() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.request.WebRequestInterceptor#afterCompletion(org.springframework.web.context.request.WebRequest,
	 *      java.lang.Exception)
	 */
	public void afterCompletion(WebRequest arg0, Exception arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.request.WebRequestInterceptor#postHandle(org.springframework.web.context.request.WebRequest,
	 *      org.springframework.ui.ModelMap)
	 */
	public void postHandle(WebRequest arg0, ModelMap arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.request.WebRequestInterceptor#preHandle(org.springframework.web.context.request.WebRequest)
	 */
	public void preHandle(WebRequest webRequest) throws Exception {
		if (getUserModel().getPortalUser() == null) {
			logger.debug("PortalUser is null.");
			PortletWebRequest portletWebRequest = (PortletWebRequest) webRequest;
			logger.debug("Fetching portalUserId from session.");
			String portalUserId = (String) portletWebRequest.getRequest()
					.getPortletSession().getAttribute(
							getPortalUserSessionAttributeName(),
							PortletSession.APPLICATION_SCOPE);
			logger.debug("portalUserId = " + portalUserId);
			if (portalUserId != null) {
				PortalUser portalUser = getPortalUserDao().getByPortalId(portalUserId);
				if(portalUser == null){
					throw new Exception("No user found for portal ID: " + portalUserId);
				}
				getUserModel().setPortalUser(portalUser);
			}
		}
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public String getPortalUserSessionAttributeName() {
		return portalUserSessionAttributeName;
	}

	public void setPortalUserSessionAttributeName(
			String portalUserSessionAttributeName) {
		this.portalUserSessionAttributeName = portalUserSessionAttributeName;
	}

	public PortalUserDao getPortalUserDao() {
		return portalUserDao;
	}

	public void setPortalUserDao(PortalUserDao portalUserDao) {
		this.portalUserDao = portalUserDao;
	}

}
