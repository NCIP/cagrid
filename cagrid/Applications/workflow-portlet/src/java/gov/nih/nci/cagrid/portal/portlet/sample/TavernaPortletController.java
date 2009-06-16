package gov.nih.nci.cagrid.portal.portlet.sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.mvc.AbstractController;
import org.springframework.web.portlet.mvc.SimpleFormController;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.validation.BindException;

import javax.portlet.*;

/**
 * User: sulakhe
 *
 * @author sulakhe@mcs.anl.gov
 */
public class TavernaPortletController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	private TavernaWorkflowServiceHelper twsHelper;
    private Map<String, WorkflowDescription> workflowList;


    public Map<String, WorkflowDescription> getWorkflowList() {
		return workflowList;
	}

	public void setWorkflowList(Map<String, WorkflowDescription> workflowList) {
		this.workflowList = workflowList;
	}

	@Override
    protected void onSubmitAction(Object o, BindException e) throws Exception {
		WorkflowBean cmd = (WorkflowBean)o;
		String id = cmd.getWorkflowId();

		logger.info("Starting onSubmitAction method..");
		if(cmd.getFormState().equals("1"))
		{
			try {
				logger.info("Setting the selected workflow in the commandClass..");
	    		cmd.setTheWorkflow(workflowList.get(id));				
				//cmd.setResult(theWorkflow.getName());
			} catch (Exception e1) {
				e.printStackTrace();
				cmd.setResult("Error Selecting the Workflow.");
				logger.error("Error Selecting the Workflow.", e1);
			}

		}
		else if(cmd.getFormState().equals("2"))
		{
			try {
				logger.info("Submitting the selected workflow..");
				WorkflowDescription temp = workflowList.get(id);
				//String result = twsHelper.submitWorkflow(temp.getName(), temp.getScuflLocation(), cmd.getInputValues());
				SessionEprs eprs = twsHelper.getSessionEprsRef();
				//eprs.putEpr(twsHelper.submitWorkflow(temp.getName(), temp.getScuflLocation(), cmd.getInputValues()), "Active");
				twsHelper.setSessionEprsRef(eprs);
				//cmd.setResult("Number of EPRS:" + Integer.toString(eprs.getEprs().size()));
				cmd.setResult(twsHelper.submitWorkflow(temp.getName(), temp.getScuflLocation(), cmd.getInputValues()));
				
				//cmd.setResult(":"+cmd.getInputValues()[1]+":");
				//cmd.setKeyword(inputs[0]);	
			} catch (Exception e1) {
				e.printStackTrace();
				cmd.setResult("Error Executing Workflow");
				logger.error("Error in executing the workflow", e1);
			}
		}
    }

    @Override
    protected ModelAndView onSubmitRender(Object o) throws Exception {
		logger.info("Starting onSubmitRender method..");
    	WorkflowBean cmd = (WorkflowBean) o;
    	if(cmd.getFormState().equals("1"))
    	{
	        ModelAndView mav = new ModelAndView(getFormView());
	        mav.addObject(getCommandName(), o);
	        return mav;
    	}
    	else //if(cmd.getFormState().equals("2"))
    	{
	        ModelAndView mav = new ModelAndView(getSuccessView());
	        mav.addObject(getCommandName(), o);
	        return mav;
    		
    	}
    }

    protected ModelAndView showForm(RenderRequest request, RenderResponse response, BindException errors) throws Exception
    {

    	
    	logger.info("Starting the showForm method..");
    	PortletPreferences prefs = request.getPreferences();
		String workflowName = prefs.getValue("WorkflowName","Error");
    	
    	WorkflowBean cmd2 = new WorkflowBean();
    	WorkflowDescription[] workflows;
    	
    	workflows = new WorkflowDescription[workflowList.size()];
    	
    	logger.info("Iterating through Map of workflows..");
    	Iterator it = workflowList.entrySet().iterator();
    	Integer count = 0;
    	while(it.hasNext())
    	{
    		Map.Entry pairs = (Map.Entry)it.next();
    		workflows[count++] = (WorkflowDescription) pairs.getValue();
    	}
    	logger.info("Iterating completed.");
    	SessionEprs sess = twsHelper.getSessionEprsRef();
    	//Map<EndpointReferenceType, String> newMap = new HashMap<EndpointReferenceType, String>();
    	//sess.setEprs(newMap);
    	
    	if(sess.getEprs().isEmpty())
    	{
        	cmd2.setKeyword("NULL");

    	}
    	else
    	{
        	cmd2.setKeyword("Number of EPRS:" + Integer.toString(sess.getEprs().size()));
    	}
    	
    	logger.info("Setting all workflows to the commandClass.");
    	cmd2.setAllWorkflows(workflows);
    	cmd2.setFormState("0");
    	return new ModelAndView(getFormView(), getCommandName(), cmd2);
    	
    }

    public TavernaWorkflowServiceHelper getTwsHelper() {
        return twsHelper;
    }

    public void setTwsHelper(TavernaWorkflowServiceHelper twsHelper) {
        this.twsHelper = twsHelper;
    }
    
}

 
 
