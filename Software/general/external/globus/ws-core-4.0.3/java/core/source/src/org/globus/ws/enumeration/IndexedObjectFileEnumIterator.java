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

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectStreamException;

import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.SerializationException;
import org.globus.wsrf.utils.io.IndexedObjectFileReader;
import org.globus.util.I18n;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A memory efficient implementation of the {@link EnumIterator EnumIterator}
 * interface that can enumerate over data stored in an indexed object file
 * created by {@link org.globus.wsrf.utils.io.IndexedObjectFileWriter 
 * IndexedObjectFileWriter}. 
 * The <tt>IndexedObjectFileEnumIterator</tt> uses the
 * {@link IndexedObjectFileReader IndexedObjectFileReader} to read the indexed
 * object file and quickly locate and retrieve the next set of objects of the 
 * enumeration.
 * The <tt>IndexedObjectFileEnumIterator</tt> can be used with transient and
 * persistent types of enumerations.
 */
public class IndexedObjectFileEnumIterator 
    implements EnumIterator, 
               Serializable {
    
    private static I18n i18n =
        I18n.getI18n("org.globus.ws.enumeration.error");

    private static Log logger =
        LogFactory.getLog(IndexedObjectFileEnumIterator.class.getName());

    private String dataFile;
    private int index;
    private QName itemName;
    private boolean autoDelete = true;
    
    protected IndexedObjectFileEnumIterator() {}

    /**
     * Creates <tt>IndexedObjectFileEnumIterator</tt> with a given file
     * and an item name.
     * <BR><BR><B>Note: </B>
     * <I>By default the file will be deleted on release or when an end of
     * sequence is reached.</I>
     *
     * @param file the name of the indexed object file.
     * @param itemName the enumeration items will be serialized into element of
     *        this name. The <tt>itemName</tt> should be <tt>null</tt>
     *        for items of type {@link SOAPElement SOAPElement} or 
     *        {@link org.w3c.dom.Element Element}. It must not be <tt>null</tt>
     *        for all other types.
     * @throws IOException if the file does not exist or is corrupted.
     */
    public IndexedObjectFileEnumIterator(File file, QName itemName) 
        throws IOException {
        this((file == null) ? null : file.getAbsolutePath(),
             itemName);
    }
    
    /**
     * Creates <tt>IndexedObjectFileEnumIterator</tt> with a given file
     * and an item name.
     * <BR><BR><B>Note: </B>
     * <I>By default the file will be deleted on release or when an end of
     * sequence is reached.</I>
     *
     * @param file the name of the indexed object file.
     * @param itemName the enumeration items will be serialized into element of
     *        this name. The <tt>itemName</tt> should be <tt>null</tt>
     *        for items of type {@link SOAPElement SOAPElement} or 
     *        {@link org.w3c.dom.Element Element}. It must not be <tt>null</tt>
     *        for all other types.
     * @throws IOException if the file does not exist or is corrupted.
     */
    public IndexedObjectFileEnumIterator(String file, QName itemName) 
        throws IOException {
        if (file == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "file"));
        }
        this.dataFile = file;
        this.itemName = itemName;
        this.index = 0;

        getSize();
    }
    
    private int getSize() 
        throws IOException {
        IndexedObjectFileReader reader = null;
        try {
            reader = new IndexedObjectFileReader(this.dataFile);
            return reader.getIndexSize();
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) {}
            }
        }
    }

    /**
     * Sets whether the file of this iterator should be deleted
     * on release or when an end of a sequence is reached.
     *
     * @param delete If true, the file will be deleted. If false,
     *        the file will not be deleted.
     */
    public void setEnableAutoDelete(boolean delete) {
        this.autoDelete = delete;
    }
    
    /**
     * Gets whether the file will be deleted on release or when an end of
     * a sequence is reached.
     *
     * @return true if the file will be deleted. False, otherwise.
     */
    public boolean isAutoDeleteEnabled() {
        return this.autoDelete;
    }
    
    public IterationResult next(IterationConstraints constraints) {
        if (this.dataFile == null) {
            throw new NoSuchElementException();
        }

        IndexedObjectFileReader reader = null;
        boolean release = false;
        try {
            reader = new IndexedObjectFileReader(this.dataFile);
            int size = reader.getIndexSize();
            
            if (this.index >= size) {
                release = true;
                throw new NoSuchElementException();
            }

            int maxElements = constraints.getMaxElements();
            List dataList = 
                new ArrayList((maxElements > 100) ? 100 : maxElements);
        
            reader.jumpTo(this.index);

            for (int i=0; (i < maxElements) && (this.index < size); i++) {
                Object obj = null;

                while (true) {
                    try {
                        // if IOException is raised then probably all reads
                        // will fail. Therefore, do not handle that exception
                        // here
                        obj = reader.readObject();
                        this.index++;
                        break;
                    } catch (ClassNotFoundException e) {
                        this.index++;
                        reader.jumpTo(this.index);
                    } catch (ObjectStreamException e) {
                        this.index++;
                        reader.jumpTo(this.index);
                    }
                }

                try {
                    SOAPElement elem =
                        ObjectSerializer.toSOAPElement(obj, this.itemName);
                    dataList.add(elem);
                } catch (SerializationException e) {
                    // let's skip this element
                    logger.debug("Failed to serialize enumeration item", e);
                    i--;
                }
            }

            boolean endOfSequence = (this.index >= size);
            release = endOfSequence;
            return IterationResult.create(dataList, endOfSequence);
        } catch (IOException e) {
            release = true;
            logger.error("Failed to open or read enumeration data file", e);
            throw new NoSuchElementException();
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) {}
            }
            if (release) {
                release();
            }
        }
    }
    
    public void release() {
        if (this.dataFile == null) {
            return;
        }
        
        if (this.autoDelete) {
            File f = new File(this.dataFile);
            f.delete();
        }
        
        this.dataFile = null;
    }
    
}
