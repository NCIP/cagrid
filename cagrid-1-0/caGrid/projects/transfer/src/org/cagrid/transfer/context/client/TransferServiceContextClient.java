package org.cagrid.transfer.context.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import org.cagrid.transfer.context.stubs.TransferServiceContextPortType;
import org.cagrid.transfer.context.stubs.service.TransferServiceContextServiceAddressingLocator;
import org.cagrid.transfer.context.common.TransferServiceContextI;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.2
 */
public class TransferServiceContextClient extends TransferServiceContextClientBase implements TransferServiceContextI {	

	public TransferServiceContextClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public TransferServiceContextClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public TransferServiceContextClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public TransferServiceContextClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(TransferServiceContextClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  TransferServiceContextClient client = new TransferServiceContextClient(args[1]);
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

  public void setStatus(org.cagrid.transfer.descriptor.Status status) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"setStatus");
    org.cagrid.transfer.context.stubs.SetStatusRequest params = new org.cagrid.transfer.context.stubs.SetStatusRequest();
    org.cagrid.transfer.context.stubs.SetStatusRequestStatus statusContainer = new org.cagrid.transfer.context.stubs.SetStatusRequestStatus();
    statusContainer.setStatus(status);
    params.setStatus(statusContainer);
    org.cagrid.transfer.context.stubs.SetStatusResponse boxedResult = portType.setStatus(params);
    }
  }

  public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"destroy");
    return portType.destroy(params);
    }
  }

  public org.oasis.wsrf.lifetime.SetTerminationTimeResponse setTerminationTime(org.oasis.wsrf.lifetime.SetTerminationTime params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"setTerminationTime");
    return portType.setTerminationTime(params);
    }
  }

  public org.cagrid.transfer.AnyXmlType get(org.cagrid.transfer.EmptyType params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    return portType.get(params);
    }
  }

  public org.cagrid.transfer.descriptor.DataTransferDescriptor getDataTransferDescriptor() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getDataTransferDescriptor");
    org.cagrid.transfer.context.stubs.GetDataTransferDescriptorRequest params = new org.cagrid.transfer.context.stubs.GetDataTransferDescriptorRequest();
    org.cagrid.transfer.context.stubs.GetDataTransferDescriptorResponse boxedResult = portType.getDataTransferDescriptor(params);
    return boxedResult.getDataTransferDescriptor();
    }
  }

  public void put(java.lang.Object anyType) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"put");
    org.cagrid.transfer.context.stubs.PutRequest params = new org.cagrid.transfer.context.stubs.PutRequest();
    params.setAnyType(anyType);
    org.cagrid.transfer.context.stubs.PutResponse boxedResult = portType.put(params);
    }
  }

  public org.oasis.wsn.SubscribeResponse subscribe(org.oasis.wsn.Subscribe params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"subscribe");
    return portType.subscribe(params);
    }
  }

  public org.cagrid.transfer.descriptor.Status getStatus() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getStatus");
    org.cagrid.transfer.context.stubs.GetStatusRequest params = new org.cagrid.transfer.context.stubs.GetStatusRequest();
    org.cagrid.transfer.context.stubs.GetStatusResponse boxedResult = portType.getStatus(params);
    return boxedResult.getStatus();
    }
  }

}
