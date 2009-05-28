/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import gov.nih.nci.cagrid.portal.portlet.AbstractViewObjectController;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.web.portlet.ModelAndView;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class BrowseViewDetailsController extends AbstractViewObjectController {

	protected ModelAndView handleRenderRequestInternal(RenderRequest request,
			RenderResponse response) throws Exception {

		ModelAndView mav = super.handleRenderRequestInternal(request, response);
		CatalogEntry entry = (CatalogEntry) mav.getModel().get(getObjectName());
		PortletURL portletUrl = response.createRenderURL();
		portletUrl.setParameter("entryId", String.valueOf(entry.getId()));
		mav.addObject("friendlyUrl", portletUrl.toString());
		return mav;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.cagrid.portal.portlet.AbstractViewObjectController#getObject(javax.portlet.RenderRequest)
	 */
	@Override
	protected Object getObject(RenderRequest request) {
		String entryId = request.getParameter("entryId");
		if(entryId == null){
			entryId = "-1";
		}
		CatalogEntry entry = new CatalogEntry();
		entry.setId(Integer.valueOf(entryId));
		return entry;
	}

}
