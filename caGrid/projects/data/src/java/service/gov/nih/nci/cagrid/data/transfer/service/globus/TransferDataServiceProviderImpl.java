package gov.nih.nci.cagrid.data.transfer.service.globus;

import gov.nih.nci.cagrid.data.service.DataServiceInitializationException;
import gov.nih.nci.cagrid.data.transfer.service.TransferDataServiceImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the EnumerationDataServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public class TransferDataServiceProviderImpl{
	
	TransferDataServiceImpl impl;
	
	public TransferDataServiceProviderImpl() throws DataServiceInitializationException {
		impl = new TransferDataServiceImpl();
	}
	

	public gov.nih.nci.cagrid.data.transfer.stubs.TransferQueryResponse transferQuery(
        gov.nih.nci.cagrid.data.transfer.stubs.TransferQueryRequest params) 
            throws RemoteException, gov.nih.nci.cagrid.data.faults.MalformedQueryExceptionType, 
            gov.nih.nci.cagrid.data.faults.QueryProcessingExceptionType {
		TransferDataServiceAuthorization.authorizeTransferQuery();
		gov.nih.nci.cagrid.data.transfer.stubs.TransferQueryResponse boxedResult = 
            new gov.nih.nci.cagrid.data.transfer.stubs.TransferQueryResponse();
		boxedResult.setTransferServiceContextReference(impl.transferQuery(params.getCqlQuery().getCQLQuery()));
		return boxedResult;
	}
}
