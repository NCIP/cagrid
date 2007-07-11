package gov.nih.nci.cagrid.fqp.client;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.common.security.ProxyUtil;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.cagrid.dcql.DCQLQuery;
import gov.nih.nci.cagrid.dcqlresult.DCQLQueryResultsCollection;
import gov.nih.nci.cagrid.dcqlresult.DCQLResult;
import gov.nih.nci.cagrid.fqp.common.FederatedQueryProcessorI;
import gov.nih.nci.cagrid.fqp.results.client.FederatedQueryResultsClient;
import gov.nih.nci.cagrid.fqp.stubs.FederatedQueryProcessorPortType;
import gov.nih.nci.cagrid.fqp.stubs.service.FederatedQueryProcessorServiceAddressingLocator;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;
import gov.nih.nci.cagrid.metadata.security.CommunicationMechanism;
import gov.nih.nci.cagrid.metadata.security.Operation;
import gov.nih.nci.cagrid.metadata.security.ProtectionLevelType;
import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadataOperations;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.axis.utils.ClassUtils;
import org.globus.gsi.GlobusCredential;
import org.globus.wsrf.impl.security.authorization.NoAuthorization;
import org.oasis.wsrf.lifetime.Destroy;


/**
 * This class is autogenerated, DO NOT EDIT. On construction the class instance
 * will contact the remote service and retrieve it's security metadata
 * description which it will use to configure the Stub specifically for each
 * method call.
 * 
 * @created by Introduce Toolkit version 1.0
 */
public class FederatedQueryProcessorClient extends ServiceSecurityClient implements FederatedQueryProcessorI {
    protected FederatedQueryProcessorPortType portType;
    private Object portTypeMutex;


    public FederatedQueryProcessorClient(String url) throws MalformedURIException, RemoteException {
        this(url, null);
    }


    public FederatedQueryProcessorClient(String url, GlobusCredential proxy) throws MalformedURIException,
        RemoteException {
        super(url, proxy);
        initialize();
    }


    public FederatedQueryProcessorClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr, null);
    }


    public FederatedQueryProcessorClient(EndpointReferenceType epr, GlobusCredential proxy)
        throws MalformedURIException, RemoteException {
        super(epr, proxy);
        initialize();
    }


    private void initialize() throws RemoteException {
        this.portTypeMutex = new Object();
        this.portType = createPortType();
    }


    private FederatedQueryProcessorPortType createPortType() throws RemoteException {

        FederatedQueryProcessorServiceAddressingLocator locator = new FederatedQueryProcessorServiceAddressingLocator();
        // attempt to load our context sensitive wsdd file
        InputStream resourceAsStream = ClassUtils.getResourceAsStream(getClass(), "client-config.wsdd");
        if (resourceAsStream != null) {
            // we found it, so tell axis to configure an engine to use it
            EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
            // set the engine of the locator
            locator.setEngine(new AxisClient(engineConfig));
        }
        FederatedQueryProcessorPortType port = null;
        try {
            port = locator.getFederatedQueryProcessorPortTypePort(getEndpointReference());
        } catch (Exception e) {
            throw new RemoteException("Unable to locate portType:" + e.getMessage(), e);
        }

        return port;
    }


    public static void usage() {
        System.out.println(FederatedQueryProcessorClient.class.getName() + " -url <service url> -dcql <DCQL file>");
    }


    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 4)) {
                if (args[0].equals("-url")) {
                    FederatedQueryProcessorClient client = new FederatedQueryProcessorClient(args[1]);
                    // place client calls here if you want to use this main as a
                    // test....

                    if (!args[2].equals("-dcql")) {
                        usage();
                        System.exit(1);
                    }

                    DCQLQuery dcql = (DCQLQuery) Utils.deserializeDocument(args[3], DCQLQuery.class);
                    FederatedQueryResultsClient resultsClilent = client.executeAsynchronously(dcql);

                    Utils.serializeDocument("resultEPR.xml", resultsClilent.getEndpointReference(), new QName(
                        "http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndPointReference"));

                    // hackish... need to subscribe to isComplete RP
                    while (!resultsClilent.isProcessingComplete()) {
                        Thread.sleep(5000);
                        System.out.print(".");
                    }

                    DCQLQueryResultsCollection dcqlResultsCol = resultsClilent.getResults();
                    DCQLResult[] dcqlResults = dcqlResultsCol.getDCQLResult();
                    if (dcqlResults != null) {
                        for (DCQLResult result : dcqlResults) {
                            String targetServiceURL = result.getTargetServiceURL();
                            System.out.println("Got results from:" + targetServiceURL);
                            CQLQueryResults queryResultCollection = result.getCQLQueryResultCollection();
                            CQLQueryResultsIterator iterator = new CQLQueryResultsIterator(queryResultCollection, true);
                            int resultCount = 0;
                            while (iterator.hasNext()) {
                                System.out.println("===== RESULT [" + resultCount++ + "] =====");
                                System.out.println(iterator.next());
                                System.out.println("===== END RESULT=====\n\n");
                            }

                        }
                    } else {
                        System.out.println("Got no results.");
                    }

                    // SetTerminationTime termTime = new SetTerminationTime();
                    // Calendar terminateAt = Calendar.getInstance();
                    // terminateAt.add(Calendar.SECOND, 10);
                    // termTime.setRequestedTerminationTime(terminateAt);
                    //
                    // SetTerminationTimeResponse response =
                    // resultsClilent.setTerminationTime(termTime);
                    //
                    // System.out.println("Current time " +
                    // response.getCurrentTime().getTime());
                    // System.out.println("Requested termination time " +
                    // terminateAt.getTime());
                    // System.out.println("Scheduled termination time " +
                    // response.getNewTerminationTime().getTime());
                    //
                    // boolean terminated = false;
                    // while (!terminated) {
                    // try {
                    // System.out.println("Should terminate in:
                    // "+(response.getNewTerminationTime().getTimeInMillis() -
                    // Calendar
                    // .getInstance().getTimeInMillis()) / 1000 +" seconds.");
                    // dcqlResultsCol = resultsClilent.getResults();
                    // Thread.sleep(1000);
                    //
                    // } catch (RemoteException e) {
                    // System.out.println("Resource has been destroyed");
                    // terminated = true;
                    // }
                    // }

                    resultsClilent.destroy(new Destroy());

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


    // overloaded to not communicate annonymously if credentials are provided
    @Override
    protected void configureStubSecurity(Stub stub, String method) throws RemoteException {

        boolean https = false;
        if (this.epr.getAddress().getScheme().equals("https")) {
            https = true;
        }

        if (method.equals("getServiceSecurityMetadata")) {
            if (https) {
                resetStub(stub);
                stub._setProperty(org.globus.wsrf.security.Constants.GSI_TRANSPORT,
                    org.globus.wsrf.security.Constants.SIGNATURE);
                stub._setProperty(org.globus.wsrf.security.Constants.GSI_ANONYMOUS, Boolean.TRUE);
                stub._setProperty(org.globus.wsrf.security.Constants.AUTHORIZATION,
                    org.globus.wsrf.impl.security.authorization.NoAuthorization.getInstance());
            }
            return;
        }

        if (this.securityMetadata == null) {
            this.operations = new HashMap();
            this.securityMetadata = getServiceSecurityMetadata();
            ServiceSecurityMetadataOperations ssmo = this.securityMetadata.getOperations();
            if (ssmo != null) {
                Operation[] ops = ssmo.getOperation();
                if (ops != null) {
                    for (Operation element : ops) {
                        String lowerMethodName = element.getName().substring(0, 1).toLowerCase()
                            + element.getName().substring(1);
                        this.operations.put(lowerMethodName, element);
                    }
                }
            }

        }
        resetStub(stub);

        CommunicationMechanism serviceDefault = this.securityMetadata.getDefaultCommunicationMechanism();

        CommunicationMechanism mechanism = null;
        if (this.operations.containsKey(method)) {
            Operation o = (Operation) this.operations.get(method);
            mechanism = o.getCommunicationMechanism();
        } else {
            mechanism = serviceDefault;
        }
        boolean anonymousAllowed = true;
        boolean authorizationAllowed = true;
        boolean delegationAllowed = true;
        boolean credentialsAllowed = true;

        if ((https) && (mechanism.getGSITransport() != null)) {
            ProtectionLevelType level = mechanism.getGSITransport().getProtectionLevel();
            if (level != null) {
                if ((level.equals(ProtectionLevelType.privacy)) || (level.equals(ProtectionLevelType.either))) {
                    stub._setProperty(org.globus.wsrf.security.Constants.GSI_TRANSPORT,
                        org.globus.wsrf.security.Constants.ENCRYPTION);
                } else {
                    stub._setProperty(org.globus.wsrf.security.Constants.GSI_TRANSPORT,
                        org.globus.wsrf.security.Constants.SIGNATURE);
                }

            } else {
                stub._setProperty(org.globus.wsrf.security.Constants.GSI_TRANSPORT,
                    org.globus.wsrf.security.Constants.SIGNATURE);
            }
            delegationAllowed = false;

        } else if (https) {
            stub._setProperty(org.globus.wsrf.security.Constants.GSI_TRANSPORT,
                org.globus.wsrf.security.Constants.SIGNATURE);
            delegationAllowed = false;
        } else if (mechanism.getGSISecureConversation() != null) {
            ProtectionLevelType level = mechanism.getGSISecureConversation().getProtectionLevel();
            if (level != null) {
                if ((level.equals(ProtectionLevelType.privacy)) || (level.equals(ProtectionLevelType.either))) {
                    stub._setProperty(org.globus.wsrf.security.Constants.GSI_SEC_CONV,
                        org.globus.wsrf.security.Constants.ENCRYPTION);

                } else {
                    stub._setProperty(org.globus.wsrf.security.Constants.GSI_SEC_CONV,
                        org.globus.wsrf.security.Constants.SIGNATURE);
                }

            } else {
                stub._setProperty(org.globus.wsrf.security.Constants.GSI_SEC_CONV,
                    org.globus.wsrf.security.Constants.ENCRYPTION);
            }

        } else if (mechanism.getGSISecureMessage() != null) {
            ProtectionLevelType level = mechanism.getGSISecureMessage().getProtectionLevel();
            if (level != null) {
                if ((level.equals(ProtectionLevelType.privacy)) || (level.equals(ProtectionLevelType.either))) {
                    stub._setProperty(org.globus.wsrf.security.Constants.GSI_SEC_MSG,
                        org.globus.wsrf.security.Constants.ENCRYPTION);
                } else {
                    stub._setProperty(org.globus.wsrf.security.Constants.GSI_SEC_MSG,
                        org.globus.wsrf.security.Constants.SIGNATURE);
                }

            } else {
                stub._setProperty(org.globus.wsrf.security.Constants.GSI_SEC_MSG,
                    org.globus.wsrf.security.Constants.ENCRYPTION);
            }
            delegationAllowed = false;
            anonymousAllowed = false;
        } else {
            anonymousAllowed = false;
            authorizationAllowed = false;
            delegationAllowed = false;
            credentialsAllowed = false;
        }

        // if a proxy was not programmatically set, try to load the default one
        if ((credentialsAllowed) && (this.proxy == null)) {
            try {
                GlobusCredential cred = ProxyUtil.getDefaultProxy();
                if (cred.getTimeLeft() > 0) {
                    this.proxy = cred;
                }
            } catch (Exception e) {
                // System.out.println("Error loading default proxy: " +
                // e.getMessage());
            }
        }

        // if we either loaded the default proxy or one was passed, let's try to
        // use it, otherwise just connect anonymously
        if ((credentialsAllowed) && (this.proxy != null)) {
            try {
                org.ietf.jgss.GSSCredential gss = new org.globus.gsi.gssapi.GlobusGSSCredentialImpl(this.proxy,
                    org.ietf.jgss.GSSCredential.INITIATE_AND_ACCEPT);
                stub._setProperty(org.globus.axis.gsi.GSIConstants.GSI_CREDENTIALS, gss);
            } catch (org.ietf.jgss.GSSException ex) {
                throw new RemoteException(ex.getMessage());
            }
        } else if (anonymousAllowed) {
            stub._setProperty(org.globus.wsrf.security.Constants.GSI_ANONYMOUS, Boolean.TRUE);
        }

        if (authorizationAllowed) {
            if (this.authorization == null) {
                stub._setProperty(org.globus.wsrf.security.Constants.AUTHORIZATION, NoAuthorization.getInstance());
            } else {
                stub._setProperty(org.globus.wsrf.security.Constants.AUTHORIZATION, getAuthorization());
            }
        }
        if (delegationAllowed) {
            if (getDelegationMode() != null) {
                stub._setProperty(org.globus.axis.gsi.GSIConstants.GSI_MODE, getDelegationMode());
            }
        }
    }


    @Override
    public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata()
        throws RemoteException {
        synchronized (this.portTypeMutex) {
            configureStubSecurity((Stub) this.portType, "getServiceSecurityMetadata");
            gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest params = new gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest();
            gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse boxedResult = this.portType
                .getServiceSecurityMetadata(params);
            return boxedResult.getServiceSecurityMetadata();
        }
    }


    public gov.nih.nci.cagrid.cqlresultset.CQLQueryResults executeAndAggregateResults(
        gov.nih.nci.cagrid.dcql.DCQLQuery query) throws RemoteException,
        gov.nih.nci.cagrid.fqp.stubs.types.FederatedQueryProcessingFault {
        synchronized (this.portTypeMutex) {
            configureStubSecurity((Stub) this.portType, "executeAndAggregateResults");
            gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsRequest params = new gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsRequest();
            gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsRequestQuery();
            queryContainer.setDCQLQuery(query);
            params.setQuery(queryContainer);
            gov.nih.nci.cagrid.fqp.stubs.ExecuteAndAggregateResultsResponse boxedResult = this.portType
                .executeAndAggregateResults(params);
            return boxedResult.getCQLQueryResultCollection();
        }
    }


    public gov.nih.nci.cagrid.dcqlresult.DCQLQueryResultsCollection execute(gov.nih.nci.cagrid.dcql.DCQLQuery query)
        throws RemoteException, gov.nih.nci.cagrid.fqp.stubs.types.FederatedQueryProcessingFault {
        synchronized (this.portTypeMutex) {
            configureStubSecurity((Stub) this.portType, "execute");
            gov.nih.nci.cagrid.fqp.stubs.ExecuteRequest params = new gov.nih.nci.cagrid.fqp.stubs.ExecuteRequest();
            gov.nih.nci.cagrid.fqp.stubs.ExecuteRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.ExecuteRequestQuery();
            queryContainer.setDCQLQuery(query);
            params.setQuery(queryContainer);
            gov.nih.nci.cagrid.fqp.stubs.ExecuteResponse boxedResult = this.portType.execute(params);
            return boxedResult.getDCQLQueryResultsCollection();
        }
    }


    public gov.nih.nci.cagrid.fqp.results.client.FederatedQueryResultsClient executeAsynchronously(
        gov.nih.nci.cagrid.dcql.DCQLQuery query) throws RemoteException,
        org.apache.axis.types.URI.MalformedURIException {
        synchronized (this.portTypeMutex) {
            configureStubSecurity((Stub) this.portType, "executeAsynchronously");
            gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyRequest params = new gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyRequest();
            gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyRequestQuery queryContainer = new gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyRequestQuery();
            queryContainer.setDCQLQuery(query);
            params.setQuery(queryContainer);
            gov.nih.nci.cagrid.fqp.stubs.ExecuteAsynchronouslyResponse boxedResult = this.portType
                .executeAsynchronously(params);
            EndpointReferenceType ref = boxedResult.getFederatedQueryResultsReference().getEndpointReference();
            return new gov.nih.nci.cagrid.fqp.results.client.FederatedQueryResultsClient(ref);
        }
    }

}
