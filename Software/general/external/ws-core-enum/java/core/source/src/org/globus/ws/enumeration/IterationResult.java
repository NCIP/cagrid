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

import java.util.List;

import org.apache.axis.message.MessageElement;

import org.globus.util.I18n;

import javax.xml.soap.SOAPElement;

/**
 * Represents the iteration results returned by the <tt>pull</tt> operation
 * of WS-Enumeration.
 */
public class IterationResult {
    
    private static I18n i18n =
        I18n.getI18n("org.globus.ws.enumeration.error");

    private boolean endOfSequence;
    private SOAPElement[] items;
    
    /**
     * Creates <tt>IterationResult</tt> instance with the specified
     * items and a <tt>endOfSequence</tt> flag.
     *
     * @param items the array of items. Can be null.
     * @param endOfSequence the end of sequence flag. 
     * @throws IllegalArgumentException if <tt>items</tt> are null or empty and
     *         the <tt>endOfSequence</tt> is false.
     */
    public IterationResult(SOAPElement [] items, 
                           boolean endOfSequence) {
        if ( (items == null || items.length == 0) &&
             (!endOfSequence) ) {
            throw new IllegalArgumentException(
                         i18n.getMessage("invalidIterationResult"));
        }
        this.items = items;
        this.endOfSequence = endOfSequence;
    }
    
    /**
     * Indicates if end of sequence was reached.
     *
     * @return true if end of sequence was reached. False, otherwise.
     */
    public boolean isEndOfSequence() {
        return this.endOfSequence;
    }
    
    /**
     * Returns the items of the enumeration.
     *
     * @return the array of items of the enumeration. Can be null or empty.
     */
    public SOAPElement[] getItems() {
        return this.items;
    }

    /**
     * Creates <tt>IterationResult</tt> instance with the specified
     * list of items and <tt>endOfSequence</tt> flag.
     *
     * @param items the list of items. The items must be of 
     *        {@link MessageElement MessageElement} type. Can be null.
     * @param endOfSequence the end of sequence flag. 
     * @return the constructed <tt>IterationResult</tt> IterationResult
     * @throws IllegalArgumentException if <tt>items</tt> are null or empty and
     *         the <tt>endOfSequence</tt> is false.
     */
    public static IterationResult create(List items,
                                         boolean endOfSequence) {
        MessageElement [] itemsArray = null;
        if (items != null && !items.isEmpty()) {
            itemsArray = new MessageElement[items.size()];
            items.toArray(itemsArray);
        }
        return new IterationResult(itemsArray, endOfSequence);
    }
    
}
