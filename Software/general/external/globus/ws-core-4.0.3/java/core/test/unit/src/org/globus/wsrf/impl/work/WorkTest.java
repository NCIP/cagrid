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
package org.globus.wsrf.impl.work;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

import javax.naming.Context;

import org.globus.wsrf.Constants;
import org.globus.wsrf.jndi.JNDIUtils;

import commonj.work.Work;
import commonj.work.WorkEvent;
import commonj.work.WorkItem;
import commonj.work.WorkManager;
import commonj.work.WorkListener;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class
    WorkTest extends TestCase
{
    private static Context initialContext = null;
    private static final String TIMER_JNDI_CONFIG =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
            "<jndiConfig xmlns=\"http://wsrf.globus.org/jndi/config\">\r\n" +
            "    <global>\r\n" +
            "        <resource name=\"wm/ContainerWorkManager\" \r\n" +
            "                  type=\"org.globus.wsrf.impl.work.WorkManagerImpl\"\r\n" +
            "                  >\r\n" +
            "            <resourceParams>\r\n" +
            "                <parameter>\r\n" +
            "                    <name>\r\n" +
            "                        factory\r\n" +
            "                    </name>\r\n" +
            "                    <value>\r\n" +
            "                        org.apache.naming.factory.BeanFactory\r\n" +
            "                    </value>\r\n" +
            "                </parameter>\r\n" +
            "            </resourceParams>\r\n" +
            "        </resource>\r\n" +
            "    </global>\r\n" +
            "</jndiConfig>\r\n";

    public static Test suite()
    {
        return new TestSuite(WorkTest.class);
    }

    public void testScheduleWork() throws Exception
    {
        WorkManager workManager = getWorkManager();
        TestWork work = new TestWork(1000);
        WorkItem workItem = workManager.schedule(work);
        waitForStatus(workItem, WorkEvent.WORK_STARTED, 2000);
        assertEquals(WorkEvent.WORK_STARTED, workItem.getStatus());
        waitForStatus(workItem, WorkEvent.WORK_COMPLETED, 2000);
        assertEquals(WorkEvent.WORK_COMPLETED, workItem.getStatus());
        assertEquals(true, work.getIRan());
    }


    public void testScheduleDaemonWork() throws Exception
    {
        WorkManager workManager = getWorkManager();
        TestWork work = new TestWork(1000, true);
        WorkItem workItem = workManager.schedule(work);
        waitForStatus(workItem, WorkEvent.WORK_STARTED, 2000);
        assertEquals(WorkEvent.WORK_STARTED, workItem.getStatus());
        waitForStatus(workItem, WorkEvent.WORK_COMPLETED, 2000);
        assertEquals(WorkEvent.WORK_COMPLETED, workItem.getStatus());
        assertEquals(true, work.getIRan());
    }

    public void testWorkListener() throws Exception
    {
        WorkManager workManager = getWorkManager();
        TestWork work = new TestWork(1000);
        TestWorkListener listener = new TestWorkListener();
        workManager.schedule(work, listener);
        waitForStatus(listener, WorkEvent.WORK_STARTED, 2000);
        assertEquals(WorkEvent.WORK_STARTED, listener.getStatus());
        waitForStatus(listener, WorkEvent.WORK_COMPLETED, 2000);
        assertEquals(WorkEvent.WORK_COMPLETED, listener.getStatus());
        assertEquals(true, work.getIRan());
    }

    public void testWaitForAllImmediate() throws Exception
    {
        WorkManager workManager = getWorkManager();
        TestWork work1 = new TestWork(1000);
        TestWork work2 = new TestWork(3000);
        List workList = new ArrayList();
        WorkItem item1 = workManager.schedule(work1);
        WorkItem item2 = workManager.schedule(work2);
        workList.add(item1);
        waitForStatus(item1, WorkEvent.WORK_COMPLETED, 2000);
        assertEquals(true, workManager.waitForAll(workList,
                                                  WorkManager.IMMEDIATE));
        workList.add(item2);
        assertEquals(false, workManager.waitForAll(workList,
                                                   WorkManager.IMMEDIATE));
    }

    public void testWaitForAllIndefinite() throws Exception
    {
        WorkManager workManager = getWorkManager();
        TestWork work1 = new TestWork(1000);
        TestWork work2 = new TestWork(3000);
        List workList = new ArrayList();
        WorkItem item1 = workManager.schedule(work1);
        WorkItem item2 = workManager.schedule(work2);
        workList.add(item1);
        workList.add(item2);
        assertEquals(true, workManager.waitForAll(workList,
                                                  WorkManager.INDEFINITE));
    }

    public void testWaitForAll() throws Exception
    {
        WorkManager workManager = getWorkManager();
        TestWork work1 = new TestWork(1000);
        TestWork work2 = new TestWork(5000);
        List workList = new ArrayList();
        WorkItem item1 = workManager.schedule(work1);
        WorkItem item2 = workManager.schedule(work2);
        workList.add(item1);
        assertEquals(true, workManager.waitForAll(workList, 3000));
        workList.add(item2);
        assertEquals(false, workManager.waitForAll(workList, 1000));
    }

    public void testWaitForAnyImmediate() throws Exception
    {
        WorkManager workManager = getWorkManager();
        TestWork work1 = new TestWork(1000);
        TestWork work2 = new TestWork(3000);
        List workList = new ArrayList();
        WorkItem item1 = workManager.schedule(work1);
        WorkItem item2 = workManager.schedule(work2);
        workList.add(item1);
        workList.add(item2);
        waitForStatus(item1, WorkEvent.WORK_COMPLETED, 2000);
        assertEquals(1, workManager.waitForAny(
                workList,
                WorkManager.IMMEDIATE).size());
        waitForStatus(item2, WorkEvent.WORK_COMPLETED, 5000);
        assertEquals(2, workManager.waitForAny(
                workList,
                WorkManager.IMMEDIATE).size());
    }

    public void testWaitForAny() throws Exception
    {
        WorkManager workManager = getWorkManager();
        TestWork work1 = new TestWork(1000);
        TestWork work2 = new TestWork(5000);
        List workList = new ArrayList();
        WorkItem item1 = workManager.schedule(work1);
        WorkItem item2 = workManager.schedule(work2);
        workList.add(item1);
        workList.add(item2);
        assertEquals(1, workManager.waitForAny(
                workList,
                3000).size());
        assertEquals(2, workManager.waitForAny(
                workList,
                10000).size());
    }

    private static WorkManager getWorkManager()
            throws Exception
    {
        if(initialContext == null)
        {
            InputStream input = new ByteArrayInputStream(
                    TIMER_JNDI_CONFIG.getBytes());
            initialContext = JNDIUtils.initJNDI();
            JNDIUtils.parseJNDIConfig(input);
        }

        return (WorkManager) initialContext.lookup(
                Constants.DEFAULT_WORK_MANAGER);
    }

    private void waitForStatus(WorkItem workItem, int status, int maxWait)
        throws InterruptedException
    {
        int waited = 0;
        while(waited < maxWait &&
              workItem.getStatus() != status)
        {
            Thread.sleep(20);
            waited += 20;
        }
    }

    private class TestWork implements Work
    {
        boolean daemon = false;
        boolean iRan = false;
        boolean release = false;
        long sleepTime = 0;

        public TestWork(long sleepTime, boolean daemon)
        {
            this.daemon = daemon;
            this.sleepTime = sleepTime;
        }

        public TestWork(long sleepTime)
        {
            this(sleepTime, false);
        }

        public boolean getIRan()
        {
            return this.iRan;
        }

        public void release()
        {
            this.release = true;
        }

        public boolean isDaemon()
        {
            return this.daemon;
        }

        public void run()
        {
            long timeSlept = 0;
            while(timeSlept < this.sleepTime && !this.release)
            {
                try
                {
                    Thread.sleep(10);
                    timeSlept += 10;
                }
                catch(InterruptedException e)
                {
                }
            }
            this.iRan = true;
        }
    }

    private class TestWorkListener implements WorkListener, WorkItem
    {
        int status = -1;

        public void workAccepted(WorkEvent event)
        {
            this.status = event.getType();
        }

        public void workRejected(WorkEvent event)
        {
            this.status = event.getType();
        }

        public void workStarted(WorkEvent event)
        {
            this.status = event.getType();
        }

        public void workCompleted(WorkEvent event)
        {
            this.status = event.getType();
        }

        public int getStatus()
        {
            return status;
        }
    }
}
