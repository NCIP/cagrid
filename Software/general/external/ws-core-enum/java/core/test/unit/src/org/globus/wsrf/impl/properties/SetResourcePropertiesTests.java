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
package org.globus.wsrf.impl.properties;

import java.util.Calendar;

import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.globus.wsrf.WSRFConstants;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.impl.TestHome;
import org.globus.wsrf.impl.TestResource;
import org.globus.wsrf.tests.basic.TestPortType;
import org.oasis.wsrf.properties.DeleteType;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.InsertType;
import org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType;
import org.oasis.wsrf.properties.ResourceUnknownFaultType;
import org.oasis.wsrf.properties.SetResourcePropertiesResponse;
import org.oasis.wsrf.properties.SetResourceProperties_Element;
import org.oasis.wsrf.properties.UnableToModifyResourcePropertyFaultType;
import org.oasis.wsrf.properties.UpdateType;

public class SetResourcePropertiesTests extends PropertiesTestCase {

    public SetResourcePropertiesTests(String name) {
        super(name);
    }

    public void testUnknownResource() throws Exception {
        TestPortType port = 
            locator.getTestPortTypePort(createEPR(TestHome.BAD_KEY));

        SetResourceProperties_Element propRequest = 
            new SetResourceProperties_Element();
        
        try {
            SetResourcePropertiesResponse propResponse = 
                port.setResourceProperties(propRequest);
            fail("Did not throw exception");
        } catch (ResourceUnknownFaultType e) {
            //it's ok
        }
    }
    
    // test changing of CurrentTime RP via SetRP operation.
    public void testChangeCurrentTime() throws Exception {
        testChangeTimeRP(WSRFConstants.CURRENT_TIME);
    }
    
    // test changing of TerminationTime RP via SetRP operation.
    public void testChangeTerminationTime() throws Exception {
        testChangeTimeRP(WSRFConstants.TERMINATION_TIME);
    }

    private void testChangeTimeRP(QName name) throws Exception {
        MessageElement [] elements = new MessageElement[1];
        SetResourceProperties_Element request = null;

        Calendar value = Calendar.getInstance();
        value.add(Calendar.SECOND, 30);

        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        // test insert
        InsertType insert = new InsertType();
        elements[0] =
            (MessageElement)ObjectSerializer.toSOAPElement(value, name);
        insert.set_any(elements);
        
        request = new SetResourceProperties_Element();
        request.setInsert(insert);
        
        try {
            port.setResourceProperties(request);
            fail("did not throw an exception");
        } catch (UnableToModifyResourcePropertyFaultType e) {
            // that's what we want
        }

        // test update
        UpdateType update = new UpdateType();
        elements[0] =
            (MessageElement)ObjectSerializer.toSOAPElement(value, name);
        update.set_any(elements);
        
        request = new SetResourceProperties_Element();
        request.setUpdate(update);
        
        try {
            port.setResourceProperties(request);
            fail("did not throw an exception");
        } catch (UnableToModifyResourcePropertyFaultType e) {
            // that's what we want
        }

        // test delete
        DeleteType delete = new DeleteType();
        delete.setResourceProperty(name);

        request = new SetResourceProperties_Element();
        request.setDelete(delete);
                
        try {
            port.setResourceProperties(request);
            fail("did not throw an exception");
        } catch (UnableToModifyResourcePropertyFaultType e) {
            // that's what we want
        }
    }

    public void testDeleteNoRP() throws Exception {
        testDelete(null);
    }
    
    public void testDeleteWrongRP() throws Exception {
        testDelete(new QName("http://foo", "bar"));
    }

    private void testDelete(QName rp) throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));
        
        // test delete
        DeleteType delete = new DeleteType();
        delete.setResourceProperty(rp);
        
        SetResourceProperties_Element request = new SetResourceProperties_Element();
        request.setDelete(delete);
                
        try {
            port.setResourceProperties(request);
            fail("did not throw an exception");
        } catch (InvalidResourcePropertyQNameFaultType e) {
            // that's what we want
        }
    }

    public void testModifyRP() throws Exception {
        TestPortType port = locator.getTestPortTypePort(getServiceAddress());
        port = locator.getTestPortTypePort(createResource(port));

        SetResourceProperties_Element request = null;
        GetResourcePropertyResponse propResponse = null;
        MessageElement [] result = null;

        QName rp = TestResource.VALUE_RP;

        // test delete
        DeleteType delete = new DeleteType();
        delete.setResourceProperty(rp);
        
        request = new SetResourceProperties_Element();
        request.setDelete(delete);
        port.setResourceProperties(request);
        
        propResponse = port.getResourceProperty(rp);
        result = propResponse.get_any();
        assertTrue(result == null);

        MessageElement [] elements = null;

        // test insert
        InsertType insert = new InsertType();
        int num = 2;
        elements = new MessageElement[num];
        for (int i=0;i<num;i++) {
            elements[i] =
                (MessageElement)ObjectSerializer.toSOAPElement(new Integer(i), 
                                                               rp);
        }
        insert.set_any(elements);

        request = new SetResourceProperties_Element();
        request.setInsert(insert);

        port.setResourceProperties(request);

        propResponse = port.getResourceProperty(rp);
        result = propResponse.get_any();
        assertTrue(result != null);
        assertEquals(num, result.length);
        for (int i=0;i<num;i++) {
            Integer value = 
                (Integer)ObjectDeserializer.toObject(result[i], Integer.class);
            assertEquals(new Integer(i), value);
        }

        // test update
        UpdateType update = new UpdateType();
        elements = new MessageElement[1];
        elements[0] =
            (MessageElement)ObjectSerializer.toSOAPElement(new Integer(90), 
                                                           rp);
        update.set_any(elements);

        request = new SetResourceProperties_Element();
        request.setUpdate(update);
        
        port.setResourceProperties(request);

        propResponse = port.getResourceProperty(rp);
        result = propResponse.get_any();
        assertTrue(result != null);
        assertEquals(1, result.length);
        Integer value = 
            (Integer)ObjectDeserializer.toObject(result[0], Integer.class);
        assertEquals(new Integer(90), value);
    }

}
