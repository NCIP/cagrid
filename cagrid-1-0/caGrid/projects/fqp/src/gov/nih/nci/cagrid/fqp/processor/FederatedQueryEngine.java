package gov.nih.nci.cagrid.fqp.processor;

import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlresultset.CQLObjectResult;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.dcql.DCQLQuery;
import gov.nih.nci.cagrid.fqp.common.SerializationUtils;
import gov.nih.nci.cagrid.fqp.processor.exceptions.FederatedQueryProcessingException;
import gov.nih.nci.cagrid.fqp.processor.exceptions.RemoteDataServiceException;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author Srini Akkala
 * @author Scott Oster
 */
public class FederatedQueryEngine {
	protected static Log LOG = LogFactory.getLog(FederatedQueryEngine.class.getName());


	public FederatedQueryEngine() {
	}


	/**
	 * Call Federated Query Processor, and send the generated CQLQuery to each
	 * targeted service, aggregating the results.
	 * 
	 * @param dcqlQuery
	 * @return
	 * @throws FederatedQueryException
	 */
	public CQLQueryResults execute(DCQLQuery dcqlQuery) throws FederatedQueryProcessingException {
		FederatedQueryProcessor processor = new FederatedQueryProcessor();
		if (LOG.isDebugEnabled()) {
			try {
				StringWriter s = new StringWriter();
				SerializationUtils.serializeDCQLQuery(dcqlQuery, s);
				LOG.debug("Beginning processing of query:" + s.toString());
				s.close();
			} catch (Exception e) {
				LOG.error("Problem in debug printout of DCQL query:" + e.getMessage(), e);
			}
		}

		CQLQuery cqlQuery = processor.processDCQLQueryPlan(dcqlQuery.getTargetObject());
		CQLQueryResults aggregateResults = null;
		String[] targetServiceURLs = dcqlQuery.getTargetServiceURL();
		for (int i = 0; i < targetServiceURLs.length; i++) {
			// aggregate results
			CQLQueryResults currResults = DataServiceQueryExecutor.queryDataService(cqlQuery, targetServiceURLs[i]);
			if (currResults != null && currResults.getObjectResult() != null) {
				if (!currResults.getTargetClassname().equals(dcqlQuery.getTargetObject().getName())) {
					throw new RemoteDataServiceException("Data service (" + targetServiceURLs[i]
						+ ") returned results of type (" + currResults.getTargetClassname() + ") when type ("
						+ dcqlQuery.getTargetObject().getName() + ") was requested!");
				}
				if (aggregateResults == null) {
					// initialize our return to current result if first time we
					// got something
					aggregateResults = currResults;
				} else {
					int neededSize = 0;
					if (aggregateResults.getObjectResult() != null) {
						neededSize += aggregateResults.getObjectResult().length;
					}

					// need room to store old + current
					neededSize += currResults.getObjectResult().length;
					CQLObjectResult[] tmpArr = new CQLObjectResult[neededSize];

					// copy old to temp
					System.arraycopy(aggregateResults.getObjectResult(), 0, tmpArr, 0, aggregateResults
						.getObjectResult().length);
					// copy new to temp, after old
					System.arraycopy(currResults.getObjectResult(), 0, tmpArr,
						aggregateResults.getObjectResult().length, currResults.getObjectResult().length);
					// set the new results on the return object
					aggregateResults.setObjectResult(tmpArr);
				}
			}
		}

		return aggregateResults;
	}
}
