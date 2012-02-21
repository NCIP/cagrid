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
package org.globus.wsrf.samples.authzService;

import java.util.Vector;
import java.util.StringTokenizer;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.impl.SimpleResourceProperty;
import org.globus.wsrf.impl.SimpleResourcePropertySet;

import org.globus.wsrf.impl.security.authorization.SAMLAuthorizationConstants;

public class SampleAuthzResource implements Resource, ResourceProperties {

    public static final javax.xml.namespace.QName RP_SET =
        new javax.xml.namespace.QName("http://www.gridforum.org/namespaces/2004/03/ogsa-authz/saml", "AuthzServiceRPSet");

    // Elements that the authz service can return. One of these should
    // be in the responeWith element of the request.
    public final org.opensaml.QName[] supportedResponse = 
        new org.opensaml.QName[] 
        { SAMLAuthorizationConstants.AUTHZ_DECISION,
          SAMLAuthorizationConstants.SIMPLE_AUTHZ_DECISION };

    protected Object key;
    private ResourcePropertySet propSet;
    Vector declinedMethods = null;

    public SampleAuthzResource() {
        // by default`
        this(Boolean.FALSE, null);
    }

    public org.opensaml.QName[] getSupportedResponse() {
        return supportedResponse;
    }

    public SampleAuthzResource(Boolean signature, String methodList) {
        // by default
        setRPs(signature);
        if ((methodList != null) && (!methodList.trim().equals(""))) {
            StringTokenizer strTok = new StringTokenizer(methodList, ",");
            int length = strTok.countTokens();
            declinedMethods = new Vector(length + 1);
            while (strTok.hasMoreTokens()) {
                String methName = strTok.nextToken().trim();
                declinedMethods.add(methName);
            }
            int pos = methodList.lastIndexOf(",");
            if (pos == -1)
                pos = 0;
            declinedMethods.add(methodList.substring(0, methodList.length()));
        }
    }

    public void addDeclinedMethod(String methodName) {
        if (declinedMethods == null)
            declinedMethods = new Vector();
        declinedMethods.add(methodName);
    }

    public Vector getDeclinedMethods() {
        return declinedMethods;
    }

    public void setRPs(Boolean signatureCapable) {

        propSet = new SimpleResourcePropertySet(RP_SET);

        ResourceProperty indeterminate = 
            new SimpleResourceProperty(
                SAMLAuthorizationConstants.RP_INDETERMINATE);
        indeterminate.add(Boolean.TRUE);

        ResourceProperty signature = 
            new SimpleResourceProperty(
                SAMLAuthorizationConstants.RP_SIGNATURE);
        signature.add(signatureCapable);
        
        propSet.add(indeterminate);
        propSet.add(signature);
    }

    public ResourcePropertySet getResourcePropertySet() {
        return this.propSet;
    }
}
