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

import java.rmi.RemoteException;

import org.globus.util.I18n;
import org.globus.wsrf.utils.Resources;

/**
 * Shutdown service implementation.
 */
public class ShutdownService {

    private static final I18n i18n =
        I18n.getI18n(Resources.class.getName());

    public void shutdown(boolean hard) throws RemoteException {
        /* if hard shutdown supported schedule
         * System.exit thread otherwise error out
         */
        if (hard) {
            if (Boolean.getBoolean(ServiceContainer.HARD_SHUTDOWN)) {
                Thread shutdownThread = (new Thread() {
                        public void run() {
                            System.exit(0);
                        }
                    });
                shutdownThread.setDaemon(true);
                shutdownThread.start();
            } else {
                throw new RemoteException(
                    i18n.getMessage("hardShutdownNotSupported"));
            }
        } else {
            Thread shutdownThread = 
                new ServiceContainerCollection.ShutdownThread();
            shutdownThread.setDaemon(true);
            shutdownThread.start();
        }
    }
    
}
