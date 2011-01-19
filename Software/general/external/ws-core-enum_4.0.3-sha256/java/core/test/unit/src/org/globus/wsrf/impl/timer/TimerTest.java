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
package org.globus.wsrf.impl.timer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;

import javax.naming.Context;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.globus.wsrf.Constants;
import org.globus.wsrf.jndi.JNDIUtils;

import commonj.timers.Timer;
import commonj.timers.TimerListener;
import commonj.timers.TimerManager;

public class TimerTest extends TestCase
{
    private static Context initialContext = null;
    private static final String TIMER_JNDI_CONFIG =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
            "<jndiConfig xmlns=\"http://wsrf.globus.org/jndi/config\">\r\n" +
            "    <global>\r\n" +
            "        <resource name=\"timer/ContainerTimer\" \r\n" +
            "                  type=\"org.globus.wsrf.impl.timer.TimerManagerImpl\"\r\n" +
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
        return new TestSuite(TimerTest.class);
    }

    public void testScheduleDelay() throws Exception
    {
        TimerManager timerManager = getTimerManager();
        Timer timer = timerManager.schedule(new TestTimerListener(), 1000);
        assertEquals(
                false,
                ((TestTimerListener) timer.getTimerListener()).getIRan());
        Thread.sleep(500);
        assertEquals(
                false,
                ((TestTimerListener) timer.getTimerListener()).getIRan());
        Thread.sleep(600);
        assertEquals(
                true,
                ((TestTimerListener) timer.getTimerListener()).getIRan());
        timer.cancel();
    }

    public void testScheduleAbsolute() throws Exception
    {
        TimerManager timerManager = getTimerManager();
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MILLISECOND, 1000);
        Timer timer = timerManager.schedule(new TestTimerListener(),
                                            time.getTime());
        assertEquals(
                false,
                ((TestTimerListener) timer.getTimerListener()).getIRan());
        Thread.sleep(500);
        assertEquals(
                false,
                ((TestTimerListener) timer.getTimerListener()).getIRan());
        Thread.sleep(600);
        assertEquals(
                true,
                ((TestTimerListener) timer.getTimerListener()).getIRan());
        timer.cancel();
    }

    public void testScheduleDelayPeriodic() throws Exception
    {
        TimerManager timerManager = getTimerManager();
        Timer timer = timerManager.schedule(
                new PeriodicTestTimerListener(), 1000, 1000);
        assertEquals(
                0,
                ((PeriodicTestTimerListener)
                timer.getTimerListener()).getCount());
        Thread.sleep(1200);
        assertEquals(
                1,
                ((PeriodicTestTimerListener)
                timer.getTimerListener()).getCount());
        Thread.sleep(1200);
        assertEquals(
                2,
                ((PeriodicTestTimerListener)
                timer.getTimerListener()).getCount());
        timer.cancel();
    }

    public void testScheduleAbsolutePeriodic() throws Exception
    {
        TimerManager timerManager = getTimerManager();
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MILLISECOND, 1000);
        Timer timer = timerManager.schedule(
                new PeriodicTestTimerListener(), time.getTime(), 1000);
        assertEquals(
                0,
                ((PeriodicTestTimerListener)
                timer.getTimerListener()).getCount());
        Thread.sleep(1200);
        assertEquals(
                1,
                ((PeriodicTestTimerListener)
                timer.getTimerListener()).getCount());
        Thread.sleep(1200);
        assertEquals(
                2,
                ((PeriodicTestTimerListener)
                timer.getTimerListener()).getCount());
        timer.cancel();
    }

    public void testSuspendResume() throws Exception
    {
        TimerManager timerManager = getTimerManager();
        Timer timer = timerManager.schedule(
                new PeriodicTestTimerListener(), 2000, 2000);
        Thread.sleep(2200);
        timerManager.suspend();
        Thread.sleep(4200);
        timerManager.resume();
        assertEquals(
                2,
                ((PeriodicTestTimerListener)
                timer.getTimerListener()).getCount());
    }

    public void testCancel() throws Exception
    {
        TimerManager timerManager = getTimerManager();
        Timer timer = timerManager.schedule(new TestTimerListener(), 1000);
        Thread.sleep(500);
        timer.cancel();
        Thread.sleep(600);
        assertEquals(
                false,
                ((TestTimerListener) timer.getTimerListener()).getIRan());
    }

    public void testStop() throws Exception
    {
        TimerManager timerManager = getTimerManager();
        Timer timer = timerManager.schedule(new TestTimerListener(), 1000);
        Thread.sleep(500);
        timerManager.stop();
        Thread.sleep(600);
        assertEquals(
                false,
                ((TestTimerListener) timer.getTimerListener()).getIRan());
    }

    public void testException() throws Exception 
    {
        TimerManager timerManager = getTimerManager();

        Timer timer1 = 
            timerManager.schedule(new ExceptionTimerListener(), 100);
        Thread.sleep(500);
        
        Timer timer2 = 
            timerManager.schedule(new ExceptionTimerListener(), 100);
        Thread.sleep(500);
        
        TestTimerListener testListener = new TestTimerListener();
        Timer timer3 = timerManager.schedule(testListener, 100);
        
        Thread.sleep(500);
        assertTrue(testListener.getIRan());

        timerManager.stop();
    }

    private class ExceptionTimerListener implements TimerListener
    {
        public void timerExpired(Timer timer)
        {
            throw new NullPointerException();
        }
    }

    private static TimerManager getTimerManager()
            throws Exception
    {
        if(initialContext == null)
        {
            InputStream input = new ByteArrayInputStream(
                    TIMER_JNDI_CONFIG.getBytes());
            initialContext = JNDIUtils.initJNDI();
            JNDIUtils.parseJNDIConfig(input);
        }
        return (TimerManager) initialContext.lookup(
                Constants.DEFAULT_TIMER);
    }

    private class TestTimerListener implements TimerListener
    {
        boolean iRan = false;

        public boolean getIRan()
        {
            return this.iRan;
        }

        public void timerExpired(Timer timer)
        {
           this.iRan = true;
        }
    }

    private class PeriodicTestTimerListener implements TimerListener
    {
        int count = 0;

        public int getCount()
        {
            return count;
        }

        public void timerExpired(Timer timer)
        {
            this.count++;
        }
    }
}
