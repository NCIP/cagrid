package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.AbstractController;

public class ViewOutputController extends AbstractController {
	protected final Log log = LogFactory.getLog(ViewOutputController.class);

	private SessionEprs eprs;
	
	@Override
	protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
		String uuid = PortletRequestUtils.getStringParameter(request, "uuid", "NaN");
    	log.info("handleRenderRequest.  Action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN") + "  uuid : " + uuid);
		WorkflowSubmitted wSub = (WorkflowSubmitted) eprs.getEprs().get(uuid);
		String[] temp = wSub.getWorkflowOutput();
		for(int i =0; i<temp.length; i++) temp[i] = temp[i].replaceAll("\\n", "<BR>");
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("outputs", temp);
		model.put("uuid", uuid);
    	return new ModelAndView("output", "model", model);
	}	
	public SessionEprs getSessionEprs() {
		return eprs;
	}
	public void setSessionEprs(SessionEprs sessionEprs) {
		this.eprs = sessionEprs;
	}
}

 
 
