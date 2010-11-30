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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import org.apache.axis.components.uuid.UUIDGen;
import org.apache.axis.components.uuid.UUIDGenFactory;
import org.globus.util.I18n;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.PersistenceCallback;
import org.globus.wsrf.RemoveCallback;
import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceIdentifier;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceLifetime;
import org.globus.wsrf.TerminationTimeRejectedException;
import org.globus.wsrf.utils.FilePersistenceHelper;

/**
 * Represents a persistent enumeration context. The enumeration resource has
 * lifetime information and a reference to the
 * {@link EnumIterator EnumIterator} object that provides access to the
 * enumeration data. The enumeration resource also contains 
 * {@link VisibilityProperties VisibilityProperties} 
 * that control access to the enumeration resource and data.
 */
public class EnumResource
    implements Resource,
               ResourceLifetime,
               ResourceIdentifier,
               PersistenceCallback,
               RemoveCallback {

    private static I18n i18n =
        I18n.getI18n("org.globus.ws.enumeration.error");

    private static final UUIDGen uuidGen = 
        UUIDGenFactory.getUUIDGen();

    private static FilePersistenceHelper persistenceHelper =
        getPersistenceHelper();

    protected Object key;

    protected boolean isDuration;
    protected Calendar terminationTime;
    protected EnumIterator iter;
    protected VisibilityProperties visibility;

    public EnumResource() {}
    
    /**
     * Creates <tt>EnumResource</tt> with given iterator and visibility
     * properties.
     * 
     * @param iter the data iterator. Cannot be null.
     * @param visibility the visibility properties. Cannot be null.
     */
    public EnumResource(EnumIterator iter, VisibilityProperties visibility) {
        if (iter == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "iter"));
        }
        if (visibility == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "visibility"));
        }
        this.iter = iter;
        this.visibility = visibility;
        this.key = uuidGen.nextUUID();
    }

    /**
     * Gets the data iterator.
     *
     * @return the data iterator.
     */
    public EnumIterator getIterator() {
        return this.iter;
    }

    /**
     * Gets the visibility properties.
     *
     * @return the visibility properties.
     */
    public VisibilityProperties getVisibility() {
        return this.visibility;
    }

    public Object getID() {
        return this.key;
    }

    public void setTerminationTime(Calendar time) {
        if (time != null && time.before(Calendar.getInstance())) {
            throw new TerminationTimeRejectedException();
        }
        this.terminationTime = time;
    }
    
    public Calendar getTerminationTime() {
        return this.terminationTime;
    }
    
    /**
     * Sets whether this resource lifetime is expressed as a duration
     * or a specific time/date.
     *
     * @param duration if true, the lifetime of this resource will be returned
     *        as a duration. If false, the lifetime of this resource will
     *        be returned as a specific time/date.
     */
    public void setDuration(boolean duration) {
        this.isDuration = duration;
    }
    
    /**
     * Gets whether this resource lifetime is expressed as a duration
     * or a specific time/date.
     *
     * @return if true, the lifetime of this resource will be returned
     *         as a duration. If false, the lifetime of this resource will
     *         be returned as a specific time/date.
     */
    public boolean isDuration() {
        return this.isDuration;
    }
    
    public Calendar getCurrentTime() {
        return Calendar.getInstance();
    }
    
    public void load(ResourceKey key) throws ResourceException {
        File file = getKeyAsFile(key.getValue());
        if (!file.exists()) {
            throw new NoSuchResourceException();
        }
        
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            this.isDuration = ois.readBoolean();
            this.terminationTime = (Calendar)ois.readObject();
            this.iter = (EnumIterator)ois.readObject();
            this.visibility = (VisibilityProperties)ois.readObject();
            this.key = key.getValue();
            
        } catch (Exception e) {
            throw new ResourceException(
                               i18n.getMessage("resourceLoadFailed"), e);
        } finally {
            if (fis != null) {
                try { fis.close(); } catch (Exception ee) {}
            }
        }
    }

    public synchronized void store() throws ResourceException {
        FileOutputStream fos = null;
        File tmpFile = null;

        try {
            tmpFile = File.createTempFile(
                "enum", ".tmp",
                persistenceHelper.getStorageDirectory());
            fos = new FileOutputStream(tmpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeBoolean(this.isDuration);
            oos.writeObject(this.terminationTime);
            oos.writeObject(this.iter);
            oos.writeObject(this.visibility);

        } catch (Exception e) {
            if (tmpFile != null) {
                tmpFile.delete();
            }
            throw new ResourceException(
                               i18n.getMessage("resourceStoreFailed"), e);
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
            throw new ResourceException(
                               i18n.getMessage("resourceStoreFailed"));
        }
    }

    public void remove() throws ResourceException {
        this.iter.release();
        persistenceHelper.remove(this.key);
    }

    private File getKeyAsFile(Object key)
        throws InvalidResourceKeyException {
        if (key instanceof String) {
            return persistenceHelper.getKeyAsFile(key);
        } else {
            throw new InvalidResourceKeyException();
        }
    }

    private static FilePersistenceHelper getPersistenceHelper() {
        try {
            return new FilePersistenceHelper(EnumResource.class, ".ser");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
