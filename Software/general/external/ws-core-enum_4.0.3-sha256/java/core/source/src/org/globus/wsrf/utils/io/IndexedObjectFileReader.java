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

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * Reads Java objects from an indexed file. The indexing allows for random
 * access to any object in the file. 
 * <BR><BR><B>Note: </B><I>This class is not thread-safe.</I>
 * 
 * @see ObjectInputStream
 */
public class IndexedObjectFileReader {

    private RandomAccessFile raf;
    private byte [] buffer;
    private long indexPointer;
    private List index;
    
    public IndexedObjectFileReader(String file) 
        throws IOException, 
               FileNotFoundException {
        this.raf = new RandomAccessFile(file, "r");
        this.buffer = new byte[1024];
        try {
            init();
        } catch (Exception e) {
            try { this.raf.close(); } catch (IOException ee) {}
            throw new IOException("Error reading object index");
        }
    }
    
    private void init() 
        throws IOException,
               ClassNotFoundException {
        int version = this.raf.readByte();
        this.indexPointer = this.raf.readLong();
        
        long startPos = this.raf.getFilePointer();
        
        this.raf.seek(this.indexPointer);
        this.index = (List)readObjectSub();
        
        this.raf.seek(startPos);
    }

    private Object readObjectSub() 
        throws IOException,
               ClassNotFoundException {
        int length = this.raf.readInt();
        
        // ensure we have large enough buffer
        if (this.buffer.length < length) {
            this.buffer = new byte[length];
        }
        
        // read the data
        this.raf.readFully(this.buffer, 0, length);
        
        // deserialize the data
        ByteArrayInputStream bis = 
            new ByteArrayInputStream(this.buffer, 0, length);
        ObjectInputStream in = new ObjectInputStream(bis);
        
        return in.readObject();
    }
    
    /**
     * Reads a Java object from the file.
     *
     * @throws IOException if any error occurs.
     * @throws ClassNotFoundException if cannot find the class for the object
     *         in the file.
     * @throws EOFException if end of the file is reached.
     * @return the object read from the file.
     * @see ObjectInputStream#readObject()
     */
    public Object readObject()
        throws IOException,
               ClassNotFoundException {
        if (this.raf.getFilePointer() >= this.indexPointer) {
            throw new EOFException();
        }
        return readObjectSub();
    }
    
    /**
     * Gets the size of the index (the number of objects in the file).
     *
     * @return the size of the index.
     */
    public int getIndexSize() {
        return this.index.size();
    }

    /**
     * Jumps to the object at the specified position in the file.
     *
     * @param index the position to jump to.
     * @throws IOException if the position specified is invalid or any other
     *                     error occurs.
     */
    public void jumpTo(int index) 
        throws IOException {
        if (index >= this.index.size()) {
            throw new IOException();
        }
        Long pos = (Long)this.index.get(index);
        this.raf.seek(pos.longValue());
    }
    
    /**
     * Closes the file.
     *
     * @throws IOException if any error occurs.
     */
    public void close() 
        throws IOException {
        // close the file
        this.raf.close();
    }
    
}
