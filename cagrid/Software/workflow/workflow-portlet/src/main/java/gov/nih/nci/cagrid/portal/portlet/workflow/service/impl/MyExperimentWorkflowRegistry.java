package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.Workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyExperimentWorkflowRegistry implements WorkflowRegistryService {
	private static final Log log = LogFactory.getLog(MyExperimentWorkflowRegistry.class);
	
	private HttpClient http = new HttpClient();	
	private Cookie sessionCookie;
	
	private String username;
	private String password;
	private String server;
	
	private Map<String, WorkflowDescription> cache = new HashMap<String, WorkflowDescription>();
	
	public MyExperimentWorkflowRegistry() {
		log.info("Initializing MyExperiment.org registry.");
	}
	
	public MyExperimentWorkflowRegistry(String username, String password, String server) {
		this.username = username;
		this.password = password;
		this.server = server;
	}

	public void getSessionCookie() {
		log.debug("Getting Session Cookie");
		PostMethod authMethod = new PostMethod("http://"+this.server+"/session/create");
		try {
			authMethod.addRequestHeader("Content-Type", "application/xml");
			authMethod.setRequestBody("<session><username>"+this.getUsername()+"</username><password>"+this.getPassword()+"</password></session>");
			http.executeMethod(authMethod);
			String val = authMethod.getResponseHeader("Set-Cookie").getValue();
			val = val.substring(0, val.indexOf(';'));
			String []c = val.split("=");
			String cookieName = c[0];
			String cookieValue = c[1];
		    this.sessionCookie = new Cookie(this.getServer(), cookieName, cookieValue, "/", 99, true);
		} catch (HttpException e) {
			log.error("Http Exception", e);
		} catch(IOException e) {
			log.error("IOException", e);
		} finally {
			authMethod.releaseConnection();
		}
	}

	/**
	 * Retrieve workflows with a specific tag from MyExperiment.org
	 * @param tag MyExperiment.org workflow tag
	 * @return <code>List&lt;WorkflowDescription&gt; containing the workflow definitions
	 * @throws <code>WorkflowException</code>
	 */
	private List<WorkflowDescription> getWorkflows(String tag) throws WorkflowException {
		GetMethod get = new GetMethod("http://"+this.getServer()+"/workflows.xml?tag="+tag);
		try {
			http.executeMethod(get);
			List<Workflows.WorkflowStub> workflows = Workflows.unmarshalWorkflows(get.getResponseBodyAsStream());
			log.debug("found " + workflows.size() + " workflow stubs for tag: " + tag);
			
			List<WorkflowDescription> list = new ArrayList<WorkflowDescription>(workflows.size());
			for(Workflows.WorkflowStub stub : workflows) {
				GetMethod getStub = new GetMethod(stub.getUri());
				http.executeMethod(getStub);
				WorkflowDescription wd = Workflows.unmarshalWorkflow(getStub.getResponseBodyAsStream());
				list.add(wd);
			}
			return list;
		} catch (Exception e) {
			log.error("Http Exception", e);
			throw new WorkflowException(e);
		} finally {
			get.releaseConnection();
		}
	}
	
	@Override
	public Map<String, WorkflowDescription> getWorkflows() throws WorkflowException {
		cache.clear();
		for(WorkflowDescription wd : getWorkflows("cabig")) {
			cache.put(wd.getWorkflowId(), wd);
		}
		return cache;
	}

	@Override
	public WorkflowDescription getWorkflow(String id) throws WorkflowException {
		GetMethod get = new GetMethod("http://"+this.getServer()+"/workflow.xml?id="+id);
		try {
			http.executeMethod(get);
			return Workflows.unmarshalWorkflow(get.getResponseBodyAsStream());
		} catch (Exception e) {
			log.error("Http Exception", e);
			throw new WorkflowException(e);
		} finally {
			get.releaseConnection();
		}
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
}
