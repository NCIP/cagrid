package gov.nih.nci.cagrid.authorization.impl.id;

import org.w3c.dom.Node;

import gov.nih.nci.cagrid.authorization.ObjectIdGenerator;

public class NodeQNameObjectIdGenerator implements ObjectIdGenerator {

	public String generateId(Node node) {
		return "{" + node.getNamespaceURI() + "}" + node.getLocalName();
	}

}
