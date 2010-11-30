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

import javax.xml.namespace.QName;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.InvalidateResourceMapping;
import org.globus.wsrf.impl.ResourceHomeImpl;
import org.globus.wsrf.impl.SimpleResourceKey;
import org.globus.wsrf.container.Lock;

public class NotificationTestHome extends ResourceHomeImpl
    implements InvalidateResourceMapping {

    public static QName KEY_TYPE_NAME =
        new QName(NotificationTestService.TEST_NS, "TestKey");

    public static ResourceKey BAD_KEY =
        new SimpleResourceKey(KEY_TYPE_NAME, new Integer(0));

    public static ResourceKey GOOD_KEY =
        new SimpleResourceKey(KEY_TYPE_NAME, new Integer(1));

    public static ResourceKey GOOD_KEY_2 =
        new SimpleResourceKey(KEY_TYPE_NAME, new Integer(2));

    // this is to ensure the resource object is not GCed
    private NotificationTestResource refResource = null;
    
    public NotificationTestHome() {
        this.keyTypeName = KEY_TYPE_NAME;
    }

    public void initialize() throws Exception {
        super.initialize();
        NotificationTestResource resource = new NotificationTestResource();
        resource.setID((Integer) GOOD_KEY.getValue());
        resource.store();
        add(GOOD_KEY, resource);

        refResource = new NotificationTestResource();
        refResource.setID((Integer) GOOD_KEY_2.getValue());
        refResource.store();
        add(GOOD_KEY_2, refResource);
    }

    public void invalidate(ResourceKey key) throws ResourceException {
        Lock lock = this.lockManager.getLock(key);
        try {
            lock.acquire();
        } catch (InterruptedException e) {
            throw new ResourceException(e);
        }
        try { 
            this.resources.remove(key);
        } finally {
            lock.release();
        }
    }
}
