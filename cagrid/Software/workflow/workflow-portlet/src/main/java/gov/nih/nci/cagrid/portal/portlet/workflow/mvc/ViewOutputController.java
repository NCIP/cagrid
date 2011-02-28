package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowBean;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;

import java.rmi.RemoteException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.AbstractController;

public class ViewOutputController extends AbstractController {
	protected final Log log = LogFactory.getLog(ViewOutputController.class);

	private WorkflowService workflowService;
	private WorkflowRegistryService registry;
	private SessionEprs eprs;
	
	@Override
	protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
		String uuid = PortletRequestUtils.getStringParameter(request, "uuid", "NaN");
    	log.info("handleRenderRequest.  Action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN") + "  uuid : " + uuid);
    	WorkflowBean cmd = new WorkflowBean();
		
    	log.debug("Show the Output of workflow..");
		updateSession();
		cmd.setEprsMap(eprs.getEprs());
		WorkflowSubmitted wSub = (WorkflowSubmitted) eprs.getEprs().get(uuid);
		
		String[] temp = wSub.getWorkflowOutput();
		for(int i =0; i<temp.length; i++) temp[i] = temp[i].replaceAll("\\n", "<BR>");
		cmd.setOutputs(temp);
    	
    	return new ModelAndView("output", "cmd", cmd);
	}	

	/**
	 * Update status of submitted workflows.
	 */
    public void updateSession() throws MalformedURIException, RemoteException{
		if(this.eprs.getEprs().isEmpty()) { log.info("The sessions object is EMPTY."); return; }
		log.info("Updating status for [" + eprs.getEprs().size() + "] submitted workflows");
		for(WorkflowSubmitted wSub : eprs.getEprs().values()) {
			if("Done".equals(wSub.getStatus())) continue;
			wSub.setStatus(workflowService.getStatus(wSub.getEpr()));
			log.debug("Workflow status: " + wSub);
			if("Done".equals(wSub.getStatus())) wSub.setWorkflowOutput(workflowService.getOutput(wSub.getEpr()));
		}
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

 
 
