package org.cagrid.gaards.websso.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.jdom.Document;
import org.jdom.input.DOMBuilder;
import org.springframework.core.io.Resource;

public class FileHelper {
	public FileHelper() {
	}
	public Document validateXMLwithSchema(Resource propertiesFileResource,
			Resource schemaFileResource) throws AuthenticationConfigurationException {
		org.w3c.dom.Document document = null;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setValidating(true);
		documentBuilderFactory.setAttribute(
				"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
				"http://www.w3.org/2001/XMLSchema");
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			documentBuilderFactory.setAttribute(
					"http://java.sun.com/xml/jaxp/properties/schemaSource",
					schemaFileResource.getInputStream());
			documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(propertiesFileResource.getInputStream());
		} catch (Exception e) {
			throw new AuthenticationConfigurationException(
					"Error in reading the " + propertiesFileResource + " file: " + e.getMessage(), e);
		}
		DOMBuilder builder = new DOMBuilder();
		org.jdom.Document jdomDocument = builder.build(document);

		return jdomDocument;
	}
}