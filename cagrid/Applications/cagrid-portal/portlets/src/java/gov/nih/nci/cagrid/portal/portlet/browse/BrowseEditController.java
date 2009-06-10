/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.portlet.AbstractViewObjectController;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 *
 */
public class BrowseEditController extends AbstractViewObjectController {
	
	private static final Log logger = LogFactory.getLog(BrowseEditController.class);

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.portal.portlet.AbstractViewObjectController#getObject(javax.portlet.RenderRequest)
	 */
	@Override
	protected Object getObject(RenderRequest request) {
		BrowseCommand command = new BrowseCommand();
		PortletPreferences prefs = request.getPreferences();
		command.setBrowseType(prefs.getValue("browseType", "DATASET"));
		return command;
	}

}
