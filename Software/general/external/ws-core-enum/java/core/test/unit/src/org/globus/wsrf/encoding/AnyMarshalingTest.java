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

import org.oasis.AnyListType;
import org.oasis.wsrf.properties.SetResourceProperties_Element;

import java.io.StringWriter;
import java.io.StringReader;

import java.util.Calendar;

import javax.xml.rpc.encoding.TypeMapping;
import javax.xml.rpc.encoding.TypeMappingRegistry;

import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.client.AxisClient;

import org.globus.wsrf.utils.XmlUtils;

import org.xml.sax.InputSource;

import junit.framework.TestCase;

import javax.xml.namespace.QName;

import org.oasis.wsrf.properties.DeleteType;
import org.oasis.wsrf.properties.InsertType;
import org.oasis.wsrf.properties.UpdateType;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AnyMarshalingTest extends TestCase {

    private static final QName ANY_TYPE = 
        new QName("http://www.w3.org/2001/XMLSchema", "anyType");
    
    public AnyMarshalingTest(String name) {
        super(name);
    }

    public void testSerializeDeserialize() throws Exception {

        MessageContext ctx = new MessageContext(new AxisClient());
        ctx.setEncodingStyle("");
        ctx.setProperty(AxisClient.PROP_DOMULTIREFS, 
                        Boolean.FALSE);
        
        StringWriter writer = new StringWriter();
        SerializationContext context =
            new SerializationContext(writer, ctx);
        context.setPretty(true);

        TypeMappingRegistry registry = context.getTypeMappingRegistry();
        TypeMapping map = registry.getDefaultTypeMapping();

        map.register(MessageElement.class,
                     ANY_TYPE,
                     new AnySerializerFactory(),
                     new AnyDeserializerFactory());

        QName RP = new QName("http://www.globus.org", "FooRP");
        
        Calendar time1 = Calendar.getInstance();
        time1.add(Calendar.SECOND, 30);
        
        MessageElement value = 
            (MessageElement)ObjectSerializer.toSOAPElement(time1, RP);
        value.setType(ANY_TYPE);

        QName request = new QName("http://www.globus.org",
                                  "FooElement");
        
        context.serialize(request, null, value);
        
        writer.flush();
        
        String valueStr = writer.toString();
        
        System.out.println(valueStr);

        InputSource input = new InputSource(new StringReader(valueStr));
        
        Element elem = XmlUtils.newDocument(input).getDocumentElement();

        assertEquals("FooElement", elem.getLocalName());
        NodeList children = elem.getChildNodes();
        assertTrue(children.getLength() > 0);
        // find first element child
        Node child = null;
        for (int i=0;i<children.getLength();i++) {
            child = children.item(i);
            if (child.getLocalName() != null) {
                break;
            } else {
                child = null;
            }
        }
        assertEquals("FooRP", child.getLocalName());
        Object tmp = ObjectDeserializer.toObject((Element)child, 
                                                 Calendar.class);
        assertEquals(time1.getTime(), ((Calendar)tmp).getTime());

        // try deserializer
        Object dserValue = ObjectDeserializer.toObject(elem);

        assertTrue(dserValue != null);
        assertTrue(elem != dserValue);
        assertTrue(dserValue instanceof MessageElement);

        System.out.println(dserValue);
    }
        
}
