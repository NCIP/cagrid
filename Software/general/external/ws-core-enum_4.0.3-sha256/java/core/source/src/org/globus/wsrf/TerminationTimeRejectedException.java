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
package org.globus.wsrf;

/**
 * This is an exception raised by the 
 * {@link ResourceLifetime#setTerminationTime(java.util.Calendar) 
 * ResourceLifetime.setTerminationTime()} operation in case the new
 * termination time cannot be accepted by the service.
 */
public class TerminationTimeRejectedException extends RuntimeException {

    /**
     * Creates a TerminationTimeRejectedException without error message.
     */
    public TerminationTimeRejectedException() {
    }
    
    /**
     * Creates a TerminationTimeRejectedException with a given error message.
     *
     * @param message error message
     */
    public TerminationTimeRejectedException(String message) {
        super(message);
    }
    
    /**
     * Creates a TerminationTimeRejectedException with a given error message
     * and nested exception.
     *
     * @param message error message
     * @param exception nested exception/
     */
    public TerminationTimeRejectedException(String message,
                                            Throwable exception) {
        super(message, exception);
    }
    
    /**
     * Creates a TerminationTimeRejectedException from a nested exception.
     *
     * @param exception nested exception
     */
    public TerminationTimeRejectedException(Throwable exception) {
        super(exception);
    }
    
}
