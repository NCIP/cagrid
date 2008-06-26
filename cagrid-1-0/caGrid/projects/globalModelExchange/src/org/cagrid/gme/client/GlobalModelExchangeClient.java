package org.cagrid.gme.client;

import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

import java.io.InputStream;
import java.rmi.RemoteException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.gme.common.GlobalModelExchangeI;
import org.cagrid.gme.stubs.GlobalModelExchangePortType;
import org.cagrid.gme.stubs.service.GlobalModelExchangeServiceAddressingLocator;
import org.globus.gsi.GlobusCredential;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE METHODS. This
 * client is generated automatically by Introduce to provide a clean unwrapped
 * API to the service. On construction the class instance will contact the
 * remote service and retrieve it's security metadata description which it will
 * use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.0
 */
public class GlobalModelExchangeClient extends ServiceSecurityClient implements GlobalModelExchangeI {
    protected GlobalModelExchangePortType portType;
    protected static Log LOG = LogFactory.getLog(GlobalModelExchangeClient.class.getName());

    private Object portTypeMutex;

    public GlobalModelExchangeClient(String url) throws MalformedURIException, RemoteException {
        this(url, null);
    }

    public GlobalModelExchangeClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(url, proxy);
        initialize();
    }

    public GlobalModelExchangeClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr, null);
    }

    public GlobalModelExchangeClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException,
        RemoteException {
        super(epr, proxy);
        initialize();
    }

    private void initialize() throws RemoteException {
        this.portTypeMutex = new Object();
        this.portType = createPortType();
    }

    private GlobalModelExchangePortType createPortType() throws RemoteException {

        GlobalModelExchangeServiceAddressingLocator locator = new GlobalModelExchangeServiceAddressingLocator();
        // attempt to load our context sensitive wsdd file
        InputStream resourceAsStream = getClass().getResourceAsStream("client-config.wsdd");
        if (resourceAsStream != null) {
            // we found it, so tell axis to configure an engine to use it
            EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
            // set the engine of the locator
            locator.setEngine(new AxisClient(engineConfig));
        }
        GlobalModelExchangePortType port = null;
        try {
            port = locator.getGlobalModelExchangePortTypePort(getEndpointReference());
        } catch (Exception e) {
            throw new RemoteException("Unable to locate portType:" + e.getMessage(), e);
        }

        return port;
    }

    public static void usage() {
        System.out.println(GlobalModelExchangeClient.class.getName() + " -url <service url>");
    }

    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 2)) {
                if (args[0].equals("-url")) {
                    GlobalModelExchangeClient client = new GlobalModelExchangeClient(args[1]);
                    // place client calls here if you want to use this main as a
                    // test....

                    // client.addSchema(null);

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

  public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getServiceSecurityMetadata");
    gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest params = new gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest();
    gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse boxedResult = portType.getServiceSecurityMetadata(params);
    return boxedResult.getServiceSecurityMetadata();
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

  public void publishSchemas(org.cagrid.gme.domain.XMLSchema[] schemas) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"publishSchemas");
    org.cagrid.gme.stubs.PublishSchemasRequest params = new org.cagrid.gme.stubs.PublishSchemasRequest();
    org.cagrid.gme.stubs.PublishSchemasRequestSchemas schemasContainer = new org.cagrid.gme.stubs.PublishSchemasRequestSchemas();
    schemasContainer.setXMLSchema(schemas);
    params.setSchemas(schemasContainer);
    org.cagrid.gme.stubs.PublishSchemasResponse boxedResult = portType.publishSchemas(params);
    }
  }

}
