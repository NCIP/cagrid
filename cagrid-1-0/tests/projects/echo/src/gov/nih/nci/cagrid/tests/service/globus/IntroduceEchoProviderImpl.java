package gov.nih.nci.cagrid.tests.service.globus;

import gov.nih.nci.cagrid.tests.service.IntroduceEchoImpl;

import java.rmi.RemoteException;

/** 
 *  DO NOT EDIT:  This class is autogenerated!
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public class IntroduceEchoProviderImpl{
	
	IntroduceEchoImpl impl;
	
	public IntroduceEchoProviderImpl() throws RemoteException {
		impl = new IntroduceEchoImpl();
	}
	

	public gov.nih.nci.cagrid.tests.stubs.EchoResponse echo(gov.nih.nci.cagrid.tests.stubs.EchoRequest params) throws RemoteException {
		gov.nih.nci.cagrid.tests.stubs.EchoResponse boxedResult = new gov.nih.nci.cagrid.tests.stubs.EchoResponse();
		boxedResult.setResponse(impl.echo(params.getValue()));
		return boxedResult;
	}

}
