package org.cagrid.metrics.service;

import java.net.InetAddress;
import java.rmi.RemoteException;

import org.apache.axis.MessageContext;
import org.globus.wsrf.security.SecurityManager;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public class MetricsImpl extends MetricsImplBase {

	public MetricsImpl() throws RemoteException {
		super();
	}

	private String getCallerIdentity() {
		return SecurityManager.getManager().getCaller();
	}

  public void report(org.cagrid.metrics.common.EventSubmission submission) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

}
