package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowBean;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.AbstractController;

public class ViewDefinitionsController extends AbstractController {
	protected final Log log = LogFactory.getLog(ViewDefinitionsController.class);

	private WorkflowService workflowService;
	private WorkflowRegistryService registry;
	private SessionEprs eprs;
	
	@Override
	protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
		String action = PortletRequestUtils.getStringParameter(request, "action", "NaN");
    	log.info("handleRenderRequest.  Action: " + action);
    	WorkflowBean cmd = new WorkflowBean();
		WorkflowDescription[] workflows = registry.getWorkflows().values().toArray(new WorkflowDescription[0]);
		workflowService.updateSession(getSessionEprs());
		cmd.setEprsMap(eprs.getEprs());
    	cmd.setKeyword(Integer.toString(eprs.getEprs().size()));
    	cmd.setAllWorkflows(workflows);
    	return new ModelAndView("view", "cmd", cmd);
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
	public WorkflowRegistryService getRegistry() {
		return registry;
	}
	public void setRegistry(WorkflowRegistryService registry) {
		this.registry = registry;
	}
}

 
 
