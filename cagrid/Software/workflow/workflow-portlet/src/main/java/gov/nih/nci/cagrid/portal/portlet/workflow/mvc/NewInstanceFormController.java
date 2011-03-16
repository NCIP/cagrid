package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowExecutionService;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SubmitWorkflowCommand;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowSubmitted;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.SimpleFormController;

@SuppressWarnings("deprecation")
public class NewInstanceFormController extends SimpleFormController {
	protected final Log log = LogFactory.getLog(getClass());

	/**   */
	private WorkflowExecutionService workflowService;
	/**   */
	private WorkflowRegistryService registry;
	/** All registry service implementations keyed by bean name */
	private Map<String, WorkflowRegistryService> registries;
	/**   */
	private SessionEprs eprs;
	/**   */
	private HttpClient http = new HttpClient();	
	
    /* @see org.springframework.web.portlet.mvc.AbstractFormController#formBackingObject(javax.portlet.PortletRequest) */
    @Override
	protected Object formBackingObject(PortletRequest request) throws Exception {
    	log.debug("formBackingObject() : reqest: " + request);
		return super.formBackingObject(request);
	}

	/* @see org.springframework.web.portlet.mvc.SimpleFormController#processFormSubmission(javax.portlet.ActionRequest, javax.portlet.ActionResponse, java.lang.Object, org.springframework.validation.BindException) */
	@Override
	protected void processFormSubmission(ActionRequest request,	ActionResponse response, Object command, BindException errors) throws Exception {
    	String id = PortletRequestUtils.getStringParameter(request, "id", "NaN");
    	log.debug("processFormSubmission. action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN") + " | id - " + id);
    	SubmitWorkflowCommand cmd = (SubmitWorkflowCommand)command;
    	try {
	    	WorkflowDescription selectedWorkflow = registry.getWorkflow(id);
	    	log.info("Submitting the selected workflow.. #" + id);
	    	String tempFilePath = saveWorkflowDefinition(selectedWorkflow);
	    	EndpointReferenceType epr = workflowService.submitWorkflow(selectedWorkflow.getName(), tempFilePath, cmd.getInputValues());
	    	UUID uuid = UUID.randomUUID();
	    	log.debug("Will submit UUID : " + uuid.toString());
//	    	eprs.put(uuid.toString(), new WorkflowSubmitted(epr, selectedWorkflow, workflowService.getStatus(epr), workflowService.getOutput(epr) ) );
	    	eprs.put(uuid.toString(), new WorkflowSubmitted(epr, selectedWorkflow, "Submitted"));
	    	cmd.setResult("The Workflow was submitted successfully.");
	    	log.info("The Workflow was submitted successfully.");
    	} catch(Throwable e) {
    		log.error("Error submitting workflow", e);
    		cmd.setResult(e.getClass().getSimpleName() + " submitting workflow: " +  e.getMessage());
    	}
    }

	/* @see org.springframework.web.portlet.mvc.SimpleFormController#renderFormSubmission(javax.portlet.RenderRequest, javax.portlet.RenderResponse, java.lang.Object, org.springframework.validation.BindException) */
	@Override
	protected ModelAndView renderFormSubmission(RenderRequest request, RenderResponse response, Object command, BindException errors) throws Exception {
		log.debug("renderFormSubmission. action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN"));
        ModelAndView mav = new ModelAndView("submitted", "cmd", command);
        return mav;
	}
	
	/* @see org.springframework.web.portlet.mvc.SimpleFormController#showForm(javax.portlet.RenderRequest, javax.portlet.RenderResponse, org.springframework.validation.BindException) */
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
		String defPath = System.getProperty("java.io.tmpdir")+"/taverna/myexperiment_"+wd.getId()+"_v"+wd.getVersion()+".t2flow";
		File defFile = new File(defPath);
		if(!defFile.createNewFile()) { 	log.debug("Definition temporary file already exists so not downloading again."); return defPath;	}
		FileOutputStream fos = new FileOutputStream(defFile);
		log.debug("Downloading workflow definition: " + defPath);
//XXX relace with Resource
		GetMethod get = new GetMethod(wd.getContentURI());
		try {
			http.executeMethod(get);
			fos.write(get.getResponseBody());
			log.debug("Downloaded definition");
			return defPath;
		} finally {
			if(get!=null) get.releaseConnection();
			if(fos!=null) fos.close();
		}
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
	public Map<String, WorkflowRegistryService> getRegistries() {
		return registries;
	}
	public void setRegistries(Map<String, WorkflowRegistryService> registries) {
		this.registries = registries;
	}
}
