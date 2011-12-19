package gov.nih.nci.cagrid.identifiers.client;

import gov.nih.nci.cagrid.identifiers.common.IdentifiersNAServiceI;

import java.rmi.RemoteException;

import namingauthority.IdentifierData;
import namingauthority.KeyData;
import namingauthority.KeyNameData;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;

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
public class IdentifiersNAServiceClient extends IdentifiersNAServiceClientBase implements IdentifiersNAServiceI {	

	public IdentifiersNAServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public IdentifiersNAServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public IdentifiersNAServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public IdentifiersNAServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(IdentifiersNAServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  IdentifiersNAServiceClient client = new IdentifiersNAServiceClient(args[1]);
			  client.setAnonymousPrefered(true);
			  
			  // place client calls here if you want to use this main as a
			  // test....

			  String[] keys = new String[] { "URLS" };
			  String[][] values = new String[][]{
					  {"http://www.google.com"},
			  };

			  KeyNameData[] kvs = new KeyNameData[ keys.length ];
			  for( int i=0; i < keys.length; i++) {

				  KeyData kd = new KeyData();
				  kd.setValue(values[i]);
				  kvs[i] = new KeyNameData(kd, keys[i]);
			  }

			  IdentifierData id = new IdentifierData(kvs);

			  org.apache.axis.types.URI identifier = client.createIdentifier(id);
			  System.out.println("Identifier: " + identifier.toString());
			  
			  //////////////////////////////////////////////////////////////////////
			  // Test identifier keys with no values are supported
			  //
//			  System.out.println(client.createIdentifier(null).toString());
//			  System.out.println(client.createIdentifier(new IdentifierKeyData()));
//			  
//			  IdentifierKeyData values = new IdentifierKeyData();
//			  values.setKeyData(null);
//			  System.out.println(client.createIdentifier(values).toString());
//			  
//			  values.setKeyData(new KeyData[]{});
//			  System.out.println(client.createIdentifier(values).toString());
//
//			  KeyData[] kvs = new KeyData[4];
//			  kvs[0] = new KeyData();
//			  kvs[0].setKeyName("key1");
			  
//				new KeyData("key1", null),
//				new KeyData("key2", new KeyData()),
//				new KeyData("key3", new KeyData(null, null)),
//				new KeyData("key4", new KeyData(null, new String[]{}))
//			  };
//			  values.setKeyValues(kvs);
//			  System.out.println(client.createIdentifier(values));
//
//			  // Try inserting a key with no name
//			  kvs = new KeyValues[] {
//					new KeyValues("", null),
//					new KeyValues("key2", new KeyData())
//			  };
//			  values.setKeyValues(kvs);
//			  System.out.println(client.createIdentifier(values));
					  
//
//			  values2 = client.resolveIdentifier(identifier);
//			  System.out.println("\n-------------------------------------\nAfter creating identifier:");
//			  printValues(values2);
//
//
//			  IdentifiersNAUtil.assertEquals(values1, values2);
			  
			  ////////////////////////////////////////
			  // Add couple of other keys
			  ////////////////////////////////////////
//			  KeyValues[] newKeyValues = new KeyValues[2];
//			  newKeyValues[0] = new KeyValues();
//			  newKeyValues[0].setKey("KEY3");
//			  newKeyValues[0].setKeyData(new KeyData(null, new String[]{"KEY3 VALUE"}));
//			  newKeyValues[1] = new KeyValues();
//			  newKeyValues[1].setKey("KEY4");
//			  newKeyValues[1].setKeyData(new KeyData(null, new String[]{"KEY4 VALUE"}));
//			  client.createKeys(identifier, new IdentifierValues(newKeyValues));
//			  values2 = client.resolveIdentifier(identifier);
//			  System.out.println("After createKeys:");
//			  System.out.println(values2.toString());
			  
			  // Delete all keys
//			  client.deleteAllKeys(identifier);
//			  IdentifierValues deleteAllKeysValues = client.resolveIdentifier(identifier);
//			  System.out.println("\n-------------------------------\nAfter deleteAllKeys:");
//			  printValues(deleteAllKeysValues);
			  
			  //Delete some keys
//			  client.deleteKeys(identifier, new String[]{ "URL" });
//			  IdentifierValues deleteKeysValues = client.resolveIdentifier(identifier);
//			  System.out.println("\n-----------------------------------------------\nAfter deleteKeys:");
//			  printValues(deleteKeysValues);
			  
			  //Replace some keys
//			  KeyValues[] replacedKeys = new KeyValues[2];
//			  replacedKeys[0] = new KeyValues();
//			  replacedKeys[0].setKey("EPR");
//			  replacedKeys[0].setKeyData(new KeyData(null, new String[] {"end point reference 3"}));
//			  
//			  replacedKeys[1] = new KeyValues();
//			  replacedKeys[1].setKey("URL");
//			  replacedKeys[1].setKeyData(new KeyData(null, new String[] {"http://www.casafiesta.com"}));
//			  
//			  client.replaceKeys(identifier, new IdentifierValues(replacedKeys));
//			  IdentifierValues replacedValues = client.resolveIdentifier(identifier);
//			  System.out.println("\n***************\nAfter replacedKeys:");
//			  printValues(replacedValues);				
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

//	private static void printValues(IdentifierValues values) {
//		if (values == null) {
//			return;
//		}
//		
//		for( KeyValues kv : values.getKeyValues()) {
//			  System.out.println("\n***********************\nKEY: " + kv.getKey());
//			  KeyData kd = kv.getKeyData();
//			  if (kd != null && kd.getValue() != null) {
//				  for(String value : kd.getValue()) {
//					  System.out.println("\t\tVALUE: " + value);
//				  }
//			  }
//		  }
//	}
	

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

  public org.apache.axis.types.URI createIdentifier(namingauthority.IdentifierData identifierData) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createIdentifier");
    gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequest params = new gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequest();
    gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequestIdentifierData identifierDataContainer = new gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierRequestIdentifierData();
    identifierDataContainer.setIdentifierData(identifierData);
    params.setIdentifierData(identifierDataContainer);
    gov.nih.nci.cagrid.identifiers.stubs.CreateIdentifierResponse boxedResult = portType.createIdentifier(params);
    return boxedResult.getIdentifier();
    }
  }

  public namingauthority.IdentifierData resolveIdentifier(org.apache.axis.types.URI identifier) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"resolveIdentifier");
    gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierRequest params = new gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierRequest();
    gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierRequestIdentifier identifierContainer = new gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierRequestIdentifier();
    identifierContainer.setIdentifier(identifier);
    params.setIdentifier(identifierContainer);
    gov.nih.nci.cagrid.identifiers.stubs.ResolveIdentifierResponse boxedResult = portType.resolveIdentifier(params);
    return boxedResult.getIdentifierData();
    }
  }

  public void deleteKeys(org.apache.axis.types.URI identifier,java.lang.String[] keyNames) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"deleteKeys");
    gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysRequest params = new gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysRequest();
    gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysRequestIdentifier identifierContainer = new gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysRequestIdentifier();
    identifierContainer.setIdentifier(identifier);
    params.setIdentifier(identifierContainer);
    gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysRequestKeyNames keyNamesContainer = new gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysRequestKeyNames();
    keyNamesContainer.setKeyName(keyNames);
    params.setKeyNames(keyNamesContainer);
    gov.nih.nci.cagrid.identifiers.stubs.DeleteKeysResponse boxedResult = portType.deleteKeys(params);
    }
  }

  public void createKeys(org.apache.axis.types.URI identifier,namingauthority.IdentifierData identifierData) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createKeys");
    gov.nih.nci.cagrid.identifiers.stubs.CreateKeysRequest params = new gov.nih.nci.cagrid.identifiers.stubs.CreateKeysRequest();
    gov.nih.nci.cagrid.identifiers.stubs.CreateKeysRequestIdentifier identifierContainer = new gov.nih.nci.cagrid.identifiers.stubs.CreateKeysRequestIdentifier();
    identifierContainer.setIdentifier(identifier);
    params.setIdentifier(identifierContainer);
    gov.nih.nci.cagrid.identifiers.stubs.CreateKeysRequestIdentifierData identifierDataContainer = new gov.nih.nci.cagrid.identifiers.stubs.CreateKeysRequestIdentifierData();
    identifierDataContainer.setIdentifierData(identifierData);
    params.setIdentifierData(identifierDataContainer);
    gov.nih.nci.cagrid.identifiers.stubs.CreateKeysResponse boxedResult = portType.createKeys(params);
    }
  }

  public void replaceKeyValues(org.apache.axis.types.URI identifier,namingauthority.IdentifierValues identifierValues) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"replaceKeyValues");
    gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesRequest params = new gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesRequest();
    gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesRequestIdentifier identifierContainer = new gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesRequestIdentifier();
    identifierContainer.setIdentifier(identifier);
    params.setIdentifier(identifierContainer);
    gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesRequestIdentifierValues identifierValuesContainer = new gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesRequestIdentifierValues();
    identifierValuesContainer.setIdentifierValues(identifierValues);
    params.setIdentifierValues(identifierValuesContainer);
    gov.nih.nci.cagrid.identifiers.stubs.ReplaceKeyValuesResponse boxedResult = portType.replaceKeyValues(params);
    }
  }

  public java.lang.String[] getKeyNames(org.apache.axis.types.URI identifier) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getKeyNames");
    gov.nih.nci.cagrid.identifiers.stubs.GetKeyNamesRequest params = new gov.nih.nci.cagrid.identifiers.stubs.GetKeyNamesRequest();
    gov.nih.nci.cagrid.identifiers.stubs.GetKeyNamesRequestIdentifier identifierContainer = new gov.nih.nci.cagrid.identifiers.stubs.GetKeyNamesRequestIdentifier();
    identifierContainer.setIdentifier(identifier);
    params.setIdentifier(identifierContainer);
    gov.nih.nci.cagrid.identifiers.stubs.GetKeyNamesResponse boxedResult = portType.getKeyNames(params);
    return boxedResult.getKeyName();
    }
  }

  public namingauthority.KeyNameData getKeyData(org.apache.axis.types.URI identifier,java.lang.String keyName) throws RemoteException, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthoritySecurityFault, gov.nih.nci.cagrid.identifiers.stubs.types.NamingAuthorityConfigurationFault, gov.nih.nci.cagrid.identifiers.stubs.types.InvalidIdentifierValuesFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getKeyData");
    gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataRequest params = new gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataRequest();
    gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataRequestIdentifier identifierContainer = new gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataRequestIdentifier();
    identifierContainer.setIdentifier(identifier);
    params.setIdentifier(identifierContainer);
    gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataRequestKeyName keyNameContainer = new gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataRequestKeyName();
    keyNameContainer.setKeyName(keyName);
    params.setKeyName(keyNameContainer);
    gov.nih.nci.cagrid.identifiers.stubs.GetKeyDataResponse boxedResult = portType.getKeyData(params);
    return boxedResult.getKeyNameData();
    }
  }

}
