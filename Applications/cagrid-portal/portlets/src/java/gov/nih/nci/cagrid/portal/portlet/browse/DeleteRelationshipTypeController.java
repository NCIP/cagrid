/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.dao.catalog.CatalogEntryRelationshipTypeDao;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRelationshipType;
import gov.nih.nci.cagrid.portal.portlet.UserModel;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.springframework.web.portlet.mvc.AbstractController;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class DeleteRelationshipTypeController extends AbstractController {

	private UserModel userModel;
	private CatalogEntryRelationshipTypeDao catalogEntryRelationshipTypeDao;

	/**
	 * 
	 */
	public DeleteRelationshipTypeController() {

	}

	protected void handleActionRequestInternal(ActionRequest request,
			ActionResponse response) throws Exception {

		Integer relTypeId = null;
		try {
			relTypeId = Integer.valueOf(request.getParameter("relTypeId"));
		} catch (Exception ex) {
			throw new RuntimeException(
					"Error getting relationship type ID from request: "
							+ ex.getMessage(), ex);
		}
		CatalogEntryRelationshipType relType = getCatalogEntryRelationshipTypeDao()
				.getById(relTypeId);
		if (relType == null) {
			throw new RuntimeException(
					"Couldn't find relationship type for id: " + relTypeId);
		}
		getCatalogEntryRelationshipTypeDao().delete(relType);
		getCatalogEntryRelationshipTypeDao().getHibernateTemplate().flush();
		getUserModel().setCurrentRelationshipType(null);

	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public CatalogEntryRelationshipTypeDao getCatalogEntryRelationshipTypeDao() {
		return catalogEntryRelationshipTypeDao;
	}

	public void setCatalogEntryRelationshipTypeDao(
			CatalogEntryRelationshipTypeDao catalogEntryRelationshipTypeDao) {
		this.catalogEntryRelationshipTypeDao = catalogEntryRelationshipTypeDao;
	}

}
