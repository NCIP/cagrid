package gov.nih.nci.cagrid.portal.portlet.sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.mvc.AbstractController;
import org.springframework.web.portlet.mvc.SimpleFormController;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.validation.BindException;

import javax.portlet.*;

/**
 * Controller class that manages all the interactions.
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 * Status: Currently this is the only controller that handles all the interactions. 
 * Eventually, need to use PortletModeParameterHandlerMappings and create a controller per view(interaction).
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
		
		//This If loop handles the Form where Users enters Input values.
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
		else if(cmd.getFormState().equals("2")) //This else if loop handles the submission of the workflow.
		{
			try {
				logger.info("Submitting the selected workflow..");
				WorkflowDescription selectedWorkflow = workflowList.get(id);
				EndpointReferenceType epr = twsHelper.submitWorkflow(
						selectedWorkflow.getName(), selectedWorkflow.getScuflLocation(), cmd.getInputValues());
				
				UUID uuid = UUID.randomUUID();
				logger.info("UUID : " + uuid.toString());
				SessionEprs eprs = twsHelper.getSessionEprsRef();
				eprs.putEpr(uuid.toString(), new WorkflowSubmitted(epr, selectedWorkflow, twsHelper.getStatus(epr), twsHelper.getOutput(epr)) );
				twsHelper.setSessionEprsRef(eprs);				
				cmd.setResult("The Workflow was submitted successfully.");
				logger.error("The Workflow was submitted successfully.");
				
			} catch (Exception e1) {
				e.printStackTrace();
				cmd.setResult("Error Submitting Workflow" + "<BR>" + e1.getMessage() );
				logger.error("Error in executing the workflow", e1);
			}
		}
		else if(cmd.getFormState().equals("3")) //This handles the List All Submitted Workflows View. 
		{
			logger.info("List all the submitted workflows..");
			twsHelper.updateSession();
			cmd.setEprsMap(twsHelper.getSessionEprsRef().getEprs());
		}
		else if(cmd.getFormState().equals("4")){ //This displays the out of the selected workflow.
			logger.info("Show the Output of workflow..");
			twsHelper.updateSession();
			
			WorkflowSubmitted wSub = (WorkflowSubmitted) twsHelper.getSessionEprsRef().getEprs().get(cmd.getSelectedUUID());
			
			String[] temp = wSub.getWorkflowOutput();
			cmd.setViewResolver(((WorkflowDescription)wSub.getWorkflowDesc()).getViewResolver());

			for(int i =0; i<temp.length; i++)
			{
				// This following if loop is added to create a TABLE view for Lymphoma workflow output.
				// Eventually, add an abstract class that would handle all the output types.
				if(cmd.getViewResolver().equals("lymphoma"))
				{
					//temp[i].replaceAll("\\[", "");
					//temp[i].replaceAll("\\]", "");
					temp[i] = twsHelper.getLymphomaResultViewer(temp[i]);
				}
				temp[i] = temp[i].replaceAll("\\n", "<BR>");
			}
			cmd.setOutputs(temp);
			cmd.setEprsMap(twsHelper.getSessionEprsRef().getEprs());
		}
		
    }

    @Override
    protected ModelAndView onSubmitRender(Object o) throws Exception {
		logger.info("Starting onSubmitRender method..");
    	WorkflowBean cmd = (WorkflowBean) o;
    	String getView = getFormView();
 
    	if(cmd.getFormState().equals("2"))
    	{
			logger.info("Inside formState=2..");
    		getView = getSuccessView();    		
    	}
    	else if(cmd.getFormState().equals("3"))
    	{	
			logger.info("Inside formState=3..");
    		getView = "list";    		
    	}
    	else if(cmd.getFormState().equals("4")){
    		logger.info("Inside formState=4");    		
    		getView = cmd.getViewResolver();
    		logger.info("ViewResolver: " + getView);
    	}
        ModelAndView mav = new ModelAndView(getView);
        mav.addObject(getCommandName(), o);
        return mav;

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
    	//Map<EndpointReferenceType, String> newMap = new HashMap<EndpointReferenceType, String>();
    	//sess.setEprs(newMap);
    	

		twsHelper.updateSession();
    	SessionEprs sess = twsHelper.getSessionEprsRef();

    	if(sess.getEprs().isEmpty())
    	{
        	cmd2.setKeyword("0");

    	}
    	else
    	{
    		cmd2.setEprsMap(twsHelper.getSessionEprsRef().getEprs());
        	cmd2.setKeyword(Integer.toString(sess.getEprs().size()));
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

 
 
