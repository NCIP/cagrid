package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;
import gov.nih.nci.cagrid.portal.portlet.workflow.service.impl.MyExperimentWorkflows.WorkflowStub;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.ws.http.HTTPException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.portlet.context.PortletContextAware;

/**
 * Workflow registry implementation using MyExperiment.org REST API.
 * @author Marek Kedzierski
 */
public class MyExperimentWorkflowRegistry implements WorkflowRegistryService, ApplicationContextAware, PortletContextAware {
	private static final Log log = LogFactory.getLog(MyExperimentWorkflowRegistry.class);
	
	private ApplicationContext applicationContext;
	private PortletContext portletContext;
	
	private HttpClient http = new HttpClient();	
	private Cookie sessionCookie;
	
	/** MyExperiment.org username */
	private String username;
	/** MyExperiment.org password */
	private String password;
	/** MyExperiment.org server. i.e. main or sandbox */
	private String server;
	/** MyExperiment.org workflow definition tag */
	private String tag;
	
	public MyExperimentWorkflowRegistry() {
		log.info("Initializing MyExperiment.org registry.");
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService#getWorkflows()
	 */
	public Map<String, WorkflowDescription> getWorkflows() throws WorkflowException {
		return getWorkflows(this.getTag());
	}
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.portal.portlets.workflow.WorkflowRegistryService#getWorkflow(java.lang.String)
	 */
	public WorkflowDescription getWorkflow(String id) throws WorkflowException {
		log.debug("Fetching workflow #" + id);
		GetMethod get = new GetMethod("http://"+this.getServer()+"/workflow.xml?id="+id+"&elements=id,title,description,content-uri,uploader,preview,thumbnail,thumbnail-big,svg,components");
		try {
			http.executeMethod(get);	
			WorkflowDescription wd = unmarshalWorkflow(get.getResponseBodyAsStream());
			if (wd.getComponents() == null && wd.getComponents().getDataflow() != null) {
				log.debug("Getting workflow inputs.  Found " + wd.getComponents().getDataflow().size() + " dataflows");
				for( WorkflowDescription.Dataflow df : wd.getComponents().getDataflow() ) {
					if(df.getRole().equals("top") && df.getSource()!=null && df.getSource().size()>0) {
						log.debug("Found root dataflow with " + df.getSource().size() + " sources");
						wd.setInputs(df.getSource());
						return wd;
					}
				}
			}
			return wd;
		} catch (Exception e) {
			throw new WorkflowException(e);
		}
		finally {
			get.releaseConnection();
		}
	}
	
	/**
	 * Retrieve workflows with a specific tag from MyExperiment.org
	 * @param tag MyExperiment.org workflow tag
	 * @return <code>List&lt;WorkflowDescription&gt; containing the workflow definitions
	 * @throws <code>WorkflowException</code>
	 */
	private Map<String, WorkflowDescription> getWorkflows(String tag) throws WorkflowException {
		GetMethod get = new GetMethod("http://"+this.getServer()+"/workflows.xml?tag="+tag);
		try {
			http.executeMethod(get);
			List<MyExperimentWorkflows.WorkflowStub> workflows = unmarshalWorkflows(get.getResponseBodyAsStream());
			log.debug("found " + workflows.size() + " workflow stubs for tag: " + tag);
			Map<String, WorkflowDescription> list = new HashMap<String, WorkflowDescription>(workflows.size());
			for(MyExperimentWorkflows.WorkflowStub stub : workflows) {
				GetMethod getStub = new GetMethod(stub.getUri()+"&elements=id,title,description,content-uri,uploader,preview,thumbnail,thumbnail-big");
				http.executeMethod(getStub);
				WorkflowDescription wd = unmarshalWorkflow(getStub.getResponseBodyAsStream()); 
				list.put(wd.getId(), wd);
			}
			log.debug("returning " + list.size() + " workflows");
			return list;
		} catch(HTTPException e) {
			throw new WorkflowException(e);
		} catch(IOException e) {
			throw new WorkflowException(e);
		} 
			finally { get.releaseConnection(); }
	}
	
	/**
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	Cookie getSessionCookie() throws HttpException, IOException {
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
		    return this.sessionCookie;
		} finally {
			authMethod.releaseConnection();
		}
	}
	
	/**
	 * Unmarshall a list of workflows from xml
	 * @param is <code>InputStream</code> of xml
	 * @return	<code>Workflow</code> representation of workflow list xml
	 * @throws JAXBException
	 */
	static List<WorkflowStub> unmarshalWorkflows(InputStream is) throws WorkflowException {
		try {
		return ((MyExperimentWorkflows)JAXBContext.newInstance(MyExperimentWorkflows.class, WorkflowStub.class).createUnmarshaller().unmarshal(is)).workflow;
		} catch(JAXBException e) {
			throw new WorkflowException("Error unmarshalling" , e);
		}
	}
	
	/**
	 * Unmarshall a single workflow from xml
	 * @param is	<code>InputStream</code> of xml
	 * @return	<code>WorkfowDescription</code> representation of workflow xml
	 * @throws JAXBException
	 */
	static WorkflowDescription unmarshalWorkflow(InputStream is) throws WorkflowException {
		try {
			return (WorkflowDescription)JAXBContext.newInstance(WorkflowDescription.class, WorkflowDescription.Components.class, WorkflowDescription.Dataflow.class, WorkflowDescription.Source.class).createUnmarshaller().unmarshal(is);
		} catch (JAXBException e) {
			throw new WorkflowException("Error marshalling" , e);
		}
	}
	
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getPassword() { return password; } 
	public void setPassword(String password) { this.password = password; }
	public String getServer() { return server; }
	public void setServer(String server) { this.server = server; }
	public String getTag() { return tag; }
	public void setTag(String tag) { this.tag = tag; }

	public void setPortletContext(PortletContext ctx) {
		this.portletContext = ctx;
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.applicationContext = ctx;
	}
}
