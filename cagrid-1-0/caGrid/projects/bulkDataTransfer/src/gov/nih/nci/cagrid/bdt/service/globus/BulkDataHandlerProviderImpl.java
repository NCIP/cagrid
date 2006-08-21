package gov.nih.nci.cagrid.bdt.service.globus;

import gov.nih.nci.cagrid.bdt.service.BulkDataHandlerImpl;

import java.rmi.RemoteException;

/** 
 *  DO NOT EDIT:  This class is autogenerated!
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public class BulkDataHandlerProviderImpl{
	
	BulkDataHandlerImpl impl;
	
	public BulkDataHandlerProviderImpl() throws RemoteException {
		impl = new BulkDataHandlerImpl();
	}
	

	public transfer.AnyXmlType get(transfer.EmptyType params) throws RemoteException {
		return impl.get(params);
	}

	public gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationResponse createEnumeration(gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationRequest params) throws RemoteException {
		gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationResponse boxedResult = new gov.nih.nci.cagrid.bdt.stubs.CreateEnumerationResponse();
		boxedResult.setEnumerateResponse(impl.createEnumeration());
		return boxedResult;
	}

}
