package gov.nih.nci.cagrid.introduce.codegen.utils;

import gov.nih.nci.cagrid.introduce.IntroduceConstants;
import gov.nih.nci.cagrid.introduce.beans.ServiceDescription;
import gov.nih.nci.cagrid.introduce.beans.method.MethodType;
import gov.nih.nci.cagrid.introduce.beans.namespace.NamespaceType;
import gov.nih.nci.cagrid.introduce.beans.service.ServiceType;
import gov.nih.nci.cagrid.introduce.info.ImportInformation;
import gov.nih.nci.cagrid.introduce.info.NamespaceInformation;
import gov.nih.nci.cagrid.introduce.info.SpecificServiceInformation;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.projectmobius.common.XMLUtilities;


/**
 * Templating Utility Functions
 * 
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella </A>
 * @created Jun 8, 2005
 * @version $Id: mobiusEclipseCodeTemplates.xml,v 1.2 2005/04/19 14:58:02 oster
 *          Exp $
 */
public class TemplateUtils {

	/**
	 * Returns the input string with the first character converted to lowercase
	 * 
	 * @param variableName
	 *            string to fix
	 * @return the input string with the first character converted to lowercase
	 */
	public static String lowerCaseFirstCharacter(String variableName) {
		return variableName.substring(0, 1).toLowerCase() + variableName.substring(1);
	}


	/**
	 * Returns the input string with the first character converted to uppercase
	 * 
	 * @param variableName
	 *            string to fix
	 * @return the input string with the first character converted to uppercase
	 */
	public static String upperCaseFirstCharacter(String variableName) {
		return variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
	}


	public static Map buildMasterNamespaceInformationMap(ServiceDescription desc) {
		Map map = new HashMap();
		int namespaceCount = 0;
		if (desc.getNamespaces() != null && desc.getNamespaces().getNamespace() != null) {
			for (int i = 0; i < desc.getNamespaces().getNamespace().length; i++) {
				NamespaceType ntype = desc.getNamespaces().getNamespace(i);
				// add the ns=>prefix entry
				if (!map.containsKey(ntype.getNamespace())) {
					if (ntype.getNamespace().equals(IntroduceConstants.W3CNAMESPACE)) {
						map.put(ntype.getNamespace(), new NamespaceInformation(ntype,
							IntroduceConstants.W3CNAMESPACE_PREFIX));
					} else {
						map.put(ntype.getNamespace(), new NamespaceInformation(ntype, "ns" + namespaceCount++));
					}
				}
			}
		}

		return map;
	}


	public static void addImportedOperationToService(MethodType method, SpecificServiceInformation serviceInfo)
		throws Exception {

		String fromDocFile = serviceInfo.getBaseDirectory().getAbsolutePath()
			+ File.separator
			+ "schema"
			+ File.separator
			+ serviceInfo.getIntroduceServiceProperties().getProperty(
				IntroduceConstants.INTRODUCE_SKELETON_SERVICE_NAME) + File.separator
			+ method.getImportInformation().getWsdlFile();
		if (!(new File(fromDocFile).exists())) {
			// try From Globus Location
			fromDocFile = gov.nih.nci.cagrid.introduce.ResourceManager
				.getConfigurationProperty(IntroduceConstants.GLOBUS_LOCATION)
				+ File.separator
				+ "schema"
				+ File.separator
				+ "wsrf"
				+ File.separator
				+ method.getImportInformation().getWsdlFile();
		}
		if (!(new File(fromDocFile).exists())) {
			throw new Exception("Cannot locate WSDL file: " + method.getImportInformation().getWsdlFile()
				+ " to import from for Method: " + method.getName());
		}

		// parse the wsdl and get the operation text.....
		Document fromDoc = null;
		Document toDoc = null;
		try {
			fromDoc = XMLUtilities.fileNameToDocument(fromDocFile);
			toDoc = XMLUtilities.fileNameToDocument(serviceInfo.getBaseDirectory().getAbsolutePath()
				+ File.separator
				+ "schema"
				+ File.separator
				+ serviceInfo.getIntroduceServiceProperties().getProperty(
					IntroduceConstants.INTRODUCE_SKELETON_SERVICE_NAME) + File.separator
				+ serviceInfo.getService().getName() + ".wsdl");
			List portTypes = fromDoc.getRootElement().getChildren("portType", fromDoc.getRootElement().getNamespace());
			for (int i = 0; i < portTypes.size(); i++) {
				Element el = (Element) portTypes.get(i);
				if (el.getAttributeValue("name").equals(method.getImportInformation().getPortTypeName())) {
					List operations = el.getChildren("operation", fromDoc.getRootElement().getNamespace());
					for (int j = 0; j < operations.size(); j++) {
						Element opEl = (Element) operations.get(j);
						if (opEl.getAttributeValue("name").equals(method.getName())) {
							// need to detach the el and add it to the service
							// which will be using it...
							List toportTypes = toDoc.getRootElement().getChildren("portType",
								toDoc.getRootElement().getNamespace());
							for (int i2 = 0; i2 < toportTypes.size(); i2++) {
								Element el2 = (Element) toportTypes.get(i2);
								if (el2.getAttributeValue("name").equals(
									serviceInfo.getService().getName() + "PortType")) {
									// found the right one... add to here
									Element copEl = (Element) opEl.clone();
									List copElChildren = copEl.getChildren();
									for (int childi = 0; childi < copElChildren.size(); childi++) {
										Element copElChild = (Element) copElChildren.get(childi);
										String messageString = copElChild.getAttributeValue("message");
										System.out.println("Looking for namespace prefix for message " + messageString);
										Namespace ns = null;
										String prefix = "";
										String message = "";
										if (messageString.indexOf(":") >= 0) {
											prefix = messageString.substring(0, messageString.indexOf(":"));
											message = messageString.substring(messageString.indexOf(":") + 1);
											ns = fromDoc.getRootElement().getNamespace(prefix);
										} else {
											message = messageString;
											ns = fromDoc.getRootElement().getNamespace();
										}
										List nslist = toDoc.getRootElement().getAdditionalNamespaces();
										for (int nsli = 0; nsli < nslist.size(); nsli++) {
											Namespace tempns = (Namespace) nslist.get(nsli);
											if (tempns.getURI().equals(ns.getURI())) {
												System.out.println("Setting message " + message + " nsPrefix: "
													+ tempns.getPrefix());
												copElChild.setAttribute("message", tempns.getPrefix() + ":" + message);
												break;
											}
										}
									}
									el2.addContent(copEl);
									break;
								}
							}
							break;
						}
					}
					break;
				}
			}
			FileWriter fw = new FileWriter(serviceInfo.getBaseDirectory().getAbsolutePath()
				+ File.separator
				+ "schema"
				+ File.separator
				+ serviceInfo.getIntroduceServiceProperties().getProperty(
					IntroduceConstants.INTRODUCE_SKELETON_SERVICE_NAME) + File.separator
				+ serviceInfo.getService().getName() + ".wsdl");
			fw.write(XMLUtilities.formatXML(XMLUtilities.documentToString(toDoc)));
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static Map buildWSDLImportMap(ServiceType service) {
		Map map = new HashMap();
		int namespaceCount = 0;
		if (service.getMethods() != null && service.getMethods().getMethod() != null) {
			for (int i = 0; i < service.getMethods().getMethod().length; i++) {
				MethodType method = service.getMethods().getMethod(i);
				if (method.isIsImported()) {
					if (!map.containsKey(method.getImportInformation().getNamespace())) {
						ImportInformation ii = new ImportInformation(method.getImportInformation(), "wns"
							+ namespaceCount++);
						map.put(method.getImportInformation().getNamespace(), ii);
					}
				}
			}
		}

		return map;
	}


	/**
	 * Walks a schema tree, following imports and placing namespaces in the
	 * namespaces set
	 * 
	 * @param schemaFile
	 *            The <i><b>FULLY QUALIFIED</i></b> file name of an XML
	 *            schema
	 * @param namespaces
	 *            The set of namespaces to populate
	 * @param visitedSchemas
	 *            The set of schemas already visited by this method
	 * @throws Exception
	 */
	public static void walkSchemasGetNamespaces(String schemaFile, Set namespaces, Set excludedNamespaces,
		Set visitedSchemas) throws Exception {
		System.out.println("Getting namespaces from schema " + schemaFile);
		visitedSchemas.add(schemaFile);
		File currentPath = new File(schemaFile).getCanonicalFile().getParentFile();
		Document schema = XMLUtilities.fileNameToDocument(schemaFile);
		List importEls = schema.getRootElement().getChildren("import",
			schema.getRootElement().getNamespace(IntroduceConstants.W3CNAMESPACE));
		for (int i = 0; i < importEls.size(); i++) {
			// get the import element
			Element importEl = (Element) importEls.get(i);

			// get the location of the imported schema
			String location = importEl.getAttributeValue("schemaLocation");
			if (location != null && !location.equals("")) {
				File importedSchema = new File(currentPath + File.separator + location);

				// has the schema been visited yet?
				if (!visitedSchemas.contains(importedSchema.getCanonicalPath())) {
					String namespace = importEl.getAttributeValue("namespace");
					if (!excludedNamespaces.contains(namespace)) {
						if (namespaces.add(namespace)) {
							System.out.println("adding namepace " + namespace);
						}
						if (!schemaFile.equals(importedSchema.getCanonicalPath())) {
							walkSchemasGetNamespaces(importedSchema.getCanonicalPath(), namespaces, excludedNamespaces,
								visitedSchemas);
						} else {
							System.err.println("WARNING: Schema is importing itself. " + schemaFile);
						}
					}
				} else {
					System.err.println("WARNING: Schema imports contain circular references. " + schemaFile);
				}
			}
		}
	}
}
