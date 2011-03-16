package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowExecutionService;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.AbstractController;

public class ViewDefinitionsController extends AbstractController  {
	protected final Log log = LogFactory.getLog(ViewDefinitionsController.class);

	private WorkflowExecutionService workflowService;
	private WorkflowRegistryService registry;
	private SessionEprs eprs;
	
	/* @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse) */
	@Override
	protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response)  throws Exception {
			log.info("handleRenderRequest.  Action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN"));
			WorkflowDescription [] workflows = registry.getWorkflows().values().toArray(new WorkflowDescription[0]);
			return new ModelAndView("view", "workflows", workflows);
	}
	public SessionEprs getSessionEprs() {
		return eprs;
	}
	public void setSessionEprs(SessionEprs sessionEprs) {
		this.eprs = sessionEprs;
	}
	public WorkflowExecutionService getWorkflowService() {
		return workflowService;
	}
	public void setWorkflowService(WorkflowExecutionService workflowService) {
		this.workflowService = workflowService;
	}
	public WorkflowRegistryService getRegistry() {
		return registry;
	}
	public void setRegistry(WorkflowRegistryService registry) {
		this.registry = registry;
	}
}
