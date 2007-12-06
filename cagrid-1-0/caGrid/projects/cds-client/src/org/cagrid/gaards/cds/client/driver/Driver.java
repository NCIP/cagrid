package org.cagrid.gaards.cds.client.driver;

import java.util.ArrayList;
import java.util.List;

import org.cagrid.gaards.cds.client.ClientConstants;
import org.cagrid.gaards.cds.client.DelegatedCredentialUserClient;
import org.cagrid.gaards.cds.client.DelegationUserClient;
import org.cagrid.gaards.cds.common.IdentityDelegationPolicy;
import org.cagrid.gaards.cds.common.ProxyLifetime;
import org.cagrid.gaards.cds.common.Utils;
import org.cagrid.gaards.cds.common.core.ProxyUtil;
import org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference;
import org.globus.gsi.GlobusCredential;

public class Driver {

		public static void main(String[] args) {
			try {
				// The Service URL of the Credential Delegation Service (CDS)
				String cdsURL = "https://localhost:8443/wsrf/services/cagrid/CredentialDelegationService";

				// The default credential or the user that is currently logged in.
				GlobusCredential credential = ProxyUtil.getDefaultProxy();

				// Specifies how long the delegation service can delegated this
				// credential to other parties.
				ProxyLifetime delegationLifetime = new ProxyLifetime();
				delegationLifetime.setHours(4);
				delegationLifetime.setMinutes(0);
				delegationLifetime.setSeconds(0);

				// Specifies the path length of the credential being delegate the
				// minumum is 1.
				int delegationPathLength = 1;

				// Specifies the how long credentials issued to allowed parties will
				// be valid for.
				ProxyLifetime issuedCredentialLifetime = new ProxyLifetime();
				issuedCredentialLifetime.setHours(1);
				issuedCredentialLifetime.setMinutes(0);
				issuedCredentialLifetime.setSeconds(0);

				// Specifies the path length of the credentials issued to allowed
				// parties. A path length of 0 means that the requesting party
				// cannot further delegate the credential.
				int issuedCredentialPathLength = 0;

				// Specifies the key length of the delegated credential
				int keySize = ClientConstants.DEFAULT_KEY_SIZE;

				// The policy stating which parties will be allowed to obtain a
				// delegated credential. The CDS will only issue credentials to
				// parties listed in this policy.
				List<String> parties = new ArrayList<String>();
				parties.add("/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=langella");
				IdentityDelegationPolicy policy = Utils
						.createIdentityDelegationPolicy(parties);

				// Create an instance of the delegation client, specifies the CDS
				// Serviced URL and the credential to be delegated.

				DelegationUserClient client = new DelegationUserClient(cdsURL,
						credential);

				// Delegates the credential and returns a reference which can later
				// be used by allowed parties to obtain a credential.

				DelegatedCredentialReference reference = client.delegateCredential(
						delegationLifetime, delegationPathLength, policy,
						issuedCredentialLifetime, issuedCredentialPathLength,
						keySize);
				

							
				//Create and Instance of the delegate credential client, specifying the 
				//DelegatedCredentialReference and the credential of the delegatee.  The 
				//DelegatedCredentialReference specifies which credential to obtain.  The 
				//delegatee's credential is required to authenticate with the CDS such 
				//that the CDS may determing if the the delegatee has been granted access 
				//to the credential in which they wish to obtain.
							
				DelegatedCredentialUserClient client2 = new DelegatedCredentialUserClient(reference,credential);
							
				//The get credential method obtains a signed delegated credential from the CDS.
							
				GlobusCredential delegatedCredential = client2.getDelegatedCredential();
							
							
				//Set the delegated credential as the default, the delegatee is now logged in as the delegator.
							
				ProxyUtil.saveProxyAsDefault(delegatedCredential);

				

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

}
