package gov.nih.nci.cagrid.bdt.client;

import gov.nih.nci.cagrid.bdt.common.BulkDataHandlerI;
import gov.nih.nci.cagrid.bdt.stubs.BulkDataHandlerPortType;
import gov.nih.nci.cagrid.bdt.stubs.service.BulkDataHandlerServiceAddressingLocator;
import gov.nih.nci.cagrid.enumeration.stubs.response.EnumerationResponseContainer;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.axis.utils.ClassUtils;
import org.globus.gsi.GlobusCredential;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;


/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE METHODS. This
 * client is generated automatically by Introduce to provide a clean unwrapped
 * API to the service. On construction the class instance will contact the
 * remote service and retrieve it's security metadata description which it will
 * use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.1
 */
public class BulkDataHandlerClient extends ServiceSecurityClient implements BulkDataHandlerI {
	protected BulkDataHandlerPortType portType;
	private Object portTypeMutex;


	public BulkDataHandlerClient(String url) throws MalformedURIException, RemoteException {
		this(url, null);
	}


	public BulkDataHandlerClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
		super(url, proxy);
		initialize();
	}


	public BulkDataHandlerClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
		this(epr, null);
	}


	public BulkDataHandlerClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException,
		RemoteException {
		super(epr, proxy);
		initialize();
	}


	private void initialize() throws RemoteException {
		portTypeMutex = new Object();
		portType = createPortType();
	}


	private BulkDataHandlerPortType createPortType() throws RemoteException {

		BulkDataHandlerServiceAddressingLocator locator = new BulkDataHandlerServiceAddressingLocator();
		// attempt to load our context sensitive wsdd file
		InputStream resourceAsStream = ClassUtils.getResourceAsStream(getClass(), "client-config.wsdd");
		if (resourceAsStream != null) {
			// we found it, so tell axis to configure an engine to use it
			EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
			// set the engine of the locator
			locator.setEngine(new AxisClient(engineConfig));
		}
		BulkDataHandlerPortType port = null;
		try {
			port = locator.getBulkDataHandlerPortTypePort(getEndpointReference());
		} catch (Exception e) {
			throw new RemoteException("Unable to locate portType:" + e.getMessage(), e);
		}

		return port;
	}


	public GetResourcePropertyResponse getResourceProperty(QName resourcePropertyQName) throws RemoteException {
		return portType.getResourceProperty(resourcePropertyQName);
	}


	public static void usage() {
		System.out.println(BulkDataHandlerClient.class.getName() + " -url <service url>");
	}


	public static void main(String[] args) {
		System.out.println("Running the Grid Service Client");
		try {
			if (!(args.length < 2)) {
				if (args[0].equals("-url")) {
					BulkDataHandlerClient client = new BulkDataHandlerClient(args[1]);
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


	public EnumerationResponseContainer createEnumeration() throws RemoteException {
		synchronized (portTypeMutex) {
			configureStubSecurity((Stub) portType, "createEnumeration");
			gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationRequest params = new gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationRequest();
			gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationResponse boxedResult = portType.createEnumeration(params);
			return boxedResult.getEnumerationResponseContainer();
		}
	}


	public org.globus.transfer.AnyXmlType get(org.globus.transfer.EmptyType params) throws RemoteException {
		synchronized (portTypeMutex) {
			configureStubSecurity((Stub) portType, "get");
			return portType.get(params);
		}
	}


	public org.apache.axis.types.URI[] getGridFTPURLs() throws RemoteException {
		synchronized (portTypeMutex) {
			configureStubSecurity((Stub) portType, "getGridFTPURLs");
			gov.nih.nci.cagrid.bdt.stubs.GetGridFTPURLsRequest params = new gov.nih.nci.cagrid.bdt.stubs.GetGridFTPURLsRequest();
			gov.nih.nci.cagrid.bdt.stubs.GetGridFTPURLsResponse boxedResult = portType.getGridFTPURLs(params);
			return boxedResult.getResponse();
		}
	}


	public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy params)
		throws RemoteException {
		synchronized (portTypeMutex) {
			configureStubSecurity((Stub) portType, "destroy");
			return portType.destroy(params);
		}
	}


	public org.oasis.wsrf.lifetime.SetTerminationTimeResponse setTerminationTime(
		org.oasis.wsrf.lifetime.SetTerminationTime params) throws RemoteException {
		synchronized (portTypeMutex) {
			configureStubSecurity((Stub) portType, "setTerminationTime");
			return portType.setTerminationTime(params);
		}
	}

}
