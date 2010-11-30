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

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.ietf.jgss.GSSContext;

public class GSSCipher extends CipherSpi {
    private int mode;
    private GSSContext context = null;
    private static Log logger =
        LogFactory.getLog(GSSCipher.class.getName());


    protected void engineSetMode(String mode)
       throws NoSuchAlgorithmException {
    }
    protected void engineSetPadding(String padding)
       throws NoSuchPaddingException {
    }
    protected int engineGetBlockSize() {
        return 0;
    }
    protected int engineGetKeySize(Key key) {
        return 0;
    }
    protected int engineGetOutputSize(int inputLen) {
        // TODO: What should be returned here - no buffer writes supported
        return 0;
    }
    protected byte[] engineGetIV() {
        return new byte[0];
    }
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }
    protected void engineInit(int opmode, Key key, SecureRandom random)
        throws InvalidKeyException {
        if(!(key instanceof GSSKey))
        {
            throw new InvalidKeyException();
        }
        this.mode = opmode;
        this.context = ((GSSKey) key).getContext();
        logger.debug("init called");
    }

    protected void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random)
       throws InvalidKeyException, InvalidAlgorithmParameterException {
       engineInit(opmode, key, random);
    }
    protected void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random)
       throws InvalidKeyException, InvalidAlgorithmParameterException {
       engineInit(opmode, key, random);
    }
    protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
       // TODO handle this more cleanly
       return null;
    }
    protected int engineUpdate(byte[] input, int inputOffset)
       throws ShortBufferException {
       throw new ShortBufferException("Not supported");
    }
    protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen)
       throws IllegalBlockSizeException, BadPaddingException {
       if (context == null) {
           throw new IllegalBlockSizeException("No GSS context");
       }
       if (this.mode == Cipher.ENCRYPT_MODE) {
           try {
               byte[] wrapres = context.wrap(input, inputOffset, inputLen, null);
               if (logger.isDebugEnabled()) {
                   logger.debug("wrap input = " + inputLen + " bytes output = " + wrapres.length + " bytes");
               }
               return wrapres;
           } catch (Exception e) {
              throw new IllegalBlockSizeException("Could not wrap data: " + e);
           }
       } else if (this.mode == Cipher.DECRYPT_MODE) {
           try {
               byte[] wrapres = context.unwrap(input, inputOffset, inputLen, null);
               if (logger.isDebugEnabled()) {
                   logger.debug("unwrap input = " + inputLen + " bytes output = " + wrapres.length + " bytes");
               }
               return wrapres;
           } catch (Exception e) {
              throw new IllegalBlockSizeException("Could not unwrap data: " + e);
           }
       }
       throw new IllegalBlockSizeException("Unsupported Mode");
    }
    protected int engineDoFinal(byte[] input, int inputOffset)
       throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
       throw new ShortBufferException("Not supported");
    }

    protected int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
       throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
       throw new ShortBufferException("Not supported");
    }
    protected int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
       throws ShortBufferException {
       throw new ShortBufferException("Not supported");
    }
}

