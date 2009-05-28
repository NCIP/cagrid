/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 *
 */
public class BrowseViewController implements InitializingBean, Controller {
	
	private String successViewName;

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {

	}

	/* (non-Javadoc)
	 * @see org.springframework.web.portlet.mvc.Controller#handleActionRequest(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
	 */
	public void handleActionRequest(ActionRequest arg0, ActionResponse arg1)
			throws Exception {
		// nothing

	}

	/* (non-Javadoc)
	 * @see org.springframework.web.portlet.mvc.Controller#handleRenderRequest(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	public ModelAndView handleRenderRequest(RenderRequest request,
			RenderResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(getSuccessViewName());
		mav.addObject("browseType", request.getPreferences().getValue("browseType", "DATASET"));
		return mav;
	}

	public String getSuccessViewName() {
		return successViewName;
	}

	public void setSuccessViewName(String successViewName) {
		this.successViewName = successViewName;
	}


}
