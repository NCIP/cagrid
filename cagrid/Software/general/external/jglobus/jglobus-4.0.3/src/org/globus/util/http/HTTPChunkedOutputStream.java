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
package org.globus.util.http;

import java.io.OutputStream;
import java.io.IOException;

public class HTTPChunkedOutputStream extends OutputStream {
    
    protected boolean closed = false;
    protected int count;
    protected byte[] buf;
    protected OutputStream out;
    
    public HTTPChunkedOutputStream(OutputStream out) {
        this(out, 4096);
    }
    
    public HTTPChunkedOutputStream(OutputStream out, int size) {
        this.out = out;
        this.buf = new byte[size]; 
        this.count = 0;
    }
    
    private void flushBuffer(boolean flush) throws IOException {
        String chunkLength = Integer.toString(this.count, 16);
        this.out.write(chunkLength.getBytes());
        this.out.write(HTTPProtocol.CRLF.getBytes());
        this.out.write(this.buf, 0, this.count);
        this.out.write(HTTPProtocol.CRLF.getBytes());
        this.count = 0;
        if (flush) {
            this.out.flush();
        }
    }
    
    private void checkNotClosed() throws IOException {
        if (this.closed) {
            throw new IOException("Already closed");
        }
    }
    
    public synchronized void write(int b) 
        throws IOException {
        checkNotClosed();
        if (this.count >= this.buf.length) {
            flushBuffer(false);
        }
        this.buf[this.count++] = (byte)b;
    }
    
    public void write(byte b[]) 
        throws IOException {
        write(b, 0, b.length);
    }
    
    public synchronized void flush() 
        throws IOException {
        if (this.closed) {
            return;
        }
        if (this.count > 0) {
            flushBuffer(true);
        }
    }
    
    public synchronized void close() 
        throws IOException {
        if (this.closed) {
            return;
        }
        flush();
        // signal end of content with a zero-length chunk
        flushBuffer(true);
        this.closed = true;
    }
    
    public synchronized void write(byte b[], int off, int len) 
        throws IOException {
        checkNotClosed();
        int remaining = len;
        int sofar = 0;
        while (remaining > 0) {
            int roomInBuf = this.buf.length - this.count;
            int lenToWrite = Math.min(roomInBuf, len);
            System.arraycopy(b, sofar + off, buf, this.count, lenToWrite);
            sofar += lenToWrite;
            this.count += lenToWrite;
            remaining -= lenToWrite;
            if (this.count >= this.buf.length) {
                flushBuffer(false);
            }
        }
    }
    
}

