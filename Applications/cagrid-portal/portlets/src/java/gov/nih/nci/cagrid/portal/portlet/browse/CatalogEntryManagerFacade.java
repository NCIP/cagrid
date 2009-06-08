/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import gov.nih.nci.cagrid.portal.portlet.UserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 *
 */
@Transactional
public class CatalogEntryManagerFacade {

	private static final Log logger = LogFactory.getLog(CatalogEntryManagerFacade.class);
	private UserModel userModel;
	private HibernateTemplate hibernateTemplate;
	private CatalogEntryFactory catalogEntryFactory;
	
	/**
	 * 
	 */
	public CatalogEntryManagerFacade() {

	}
	
	
	public void createCatalogEntry(String entryTypeName) {
		try{
			CatalogEntry ce = getCatalogEntryFactory().newCatalogEntry(entryTypeName);
			getUserModel().setCurrentCatalogEntry(ce);
		}catch(Exception ex){
			String msg = "Error creating catalog entry: " + ex.getMessage();
			logger.debug(msg, ex);
			throw new RuntimeException(msg, ex);
		}
	}
	
	public void saveCatalogEntry(){
		try{
			getHibernateTemplate().save(getUserModel().getCurrentCatalogEntry());
		}catch(Exception ex){
			String msg = "Error creating catalog entry: " + ex.getMessage();
			logger.debug(msg, ex);
			throw new RuntimeException(msg, ex);
		}
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


	public CatalogEntryFactory getCatalogEntryFactory() {
		return catalogEntryFactory;
	}


	public void setCatalogEntryFactory(CatalogEntryFactory catalogEntryFactory) {
		this.catalogEntryFactory = catalogEntryFactory;
	}
	
	

}
