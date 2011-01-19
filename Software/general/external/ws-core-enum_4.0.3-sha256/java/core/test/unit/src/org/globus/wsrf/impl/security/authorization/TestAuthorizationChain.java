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
package org.globus.wsrf.impl.security.authorization;

import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.MessageContext;

import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;
import org.globus.wsrf.security.authorization.Interceptor;

public class TestAuthorizationChain extends ServiceAuthorizationChain {
    
    public TestAuthorizationChain(Interceptor[] interceptors, 
                                  String id) 
        throws Exception {

	this.interceptor = interceptors;
        this.initialized = true;
        this.interceptorName = new String[this.interceptor.length];
        for (int i=0; i<this.interceptor.length; i++) {
            this.interceptorName[i] = "temp";
            this.interceptor[i].initialize(null, this.interceptorName[i], 
                                           id);
        }
    }

    public boolean authorize(Subject peerSubject, MessageContext context) 
        throws AuthorizationException {

        // Dummy operation name
        QName opName = new QName("http://temp.ns", "dummyOp");
	return authorize(peerSubject, context, opName);
    }
}
