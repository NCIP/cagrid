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
package org.globus.wsrf.encoding;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

import junit.framework.TestCase;

import org.apache.axis.message.MessageElement;
import org.globus.wsrf.types.profiling.Timestamp;
import org.globus.wsrf.types.profiling.TimestampType;
import org.globus.wsrf.utils.AnyHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ObjectSerializerTest extends TestCase {

    public void testSimple() throws Exception {
        SOAPElement soapElem = null;
        Element elem = null;

        QName name = new QName("http://foo", "abcdef");
        String value = "abc";

        soapElem = ObjectSerializer.toSOAPElement(value, name);
        System.out.println(AnyHelper.toString((MessageElement)soapElem));
        elem = AnyHelper.toElement((MessageElement)soapElem);

        assertEquals(name.getNamespaceURI(), elem.getNamespaceURI());
        assertTrue (elem.getTagName().indexOf(name.getLocalPart()) != -1);
    }

    public void testBean() throws Exception {
        SOAPElement soapElem = null;
        Element elem = null;

        TimestampType timestampType = new TimestampType();
        Timestamp timestamp = new Timestamp();
        timestampType.setTimestamp(timestamp);
        timestamp.setThreadID("foo");
        timestamp.setMessageContextHash("bar");
        timestamp.setServiceURL("http://mismis");
        timestamp.setOperation("createCounter");
        


        QName name = new QName("http://foo", "abcdef");
        soapElem = ObjectSerializer.toSOAPElement(timestampType, name);
        System.out.println(AnyHelper.toString((MessageElement)soapElem));
        elem = AnyHelper.toElement((MessageElement)soapElem);

        NodeList list = 
            elem.getElementsByTagNameNS("http://wsrf.globus.org/types/profiling",
                                        "threadID");
        assertEquals(1, list.getLength());
        assertEquals("foo", list.item(0).getFirstChild().getNodeValue());
    }
            
}
