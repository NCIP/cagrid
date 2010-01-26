package org.cagrid.introduce.tutorial.stockmanager.service;

import java.rmi.RemoteException;
import org.cagrid.transfer.context.service.helper.TransferServiceHelper;
import org.cagrid.transfer.descriptor.DataDescriptor;
import java.util.Calendar;
import java.util.GregorianCalendar;
/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class StockManagerImpl extends StockManagerImplBase {

	
	public StockManagerImpl() throws RemoteException {
		super();
	}
	
  public org.cagrid.introduce.tutorial.tools.beans.Quote getQuote(java.lang.String symbol) throws RemoteException {
    try {
	org.cagrid.introduce.tutorial.tools.beans.Quote quote = 
		org.cagrid.introduce.tutorial.tools.YahooQuote.getQuote(symbol);
	return quote;
} catch (Exception e) {
	throw new RemoteException("Unable to generate quote: " + e.getMessage());
}
  }

  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference getChart(java.lang.String symbol) throws RemoteException {
    byte[] chart;
	try {
	chart = org.cagrid.introduce.tutorial.tools.YahooQuote.getChart(symbol);
	DataDescriptor dd = new DataDescriptor(null, "Stock Chart for " + symbol);
	// create the transfer resource that will handle delivering the data and
	// return the reference to the user
	return TransferServiceHelper.createTransferContext(chart, dd);
	} catch (Exception e) {
	// TODO Auto-generated catch block
	String msg = e.getMessage();
	System.err.println(msg);
	throw new RemoteException(msg);
	}

  }

  public org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.types.StockPortfolioManagerReference createPortfolio(java.lang.String portfolioName) throws RemoteException {
		org.apache.axis.message.addressing.EndpointReferenceType epr = new org.apache.axis.message.addressing.EndpointReferenceType();
		org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResourceHome home = null;
		org.globus.wsrf.ResourceKey resourceKey = null;
		org.apache.axis.MessageContext ctx = org.apache.axis.MessageContext.getCurrentContext();
		String servicePath = ctx.getTargetService();
		String homeName = org.globus.wsrf.Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/" + "stockPortfolioManagerHome";

		try {
			javax.naming.Context initialContext = new javax.naming.InitialContext();
			home = (org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResourceHome) initialContext.lookup(homeName);
			resourceKey = home.createResource();
			
			//  Grab the newly created resource
			org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResource thisResource = (org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResource)home.find(resourceKey);
			
			//  This is where the creator of this resource type can set whatever needs
			//  to be set on the resource so that it can function appropriatly  for instance
			//  if you want the resouce to only have the query string then there is where you would
			//  give it the query string.
			//this will create a new empty portfolio with the given name and set it on the newly created resource
			thisResource.setPortfolio(new org.cagrid.introduce.tutorial.tools.beans.Portfolio(portfolioName,null));
//set the default termination time to 20 minutes, this will destroy the resource in 20 minutes unless someone alters it
Calendar cal = new GregorianCalendar();
cal.add(Calendar.MINUTE, 20);
thisResource.setTerminationTime(cal);

			
			// sample of setting creator only security.  This will only allow the caller that created
			// this resource to be able to use it.
			//thisResource.setSecurityDescriptor(gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.createCreatorOnlyResourceSecurityDescriptor());
			
			

			String transportURL = (String) ctx.getProperty(org.apache.axis.MessageContext.TRANS_URL);
			transportURL = transportURL.substring(0,transportURL.lastIndexOf('/') +1 );
			transportURL += "StockPortfolioManager";
			epr = org.globus.wsrf.utils.AddressingUtils.createEndpointReference(transportURL,resourceKey);
		} catch (Exception e) {
			throw new RemoteException("Error looking up StockPortfolioManager home:" + e.getMessage(), e);
		}

		//return the typed EPR
		org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.types.StockPortfolioManagerReference ref = new org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.types.StockPortfolioManagerReference();
		ref.setEndpointReference(epr);

		return ref;
  }

}

