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
import java.util.Arrays;

import junit.framework.TestCase;

import org.globus.util.Base64;

import org.apache.axis.Constants;
import org.apache.axis.message.MessageElement;

import org.xml.sax.InputSource;

import javax.xml.soap.SOAPElement;

public class ObjectDeserializerTest extends TestCase {
    
    private static final String BASE64 =
        "MiAyIDMgAQIDMyABAgMyIDMgAQIDMyABAgM=";
    
    private static final String INPUT_1 =
        "<ns1:RegistrantData xmlns:ns1=\"http://www.globus.org/namespaces/2004/09/rendezvous\">" + BASE64 + "</ns1:RegistrantData>";
    
    public void testBase64() throws Exception {
        
        InputSource in = new InputSource(new StringReader(INPUT_1));
        byte[] data = (byte[])ObjectDeserializer.deserialize(in, byte[].class);
        
        assertTrue(data != null);
        
        for (int i=0;i<data.length;i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
        
        assertTrue(Arrays.equals(Base64.decode(BASE64.getBytes()), 
                                 data));
    }

    public void testBase64Wire() throws Exception {

        InputSource in = new InputSource(new StringReader(INPUT_1));
        ObjectDeserializationContext ctx =
            new ObjectDeserializationContext(in, byte[].class);
        ctx.parse();

        MessageElement elem = ctx.getMessageElement();
        
        System.out.println("elem:" + elem);

        // XXX: Ugly hack but it works
        elem.setType(Constants.XSD_BASE64);
        byte[] data = (byte[])ObjectDeserializer.toObject(elem);
        
        assertTrue(data != null);
        
        for (int i=0;i<data.length;i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
        
        assertTrue(Arrays.equals(Base64.decode(BASE64.getBytes()), 
                                 data));
    }

}
