package gov.nih.nci.cagrid.introduce.extensions.metadata.codegen;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.introduce.beans.extension.ServiceExtensionDescriptionType;
import gov.nih.nci.cagrid.introduce.beans.service.ServiceType;
import gov.nih.nci.cagrid.introduce.extension.CodegenExtensionException;
import gov.nih.nci.cagrid.introduce.extension.CodegenExtensionPostProcessor;
import gov.nih.nci.cagrid.introduce.extensions.metadata.constants.MetadataConstants;
import gov.nih.nci.cagrid.introduce.info.ServiceInformation;
import gov.nih.nci.cagrid.metadata.ServiceMetadata;
import gov.nih.nci.cagrid.metadata.ServiceMetadataServiceDescription;
import gov.nih.nci.cagrid.metadata.service.Service;
import gov.nih.nci.cagrid.metadata.service.ServiceContext;
import gov.nih.nci.cagrid.metadata.service.ServiceServiceContextCollection;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Creates a metadata instance file. Only creates/modifies the Service portion,
 * will leave any existing other aspects (ResearchCenter) intact.
 * 
 * @author oster
 * 
 */
public class MetadataCodegenPostProcessor implements CodegenExtensionPostProcessor {
	private static final String FILENAME = "serviceMetadata.xml";
	private static final String MAIN_RF_TYPE = "main";

	protected static Log LOG = LogFactory.getLog(MetadataCodegenPostProcessor.class.getName());


	public void postCodegen(ServiceExtensionDescriptionType desc, ServiceInformation info)
		throws CodegenExtensionException {
		ServiceMetadata metadata = null;

		// read jndi file to determine where to store instance document? or just
		// hard code it to etc/serviceMetadata.xml?
		String filename = info.getBaseDirectory() + File.separator + "etc" + File.separator + FILENAME;

		// look if the file already exists, and load it in, in case other
		// aspects of it (such as cancer center info) are set by something else
		File mdFile = new File(filename);
		if (mdFile.exists() && mdFile.canRead()) {
			try {
				metadata = (ServiceMetadata) Utils.deserializeDocument(filename, ServiceMetadata.class);
			} catch (Exception e) {
				LOG.error("Failed to deserialize existing metadata document!  A new one will be created.", e);
			}
		}

		// create a new model if need be, and initialize it
		if (metadata == null) {
			metadata = new ServiceMetadata();
		}
		initializeModel(metadata);

		// shell it without UML informaiton
		populateService(metadata.getServiceDescription().getService(), info);

		// serialize the model
		try {
			Utils.serializeDocument(filename, metadata, MetadataConstants.SERVICE_QNAME);
		} catch (Exception e) {
			throw new CodegenExtensionException("Error serializing metadata document.", e);
		}

	}


	/**
	 * Reads the service model, and builds the metadata
	 * 
	 * @param metadata
	 * @param info
	 * @throws CodegenExtensionException
	 */
	private void populateService(Service service, ServiceInformation info) throws CodegenExtensionException {
		ServiceType services[] = info.getServiceDescriptor().getServices().getService();

		ServiceContext[] newServContexts = new ServiceContext[services.length];

		// build a map based on context names (only for lookup)
		Map contextMap = new HashMap();
		ServiceContext[] existingServiceContexts = service.getServiceContextCollection().getServiceContext();
		if (existingServiceContexts != null) {
			for (int i = 0; i < existingServiceContexts.length; i++) {
				ServiceContext context = existingServiceContexts[i];
				contextMap.put(context.getName(), context);
			}
		}

		// create/edit a service context for each service
		for (int i = 0; i < services.length; i++) {
			ServiceType serv = services[i];

			// find the existing context
			ServiceContext currContext = (ServiceContext) contextMap.get(serv.getName());
			// create a new one if necessary
			if (currContext == null) {
				currContext = new ServiceContext();
			}

			// now edit it
			editServiceContextForService(serv, currContext);
			newServContexts[i] = currContext;

			// use the main service to set some higher level items
			if (serv.getResourceFrameworkType().equals(MAIN_RF_TYPE)) {
				service.setName(serv.getName());

				// set a description
				if (service.getDescription() == null || service.getDescription().trim().equals("")) {
					service.setDescription("The " + service.getName()
						+ " grid service, created with caGrid Introduce, version:"
						+ info.getServiceDescriptor().getIntroduceVersion() + ".");
				}

				// set a version
				if (service.getVersion() == null || service.getVersion().trim().equals("")) {
					// version is introduce's version... should be set elsewhere
					service.setVersion(info.getServiceDescriptor().getIntroduceVersion());
				}
			}
		}

		// replace the old with the new
		service.getServiceContextCollection().setServiceContext(newServContexts);
	}


	/**
	 * 
	 * @param serv
	 * @param currContext
	 */
	private void editServiceContextForService(ServiceType service, ServiceContext serviceContext) {
		serviceContext.setName(service.getName());
		// TODO: make context properties for RPs

		// TODO: make operations

	}


	/**
	 * bootstrap the necessary fields as needed, to avoid null checks
	 * everywhere.
	 * 
	 * @param metadata
	 */
	private static void initializeModel(ServiceMetadata metadata) {
		// every model needs a service desc
		ServiceMetadataServiceDescription desc = metadata.getServiceDescription();
		if (desc == null) {
			desc = new ServiceMetadataServiceDescription();
			metadata.setServiceDescription(desc);
		}

		// every service desc needs a service
		Service serv = desc.getService();
		if (serv == null) {
			serv = new Service();
			desc.setService(serv);
		}

		// every service needs a context coll
		ServiceServiceContextCollection contCol = serv.getServiceContextCollection();
		if (contCol == null) {
			contCol = new ServiceServiceContextCollection();
			serv.setServiceContextCollection(contCol);
		}
	}
}
