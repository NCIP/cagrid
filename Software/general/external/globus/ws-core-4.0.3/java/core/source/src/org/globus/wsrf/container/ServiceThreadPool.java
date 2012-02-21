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

import org.apache.axis.AxisEngine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Vector;

/**
 * This class is responsible for managing a set of threads.
 * New threads can be added to the pool at any given time.
 */
public class ServiceThreadPool {
    
    private Vector serviceThreads = new Vector();
    protected ServiceRequestQueue queue;
    protected AxisEngine engine;

    static Log logger = 
        LogFactory.getLog(ServiceThreadPool.class.getName());

    public ServiceThreadPool(ServiceRequestQueue queue, AxisEngine engine) {
        this.queue = queue;
        this.engine = engine;
    }

    public void startThreads(int threads) {
        for (int i = 0; i < threads; i++) {
            ServiceThread serviceThread = createThread();
            this.serviceThreads.addElement(serviceThread);
            serviceThread.start();
        }
    }

    public int getThreads() {
        return this.serviceThreads.size();
    }

    protected ServiceThread createThread() {
        return new ServiceThread(this.queue, this, this.engine);
    }

    public void stopThreads(int numThreads) {
        this.queue.stopThreads(numThreads);
    }

    public void stopThreads() {
        this.queue.stopThreads(this.serviceThreads.size());
    }

    public void removeThread(ServiceThread thread) {
        this.serviceThreads.removeElement(thread);
        synchronized (this) {
            notifyAll();
        }
    }

    public synchronized void waitForThreads() 
        throws InterruptedException {
        while (!isDone()) {
            wait();
        }
    }
    
    public synchronized void waitForThreads(int timeout) 
        throws InterruptedException {
        while (timeout > 0 && !isDone()) {
            wait(5000);
            timeout -= 5000;
        }
    }
    
    private boolean isDone() {
        int size = serviceThreads.size();
        return ((size == 0) || 
                (size == 1 && 
                 serviceThreads.contains(Thread.currentThread())));
    }
    
}
