package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowExecutionService;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SubmitWorkflowCommand;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;
import gov.nih.nci.cagrid.portal.portlet.workflow.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.SimpleFormController;

@Controller
@RequestMapping(params={"action=newInstance"})
@SuppressWarnings("deprecation")
public class NewInstanceFormController extends SimpleFormController {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private WorkflowExecutionService workflowService;
	@Autowired
	@Qualifier("MyExperiment")
	private WorkflowRegistryService registry;
	@Autowired
	private SessionEprs eprs;
	@Autowired
	private Utils utilities;
	
	/* @see org.springframework.web.portlet.mvc.SimpleFormController#processFormSubmission(javax.portlet.ActionRequest, javax.portlet.ActionResponse, java.lang.Object, org.springframework.validation.BindException) */
	@Override
	protected void processFormSubmission(ActionRequest request,	ActionResponse response, Object command, BindException errors) throws Exception {
    	String id = PortletRequestUtils.getStringParameter(request, "id", "NaN");
    	log.debug("processFormSubmission. action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN") + " | id - " + id);
    	SubmitWorkflowCommand cmd = (SubmitWorkflowCommand)command;
    	log.debug("Command Object: " + cmd.getTheWorkflow());
    	try {
	    	WorkflowDescription selectedWorkflow = registry.getWorkflow(id);
	    	log.info("Submitting the selected workflow.. #" + id);
	    	String tempFilePath = saveWorkflowDefinition(selectedWorkflow);
	    	EndpointReferenceType epr = workflowService.submitWorkflow(selectedWorkflow.getName(), tempFilePath, cmd.getInputValues());
	    	UUID uuid = UUID.randomUUID();
	    	log.debug("Will submit UUID : " + uuid.toString());
	    	eprs.put(uuid.toString(), new WorkflowSubmitted(epr, selectedWorkflow, "Submitted"));
	    	cmd.setResult("The Workflow was submitted successfully.");
	    	log.info("The Workflow was submitted successfully.");
    	} catch(Throwable e) {
    		log.error("Error submitting workflow", e);
    		Throwable ex = e.getCause();
    		while(ex.getCause() !=null ) { 
    			ex = ex.getCause();
    		}
    		cmd.setResult(e.getClass().getSimpleName() + " submitting workflow: " +  e.getMessage());
    	}
    }

	/* @see org.springframework.web.portlet.mvc.SimpleFormController#renderFormSubmission(javax.portlet.RenderRequest, javax.portlet.RenderResponse, java.lang.Object, org.springframework.validation.BindException) */
	@Override
	protected ModelAndView renderFormSubmission(RenderRequest request, RenderResponse response, Object cmd, BindException errors) throws Exception {
		log.debug("renderFormSubmission. action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN"));
        return new ModelAndView("json", "contents", ((SubmitWorkflowCommand)cmd).getResult());
	}
	
	/* @see org.springframework.web.portlet.mvc.SimpleFormController#showForm(javax.portlet.RenderRequest, javax.portlet.RenderResponse, org.springframework.validation.BindException) */
	@Override
	protected ModelAndView showForm(RenderRequest request, RenderResponse response, BindException errors) throws Exception {
		String id = PortletRequestUtils.getStringParameter(request, "id", "NaN");
    	log.info("showForm.  Action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN") + " | id: " + id);
    	SubmitWorkflowCommand cmd = new SubmitWorkflowCommand();
    	cmd.setTheWorkflow(registry.getWorkflow(id));
    	return new ModelAndView("newInstance", "cmd", cmd);
    }

	/**
	 * Download the workflow definition to local filesystem
	 * @param wd Workflow Definition
	 * @return path of temporary file
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private String saveWorkflowDefinition(WorkflowDescription wd) throws HttpException, IOException  {
		File tmpPath = new File( System.getProperty("java.io.tmpdir")+"/taverna" );
		tmpPath.mkdirs();
		String defPath = tmpPath.getAbsolutePath()+"/myexperiment_"+wd.getId()+"_v"+wd.getVersion()+".t2flow";
		if(new File(defPath).exists()) { 	log.debug("Definition temporary file already exists so not downloading again."); return defPath;	}
		getUtilities().saveFile(defPath, getUtilities().download(wd.getContentURI()) );
		return defPath;
	}
	
	public SessionEprs getSessionEprs() {return eprs;}
	public void setSessionEprs(SessionEprs sessionEprs) {this.eprs = sessionEprs;}
	public WorkflowExecutionService getWorkflowService() {return workflowService;}
	public void setWorkflowService(WorkflowExecutionService workflowService) {this.workflowService = workflowService;}
	public WorkflowRegistryService getRegistry() {return registry;}
	public void setRegistry(WorkflowRegistryService registry) {this.registry = registry;}
	public Utils getUtilities() {return utilities;}
	public void setUtilities(Utils utilities) {this.utilities = utilities;}
}
