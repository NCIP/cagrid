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
package org.globus.wsrf.samples.counter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.PersistenceCallback;
import org.globus.wsrf.RemoveCallback;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.utils.FilePersistenceHelper;
import org.globus.wsrf.utils.SubscriptionPersistenceUtils;

/**
 * Persistent Resource Implementation
 */
public class PersistentCounter
    extends Counter
    implements PersistenceCallback, RemoveCallback {

    private FilePersistenceHelper persistenceHelper;

    public void setValue(int value) {
        super.setValue(value);
        try {
            store();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setTerminationTime(Calendar time) {
        super.setTerminationTime(time);
        try {
            store();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * User-defined function.
     *
     * @return the resource key
     */
    public Object create() throws Exception {
        Object key = super.create();
        store();
        return key;
    }

    /**
     * Called when activating a Counter resource by ResourceHomeImpl
     */
    public void load(ResourceKey key) throws ResourceException {
        File file = getKeyAsFile(key.getValue());
        if (!file.exists()) {
            throw new NoSuchResourceException();
        }
        initialize(key.getValue());
        FileInputStream fis = null;
        int value = 0;
        try {
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            value = ois.readInt();
            this.terminationTime = (Calendar)ois.readObject();
            SubscriptionPersistenceUtils.loadSubscriptionListeners(
                this.getTopicList(), ois);
        } catch (Exception e) {
            throw new ResourceException("Failed to load resource", e);
        } finally {
            if (fis != null) {
                try { fis.close(); } catch (Exception ee) {}
            }
        }
        this.value.set(0, new Integer(value));
    }

    public synchronized void store() throws ResourceException {
        FileOutputStream fos = null;
        File tmpFile = null;

        try {
            tmpFile = File.createTempFile(
                "counter", ".tmp",
                getPersistenceHelper().getStorageDirectory());
            fos = new FileOutputStream(tmpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeInt(((Integer) this.value.get(0)).intValue());
            oos.writeObject(this.terminationTime);
            SubscriptionPersistenceUtils.storeSubscriptionListeners(
                this.getTopicList(), oos);
        } catch (Exception e) {
            if (tmpFile != null) {
                tmpFile.delete();
            }
            throw new ResourceException("Failed to store resource", e);
        } finally {
            if (fos != null) {
                try { fos.close();} catch (Exception ee) {}
            }
        }

        File file = getKeyAsFile(this.key);
        if (file.exists()) {
            file.delete();
        }
        if (!tmpFile.renameTo(file)) {
            tmpFile.delete();
            throw new ResourceException("Failed to store resource");
        }
    }

    private File getKeyAsFile(Object key)
        throws InvalidResourceKeyException {
        if (key instanceof Integer) {
            return getPersistenceHelper().getKeyAsFile(key);
        } else {
            throw new InvalidResourceKeyException();
        }
    }

    public void remove() throws ResourceException {
        getPersistenceHelper().remove(this.key);
    }

    protected synchronized FilePersistenceHelper getPersistenceHelper() {
        if (this.persistenceHelper == null) {
            try {
                this.persistenceHelper =
                    new FilePersistenceHelper(getClass(), ".ser");
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return this.persistenceHelper;
    }

}
