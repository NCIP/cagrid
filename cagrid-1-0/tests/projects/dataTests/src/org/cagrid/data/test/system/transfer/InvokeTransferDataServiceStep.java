package org.cagrid.data.test.system.transfer;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.Group;
import gov.nih.nci.cagrid.cqlquery.LogicalOperator;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.faults.MalformedQueryExceptionType;
import gov.nih.nci.cagrid.data.faults.QueryProcessingExceptionType;
import gov.nih.nci.cagrid.data.transfer.client.TransferDataServiceClient;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainer;
import gov.nih.nci.cagrid.testing.system.haste.Step;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.rmi.RemoteException;

import org.apache.axis.types.URI;
import org.cagrid.transfer.context.client.TransferServiceContextClient;
import org.cagrid.transfer.context.client.helper.TransferClientHelper;
import org.cagrid.transfer.context.stubs.types.TransferServiceContextReference;
import org.cagrid.transfer.descriptor.DataTransferDescriptor;
import org.projectmobius.bookstore.Book;

/** 
 *  InvokeTransferDataServiceStep
 *  Testing step to invoke a caGrid transfer data service
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>  * 
 * @created Nov 23, 2006 
 * @version $Id: InvokeTransferDataServiceStep.java,v 1.1 2009-04-16 20:59:28 dervin Exp $ 
 */
public class InvokeTransferDataServiceStep extends Step {
	
    private ServiceContainer container;
	private String serviceName;
	
	public InvokeTransferDataServiceStep(ServiceContainer container, String serviceName) {
        this.container = container;
		this.serviceName = serviceName;
	}
    

	public void runStep() throws Throwable {
		String serviceUrl = getServiceUrl();
		// create the generic transfer data service client
		TransferDataServiceClient client = new TransferDataServiceClient(serviceUrl);
		
		// run a query and transfer the results
		queryAndTransfer(client);
		
        // make sure invalid classes still throw exceptions
		queryForInvalidClass(client);
		
        // make sure malformed queries behave as expected
		submitMalformedQuery(client);
    }
	
	
	private void queryForInvalidClass(TransferDataServiceClient client) throws Throwable {
		CQLQuery query = new CQLQuery();
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
		target.setName("non.existant.class");
		query.setTarget(target);
		try {
            client.transferQuery(query);
            fail("Query for non-existant class did not throw an exception!");
		} catch (QueryProcessingExceptionType ex) {
			// expected
		} catch (Exception ex) {
            ex.printStackTrace();
		    fail("Unexpected exception thrown: " + ex.getClass().getName());
        }
	}
	
	
	private void submitMalformedQuery(TransferDataServiceClient client) throws Throwable {
		CQLQuery query = new CQLQuery();
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
		target.setName(Book.class.getName());
		Attribute attrib1 = new Attribute("name", Predicate.LIKE, "E%");
		target.setAttribute(attrib1);
		Group group = new Group();
		group.setLogicRelation(LogicalOperator.AND);
		group.setAttribute(new Attribute[] {
			new Attribute("author", Predicate.IS_NOT_NULL, ""),
			new Attribute("ISBN", Predicate.IS_NULL, "")
		});
		target.setGroup(group);
		query.setTarget(target);
		try {
            client.transferQuery(query);
            fail("Malformed query did not throw an exception!");
		} catch (MalformedQueryExceptionType ex) {
			// expected
		} catch (Exception ex) {
            ex.printStackTrace();
		    fail("Unexpected exception thrown: " + ex.getClass().getName());
        }
	}
	
	
	private TransferServiceContextReference queryForBooks(TransferDataServiceClient client) throws Throwable {
		CQLQuery query = new CQLQuery();
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
		target.setName(Book.class.getName());
		query.setTarget(target);
        TransferServiceContextReference transferContext = null;
		try {
			transferContext = client.transferQuery(query);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return transferContext;
	}
	
	
	private void queryAndTransfer(TransferDataServiceClient client) throws Throwable {
		TransferServiceContextReference transferContext = queryForBooks(client);
        
		TransferServiceContextClient transferClient = null;
        try {
            transferClient = new TransferServiceContextClient(transferContext.getEndpointReference());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Error creating transfer client: " + ex.getMessage());
        }
        
        /*
         * FIXME: This throws a no deserializer found if the metadata is populated
         */
        DataTransferDescriptor transferDescriptor = null;
        try {
            transferDescriptor = transferClient.getDataTransferDescriptor();
        } catch (RemoteException ex) {
            ex.printStackTrace();
            fail("Error getting data transfer descriptor: " + ex.getMessage());
        }
        
        InputStream dataStream = null;
        try {
            dataStream = TransferClientHelper.getData(transferDescriptor);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Error getting data input stream from transfer context: " + ex.getMessage());
        }
        
        ByteArrayOutputStream dataBuffer = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        int bytesRead = -1;
        try {
            while ((bytesRead = dataStream.read(temp)) != -1) {
                dataBuffer.write(temp, 0, bytesRead);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            fail("Error reading data from transfer service: " + ex.getMessage());
        }
        
        try {
            dataStream.close();
        } catch (IOException ex) {
            System.err.println("Error closing data streams: " + ex.getMessage());
        }
        
        String xml = dataBuffer.toString();
        StringReader xmlReader = new StringReader(xml);
        CQLQueryResults results = null;
        try {
            results = (CQLQueryResults) Utils.deserializeObject(xmlReader, CQLQueryResults.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Error deserializing CQL query results: " + ex.getMessage());
        }
        
        // release the transfer resource
        try {
            transferClient.destroy();
        } catch (RemoteException ex) {
            ex.printStackTrace();
            System.err.println("Error destroying transfer context: " + ex.getMessage());
        }
        
        // validate results from transfer
        // FIXME: compare results to known-gold results
        int resultCount = 0;
        CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results, true);
        while (iter.hasNext()) {
            resultCount++;
            iter.next();
        }
        
        assertTrue("No results were returned from the enumeration", resultCount != 0);
	}
	
	
	private String getServiceUrl() throws Exception {
        URI baseUri = container.getContainerBaseURI();
        return baseUri.toString() + "cagrid/" + serviceName;
	}
}
