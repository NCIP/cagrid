package gov.nih.nci.cagrid.portal.portlet.sample;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import net.sf.taverna.t2.workflowmodel.Dataflow;
//import net.sf.taverna.t2.workflowmodel.DataflowInputPort;
import net.sf.taverna.t2.workflowmodel.EditException;
import net.sf.taverna.t2.workflowmodel.serialization.DeserializationException;
import net.sf.taverna.t2.workflowmodel.serialization.xml.XMLDeserializer;
import net.sf.taverna.t2.workflowmodel.serialization.xml.XMLDeserializerRegistry;

/**
 * Helper class that will be used in future to get the number of Input (and Output?) ports of a user uploaded workflow.
 * 
 * @author Dinanath Sulakhe sulakhe@mcs.anl.gov
 * Status: Currently it is not used in the portlet.
 */

public class GetInputPorts {
	
	private static XMLDeserializer deserializer = XMLDeserializerRegistry.getInstance().getDeserializer();

	
	public static void main(String[] args) throws Exception {
		//new ExecuteWorkflow().run(args);

	//	String[] args1 = {"/Users/sulakhe/Desktop/for-dina/cadsr.t2flow"};
		String args1 = "/Users/sulakhe/Desktop/caGrid+gRAVI/Taverna-2/fishsoup.t2flow";
		new GetInputPorts().getNumberOfPorts(args1);
	}

	protected Dataflow loadDataflow(File workflowFile) throws IOException,
	JDOMException, DeserializationException, EditException {
		InputStream inStream = new BufferedInputStream(new FileInputStream(
				workflowFile));
		if (inStream == null) {
			throw new IOException("Unable to find resource for t2 dataflow: "
					+ workflowFile);
		}
		SAXBuilder builder = new SAXBuilder();
		Element element = builder.build(inStream).detachRootElement();
		Dataflow dataflow = deserializer.deserializeDataflow(element);
		return dataflow;
	}
	
	
	public Integer getNumberOfPorts(String arg) throws Exception
	{
		File workflowFile = new File(arg);
		if (!workflowFile.isFile()) {
			System.err.println("Not a workflow file: " + workflowFile);
			return null;
			//System.exit(Exit.WORKFLOW_FILE.ordinal());
		}
		
		Dataflow dataflow = loadDataflow(workflowFile);
		List ports = dataflow.getInputPorts();
	
		System.out.println("Number of Input ports: " + ports.size());
		return ports.size();
		
	}

}
