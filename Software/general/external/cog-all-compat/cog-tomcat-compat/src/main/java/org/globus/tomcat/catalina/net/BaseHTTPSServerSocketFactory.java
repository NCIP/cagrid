/*
 * Copyright 1999-2006 University of Chicago
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.globus.tomcat.catalina.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.net.ServerSocketFactory;
import org.globus.common.ChainedIOException;
import org.globus.gsi.CredentialException;
import org.globus.gsi.GSIConstants;
import org.globus.gsi.TrustedCertificates;
import org.globus.gsi.X509Credential;
import org.globus.gsi.gssapi.GSSConstants;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.gridforum.jgss.ExtendedGSSContext;
import org.gridforum.jgss.ExtendedGSSManager;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;

/*
 * 1. Tomcat 5.5.x, 5.0.x set the attributes properties internally, so 
 *    the attribute cases must match in config file and property names.
 * 2. Tomcat 4.0.x set the attributes using the methods defined.
 */

// Tomcat 5.0.x and 5.5 need this
public class BaseHTTPSServerSocketFactory extends ServerSocketFactory {

    public static final String PROXY_FILE = "proxy";
    public static final String CERT_FILE = "cert";
    public static final String KEY_FILE = "key";
    public static final String CA_CERT_DIR = "cACertDir";
    private static final String CA_CERT_DIR_2 = CA_CERT_DIR.toLowerCase();
    public static final String AUTO_FLUSH = "autoFlush";
    public static final String ENCRYPTION = "encryption";
    public static final String MODE = "mode";

    protected static final String MODE_SSL = "ssl";
    protected static final String MODE_GSI = "gsi";

    private static Log logger =
        LogFactory.getLog(BaseHTTPSServerSocketFactory.class);

    public String getCert() {
        return (String)attributes.get(CERT_FILE);
    }

    public void setCert(String serverCert) {
        attributes.put(CERT_FILE, serverCert);
    }

    public String getKey() {
        return (String)attributes.get(KEY_FILE);
    }

    public void setKey(String serverKey) {
        attributes.put(KEY_FILE, serverKey);
    }

    public String getProxy() {
        return (String)attributes.get(PROXY_FILE);
    }

    public void setProxy(String serverProxy) {
        attributes.put(PROXY_FILE, serverProxy);
    }

    public String getCacertdir() {
        String dir = (String)attributes.get(CA_CERT_DIR);
        if (dir == null) {
            dir = (String)attributes.get(CA_CERT_DIR_2);
        }
        return dir;
    }

    public void setCacertdir(String caCertDir) {
        attributes.put(CA_CERT_DIR, caCertDir);
    }

    public boolean getAutoFlush() {
        String autoFlush = (String)attributes.get(AUTO_FLUSH);
        return (autoFlush == null) ? false : autoFlush.equals("true");
    }
    
    public void setAutoFlush(boolean autoFlush) {
        attributes.put(AUTO_FLUSH, (autoFlush) ? "true" : "false");
    }

    public boolean getEncryption() {
        String encryption = (String)attributes.get(ENCRYPTION);
        return (encryption == null) ? true : encryption.equals("true");
    }
    
    public void setEncryption(boolean encrypt) {
        attributes.put(ENCRYPTION, (encrypt) ? "true" : "false");
    }
    
    public void setMode(String mode) {
        attributes.put(MODE, mode);
    }

    public String getMode() {
        return (String)attributes.get(MODE);
    }
    
    protected Integer getSecMode() {
        String mode = getMode();
        if (mode == null) {
            // assumes SSL as default
            return GSIConstants.MODE_SSL;
        } else if (mode.equalsIgnoreCase(MODE_SSL)) {
            return GSIConstants.MODE_SSL;
        } else if (mode.equalsIgnoreCase(MODE_GSI)) {
            return GSIConstants.MODE_GSI;
        } else {
            throw new IllegalArgumentException("Unsupported mode: " + mode);
        }
    }
    
    // ------------------------------------------

    /**
     * Creates a secure server socket on a specified port with default
     * user credentials. A port of <code>0</code> creates a socket on
     * any free port or if the tcp.port.range system property is set
     * it creates a socket within the specified port range.
     * <p>
     * The maximum queue length for incoming connection indications (a
     * request to connect) is set to <code>50</code>. If a connection
     * indication arrives when the queue is full, the connection is refused.
     * <p>
     *
     * @param      port  the port number, or <code>0</code> to use any
     *                   free port or if the tcp.port.range property set
     *                   to use any available port within the specified port
     *                   range.
     * @exception  IOException
     *              if an I/O error occurs when opening the socket.
     */
    public ServerSocket createSocket(int port)
        throws IOException {
        return createSocket(port, 50);
    }

    /**
     * Creates a secure server socket on a specified port with default
     * user credentials. A port of <code>0</code> creates a socket on
     * any free port or if the tcp.port.range system property is set
     * it creates a socket within the specified port range.
     * <p>
     * The maximum queue length for incoming connection indications (a
     * request to connect) is set to the <code>backlog</code> parameter. If
     * a connection indication arrives when the queue is full, the
     * connection is refused.
     *
     * @param      port  the port number, or <code>0</code> to use any
     *                   free port or if the tcp.port.range property set
     *                   to use any available port within the specified port
     *                   range.
     * @param      backlog
     *              the maximum length of the queue.
     * @exception  IOException
     *              if an I/O error occurs when opening the socket.
     */
    public ServerSocket createSocket(int port, int backlog)
        throws IOException {
        return createSocket(port, backlog, null);
    }

    /**
     * Creates a secure server socket on a specified port with default
     * user credentials. A port of <code>0</code> creates a socket on
     * any free port or if the tcp.port.range system property is set
     * it creates a socket within the specified port range.
     * <p>
     * The maximum queue length for incoming connection indications (a
     * request to connect) is set to the <code>backlog</code> parameter. If
     * a connection indication arrives when the queue is full, the
     * connection is refused.
     *
     * @param      port  the port number, or <code>0</code> to use any
     *                   free port or if the tcp.port.range property set
     *                   to use any available port within the specified port
     *                   range.
     * @param      backlog
     *              the maximum length of the queue.
     * @param      bindAddr the local InetAddress the server will bind to.
     * @exception  IOException
     *              if an I/O error occurs when opening the socket.
     */
    public ServerSocket createSocket(int port,
                                     int backlog,
                                     InetAddress bindAddr)
        throws IOException {

        GSSCredential gssCred = null;
        X509Credential cred = null;

        logger.info("Loading credentials");

        String serverProxy = (String) attributes.get(PROXY_FILE);
        String serverCert = (String) attributes.get(CERT_FILE);
        String serverKey = (String) attributes.get(KEY_FILE);

        try {
            if (serverProxy != null && !serverProxy.equals("")) {
                if (logger.isInfoEnabled()) {
                    logger.info("Server Proxy: " + serverProxy);
                }
                cred = new X509Credential(serverProxy);
            } else if (serverCert != null && serverKey != null) {
                if (logger.isInfoEnabled()) {
                    logger.info("Server Certificate: " + serverCert);
                    logger.info("Server Key: " + serverKey);
                }
                cred = new X509Credential(serverCert, serverKey);
            }

            if (cred != null) {
                gssCred = 
                    new GlobusGSSCredentialImpl(cred,
                                                GSSCredential.ACCEPT_ONLY);
            }
        } catch (CredentialException e) {
            throw new ChainedIOException("Failed to load credentials", e);
        } catch (GSSException e) {
            throw new ChainedIOException("Failed to load credentials", e);
        }

        String caCertDir = getCacertdir();
        
        if (logger.isInfoEnabled()) {
            logger.info("CA certificate directory: " + caCertDir);
        }

        TrustedCertificates trustedCerts = null;

        if (caCertDir != null) {
        	logger.error("Setting trusted certs to " + caCertDir);
            trustedCerts = TrustedCertificates.load(caCertDir);
        } else {
        	logger.error("No trusted certs");
        }

        HTTPSServerSocket serverSocket =
                createServerSocket(port, backlog, bindAddr);

        serverSocket.setCredentials(gssCred);
        serverSocket.setTrustedCertificates(trustedCerts);

        return serverSocket;
    }

    protected HTTPSServerSocket createServerSocket(
            int port, int backlog, InetAddress bindAddr)
        throws IOException {
        return new HTTPSServerSocket(port, backlog, bindAddr);
    }

    /**
     Wrapper function for accept(). This allows us to trap and
     translate exceptions if necessary

     @exception IOException;
     */
    public Socket acceptSocket(ServerSocket socket)
        throws IOException {
        return socket.accept();
    }

    /**
     Extra function to initiate the handshake. Sometimes necessary
     for SSL

     @exception IOException;
     */
    public void handshake(Socket sock)
        throws IOException {}

    protected class HTTPSServerSocket extends ServerSocket {

        protected TrustedCertificates _trustedCerts;

        private GSSCredential _credentials;
        private GSSManager _manager;
        private boolean _autoFlush;
        private boolean _encrypt;
        private Integer _mode;

        protected HTTPSServerSocket(int port, 
                                    int backlog,
                                    InetAddress bindAddr)
            throws IOException {
            super(port, backlog, bindAddr);

            this._manager = ExtendedGSSManager.getInstance();
            this._autoFlush = getAutoFlush();
            this._encrypt = getEncryption();
            this._mode = getSecMode();

            logger.info("Encryption: " + this._encrypt);
            logger.info("Mode: " + this._mode);
        }

        public void setTrustedCertificates(TrustedCertificates trustedCerts) {
            this._trustedCerts = trustedCerts;
        }

        public void setCredentials(GSSCredential creds) {
            this._credentials = creds;
        }

        public Socket accept()
            throws IOException {

            Socket s = super.accept();

            ExtendedGSSContext context = null;

            try {
                context =
                    (ExtendedGSSContext)this._manager.createContext(
                                                        this._credentials);
                setContextOptions(context);

            } catch (GSSException e) {
                throw new ChainedIOException("Failed to init GSS context", e);
            }

            HTTPSSocket httpsSocket = (HTTPSSocket) createSocket(s, context);
            httpsSocket.setAutoFlush(this._autoFlush);
            // server socket
            httpsSocket.setUseClientMode(false);
            
            return httpsSocket;
        }

        protected Socket createSocket(Socket s, ExtendedGSSContext context) {
            return new HTTPSSocket(s, context);
        }

        protected void setContextOptions(ExtendedGSSContext context) 
            throws GSSException {

            if (this._mode != null) {
                context.setOption(GSSConstants.GSS_MODE,
                                  this._mode);
            }

            context.setOption(GSSConstants.ACCEPT_NO_CLIENT_CERTS,
                              Boolean.TRUE);

            if (this._trustedCerts != null) {
            	logger.error("_trustedCerts is NOT null");
                context.setOption(GSSConstants.TRUSTED_CERTIFICATES,
                                  this._trustedCerts);
            } else {
            	logger.error("_trustedCerts is null");
            }

            context.requestConf(this._encrypt);
        }
    }

}
