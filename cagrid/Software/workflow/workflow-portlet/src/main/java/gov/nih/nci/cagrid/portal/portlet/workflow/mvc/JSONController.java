package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

@Controller
@RequestMapping(method={ RequestMethod.GET }, params={ "action"}, value={"json"} )
public class JSONController extends AbstractController {
	private static Log log = LogFactory.getLog(JSONController.class);

	@Override
	public ModelAndView handleRenderRequest(RenderRequest request,RenderResponse response) throws Exception {
		log.debug("handleRenderRequest");
		return new ModelAndView("json", "contents", "{ first : 'first', last : 'last' } ");
	}
}