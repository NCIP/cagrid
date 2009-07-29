package gov.nih.nci.cagrid.identifiers.service.globus;

import gov.nih.nci.cagrid.identifiers.service.IdentifiersNAServiceImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the IdentifiersNAServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class IdentifiersNAServiceProviderImpl{
	
	IdentifiersNAServiceImpl impl;
	
	public IdentifiersNAServiceProviderImpl() throws RemoteException {
		impl = new IdentifiersNAServiceImpl();
	}
	

    public gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierResponse createIdentifier(gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequest params) throws RemoteException {
    gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierResponse boxedResult = new gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierResponse();
    boxedResult.setResponse(impl.createIdentifier(params.getTypeValues().getTypeValuesMap()));
    return boxedResult;
  }

    public gov.nih.nci.cagrid.identifiers.stubs.GetTypeValuesResponse getTypeValues(gov.nih.nci.cagrid.identifiers.stubs.GetTypeValuesRequest params) throws RemoteException {
    gov.nih.nci.cagrid.identifiers.stubs.GetTypeValuesResponse boxedResult = new gov.nih.nci.cagrid.identifiers.stubs.GetTypeValuesResponse();
    boxedResult.setTypeValuesMap(impl.getTypeValues(params.getIdentifier()));
    return boxedResult;
  }

}
