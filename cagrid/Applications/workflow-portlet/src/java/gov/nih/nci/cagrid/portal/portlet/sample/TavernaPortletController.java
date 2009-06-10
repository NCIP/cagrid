package gov.nih.nci.cagrid.portal.portlet.sample;

import java.util.Iterator;
import java.util.Map;

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

	TavernaWorkflowServiceHelper twsHelper;
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

		if(cmd.getFormState().equals("1"))
		{
			try {
	    		cmd.setTheWorkflow(workflowList.get(id));				
				//cmd.setResult(theWorkflow.getName());
			} catch (Exception e1) {
				e.printStackTrace();
				cmd.setResult("Error Executing Workflow");
			}

		}
		else if(cmd.getFormState().equals("2"))
		{
			try {
								
				WorkflowDescription temp = workflowList.get(id);
				cmd.setResult(twsHelper.submitWorkflow(temp.getName(), temp.getScuflLocation(), cmd.getInputValues()));
				//cmd.setResult(":"+cmd.getInputValues()[1]+":");
				//cmd.setKeyword(inputs[0]);	
			} catch (Exception e1) {
				e.printStackTrace();
				cmd.setResult("Error Executing Workflow");
			}
		}
    }

    @Override
    protected ModelAndView onSubmitRender(Object o) throws Exception {
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

    	
    	PortletPreferences prefs = request.getPreferences();
		String workflowName = prefs.getValue("WorkflowName","Error");
    	
    	WorkflowBean cmd2 = new WorkflowBean();
    	WorkflowDescription[] workflows;
    	
    	workflows = new WorkflowDescription[workflowList.size()];
    	
    	Iterator it = workflowList.entrySet().iterator();
    	Integer count = 0;
    	while(it.hasNext())
    	{
    		Map.Entry pairs = (Map.Entry)it.next();
    		workflows[count++] = (WorkflowDescription) pairs.getValue();
    	}
    	
    	
    	cmd2.setAllWorkflows(workflows);
    	cmd2.setKeyword(System.getProperty("user.dir"));
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

 
 
