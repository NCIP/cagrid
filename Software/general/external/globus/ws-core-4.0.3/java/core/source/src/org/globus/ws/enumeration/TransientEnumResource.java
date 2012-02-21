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

import java.util.Hashtable;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceException;

/**
 * Represents a transient enumeration context (a context that only exists while
 * the container is running). 
 * This implementation extends the persistent enumeration resource 
 * implementation with disabled
 * {@link #load(org.globus.wsrf.ResourceKey) load()} and
 * {@link #store() store()} methods.
 */
public class TransientEnumResource extends EnumResource {
    
    // a hack to ensure that these resources never get GCed
    private static Hashtable resources = new Hashtable();

    public TransientEnumResource(EnumIterator iter, 
                                 VisibilityProperties visibility) {
        super(iter, visibility);
        resources.put(this.key, this);
    }

    public void load(ResourceKey key) throws ResourceException {
        // should never be called
        throw new ResourceException();
    }
    
    public void store() throws ResourceException {
    }
    
    public void remove() throws ResourceException {
        resources.remove(this.key);
        this.iter.release();
    }
    
}
