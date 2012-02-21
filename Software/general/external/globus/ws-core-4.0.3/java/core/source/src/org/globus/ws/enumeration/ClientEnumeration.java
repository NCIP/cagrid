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

import java.rmi.RemoteException;

import org.xmlsoap.schemas.ws._2004._09.enumeration.DataSource;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Release;
import org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Pull;
import org.xmlsoap.schemas.ws._2004._09.enumeration.RenewResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Renew;
import org.xmlsoap.schemas.ws._2004._09.enumeration.ItemListType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.GetStatusResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.GetStatus;

import org.globus.wsrf.utils.AdapterProxy;
import org.globus.util.I18n;

import javax.xml.soap.SOAPElement;
import javax.xml.rpc.Stub;

/**
 * Provides basic client-side API for managing enumeration lifetime and
 * retrieving enumeration data.
 */
public class ClientEnumeration {

    private static I18n i18n =
        I18n.getI18n("org.globus.ws.enumeration.error");

    protected DataSource port;
    protected EnumerationContextType context;

    /**
     * Creates <tt>ClientEnumeration</tt> with a given data source port and
     * an enumeration context. 
     *
     * @param port the data source port.
     * @param context the enumeration context.
     * @throws IllegalArgumentException if the port or context parameters are
     *         null.
     */
    public ClientEnumeration(DataSource port,
                             EnumerationContextType context) {
        if (port == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "port"));
        }
        if (context == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "context"));
        }
        this.port = port;
        this.context = context;
    }

    /**
     * Creates <tt>ClientEnumeration</tt> with a given stub and
     * an enumeration context. The stub instance must define all of the
     * operations exposed by the {@link DataSource DataSource} interface.
     *
     * @param stub the data source stub.
     * @param context the enumeration context.
     * @throws IllegalArgumentException if stub or context parameters are null
     *         or if the stub does not define all of the operations of the
     *         {@link DataSource DataSource} interface.
     * @see #getAsDataSource(Stub)
     */
    public ClientEnumeration(Stub stub, 
                             EnumerationContextType context) {
        if (stub == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "stub"));
        }
        if (context == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "context"));
        }
        this.port = getAsDataSource(stub);
        this.context = context;
    }

    /**
     * Exposes the given stub as a data source interface.
     *
     * @param stub the stub to expose as a data source.
     * @return the data source interface dynamic proxy for the stub.
     * @throws IllegalArgumentException if the stub cannot be exposed
     *         as a data source interface (the stub does not define
     *         any or all of the data source methods).
     */
    public static DataSource getAsDataSource(Stub stub) {
        return (DataSource)AdapterProxy.newInstance(
                                     stub,
                                     new Class[] {DataSource.class});
    }

    /**
     * Gets the current enumeration context. The enumeration context can change
     * after {@link #renew(EnumExpiration) renew} or 
     * {@link #pull(IterationConstraints) pull} operations are called or 
     * it can become null after the {@link #release release} operation is 
     * called.
     *
     * @return the current enumeration context.
     */
    public EnumerationContextType getContext() {
        return this.context;
    }

    /**
     * Retrieves the next set of elements of the enumeration.
     * 
     * @return the iteration result (always non-null). It contains the next
     *         set of elements of the enumeration that meet the default 
     *         constraints (see 
     *         {@link IterationConstraints#IterationConstraints()
     *         IterationConstraints}).
     */
    public IterationResult pull() 
        throws RemoteException {
        return pull(null);
    }

    /**
     * Retrieves the next set of elements of the enumeration.
     * 
     * @param constraints the enumeration constraints. Can be null. If null
     *        the default constraints are assumed (see 
     *        ({@link IterationConstraints#IterationConstraints()
     *        IterationConstraints}).
     * @return the iteration result (always non-null). It contains the next
     *         set of elements of the enumeration that meet the specified 
     *         constraints. 
     */
    public IterationResult pull(IterationConstraints constraints) 
        throws RemoteException {
        checkContext();

        Pull pull = new Pull();
        pull.setEnumerationContext(this.context);

        if (constraints != null) {
            EnumProvider.setPullConstraints(pull, constraints);
        }
        
        PullResponse pullResponse = this.port.pullOp(pull);
        
        if (pullResponse.getEnumerationContext() != null) {
            this.context = pullResponse.getEnumerationContext();
        }
        
        ItemListType items = pullResponse.getItems();
        SOAPElement[] elements = (items != null && items.get_any() != null) ?
            items.get_any() : null;
        boolean endOfSequence = (pullResponse.getEndOfSequence() != null);

        return new IterationResult(elements, endOfSequence);
    }
    
    /**
     * Releases the enumeration.
     */
    public void release() 
        throws RemoteException {
        checkContext();

        Release release = new Release();
        release.setEnumerationContext(this.context);
        this.port.releaseOp(release);
        
        this.context = null;
    }
    
    /**
     * Sets a new expiration time/duration of the enumeration.
     *
     * @param expiration the suggested expiration time/duration of the
     *        enumeration. Can be null to configure the enumeration without
     *        an expiration time/duration (the enumeration will not expire).
     * @return the actual expiration time accepted by the service. Can be null
     *         if the enumeration does not have the expiration time/duration.
     */
    public EnumExpiration renew(EnumExpiration expiration) 
        throws RemoteException {
        checkContext();

        Renew request = new Renew();
        request.setEnumerationContext(this.context);
        request.setExpires(EnumProvider.toExpirationType(expiration));
        
        RenewResponse response = this.port.renewOp(request);
        if (response.getEnumerationContext() != null) {
            this.context = response.getEnumerationContext();
        }
        return EnumProvider.toEnumExpiration(response.getExpires());
    }

    /**
     * Gets the expiration time/duration of the enumeration.
     *
     * @return the expiration status of the enumeration. Can be null
     *         if the enumeration does not have expiration time/duration.
     */
    public EnumExpiration getStatus() 
        throws RemoteException {
        checkContext();

        GetStatus request = new GetStatus();
        request.setEnumerationContext(this.context);
        
        GetStatusResponse response = this.port.getStatusOp(request);
        return EnumProvider.toEnumExpiration(response.getExpires());
    }

    private void checkContext() 
        throws RemoteException {
        if (this.context == null) {
            throw new RemoteException(i18n.getMessage("contextReleased"));
        }
    }
    
}
