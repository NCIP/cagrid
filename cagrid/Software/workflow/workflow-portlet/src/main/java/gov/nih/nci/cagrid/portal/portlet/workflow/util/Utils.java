package gov.nih.nci.cagrid.portal.portlet.workflow.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Utils
 * TODO type-comment Utils
 * @author Marek Kedzierski (kedzie@ci.uchicago.edu)
 * @created Apr 2, 2011 - 2011
 */
public class Utils implements ApplicationContextAware {
	private static final Log log = LogFactory.getLog(Utils.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	private HttpClient http = new DefaultHttpClient();
	
	/**
	 * Download a file
	 * @param uri address of file to download
	 * @return file contents
	 * @throws IOException 
	 */
	public byte[] download(String uri) throws IOException {
		log.debug("Downloading File: " + uri);
		HttpGet get = new HttpGet(uri);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String body = http.execute(get, responseHandler);
		return body.getBytes();
	}
	
	/**
	 * Write binary data to filesystem; creating all necessary subfolders.
	 * @param filename the filename
	 * @param contents file contents
	 */
	public void saveFile(String filename, byte[] contents) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename);
			fos.write(contents);
			log.debug("Wrote" + contents.length + " bytes to: " + filename);
		} catch(FileNotFoundException e) {} finally { if(fos!=null) fos.close(); }
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
