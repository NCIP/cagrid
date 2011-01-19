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

import javax.naming.Context;
import javax.naming.NamingException;

import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.ResourceHomeImpl;
import org.globus.wsrf.impl.SimpleResourceKey;
import org.globus.wsrf.jndi.JNDIUtils;

/**
 * Resource home for enumerations.
 */
public class EnumResourceHome extends ResourceHomeImpl {

    /**
     * Create a transient or persistent enumeration resource with the given
     * iterator and visibility properties created from the context.
     *
     * @param iterator iterator of the enumeration resource.
     * @param persistent if true create a persistent enumeration, if false
     *        create a transient enumeration.
     * @return the enumeration resource created.
     * @throws ResourceException if error storing persistent enumeration
     *         resource.
     * @throws ResourceContextException if failed to create visibility
     *         properties from the context.
     * @throws IllegalArgumentException if iterator or visibility parameter
     *         is null.
     */
    public EnumResource createEnumeration(EnumIterator iterator,
                                          boolean persistent) 
        throws ResourceException, 
               ResourceContextException  {
        return createEnumeration(iterator,
                                 VisibilityProperties.createFromContext(),
                                 persistent);
    }
    
    /**
     * Create a transient or persistent enumeration resource with the given
     * iterator and visibility properties.
     *
     * @param iterator iterator of the enumeration resource.
     * @param visibility the visibility properties of the enumeration resource.
     * @param persistent if true create a persistent enumeration, if false
     *        create a transient enumeration.
     * @return the enumeration resource created.
     * @throws ResourceException if error storing persistent enumeration
     *         resource.
     * @throws IllegalArgumentException if iterator or visibility parameter
     *         is null.
     */
    public EnumResource createEnumeration(EnumIterator iterator,
                                          VisibilityProperties visibility,
                                          boolean persistent) 
        throws ResourceException {
        EnumResource resource = null;
        
        if (persistent) {
            resource = new EnumResource(iterator, visibility);
        } else {
            resource = new TransientEnumResource(iterator, visibility);
        }
        
        // store the resource
        resource.store(); 
        
        ResourceKey key = getKey(resource);
        add(key, resource);

        return resource;
    }
    
    /**
     * Gets a resource key for the given enumeration resource.
     *
     * @param resource the enumeration resource.
     * @return the key for the enumeration resource.
     */
    public ResourceKey getKey(EnumResource resource) {
        return new SimpleResourceKey(this.keyTypeName, resource.getID());
    }

    /**
     * Gets the default enumeration resource home.
     *
     * @return the default enumeration resource home.
     * @throws NamingException if failed to get the resource home.
     */
    public static EnumResourceHome getEnumResourceHome() 
        throws NamingException {
        Context initialContext = JNDIUtils.getInitialContext();
        return (EnumResourceHome)JNDIUtils.lookup(
                    initialContext,
                    "java:comp/env/enumeration/EnumerationHome",
                    EnumResourceHome.class);
    }

}
