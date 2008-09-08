package org.cagrid.transfer.context.service.globus.resource;

import gov.nih.nci.cagrid.common.Utils;

import gov.nih.nci.cagrid.advertisement.AdvertisementClient;
import gov.nih.nci.cagrid.advertisement.exceptions.UnregistrationException;

import org.cagrid.transfer.context.common.TransferServiceContextConstants;
import org.cagrid.transfer.context.stubs.TransferServiceContextResourceProperties;
import org.cagrid.transfer.service.TransferServiceConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.namespace.QName;

import gov.nih.nci.cagrid.introduce.servicetools.FilePersistenceHelper;
import gov.nih.nci.cagrid.introduce.servicetools.PersistenceHelper;
import gov.nih.nci.cagrid.introduce.servicetools.ReflectionResource;

import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.mds.aggregator.types.AggregatorConfig;
import org.globus.mds.aggregator.types.AggregatorContent;
import org.globus.mds.aggregator.types.GetMultipleResourcePropertiesPollType;
import org.globus.mds.servicegroup.client.ServiceGroupRegistrationParameters;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.Constants;
import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.RemoveCallback;
import org.globus.wsrf.PersistenceCallback;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.ResourceIdentifier;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceLifetime;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.config.ContainerConfig;
import org.globus.wsrf.container.ServiceHost;
import org.globus.wsrf.encoding.DeserializationException;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.impl.ReflectionResourceProperty;
import org.globus.wsrf.impl.SimpleResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertyMetaData;
import org.globus.wsrf.impl.SimpleResourcePropertySet;
import org.globus.wsrf.impl.security.descriptor.ResourceSecurityDescriptor;
import org.globus.wsrf.impl.servicegroup.client.ServiceGroupRegistrationClient;
import org.globus.wsrf.jndi.Initializable;
import org.globus.wsrf.security.SecureResource;
import org.globus.wsrf.utils.AddressingUtils;

import org.globus.wsrf.Topic;
import org.globus.wsrf.TopicList;
import org.globus.wsrf.TopicListAccessor;
import org.globus.wsrf.utils.SubscriptionPersistenceUtils;

import org.globus.wsrf.impl.ResourcePropertyTopic;
import org.globus.wsrf.impl.SimpleTopicList;
import org.oasis.wsrf.lifetime.TerminationNotification;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class is the base class of the resource type created for this service.
 * It contains accessor and utility methods for managing any resource properties
 * of these resource as well as code for registering any properties selected
 * to the index service.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public abstract class TransferServiceContextResourceBase extends ReflectionResource implements Resource
                                                  ,PersistenceCallback
                                                  ,TopicListAccessor
                                                  ,SecureResource
                                                  ,RemoveCallback
                                                  {

	static final Log logger = LogFactory.getLog(TransferServiceContextResourceBase.class);

	private TransferServiceContextResourceConfiguration configuration;
	private ResourceSecurityDescriptor desc;
	private ResourceKey key;

	// this can be used to cancel the registration renewal
    private AdvertisementClient registrationClient;
    
    private URL baseURL;
    //used to persist the resource properties
    private PersistenceHelper resourcePropertyPersistenceHelper = null;
    //used to persist notifications
    private FilePersistenceHelper resourcePersistenceHelper = null;
    private TopicList topicList;
    private boolean beingLoaded = false;
    
    public TransferServiceContextResourceBase() {
        try {
            resourcePropertyPersistenceHelper = new gov.nih.nci.cagrid.introduce.servicetools.XmlPersistenceHelper(TransferServiceContextResourceProperties.class,TransferServiceConfiguration.getConfiguration());
            resourcePersistenceHelper = new FilePersistenceHelper(this.getClass(),TransferServiceConfiguration.getConfiguration(),".resource");
        } catch (Exception ex) {
            logger.warn("Unable to initialize resource properties persistence helper", ex);
        }
    }


	/**
	 * @see org.globus.wsrf.jndi.Initializable#initialize()
	 */
	public void initialize(Object resourceBean,
                           QName resourceElementQName,
                           Object id) throws ResourceException {
                           
        // Call the super initialize on the ReflectionResource                  
	    super.initialize(resourceBean,resourceElementQName,id);
		this.desc = null;
		this.topicList = new SimpleTopicList(this);

        // create the topics for each resource property
        Iterator it = getResourcePropertySet().iterator();
        List newTopicProps = new ArrayList();
        while(it.hasNext()){
            ResourceProperty prop = (ResourceProperty)it.next();
            prop.getMetaData().getName();
            prop = new ResourcePropertyTopic(prop);
            this.topicList.addTopic((Topic)prop);
            newTopicProps.add(prop);
        }
        // replace the non topic properties with the topic properties
        Iterator newTopicIt = newTopicProps.iterator();
        while(newTopicIt.hasNext()){
            ResourceProperty prop = (ResourceProperty)newTopicIt.next();
            getResourcePropertySet().remove(prop.getMetaData().getName());
            getResourcePropertySet().add(prop);
        }
        


		// register the service to the index service
		refreshRegistration(true);
		
        //call the first store to persist the resource
        store();
	}
	
	
	
	/**
	 * 
	 * @see org.globus.wsrf.ResourceLifetime#setTerminationTime(java.util.Calendar)
	 */
	public void setTerminationTime(Calendar time) {	
		Topic terminationTopic = ((Topic)getResourcePropertySet().get(TransferServiceContextConstants.TERMINATIONTIME));
        if (terminationTopic != null) {
            TerminationNotification terminationNotification =
                new TerminationNotification();
            terminationNotification.setTerminationTime(time);
            try {
                terminationTopic.notify(terminationNotification);
            } catch(Exception e) {
                logger.error("Unable to send terminationTime notification", e);
            }
        }	
        
		super.setTerminationTime(time);
        //call the first store to persist the resource
        try {
            store();
        } catch (ResourceException e) {
            throw new RuntimeException(e);
        }
	}



	    //Getters/Setters for ResourceProperties
	
	
	public org.cagrid.transfer.descriptor.DataStorageDescriptor getDataStorageDescriptor(){
		return ((TransferServiceContextResourceProperties) getResourceBean()).getDataStorageDescriptor();
	}
	
	public void setDataStorageDescriptor(org.cagrid.transfer.descriptor.DataStorageDescriptor dataStorageDescriptor ) throws ResourceException {
        ResourceProperty prop = getResourcePropertySet().get(TransferServiceContextConstants.DATASTORAGEDESCRIPTOR);
		prop.set(0, dataStorageDescriptor);
        //call the first store to persist the resource
        store();
	}
	


	
    /**
     * Sets the security descriptor for this resource.  The default resource
     * security will be null so it will fall back to method level then service
     * level security.  If you want to protect this particular instance of this
     * resource then provide a resource security descriptor to this resource
     * through this method.
     */
	public void setSecurityDescriptor(ResourceSecurityDescriptor desc) {
		this.desc = desc;
	}
	
	
	public ResourceSecurityDescriptor getSecurityDescriptor() {
		return this.desc;
	}  

	
	public TransferServiceContextResourceConfiguration getConfiguration() {
		if (this.configuration != null) {
			return this.configuration;
		}
		MessageContext ctx = MessageContext.getCurrentContext();

		//TODO: hardcoded for now to enable no conflicts with using services from other contexts
		String servicePath = "/cagrid/TransferServiceContext";

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/configuration";
		logger.debug("Will read configuration from jndi name: " + jndiName);
		try {
			Context initialContext = new InitialContext();
			this.configuration = (TransferServiceContextResourceConfiguration) initialContext.lookup(jndiName);
		} catch (Exception e) {
			logger.error("when performing JNDI lookup for " + jndiName + ": " + e, e);
		}

		return this.configuration;
	}


    /**
     * This checks the configuration file, and attempts to register to the
     * IndexService if shouldPerformRegistration==true. It will first read the
     * current container URL, and compare it against the saved value. If the
     * value exists, it will only try to reregister if the values are different.
     * This exists to handle fixing the registration URL which may be incorrect
     * during initialization, then later corrected during invocation. The
     * existence of baseURL does not imply successful registration (a non-null
     * registrationClient does). We will only attempt to reregister when the URL
     * changes (to prevent attempting registration with each invocation if there
     * is a configuration problem).
     */
    public void refreshRegistration(boolean forceRefresh) {
        if (getConfiguration().shouldPerformRegistration()) {

            // first check to see if there are any resource properties that
            // require registration
            ResourceContext ctx;
            try {
                MessageContext msgContext = MessageContext.getCurrentContext();
                if (msgContext == null) {
                    logger.error("Unable to determine message context!");
                    return;
                }

                ctx = ResourceContext.getResourceContext(msgContext);
            } catch (ResourceContextException e) {
                logger.error("Could not get ResourceContext: " + e, e);
                return;
            }
            EndpointReferenceType epr;
            try {
               String transportURL = (String) ctx.getProperty(org.apache.axis.MessageContext.TRANS_URL);
	           transportURL = transportURL.substring(0,transportURL.lastIndexOf('/') +1 );
	           transportURL += "TransferServiceContext";
			   epr = AddressingUtils.createEndpointReference(transportURL, getResourceKey());
            } catch (Exception e) {
                logger.error("Could not form EPR: " + e, e);
                return;
            }

            ServiceGroupRegistrationParameters params = null;

            File registrationFile = new File(ContainerConfig.getBaseDirectory() + File.separator
                + getConfiguration().getRegistrationTemplateFile());

            if (registrationFile.exists() && registrationFile.canRead()) {
                logger.debug("Loading registration argumentsrmation from:" + registrationFile);

                try {
                    params = ServiceGroupRegistrationClient.readParams(registrationFile.getAbsolutePath());
                } catch (Exception e) {
                    logger.error("Unable to read registration file:" + registrationFile, e);
                }

                // set our service's EPR as the registrant, or use the specified
                // value
                EndpointReferenceType registrantEpr = params.getRegistrantEPR();
                if (registrantEpr == null) {
                    params.setRegistrantEPR(epr);
                }

            } else {
                logger.error("Unable to read registration file:" + registrationFile);
            }

            if (params != null) {

                AggregatorContent content = (AggregatorContent) params.getContent();
                AggregatorConfig config = content.getAggregatorConfig();
                MessageElement[] elements = config.get_any();
                GetMultipleResourcePropertiesPollType pollType = null;
                try {
                    pollType = (GetMultipleResourcePropertiesPollType) ObjectDeserializer.toObject(elements[0],
                        GetMultipleResourcePropertiesPollType.class);
                } catch (DeserializationException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

 
                if (pollType != null) {
                    
                    // if there are properties names that need to be registered then
                    // register them to the index service
                    if (pollType.getResourcePropertyNames()!=null && pollType.getResourcePropertyNames().length != 0) {

                        URL currentContainerURL = null;
                        try {
                            currentContainerURL = ServiceHost.getBaseURL();
                        } catch (IOException e) {
                            logger.error("Unable to determine container's URL!  Skipping registration.", e);
                            return;
                        }

                        if (this.baseURL != null) {
                            // we've tried to register before (or we are being
                            // forced to
                            // retry)
                            // do a string comparison as we don't want to do DNS
                            // lookups
                            // for comparison
                            if (forceRefresh || !this.baseURL.equals(currentContainerURL)) {
                                // we've tried to register before, and we have a
                                // different
                                // URL now.. so cancel the old registration (if
                                // it
                                // exists),
                                // and try to redo it.
                                if (registrationClient != null) {
                                    try {
                                        this.registrationClient.unregister();
                                    } catch (UnregistrationException e) {
                                        logger
                                            .error("Problem unregistering existing registration:" + e.getMessage(), e);
                                    }
                                }

                                // save the new value
                                this.baseURL = currentContainerURL;
                                logger.info("Refreshing existing registration [container URL=" + this.baseURL + "].");
                            } else {
                                // URLs are the same (and we weren't forced), so
                                // don't
                                // try
                                // to reregister
                                return;
                            }

                        } else {
                            // we've never saved the baseURL (and therefore
                            // haven't
                            // tried to
                            // register)
                            this.baseURL = currentContainerURL;
                            logger.info("Attempting registration for the first time[container URL=" + this.baseURL
                                + "].");
                        }

                        try {
                            // perform the registration for this service
                            this.registrationClient = new AdvertisementClient(params);
                            this.registrationClient.register();

                        } catch (Exception e) {
                            logger.error("Exception when trying to register service (" + epr + "): " + e, e);
                        }
                    } else {
                        logger.info("No resource properties to register for service (" + epr + ")");
                    }
                } else {
                    logger.warn("Registration file deserialized with no poll type (" + epr + ")");
                }
            } else {
                logger.warn("Registration file deserialized with returned null SeviceGroupParams");
            }
        } else {
            logger.info("Skipping registration.");
        }
    }
    
    

	public ResourceKey getResourceKey(){
	    return this.key;
	}
	
	protected void setResourceKey(ResourceKey key){
	    this.key = key;
	}
	

    public TopicList getTopicList() {
        return this.topicList;
    }

    public void remove() throws ResourceException {     
		resourcePropertyPersistenceHelper.remove(this);
    }


    public void load(ResourceKey resourceKey) throws ResourceException, NoSuchResourceException, InvalidResourceKeyException {
	  beingLoaded = true;
       //first we will recover the resource properties and initialize the resource
	   TransferServiceContextResourceProperties props = (TransferServiceContextResourceProperties)resourcePropertyPersistenceHelper.load(TransferServiceContextResourceProperties.class, resourceKey.getValue());
       this.initialize(props, TransferServiceContextConstants.RESOURCE_PROPERTY_SET, resourceKey.getValue());
       
        //next we will recover the resource itself
        File file = resourcePersistenceHelper.getKeyAsFile(this.getClass(), resourceKey.getValue());
        if (!file.exists()) {
            beingLoaded = false;
            throw new NoSuchResourceException();
        }
        FileInputStream fis = null;
        int value = 0;
        try {
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SubscriptionPersistenceUtils.loadSubscriptionListeners(
                this.getTopicList(), ois);
        } catch (Exception e) {
            beingLoaded = false;
            throw new ResourceException("Failed to load resource", e);
        } finally {
            if (fis != null) {
                try { fis.close(); } catch (Exception ee) {}
            }
        } 
       
       beingLoaded = false;
    }


    public void store() throws ResourceException {
      if(!beingLoaded){
        //store the resource properties
        resourcePropertyPersistenceHelper.store(this);
        
        FileOutputStream fos = null;
        File tmpFile = null;

        try {
            tmpFile = File.createTempFile(
                this.getClass().getName(), ".tmp",
                resourcePersistenceHelper.getStorageDirectory());
            fos = new FileOutputStream(tmpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            SubscriptionPersistenceUtils.storeSubscriptionListeners(
                this.getTopicList(), oos);
        } catch (Exception e) {
            if (tmpFile != null) {
                tmpFile.delete();
            }
            throw new ResourceException("Failed to store resource", e);
        } finally {
            if (fos != null) {
                try { fos.close();} catch (Exception ee) {}
            }
        }

        File file = resourcePersistenceHelper.getKeyAsFile(this.getClass(), getID());
        if (file.exists()) {
            file.delete();
        }
        if (!tmpFile.renameTo(file)) {
            tmpFile.delete();
            throw new ResourceException("Failed to store resource");
        }
        }
    }
	
	
}