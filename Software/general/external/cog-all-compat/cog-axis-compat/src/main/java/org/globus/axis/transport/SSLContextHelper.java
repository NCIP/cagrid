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
package org.globus.axis.transport;

import java.net.Socket;

import org.apache.axis.MessageContext;
import org.globus.axis.util.Util;
import org.globus.gsi.GSIConstants;
import org.globus.gsi.TrustedCertificates;
import org.globus.gsi.gssapi.GSSConstants;
import org.globus.gsi.gssapi.auth.Authorization;
import org.globus.gsi.gssapi.auth.GSSAuthorization;
import org.globus.gsi.gssapi.auth.HostAuthorization;
import org.globus.gsi.gssapi.net.GssSocket;
import org.globus.gsi.gssapi.net.GssSocketFactory;
import org.gridforum.jgss.ExtendedGSSContext;
import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

/**
 * This is meant to be used on a SOAP Client to call a SOAP server.
 * <BR><I>This code is based on Axis HTTPSender.java code.</I>
 */
public class SSLContextHelper {

    private String host;
    private int port;

    private Authorization myAuth;
    private ExtendedGSSContext myContext;

    public SSLContextHelper(MessageContext msgContext, 
                            String host, int port) 
        throws GSSException {
        Authorization auth = (Authorization)Util.getProperty(msgContext, 
                                         GSIHTTPTransport.GSI_AUTHORIZATION);
        Boolean anonymous =  (Boolean)Util.getProperty(msgContext, 
                                         GSIHTTPTransport.GSI_ANONYMOUS);
        GSSCredential cred = (GSSCredential)Util.getProperty(msgContext, 
                                         GSIHTTPTransport.GSI_CREDENTIALS);
        Integer protection = (Integer)Util.getProperty(msgContext,
                                         GSIConstants.GSI_TRANSPORT);
	TrustedCertificates trustedCerts = 
            (TrustedCertificates)Util.getProperty(msgContext, 
                                                  GSIHTTPTransport
                                                  .TRUSTED_CERTIFICATES);
        init(host, port,
             auth, anonymous, cred, protection, trustedCerts);
    }

    public SSLContextHelper(String host, int port,
                            Authorization auth,
                            Boolean anonymous,
                            GSSCredential cred,
                            Integer protection,
                            TrustedCertificates trustedCerts)
        throws GSSException {

        init(host, port, 
             auth, anonymous, cred, protection, trustedCerts);
    }

    protected void init(String host, int port,
                        Authorization auth,
                        Boolean anonymous,
                        GSSCredential cred,
                        Integer protection,
                        TrustedCertificates trustedCerts)
        throws GSSException {

        this.host = host;
        this.port = port;
        
        if (auth == null) {
            auth = HostAuthorization.getInstance();
        }

        GSSManager manager = ExtendedGSSManager.getInstance();
        
        boolean anon = false;
        
        if (anonymous != null && anonymous.equals(Boolean.TRUE)) {
            anon = true;
        }

        if (anon) {
            GSSName name = manager.createName((String)null,
                                              (Oid)null);
            cred = manager.createCredential(
                name,
                GSSCredential.DEFAULT_LIFETIME,
                (Oid)null,
                GSSCredential.INITIATE_ONLY);
        }
        
        GSSName expectedName = null;
        
        if (auth instanceof GSSAuthorization) {
            GSSAuthorization gssAuth = (GSSAuthorization)auth;
            expectedName = gssAuth.getExpectedName(cred, host);
        }

        ExtendedGSSContext context =(ExtendedGSSContext)manager
            .createContext(expectedName,
                           GSSConstants.MECH_OID,
                           cred,
                           GSSContext.DEFAULT_LIFETIME);

        if (anon) {
            context.requestAnonymity(true);
        }
                        
        context.setOption(GSSConstants.GSS_MODE, GSIConstants.MODE_SSL);

        if (GSIConstants.ENCRYPTION.equals(protection)) {
            context.requestConf(true);
        } else {
            context.requestConf(false);
        }
        
        if (trustedCerts != null) {
            context.setOption(GSSConstants.TRUSTED_CERTIFICATES, trustedCerts);
        }

        this.myContext = context;
        this.myAuth = auth;
    }

        
    public GssSocket wrapSocket(Socket socket) {
        
        GssSocketFactory factory = GssSocketFactory.getDefault();
        
        GssSocket gsiSocket =
            (GssSocket)factory.createSocket(socket, 
                                            this.host,
                                            this.port,
                                            this.myContext);
        
        gsiSocket.setAuthorization(this.myAuth);
        
        return gsiSocket;
    }
    
}