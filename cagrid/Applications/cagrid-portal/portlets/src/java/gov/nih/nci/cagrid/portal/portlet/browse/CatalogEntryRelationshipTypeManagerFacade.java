/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.dao.catalog.CatalogEntryRelationshipTypeDao;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRelationshipType;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRoleType;
import gov.nih.nci.cagrid.portal.portlet.UserModel;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
@Transactional
public class CatalogEntryRelationshipTypeManagerFacade {

	private UserModel userModel;
	private CatalogEntryRelationshipTypeDao catalogEntryRelationshipTypeDao;

	/**
	 * 
	 */
	public CatalogEntryRelationshipTypeManagerFacade() {

	}

	public String setName(String name) {
		String message = null;
		try {
			CatalogEntryRelationshipType relType = null;
			List l = getCatalogEntryRelationshipTypeDao()
					.getHibernateTemplate().find(
							"from CatalogEntryRelationshipType where name = ?",
							name);
			if (l.size() > 0) {
				message = "A relationship type with that name already exists.";
			} else {
				relType = getUserModel().getCurrentRelationshipType();
				relType.setName(name);
			}
		} catch (Exception ex) {
			throw new RuntimeException(
					"Error setting name: " + ex.getMessage(), ex);
		}
		return message;
	}

	public String setDescription(String description) {
		String message = null;
		try {

			getUserModel().getCurrentRelationshipType().setDescription(
					description);

		} catch (Exception ex) {
			throw new RuntimeException("Error setting description: "
					+ ex.getMessage(), ex);
		}
		return message;
	}
	
	public String setRoleAType(String roleAType) {
		String message = null;
		try {
			CatalogEntryRoleType roleTypeA = getUserModel().getCurrentRelationshipType().getRoleTypeA();
			if(roleTypeA == null){
				roleTypeA = new CatalogEntryRoleType();
				getUserModel().getCurrentRelationshipType().setRoleTypeA(roleTypeA);
			}
			roleTypeA.setType(roleAType);
			
		} catch (Exception ex) {
			throw new RuntimeException("Error setting role A type: "
					+ ex.getMessage(), ex);
		}
		return message;
	}
	
	public String setRoleBType(String roleBType) {
		String message = null;
		try {
			CatalogEntryRoleType roleTypeB = getUserModel().getCurrentRelationshipType().getRoleTypeB();
			if(roleTypeB == null){
				roleTypeB = new CatalogEntryRoleType();
				getUserModel().getCurrentRelationshipType().setRoleTypeA(roleTypeB);
			}
			roleTypeB.setType(roleBType);
			
		} catch (Exception ex) {
			throw new RuntimeException("Error setting role B type: "
					+ ex.getMessage(), ex);
		}
		return message;
	}
	
	public String setRoleAName(String roleAName) {
		String message = null;
		try {
			CatalogEntryRoleType roleTypeB = getUserModel().getCurrentRelationshipType().getRoleTypeB();
			if(roleTypeB != null && roleTypeB.getName() != null && roleTypeB.getName().equals(roleAName)){
				return "The name of role A must be different than role B.";
			}
			CatalogEntryRoleType roleTypeA = getUserModel().getCurrentRelationshipType().getRoleTypeA();
			if(roleTypeA == null){
				roleTypeA = new CatalogEntryRoleType();
				getUserModel().getCurrentRelationshipType().setRoleTypeA(roleTypeA);
			}
			roleTypeA.setName(roleAName);
			
		} catch (Exception ex) {
			throw new RuntimeException("Error setting role A name: "
					+ ex.getMessage(), ex);
		}
		return message;
	}
	
	public String setRoleBName(String roleBName) {
		String message = null;
		try {
			CatalogEntryRoleType roleTypeA = getUserModel().getCurrentRelationshipType().getRoleTypeA();
			if(roleTypeA != null && roleTypeA.getName() != null && roleTypeA.getName().equals(roleBName)){
				return "The name of role B must be different than role A.";
			}
			CatalogEntryRoleType roleTypeB = getUserModel().getCurrentRelationshipType().getRoleTypeB();
			if(roleTypeB == null){
				roleTypeB = new CatalogEntryRoleType();
				getUserModel().getCurrentRelationshipType().setRoleTypeB(roleTypeB);
			}
			roleTypeB.setName(roleBName);
			
		} catch (Exception ex) {
			throw new RuntimeException("Error setting role B name: "
					+ ex.getMessage(), ex);
		}
		return message;
	}
	
	public String setRoleADescription(String roleADescription) {
		String message = null;
		try {
			CatalogEntryRoleType roleTypeA = getUserModel().getCurrentRelationshipType().getRoleTypeA();
			if(roleTypeA == null){
				roleTypeA = new CatalogEntryRoleType();
				getUserModel().getCurrentRelationshipType().setRoleTypeA(roleTypeA);
			}
			roleTypeA.setDescription(roleADescription);
			
		} catch (Exception ex) {
			throw new RuntimeException("Error setting role A description: "
					+ ex.getMessage(), ex);
		}
		return message;
	}
	
	public String setRoleBDescription(String roleBDescription) {
		String message = null;
		try {
			CatalogEntryRoleType roleTypeB = getUserModel().getCurrentRelationshipType().getRoleTypeB();
			if(roleTypeB == null){
				roleTypeB = new CatalogEntryRoleType();
				getUserModel().getCurrentRelationshipType().setRoleTypeB(roleTypeB);
			}
			roleTypeB.setDescription(roleBDescription);
			
		} catch (Exception ex) {
			throw new RuntimeException("Error setting role B description: "
					+ ex.getMessage(), ex);
		}
		return message;
	}
	
	public String validate(){
		String message = null;
		//TODO: do some real validation
		return message;
	}
	
	public Integer save(){
		Integer id = null;
		try {
			CatalogEntryRelationshipType relType = getUserModel().getCurrentRelationshipType();
			HibernateTemplate templ = getCatalogEntryRelationshipTypeDao().getHibernateTemplate();
			templ.saveOrUpdate(relType.getRoleTypeA());
			templ.saveOrUpdate(relType.getRoleTypeB());
			templ.saveOrUpdate(relType);
			id = relType.getId();
		} catch (Exception ex) {
			throw new RuntimeException("Error saving: "
					+ ex.getMessage(), ex);
		}		
		return id;
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
