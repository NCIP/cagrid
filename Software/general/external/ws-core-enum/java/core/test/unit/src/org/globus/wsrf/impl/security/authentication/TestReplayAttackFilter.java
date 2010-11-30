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
package org.globus.wsrf.impl.security.authentication;

import java.util.Calendar;
import java.util.Random;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.globus.wsrf.impl.security.authentication.wssec.WSSecurityException;

public class TestReplayAttackFilter extends TestCase {

    public TestReplayAttackFilter(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TestReplayAttackFilter.class);
    }

    public void testTimestamp() throws Exception {
        // Valid instance should go thro'
        Calendar tsstore = Calendar.getInstance();
        Calendar ts1 = Calendar.getInstance();
        ts1.setTime(tsstore.getTime());
        Random random = new Random(System.currentTimeMillis());
        String nonceVal1 = new Integer(random.nextInt()).toString();
        VerifyReplayFilter replay = new VerifyReplayFilter();
        replay.checkMessageValidity(nonceVal1, ts1);

        // after one min, send in same nonce value
        Thread.sleep(60000);
        // Replay same msg as is.
        boolean exp = false;
        ts1.setTime(tsstore.getTime());
        try {
            replay.checkMessageValidity(nonceVal1, ts1);
        } catch (WSSecurityException e) {
            assertTrue(e.getErrorCode() == WSSecurityException.FAILURE);
            assertTrue(e.getMessage().indexOf("Duplicate") != -1);
            exp = true;
        }
        assertTrue(exp);

        // Replay msg with same nonce value, but time changed to be
        // within window
        ts1.setTime(tsstore.getTime());
        ts1.add(Calendar.MINUTE, 2);
        exp = false;
        try {
            replay.checkMessageValidity(nonceVal1, ts1);
        } catch (WSSecurityException e) {
            assertTrue(e.getErrorCode() == WSSecurityException.FAILURE);
            assertTrue(e.getMessage().indexOf("Duplicate") != -1);
            exp = true;
        }
        assertTrue(exp);

        // Replay msg with same nonce value, but time changed to be
        // outside window
        ts1.add(Calendar.MINUTE, 6);
        exp = false;
        try {
            replay.checkMessageValidity(nonceVal1, ts1);
        } catch (WSSecurityException e) {
            assertTrue(e.getErrorCode() == WSSecurityException.MESSAGE_EXPIRED);
            exp = true;
        }
        assertTrue(exp);

        // Diff nonce value, same timestamp should go thro'
        String nonceVal2 = new Integer(random.nextInt()).toString();
        Calendar ts3 = Calendar.getInstance();
        Calendar ts4 = Calendar.getInstance();
        ts4.setTime(ts3.getTime());
        replay.checkMessageValidity(nonceVal2, ts3);
        String nonceVal3 = new Integer(random.nextInt()).toString();
        replay.checkMessageValidity(nonceVal3, ts4);
    }
}
