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
package org.globus.wsrf.providers;

import java.security.Provider;
import java.security.Security;

/**
 * Provides implementation of java security cipher and signature spis to handle
 * GSS API encryption/decryption and signing
 */
public final class JCEProvider extends Provider {
    private static JCEProvider provider = new JCEProvider();
    public JCEProvider() {
        super("Globus", 1.0, "Globus Security Provider");
        setProperty("Cipher.GSSAPI", GSSCipher.class.getName());
        setProperty("Signature.GSSAPI", GSSSignature.class.getName());
    }
    public static JCEProvider getInstance() {
        return provider;
    }

    public static void addProvider() {
        Security.addProvider(getInstance());
    }
}
