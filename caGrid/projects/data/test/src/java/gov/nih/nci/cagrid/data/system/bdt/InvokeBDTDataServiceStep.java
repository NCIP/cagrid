package gov.nih.nci.cagrid.data.system.bdt;

import gov.nih.nci.cagrid.bdt.client.BulkDataHandlerClient;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.data.bdt.client.BDTDataServiceClient;
import gov.nih.nci.cagrid.introduce.extension.utils.AxisJdomUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.soap.SOAPElement;

import org.apache.axis.message.MessageElement;
import org.globus.transfer.AnyXmlType;
import org.globus.transfer.EmptyType;
import org.globus.ws.enumeration.ClientEnumIterator;
import org.jdom.Element;
import org.projectmobius.bookstore.Book;
import org.projectmobius.common.XMLUtilities;
import org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;

import com.atomicobject.haste.framework.Step;

/** 
 *  InvokeBDTDataServiceStep
 *  Step to invoke a BDT data service and exercise its methods
 * 
 * @author David Ervin
 * 
 * @created Mar 14, 2007 2:37:02 PM
 * @version $Id: InvokeBDTDataServiceStep.java,v 1.4 2007-05-08 20:52:43 dervin Exp $ 
 */
public class InvokeBDTDataServiceStep extends Step {
	public static final String URL_PART = "/wsrf/services/cagrid/";
	
	private String hostName;
	private int port;
	private String serviceName;
	
	
	public InvokeBDTDataServiceStep(String hostName, int port, String serviceName) {
		this.hostName = hostName;
		this.port = port;
		this.serviceName = serviceName;
	}
	

	public void runStep() throws Throwable {
		System.out.println("Running step " + getClass().getName());
		String serviceUrl = getServiceUrl();
		System.out.println("Invoking service at URL " + serviceUrl);
		
        // create the BDT service client handle
        BDTDataServiceClient bdtClient = new BDTDataServiceClient(serviceUrl);
        
        BulkDataHandlerClient bdtHandlerClient = startBdt(bdtClient);
        
        iterateEnumeration(bdtHandlerClient);
        
        invokeTransfer(bdtHandlerClient);
        
        // TODO: invoke getGridFTPURls()
	}
    
    
    private BulkDataHandlerClient startBdt(BDTDataServiceClient client) throws Exception {
        CQLQuery bookQuery = new CQLQuery();
        gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
        target.setName(Book.class.getName());
        bookQuery.setTarget(target);
        BulkDataHandlerClient bdtHandlerClient = client.bdtQuery(bookQuery);
        return bdtHandlerClient;
    }
    
    
    private EnumerateResponse beginEnumeration(BulkDataHandlerClient client) throws Exception {
        EnumerateResponse response = client.createEnumeration();
        assertNotNull("Enumeration response does not contain an enumeration context", response.getEnumerationContext());
        return response;
    }
    
    
    private void iterateEnumeration(BulkDataHandlerClient client) throws Exception {
        EnumerateResponse response = beginEnumeration(client);
        
        /*
         * This is the preferred way to access an enumeration, but the client enum iterator hides
         * remote exceptions from the user and throws an empty NoSuchElement exception.
         */
        // TODO: fix this to use the enumerate response container
        ClientEnumIterator iter = new ClientEnumIterator((DataSource) null, response.getEnumerationContext());
        int resultCount = 0;
        try {
            while (iter.hasNext()) {
                SOAPElement elem = (SOAPElement) iter.next();
                String elemText = elem.toString();
                // make sure it's a book element
                int bookIndex = elemText.indexOf("Book");
                if (bookIndex == -1) {
                    throw new NoSuchElementException("Element returned was not of the type Book!");
                }
                resultCount++;
            }
        } catch (NoSuchElementException ex) {
            if (resultCount == 0) {
                throw ex;
            }
        } finally {
            iter.release();
            try {
                iter.next();
                fail("Call to next() after release should have failed!");
            } catch (NoSuchElementException ex) {
                // expected
            } catch (Exception ex) {
                ex.printStackTrace();
                fail("Exception other than NoSuchElementException thrown: " + ex.getClass().getName());
            }
        }
        assertTrue("No results were returned from the enumeration", resultCount != 0);
    }
    
    
    private void invokeTransfer(BulkDataHandlerClient client) throws Exception {
        AnyXmlType any = client.get(new EmptyType());
        MessageElement[] anyElements = any.get_any();
        assertNotNull("Content from transfer was null", anyElements);
        assertTrue("Content from transfer was empty", anyElements.length != 0);
        
        Element resultsElement = AxisJdomUtils.fromMessageElement(anyElements[0]);
        // get object results out of it
        int resultCount = 0;
        Iterator objectElementIter = resultsElement.getChildren(
            "ObjectResult", resultsElement.getNamespace()).iterator();
        while (objectElementIter.hasNext()) {
            resultCount++;
            Element objectResultElement = (Element) objectElementIter.next();
            Element objectElement = (Element) objectResultElement.getChildren().get(0);
            // convert the object element back to a string
            String elementString = XMLUtilities.elementToString(objectElement);
            assertTrue("Element did not contain a Book object", 
                elementString.indexOf("Book") != -1);
        }
        assertTrue("Object results were not returned", resultCount != 0);
    }
	
	
	private String getServiceUrl() {
		return "http://" + hostName + ":" + port + URL_PART + serviceName;
	}
}
