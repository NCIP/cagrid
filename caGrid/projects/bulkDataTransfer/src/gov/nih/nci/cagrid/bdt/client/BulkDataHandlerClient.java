package gov.nih.nci.cagrid.bdt.client;

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

import gov.nih.nci.cagrid.bdt.stubs.BulkDataHandlerPortType;
import gov.nih.nci.cagrid.bdt.stubs.service.BulkDataHandlerServiceAddressingLocator;
import gov.nih.nci.cagrid.bdt.common.BulkDataHandlerI;
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
public class BulkDataHandlerClient extends ServiceSecurityClient implements BulkDataHandlerI {	
	protected BulkDataHandlerPortType portType;
	private Object portTypeMutex;

	public BulkDataHandlerClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public BulkDataHandlerClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	   	initialize();
	}
	
	public BulkDataHandlerClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public BulkDataHandlerClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
		initialize();
	}
	
	private void initialize() throws RemoteException {
	    this.portTypeMutex = new Object();
		this.portType = createPortType();
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

	public static void usage(){
		System.out.println(BulkDataHandlerClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
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

    public org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse pullOp(org.xmlsoap.schemas.ws._2004._09.enumeration.Pull pull) throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"pullOp");
        return portType.pullOp(pull);
      }
    }
    public org.xmlsoap.schemas.ws._2004._09.enumeration.RenewResponse renewOp(org.xmlsoap.schemas.ws._2004._09.enumeration.Renew renew) throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"renewOp");
        return portType.renewOp(renew);
      }
    }
    public org.xmlsoap.schemas.ws._2004._09.enumeration.GetStatusResponse getStatusOp(org.xmlsoap.schemas.ws._2004._09.enumeration.GetStatus status) throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"getStatusOp");
        return portType.getStatusOp(status);
      }
    }
    public void releaseOp(org.xmlsoap.schemas.ws._2004._09.enumeration.Release release) throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"releaseOp");
        portType.releaseOp(release);
      }
    }
    public org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse createEnumeration() throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"createEnumeration");
        gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationRequest params = new gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationRequest();
        gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationResponse boxedResult = portType.createEnumeration(params);
        return boxedResult.getEnumerateResponse();
      }
    }
    public org.globus.transfer.AnyXmlType get(org.globus.transfer.EmptyType empty) throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"get");
        return portType.get(empty);
      }
    }
    public org.apache.axis.types.URI[] getGridFTPURLs() throws RemoteException {
      synchronized(portTypeMutex){
        configureStubSecurity((Stub)portType,"getGridFTPURLs");
        gov.nih.nci.cagrid.bdt.stubs.GetGridFTPURLsRequest params = new gov.nih.nci.cagrid.bdt.stubs.GetGridFTPURLsRequest();
        gov.nih.nci.cagrid.bdt.stubs.GetGridFTPURLsResponse boxedResult = portType.getGridFTPURLs(params);
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
