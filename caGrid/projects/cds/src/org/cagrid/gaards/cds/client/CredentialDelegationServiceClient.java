package org.cagrid.gaards.cds.client;

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
import org.cagrid.gaards.cds.common.CredentialDelegationServiceI;
import org.cagrid.gaards.cds.stubs.CredentialDelegationServicePortType;
import org.cagrid.gaards.cds.stubs.service.CredentialDelegationServiceAddressingLocator;
import org.globus.gsi.GlobusCredential;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS
 * METHODS. This client is generated automatically by Introduce to provide a
 * clean unwrapped API to the service. On construction the class instance will
 * contact the remote service and retrieve it's security metadata description
 * which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.1
 */
public class CredentialDelegationServiceClient extends ServiceSecurityClient
		implements CredentialDelegationServiceI {
	protected CredentialDelegationServicePortType portType;
	private Object portTypeMutex;

	public CredentialDelegationServiceClient(String url)
			throws MalformedURIException, RemoteException {
		this(url, null);
	}

	public CredentialDelegationServiceClient(String url, GlobusCredential proxy)
			throws MalformedURIException, RemoteException {
		super(url, proxy);
		initialize();
	}

	public CredentialDelegationServiceClient(EndpointReferenceType epr)
			throws MalformedURIException, RemoteException {
		this(epr, null);
	}

	public CredentialDelegationServiceClient(EndpointReferenceType epr,
			GlobusCredential proxy) throws MalformedURIException,
			RemoteException {
		super(epr, proxy);
		initialize();
	}

	private void initialize() throws RemoteException {
		this.portTypeMutex = new Object();
		this.portType = createPortType();
	}

	private CredentialDelegationServicePortType createPortType()
			throws RemoteException {

		CredentialDelegationServiceAddressingLocator locator = new CredentialDelegationServiceAddressingLocator();
		// attempt to load our context sensitive wsdd file
		InputStream resourceAsStream = getClass().getResourceAsStream(
				"client-config.wsdd");
		if (resourceAsStream != null) {
			// we found it, so tell axis to configure an engine to use it
			EngineConfiguration engineConfig = new FileProvider(
					resourceAsStream);
			// set the engine of the locator
			locator.setEngine(new AxisClient(engineConfig));
		}
		CredentialDelegationServicePortType port = null;
		try {
			port = locator
					.getCredentialDelegationServicePortTypePort(getEndpointReference());
		} catch (Exception e) {
			throw new RemoteException("Unable to locate portType:"
					+ e.getMessage(), e);
		}

		return port;
	}

	public GetResourcePropertyResponse getResourceProperty(
			QName resourcePropertyQName) throws RemoteException {
		return portType.getResourceProperty(resourcePropertyQName);
	}

	public static void usage() {
		System.out.println(CredentialDelegationServiceClient.class.getName()
				+ " -url <service url>");
	}

  public org.cagrid.gaards.cds.common.DelegationSigningRequest initiateDelegation(org.cagrid.gaards.cds.common.DelegationRequest req) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.InvalidPolicyFault, org.cagrid.gaards.cds.stubs.types.DelegationFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"initiateDelegation");
    org.cagrid.gaards.cds.stubs.InitiateDelegationRequest params = new org.cagrid.gaards.cds.stubs.InitiateDelegationRequest();
    org.cagrid.gaards.cds.stubs.InitiateDelegationRequestReq reqContainer = new org.cagrid.gaards.cds.stubs.InitiateDelegationRequestReq();
    reqContainer.setDelegationRequest(req);
    params.setReq(reqContainer);
    org.cagrid.gaards.cds.stubs.InitiateDelegationResponse boxedResult = portType.initiateDelegation(params);
    return boxedResult.getDelegationSigningRequest();
    }
  }

  public org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference approveDelegation(org.cagrid.gaards.cds.common.DelegationSigningResponse delegationSigningResponse) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.DelegationFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"approveDelegation");
    org.cagrid.gaards.cds.stubs.ApproveDelegationRequest params = new org.cagrid.gaards.cds.stubs.ApproveDelegationRequest();
    org.cagrid.gaards.cds.stubs.ApproveDelegationRequestDelegationSigningResponse delegationSigningResponseContainer = new org.cagrid.gaards.cds.stubs.ApproveDelegationRequestDelegationSigningResponse();
    delegationSigningResponseContainer.setDelegationSigningResponse(delegationSigningResponse);
    params.setDelegationSigningResponse(delegationSigningResponseContainer);
    org.cagrid.gaards.cds.stubs.ApproveDelegationResponse boxedResult = portType.approveDelegation(params);
    return boxedResult.getDelegatedCredentialReference();
    }
  }

  public org.cagrid.gaards.cds.common.DelegationRecord[] findDelegatedCredentials(org.cagrid.gaards.cds.common.DelegationRecordFilter filter) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"findDelegatedCredentials");
    org.cagrid.gaards.cds.stubs.FindDelegatedCredentialsRequest params = new org.cagrid.gaards.cds.stubs.FindDelegatedCredentialsRequest();
    org.cagrid.gaards.cds.stubs.FindDelegatedCredentialsRequestFilter filterContainer = new org.cagrid.gaards.cds.stubs.FindDelegatedCredentialsRequestFilter();
    filterContainer.setDelegationRecordFilter(filter);
    params.setFilter(filterContainer);
    org.cagrid.gaards.cds.stubs.FindDelegatedCredentialsResponse boxedResult = portType.findDelegatedCredentials(params);
    return boxedResult.getDelegationRecord();
    }
  }

  public void updateDelegatedCredentialStatus(org.cagrid.gaards.cds.common.DelegationIdentifier id,org.cagrid.gaards.cds.common.DelegationStatus status) throws RemoteException, org.cagrid.gaards.cds.stubs.types.CDSInternalFault, org.cagrid.gaards.cds.stubs.types.DelegationFault, org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateDelegatedCredentialStatus");
    org.cagrid.gaards.cds.stubs.UpdateDelegatedCredentialStatusRequest params = new org.cagrid.gaards.cds.stubs.UpdateDelegatedCredentialStatusRequest();
    org.cagrid.gaards.cds.stubs.UpdateDelegatedCredentialStatusRequestId idContainer = new org.cagrid.gaards.cds.stubs.UpdateDelegatedCredentialStatusRequestId();
    idContainer.setDelegationIdentifier(id);
    params.setId(idContainer);
    org.cagrid.gaards.cds.stubs.UpdateDelegatedCredentialStatusRequestStatus statusContainer = new org.cagrid.gaards.cds.stubs.UpdateDelegatedCredentialStatusRequestStatus();
    statusContainer.setDelegationStatus(status);
    params.setStatus(statusContainer);
    org.cagrid.gaards.cds.stubs.UpdateDelegatedCredentialStatusResponse boxedResult = portType.updateDelegatedCredentialStatus(params);
    }
  }

}
