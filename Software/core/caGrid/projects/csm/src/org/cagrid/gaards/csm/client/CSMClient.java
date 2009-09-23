package org.cagrid.gaards.csm.client;

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

import org.cagrid.gaards.csm.stubs.CSMPortType;
import org.cagrid.gaards.csm.stubs.service.CSMServiceAddressingLocator;
import org.cagrid.gaards.csm.common.CSMI;
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
public class CSMClient extends CSMClientBase implements CSMI {	

	public CSMClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public CSMClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public CSMClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public CSMClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(CSMClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
	
			  CSMClient client = new CSMClient("https://localhost:8443/wsrf/services/cagrid/CSM");
			 // System.out.println(client.getApplications());
		
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

  public org.cagrid.gaards.csm.bean.Application[] getApplications(org.cagrid.gaards.csm.bean.ApplicationSearchCriteria applicationSearchCriteria) throws RemoteException, org.cagrid.gaards.csm.stubs.types.CSMInternalFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getApplications");
    org.cagrid.gaards.csm.stubs.GetApplicationsRequest params = new org.cagrid.gaards.csm.stubs.GetApplicationsRequest();
    org.cagrid.gaards.csm.stubs.GetApplicationsRequestApplicationSearchCriteria applicationSearchCriteriaContainer = new org.cagrid.gaards.csm.stubs.GetApplicationsRequestApplicationSearchCriteria();
    applicationSearchCriteriaContainer.setApplicationSearchCriteria(applicationSearchCriteria);
    params.setApplicationSearchCriteria(applicationSearchCriteriaContainer);
    org.cagrid.gaards.csm.stubs.GetApplicationsResponse boxedResult = portType.getApplications(params);
    return boxedResult.getApplication();
    }
  }

  public org.cagrid.gaards.csm.bean.Application createApplication(org.cagrid.gaards.csm.bean.Application application) throws RemoteException, org.cagrid.gaards.csm.stubs.types.CSMInternalFault, org.cagrid.gaards.csm.stubs.types.AccessDeniedFault, org.cagrid.gaards.csm.stubs.types.CSMTransactionFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createApplication");
    org.cagrid.gaards.csm.stubs.CreateApplicationRequest params = new org.cagrid.gaards.csm.stubs.CreateApplicationRequest();
    org.cagrid.gaards.csm.stubs.CreateApplicationRequestApplication applicationContainer = new org.cagrid.gaards.csm.stubs.CreateApplicationRequestApplication();
    applicationContainer.setApplication(application);
    params.setApplication(applicationContainer);
    org.cagrid.gaards.csm.stubs.CreateApplicationResponse boxedResult = portType.createApplication(params);
    return boxedResult.getApplication();
    }
  }

  public void modifyApplication(org.cagrid.gaards.csm.bean.Application application) throws RemoteException, org.cagrid.gaards.csm.stubs.types.CSMInternalFault, org.cagrid.gaards.csm.stubs.types.AccessDeniedFault, org.cagrid.gaards.csm.stubs.types.CSMTransactionFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"modifyApplication");
    org.cagrid.gaards.csm.stubs.ModifyApplicationRequest params = new org.cagrid.gaards.csm.stubs.ModifyApplicationRequest();
    org.cagrid.gaards.csm.stubs.ModifyApplicationRequestApplication applicationContainer = new org.cagrid.gaards.csm.stubs.ModifyApplicationRequestApplication();
    applicationContainer.setApplication(application);
    params.setApplication(applicationContainer);
    org.cagrid.gaards.csm.stubs.ModifyApplicationResponse boxedResult = portType.modifyApplication(params);
    }
  }

  public void removeApplication(long applicationId) throws RemoteException, org.cagrid.gaards.csm.stubs.types.CSMInternalFault, org.cagrid.gaards.csm.stubs.types.AccessDeniedFault, org.cagrid.gaards.csm.stubs.types.CSMTransactionFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"removeApplication");
    org.cagrid.gaards.csm.stubs.RemoveApplicationRequest params = new org.cagrid.gaards.csm.stubs.RemoveApplicationRequest();
    params.setApplicationId(applicationId);
    org.cagrid.gaards.csm.stubs.RemoveApplicationResponse boxedResult = portType.removeApplication(params);
    }
  }

}
