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

import org.globus.common.ChainedIOException;
import org.globus.gsi.gssapi.net.impl.GSIGssSocket;
import org.globus.gsi.gssapi.net.impl.GSIGssInputStream;
import org.globus.gsi.gssapi.net.GssOutputStream;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;

import java.io.InputStream;
import java.io.IOException;
import java.net.Socket;

public class HTTPSSocket extends GSIGssSocket {

    protected boolean autoFlush;
    protected String userDN;

    private boolean init = false;

    public HTTPSSocket(Socket socket, GSSContext context) {
        super(socket, context);
        setAuthorization(null);
    }

    public void setAutoFlush(boolean autoFlush) {
        this.autoFlush = autoFlush;
    }

    public String getUserDN() {
        return this.userDN;
    }

    public synchronized void startHandshake()
        throws IOException {
        super.startHandshake();

        if (init) {
            return;
        }

        GSSContext context = getContext();

        try {
            userDN = context.getSrcName().toString();
        } catch (GSSException e) {
            throw new ChainedIOException("Failed to retreive context properties", e);
        }

        if (this.out != null) {
            ((GssOutputStream)this.out).setAutoFlush(this.autoFlush);
        }

        init = true;
    }

    public void close() throws IOException {
        if (out != null) {
            out.flush();
        }
        super.close();
    }

    public void shutdownOutput() throws IOException {
        if (out != null) {
            out.flush();
        }
        super.shutdownOutput();
    }
    
    protected byte[] readToken()
	throws IOException {
	if (this.in == null) {
	    this.in = new SocketGSIGssInputStream(this.socket.getInputStream(),
                                                  this.context,
                                                  this);
	}
        return super.readToken();
    }

    public static class SocketGSIGssInputStream extends GSIGssInputStream {
        
        private HTTPSSocket socket;
        
        public SocketGSIGssInputStream(InputStream in,
                                       GSSContext context,
                                       HTTPSSocket socket) {
            super(in, context);
            this.socket = socket;
        }
        
        public HTTPSSocket getSocket() {
            return this.socket;
        }
    }
    
}
