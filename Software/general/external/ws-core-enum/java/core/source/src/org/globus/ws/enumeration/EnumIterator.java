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

import java.util.NoSuchElementException;

/**
 * An iterator over a sequence of XML elements. 
 * The implementations can assume a single thread access. Only one client
 * is allowed to access a particular enumeration at a time. The implementations
 * must keep track of the progress of the enumeration (for example, store the 
 * index of the last item retrieved). 
 * For persistent enumerations, the <tt>EnumIterator</tt> implementation must
 * be fully serializable using the Java serialization framework.
 */
public interface EnumIterator {
    
    /**
     * Retrieves the next set of items of the enumeration.
     *
     * @param constraints the constrains for this iteration. Can be null.
     *        If null, default constraints must be assumed.
     * @return the result of this iteration that fulfils the specified
     *         constraints. It must always be non-null.
     * @throws TimeoutException if <tt>maxTime</tt> constraint was specified
     *         and the enumeration data was not collected within that time.
     * @throws NoSuchElementException if iterator has no more elements
     */
    IterationResult next(IterationConstraints constraints)
        throws TimeoutException, 
               NoSuchElementException;
    
    /**
     * Release any resources associated with this iterator. For example, 
     * close database connections, delete files, etc.
     * This method is called when the enumeration resource is explicitly
     * released, expires, or the user finished enumerating through all 
     * the data.
     */
    void release();
    
}
