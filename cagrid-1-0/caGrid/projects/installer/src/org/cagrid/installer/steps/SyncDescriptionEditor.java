/**
 * 
 */
package org.cagrid.installer.steps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class SyncDescriptionEditor {

	private Document doc;

	private TransformerFactory txFact;

	private XPathFactory xpFact;

	private int currentSyncDescriptor;

	/**
	 * 
	 */
	public SyncDescriptionEditor() {
		this.txFact = TransformerFactory.newInstance();
		this.xpFact = XPathFactory.newInstance();
	}

	public void load(String fileName) throws Exception {
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		fact.setValidating(false);
		fact.setNamespaceAware(true);
		DocumentBuilder builder = fact.newDocumentBuilder();
		this.doc = builder.parse(new File(fileName));
	}

	public void store(String fileName) throws Exception {
		Transformer t = this.txFact.newTransformer();
		t.setOutputProperty("omit-xml-declaration", "yes");
		t.setOutputProperty("indent", "yes");
		t.transform(new DOMSource(this.doc), new StreamResult(
				new FileOutputStream(fileName)));
	}

	public String getXML() throws Exception {
		StringWriter w = new StringWriter();
		Transformer t = this.txFact.newTransformer();
		t.setOutputProperty("omit-xml-declaration", "yes");
		t.setOutputProperty("indent", "yes");
		t.transform(new DOMSource(this.doc), new StreamResult(w));
		return w.getBuffer().toString();
	}

	public void addSyncDescriptor(String gtsServiceURI) {
		Node root = this.doc.getDocumentElement();
		Element newSyncDesc = this.doc.createElementNS(root.getNamespaceURI(),
				root.getPrefix() + ":SyncDescriptor");

		Node existingSyncDesc = getSyncDescriptor(1);
		Node sib = null;
		if (existingSyncDesc != null) {
			sib = existingSyncDesc.getNextSibling();
		} else {
			sib = root.getFirstChild();
		}
		if (sib != null) {
			root.insertBefore(newSyncDesc, sib);
		} else {
			root.appendChild(newSyncDesc);
		}
		
		//Add gtsServiceURI element
		Element gtsServiceURIEl = this.doc.createElementNS(root
				.getNamespaceURI(), root.getPrefix() + ":gtsServiceURI");
		newSyncDesc.appendChild(gtsServiceURIEl);
		gtsServiceURIEl.setTextContent(gtsServiceURI);

		//Add Expiration element
		Element expirationEl = this.doc.createElementNS(root.getNamespaceURI(), root.getPrefix() + ":Expiration");
		newSyncDesc.appendChild(expirationEl);
		expirationEl.setAttribute("hours", "1");
		expirationEl.setAttribute("minutes", "0");
		expirationEl.setAttribute("seconds", "0");
		
		//Add TrustedAuthorityFilter
		Element trustedAuthorityFilterEl = this.doc.createElementNS(root.getNamespaceURI(), root.getPrefix() + ":TrustedAuthorityFilter");
		
	}

	private Node getSyncDescriptor(int i) {
		try {
			return (Node) this.xpFact.newXPath().compile(
					"//*[local-name()='SyncDescriptor'][" + i + "]").evaluate(
					this.doc, XPathConstants.NODE);
		} catch (Exception ex) {
			throw new RuntimeException("Error select sync descriptor: "
					+ ex.getMessage(), ex);
		}
	}

	public void selectSyncDescriptor(int idx) {
		this.currentSyncDescriptor = idx;
	}

	public int getSyncDescriptorCount() {
		try {
			String xpath = "count(//*[local-name()='SyncDescriptor'])";
			NodeList nodes = (NodeList) this.xpFact.newXPath().compile(xpath)
					.evaluate(this.doc, XPathConstants.NODESET);
			return nodes.getLength();
		} catch (Exception ex) {
			throw new RuntimeException("Error getting descriptor count: "
					+ ex.getMessage(), ex);
		}
	}

	public static void main(String[] args) throws Exception {
		SyncDescriptionEditor ed = new SyncDescriptionEditor();
		ed
				.load("/Users/joshua/dev4/caGrid/projects/syncgts/ext/resources/sync-description.xml");
		ed.addSyncDescriptor("http://some.url/yadda");
		System.out.println(ed.getXML());
	}

}
