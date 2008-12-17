package gov.nih.nci.cagrid.workflow.factory.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.xml.sax.InputSource;

import org.globus.gsi.GlobusCredential;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;

import workflowmanagementfactoryservice.StartInputType;
import workflowmanagementfactoryservice.WMSInputType;
import workflowmanagementfactoryservice.WMSOutputType;
import workflowmanagementfactoryservice.WSDLReferences;
import workflowmanagementfactoryservice.WorkflowOutputType;
import workflowmanagementfactoryservice.WorkflowStatusType;

import gov.nih.nci.cagrid.workflow.factory.stubs.TavernaWorkflowServicePortType;
import gov.nih.nci.cagrid.workflow.factory.stubs.service.TavernaWorkflowServiceAddressingLocator;
import gov.nih.nci.cagrid.workflow.factory.common.TavernaWorkflowServiceI;
import gov.nih.nci.cagrid.workflow.service.impl.client.TavernaWorkflowServiceImplClient;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS
 * METHODS.
 * 
 * This client is generated automatically by Introduce to provide a clean
 * unwrapped API to the service.
 * 
 * On construction the class instance will contact the remote service and
 * retrieve it's security metadata description which it will use to configure
 * the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class TavernaWorkflowServiceClient extends
		TavernaWorkflowServiceClientBase implements TavernaWorkflowServiceI {

	private EndpointReferenceType workflowEPR = null;

	public EndpointReferenceType getWorkflowEPR() {
		return this.workflowEPR;
	}

	public void setWorkflowEPR(EndpointReferenceType workflowEPR) {
		this.workflowEPR = workflowEPR;
	}

	public TavernaWorkflowServiceClient(String url)
			throws MalformedURIException, RemoteException {
		this(url, null);
	}

	public TavernaWorkflowServiceClient(String url, GlobusCredential proxy)
			throws MalformedURIException, RemoteException {
		super(url, proxy);
	}

	public TavernaWorkflowServiceClient(EndpointReferenceType epr)
			throws MalformedURIException, RemoteException {
		this(epr, null);
	}

	public TavernaWorkflowServiceClient(EndpointReferenceType epr,
			GlobusCredential proxy) throws MalformedURIException,
			RemoteException {
		super(epr, proxy);
	}

	public static void usage() {
		System.out.println(TavernaWorkflowServiceClient.class.getName()
				+ " -url <service url>");
	}

	public static void main(String[] args) {
		System.out.println("Running the Grid Service Client");
		try {
			if (!(args.length < 2)) {
				if (args[0].equals("-url")) {
					TavernaWorkflowServiceClient client = new TavernaWorkflowServiceClient(
							args[1]);
					// place client calls here if you want to use this main as a
					// test....
				} else {
					usage();
					System.exit(1);
				}
			} else {
				usage();
				System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static EndpointReferenceType setupWorkflow(String url,
			String scuflDoc, String workflowName) throws MalformedURIException,
			RemoteException, Exception {
		TavernaWorkflowServiceClient client = new TavernaWorkflowServiceClient(
				url);
		WMSInputType input = createInput(scuflDoc, workflowName, null);
		WMSOutputType wMSOutputElement = client.createWorkflow(input);
		client.setWorkflowEPR(wMSOutputElement.getWorkflowEPR());
		return wMSOutputElement.getWorkflowEPR();

	}

	public static WorkflowStatusType startWorkflow(String[] inputString,
			EndpointReferenceType epr) throws MalformedURIException,
			RemoteException, Exception {
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(
				epr);
		StartInputType startInputElement = null;
		if (!inputString.equals("")) {
			startInputElement = new StartInputType();
			startInputElement.setInputArgs(inputString);

		}
		// startInputElement.setInputArgs(inputString);
		WorkflowStatusType workflowStatusElement = serviceClient
				.start(startInputElement);

		return workflowStatusElement;

	}

	public static WorkflowStatusType getStatus(EndpointReferenceType epr)
			throws MalformedURIException, RemoteException {
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(
				epr);
		WorkflowStatusType workflowStatusElement = serviceClient.getStatus();
		return workflowStatusElement;

	}

	public static WorkflowOutputType getOutput(EndpointReferenceType epr)
			throws MalformedURIException, RemoteException {
		TavernaWorkflowServiceImplClient serviceClient = new TavernaWorkflowServiceImplClient(
				epr);
		WorkflowOutputType workflowOutputElement = serviceClient
				.getWorkflowOutput();
		return workflowOutputElement;

	}

	public static void writeEprToFile(EndpointReferenceType epr,
			String workflowName) throws Exception {
		FileWriter writer = null;
		String curDir = System.getProperty("user.dir");
		System.out.println(curDir + "/" + workflowName + ".epr");

		try {
			writer = new FileWriter(curDir + "/" + workflowName + ".epr");
			QName qName = new QName("", "TWS_EPR");

			writer.write(ObjectSerializer.toString(epr, qName));

		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public static EndpointReferenceType readEprFromFile(String fileName)
			throws Exception {
		FileInputStream in = null;
		EndpointReferenceType ref = new EndpointReferenceType();
		try {
			in = new FileInputStream(fileName);
			ref = (EndpointReferenceType) ObjectDeserializer.deserialize(
					new InputSource(in), EndpointReferenceType.class);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return ref;
	}

	public static WMSInputType createInput(String scuflFile, String name,
			WSDLReferences[] wsdlRefArray) throws Exception {
		WMSInputType input = new WMSInputType();
		String scuflProcess = Utils.fileToStringBuffer(new File(scuflFile))
				.toString();

		input.setScuflDoc(scuflProcess);
		input.setWorkflowName(name);
		if (wsdlRefArray == null) {
			wsdlRefArray = new WSDLReferences[1];
			wsdlRefArray[0] = new WSDLReferences();
			wsdlRefArray[0]
					.setServiceUrl(new URI(
							"http://localhost:8080/wsrf/services/cagrid/WorkflowTestService1"));
			wsdlRefArray[0]
					.setWsdlLocation("http://localhost:8080/wsrf/share/schema/WorkflowTestService1/WorkflowTestService1.wsdl");
			wsdlRefArray[0]
					.setWsdlNamespace(new URI(
							"http://sample1.tests.workflow.cagrid.nci.nih.gov/WorkflowTestService1"));
		}
		input.setWsdlReferences(wsdlRefArray);
		return input;

	}
  public workflowmanagementfactoryservice.WMSOutputType createWorkflow(workflowmanagementfactoryservice.WMSInputType wMSInputElement) throws RemoteException, gov.nih.nci.cagrid.workflow.factory.stubs.types.WorkflowException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createWorkflow");
    gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowRequest params = new gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowRequest();
    gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowRequestWMSInputElement wMSInputElementContainer = new gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowRequestWMSInputElement();
    wMSInputElementContainer.setWMSInputElement(wMSInputElement);
    params.setWMSInputElement(wMSInputElementContainer);
    gov.nih.nci.cagrid.workflow.factory.stubs.CreateWorkflowResponse boxedResult = portType.createWorkflow(params);
    return boxedResult.getWMSOutputElement();
    }
  }

  public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getMultipleResourceProperties");
    return portType.getMultipleResourceProperties(params);
    }
  }

  public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getResourceProperty");
    return portType.getResourceProperty(params);
    }
  }

  public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"queryResourceProperties");
    return portType.queryResourceProperties(params);
    }
  }

}
