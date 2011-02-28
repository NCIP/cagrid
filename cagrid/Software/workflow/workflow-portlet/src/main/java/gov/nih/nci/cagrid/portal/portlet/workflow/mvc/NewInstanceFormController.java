package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowBean;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;

import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.SimpleFormController;

public class NewInstanceFormController extends SimpleFormController {
	protected final Log log = LogFactory.getLog(getClass());

	private WorkflowService workflowService;
	private WorkflowRegistryService registry;
	private SessionEprs eprs;
	
    @Override
	protected void processFormSubmission(ActionRequest request,	ActionResponse response, Object command, BindException errors) throws Exception {
    	String id = PortletRequestUtils.getStringParameter(request, "id", "NaN");
    	log.debug("processFormSubmission. action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN") + " id - " + id);
    	WorkflowBean cmd = (WorkflowBean)command;
    	try {
	    	WorkflowDescription selectedWorkflow = registry.getWorkflow(id);
	    	log.info("Submitting the selected workflow.. #" + id);
	    	EndpointReferenceType epr = workflowService.submitWorkflow(selectedWorkflow.getName(), selectedWorkflow.getScuflLocation(), cmd.getInputValues());
	    	UUID uuid = UUID.randomUUID();
	    	log.debug("Will submit UUID : " + uuid.toString());
	    	eprs.putEpr(uuid.toString(), new WorkflowSubmitted(epr, selectedWorkflow, workflowService.getStatus(epr), workflowService.getOutput(epr)) );
	    	cmd.setResult("The Workflow was submitted successfully.");
	    	log.info("The Workflow was submitted successfully.");
    	} catch(Exception e) {
    		log.error("Error submitting workflow", e);
    		cmd.setResult(e.getClass().getSimpleName() + " submitting workflow: " +  e.getMessage());
    	}
    }

	@Override
	protected ModelAndView renderFormSubmission(RenderRequest request, RenderResponse response, Object command, BindException errors) throws Exception {
		log.debug("renderFormSubmission. action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN"));
        ModelAndView mav = new ModelAndView(getSuccessView(), getCommandName(), command);
        return mav;
	}
	
	protected ModelAndView showForm(RenderRequest request, RenderResponse response, BindException errors) throws Exception {
		String id = PortletRequestUtils.getStringParameter(request, "id", "NaN");
    	log.info("showForm.  Action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN") + " | id: " + id);
    	WorkflowBean cmd = new WorkflowBean();
    	cmd.setTheWorkflow(registry.getWorkflowStub(id));	
    	return new ModelAndView(getFormView(), getCommandName(), cmd);
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