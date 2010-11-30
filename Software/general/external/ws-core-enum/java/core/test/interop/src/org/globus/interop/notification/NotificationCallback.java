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
package org.globus.interop.notification;

import java.util.List;

import org.apache.axis.message.addressing.EndpointReferenceType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.globus.wsrf.NotifyCallback;

public class NotificationCallback implements NotifyCallback
{
    static Log logger = 
        LogFactory.getLog(NotificationCallback.class.getName());
    
    /* (non-Javadoc)
     * @see org.globus.wsrf.NotifyCallback#deliver(java.util.List, org.apache.axis.message.addressing.EndpointReferenceType, java.lang.Object)
     */
    public void deliver(List topicPath,
                        EndpointReferenceType producer,
                        Object message)
    {
        logger.debug("Topic path: " + topicPath.toString());
        logger.debug("Message: " + message);
        logger.debug("From: " + producer.getAddress());
    }

}
