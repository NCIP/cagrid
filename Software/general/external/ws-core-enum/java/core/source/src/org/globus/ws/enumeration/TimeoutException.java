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

/**
 * This is an exception raised by the 
 * {@link EnumIterator#next(IterationConstraints) EnumIterator.next()} 
 * operation when the enumeration data was not collected within the
 * time specified.
 */
public class TimeoutException extends RuntimeException {

    /**
     * Creates a TimeoutException without error message.
     */
    public TimeoutException() {
    }
    
    /**
     * Creates a TimeoutException with a given error message.
     *
     * @param message error message
     */
    public TimeoutException(String message) {
        super(message);
    }
    
    /**
     * Creates a TimeoutException with a given error message
     * and nested exception.
     *
     * @param message error message
     * @param exception nested exception/
     */
    public TimeoutException(String message,
                               Throwable exception) {
        super(message, exception);
    }
    
    /**
     * Creates a TimeoutException from a nested exception.
     *
     * @param exception nested exception
     */
    public TimeoutException(Throwable exception) {
        super(exception);
    }
    
}
