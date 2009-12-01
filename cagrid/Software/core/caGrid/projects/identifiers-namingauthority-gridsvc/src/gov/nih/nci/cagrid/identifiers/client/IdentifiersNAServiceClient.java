package gov.nih.nci.cagrid.identifiers.client;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import gov.nih.nci.cagrid.identifiers.stubs.IdentifiersNAServicePortType;
import gov.nih.nci.cagrid.identifiers.stubs.service.IdentifiersNAServiceAddressingLocator;
import gov.nih.nci.cagrid.identifiers.common.IdentifiersNAServiceI;
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
public class IdentifiersNAServiceClient extends IdentifiersNAServiceClientBase implements IdentifiersNAServiceI {	

	public IdentifiersNAServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public IdentifiersNAServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public IdentifiersNAServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public IdentifiersNAServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(IdentifiersNAServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
//			  IdentifiersNAServiceClient client = new IdentifiersNAServiceClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
				
//			  KeyValues[] tvs = new KeyValues[2];
//			  tvs[0] = new KeyValues();
//			  tvs[0].setKey("URL");
//			  Values values = new Values();
//			  values.setValue(new String[] { "http://www.yahoo1.com", "http://www.yahoo2.com" });
//			  tvs[0].setValues(values);
//
//			  tvs[1] = new KeyValues();
//			  tvs[1].setKey("XXX");
//			  values = new Values();
//			  values.setValue(new String[] { "text1", "text2" });
//			  tvs[1].setValues(values);
//
//			  KeyValuesMap tvm = new KeyValuesMap();
//			  tvm.setKeyValues(tvs);
//
//			  org.apache.axis.types.URI identifier = client.createIdentifier(tvm);
//			  System.out.println("Created [" + identifier.toString() + "]");
//				
//			  org.apache.axis.types.URI identifier = new org.apache.axis.types.URI(
//					  "http://purlz.cagrid.org:8080/localhost/d34c03a9-705b-4287-92f9-a021452e3716");
//			  System.out.println("Now retrieving values for " + identifier);
//			  namingauthority tvm2 = client.resolveIdentifier(identifier);
//			  for( KeyValues tv : tvm2.getKeyValues() ) {
//				  System.out.println("KEY: " + tv.getKey());
//				  for( String value : tv.getValues().getValue() ) {
//					  System.out.println("\tVALUE: " + value);
//				  }
//			  }
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

  public org.apache.axis.types.URI createIdentifier(namingauthority.IdentifierValues identifierValues) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createIdentifier");
    gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequest params = new gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequest();
    gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequestIdentifierValues identifierValuesContainer = new gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequestIdentifierValues();
    identifierValuesContainer.setIdentifierValues(identifierValues);
    params.setIdentifierValues(identifierValuesContainer);
    gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierResponse boxedResult = portType.createIdentifier(params);
    return boxedResult.getIdentifier();
    }
  }

  public namingauthority.IdentifierValues resolveIdentifier(org.apache.axis.types.URI identifier) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"resolveIdentifier");
    gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierRequest params = new gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierRequest();
    gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierRequestIdentifier identifierContainer = new gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierRequestIdentifier();
    identifierContainer.setIdentifier(identifier);
    params.setIdentifier(identifierContainer);
    gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierResponse boxedResult = portType.resolveIdentifier(params);
    return boxedResult.getIdentifierValues();
    }
  }

}
