package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowBean;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.SimpleFormController;

public class TavernaPortletController extends SimpleFormController {
	protected final Log log = LogFactory.getLog(getClass());

	private WorkflowService workflowService;
	private WorkflowRegistryService registry;
	
	private SessionEprs eprs;
	
	@Override
	protected Map referenceData(PortletRequest request) throws Exception {
		return super.referenceData(request);
	}
	/*
	@Override
    protected void onSubmitAction(Object o, BindException e) throws Exception {
		WorkflowBean cmd = (WorkflowBean)o;
		String id = cmd.getWorkflowId();

		if(cmd.getFormState().equals("1")) {
			log.debug("Setting the selected workflow in the commandClass..");
	    	cmd.setTheWorkflow(registry.getWorkflow(id));		
		} else if(cmd.getFormState().equals("2")) {//This else if loop handles the submission of the workflow.
			log.info("Submitting the selected workflow..");
			WorkflowDescription selectedWorkflow = registry.getWorkflow(id);
			EndpointReferenceType epr = workflowService.submitWorkflow(selectedWorkflow.getName(), selectedWorkflow.getScuflLocation(), cmd.getInputValues());
			UUID uuid = UUID.randomUUID();
			log.debug("Will submit UUID : " + uuid.toString());
			eprs.putEpr(uuid.toString(), new WorkflowSubmitted(epr, selectedWorkflow, workflowService.getStatus(epr), workflowService.getOutput(epr)) );
			cmd.setResult("The Workflow was submitted successfully.");
			log.info("The Workflow was submitted successfully.");
		} else if(cmd.getFormState().equals("3")) { //This handles the List All Submitted Workflows View. 
			log.debug("List all the submitted workflows..");
			workflowService.updateSession();
			cmd.setEprsMap(eprs.getEprs());
		} else if(cmd.getFormState().equals("4")){ //This displays the out of the selected workflow.
			log.debug("Show the Output of workflow..");
			workflowService.updateSession();
			cmd.setEprsMap(eprs.getEprs());
			WorkflowSubmitted wSub = (WorkflowSubmitted) eprs.getEprs().get(cmd.getSelectedUUID());
			cmd.setViewResolver(wSub.getWorkflowDesc().getViewResolver());
			String[] temp = wSub.getWorkflowOutput();
			for(int i =0; i<temp.length; i++) temp[i] = temp[i].replaceAll("\\n", "<BR>");
			cmd.setOutputs(temp);
		}
    }

    @Override
    protected ModelAndView onSubmitRender(Object o) throws Exception {
    	WorkflowBean cmd = (WorkflowBean) o;
    	String view = getFormView();
 
		if(cmd.getFormState().equals("1")) view = "newInstance";
		else if(cmd.getFormState().equals("2")) view = getSuccessView();    		
    	else if(cmd.getFormState().equals("3")) view = "viewInstances";    		
    	else if(cmd.getFormState().equals("4")) view = cmd.getViewResolver();
  
        ModelAndView mav = new ModelAndView(view);
        mav.addObject(getCommandName(), o);
        return mav;
    }*/

    @Override
	protected void processFormSubmission(ActionRequest request,	ActionResponse response, Object command, BindException errors) throws Exception {
    	log.debug("processFormSubmission");
		WorkflowBean cmd = (WorkflowBean)command;
		String id = cmd.getWorkflowId();

		if(cmd.getFormState().equals("1")) {
			log.debug("Setting the selected workflow in the commandClass..");
	    	cmd.setTheWorkflow(registry.getWorkflow(id));	
			response.setRenderParameter("action", "newInstance");
		} else if(cmd.getFormState().equals("2")) {//This else if loop handles the submission of the workflow.
			log.info("Submitting the selected workflow..");
			WorkflowDescription selectedWorkflow = registry.getWorkflow(id);
			EndpointReferenceType epr = workflowService.submitWorkflow(selectedWorkflow.getName(), selectedWorkflow.getScuflLocation(), cmd.getInputValues());
			UUID uuid = UUID.randomUUID();
			log.debug("Will submit UUID : " + uuid.toString());
			eprs.putEpr(uuid.toString(), new WorkflowSubmitted(epr, selectedWorkflow, workflowService.getStatus(epr), workflowService.getOutput(epr)) );
			cmd.setResult("The Workflow was submitted successfully.");
			log.info("The Workflow was submitted successfully.");
			response.setRenderParameter("action", getSuccessView()); 
		} else if (cmd.getFormState().equals("3")) {
			log.debug("List all the submitted workflows..");
			workflowService.updateSession();
			cmd.setEprsMap(eprs.getEprs());
			response.setRenderParameter("action", "viewInstances");
		} else if(cmd.getFormState().equals("4")){ //This displays the out of the selected workflow.
			log.debug("Show the Output of workflow..");
			workflowService.updateSession();
			cmd.setEprsMap(eprs.getEprs());
			WorkflowSubmitted wSub = (WorkflowSubmitted) eprs.getEprs().get(cmd.getSelectedUUID());
			cmd.setViewResolver(wSub.getWorkflowDesc().getViewResolver());
			String[] temp = wSub.getWorkflowOutput();
			for(int i =0; i<temp.length; i++) temp[i] = temp[i].replaceAll("\\n", "<BR>");
			cmd.setOutputs(temp);
			response.setRenderParameter("action", "viewOutput");
		}
	}

	@Override
	protected ModelAndView renderFormSubmission(RenderRequest request,RenderResponse response, Object command, BindException errors) throws Exception {
		log.debug("renderFormSubmission");
		WorkflowBean cmd = (WorkflowBean) command;
    	String view = getFormView();
 
		if(cmd.getFormState().equals("1")) view = "newInstance";
		else if(cmd.getFormState().equals("2")) view = getSuccessView();    		
    	else if(cmd.getFormState().equals("3")) view = "viewInstances";    		
    	else if(cmd.getFormState().equals("4")) view = cmd.getViewResolver();
  
        ModelAndView mav = new ModelAndView(view);
        mav.addObject(getCommandName(), command);
        return mav;
	}
	
	protected ModelAndView showForm(RenderRequest request, RenderResponse response, BindException errors) throws Exception {
    	log.info("showForm");
    	WorkflowBean cmd2 = new WorkflowBean();

    	Map<String,WorkflowDescription> workflowList = registry.getWorkflows();
    	WorkflowDescription[] workflows = new WorkflowDescription[workflowList.size()];
		int count = 0;
		for(Map.Entry<String, WorkflowDescription> entry : workflowList.entrySet()) workflows[count++]=entry.getValue();

		workflowService.updateSession();

    	if(eprs.getEprs().isEmpty()) {
        	cmd2.setKeyword("0");
    	} else {
    		cmd2.setEprsMap(eprs.getEprs());
        	cmd2.setKeyword(Integer.toString(eprs.getEprs().size()));
    	}
    	log.info("Setting all workflows to the commandClass.");
    	cmd2.setAllWorkflows(workflows);
    	cmd2.setFormState("0");
    	return new ModelAndView(getFormView(), getCommandName(), cmd2);
    }
    
	/**
	 * Update status of submitted workflows.
	 */
    public void updateSession() throws MalformedURIException, RemoteException{
		if(this.eprs.getEprs().isEmpty()) { log.info("The sessions object is EMPTY."); return; }
		log.info("Updating status for [" + eprs.getEprs().size() + "] submitted workflows");

		for(Map.Entry<String,WorkflowSubmitted> pairs : eprs.getEprs().entrySet()) {
			if("Done".equals(pairs.getValue().getStatus())) continue;
			String uuid = pairs.getKey();
			WorkflowSubmitted wSub = pairs.getValue();
			
			wSub.setStatus(workflowService.getStatus(wSub.getEpr()));
			if( "Done".equals(wSub.getStatus()) ) {
				wSub.setWorkflowOutput(workflowService.getOutput(wSub.getEpr()));
			} else if("Pending".equals(wSub.getStatus()))	{
				Map<String, WorkflowSubmitted> newMap = eprs.getEprs();
				newMap.remove(uuid);
				eprs.setEprs(newMap);
			}
		}
		this.setSessionEprs(eprs);
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

 
 
