package org.cagrid.cacore.sdk4x.cql2.processor;

import gov.nih.nci.cagrid.common.XMLUtilities;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 * HBMTool
 * Tool which can read Hibernate Mapping XML documents and 
 * determine what the class identifier type and value should be.
 * 
 * @author David
 */
public class HBMTool {
    
    private Map<String, Object> subclassIdentifiers = null;
    
    private Filter discriminatorFilter = null;

    public HBMTool() {
        subclassIdentifiers = new HashMap<String, Object>();
        this.discriminatorFilter = new Filter() {
            public boolean matches(Object o) {
                if (o instanceof Element) {
                    Element e = (Element) o;
                    return e.getName().equals("discriminator");
                }
                return false;
            }
        };
    }
    
    
    /**
     * Hibernate identifies subclasses stored in separate DB tables from their parent class by an Integer
     * value, indexed by the order of appearance in the HBM document.  It stores such identifiers
     * for subclasses stores in the SAME database table by a String value which is the short classname.
     * 
     * Yay abstraction!
     * 
     * @param parentClassName
     * @param subclassName
     * @return
     * @throws Exception
     */
    public Object getClassIdentifier(String parentClassName, String subclassName) throws Exception {
        Object identifier = subclassIdentifiers.get(subclassName);
        if (identifier == null) {
            // load the HBM XML document from the classpath
            String hbmResourceName = getHbmResourceName(parentClassName);
            InputStream hbmStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(hbmResourceName);
            Element hbmElem = XMLUtilities.streamToDocument(hbmStream).getRootElement();
            hbmStream.close();
            
            // if there's a discriminator element, we're using a String
            String shortClassname = getShortClassName(subclassName);
            List<?> discriminatorElements = hbmElem.getContent(discriminatorFilter);
            if (discriminatorElements.size() != 0) {
                identifier = shortClassname;
            } else {
                // using an integer, but we need to know which one
                Element classElem = hbmElem.getChild("class", hbmElem.getNamespace());
                List<?> subclassElements = classElem.getChildren("joined-subclass", classElem.getNamespace());
                Iterator<?> subclassIter = subclassElements.iterator();
                int index = 0;
                while (subclassIter.hasNext()) {
                    index++; // first subclass is index 1, so this is fine
                    Element subclassElem = (Element) subclassIter.next();
                    String name = subclassElem.getAttributeValue("name");
                    if (shortClassname.equals(name)) {
                        identifier = Integer.valueOf(index);
                        break;
                    }
                }
            }
            
            subclassIdentifiers.put(subclassName, identifier);
        }
        return identifier;
    }
    
    
    private String getHbmResourceName(String className) {
        String slashified = className.replace('.', '/');
        return slashified + ".hbm.xml";
    }
    
    
    private String getShortClassName(String className) {
        int dotIndex = className.lastIndexOf('.');
        return className.substring(dotIndex + 1);
    }
}
