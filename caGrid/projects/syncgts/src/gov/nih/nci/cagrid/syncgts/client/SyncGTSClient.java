package gov.nih.nci.cagrid.syncgts.client;


import java.io.InputStream;
import java.rmi.RemoteException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.axis.utils.ClassUtils;

import org.globus.gsi.GlobusCredential;

import gov.nih.nci.cagrid.syncgts.stubs.SyncGTSPortType;
import gov.nih.nci.cagrid.syncgts.stubs.service.SyncGTSServiceAddressingLocator;
import gov.nih.nci.cagrid.syncgts.common.SyncGTSI;


/**
 * This class is autogenerated, DO NOT EDIT.
 * 
 * @created by Introduce Toolkit version 1.0
 */
public class SyncGTSClient implements SyncGTSI {	
	private GlobusCredential proxy;
	private EndpointReferenceType epr;
	
	static{
		org.globus.axis.util.Util.registerTransport();
	}
	
	public SyncGTSClient(String url) throws MalformedURIException {
		this(url,null);	
	}

	public SyncGTSClient(String url, GlobusCredential proxy) throws MalformedURIException {
	   	this.proxy = proxy;
	   	this.epr = new EndpointReferenceType();
	   	this.epr.setAddress(new Address(url));
	}
	
	public SyncGTSClient(EndpointReferenceType epr) throws MalformedURIException {
	   	this(epr,null);
	}
	
	public SyncGTSClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException {
	   	this.proxy = proxy;
	   	this.epr = epr;
	}

	private SyncGTSPortType getPortType() throws RemoteException {

		SyncGTSServiceAddressingLocator locator = new SyncGTSServiceAddressingLocator();
		// attempt to load our context sensitive wsdd file
		InputStream resourceAsStream = ClassUtils.getResourceAsStream(getClass(), "client-config.wsdd");
		if (resourceAsStream != null) {
			// we found it, so tell axis to configure an engine to use it
			EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
			// set the engine of the locator
			locator.setEngine(new AxisClient(engineConfig));
		}
		SyncGTSPortType port = null;
		try {
			port = locator.getSyncGTSPortTypePort(this.epr);
		} catch (Exception e) {
			throw new RemoteException("Unable to configured porttype:" + e.getMessage(), e);
		}

		return port;
	}
	
	public static void usage(){
		System.out.println(SyncGTSClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  SyncGTSClient client = new SyncGTSClient(args[1]);
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
		}
	}
	

}