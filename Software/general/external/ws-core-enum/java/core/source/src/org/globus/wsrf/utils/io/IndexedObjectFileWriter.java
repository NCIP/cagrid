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

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

/**
 * Writes Java objects to an indexed file. The indexing allows for random
 * access to any object in the file. Only Java objects that implement
 * the {@link java.io.Serializable Serializable} interface can be written
 * to the file.
 * <BR><BR><B>Note: </B><I>This class is not thread-safe.</I>
 * 
 * @see ObjectOutputStream
 */
public class IndexedObjectFileWriter {

    private ByteArrayOutputStream bos;
    private RandomAccessFile raf;
    private long indexPointer;
    private List index;
    
    public IndexedObjectFileWriter(String file) 
        throws IOException, 
               FileNotFoundException {
        this.raf = new RandomAccessFile(file, "rw");
        this.index = new ArrayList();
        this.bos = new ByteArrayOutputStream();
        try {
            init();
        } catch (IOException e) {
            try { this.raf.close(); } catch (IOException ee) {}
            throw new IOException("Error writting file header");
        }
    }
    
    private void init() 
        throws IOException {
        // write header info: version (byte), indexPosition (long)
        this.raf.writeByte(0);
        
        this.indexPointer = raf.getFilePointer();
        this.raf.writeLong(-1L);
    }

    private void writeObjectSub(Object obj)
        throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(this.bos);
        out.writeObject(obj);
        out.close();
        
        byte [] data = this.bos.toByteArray();
        this.raf.writeInt(data.length);
        this.raf.write(data);
        
        this.bos.reset();
    }
    
    /**
     * Writes the Java object to the file.
     *
     * @param obj object to write.
     * @throws IOException if any error occurs.
     * @see ObjectOutputStream#writeObject(Object)
     */
    public void writeObject(Object obj)
        throws IOException {
        long pos = this.raf.getFilePointer();
        writeObjectSub(obj);
        this.index.add(new Long(pos));
    }

    /**
     * Gets the current size of the index (the number of object written
     * so far).
     *
     * @return the current size of the index.
     */
    public int getIndexSize() {
        return this.index.size();
    }
    
    /**
     * Writes the object index and closes the file. This function must be
     * called, otherwise an incomplete file might be created.
     *
     * @throws IOException if any error occurs.
     */
    public void close() 
        throws IOException {
        long endPos = raf.getFilePointer();

        // write index
        writeObjectSub(this.index);
                
        // update index pointer
        this.raf.seek(this.indexPointer);
        this.raf.writeLong(endPos);

        // close the file
        this.raf.close();
    }
    
}
