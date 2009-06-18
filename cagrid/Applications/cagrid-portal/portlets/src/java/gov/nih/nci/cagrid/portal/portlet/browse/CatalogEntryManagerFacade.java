/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.dao.catalog.CatalogEntryDao;
import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRelationshipInstance;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRelationshipType;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRoleInstance;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRoleType;
import gov.nih.nci.cagrid.portal.portlet.UserModel;
import gov.nih.nci.cagrid.portal.portlet.util.PortletUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
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
	private CatalogEntryDao catalogEntryDao;
	private CatalogEntryViewBeanFactory catalogEntryViewBeanFactory;
	private UserModel userModel;
	private HibernateTemplate hibernateTemplate;
	private String roleTypeRenderServletUrl;
	private String newRelatedItemFormRenderServletUrl;
	private String relatedItemsRenderServletUrl;

	private String sourceRoleDescription;
	private String targetRoleDescription;
	private Integer relatedEntryId;
	private Integer roleTypeId;

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

	public String renderRoleTypesForType(String targetType, String namespace) {
		String html = null;
		try {
			String sourceType = getUserModel().getCurrentCatalogEntry()
					.getClass().getName();
			Set<String> types = new HashSet<String>();
			Set<String> sourceTypes = new HashSet<String>();
			Set<String> targetTypes = new HashSet<String>();
			for (Class klass : PortletUtils.getSubclasses(null, Class
					.forName(targetType))) {
				targetTypes.add(klass.getName());
			}
			for (Class klass : PortletUtils.getSubclasses(null, Class
					.forName(sourceType))) {
				sourceTypes.add(klass.getName());
			}
			types.addAll(sourceTypes);
			types.addAll(targetTypes);

			StringBuilder sb = new StringBuilder();
			for (Iterator<String> i = types.iterator(); i.hasNext();) {
				String type = i.next();
				sb.append("'").append(type).append("'");
				if (i.hasNext()) {
					sb.append(",");
				}
			}
			List l = getHibernateTemplate().find(
					"from CatalogEntryRoleType where type in (" + sb + ")");
			List<CatalogEntryRoleType> roleTypes = new ArrayList<CatalogEntryRoleType>();
			for (Iterator<CatalogEntryRoleType> i = l.iterator(); i.hasNext();) {
				CatalogEntryRoleType targetTypeObj = i.next();
				if (targetTypes.contains(targetTypeObj.getType())) {
					CatalogEntryRelationshipType relType = targetTypeObj
							.getRelationshipType();
					CatalogEntryRoleType sourceTypeObj = relType.getRoleTypeA();
					if (sourceTypeObj.getId().equals(targetTypeObj.getId())) {
						sourceTypeObj = relType.getRoleTypeB();
					}
					if (sourceTypes.contains(sourceTypeObj.getType())) {
						roleTypes.add(targetTypeObj);
					}
				}
			}

			WebContext webContext = WebContextFactory.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			request.setAttribute("roleTypes", roleTypes);
			request.setAttribute("namespace", namespace);

			html = webContext.forwardToString(getRoleTypeRenderServletUrl());
		} catch (Exception ex) {
			String msg = "Error rendering role types: " + ex.getMessage();
			logger.error(msg, ex);
			throw new RuntimeException(msg, ex);
		}

		return html;
	}

	public String renderNewRelatedItemForm(Integer roleTypeId, String namespace) {
		this.roleTypeId = roleTypeId;

		String html = null;
		try {
			getUserModel().setCurrentRelationshipInstance(null);
			CatalogEntryRoleType targetRoleType = (CatalogEntryRoleType) getHibernateTemplate()
					.find("from CatalogEntryRoleType where id = ?", roleTypeId)
					.iterator().next();

			CatalogEntryRelationshipType relType = targetRoleType
					.getRelationshipType();
			CatalogEntryRoleType sourceRoleType = relType.getRoleTypeA();
			if (sourceRoleType.getId().equals(targetRoleType.getId())) {
				sourceRoleType = relType.getRoleTypeB();
			}

			WebContext webContext = WebContextFactory.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			request.setAttribute("targetRoleType", targetRoleType);
			request.setAttribute("sourceRoleType", sourceRoleType);
			request.setAttribute("namespace", namespace);

			html = webContext
					.forwardToString(getNewRelatedItemFormRenderServletUrl());
			
			
		} catch (Exception ex) {
			String msg = "Error rendering role types: " + ex.getMessage();
			logger.error(msg, ex);
			throw new RuntimeException(msg, ex);
		}

		return html;
	}

	public List<LabelDescriptionBean> getCatalogEntriesForPartialName(
			String name, String type) {
		List<LabelDescriptionBean> ldbs = new ArrayList<LabelDescriptionBean>();
		try {
			List entries = getHibernateTemplate().find(
					"from " + Class.forName(type).getSimpleName());
			for (Iterator<CatalogEntry> i = entries.iterator(); i.hasNext();) {
				CatalogEntry entry = i.next();
				ldbs.add(new LabelDescriptionBean(
						String.valueOf(entry.getId()), entry.getName(), entry
								.getDescription()));
			}
		} catch (Exception ex) {
			String msg = "Error getting entries by partial name: "
					+ ex.getMessage();
			logger.error(msg, ex);
			throw new RuntimeException(msg, ex);
		}
		return ldbs;
	}
	
	public String renderRelatedItems(String namespace){
		String html = null;
		try {

			CatalogEntry entry = getCatalogEntryDao().getById(getUserModel().getCurrentCatalogEntry().getId());
			if(entry == null){
				throw new RuntimeException("No current catalog entry");
			}
			CatalogEntryViewBean catalogEntryViewBean = getCatalogEntryViewBeanFactory().newCatalogEntryViewBean(entry);
			
			WebContext webContext = WebContextFactory.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			request.setAttribute("catalogEntryViewBean", catalogEntryViewBean);
			request.setAttribute("namespace", namespace);

			html = webContext
					.forwardToString(getRelatedItemsRenderServletUrl());
		} catch (Exception ex) {
			String msg = "Error rendering role types: " + ex.getMessage();
			logger.error(msg, ex);
			throw new RuntimeException(msg, ex);
		}

		return html;
	}

	public String setSourceRoleDescription(String sourceRoleDescription) {
		String message = null;
		this.sourceRoleDescription = sourceRoleDescription;
		return message;
	}

	public String setTargetRoleDescription(String targetRoleDescription) {
		String message = null;
		this.targetRoleDescription = targetRoleDescription;
		return message;
	}

	public String setRelatedEntryId(Integer relatedEntryId) {
		String message = null;
		this.relatedEntryId = relatedEntryId;
		return message;
	}

	public String setRoleTypeId(Integer roleTypeId) {
		String message = null;
		this.roleTypeId = roleTypeId;
		return message;
	}

	public String saveRelationship() {
		String message = null;
		try {
			CatalogEntry entry = getUserModel().getCurrentCatalogEntry();
			CatalogEntryRelationshipInstance relInst = getUserModel()
					.getCurrentRelationshipInstance();
			if (relInst == null) {
				relInst = new CatalogEntryRelationshipInstance();

				CatalogEntry relatedEntry = (CatalogEntry) getHibernateTemplate()
						.find("from CatalogEntry where id = ?",
								this.relatedEntryId).iterator().next();
				CatalogEntryRoleType targetRoleType = (CatalogEntryRoleType) getHibernateTemplate()
						.find("from CatalogEntryRoleType where id = ?",
								this.roleTypeId).iterator().next();
				CatalogEntryRelationshipType relType = targetRoleType
						.getRelationshipType();
				CatalogEntryRoleType sourceRoleType = relType.getRoleTypeA();
				if (sourceRoleType.getId().equals(targetRoleType.getId())) {
					sourceRoleType = relType.getRoleTypeB();
				}

				relInst.setCreatedAt(new Date());
				relInst.setAuthor(getUserModel().getPortalUser());
				relInst.setType(relType);
				getHibernateTemplate().save(relInst);

				CatalogEntryRoleInstance roleAInst = new CatalogEntryRoleInstance();
				roleAInst.setCreatedAt(new Date());
				roleAInst.setCatalogEntry(entry);
				roleAInst.setRelationship(relInst);
				roleAInst.setType(sourceRoleType);
				getHibernateTemplate().save(roleAInst);
				relInst.setRoleA(roleAInst);

				CatalogEntryRoleInstance roleBInst = new CatalogEntryRoleInstance();
				roleBInst.setCreatedAt(new Date());
				roleBInst.setCatalogEntry(relatedEntry);
				roleBInst.setRelationship(relInst);
				roleBInst.setType(targetRoleType);
				getHibernateTemplate().save(roleBInst);
				relInst.setRoleB(roleBInst);

				getUserModel().setCurrentRelationshipInstance(relInst);
			}
			CatalogEntryRoleInstance sourceRoleInst = relInst.getRoleA();
			CatalogEntryRoleInstance targetRoleInst = relInst.getRoleB();
			if (!sourceRoleInst.getCatalogEntry().getId().equals(entry.getId())) {
				sourceRoleInst = relInst.getRoleB();
				targetRoleInst = relInst.getRoleA();
			}
			sourceRoleInst.setDescription(sourceRoleDescription);
			sourceRoleInst.setUpdatedAt(new Date());
			targetRoleInst.setDescription(targetRoleDescription);
			targetRoleInst.setUpdatedAt(new Date());
			getHibernateTemplate().update(sourceRoleInst);
			getHibernateTemplate().update(targetRoleInst);
			getHibernateTemplate().update(relInst);
			
			getHibernateTemplate().flush();

		} catch (Exception ex) {
			String msg = "Error saving relationship: " + ex.getMessage();
			logger.error(msg, ex);
			throw new RuntimeException(msg, ex);
		}
		return message;
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

	public String getRoleTypeRenderServletUrl() {
		return roleTypeRenderServletUrl;
	}

	public void setRoleTypeRenderServletUrl(String roleTypeRenderServletUrl) {
		this.roleTypeRenderServletUrl = roleTypeRenderServletUrl;
	}

	public String getNewRelatedItemFormRenderServletUrl() {
		return newRelatedItemFormRenderServletUrl;
	}

	public void setNewRelatedItemFormRenderServletUrl(
			String newRelatedItemFormRenderServletUrl) {
		this.newRelatedItemFormRenderServletUrl = newRelatedItemFormRenderServletUrl;
	}

	public String getRelatedItemsRenderServletUrl() {
		return relatedItemsRenderServletUrl;
	}

	public void setRelatedItemsRenderServletUrl(String relatedItemsRenderServletUrl) {
		this.relatedItemsRenderServletUrl = relatedItemsRenderServletUrl;
	}

	public CatalogEntryDao getCatalogEntryDao() {
		return catalogEntryDao;
	}

	public void setCatalogEntryDao(CatalogEntryDao catalogEntryDao) {
		this.catalogEntryDao = catalogEntryDao;
	}

	public CatalogEntryViewBeanFactory getCatalogEntryViewBeanFactory() {
		return catalogEntryViewBeanFactory;
	}

	public void setCatalogEntryViewBeanFactory(
			CatalogEntryViewBeanFactory catalogEntryViewBeanFactory) {
		this.catalogEntryViewBeanFactory = catalogEntryViewBeanFactory;
	}

}
