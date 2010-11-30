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
package org.globus.wsrf.utils.io;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import org.globus.wsrf.utils.FilePersistenceHelper;
import org.globus.wsrf.utils.Resources;
import org.globus.util.I18n;

/**
 * Utility functions for indexed object files.
 * 
 * @see IndexedObjectFileReader
 * @see IndexedObjectFileWriter
 */
public class IndexedObjectFileUtils {

    private static I18n i18n =
        I18n.getI18n(Resources.class.getName());

    private static File persistenceDir = getPersistenceDir();
    
    private static File getPersistenceDir() {
        try {
            File dir = FilePersistenceHelper.getDefaultStorageDirectory(
                                                IndexedObjectFileUtils.class);
            FilePersistenceHelper.createStorageDirectory(dir);
            return dir;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Creates an indexed object file in the default storage directory
     * with the specified data.
     *
     * @param items data to store in the file (can be null or empty)
     * @return the created file 
     * @throws IOException if any error occurs
     */
    public static File createIndexedObjectFile(Object[] items)
        throws IOException {
        File file = File.createTempFile("indexed", ".ser", persistenceDir);
        try {
            createIndexedObjectFile(file.getAbsolutePath(), items);
        } catch (IOException e) {
            file.delete();
            throw e;
        }
        return file;
    }

    /**
     * Creates an indexed object file in the default storage directory
     * with the specified data.
     *
     * @param items data to store in the file (can be null or empty)
     * @return the created file 
     * @throws IOException if any error occurs
     */
    public static File createIndexedObjectFile(List items)
        throws IOException {
        File file = File.createTempFile("indexed", ".ser", persistenceDir);
        try {
            createIndexedObjectFile(file.getAbsolutePath(), items);
        } catch (IOException e) {
            file.delete();
            throw e;
        }
        return file;
    }

    /**
     * Create an indexed object file with the specified data.
     * 
     * @param file name of the file to create
     * @param items data to store in the file (can be null or empty)
     * @throws IOException if any error occurs
     */
    public static void createIndexedObjectFile(String file,
                                               Object[] items) 
        throws IOException, 
               FileNotFoundException {
        createIndexedObjectFile(file, 
                                (items == null) ? null : Arrays.asList(items));
    }

    /**
     * Create an indexed object file with the specified data.
     * 
     * @param file name of the file to create
     * @param items data to store in the file (can be null or empty)
     * @throws IOException if any error occurs
     */
    public static void createIndexedObjectFile(String file,
                                               List items) 
        throws IOException, 
               FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException(
                    i18n.getMessage("nullArgument", "file"));
        }
        IndexedObjectFileWriter dataFile = null;
        try {
            dataFile = new IndexedObjectFileWriter(file);
            
            if (items != null) {
                for (int i=0;i<items.size();i++) {
                    dataFile.writeObject(items.get(i));
                }
            }
        } finally {
            if (dataFile != null) {
                try { dataFile.close(); } catch (IOException e) {}
            }
        }
    }
}
