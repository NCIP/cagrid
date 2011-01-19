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
package org.globus.wsrf.utils;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.globus.wsrf.encoding.ObjectSerializer;
import org.oasis.wsrf.faults.BaseFaultType;

public class FaultHelperTest extends TestCase {

    private static final QName FAULT_QNAME =
        new QName("http://foo", "bar");
    
    public void testDescription() throws Exception {
        BaseFaultType fault = new BaseFaultType();
        FaultHelper faultHelper = new FaultHelper(fault);

        faultHelper.setDescription("foo");
        assertTrue(faultHelper.getDescription() != null);
        assertEquals(1, faultHelper.getDescription().length);
        assertEquals("foo", faultHelper.getDescription()[0]);
        assertEquals("foo", faultHelper.getDescriptionAsString());
        
        faultHelper.setDescription(new String[] {"bar", "foo"});
        assertTrue(faultHelper.getDescription() != null);
        assertEquals(2, faultHelper.getDescription().length);
        assertEquals("bar", faultHelper.getDescription()[0]);
        assertEquals("foo", faultHelper.getDescription()[1]);
        assertEquals("bar / foo", faultHelper.getDescriptionAsString());
        
        faultHelper.setDescription((String)null);
        assertTrue(faultHelper.getDescription() == null);

        faultHelper.setDescription((String[])null);
        assertTrue(faultHelper.getDescription() == null);

        faultHelper.addDescription("aaa");
        faultHelper.addDescription("bbb");
        assertTrue(faultHelper.getDescription() != null);
        assertEquals(2, faultHelper.getDescription().length);
        assertEquals("aaa", faultHelper.getDescription()[0]);
        assertEquals("bbb", faultHelper.getDescription()[1]);
    }
    
    public void testChainingBaseFault() throws Exception {
        BaseFaultType fault = new BaseFaultType();
        
        assertTrue(fault.getFaultCause() == null);
        
        FaultHelper faultHelper = new FaultHelper(fault);
        
        assertTrue(fault.getFaultCause() != null);
        assertEquals(1, fault.getFaultCause().length);
        assertEquals(FaultHelper.STACK_TRACE, 
                     fault.getFaultCause()[0].getErrorCode().getDialect());

        BaseFaultType fault1 = new BaseFaultType();

        assertTrue(fault1.getFaultCause() == null);

        faultHelper.addFaultCause(fault1);

        assertTrue(fault1.getFaultCause() != null);
        assertEquals(1, fault1.getFaultCause().length);
        assertEquals(FaultHelper.STACK_TRACE, 
                     fault1.getFaultCause()[0].getErrorCode().getDialect());

        assertEquals(2, fault.getFaultCause().length);

        System.out.println(ObjectSerializer.toString(fault, FAULT_QNAME));
    }

    public void testChainingException() throws Exception {
        BaseFaultType fault = new BaseFaultType();
        
        assertTrue(fault.getFaultCause() == null);
        
        FaultHelper faultHelper = new FaultHelper(fault);
        
        assertTrue(fault.getFaultCause() != null);
        assertEquals(1, fault.getFaultCause().length);
        assertEquals(FaultHelper.STACK_TRACE, 
                     fault.getFaultCause()[0].getErrorCode().getDialect());
        
        Exception e = new Exception("foo bar");
        
        faultHelper.addFaultCause(e);
        
        assertEquals(2, fault.getFaultCause().length);
        assertEquals(FaultHelper.STACK_TRACE, 
                     fault.getFaultCause()[0].getErrorCode().getDialect());

        assertEquals("foo bar", 
                     fault.getFaultCause()[1].getDescription()[0].get_value());
        assertEquals(1, 
                     fault.getFaultCause()[1].getFaultCause().length);
        assertEquals(FaultHelper.STACK_TRACE, 
                     fault.getFaultCause()[1].getFaultCause()[0].getErrorCode().getDialect());
        
        System.out.println(ObjectSerializer.toString(fault, FAULT_QNAME));
    }

}
