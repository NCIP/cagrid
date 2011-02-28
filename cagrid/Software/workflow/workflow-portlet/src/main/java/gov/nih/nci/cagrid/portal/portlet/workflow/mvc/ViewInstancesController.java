package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowBean;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.AbstractController;

public class ViewInstancesController extends AbstractController {
	protected final Log log = LogFactory.getLog(ViewInstancesController.class);

	private WorkflowService workflowService;
	private SessionEprs eprs;
	
	@Override
	protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
		String action = PortletRequestUtils.getStringParameter(request, "action", "NaN");
    	log.info("handleRenderRequest.  Action: " + action);
    	WorkflowBean cmd = new WorkflowBean();
    	workflowService.updateSession(getSessionEprs());
		cmd.setEprsMap(eprs.getEprs());
    	return new ModelAndView("viewInstances", "cmd", cmd);
	}	

	public SessionEprs getSessionEprs() {
		return eprs;
	}
	public void setSessionEprs(SessionEprs sessionEprs) {
		this.eprs = sessionEprs;
	}
	public WorkflowService getWorkflowService() {
		return workflowService;
	}
	public void setWorkflowService(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}
}

 
 
