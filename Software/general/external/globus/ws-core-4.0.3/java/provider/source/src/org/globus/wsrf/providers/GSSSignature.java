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

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSContext;

public class GSSSignature extends SignatureSpi
{
    private GSSContext context;
    private static Log logger =
        LogFactory.getLog(GSSSignature.class.getName());
    private byte[] plaintext = null;

    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException
    {
        if(!(publicKey instanceof GSSPublicKey))
        {
            throw new InvalidKeyException();
        }
        this.context = ((GSSKey) publicKey).getContext();
        logger.debug("init called");
    }

    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException
    {
        if(!(privateKey instanceof GSSPrivateKey))
        {
            throw new InvalidKeyException();
        }
        this.context = ((GSSKey) privateKey).getContext();
        logger.debug("init called");
    }

    protected void engineUpdate(byte b)
    {
        //go for simple & stupid first

        if(this.plaintext == null)
        {
            this.plaintext = new byte[] {b};
        }
        else
        {
            byte [] tmp = new byte[this.plaintext.length + 1];
            System.arraycopy(this.plaintext, 0, tmp, 0, this.plaintext.length);
            tmp[this.plaintext.length] = b;
            this.plaintext = tmp;
        }
    }

    protected void engineUpdate(byte[] bytes, int offset, int length)
    {
        //go for simple & stupid first

        if(this.plaintext == null)
        {
            this.plaintext = new byte[length];
            System.arraycopy(bytes, offset, this.plaintext, 0, length);
        }
        else
        {
            byte [] tmp = new byte[this.plaintext.length + length];
            System.arraycopy(this.plaintext, 0, tmp, 0, this.plaintext.length);
            System.arraycopy(bytes, offset, tmp, this.plaintext.length, length);
            this.plaintext = tmp;
        }
    }

    protected byte[] engineSign() throws SignatureException
    {
        byte[] signature = null;

        if(this.plaintext == null)
        {
            throw new SignatureException("Nothing to sign");
        }

        try
        {
            signature = context.getMIC(
                this.plaintext, 0, this.plaintext.length, null);
        }
        catch(GSSException e)
        {
            logger.debug("", e);
            throw new SignatureException("Failed to sign");
        }

        this.plaintext = null;
        return signature;
    }

    protected boolean engineVerify(byte[] sigBytes) throws SignatureException
    {
        if(this.plaintext == null)
        {
            throw new SignatureException("Nothing to verify");
        }

        try
        {
            context.verifyMIC(
                sigBytes, 0, sigBytes.length, this.plaintext, 0,
                this.plaintext.length, null);
        }
        catch(GSSException e)
        {
            logger.debug("", e);
            throw new SignatureException("Failed to verify");
        }
        return true;
    }

    protected void engineSetParameter(String param, Object value)
        throws InvalidParameterException
    {
    }

    protected Object engineGetParameter(String param)
        throws InvalidParameterException
    {
        return null;
    }
}
