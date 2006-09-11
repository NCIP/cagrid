package gov.nih.nci.cagrid.idp.service.globus;

import gov.nih.nci.cagrid.idp.service.IDPServiceImpl;

import java.rmi.RemoteException;

/** 
 *  DO NOT EDIT:  This class is autogenerated!
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public class IDPServiceProviderImpl{
	
	IDPServiceImpl impl;
	
	public IDPServiceProviderImpl() throws RemoteException {
		impl = new IDPServiceImpl();
	}
	

	public gov.nih.nci.cagrid.idp.stubs.LoginResponse login(gov.nih.nci.cagrid.idp.stubs.LoginRequest params) throws RemoteException, gov.nih.nci.cagrid.idp.stubs.IdpInternalException, gov.nih.nci.cagrid.idp.stubs.InValidCredentialException, gov.nih.nci.cagrid.idp.stubs.InsufficientAttributeException {
		gov.nih.nci.cagrid.idp.stubs.LoginResponse boxedResult = new gov.nih.nci.cagrid.idp.stubs.LoginResponse();
		boxedResult.setSAMLAssertion(impl.login(params.getCredential().getCredential()));
		return boxedResult;
	}

}
