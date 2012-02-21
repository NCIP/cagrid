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

import org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.rmi.RemoteException;

import org.w3c.dom.Element;

import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.util.I18n;

import org.apache.axis.message.MessageElement;

import javax.xml.soap.SOAPElement;
import javax.xml.rpc.Stub;

/**
 * Provides simple-to-use, client-side API for enumerating over data using
 * the WS-Enumeration operations. This class implements the {@link Iterator
 * Iterator} interface but the implementation does not follow the 
 * <tt>Iterator</tt> specification exactly because of the WS-Enumeration
 * specification limitations. See {@link #hasNext() hasNext} 
 * and {@link #next() next} operations for details.
 */
public class ClientEnumIterator implements Iterator {

    private static I18n i18n =
        I18n.getI18n("org.globus.ws.enumeration.error");

    protected ClientEnumeration enumeration;

    protected SOAPElement[] items;
    protected int itemsIndex;
    protected boolean endOfSequence;

    protected Class itemType;
    protected IterationConstraints constraints;

    /**
     * Creates <tt>ClientEnumIterator</tt> with a given data source port and
     * an enumeration context. 
     *
     * @param port the data source port.
     * @param context the enumeration context.
     * @throws IllegalArgumentException if the port or context parameters are
     *         null.
     */
    public ClientEnumIterator(DataSource port,
                              EnumerationContextType context) {
        this.enumeration = new ClientEnumeration(port, context);
    }

    /**
     * Creates <tt>ClientEnumIterator</tt> with a given stub and
     * an enumeration context. The stub instance must define all of the
     * operations exposed by the {@link DataSource DataSource} interface.
     *
     * @param stub the data source stub.
     * @param context the enumeration context.
     * @throws IllegalArgumentException if stub or context parameters are null
     *         or if the stub does not define all of the operations of the
     *         {@link DataSource DataSource} interface.
     * @see ClientEnumeration#getAsDataSource(Stub)
     */    
    public ClientEnumIterator(Stub stub, 
                              EnumerationContextType context) {
        this.enumeration = new ClientEnumeration(stub, context);
    }

    /**
     * Gets the {@link ClientEnumeration ClientEnumeration} associated with
     * this iterator.
     *
     * @return the {@link ClientEnumeration ClientEnumeration} associated with
     *         this iterator.
     */
    public ClientEnumeration getClientEnumeration() {
        return this.enumeration;
    }

    /**
     * Sets iteration constraints for the iterator.
     *
     * @param constraints the iteration constraints. Can be null.
     */
    public void setIterationConstraints(IterationConstraints constraints) {
        this.constraints = constraints;
    }
    
    /**
     * Gets iteration constraints for the iterator.
     *
     * @return the iteration constraints.
     */
    public IterationConstraints getIterationConstraints() {
        return this.constraints;
    }

    /**
     * Sets the type of the object returned by the {@link #next() next}
     * operation. By default the objects returned by the {@link #next() next}
     * operation will be of the {@link SOAPElement SOAPElement} type. If the
     * item type is set the enumeration elements will be automatically
     * deserialized into this type. This assumes the enumeration elements are
     * all of the same type.
     *
     * @param itemType the type of object the enumeration items should be
     *        deserialized into. Can be null (if null deserialization is 
     *        performed).
     */
    public void setItemType(Class itemType) {
        this.itemType = itemType;
    }

    /**
     * Gets the type of the object that should be returned by the
     * {@link #next() next} operation. 
     *
     * @return the type of the object that should be returned by the
     *         {@link #next() next} operation. Can be null (if null
     *         objects of the  {@link SOAPElement SOAPElement} type will
     *         be returned by the {@link #next() next} operation.
     */
    public Class getItemType() {
        return this.itemType;
    }

    /**
     * Returns <tt>true</tt> if there <B>might</B> be more elements in the 
     * enumeration.
     * <BR><BR><B>Note: </B><I>
     * This function in certain cases might return <B>true</B> even
     * though there might not be any more elements in the enumeration.</I>
     *
     * @return <tt>false</tt> if there are no more elements in the enumeration.
     *         <tt>True</tt> if there might be more elements in the 
     *         enumeration.
     */
    public boolean hasNext() {
        if (this.endOfSequence) {
            return (this.items != null &&
                    this.itemsIndex < this.items.length);
        } else {
            return true;
        }
    }
    
    /**
     * Returns the next element in the iteration. 
     * <BR><BR><B>Note: </B><I>
     * This function might throw {@link NoSuchElementException 
     * NoSuchElementException} exception even though the
     * {@link #hasNext() hasNext()} function returned true. </I>
     *
     * @return the next element in the iteration. Can return null if there
     *         are more elements in the enumeration but they cannot be returned
     *         because of the current iteration constraints.
     * @throws NoSuchElementException iteration has no more elements or
     *         enumeration context has expired.
     * @throws ConversionException if object conversion is performed and failed
     *         to convert (the index of the iteration will not be advanced).
     */
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if (this.items == null ||
            this.itemsIndex >= this.items.length) {
            getNext();

            if (this.items == null) {
                if (this.endOfSequence) {
                    throw new NoSuchElementException();
                } else {
                    // this should not happen according to the spec
                    // therefore force end of sequence
                    this.endOfSequence = true;
                    throw new NoSuchElementException();
                }
            }
        }
        
        if (this.items != null) {
            Object obj = convert(this.items[this.itemsIndex]);
            this.itemsIndex++;
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Performs object conversion. Can be overwritten by subclasses to
     * provide a custom way of deserializing the objects.
     *
     * @param elem the raw XML element
     * @return the deserialized object.
     * @throws ConversionException if conversion fails.
     */
    protected Object convert(SOAPElement elem) {
        if (this.itemType == null) {
            return elem;
        }
        try {
            if (this.itemType.equals(Element.class)) {
                return ((MessageElement)elem).getAsDOM();
            } else {
                return ObjectDeserializer.toObject(elem, this.itemType);
            }
        } catch (Exception e) {
            throw new ConversionException(i18n.getMessage("cantConvert"), e);
        }
    }

    /**
     * Not implemented. Always throws {@link UnsupportedOperationException 
     * UnsupportedOperationException} exception.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void getNext() {
        try {
            IterationResult iterationResult = 
                this.enumeration.pull(this.constraints);
            this.items = iterationResult.getItems();
            // enusure the array is not of size 0 
            if (this.items != null && this.items.length == 0) {
                this.items = null;
            }
            this.itemsIndex = 0;
            this.endOfSequence = iterationResult.isEndOfSequence();
        } catch (RemoteException e) {
            // TODO: what should happen if the enumeration times out?
            end();
            throw new NoSuchElementException();
        }
    }

    /**
     * Releases the enumeration.
     */
    public void release() {
        if (!this.endOfSequence) {
            try {
                this.enumeration.release();
            } catch (RemoteException e) {
                // ignore exception?
            } finally {
                end();
            }
        }
    }

    private void end() {
        this.items = null;
        this.itemsIndex = 0;
        this.endOfSequence = true;
    }
    
}
