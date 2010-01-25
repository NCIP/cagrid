package gov.nih.nci.cagrid.gts.service.globus;


import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.MessageContext;

import gov.nih.nci.cagrid.introduce.servicetools.security.AuthorizationExtension;
import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;
import org.globus.wsrf.impl.security.authorization.exceptions.CloseException;
import org.globus.wsrf.impl.security.authorization.exceptions.InitializeException;
import org.globus.wsrf.impl.security.authorization.exceptions.InvalidPolicyException;
import org.globus.wsrf.security.authorization.PDP;
import org.globus.wsrf.security.authorization.PDPConfig;
import org.globus.wsrf.config.ContainerConfig;
import org.w3c.dom.Node;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This is a PDP for use with the globus authorization callout.
 * This class will have a authorize method for each method on this grid service.
 * The method is responsible for making any authorization callouts required to satisfy the 
 * authorization requirements placed on each method call.  Each method will either return
 * upon a successful authorization or will throw an exception upon a failed authorization.
 * 
 * @created by Introduce Toolkit version 1.4
 * 
 */
public class GTSAuthorization implements PDP {

	public static final String SERVICE_NAMESPACE = "http://cagrid.nci.nih.gov/GTS";
	
	Map authorizationClassMap = new HashMap();
	
	
	public GTSAuthorization() {
	}
	
	protected String getServiceNamespace(){
		return SERVICE_NAMESPACE;
	}
	
	public static String getCallerIdentity() {
		String caller = org.globus.wsrf.security.SecurityManager.getManager().getCaller();
		if ((caller == null) || (caller.equals("<anonymous>"))) {
			return null;
		} else {
			return caller;
		}
	}
					
	public void authorizeAddTrustedAuthority(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeFindTrustedAuthorities(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeRemoveTrustedAuthority(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeAddPermission(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeFindPermissions(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeRevokePermission(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeUpdateTrustedAuthority(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeAddTrustLevel(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeUpdateTrustLevel(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetTrustLevels(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeRemoveTrustLevel(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeAddAuthority(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeUpdateAuthority(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeUpdateAuthorityPriorities(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetAuthorities(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeRemoveAuthority(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeUpdateCRL(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeValidate(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetServiceSecurityMetadata(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetMultipleResourceProperties(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeGetResourceProperty(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeQueryResourceProperties(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   
	
	public boolean isPermitted(Subject peerSubject, MessageContext context, QName operation)
		throws AuthorizationException {
		
		if(!operation.getNamespaceURI().equals(getServiceNamespace())){
		  return false;
		}
		if(operation.getLocalPart().equals("addTrustedAuthority")){
			authorizeAddTrustedAuthority(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("findTrustedAuthorities")){
			authorizeFindTrustedAuthorities(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("removeTrustedAuthority")){
			authorizeRemoveTrustedAuthority(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("addPermission")){
			authorizeAddPermission(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("findPermissions")){
			authorizeFindPermissions(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("revokePermission")){
			authorizeRevokePermission(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("updateTrustedAuthority")){
			authorizeUpdateTrustedAuthority(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("addTrustLevel")){
			authorizeAddTrustLevel(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("updateTrustLevel")){
			authorizeUpdateTrustLevel(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getTrustLevels")){
			authorizeGetTrustLevels(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("removeTrustLevel")){
			authorizeRemoveTrustLevel(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("addAuthority")){
			authorizeAddAuthority(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("updateAuthority")){
			authorizeUpdateAuthority(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("updateAuthorityPriorities")){
			authorizeUpdateAuthorityPriorities(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getAuthorities")){
			authorizeGetAuthorities(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("removeAuthority")){
			authorizeRemoveAuthority(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("updateCRL")){
			authorizeUpdateCRL(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("validate")){
			authorizeValidate(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getServiceSecurityMetadata")){
			authorizeGetServiceSecurityMetadata(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getMultipleResourceProperties")){
			authorizeGetMultipleResourceProperties(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getResourceProperty")){
			authorizeGetResourceProperty(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("queryResourceProperties")){
			authorizeQueryResourceProperties(peerSubject, context, operation);
			return true;
		} 		
		return false;
	}
	

	public Node getPolicy(Node query) throws InvalidPolicyException {
		return null;
	}


	public String[] getPolicyNames() {
		return null;
	}


	public Node setPolicy(Node policy) throws InvalidPolicyException {
		return null;
	}


	public void close() throws CloseException {


	}


	public void initialize(PDPConfig config, String name, String id) throws InitializeException {
    	try{
    		String serviceName = (String)config.getProperty(name, "serviceName");
    	    String etcPath = ContainerConfig.getBaseDirectory() + File.separator + (String)config.getProperty(name, "etcDirectoryPath");

    	
    	} catch (Exception e){
        	throw new InitializeException(e.getMessage(),e);
		}
	}
	
	
}
