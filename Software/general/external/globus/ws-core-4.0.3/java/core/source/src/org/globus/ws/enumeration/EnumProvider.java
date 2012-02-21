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

import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Pull;
import org.xmlsoap.schemas.ws._2004._09.enumeration.RenewResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Renew;
import org.xmlsoap.schemas.ws._2004._09.enumeration.GetStatusResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.GetStatus;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Release;
import org.xmlsoap.schemas.ws._2004._09.enumeration.ItemListType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.ExpirationType;

import org.globus.axis.utils.DurationUtils;
import org.globus.wsrf.utils.AnyHelper;
import org.globus.wsrf.encoding.SerializationException;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.TerminationTimeRejectedException;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.impl.SimpleResourceKey;
import org.globus.util.I18n;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

import org.apache.axis.AxisFault;
import org.apache.axis.Constants;
import org.apache.axis.types.PositiveInteger;
import org.apache.axis.types.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * WS-Enumeration operation provider. It implements four WS-Enumeration 
 * operations such as 
 * {@link #getStatusOp(GetStatus) getStatus()}, 
 * {@link #pullOp(Pull) pull()},
 * {@link #releaseOp(Release) release()}, and
 * {@link #renewOp(Renew) renew()}. This provider works together with 
 * {@link EnumResourceHome EnumResourceHome} and 
 * {@link EnumResource EnumResource}.
 */
public class EnumProvider {

    private static Log logger =
        LogFactory.getLog(EnumProvider.class.getName());
    
    private static I18n i18n =
        I18n.getI18n("org.globus.ws.enumeration.error");

    private static QName CLIENT_FAULT = 
        new QName(Constants.URI_SOAP11_ENV, "Client");
    
    private static QName SERVER_FAULT = 
        new QName(Constants.URI_SOAP11_ENV, "Server");

    protected EnumResourceHome enumHome;

    /**
     * Creates WS-Enumeration operation provider.
     *
     * @throws NamingException if unable to obtain the enumeration resource
     *         home.
     */
    public EnumProvider() 
        throws NamingException {
        this.enumHome = EnumResourceHome.getEnumResourceHome();
    }

    private RemoteException getServerFault(String faultMessage) {
        return new AxisFault(CLIENT_FAULT,
                             faultMessage,
                             null,
                             null);
    }

    private RemoteException getClientFault(String faultMessage) {
        return new AxisFault(SERVER_FAULT, 
                             faultMessage,
                             null,
                             null);
    }

    private ResourceKey getEnumKey(EnumerationContextType context) 
        throws RemoteException {
        SOAPElement keyElement = 
            AnyHelper.findFirstElement(context,
                                       this.enumHome.getKeyTypeName());
        if (keyElement == null) {
            throw getServerFault(
                         i18n.getMessage("invalidEnumerationContext01"));
        }
        
        try {
            return new SimpleResourceKey(keyElement,
                                         this.enumHome.getKeyTypeClass());
        } catch (InvalidResourceKeyException e) {
            throw getServerFault(
                         i18n.getMessage("invalidEnumerationContext01"));
        }
    }

    private EnumResource getEnumResource(ResourceKey key) 
        throws RemoteException {
        EnumResource resource = null;

        try {
            resource = (EnumResource)this.enumHome.find(key);
        } catch (InvalidResourceKeyException e) {
            throw getServerFault(
                         i18n.getMessage("invalidEnumerationContext01"));
        } catch (ResourceException e) {
            throw getServerFault(
                         i18n.getMessage("invalidEnumerationContext00"));
        }

        try {
            ResourceContext ctx = ResourceContext.getResourceContext();

            VisibilityProperties props =
                VisibilityProperties.createFromContext(ctx);
        
            if (!props.equals(resource.getVisibility())) {
                logger.warn("Resource visibility error");
                throw getServerFault(
                             i18n.getMessage("invalidEnumerationContext00"));
            }
        } catch (ResourceContextException e) {
            logger.debug("Resource visibility will not be enforced");
        }
        
        return resource;
    }
    
    // Utility functions - might need to be moved

    /**
     * Creates <tt>EnumerateResponse</tt> that represents a response
     * object to the <tt>enumerate</tt> operation of WS-Enumeration.
     *
     * @param key the key of the enumeration resource. Cannot be null.
     * @param resource the enumeration resource. Can be null. If null, 
     *        the returned object will not contain the expiration time
     *        information.
     * @throws SerializationException if key serialization fails.
     * @return the created <tt>EnumerateResponse</tt> object.
     */
    public static EnumerateResponse createEnumerateOpResponse(
                                                   ResourceKey key,
                                                   EnumResource resource) 
        throws SerializationException {
        EnumerateResponse response = new EnumerateResponse();
        response.setEnumerationContext(createEnumerationContextType(key));
        if (resource != null) {
            response.setExpires(getExpirationType(resource));
        }
        return response;
    }

    /**
     * Creates <tt>EnumerationContextType</tt> object that represents an 
     * enumeration context. 
     *
     * @param key the key of the enumeration resource. Cannot be null.
     * @throws SerializationException if key serialization fails.
     * @return the created <tt>EnumerationContextType</tt> object.
     */
    public static EnumerationContextType createEnumerationContextType(
                                                           ResourceKey key) 
        throws SerializationException {
        if (key == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "key"));
        }
        EnumerationContextType context = new EnumerationContextType();
        AnyHelper.setAny(context, key.toSOAPElement());
        return context;
    }

    /**
     * Creates <tt>ExpirationType</tt> object that represents an expiration 
     * time of an enumeration context as defined by the WS-Enumeration
     * specification.
     *
     * @param resource the enumeration resource. Cannot be null.
     * @return the created <tt>ExpirationType</tt> object. Can be null if
     *         the enumeration resource does not have expiration time set.
     */
    public static ExpirationType getExpirationType(EnumResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "resource"));
        }
        ExpirationType expires = null;
        if (resource.getTerminationTime() != null) {
            expires = new ExpirationType();
            if (resource.isDuration()) {
                Duration duration = 
                    DurationUtils.computeDuration(Calendar.getInstance(),
                                                  resource.getTerminationTime());
                expires.setNonNegativeDurationTypeValue(duration);
            } else {
                expires.setDateTimeValue(resource.getTerminationTime());
            }
        }
        return expires;
    }

    // WS-Enumeration operation implementations

    public PullResponse pullOp(Pull body) 
        throws RemoteException {

        ResourceKey key = getEnumKey(body.getEnumerationContext());
        EnumResource resource = getEnumResource(key);
        EnumIterator iter = resource.getIterator();

        PullResponse response = new PullResponse();
        try {
            IterationResult result = iter.next(getPullConstraints(body));
            
            if (result.getItems() != null) {
                ItemListType items = new ItemListType();
                AnyHelper.setAny(items, result.getItems());
                response.setItems(items);
            }
            
            if (result.isEndOfSequence()) {
                response.setEndOfSequence(Boolean.TRUE);
                // destroy enum resource
                remove(key);
            } else {
                // store its new iteration state
                store(resource);
            }
        } catch (TimeoutException e) {
            // XXX: should update iterator state or not?
            throw getServerFault(i18n.getMessage("timedOut"));
        } catch (NoSuchElementException e) {
            response.setEndOfSequence(Boolean.TRUE);
            // destroy enum resource
            remove(key);
        }
        
        return response;
    }

    public RenewResponse renewOp(Renew body)
        throws RemoteException {
        
        ResourceKey key = getEnumKey(body.getEnumerationContext());
        EnumResource resource = getEnumResource(key);
        
        ExpirationType expires = body.getExpires();
        if (expires == null) {
            resource.setDuration(false);
            resource.setTerminationTime(null);
        } else {
            Calendar termTime = null;
            try {
                termTime = expires.getDateTimeValue();

                resource.setDuration(false);
            } catch (Exception e) {
                try {
                    Duration duration = 
                        expires.getNonNegativeDurationTypeValue();
                    
                    resource.setDuration(true);

                    termTime = Calendar.getInstance();
                    DurationUtils.updateCalendar(termTime, duration);
                } catch (Exception ee) {
                    throw getClientFault(
                                i18n.getMessage("invalidExpirationTime00"));
                }
            }

            try {
                resource.setTerminationTime(termTime);
            } catch (TerminationTimeRejectedException e) {
                if (resource.isDuration()) {
                    throw getClientFault(
                                i18n.getMessage("invalidExpirationTime01"));
                } else {
                    throw getClientFault(
                                i18n.getMessage("invalidExpirationTime02"));
                }
            }
            
        }
        
        // store its new termination time
        store(resource);

        RenewResponse response = new RenewResponse();
        response.setExpires(getExpirationType(resource));
        return response;
    }

    public GetStatusResponse getStatusOp(GetStatus body) 
        throws RemoteException {
        ResourceKey key = getEnumKey(body.getEnumerationContext());
        EnumResource resource = getEnumResource(key);

        GetStatusResponse response = new GetStatusResponse();
        response.setExpires(getExpirationType(resource));
        return response;
    }
    
    public void releaseOp(Release body) 
        throws RemoteException {
        ResourceKey key = getEnumKey(body.getEnumerationContext());
        EnumResource resource = getEnumResource(key);
        remove(key);
    }
    
    // helper functions

    private void store(EnumResource resource)
        throws RemoteException {
        try {
            resource.store(); 
        } catch (ResourceException e) {
            logger.error(i18n.getMessage("resourceStoreFailed"), e);
            throw getServerFault(
                         i18n.getMessage("resourceStoreFailed"));
        }
    }
    
    private void remove(ResourceKey key) 
        throws RemoteException {
        try {
            this.enumHome.remove(key);
        } catch (NoSuchResourceException e) {
            // ignore 
        } catch (ResourceException e) {
            logger.error(i18n.getMessage("resourceRemoveFailed"), e);
            throw getServerFault(
                         i18n.getMessage("resourceRemoveFailed"));
        }
    }
 
    static void setPullConstraints(Pull pull, 
                                   IterationConstraints constraints) {
        int maxElements = constraints.getMaxElements();
        if (maxElements > 0) {
            pull.setMaxElements(new PositiveInteger(
                                         String.valueOf(maxElements)));
        }
        int maxCharacters = constraints.getMaxCharacters();
        if (maxCharacters > 0) {
            pull.setMaxCharacters(new PositiveInteger(
                                         String.valueOf(maxCharacters)));
        }
        Duration maxTime = constraints.getMaxTime();
        if (maxTime != null) {
            pull.setMaxTime(maxTime);
        }
    }

    static IterationConstraints getPullConstraints(Pull body) {
        int maxElements = 1;
        if (body.getMaxElements() != null) {
            maxElements = body.getMaxElements().intValue();
        }
        
        int maxCharacters = -1;
        if (body.getMaxCharacters() != null) {
            maxCharacters = body.getMaxCharacters().intValue();
        }
        
        Duration maxTime = body.getMaxTime();
        
        return new IterationConstraints(maxElements, maxCharacters, maxTime);
    }

    static EnumExpiration toEnumExpiration(ExpirationType expires) {
        if (expires == null) {
            return null;
        }
        try {
            Duration duration = 
                expires.getNonNegativeDurationTypeValue();
            return new EnumExpiration(duration);
        } catch (Exception e) {
            try {
                Calendar cal = expires.getDateTimeValue();
                return new EnumExpiration(cal);
            } catch (Exception ee) {
                throw new IllegalArgumentException(
                                 i18n.getMessage("invalidExpirationTime00"));
            }
        }
    }
    
    static ExpirationType toExpirationType(EnumExpiration expires) {
        if (expires == null) {
            return null;
        }
        ExpirationType ex = null;
        if (expires.getCalendar() != null) {
            ex = new ExpirationType();
            ex.setDateTimeValue(expires.getCalendar());
        } else if (expires.getDuration() != null) {
            ex = new ExpirationType();
            ex.setNonNegativeDurationTypeValue(expires.getDuration());
        } 
        return ex;
    }
}
