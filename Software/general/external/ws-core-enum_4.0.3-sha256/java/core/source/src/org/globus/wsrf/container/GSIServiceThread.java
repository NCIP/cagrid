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

import java.io.OutputStream;
import java.security.cert.X509Certificate;

import javax.security.auth.Subject;

import org.apache.axis.AxisEngine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.axis.gsi.GSIConstants;
import org.globus.gsi.gssapi.GSSConstants;
import org.globus.gsi.gssapi.JaasGssUtil;
import org.globus.gsi.gssapi.jaas.GlobusPrincipal;
import org.globus.gsi.gssapi.net.GssSocket;
import org.globus.gsi.gssapi.net.GssSocketFactory;
import org.globus.util.I18n;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.descriptor.ContainerSecurityConfig;
import org.globus.wsrf.utils.Resources;
import org.gridforum.jgss.ExtendedGSSContext;
import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSManager;

class GSIServiceThread extends ServiceThread {

    private static Log logger = 
        LogFactory.getLog(GSIServiceThread.class.getName());

    private static I18n i18n = I18n.getI18n(Resources.class.getName());

    public GSIServiceThread(ServiceRequestQueue queue,
                            ServiceThreadPool pool,
                            AxisEngine engine) {
        super(queue, pool, engine);
    }

    protected String getProtocol() {
        return "https";
    }

    protected void process(ServiceRequest request) {
        logger.debug(getName() + ": processing requests");

        GSSManager manager = ExtendedGSSManager.getInstance();

        GssSocket gsiSocket = null;
        OutputStream out = null;

        try {
            ContainerSecurityConfig config =
                ContainerSecurityConfig.getConfig();
            config.refresh();
            Subject containerSub = config.getSubject();
            if (containerSub == null) {
                throw new RuntimeException(i18n.getMessage("noValidCreds"));
            }
            GSSCredential credentials = 
                JaasGssUtil.getCredential(containerSub);

            ExtendedGSSContext context =
                (ExtendedGSSContext) manager.createContext(credentials);

            context.setOption(GSSConstants.GSS_MODE, GSIConstants.MODE_SSL);

            context.setOption(GSSConstants.ACCEPT_NO_CLIENT_CERTS,
                              Boolean.TRUE);

            GssSocketFactory factory = GssSocketFactory.getDefault();

            gsiSocket =
                (GssSocket) factory.createSocket(
                    request.getSocket(), null, 0, context
                );

            // server socket
            gsiSocket.setUseClientMode(false);
            gsiSocket.setAuthorization(null);

            // forces handshake
            out = gsiSocket.getOutputStream();

            String globusID = context.getSrcName().toString();

            logger.debug(getName() + ": Authenticated globus user: " + globusID);

            Subject subject = getSubject();
            subject.getPrincipals().add(new GlobusPrincipal(globusID));

            if (context instanceof ExtendedGSSContext) {
                ExtendedGSSContext extGss = (ExtendedGSSContext) context;
                X509Certificate[] certs =
                    (X509Certificate[]) extGss
                    .inquireByOid(GSSConstants.X509_CERT_CHAIN);
                if (certs != null) {
                    subject.getPublicCredentials().add(certs);
                }
            }
            
            this.msgContext
                .setProperty(Constants.TRANSPORT_SECURITY_CONTEXT, context);
            
            if (context.getConfState()) {
                this.msgContext.setProperty(Constants.GSI_TRANSPORT,
                                            Constants.ENCRYPTION);
            } else if (context.getIntegState()) {
                this.msgContext.setProperty(Constants.GSI_TRANSPORT,
                                            Constants.SIGNATURE);
            } else {
                this.msgContext.setProperty(Constants.GSI_TRANSPORT,
                                            Constants.NONE);
            }
        } catch (Exception e) {
            if (gsiSocket == null) {
                try {
                    request.getSocket().close();
                } catch (Exception ee) {}
            } else {
                try {
                    gsiSocket.close();
                } catch (Exception ee) {}
            }

            logger.error(i18n.getMessage("serverFault00"), e);
            return;
        }

        ServiceRequest req =
            new ServiceRequest(gsiSocket, request.getServerSocket());
        super.process(req);
    }

    protected Subject getSubject() {
        Subject subject =
            (Subject) msgContext.getProperty(Constants.PEER_SUBJECT);

        if (subject == null) {
            subject = new Subject();
            msgContext.setProperty(Constants.PEER_SUBJECT, subject);
        }

        return subject;
    }
}
