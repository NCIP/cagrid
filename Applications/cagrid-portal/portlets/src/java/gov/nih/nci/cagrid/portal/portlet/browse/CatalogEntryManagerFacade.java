/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import gov.nih.nci.cagrid.portal.portlet.UserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.liferay.portal.service.ResourceLocalServiceUtil;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
@Transactional
public class CatalogEntryManagerFacade {

	private static final Log logger = LogFactory
			.getLog(CatalogEntryManagerFacade.class);
	private UserModel userModel;
	private HibernateTemplate hibernateTemplate;

	/**
	 * 
	 */
	public CatalogEntryManagerFacade() {

	}

	public Integer save() {
		Integer id = null;
		try {
			PortalUser portalUser = getUserModel().getPortalUser();
			CatalogEntry ce = getUserModel().getCurrentCatalogEntry();
			ce.setAuthor(portalUser);
			getHibernateTemplate().saveOrUpdate(ce);

			String[] portalId = portalUser.getPortalId().split(":");
			ResourceLocalServiceUtil.addResources(Long.parseLong(portalId[0]),
					0, Long.parseLong(portalId[1]), CatalogEntry.class
							.getName(), String.valueOf(ce.getId()), false,
					false, false);
			id = saveInternal(ce);
		} catch (Exception ex) {
			String msg = "Error saving catalog entry: " + ex.getMessage();
			logger.debug(msg, ex);
			throw new RuntimeException(msg, ex);
		}
		return id;
	}

	public String validate() {
		return null;
	}

	protected Integer saveInternal(CatalogEntry catalogEntry) {
		return catalogEntry.getId();
	}

	public String setName(String name) {
		String message = null;
		getUserModel().getCurrentCatalogEntry().setName(name);
		return message;
	}

	public String setDescription(String description) {
		String message = null;
		getUserModel().getCurrentCatalogEntry().setDescription(description);
		return message;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
