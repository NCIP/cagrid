/*
 * Copyright 1999-2006 University of Chicago
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.globus.ws.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.SerializationException;

/**
 * A basic {@link EnumIterator EnumIterator} implementation that can enumerate
 * over in-memory data passed either as an array of objects or a list
 * ({@link List List}). The enumeration contents can be of 
 * {@link SOAPElement SOAPElement} type, simple types such as
 * {@link Integer Integer}, etc. or an Axis generated Java bean. 
 * The <tt>SimpleEnumIterator</tt> can only be used with transient type of
 * enumerations. 
 **/
public class SimpleEnumIterator implements EnumIterator {
    
    private static Log logger =
        LogFactory.getLog(SimpleEnumIterator.class.getName());

    protected Iterator iter;
    protected QName itemName;
    
    /**
     * Creates <tt>SimpleEnumIterator</tt> with a given items array 
     * and an item name.
     *
     * @param items the array of the items of the enumeration. 
     *        Can be null or empty. The items 
     * @param itemName the enumeration items will be serialized into element of
     *        this name. The <tt>itemName</tt> should be <tt>null</tt>
     *        for items of type {@link SOAPElement SOAPElement} or 
     *        {@link org.w3c.dom.Element Element}. It must not be <tt>null</tt>
     *        for all other types.
     */
    public SimpleEnumIterator(Object[] items, QName itemName) {
        this.iter = (items == null) ? null : Arrays.asList(items).iterator();
        this.itemName = itemName;
    }
    
    /**
     * Creates <tt>SimpleEnumIterator</tt> with a given items list
     * and an item name.
     *
     * @param items the list of the items of the enumeration. 
     *        Can be null or empty.
     * @param itemName the enumeration items will be serialized into element of
     *        this name. The <tt>itemName</tt> should be <tt>null</tt>
     *        for items of type {@link SOAPElement SOAPElement} or 
     *        {@link org.w3c.dom.Element Element}. It must not be <tt>null</tt>
     *        for all other types.
     */
    public SimpleEnumIterator(List items, QName itemName) {
        this.iter = (items == null) ?  null : items.iterator();
        this.itemName = itemName;
    }

    public IterationResult next(IterationConstraints constraints) {
        if (this.iter == null || !this.iter.hasNext()) {
            throw new NoSuchElementException();
        }
        
        int maxElements = constraints.getMaxElements();
        List dataList = 
            new ArrayList((maxElements > 100) ? 100 : maxElements);
        
        for (int i=0; (i < maxElements) && (this.iter.hasNext()); i++) {
            Object obj = this.iter.next();
            try {
                SOAPElement elem =
                    ObjectSerializer.toSOAPElement(obj, this.itemName);
                dataList.add(elem);
            } catch (SerializationException e) {
                // let's skip this element
                logger.debug("Failed to serialize enumeration item", e);
                i--;
            }
        }
        
        boolean endOfSequence = !this.iter.hasNext();
        return IterationResult.create(dataList, endOfSequence);
    }
    
    public void release() {
        this.iter = null;
    }
    
}
