package gov.nih.nci.cagrid.data.bdt.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public interface BDTDataServiceI {

  /**
   * The standard caGrid Data Service query method which returns results handled by Bulk Data Transfer.
   *
   * @param cqlQuery
   *	The CQL query to be executed against the data source.
   */
  public gov.nih.nci.cagrid.bdt.client.BulkDataHandlerClient bdtQuery(gov.nih.nci.cagrid.cqlquery.CQLQuery cqlQuery) throws RemoteException, org.apache.axis.types.URI.MalformedURIException ;

}

