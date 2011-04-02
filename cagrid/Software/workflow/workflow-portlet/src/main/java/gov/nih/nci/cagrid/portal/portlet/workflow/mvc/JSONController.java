package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.AbstractController;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping(params="action=json")
public class JSONController extends AbstractController {
	private static Log log = LogFactory.getLog(JSONController.class);

	private SessionEprs sessionEprs;
	
	@Override
	public ModelAndView handleRenderRequest(RenderRequest request,RenderResponse response) throws Exception {
		String mode = PortletRequestUtils.getStringParameter(request, "mode");
		if(!mode.equals("jackson") && !mode.equals("text")) throw new IllegalArgumentException("Mode parameter must be either 'jackson' | 'text' ");
		log.debug("JSON Handler.. mode: " + mode);
		
		if(mode.equals("jackson")) {
			return new ModelAndView(new MappingJacksonJsonView(),  "eprs", getSessionEprs());
		} else {
			return new ModelAndView("json", "contents", PortletRequestUtils.getStringParameter(request, "contents", "NaN"));	
		}
	}
	
	public SessionEprs getSessionEprs() {
		return sessionEprs;
	}
	public void setSessionEprs(SessionEprs sessionEprs) {
		this.sessionEprs = sessionEprs;
	}
}