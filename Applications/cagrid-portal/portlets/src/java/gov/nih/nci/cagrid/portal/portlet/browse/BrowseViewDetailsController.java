/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.dao.catalog.CatalogEntryDao;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class BrowseViewDetailsController extends AbstractController {
	
	
	private static final Log logger = LogFactory.getLog(BrowseViewDetailsController.class);
	private CatalogEntryDao catalogEntryDao;
	private String objectName;
	private Map<String,String> entryTypeViewMap = new HashMap<String,String>();
	private String emptyViewName;
	
	protected ModelAndView handleRenderRequestInternal(RenderRequest request,
			RenderResponse response) throws Exception {

		CatalogEntry entry = null;
		
		Integer entryId = null;
		try{
			entryId = Integer.valueOf(request.getParameter("entryId"));
		}catch(Exception ex){
			logger.warn("Error parsing entryId: " + ex.getMessage(), ex);
		}
		if(entryId != null){
			entry = getCatalogEntryDao().getById(entryId);
		}
		
		String viewName = null;
		if(entry == null){
			viewName = getEmptyViewName();
		}else{
			viewName = getEntryTypeViewMap().get(entry.getClass().getName());
		}
		if(viewName == null){
			throw new RuntimeException("Couldn't determine view name for: " + entry);
		}
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject(getObjectName(), new CatalogEntryViewBean(entry));

		return mav;
	}



	public CatalogEntryDao getCatalogEntryDao() {
		return catalogEntryDao;
	}

	public void setCatalogEntryDao(CatalogEntryDao catalogEntryDao) {
		this.catalogEntryDao = catalogEntryDao;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Map<String, String> getEntryTypeViewMap() {
		return entryTypeViewMap;
	}

	public void setEntryTypeViewMap(Map<String, String> entryTypeViewMap) {
		this.entryTypeViewMap = entryTypeViewMap;
	}



	public String getEmptyViewName() {
		return emptyViewName;
	}



	public void setEmptyViewName(String emptyViewName) {
		this.emptyViewName = emptyViewName;
	}

}
