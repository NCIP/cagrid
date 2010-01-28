package org.cagrid.introduce.tutorial.stockmanager.portfolio.service;

import java.io.File;
import java.rmi.RemoteException;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.enumeration.stubs.response.EnumerationResponseContainer;
import gov.nih.nci.cagrid.wsenum.utils.EnumIteratorFactory;
import gov.nih.nci.cagrid.wsenum.utils.EnumerateResponseFactory;
import gov.nih.nci.cagrid.wsenum.utils.IterImplType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.types.PortfolioEnumerationException;
import org.cagrid.introduce.tutorial.tools.beans.Portfolio;
import org.cagrid.introduce.tutorial.tools.beans.Symbols;
import org.cagrid.transfer.context.service.globus.resource.TransferServiceContextResource;
import org.cagrid.transfer.context.service.helper.DataStagedCallback;
import org.cagrid.transfer.context.service.helper.TransferServiceHelper;
import org.cagrid.transfer.descriptor.DataDescriptor;
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

  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference addPortfolioSymbols() throws RemoteException {
	org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResource resource = null;
	try {
	    //get the currently addressed resource
	    resource = getResourceHome().getAddressedResource();
	} catch (Exception e1) {
	    throw new RemoteException("Unable to locate resource: " + e1.getMessage());
	}
	
	// create a data descriptor for the upload for the data to be uploaded
	DataDescriptor dd = new DataDescriptor(null, "Users Data");
	
	// create a callback that will handle the data once it is uploaded
	DataStagedCallback callback = new MyDataStagedCallback(resource);
	
	// create the transfer resource that will handle receiving the data and
	// return the reference to the user
	return TransferServiceHelper.createTransferContext(dd, callback);
  }
  
  static class MyDataStagedCallback implements DataStagedCallback {
	  
	private org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResource resource;
	  
	public MyDataStagedCallback(org.cagrid.introduce.tutorial.stockmanager.portfolio.service.globus.resource.StockPortfolioManagerResource resource) {
	  this.resource = resource;
	}
			
	public void dataStaged(TransferServiceContextResource resource) {
	    File dataFileUserSentMe = new File(resource.getDataStorageDescriptor().getLocation());
	    
	    // do something with this data
	    System.out.println("Received File: " +resource.getDataStorageDescriptor().getLocation());
	    
	    try {	        	
	    	// deserialize the document to a Symbols object
	    	Symbols symbols = (Symbols) Utils.deserializeDocument(dataFileUserSentMe.getPath(), Symbols.class);
	        List<String> addSymbolList = Arrays.asList(symbols.getSymbol());
	        
	        if (addSymbolList.size() == 0) {
	        	return;
	        }
	        
	        //get referenced portfolio
	        Portfolio managedPortfolio  = this.resource.getPortfolio();
	        
	        // Adding stocks to existing symbol list
	        if (managedPortfolio.getSymbols() != null) {
	        	
	        	Symbols oldSymbols = managedPortfolio.getSymbols();
	            List<String> oldSymbolList = Arrays.asList(oldSymbols.getSymbol());
	            
	            // add current symbols to list
	            List<String> newSymbolList = new ArrayList<String>();
	            newSymbolList.addAll(oldSymbolList);
	            
	            // add new symbols that do not currently exist
	            Iterator<String> iter = addSymbolList.iterator();
	            
	            while (iter.hasNext()) {
	            	String value = (String) iter.next();
	            	
	            	// don't add duplicate symbols
	            	if (! newSymbolList.contains(value)) {
	            		newSymbolList.add(value);
	            	}
	            }
	            
	            //convert to String[]
	            String[] symbolArray = new String[newSymbolList.size()];
	            newSymbolList.toArray(symbolArray);
	            
	            // add to portfolio
	            managedPortfolio.getSymbols().setSymbol(symbolArray);
	        }
	        else {
	            // add Symbols to portfolio
	            managedPortfolio.setSymbols(symbols);
	        }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  };

}

