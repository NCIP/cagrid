package gov.nih.nci.cagrid.data.ui.table;

import java.util.EventObject;

import javax.swing.JTable;

/** 
 *  ElementNameChangeEvent
 *  Event fired when an element name changes for a class
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * 
 * @created Oct 6, 2006 
 * @version $Id$ 
 */
public class ClassChangeEvent extends EventObject {
	private String packageName;
	private String className;
	private String namespace;
	private String elementName;
	private String serializer;
	private String deserializer;

	public ClassChangeEvent(JTable table, String packName, String className, String namespace, 
		String elementName, String serializer, String deserializer) {
		super(table);
		this.packageName = packName;
		this.className = className;
		this.namespace = namespace;
		this.elementName = elementName;
		this.serializer = serializer;
		this.deserializer = deserializer;
	}

	
	public String getClassName() {
		return className;
	}
	

	public String getDeserializer() {
		return deserializer;
	}
	
	
	public String getElementName() {
		return elementName;
	}
	
	
	public String getNamespace() {
		return namespace;
	}

	
	public String getPackageName() {
		return packageName;
	}

	
	public String getSerializer() {
		return serializer;
	}	
}
