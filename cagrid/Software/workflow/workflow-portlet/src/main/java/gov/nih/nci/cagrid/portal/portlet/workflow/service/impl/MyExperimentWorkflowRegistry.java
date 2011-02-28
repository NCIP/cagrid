package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.Components;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.Source;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.Workflows;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.Workflows.WorkflowStub;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

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
			List<Workflows.WorkflowStub> workflows = unmarshalWorkflows(get.getResponseBodyAsStream());
			log.debug("found " + workflows.size() + " workflow stubs for tag: " + tag);
			
			List<WorkflowDescription> list = new ArrayList<WorkflowDescription>(workflows.size());
			for(Workflows.WorkflowStub stub : workflows) {
				GetMethod getStub = new GetMethod(stub.getUri()+"&elements=id,title,description,content-uri,uploader,preview");
				http.executeMethod(getStub);
				WorkflowDescription wd = unmarshalWorkflow(getStub.getResponseBodyAsStream());
				list.add(wd);
			}
			log.debug("returning " + list.size() + " workflows");
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
	public WorkflowDescription getWorkflowStub(String id) throws WorkflowException {
		log.debug("Fetching workflow #" + id);
		GetMethod get = new GetMethod("http://"+this.getServer()+"/workflow.xml?id="+id+"&elements=id,title,description,content-uri,uploader,preview");
		try {
			http.executeMethod(get);
			WorkflowDescription wd = unmarshalWorkflow(get.getResponseBodyAsStream());
			return wd;
		} catch (Exception e) {
			log.error("Http Exception", e);
			throw new WorkflowException(e);
		} finally {
			get.releaseConnection();
		}
	}
	
	@Override
	public WorkflowDescription getWorkflow(String id) throws WorkflowException {
		log.debug("Fetching workflow #" + id);
		GetMethod get = new GetMethod("http://"+this.getServer()+"/workflow.xml?id="+id+"&elements=id,title,description,content-uri,uploader,preview,components");
		try {
			http.executeMethod(get);
			WorkflowDescription wd = unmarshalWorkflow(get.getResponseBodyAsStream());
			
			String defPath = "/home/marek/portal-liferay/"+id+".t2flow";
			log.debug("Downloading workflow definition: " + defPath);
			GetMethod getScufl = new GetMethod(wd.getFilePath());
			http.executeMethod(getScufl);
			FileOutputStream fos = new FileOutputStream(defPath);
			fos.write(getScufl.getResponseBody());
			wd.setScuflLocation(defPath);
			fos.close();
			log.debug("Downloaded definition");
			
			if(wd.getComponents() == null) {
				log.warn("Workflow definition specifies no inputs: " + wd.getComponents());
				wd.setInputPorts(0);
				return wd;
			}
			if (wd.getComponents().getDataflow() != null) {
				log.debug("Getting workflow inputs.  Found " + wd.getComponents().getDataflow().size() + " dataflows");
				for( Components.Dataflow df : wd.getComponents().getDataflow() ) {
					if(df.getRole().equals("top") && df.getSource()!=null && df.getSource().size()>0) {
						log.debug("Found root dataflow with " + df.getSource().size() + " sources");
						wd.setInputs(df.getSource());
						wd.setInputPorts(df.getSource().size());
						return wd;
					}
				}
			}
			return wd;
		} catch (Exception e) {
			log.error("Http Exception", e);
			throw new WorkflowException(e);
		} finally {
			get.releaseConnection();
		}
	}
	
	/**
	 * Unmarshall a list of workflows from xml
	 * @param is <code>InputStream</code> of xml
	 * @return	<code>Workflow</code> representation of workflow list xml
	 * @throws JAXBException
	 */
	public static List<WorkflowStub> unmarshalWorkflows(InputStream is) throws JAXBException {
		return ((Workflows)JAXBContext.newInstance(Workflows.class, WorkflowStub.class).createUnmarshaller().unmarshal(is)).workflow;
	}
	
	/**
	 * Unmarshall a single workflow from xml
	 * @param is	<code>InputStream</code> of xml
	 * @return	<code>WorkfowDescription</code> representation of workflow xml
	 * @throws JAXBException
	 */
	public static WorkflowDescription unmarshalWorkflow(InputStream is) throws JAXBException {
		return (WorkflowDescription)JAXBContext.newInstance(WorkflowDescription.class, Components.class, Components.Dataflow.class, Source.class).createUnmarshaller().unmarshal(is);
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
