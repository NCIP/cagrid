package gov.nih.nci.cagrid.workflow.samples.secure.service.globus;

import gov.nih.nci.cagrid.workflow.samples.secure.service.SecureSampleImpl;

import java.rmi.RemoteException;

/** 
 *  DO NOT EDIT:  This class is autogenerated!
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public class SecureSampleProviderImpl{
	
	SecureSampleImpl impl;
	
	public SecureSampleProviderImpl() throws RemoteException {
		impl = new SecureSampleImpl();
	}
	









	public gov.nih.nci.cagrid.workflow.samples.secure.stubs.InvokeResponse invoke(gov.nih.nci.cagrid.workflow.samples.secure.stubs.InvokeRequest params) throws RemoteException {
		gov.nih.nci.cagrid.workflow.samples.secure.stubs.InvokeResponse boxedResult = new gov.nih.nci.cagrid.workflow.samples.secure.stubs.InvokeResponse();
		boxedResult.setResponse(impl.invoke(params.getString()));
		return boxedResult;
	}

}
