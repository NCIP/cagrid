package org.cagrid.introduce.tutorial.stockmanager.portfolio.service;

import java.rmi.RemoteException;

import gov.nih.nci.cagrid.enumeration.stubs.response.EnumerationResponseContainer;
import gov.nih.nci.cagrid.wsenum.utils.EnumIteratorFactory;
import gov.nih.nci.cagrid.wsenum.utils.EnumerateResponseFactory;
import gov.nih.nci.cagrid.wsenum.utils.IterImplType;

import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;

import org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.types.PortfolioEnumerationException;
import org.cagrid.introduce.tutorial.tools.beans.Symbols;
import org.globus.ws.enumeration.EnumIterator;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class StockPortfolioManagerImpl extends StockPortfolioManagerImplBase {

	
	public StockPortfolioManagerImpl() throws RemoteException {
		super();
	}
	
  public void addStock(java.lang.String symbol) throws RemoteException {
    org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResource resource = null;
try {
    //get the currently addressed resource
    resource = getResourceHome().getAddressedResource();
} catch (Exception e1) {
    throw new RemoteException("Unable to locate resource: " + e1.getMessage());
}

//get that resource instance's portfolio
org.cagrid.introduce.tutorial.tools.beans.Portfolio port = resource.getPortfolio();

//add the symbol to the portfolio
org.cagrid.introduce.tutorial.tools.PortfolioManager.addSymbol(port, symbol);

//reset the portfolio instance on the resource instance
resource.setPortfolio(port);

  }

  public org.cagrid.introduce.tutorial.tools.beans.PortfolioInstance getPortfolioQuote() throws RemoteException {
    org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResource resource = null;
try {
    //get the currently addressed resource
    resource = getResourceHome().getAddressedResource();
} catch (Exception e1) {
    throw new RemoteException("Unable to locate resource: " + e1.getMessage());
}

//get that resource instance's portfolio
org.cagrid.introduce.tutorial.tools.beans.Portfolio port = resource.getPortfolio();

//return a generated portfolio instance
return org.cagrid.introduce.tutorial.tools.PortfolioManager.generatePortfolioInstance(port);

  }

  public gov.nih.nci.cagrid.enumeration.stubs.response.EnumerationResponseContainer enumerateStocks() throws RemoteException, org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.types.PortfolioEnumerationException {
    try {
	org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResource resource = null;
	//get the currently addressed resource
	resource = getResourceHome().getAddressedResource();

	//get that resource instance's portfolio
	org.cagrid.introduce.tutorial.tools.beans.Portfolio portfolio = resource.getPortfolio();

	Symbols portfolioSymbols = portfolio.getSymbols();
	List<String> symbols = Arrays.asList(portfolioSymbols.getSymbol());

	// create an enum iterator
	EnumIterator enumIter = EnumIteratorFactory.createIterator(
			IterImplType.CAGRID_CONCURRENT_COMPLETE, 
			symbols, 
			new QName("http://www.w3.org/2001/XMLSchema", "string"),
			null);

	// formulate the response container and return
	EnumerationResponseContainer response = 
		EnumerateResponseFactory.createEnumerationResponse(enumIter);
	return response;
} catch (Exception e) {
	System.err.println(e.getMessage());
	PortfolioEnumerationException pee = (PortfolioEnumerationException)PortfolioEnumerationException.makeFault(e);
	throw pee;
}

  }

}

