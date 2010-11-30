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

import java.io.Serializable;

import org.globus.util.I18n;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.ResourceKey;

/**
 * Represents visibility properties. Visibility properties are used to restrict
 * what service and/or resource can access the particular enumeration context.
 * In general, an enumeration context created by service S is only accessible
 * through service S. Similarly, an enumeration context created by resource R
 * is only accessible through resource R. 
 */
public class VisibilityProperties implements Serializable {
    
    private static I18n i18n =
        I18n.getI18n("org.globus.ws.enumeration.error");

    private String service;
    private Object key;
    
    /**
     * Creates <tt>VisibilityProperties</tt> with given service name
     * and a resource key.
     *
     * @param service the service name. Cannot be null.
     * @param key the resource key. Can be null.
     */
    public VisibilityProperties(String service, Object key) {
        if (service == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "service"));
        }
        this.service = service;
        this.key = key;
    }
    
    /**
     * Gets the service name.
     *
     * @return the service name.
     */
    public String getService() {
        return this.service;
    }

    /**
     * Gets the resource key.
     *
     * @return the resource key.
     */
    public Object getKey() {
        return this.key;
    }

    public int hashCode() {
        int hashCode = this.service.hashCode();
        if (this.key != null) {
            hashCode += this.key.hashCode();
        }
        return hashCode;
    }
    
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VisibilityProperties)) {
            return false;
        }
        VisibilityProperties other = (VisibilityProperties)obj;
        
        return (equals(this.service, other.service) &&
                equals(this.key, other.key));
    }
    
    private static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null) {
            return (obj2 == null);
        } else {
            return obj1.equals(obj2);
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("service: ").append(this.service);
        if (this.key != null) {
            buf.append(" with key: ").append(this.key);
        }
        return buf.toString();
    }
    
    // factory methods

    /**
     * Creates <tt>VisibilityProperties</tt> from the current context.
     * A ResourceContext must be associated with the current thread.
     *
     * @throws IllegalArgumentException if there is no ResourceContext
     *         associated with the current thread.
     * @throws ResourceContextException if there any other problems with
     *         obtaining the ResourceContext.
     * @return the created <tt>VisibilityProperties</tt>.
     */
    public static VisibilityProperties createFromContext() 
        throws ResourceContextException {
        return createFromContext(ResourceContext.getResourceContext());
    }

    /**
     * Creates <tt>VisibilityProperties</tt> from a given context.
     *
     * @param ctx the context to create <tt>VisibilityProperties</tt> from.
     *        Cannot be null.
     * @throws IllegalArgumentException if context is null.
     * @return the created <tt>VisibilityProperties</tt>.
     */
    public static VisibilityProperties createFromContext(ResourceContext ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "ctx"));
        }

        String service = ctx.getService();

        Object key = null;
        try {
            ResourceKey resourceKey = ctx.getResourceKey();
            if (resourceKey != null) {
                key = resourceKey.getValue();
            }
        } catch (ResourceContextException e) {
            // assume it can't be obtained
        }
        
        return new VisibilityProperties(service, key);
    }
    
}
