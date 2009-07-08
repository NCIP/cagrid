package org.cagrid.demo.photosharing.utils;

import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.dorian.client.IFSUserClient;
import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

import java.rmi.RemoteException;

import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;

public class GridLogin {
	public static SAMLAssertion authenticate(String authenticationServiceURL, String username, String password) throws MalformedURIException, RemoteException {
		Credential credential = new Credential();
		BasicAuthenticationCredential bac = new BasicAuthenticationCredential();
		bac.setUserId(username);
		bac.setPassword(password);
		credential.setBasicAuthenticationCredential(bac);
		AuthenticationClient authClient = new AuthenticationClient(authenticationServiceURL, credential);
		SAMLAssertion saml = authClient.authenticate();
		return saml;
	}
	
	public static GlobusCredential login(String dorianURL, SAMLAssertion saml) throws MalformedURIException, RemoteException {
		// Create a IFS Client for authorization
		IFSUserClient ifsClient = new IFSUserClient(dorianURL);

		// Create a lifetime for the proxy, 12 hours in this case
		ProxyLifetime lifetime = new ProxyLifetime();
		lifetime.setHours(12);
		lifetime.setMinutes(0);
		lifetime.setSeconds(0);

		// specify delegation, use 0 for now. 0 indicates that the credential cannot be delegated
		int delegation = 0;
		delegation = Integer.valueOf(1);        
		// obtain your proxy and save it for use in invoking grid services
		GlobusCredential cred = ifsClient.createProxy(saml, lifetime, delegation);
		return cred;
	}

}
