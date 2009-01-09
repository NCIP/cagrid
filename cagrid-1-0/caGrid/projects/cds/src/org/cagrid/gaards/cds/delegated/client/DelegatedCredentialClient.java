package org.cagrid.gaards.cds.delegated.client;

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

import org.cagrid.gaards.cds.delegated.stubs.DelegatedCredentialPortType;
import org.cagrid.gaards.cds.delegated.stubs.service.DelegatedCredentialServiceAddressingLocator;
import org.cagrid.gaards.cds.delegated.common.DelegatedCredentialI;
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
public class DelegatedCredentialClient extends DelegatedCredentialClientBase implements DelegatedCredentialI {	

	public DelegatedCredentialClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public DelegatedCredentialClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public DelegatedCredentialClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public DelegatedCredentialClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(DelegatedCredentialClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  DelegatedCredentialClient client = new DelegatedCredentialClient(args[1]);
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

  public org.cagrid.gaards.cds.common.CertificateChain getDelegatedCredential(org.cagrid.gaards.cds.common.PublicKey publicKey) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.DelegationFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getDelegatedCredential");
    org.cagrid.gaards.cds.delegated.stubs.GetDelegatedCredentialRequest params = new org.cagrid.gaards.cds.delegated.stubs.GetDelegatedCredentialRequest();
    org.cagrid.gaards.cds.delegated.stubs.GetDelegatedCredentialRequestPublicKey publicKeyContainer = new org.cagrid.gaards.cds.delegated.stubs.GetDelegatedCredentialRequestPublicKey();
    publicKeyContainer.setPublicKey(publicKey);
    params.setPublicKey(publicKeyContainer);
    org.cagrid.gaards.cds.delegated.stubs.GetDelegatedCredentialResponse boxedResult = portType.getDelegatedCredential(params);
    return boxedResult.getCertificateChain();
    }
  }

  public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"destroy");
    return portType.destroy(params);
    }
  }

}
