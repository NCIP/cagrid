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
package org.globus.wsrf.impl.notification;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.wsrf.NotifyCallback;

public class TestNotifyCallback implements NotifyCallback
{
    private int notifyCount = 0;
    private QName notificationTopic = null;
    private Object message = null;

    public int getNotifyCount()
    {
        return notifyCount;
    }

    public QName getNotificationTopic()
    {
        return notificationTopic;
    }

    public Object getMessage()
    {
        return message;
    }

    public synchronized void deliver(List topicPath,
                                     EndpointReferenceType producer,
                                     Object message)
    {
        this.message = message;
        this.notificationTopic = (QName) topicPath.get(0);
        this.notifyCount++;
        notify();
    }

    public synchronized boolean waitForCount(int count, int maxWait)
        throws InterruptedException
    {
        int oldNotifyCount;
        while( (oldNotifyCount = this.notifyCount) != count)
        {
            wait(maxWait);
            if (oldNotifyCount == this.notifyCount)
            {
                // timeout
                return false;
            }
        }
        return true;
    }
}
