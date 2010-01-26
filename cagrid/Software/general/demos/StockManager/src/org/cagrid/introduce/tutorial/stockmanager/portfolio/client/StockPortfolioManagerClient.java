package org.cagrid.introduce.tutorial.stockmanager.portfolio.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.StockPortfolioManagerPortType;
import org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.service.StockPortfolioManagerServiceAddressingLocator;
import org.cagrid.introduce.tutorial.stockmanager.portfolio.common.StockPortfolioManagerI;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class StockPortfolioManagerClient extends StockPortfolioManagerClientBase implements StockPortfolioManagerI {	

	public StockPortfolioManagerClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public StockPortfolioManagerClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public StockPortfolioManagerClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public StockPortfolioManagerClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(StockPortfolioManagerClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StockPortfolioManagerClient client = new StockPortfolioManagerClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
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

  public void addStock(java.lang.String symbol) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"addStock");
    org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.AddStockRequest params = new org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.AddStockRequest();
    org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.AddStockRequestSymbol symbolContainer = new org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.AddStockRequestSymbol();
    symbolContainer.setSymbol(symbol);
    params.setSymbol(symbolContainer);
    org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.AddStockResponse boxedResult = portType.addStock(params);
    }
  }

  public org.cagrid.introduce.tutorial.tools.beans.PortfolioInstance getPortfolioQuote() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPortfolioQuote");
    org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.GetPortfolioQuoteRequest params = new org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.GetPortfolioQuoteRequest();
    org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.GetPortfolioQuoteResponse boxedResult = portType.getPortfolioQuote(params);
    return boxedResult.getPortfolioInstance();
    }
  }

  public gov.nih.nci.cagrid.enumeration.stubs.response.EnumerationResponseContainer enumerateStocks() throws RemoteException, org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.types.PortfolioEnumerationException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"enumerateStocks");
    org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.EnumerateStocksRequest params = new org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.EnumerateStocksRequest();
    org.cagrid.introduce.tutorial.stockmanager.portfolio.stubs.EnumerateStocksResponse boxedResult = portType.enumerateStocks(params);
    return boxedResult.getEnumerationResponseContainer();
    }
  }

  public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"destroy");
    return portType.destroy(params);
    }
  }

  public org.oasis.wsrf.lifetime.SetTerminationTimeResponse setTerminationTime(org.oasis.wsrf.lifetime.SetTerminationTime params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"setTerminationTime");
    return portType.setTerminationTime(params);
    }
  }

  public org.oasis.wsn.SubscribeResponse subscribe(org.oasis.wsn.Subscribe params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"subscribe");
    return portType.subscribe(params);
    }
  }

}
