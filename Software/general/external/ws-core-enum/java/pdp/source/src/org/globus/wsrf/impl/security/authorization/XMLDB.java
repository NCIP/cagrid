/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
package org.globus.wsrf.impl.security.authorization;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator; 
import org.xmldb.api.DatabaseManager;   
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.apache.axis.utils.XMLUtils;
import org.apache.xindice.xml.dom.DOMParser;
import org.apache.xindice.client.xmldb.services.CollectionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

/**
 * This class is used to store keyed XML elements (cfr RDBMS rows)
 * in an XML database collection (cfr RDBMS table). It is used as a 
 * persistence engine behind stateful XML based entites.
 */
public class XMLDB {
    private static Boolean initialized = Boolean.FALSE;
    private static Log logger = LogFactory.getLog(XMLDB.class.getName());
    private static final String DB_ROOT = "xmldb:xindice-embed:///db/";
    private static final String DRIVER = org.apache.xindice.client.xmldb.DatabaseImpl.class.getName();

    private Collection collection;
    private CollectionManager manager;
    private String key;
    private String collectionName;
    
    private XMLDB() { 
    }

    /**
     * @param collectionName the name of the collection where the 
     *                       XML element shoulde reside
     * @param key the unique key of this stateful entity
     */
    public XMLDB(String collectionName, String key) throws Exception { 
	initDB();
	this.collectionName = collectionName;
	Collection root = DatabaseManager.getCollection(DB_ROOT);
	this.manager = (CollectionManager) root.getService("CollectionManager",
                                                           "1.0");
	this.collection = DatabaseManager.getCollection(DB_ROOT 
                                                        + collectionName);
	this.key = key.replaceAll("/","-");
	if (this.collection == null) {
	    createCollection();
	}
    }
   
    /**
     * retrieves the element from the database
     * @return the element found in the database matching the key of
     *         this object or null if none found  
     */ 
    public Element load() throws Exception {
        Resource resource = this.collection.getResource(this.key);
        if (resource != null) {
            Document doc = 
                DOMParser.toDocument((String) resource.getContent());
            return doc.getDocumentElement();    
        }
        return null;
    }

    /**
     * stores an element into the database
     * @param element that should be stored in the database
     *          with the key of this object 
     */ 
    public void store(Element element) throws Exception {
	XMLResource document = 
            (XMLResource) this.collection.createResource(this.key, 
                                                         "XMLResource");
	document.setContent(XMLUtils.ElementToString(element));
	this.collection.storeResource(document);
    }

    /**
     * removes the element from the database
     */ 
    public void remove() throws Exception {
	Resource document = this.collection.getResource(this.key);
	this.collection.removeResource(document);
    }
    

    private void createCollection() throws Exception {
	String collectionConfig =
          "<collection compressed=\"true\" " +
          "            name=\"" + this.collectionName + "\">" +
          "   <filer class=\"org.apache.xindice.core.filer.BTreeFiler\"/>" +
          "</collection>";
	this.collection = this.manager.createCollection(this.collectionName,
		                 DOMParser.toDocument(collectionConfig));
    }	

    private void initDB() throws Exception {
	synchronized (initialized) {
	    if (!initialized.booleanValue()) {
	        Class cls = Class.forName(DRIVER);
	        Database database = (Database) cls.newInstance();
	        DatabaseManager.registerDatabase(database);
		initialized = Boolean.TRUE;
	    }
	}
    }	
}
