package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.MyExperimentWorkflows;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Repository;
import org.springframework.web.portlet.context.PortletContextAware;

import com.googlecode.ehcache.annotations.Cacheable;

/**
 * Workflow registry implementation using MyExperiment.org REST API.
 * @author Marek Kedzierski
 */
@Repository
@Qualifier("MyExperiment")
public class MyExperimentWorkflowRegistry implements WorkflowRegistryService, ApplicationContextAware, PortletContextAware {
	private static final Log log = LogFactory.getLog(MyExperimentWorkflowRegistry.class);
	/**  specify which elements to return from MyExperiment.org Rest API  */
	public static final String LAZY_ELEMENTS = "id,title,description,content-uri,uploader,preview,thumbnail,thumbnail-big,svg";
	public static final String ALL_ELEMENTS = "id,title,description,content-uri,uploader,preview,thumbnail,thumbnail-big,svg,components";
	
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private PortletContext portletContext;
	
	private HttpClient http = new HttpClient();	
	private Cookie sessionCookie;
	
	private Unmarshaller workflowsJaxbUnmarshaller;
	private Unmarshaller workflowDescriptionJaxbUnmarshaller;
	
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
	@SuppressWarnings("unchecked")
	@Cacheable(cacheName="workflowListCache")
	public Map<String, WorkflowDescription> getWorkflows() throws WorkflowException {
		GetMethod get = new GetMethod("http://"+this.getServer()+"/workflows.xml?tag="+getTag());
		try {
			http.executeMethod(get);
			MyExperimentWorkflows workflow = unmarshalMyExperimentWorkflows(get.getResponseBodyAsStream());
			List<MyExperimentWorkflows.WorkflowStub> workflows = workflow.workflow;
			log.debug("found " + workflows.size() + " workflow stubs for tag: " + tag);
			Map<String, WorkflowDescription> list = new HashMap<String, WorkflowDescription>(workflows.size());
			for(MyExperimentWorkflows.WorkflowStub stub : workflows) {
//				WorkflowDescription wd = downloadWorkflow(stub.getUri()+"&elements="+MyExperimentWorkflowRegistry.ELEMENTS);
//				list.put(wd.getId(), wd);
				GetMethod getStub = new GetMethod(stub.getUri()+"&elements=id,title,description,content-uri,uploader,preview,thumbnail,thumbnail-big,svg");
				http.executeMethod(getStub);
				WorkflowDescription wd = (WorkflowDescription)getWorkflowDescriptionJaxbUnmarshaller().unmarshal(new StreamSource(getStub.getResponseBodyAsStream())); 
				list.put(wd.getId(), wd);
			}
			log.debug("returning " + list.size() + " workflows");
			return list;
		} catch(Exception e) {
			throw new WorkflowException(e);
		} 
			finally { get.releaseConnection(); }
	}
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.portal.portlets.workflow.WorkflowRegistryService#getWorkflow(java.lang.String)
	 */
	@Cacheable(cacheName="workflowCache")
	public WorkflowDescription getWorkflow(String id) throws WorkflowException {
		log.debug("Fetching workflow #" + id);
		return downloadWorkflow( "http://"+this.getServer()+"/workflow.xml?id="+id+"&elements="+MyExperimentWorkflowRegistry.ALL_ELEMENTS );
	}
	
	@SuppressWarnings("unchecked")
	private WorkflowDescription downloadWorkflow(String uri) throws WorkflowException {
		log.debug("Downloading workflow #" + uri);
		GetMethod get = new GetMethod(uri);
		try {
			http.executeMethod(get);	
			WorkflowDescription wd = unmarshalWorkflowDescription(get.getResponseBodyAsStream());
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
		}finally {get.releaseConnection();}
	}
	
	WorkflowDescription unmarshalWorkflowDescription(InputStream is) throws IOException {
		log.debug("Unmarshalling WOrkflow Description");
		 return (WorkflowDescription)getWorkflowDescriptionJaxbUnmarshaller().unmarshal(new StreamSource(is));
	}
	
	MyExperimentWorkflows unmarshalMyExperimentWorkflows(InputStream is ) throws IOException {
		log.debug("Unmarshalling MyExperimentWOrkflows.");
		return (MyExperimentWorkflows)getWorkflowsJaxbUnmarshaller().unmarshal(new StreamSource(is));
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
	
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getPassword() { return password; } 
	public void setPassword(String password) { this.password = password; }
	public String getServer() { return server; }
	public void setServer(String server) { this.server = server; }
	public String getTag() { return tag; }
	public void setTag(String tag) { this.tag = tag; }
	public void setPortletContext(PortletContext ctx) {this.portletContext = ctx;}
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {this.applicationContext = ctx;}
	public Unmarshaller getWorkflowsJaxbUnmarshaller() { return workflowsJaxbUnmarshaller;}
	public void setWorkflowsJaxbUnmarshaller(Unmarshaller workflowsJaxbUnmarshaller) {this.workflowsJaxbUnmarshaller = workflowsJaxbUnmarshaller;}
	public Unmarshaller getWorkflowDescriptionJaxbUnmarshaller() {return workflowDescriptionJaxbUnmarshaller;}
	public void setWorkflowDescriptionJaxbUnmarshaller(Unmarshaller workflowDescriptionJaxbUnmarshaller) {this.workflowDescriptionJaxbUnmarshaller = workflowDescriptionJaxbUnmarshaller;}
}
