package gov.nih.nci.cagrid.tests.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.axis.utils.ClassUtils;

import org.globus.gsi.GlobusCredential;

import gov.nih.nci.cagrid.tests.stubs.IntroduceEchoPortType;
import gov.nih.nci.cagrid.tests.stubs.service.IntroduceEchoServiceAddressingLocator;
import gov.nih.nci.cagrid.tests.common.IntroduceEchoI;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT.
 *
 * This class is not thread safe.  A new instance should be created for any threads using this class.
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.0
 */
public class IntroduceEchoClient extends ServiceSecurityClient implements IntroduceEchoI {	
	protected IntroduceEchoPortType portType;
	private Object portTypeMutex;

	public IntroduceEchoClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public IntroduceEchoClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	   	initialize();
	}
	
	public IntroduceEchoClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public IntroduceEchoClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
		initialize();
	}
	
	private void initialize() throws RemoteException {
	    this.portTypeMutex = new Object();
		this.portType = createPortType();
	}

	private IntroduceEchoPortType createPortType() throws RemoteException {

		IntroduceEchoServiceAddressingLocator locator = new IntroduceEchoServiceAddressingLocator();
		// attempt to load our context sensitive wsdd file
		InputStream resourceAsStream = ClassUtils.getResourceAsStream(getClass(), "client-config.wsdd");
		if (resourceAsStream != null) {
			// we found it, so tell axis to configure an engine to use it
			EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
			// set the engine of the locator
			locator.setEngine(new AxisClient(engineConfig));
		}
		IntroduceEchoPortType port = null;
		try {
			port = locator.getIntroduceEchoPortTypePort(getEndpointReference());
		} catch (Exception e) {
			throw new RemoteException("Unable to locate portType:" + e.getMessage(), e);
		}

		return port;
	}

	public static void usage(){
		System.out.println(IntroduceEchoClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  IntroduceEchoClient client = new IntroduceEchoClient(args[1]);
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

    public java.lang.String echo(java.lang.String value) throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"echo");
        gov.nih.nci.cagrid.tests.stubs.EchoRequest params = new gov.nih.nci.cagrid.tests.stubs.EchoRequest();
        params.setValue(value);
        gov.nih.nci.cagrid.tests.stubs.EchoResponse boxedResult = portType.echo(params);
        return boxedResult.getResponse();
      }
    }
    public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"getServiceSecurityMetadata");
        gov.nih.nci.cagrid.introduce.security.GetServiceSecurityMetadataRequest params = new gov.nih.nci.cagrid.introduce.security.GetServiceSecurityMetadataRequest();
        gov.nih.nci.cagrid.introduce.security.GetServiceSecurityMetadataResponse boxedResult = portType.getServiceSecurityMetadata(params);
        return boxedResult.getServiceSecurityMetadata();
      }
    }

}
