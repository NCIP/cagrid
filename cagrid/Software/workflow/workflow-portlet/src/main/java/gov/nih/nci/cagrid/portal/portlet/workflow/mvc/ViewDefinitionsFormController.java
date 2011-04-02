package gov.nih.nci.cagrid.portal.portlet.workflow.mvc;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowExecutionService;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.SessionEprs;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.AbstractController;

@Controller
@RequestMapping("VIEW")
public class ViewDefinitionsFormController extends AbstractController {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private WorkflowExecutionService workflowService;
	@Autowired
	@Qualifier("MyExperiment")
	private WorkflowRegistryService registry;
	@Autowired
	private SessionEprs eprs;
	
	@Override
	protected ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
		String id = PortletRequestUtils.getStringParameter(request, "id", "NaN");
    	log.info("showForm.  Action: " + PortletRequestUtils.getStringParameter(request, "action", "NaN") + " | id: " + id);
    	Map<String, Object> model = new HashMap<String,Object>();
    	model.put("workflows", registry.getWorkflows().values().toArray(new WorkflowDescription[0]));
    	return new ModelAndView("viewform", model);
    }

	public SessionEprs getSessionEprs() {return eprs;}
	public void setSessionEprs(SessionEprs sessionEprs) {this.eprs = sessionEprs;}
	public WorkflowExecutionService getWorkflowService() {return workflowService;	}
	public void setWorkflowService(WorkflowExecutionService workflowService) {this.workflowService = workflowService;}
	public WorkflowRegistryService getRegistry() {return registry;	}
	public void setRegistry(WorkflowRegistryService registry) {this.registry = registry;	}
}
