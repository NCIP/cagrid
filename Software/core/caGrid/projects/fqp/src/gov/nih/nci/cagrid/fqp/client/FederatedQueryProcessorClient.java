package gov.nih.nci.cagrid.fqp.client;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.cagrid.dcql.DCQLQuery;
import gov.nih.nci.cagrid.dcqlresult.DCQLQueryResultsCollection;
import gov.nih.nci.cagrid.dcqlresult.DCQLResult;
import gov.nih.nci.cagrid.fqp.common.FederatedQueryProcessorI;
import gov.nih.nci.cagrid.fqp.results.client.FederatedQueryResultsClient;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;
import org.oasis.wsrf.lifetime.Destroy;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS
 * METHODS. This client is generated automatically by Introduce to provide a
 * clean unwrapped API to the service. On construction the class instance will
 * contact the remote service and retrieve it's security metadata description
 * which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class FederatedQueryProcessorClient extends FederatedQueryProcessorClientBase
    implements
        FederatedQueryProcessorI {

    public FederatedQueryProcessorClient(String url) throws MalformedURIException, RemoteException {
        this(url, null);
    }

    public FederatedQueryProcessorClient(String url, GlobusCredential proxy) throws MalformedURIException,
        RemoteException {
        super(url, proxy);
    }

    public FederatedQueryProcessorClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr, null);
    }

    public FederatedQueryProcessorClient(EndpointReferenceType epr, GlobusCredential proxy)
        throws MalformedURIException, RemoteException {
        super(epr, proxy);
    }

    public static void usage() {
        System.out.println(FederatedQueryProcessorClient.class.getName() + " -url <service url> -dcql <DCQL file>");
    }

    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 4)) {
                if (args[0].equals("-url")) {
                    FederatedQueryProcessorClient client = new FederatedQueryProcessorClient(args[1]);
                    // place client calls here if you want to use this main as a
                    // test....

                    if (!args[2].equals("-dcql")) {
                        usage();
                        System.exit(1);
                    }

                    DCQLQuery dcql = Utils.deserializeDocument(args[3], DCQLQuery.class);
                    FederatedQueryResultsClient resultsClilent = client.executeAsynchronously(dcql);

                    Utils.serializeDocument("resultEPR.xml", resultsClilent.getEndpointReference(), new QName(
                        "http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndPointReference"));

                    // HACK: need to subscribe to the status Resource Property
                    while (!resultsClilent.isProcessingComplete()) {
                        Thread.sleep(500);
                        System.out.print(".");
                    }

                    DCQLQueryResultsCollection dcqlResultsCol = resultsClilent.getResults();
                    DCQLResult[] dcqlResults = dcqlResultsCol.getDCQLResult();
                    if (dcqlResults != null) {
                        for (DCQLResult result : dcqlResults) {
                            String targetServiceURL = result.getTargetServiceURL();
                            System.out.println("Got results from:" + targetServiceURL);
                            CQLQueryResults queryResultCollection = result.getCQLQueryResultCollection();
                            CQLQueryResultsIterator iterator = new CQLQueryResultsIterator(queryResultCollection, true);
                            int resultCount = 0;
                            while (iterator.hasNext()) {
                                System.out.println("===== RESULT [" + resultCount++ + "] =====");
                                System.out.println(iterator.next());
                                System.out.println("===== END RESULT=====\n\n");
                            }

                        }
                    } else {
                        System.out.println("Got no results.");
                    }

                    resultsClilent.destroy(new Destroy());

                } else {
                    usage();
                    System.exit(1);
                }
            } else {
                usage();
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

  public gov.nih.nci.cagrid.fqp.resultsretrieval.client.FederatedQueryResultsRetrievalClient queryAsynchronously(org.cagrid.data.dcql.DCQLQuery query,org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference delegatedCredentialReference,org.cagrid.fqp.execution.QueryExecutionParameters queryExecutionParameters) throws RemoteException, org.apache.axis.types.URI.MalformedURIException, gov.nih.nci.cagrid.fqp.results.stubs.types.InternalErrorFault, gov.nih.nci.cagrid.fqp.stubs.types.FederatedQueryProcessingFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"queryAsynchronously");
    gov.nih.nci.cagrid.fqp.stubs.QueryAsynchronouslyRequest params = new gov.nih.nci.cagrid.fqp.stubs.QueryAsynchronouslyRequest();
    gov.nih.nci.cagrid.fqp.stubs.QueryAsynchronouslyRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.QueryAsynchronouslyRequestQuery();
    queryContainer.setDCQLQuery(query);
    params.setQuery(queryContainer);
    gov.nih.nci.cagrid.fqp.stubs.QueryAsynchronouslyRequestDelegatedCredentialReference delegatedCredentialReferenceContainer = new gov.nih.nci.cagrid.fqp.stubs.QueryAsynchronouslyRequestDelegatedCredentialReference();
    delegatedCredentialReferenceContainer.setDelegatedCredentialReference(delegatedCredentialReference);
    params.setDelegatedCredentialReference(delegatedCredentialReferenceContainer);
    gov.nih.nci.cagrid.fqp.stubs.QueryAsynchronouslyRequestQueryExecutionParameters queryExecutionParametersContainer = new gov.nih.nci.cagrid.fqp.stubs.QueryAsynchronouslyRequestQueryExecutionParameters();
    queryExecutionParametersContainer.setQueryExecutionParameters(queryExecutionParameters);
    params.setQueryExecutionParameters(queryExecutionParametersContainer);
    gov.nih.nci.cagrid.fqp.stubs.QueryAsynchronouslyResponse boxedResult = portType.queryAsynchronously(params);
    EndpointReferenceType ref = boxedResult.getFederatedQueryResultsRetrievalReference().getEndpointReference();
    return new gov.nih.nci.cagrid.fqp.resultsretrieval.client.FederatedQueryResultsRetrievalClient(ref,getProxy());
    }
  }

  public gov.nih.nci.cagrid.dcqlresult.DCQLQueryResultsCollection execute(gov.nih.nci.cagrid.dcql.DCQLQuery query) throws RemoteException, gov.nih.nci.cagrid.fqp.stubs.types.FederatedQueryProcessingFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"execute");
    gov.nih.nci.cagrid.fqp.stubs.ExecuteRequest params = new gov.nih.nci.cagrid.fqp.stubs.ExecuteRequest();
    gov.nih.nci.cagrid.fqp.stubs.ExecuteRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.ExecuteRequestQuery();
    queryContainer.setDCQLQuery(query);
    params.setQuery(queryContainer);
    gov.nih.nci.cagrid.fqp.stubs.ExecuteResponse boxedResult = portType.execute(params);
    return boxedResult.getDCQLQueryResultsCollection();
    }
  }

  public gov.nih.nci.cagrid.cqlresultset.CQLQueryResults executeAndAggregateResults(gov.nih.nci.cagrid.dcql.DCQLQuery query) throws RemoteException, gov.nih.nci.cagrid.fqp.stubs.types.FederatedQueryProcessingFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"executeAndAggregateResults");
    gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsRequest params = new gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsRequest();
    gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsRequestQuery();
    queryContainer.setDCQLQuery(query);
    params.setQuery(queryContainer);
    gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsResponse boxedResult = portType.executeAndAggregateResults(params);
    return boxedResult.getCQLQueryResultCollection();
    }
  }

  public gov.nih.nci.cagrid.fqp.results.client.FederatedQueryResultsClient executeAsynchronously(gov.nih.nci.cagrid.dcql.DCQLQuery query) throws RemoteException, org.apache.axis.types.URI.MalformedURIException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"executeAsynchronously");
    gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyRequest params = new gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyRequest();
    gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyRequestQuery();
    queryContainer.setDCQLQuery(query);
    params.setQuery(queryContainer);
    gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyResponse boxedResult = portType.executeAsynchronously(params);
    EndpointReferenceType ref = boxedResult.getFederatedQueryResultsReference().getEndpointReference();
    return new gov.nih.nci.cagrid.fqp.results.client.FederatedQueryResultsClient(ref,getProxy());
    }
  }

  public gov.nih.nci.cagrid.fqp.results.client.FederatedQueryResultsClient query(gov.nih.nci.cagrid.dcql.DCQLQuery query,org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference delegatedCredentialReference,org.cagrid.fqp.execution.QueryExecutionParameters queryExecutionParameters) throws RemoteException, org.apache.axis.types.URI.MalformedURIException, gov.nih.nci.cagrid.fqp.stubs.types.FederatedQueryProcessingFault, gov.nih.nci.cagrid.fqp.results.stubs.types.InternalErrorFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"query");
    gov.nih.nci.cagrid.fqp.stubs.QueryRequest params = new gov.nih.nci.cagrid.fqp.stubs.QueryRequest();
    gov.nih.nci.cagrid.fqp.stubs.QueryRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.QueryRequestQuery();
    queryContainer.setDCQLQuery(query);
    params.setQuery(queryContainer);
    gov.nih.nci.cagrid.fqp.stubs.QueryRequestDelegatedCredentialReference delegatedCredentialReferenceContainer = new gov.nih.nci.cagrid.fqp.stubs.QueryRequestDelegatedCredentialReference();
    delegatedCredentialReferenceContainer.setDelegatedCredentialReference(delegatedCredentialReference);
    params.setDelegatedCredentialReference(delegatedCredentialReferenceContainer);
    gov.nih.nci.cagrid.fqp.stubs.QueryRequestQueryExecutionParameters queryExecutionParametersContainer = new gov.nih.nci.cagrid.fqp.stubs.QueryRequestQueryExecutionParameters();
    queryExecutionParametersContainer.setQueryExecutionParameters(queryExecutionParameters);
    params.setQueryExecutionParameters(queryExecutionParametersContainer);
    gov.nih.nci.cagrid.fqp.stubs.QueryResponse boxedResult = portType.query(params);
    EndpointReferenceType ref = boxedResult.getFederatedQueryResultsReference().getEndpointReference();
    return new gov.nih.nci.cagrid.fqp.results.client.FederatedQueryResultsClient(ref,getProxy());
    }
  }

  public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getMultipleResourceProperties");
    return portType.getMultipleResourceProperties(params);
    }
  }

  public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getResourceProperty");
    return portType.getResourceProperty(params);
    }
  }

  public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"queryResourceProperties");
    return portType.queryResourceProperties(params);
    }
  }

  public org.cagrid.data.dcql.results.DCQLQueryResultsCollection executeQuery(org.cagrid.data.dcql.DCQLQuery query) throws RemoteException, gov.nih.nci.cagrid.fqp.stubs.types.FederatedQueryProcessingFault, gov.nih.nci.cagrid.fqp.results.stubs.types.InternalErrorFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"executeQuery");
    gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryRequest params = new gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryRequest();
    gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryRequestQuery();
    queryContainer.setDCQLQuery(query);
    params.setQuery(queryContainer);
    gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryResponse boxedResult = portType.executeQuery(params);
    return boxedResult.getDCQLQueryResultsCollection();
    }
  }

  public org.cagrid.cql2.results.CQLQueryResults executeQueryAndAggregate(org.cagrid.data.dcql.DCQLQuery query) throws RemoteException, gov.nih.nci.cagrid.fqp.stubs.types.FederatedQueryProcessingFault, gov.nih.nci.cagrid.fqp.results.stubs.types.InternalErrorFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"executeQueryAndAggregate");
    gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryAndAggregateRequest params = new gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryAndAggregateRequest();
    gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryAndAggregateRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryAndAggregateRequestQuery();
    queryContainer.setDCQLQuery(query);
    params.setQuery(queryContainer);
    gov.nih.nci.cagrid.fqp.stubs.ExecuteQueryAndAggregateResponse boxedResult = portType.executeQueryAndAggregate(params);
    return boxedResult.getCQLQueryResults();
    }
  }

}
