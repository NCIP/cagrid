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
package org.globus.wsrf.impl.security;

import org.globus.gsi.CertUtil;
import org.globus.gsi.GlobusCredential;

import org.globus.wsrf.impl.security.authentication.encryption.EncryptionCredentials;

import javax.security.auth.Subject;

import java.security.cert.X509Certificate;

import org.globus.wsrf.impl.security.authorization.Authorization;
import org.globus.wsrf.impl.security.authorization.SelfAuthorization;
import org.globus.wsrf.impl.security.authorization.HostAuthorization;

public class TestConstants {

    public static final String SECURITY_SERVICE_PATH = "SecurityTestService";
    public static final String CUSTOM_AUTHZ_SERVICE_PATH =
        "AuthzCalloutTestService";
    public static final String TEST_AUTHZ_SERVICE = "TestAuthzService";

    public static final String TEST_CLIENT_AUTHZ_PROP = "TEST_CLIENT_AUTHZ"; 
    public static final String TEST_CLIENT_ENCRYPTION_CRED = 
        "TEST_CLIENT_ENCRYPTION_CRED"; 
    public static final String TEST_AUTHZ_SERVICE_IDENTITY = 
        "TEST_AUTHZ_SERVICE_IDENTITY";

    public static Authorization getConfiguredClientAuthz() {
        Authorization authz = null;
        String authzStr =
            System.getProperty(TestConstants.TEST_CLIENT_AUTHZ_PROP);
        if ((authzStr != null) &&
            (authzStr.equals(Authorization.AUTHZ_HOST))) {
            authz = HostAuthorization.getInstance();
        } else {
            authz = SelfAuthorization.getInstance();
        }
        return authz;
    }

    public static org.globus.gsi.gssapi.auth.Authorization
        getConfiguredClientGSIAuthz() {
        org.globus.gsi.gssapi.auth.Authorization authz = null;
        String authzStr =
            System.getProperty(TestConstants.TEST_CLIENT_AUTHZ_PROP);
        if ((authzStr != null) &&
            (authzStr.equals(Authorization.AUTHZ_HOST))) {
            authz = org.globus.gsi.gssapi.auth.HostAuthorization.getInstance();
        } else {
            authz = org.globus.gsi.gssapi.auth.SelfAuthorization.getInstance();
        }
        return authz;
    }

    public static Subject getPeerSubjectForClient(boolean self)
        throws Exception{

        X509Certificate serverCert = null;
        if (self){
            GlobusCredential credential =
                GlobusCredential.getDefaultCredential();
            serverCert = credential.getCertificateChain() [0];
        } else {
            String encCredFile =
                System.getProperty(TestConstants.TEST_CLIENT_ENCRYPTION_CRED);
            if (encCredFile == null || encCredFile.equals("")) {
                throw new Exception(TestConstants.TEST_CLIENT_ENCRYPTION_CRED
                                    + " not set");
            }

            serverCert = CertUtil.loadCertificate(encCredFile);
        }
        EncryptionCredentials encryptionCreds =
            new EncryptionCredentials(new X509Certificate[]
                { serverCert });
        Subject subject = new Subject();
        subject.getPublicCredentials().add(encryptionCreds);
        return subject;
    }
}
