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
package org.globus.wsrf.container;

import EDU.oswego.cs.dl.util.concurrent.ReentrantLock;

/**
 * This class is a simple reentrant lock implementation.
 */
public class Lock extends ReentrantLock {

    protected boolean removingLock;

    private LockManager lockManager;
    private Object key;
    
    public Lock(LockManager lockManager, Object key) {
        this.lockManager = lockManager;
        this.key = key;

        this.removingLock = false;
    }

    public boolean acquire(boolean removingLock) 
        throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return acquire(Thread.currentThread(), removingLock);
    }

    protected synchronized boolean acquire(Thread caller, boolean removingLock)
        throws InterruptedException {
        if (this.owner_ == null) {
            this.owner_ = caller;
            this.holds_ = 1;
            this.removingLock = removingLock;
            return true;
        } else if (this.owner_ == caller) {
            ++this.holds_;
            return true;
        } else if (this.removingLock) {
            return false;
        } else {
            try {  
                while (this.owner_ != null) {
                    wait(); 
                }
                this.owner_ = caller;
                this.holds_ = 1;
                this.removingLock = removingLock;
            } catch (InterruptedException ex) {
                notify();
                throw ex;
            }
            return true;
        }
    }
    
    public synchronized void release() {
        if (Thread.currentThread() != this.owner_) {
            throw new Error("Illegal Lock usage"); 
        }
        
        this.holds_ --;
        if (this.holds_ == 0) {
            reset();
        }
    }
    
    public synchronized void release(long n) {
        if (Thread.currentThread() != this.owner_ || n > this.holds_) {
            throw new Error("Illegal Lock usage"); 
        }
        
        this.holds_ -= n;
        if (this.holds_ == 0) {
            reset();
        }
    }

    // called only when holds becomes 0
    protected synchronized void reset() {
        this.owner_ = null;
        this.removingLock = false;
        if (this.lockManager != null) {
            this.lockManager.removeLock(this.key);
        }
        notify();
    }
 
}
