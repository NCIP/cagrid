/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.portlet.UserModel;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class BrowseViewController implements InitializingBean, Controller {

	private HibernateTemplate hibernateTemplate;
	private String successViewName;
    private String solrServiceUrl;
    private UserModel userModel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.portlet.mvc.Controller#handleActionRequest(javax.portlet.ActionRequest,
	 *      javax.portlet.ActionResponse)
	 */
	public void handleActionRequest(ActionRequest arg0, ActionResponse arg1)
			throws Exception {
		// nothing

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.portlet.mvc.Controller#handleRenderRequest(javax.portlet.RenderRequest,
	 *      javax.portlet.RenderResponse)
	 */
	public ModelAndView handleRenderRequest(RenderRequest request,
			RenderResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getSuccessViewName());
		BrowseTypeEnum browseType = BrowseTypeEnum.valueOf(request
				.getPreferences().getValue("browseType",
						BrowseTypeEnum.DATASET.toString()));
		mav.addObject("browseType", browseType.toString());
        mav.addObject("solrServiceUrl", getSolrServiceUrl());

    	String entryTypeName = null;
		if (browseType.equals(BrowseTypeEnum.DATASET)) {
			entryTypeName = "DataSetCatalogEntry";
            mav.addObject("catalogType", "dataset");
		} else if (browseType.equals(BrowseTypeEnum.COMMUNITY)) {
			entryTypeName = "CommunityCatalogEntry";
            mav.addObject("catalogType", "community");
		} else if (browseType.equals(BrowseTypeEnum.INSTITUTION)) {
			entryTypeName = "InstitutionCatalogEntry";
            mav.addObject("catalogType", "institution");
		} else if (browseType.equals(BrowseTypeEnum.PERSON)) {
			entryTypeName = "PersonCatalogEntry";
              mav.addObject("catalogType", "person");
		} else if (browseType.equals(BrowseTypeEnum.TOOL)) {
			entryTypeName = "ToolCatalogEntry";
            mav.addObject("catalogType", "tool");
		} else if (browseType.equals(BrowseTypeEnum.ALL)) {
			entryTypeName = "CatalogEntry";
		} else {
			throw new RuntimeException("Unknown browse type: " + browseType);
		}

		List entries = getHibernateTemplate().find("from " + entryTypeName);
		mav.addObject("entries", entries);
		
		if(getUserModel().getPortalUser() != null){
			mav.addObject("portalUser", getUserModel().getPortalUser());
		}

		
		return mav;
	}

    public String getSolrServiceUrl() {
        return solrServiceUrl;
    }

    public void setSolrServiceUrl(String solrServiceUrl) {
        this.solrServiceUrl = solrServiceUrl;
    }

    public String getSuccessViewName() {
		return successViewName;
	}

	public void setSuccessViewName(String successViewName) {
		this.successViewName = successViewName;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

}
