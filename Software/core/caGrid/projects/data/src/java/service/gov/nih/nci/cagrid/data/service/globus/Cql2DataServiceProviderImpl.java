package gov.nih.nci.cagrid.data.service.globus;

import gov.nih.nci.cagrid.data.service.Cql2DataServiceImpl;
import gov.nih.nci.cagrid.data.service.DataServiceInitializationException;

import java.rmi.RemoteException;

/** 
 *  DO NOT EDIT:  This class is autogenerated!
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public class Cql2DataServiceProviderImpl{
	
	Cql2DataServiceImpl impl;
	
	public Cql2DataServiceProviderImpl() throws DataServiceInitializationException {
		impl = new Cql2DataServiceImpl();
	}
	

	public org.cagrid.dataservice.stubs.ExecuteQueryResponse query(org.cagrid.dataservice.stubs.ExecuteQueryRequest params)
            throws RemoteException, gov.nih.nci.cagrid.data.faults.QueryProcessingExceptionType, 
            gov.nih.nci.cagrid.data.faults.MalformedQueryExceptionType {
	    org.cagrid.dataservice.stubs.ExecuteQueryResponse boxedResult = 
            new org.cagrid.dataservice.stubs.ExecuteQueryResponse();
		boxedResult.setCQLQueryResults(impl.executeQuery(params.getQuery().getCQLQuery()));
		return boxedResult;
	}

}
