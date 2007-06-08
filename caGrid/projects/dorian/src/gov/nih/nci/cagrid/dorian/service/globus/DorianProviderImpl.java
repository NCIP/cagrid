package gov.nih.nci.cagrid.dorian.service.globus;

import gov.nih.nci.cagrid.dorian.service.DorianImpl;

import java.rmi.RemoteException;

/** 
 *  DO NOT EDIT:  This class is autogenerated!
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public class DorianProviderImpl{
	
	DorianImpl impl;
	
	public DorianProviderImpl() throws RemoteException {
		impl = new DorianImpl();
	}
	

	public gov.nih.nci.cagrid.dorian.stubs.RegisterWithIdPResponse registerWithIdP(gov.nih.nci.cagrid.dorian.stubs.RegisterWithIdPRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserPropertyFault {
		DorianAuthorization.authorizeRegisterWithIdP();
		gov.nih.nci.cagrid.dorian.stubs.RegisterWithIdPResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.RegisterWithIdPResponse();
		boxedResult.setResponse(impl.registerWithIdP(params.getApplication().getApplication()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.FindIdPUsersResponse findIdPUsers(gov.nih.nci.cagrid.dorian.stubs.FindIdPUsersRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeFindIdPUsers();
		gov.nih.nci.cagrid.dorian.stubs.FindIdPUsersResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.FindIdPUsersResponse();
		boxedResult.setIdPUser(impl.findIdPUsers(params.getFilter().getIdPUserFilter()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.UpdateIdPUserResponse updateIdPUser(gov.nih.nci.cagrid.dorian.stubs.UpdateIdPUserRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.NoSuchUserFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeUpdateIdPUser();
		gov.nih.nci.cagrid.dorian.stubs.UpdateIdPUserResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.UpdateIdPUserResponse();
		impl.updateIdPUser(params.getUser().getIdPUser());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.RemoveIdPUserResponse removeIdPUser(gov.nih.nci.cagrid.dorian.stubs.RemoveIdPUserRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeRemoveIdPUser();
		gov.nih.nci.cagrid.dorian.stubs.RemoveIdPUserResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.RemoveIdPUserResponse();
		impl.removeIdPUser(params.getUserId());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.AuthenticateWithIdPResponse authenticateWithIdP(gov.nih.nci.cagrid.dorian.stubs.AuthenticateWithIdPRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeAuthenticateWithIdP();
		gov.nih.nci.cagrid.dorian.stubs.AuthenticateWithIdPResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.AuthenticateWithIdPResponse();
		boxedResult.setSAMLAssertion(impl.authenticateWithIdP(params.getCred().getBasicAuthCredential()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.CreateProxyResponse createProxy(gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidAssertionFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidProxyFault, gov.nih.nci.cagrid.dorian.stubs.types.UserPolicyFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeCreateProxy();
		gov.nih.nci.cagrid.dorian.stubs.CreateProxyResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.CreateProxyResponse();
		boxedResult.setX509Certificate(impl.createProxy(params.getSaml().getSAMLAssertion(),params.getPublicKey().getPublicKey(),params.getLifetime().getProxyLifetime(),params.getDelegation().getDelegationPathLength()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.GetCACertificateResponse getCACertificate(gov.nih.nci.cagrid.dorian.stubs.GetCACertificateRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault {
		DorianAuthorization.authorizeGetCACertificate();
		gov.nih.nci.cagrid.dorian.stubs.GetCACertificateResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.GetCACertificateResponse();
		boxedResult.setX509Certificate(impl.getCACertificate());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.GetTrustedIdPsResponse getTrustedIdPs(gov.nih.nci.cagrid.dorian.stubs.GetTrustedIdPsRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeGetTrustedIdPs();
		gov.nih.nci.cagrid.dorian.stubs.GetTrustedIdPsResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.GetTrustedIdPsResponse();
		boxedResult.setTrustedIdP(impl.getTrustedIdPs());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.AddTrustedIdPResponse addTrustedIdP(gov.nih.nci.cagrid.dorian.stubs.AddTrustedIdPRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidTrustedIdPFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeAddTrustedIdP();
		gov.nih.nci.cagrid.dorian.stubs.AddTrustedIdPResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.AddTrustedIdPResponse();
		boxedResult.setTrustedIdP(impl.addTrustedIdP(params.getIdp().getTrustedIdP()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.UpdateTrustedIdPResponse updateTrustedIdP(gov.nih.nci.cagrid.dorian.stubs.UpdateTrustedIdPRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidTrustedIdPFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeUpdateTrustedIdP();
		gov.nih.nci.cagrid.dorian.stubs.UpdateTrustedIdPResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.UpdateTrustedIdPResponse();
		impl.updateTrustedIdP(params.getTrustedIdP().getTrustedIdP());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.RemoveTrustedIdPResponse removeTrustedIdP(gov.nih.nci.cagrid.dorian.stubs.RemoveTrustedIdPRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidTrustedIdPFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeRemoveTrustedIdP();
		gov.nih.nci.cagrid.dorian.stubs.RemoveTrustedIdPResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.RemoveTrustedIdPResponse();
		impl.removeTrustedIdP(params.getTrustedIdP().getTrustedIdP());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.FindIFSUsersResponse findIFSUsers(gov.nih.nci.cagrid.dorian.stubs.FindIFSUsersRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeFindIFSUsers();
		gov.nih.nci.cagrid.dorian.stubs.FindIFSUsersResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.FindIFSUsersResponse();
		boxedResult.setIFSUser(impl.findIFSUsers(params.getFilter().getIFSUserFilter()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.UpdateIFSUserResponse updateIFSUser(gov.nih.nci.cagrid.dorian.stubs.UpdateIFSUserRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeUpdateIFSUser();
		gov.nih.nci.cagrid.dorian.stubs.UpdateIFSUserResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.UpdateIFSUserResponse();
		impl.updateIFSUser(params.getUser().getIFSUser());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.RemoveIFSUserResponse removeIFSUser(gov.nih.nci.cagrid.dorian.stubs.RemoveIFSUserRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeRemoveIFSUser();
		gov.nih.nci.cagrid.dorian.stubs.RemoveIFSUserResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.RemoveIFSUserResponse();
		impl.removeIFSUser(params.getUser().getIFSUser());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.RenewIFSUserCredentialsResponse renewIFSUserCredentials(gov.nih.nci.cagrid.dorian.stubs.RenewIFSUserCredentialsRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeRenewIFSUserCredentials();
		gov.nih.nci.cagrid.dorian.stubs.RenewIFSUserCredentialsResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.RenewIFSUserCredentialsResponse();
		boxedResult.setIFSUser(impl.renewIFSUserCredentials(params.getUser().getIFSUser()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.GetIFSUserPoliciesResponse getIFSUserPolicies(gov.nih.nci.cagrid.dorian.stubs.GetIFSUserPoliciesRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeGetIFSUserPolicies();
		gov.nih.nci.cagrid.dorian.stubs.GetIFSUserPoliciesResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.GetIFSUserPoliciesResponse();
		boxedResult.setIFSUserPolicy(impl.getIFSUserPolicies());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.authentication.AuthenticateResponse authenticate(gov.nih.nci.cagrid.authentication.AuthenticateRequest params) throws RemoteException, gov.nih.nci.cagrid.authentication.stubs.types.InvalidCredentialFault, gov.nih.nci.cagrid.authentication.stubs.types.InsufficientAttributeFault, gov.nih.nci.cagrid.authentication.stubs.types.AuthenticationProviderFault {
		DorianAuthorization.authorizeAuthenticate();
		gov.nih.nci.cagrid.authentication.AuthenticateResponse boxedResult = new gov.nih.nci.cagrid.authentication.AuthenticateResponse();
		boxedResult.setSAMLAssertion(impl.authenticate(params.getCredential().getCredential()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.AddAdminResponse addAdmin(gov.nih.nci.cagrid.dorian.stubs.AddAdminRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeAddAdmin();
		gov.nih.nci.cagrid.dorian.stubs.AddAdminResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.AddAdminResponse();
		impl.addAdmin(params.getGridIdentity());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.RemoveAdminResponse removeAdmin(gov.nih.nci.cagrid.dorian.stubs.RemoveAdminRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeRemoveAdmin();
		gov.nih.nci.cagrid.dorian.stubs.RemoveAdminResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.RemoveAdminResponse();
		impl.removeAdmin(params.getGridIdentity());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.GetAdminsResponse getAdmins(gov.nih.nci.cagrid.dorian.stubs.GetAdminsRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeGetAdmins();
		gov.nih.nci.cagrid.dorian.stubs.GetAdminsResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.GetAdminsResponse();
		boxedResult.setResponse(impl.getAdmins());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.RequestHostCertificateResponse requestHostCertificate(gov.nih.nci.cagrid.dorian.stubs.RequestHostCertificateRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateRequestFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeRequestHostCertificate();
		gov.nih.nci.cagrid.dorian.stubs.RequestHostCertificateResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.RequestHostCertificateResponse();
		boxedResult.setHostCertificateRecord(impl.requestHostCertificate(params.getReq().getHostCertificateRequest()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.GetOwnedHostCertificatesResponse getOwnedHostCertificates(gov.nih.nci.cagrid.dorian.stubs.GetOwnedHostCertificatesRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeGetOwnedHostCertificates();
		gov.nih.nci.cagrid.dorian.stubs.GetOwnedHostCertificatesResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.GetOwnedHostCertificatesResponse();
		boxedResult.setHostCertificateRecord(impl.getOwnedHostCertificates());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.ApproveHostCertificateResponse approveHostCertificate(gov.nih.nci.cagrid.dorian.stubs.ApproveHostCertificateRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeApproveHostCertificate();
		gov.nih.nci.cagrid.dorian.stubs.ApproveHostCertificateResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.ApproveHostCertificateResponse();
		boxedResult.setHostCertificateRecord(impl.approveHostCertificate(params.getRecordId()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.FindHostCertificatesResponse findHostCertificates(gov.nih.nci.cagrid.dorian.stubs.FindHostCertificatesRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeFindHostCertificates();
		gov.nih.nci.cagrid.dorian.stubs.FindHostCertificatesResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.FindHostCertificatesResponse();
		boxedResult.setHostCertificateRecord(impl.findHostCertificates(params.getHostCertificateFilter().getHostCertificateFilter()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.UpdateHostCertificateRecordResponse updateHostCertificateRecord(gov.nih.nci.cagrid.dorian.stubs.UpdateHostCertificateRecordRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeUpdateHostCertificateRecord();
		gov.nih.nci.cagrid.dorian.stubs.UpdateHostCertificateRecordResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.UpdateHostCertificateRecordResponse();
		impl.updateHostCertificateRecord(params.getHostCertificateUpdate().getHostCertificateUpdate());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.dorian.stubs.RenewHostCertificateResponse renewHostCertificate(gov.nih.nci.cagrid.dorian.stubs.RenewHostCertificateRequest params) throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault, gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateFault, gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		DorianAuthorization.authorizeRenewHostCertificate();
		gov.nih.nci.cagrid.dorian.stubs.RenewHostCertificateResponse boxedResult = new gov.nih.nci.cagrid.dorian.stubs.RenewHostCertificateResponse();
		boxedResult.setHostCertificateRecord(impl.renewHostCertificate(params.getRecordId()));
		return boxedResult;
	}

}
