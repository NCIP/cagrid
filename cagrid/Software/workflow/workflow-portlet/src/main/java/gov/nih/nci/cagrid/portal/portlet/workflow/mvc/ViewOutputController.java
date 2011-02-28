package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowBean;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;

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
    	WorkflowBean cmd = new WorkflowBean();
		WorkflowSubmitted wSub = (WorkflowSubmitted) eprs.getEprs().get(uuid);
		String[] temp = wSub.getWorkflowOutput();
		for(int i =0; i<temp.length; i++) temp[i] = temp[i].replaceAll("\\n", "<BR>");
		cmd.setOutputs(temp);
    	return new ModelAndView("output", "cmd", cmd);
	}	
	public SessionEprs getSessionEprs() {
		return eprs;
	}
	public void setSessionEprs(SessionEprs sessionEprs) {
		this.eprs = sessionEprs;
	}
}

 
 
