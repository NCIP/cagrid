package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.MyExperimentWorkflows;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Repository;
import org.springframework.web.portlet.context.PortletContextAware;

/**
 * Workflow registry implementation using MyExperiment.org REST API.
 * @author Marek Kedzierski
 */
@Repository
@Qualifier("MyExperiment")
public class MyExperimentWorkflowRegistry implements WorkflowRegistryService, DisposableBean {
	private static final Log log = LogFactory.getLog(MyExperimentWorkflowRegistry.class);
	
	/**  specify which elements to return from MyExperiment.org Rest API as a workflow description to show the user  */
	public static final String LAZY_ELEMENTS = "id,title,description,content-uri,uploader,preview,thumbnail,thumbnail-big,svg";
	/**  specify which elements to return from MyExperiment.org Rest API as a runnable workflow definition  */
	public static final String ALL_ELEMENTS = "id,title,description,content-uri,uploader,preview,thumbnail,thumbnail-big,svg,components";
	
	/** MyExperiment.org server. i.e. main or sandbox */
	private String server;
	/** MyExperiment.org workflow definition tag */
	private String tag;
	
	/** HTTPClient needs to be thread-safe */
	private ThreadSafeClientConnManager connMgr;
	/** Makes HTTP Requests to MyExperiment.org */
	private HttpClient httpClient;

	private Unmarshaller workflowsJaxbUnmarshaller;
	private Unmarshaller workflowDescriptionJaxbUnmarshaller;
	
	public MyExperimentWorkflowRegistry() {
		this.connMgr = new ThreadSafeClientConnManager();
		this.httpClient = new DefaultHttpClient(connMgr);
	}
	
	@Override
    public void destroy() throws Exception {
		if(this.connMgr!=null) this.connMgr.shutdown();
    }
	
	/* @see gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService#getWorkflows() */
	public Map<String, WorkflowDescription> getWorkflows() throws WorkflowException {
		ResponseHandler<String> rh = new BasicResponseHandler();
		HttpGet get = new HttpGet("http://"+this.getServer()+"/workflows.xml?tag="+getTag());
		String body = "";
		try {
			body = getHTTPClient().execute(get, rh);	
		} catch(Throwable e) {
			get.abort();
			throw new WorkflowException(e);
		} 
		
		log.debug("Unmarshalling workflow stubs");
		MyExperimentWorkflows workflow = unmarshalMyExperimentWorkflows(new ByteArrayInputStream(body.getBytes()));
		List<MyExperimentWorkflows.WorkflowStub> workflows = workflow.workflow;
		log.debug("found " + workflows.size() + " workflow stubs for tag: " + tag);
		Map<String, WorkflowDescription> list = new HashMap<String, WorkflowDescription>(workflows.size());
		
		for(MyExperimentWorkflows.WorkflowStub stub : workflows) {
			HttpGet getStub = new HttpGet(stub.getUri()+"&elements="+LAZY_ELEMENTS);
			try {
				body = getHTTPClient().execute(getStub, rh);
				WorkflowDescription wd = unmarshalWorkflowDescription(new ByteArrayInputStream(body.getBytes()));
				list.put(wd.getId(), wd);
			} catch(Throwable e) {
				getStub.abort();
				throw new WorkflowException(e);
			} 
		}
		log.debug("returning " + list.size() + " workflows");
		return list;
	}
	
	/* @see gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService#getWorkflow(java.lang.String) */
	public WorkflowDescription getWorkflow(String id) throws WorkflowException {
		log.debug("Fetching workflow #" + id);
		return downloadWorkflow( "http://"+this.getServer()+"/workflow.xml?id="+id+"&elements="+ALL_ELEMENTS );
	}
	
	private WorkflowDescription downloadWorkflow(String uri) throws WorkflowException {
		log.debug("Downloading workflow #" + uri);
		ResponseHandler<String> rh = new BasicResponseHandler();
		HttpGet get = new HttpGet(uri);
		try {
			String body = getHTTPClient().execute(get, rh);	
			WorkflowDescription wd = unmarshalWorkflowDescription( new ByteArrayInputStream(body.getBytes()) );
			log.debug("Workflow components: " + wd.getComponents());
			if (wd.getComponents() != null && wd.getComponents().getDataflow() != null) {
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
		} catch (Throwable e) {
			get.abort();
			throw new WorkflowException(e);
		}
	}
	
	private HttpClient getHTTPClient() {
		return this.httpClient;
	}
	
	WorkflowDescription unmarshalWorkflowDescription(InputStream is) throws WorkflowException {
		log.debug("Unmarshalling WOrkflow Description");
		try {
			return (WorkflowDescription)getWorkflowDescriptionJaxbUnmarshaller().unmarshal(new StreamSource(is));
		} catch(IOException e) { throw new WorkflowException(e); }
	}

	MyExperimentWorkflows unmarshalMyExperimentWorkflows(InputStream is ) throws WorkflowException {
		log.debug("Unmarshalling MyExperimentWOrkflows.");
		try {
			return (MyExperimentWorkflows)getWorkflowsJaxbUnmarshaller().unmarshal(new StreamSource(is));
		} catch(IOException e) { throw new WorkflowException(e); }
	}
	
	public String getServer() { return server; }
	public void setServer(String server) { this.server = server; }
	public String getTag() { return tag; }
	public void setTag(String tag) { this.tag = tag; }
	public Unmarshaller getWorkflowsJaxbUnmarshaller() { return workflowsJaxbUnmarshaller;}
	public void setWorkflowsJaxbUnmarshaller(Unmarshaller workflowsJaxbUnmarshaller) {this.workflowsJaxbUnmarshaller = workflowsJaxbUnmarshaller;}
	public Unmarshaller getWorkflowDescriptionJaxbUnmarshaller() {return workflowDescriptionJaxbUnmarshaller;}
	public void setWorkflowDescriptionJaxbUnmarshaller(Unmarshaller workflowDescriptionJaxbUnmarshaller) {this.workflowDescriptionJaxbUnmarshaller = workflowDescriptionJaxbUnmarshaller;}
}
