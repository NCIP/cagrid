package org.cagrid.demos.photoservicereg.client;

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

import org.cagrid.demos.photoservicereg.stubs.PhotoSharingRegistrationPortType;
import org.cagrid.demos.photoservicereg.stubs.service.PhotoSharingRegistrationServiceAddressingLocator;
import org.cagrid.demos.photoservicereg.common.PhotoSharingRegistrationI;
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
 * @created by Introduce Toolkit version 1.3
 */
public class PhotoSharingRegistrationClient extends PhotoSharingRegistrationClientBase implements PhotoSharingRegistrationI {	

	public PhotoSharingRegistrationClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public PhotoSharingRegistrationClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public PhotoSharingRegistrationClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public PhotoSharingRegistrationClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(PhotoSharingRegistrationClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  PhotoSharingRegistrationClient client = new PhotoSharingRegistrationClient(args[1]);
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

  public void registerPhotoSharingService(java.lang.String stemSystemExtension,java.lang.String stemDisplayExtension,java.lang.String serviceIdentity,java.lang.String userIdentity) throws RemoteException, org.cagrid.demos.photoservicereg.stubs.types.RegistrationException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"registerPhotoSharingService");
    org.cagrid.demos.photoservicereg.stubs.RegisterPhotoSharingServiceRequest params = new org.cagrid.demos.photoservicereg.stubs.RegisterPhotoSharingServiceRequest();
    params.setStemSystemExtension(stemSystemExtension);
    params.setStemDisplayExtension(stemDisplayExtension);
    params.setServiceIdentity(serviceIdentity);
    params.setUserIdentity(userIdentity);
    org.cagrid.demos.photoservicereg.stubs.RegisterPhotoSharingServiceResponse boxedResult = portType.registerPhotoSharingService(params);
    }
  }

  public void unregisterPhotoSharingService(java.lang.String stemSystemExtension) throws RemoteException, org.cagrid.demos.photoservicereg.stubs.types.RegistrationException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"unregisterPhotoSharingService");
    org.cagrid.demos.photoservicereg.stubs.UnregisterPhotoSharingServiceRequest params = new org.cagrid.demos.photoservicereg.stubs.UnregisterPhotoSharingServiceRequest();
    params.setStemSystemExtension(stemSystemExtension);
    org.cagrid.demos.photoservicereg.stubs.UnregisterPhotoSharingServiceResponse boxedResult = portType.unregisterPhotoSharingService(params);
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
