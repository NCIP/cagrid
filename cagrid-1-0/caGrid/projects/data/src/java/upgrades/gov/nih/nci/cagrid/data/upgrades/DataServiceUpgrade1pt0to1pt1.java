package gov.nih.nci.cagrid.data.upgrades;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.data.DataServiceConstants;
import gov.nih.nci.cagrid.data.cql.CQLQueryProcessor;
import gov.nih.nci.cagrid.data.extension.Data;
import gov.nih.nci.cagrid.data.utilities.CastorMappingUtil;
import gov.nih.nci.cagrid.data.utilities.WsddUtil;
import gov.nih.nci.cagrid.introduce.IntroduceConstants;
import gov.nih.nci.cagrid.introduce.beans.ServiceDescription;
import gov.nih.nci.cagrid.introduce.beans.extension.ExtensionType;
import gov.nih.nci.cagrid.introduce.beans.extension.ExtensionTypeExtensionData;
import gov.nih.nci.cagrid.introduce.beans.service.ServiceType;
import gov.nih.nci.cagrid.introduce.common.CommonTools;
import gov.nih.nci.cagrid.introduce.common.FileFilters;
import gov.nih.nci.cagrid.introduce.extension.utils.AxisJdomUtils;
import gov.nih.nci.cagrid.introduce.extension.utils.ExtensionUtilities;
import gov.nih.nci.cagrid.introduce.info.ServiceInformation;
import gov.nih.nci.cagrid.introduce.upgrade.ExtensionUpgraderBase;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import org.apache.axis.message.MessageElement;
import org.jdom.Element;
import org.jdom.JDOMException;


/**
 * DataServiceUpgrade1pt0to1pt1 Utility to upgrade a 1.0 data service to 1.1
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A> *
 * @created Feb 19, 2007
 * @version $Id: DataServiceUpgrade1pt0to1pt1.java,v 1.1 2007/02/19 21:52:52
 *          dervin Exp $
 */
public class DataServiceUpgrade1pt0to1pt1 extends ExtensionUpgraderBase {

	public DataServiceUpgrade1pt0to1pt1(ExtensionType extensionType, ServiceDescription serviceDescription,
		String servicePath, String fromVersion, String toVersion) {
		super(extensionType, serviceDescription, servicePath, fromVersion, toVersion);
	}


	protected void upgrade() throws Exception {
		// ensure we're upgrading appropriatly
		validateUpgrade();
		// get the extension data in raw form
		Element extensionData = getExtensionDataElement();
		// fix the cadsr information block
		setCadsrInformation(extensionData);
		// move the configuration for the CQL query processor into
		// the service properties and remove it from the extension data
		reconfigureCqlQueryProcessor(extensionData);
		// update the data service libraries
		updateLibraries();
		// change the version number
		setCurrentExtensionVersion();
		// fix up the castor mapping location
		moveCastorMappingFile();
		// store the modified extension data back into the service model
		setExtensionDataElement(extensionData);
	}


	private void validateUpgrade() throws UpgradeException {
		if (!((getFromVersion() == null) || getFromVersion().equals("1.0"))) {
			throw new UpgradeException(getClass().getName() + " upgrades FROM 1.0 TO 1.1, found FROM = "
				+ getFromVersion());
		}
		if (!getToVersion().equals("1.1")) {
			throw new UpgradeException(getClass().getName() + " upgrades FROM 1.0 TO 1.1, found TO = " + getToVersion());
		}
		String currentVersion = getExtensionType().getVersion();
		if (!((currentVersion == null) || currentVersion.equals("1.0"))) {
			throw new UpgradeException(getClass().getName() + " upgrades FROM 1.0 TO 1.1, current version found is "
				+ currentVersion);
		}
	}


	private void updateLibraries() throws UpgradeException {
		FileFilter dataLibFilter = new FileFilter() {
			public boolean accept(File name) {
				String filename = name.getName();
				return filename.startsWith("caGrid-1.0-data-") && filename.endsWith(".jar");
			}
		};
		// locate the old data service libs in the service
		File serviceLibDir = new File(getServicePath() + File.separator + "lib");
		File[] serviceDataLibs = serviceLibDir.listFiles(dataLibFilter);
		// delete the old libraries
		for (int i = 0; i < serviceDataLibs.length; i++) {
			serviceDataLibs[i].delete();
		}
		// copy new libraries in
		File buildLibDir = new File(".." + File.separator + "data" + File.separator + "build" + File.separator + "lib");
		File[] dataLibs = buildLibDir.listFiles(dataLibFilter);
		File[] outLibs = new File[dataLibs.length];
		for (int i = 0; i < dataLibs.length; i++) {
			File out = new File(serviceLibDir.getAbsolutePath() + File.separator + dataLibs[i].getName());
			try {
				Utils.copyFile(dataLibs[i], out);
			} catch (IOException ex) {
				throw new UpgradeException("Error copying new data service library: " + ex.getMessage(), ex);
			}
			outLibs[i] = out;
		}
		File classpathFile = new File(getServicePath() + File.separator + ".classpath");
		try {
			ExtensionUtilities.syncEclipseClasspath(classpathFile, outLibs);
		} catch (Exception ex) {
			throw new UpgradeException("Error updating eclipse .classpath file: " + ex.getMessage(), ex);
		}
	}


	private void moveCastorMappingFile() throws UpgradeException {
		File oldCastorMapping = new File(getServicePath() + File.separator + "xml-mapping.xml");
		if (oldCastorMapping.exists()) {
			Properties introduceProperties = new Properties();
			try {
				introduceProperties
					.load(new FileInputStream(getServicePath() + File.separator + "introduce.properties"));
			} catch (IOException ex) {
				throw new UpgradeException("Error loading introduce properties for this service: " + ex.getMessage(),
					ex);
			}
			ServiceInformation serviceInfo = new ServiceInformation(getServiceDescription(), introduceProperties,
				new File(getServicePath()));
			File newCastorMapping = new File(CastorMappingUtil.getCustomCastorMappingFileName(serviceInfo));
			try {
				Utils.copyFile(oldCastorMapping, newCastorMapping);
			} catch (IOException ex) {
				throw new UpgradeException("Error moving castor mapping file: " + ex.getMessage(), ex);
			}
			// fix the server-config.wsdd file's castrorMapping parameter
			File serverConfigFile = new File(getServicePath() + File.separator + "server-config.wsdd");
			try {
				WsddUtil.setServiceParameter(serverConfigFile.getAbsolutePath(), serviceInfo.getServices()
					.getService(0).getName(), DataServiceConstants.CASTOR_MAPPING_WSDD_PARAMETER, CastorMappingUtil
					.getCustomCastorMappingName(serviceInfo));
			} catch (Exception ex) {
				throw new UpgradeException("Error setting castor mapping parameter in server-config.wsdd: "
					+ ex.getMessage(), ex);
			}
			// fix the client config file
			String mainServiceName = serviceInfo.getIntroduceServiceProperties().getProperty(
				IntroduceConstants.INTRODUCE_SKELETON_SERVICE_NAME);
			ServiceType mainService = CommonTools.getService(serviceInfo.getServices(), mainServiceName);
			String servicePackageName = mainService.getPackageName();
			String packageDir = servicePackageName.replace('.', File.separatorChar);
			File clientConfigFile = new File(getServicePath() + File.separator + "src" + File.separator + packageDir
				+ File.separator + "client" + File.separator + "client-config.wsdd");
			try {
				WsddUtil.setGlobalClientParameter(clientConfigFile.getAbsolutePath(),
					DataServiceConstants.CASTOR_MAPPING_WSDD_PARAMETER, CastorMappingUtil
						.getCustomCastorMappingName(serviceInfo));
			} catch (Exception ex) {
				throw new UpgradeException("Error setting castor mapping parameter in client-config.wsdd: "
					+ ex.getMessage(), ex);
			}
			oldCastorMapping.delete();
		}
	}


	private void setCurrentExtensionVersion() throws UpgradeException {
		getExtensionType().setVersion("1.1");
	}


	private void reconfigureCqlQueryProcessor(Element extensionData) throws UpgradeException {
		// service properties now contain CQL Query Processor configuration
		// get the current config properties out of the data element
		Element procConfig = extensionData.getChild("CQLProcessorConfig", extensionData.getNamespace());
		Properties configuredProps = new Properties();
		Iterator configuredPropElemIter = procConfig.getChildren("Property", procConfig.getNamespace()).iterator();
		while (configuredPropElemIter.hasNext()) {
			Element propElem = (Element) configuredPropElemIter.next();
			String key = propElem.getAttributeValue("name");
			String value = propElem.getAttributeValue("value");
			configuredProps.setProperty(key, value);
		}
		// remove all the processor config properties from the model
		extensionData.removeChild("CQLProcessorConfig", extensionData.getNamespace());

		// locate the query processor class property
		String queryProcessorClassName = null;
		try {
			queryProcessorClassName = CommonTools.getServicePropertyValue(getServiceDescription(),
				"queryProcessorClass");
		} catch (Exception ex) {
			throw new UpgradeException("Error getting query processor class name: " + ex.getMessage(), ex);
		}
		// load the query processor so we can ask it some questions
		CQLQueryProcessor proc = loadQueryProcessorInstance(queryProcessorClassName);
		// get the properties for the query processor
		Properties qpProps = proc.getRequiredParameters();
		// set the user configured properties
		Enumeration keyEnum = qpProps.keys();
		while (keyEnum.hasMoreElements()) {
			String key = (String) keyEnum.nextElement();
			String value = qpProps.getProperty(key);
			if (configuredProps.containsKey(key)) {
				value = configuredProps.getProperty(key);
			}
			// add the property to the service properties
			String extendedKey = "cqlQueryProcessorConfig_" + key;
			CommonTools.setServiceProperty(getServiceDescription(), extendedKey, value, false, "");
		}
	}


	private CQLQueryProcessor loadQueryProcessorInstance(String queryProcessorClassName) throws UpgradeException {
		// reflect load the query processor (this should live in the service's
		// lib dir)
		File libDir = new File(getServicePath() + File.separator + "lib");
		File[] libs = libDir.listFiles(new FileFilters.JarFileFilter());
		URL[] libUrls = new URL[libs.length];
		try {
			for (int i = 0; i < libs.length; i++) {
				libUrls[i] = libs[i].toURL();
			}
		} catch (MalformedURLException ex) {
			throw new UpgradeException("Error converting library path to URL: " + ex.getMessage(), ex);
		}
		ClassLoader libLoader = new URLClassLoader(libUrls, Thread.currentThread().getContextClassLoader());
		CQLQueryProcessor proc = null;
		try {
			Class qpClass = libLoader.loadClass(queryProcessorClassName);
			proc = (CQLQueryProcessor) qpClass.newInstance();
		} catch (Exception ex) {
			throw new UpgradeException("Error instantiating query processor class: " + ex.getMessage(), ex);
		}
		return proc;
	}


	private void setCadsrInformation(Element extensionData) {
		// additional libraries / jar names elements are unchanged
		// get cadsr information
		Element cadsrInfo = extensionData.getChild("CadsrInformation", extensionData.getNamespace());
		// now we have a noDomainModel boolean flag...
		boolean hasCadsrUrl = cadsrInfo.getAttributeValue("serviceUrl") != null;
		boolean usingSuppliedModel = (cadsrInfo.getAttributeValue("useSuppliedModel") != null)
			&& cadsrInfo.getAttributeValue("useSuppliedModel").equals("true");
		boolean noDomainModel = (!hasCadsrUrl && !usingSuppliedModel);
		cadsrInfo.setAttribute("noDomainModel", String.valueOf(noDomainModel));
	}


	private void setExtensionDataElement(Element extensionData) throws UpgradeException {
		ExtensionTypeExtensionData ext = getExtensionType().getExtensionData();
		MessageElement rawExtensionData = null;
		try {
			rawExtensionData = AxisJdomUtils.fromElement(extensionData);
		} catch (JDOMException ex) {
			throw new UpgradeException("Error converting extension data to Axis MessageElement: " + ex.getMessage(), ex);
		}
		ext.set_any(new MessageElement[]{rawExtensionData});
	}


	private Element getExtensionDataElement() throws UpgradeException {
		MessageElement[] anys = getExtensionType().getExtensionData().get_any();
		MessageElement rawDataElement = null;
		for (int i = 0; (anys != null) && (i < anys.length); i++) {
			if (anys[i].getQName().equals(Data.getTypeDesc().getXmlType())) {
				rawDataElement = anys[i];
				break;
			}
		}
		if (rawDataElement == null) {
			throw new UpgradeException("No extension data was found for the data service extension");
		}
		Element extensionDataElement = AxisJdomUtils.fromMessageElement(rawDataElement);
		return extensionDataElement;
	}
}
