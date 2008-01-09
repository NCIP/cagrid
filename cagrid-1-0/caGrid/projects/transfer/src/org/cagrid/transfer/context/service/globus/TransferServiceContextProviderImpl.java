package org.cagrid.transfer.context.service.globus;

import org.cagrid.transfer.context.service.TransferServiceContextImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the TransferServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class TransferServiceContextProviderImpl{
	
	TransferServiceContextImpl impl;
	
	public TransferServiceContextProviderImpl() throws RemoteException {
		impl = new TransferServiceContextImpl();
	}
	

  public org.cagrid.transfer.AnyXmlType get(org.cagrid.transfer.EmptyType params) throws RemoteException {
    return impl.get(params);
  }

    public org.cagrid.transfer.context.stubs.GetDataTransferDescriptorResponse getDataTransferDescriptor(org.cagrid.transfer.context.stubs.GetDataTransferDescriptorRequest params) throws RemoteException {
    org.cagrid.transfer.context.stubs.GetDataTransferDescriptorResponse boxedResult = new org.cagrid.transfer.context.stubs.GetDataTransferDescriptorResponse();
    boxedResult.setDataTransferDescriptor(impl.getDataTransferDescriptor());
    return boxedResult;
  }

}
