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

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.axis.MessageContext;
import org.apache.axis.client.AxisClient;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.message.MessageElement;
import org.globus.wsrf.utils.XmlUtils;
import org.oasis.AnyListType;
import org.oasis.wsrf.properties.DeleteType;
import org.oasis.wsrf.properties.InsertType;
import org.oasis.wsrf.properties.SetResourceProperties_Element;
import org.oasis.wsrf.properties.UpdateType;
import org.xml.sax.InputSource;

public class AnyListMarshalingTest extends TestCase {

    public AnyListMarshalingTest(String name) {
        super(name);
    }

    public void testSimple() throws Exception {

        MessageContext ctx = new MessageContext(new AxisClient());
        ctx.setEncodingStyle("");
        ctx.setProperty(AxisClient.PROP_DOMULTIREFS, 
                        Boolean.FALSE);
        
        StringWriter writer = new StringWriter();
        SerializationContext context =
            new SerializationContext(writer, ctx);
        context.setPretty(true);
        
        QName foo = new QName("http://www.globus.org", "bar");

        AnyListType values = new SetResourceProperties_Element();

        QName RP = new QName("http://www.globus.org", "FooRP");

        Calendar time1 = Calendar.getInstance();
        time1.add(Calendar.SECOND, 30);
        
        Calendar time2 = Calendar.getInstance();
        time2.add(Calendar.HOUR, 30);

        DeleteType delete = new DeleteType();
        delete.setResourceProperty(RP);
        
        values.add(delete);

        InsertType insert = new InsertType();
        insert.set_any(new MessageElement[] {
            (MessageElement)ObjectSerializer.toSOAPElement(time1, RP)
        });

        values.add(insert);

        UpdateType update = new UpdateType();
        update.set_any(new MessageElement[] {
            (MessageElement)ObjectSerializer.toSOAPElement(time2, RP)
        });

        values.add(update);


        QName xmlType = SetResourceProperties_Element.getTypeDesc().getXmlType();
        QName request = new QName(xmlType.getNamespaceURI(),
                                  xmlType.getLocalPart().substring(1));
        
        context.serialize(request, null, values);

        writer.flush();

        String valueStr = writer.toString();
        
        System.out.println(valueStr);

        InputSource input = new InputSource(new StringReader(valueStr));
        
        Object dserValue = 
            ObjectDeserializer.toObject(XmlUtils.newDocument(input).getDocumentElement(), SetResourceProperties_Element.class);

        assertTrue(dserValue != null);
        assertTrue(dserValue instanceof AnyListType);
        AnyListType rt = (AnyListType)dserValue;
        assertEquals(3, rt.size());

        assertTrue(rt.get(0) instanceof DeleteType);
        assertEquals(RP, ((DeleteType)rt.get(0)).getResourceProperty());

        Object tmp = null;
        assertTrue(rt.get(1) instanceof InsertType);
        assertEquals(1, ((InsertType)rt.get(1)).get_any().length);
        tmp = ObjectDeserializer.toObject(
                         ((InsertType)rt.get(1)).get_any()[0],
                         Calendar.class);
        assertEquals(time1.getTime(), ((Calendar)tmp).getTime());

        assertTrue(rt.get(2) instanceof UpdateType);
        assertEquals(1, ((UpdateType)rt.get(2)).get_any().length);
        tmp = ObjectDeserializer.toObject(
                         ((UpdateType)rt.get(2)).get_any()[0],
                         Calendar.class);
        assertEquals(time2.getTime(), ((Calendar)tmp).getTime());

    }
        
}
