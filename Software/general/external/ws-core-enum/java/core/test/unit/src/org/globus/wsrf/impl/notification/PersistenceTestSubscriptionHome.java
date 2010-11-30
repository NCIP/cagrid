/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
package org.globus.wsrf.impl.notification;

import org.globus.wsrf.InvalidateResourceMapping;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.container.Lock;
import org.globus.wsrf.container.LockManager;

public class PersistenceTestSubscriptionHome extends SubscriptionHome
    implements InvalidateResourceMapping
{

    public void invalidate(ResourceKey key) throws ResourceException
    {
        Lock lock = this.lockManager.getLock(key);
        try 
        {
            lock.acquire();
        } 
        catch (InterruptedException e) 
        {
            throw new ResourceException(e);
        }
        try 
        {
            this.resources.remove(key);
        }
        finally 
        {
            lock.release();
        }
    }
}
