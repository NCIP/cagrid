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
package org.globus.wsrf.impl;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.message.addressing.ReferencePropertiesType;
import org.apache.axis.types.URI;

import org.globus.wsrf.tests.performance.basic.TestPortType;
import org.globus.wsrf.tests.performance.basic.BaseType;
import org.globus.wsrf.tests.performance.basic.BaseTypeJob;
import org.globus.wsrf.tests.performance.basic.service.TestServiceAddressingLocator;

public class ClientTest {
    
    public static void main(String [] args) throws Exception {
        
        String address =
            "http://localhost:8080/wsrf/services/PerformanceTestService" ;
        
        EndpointReferenceType testServiceEPR =
            new EndpointReferenceType(new URI(address));
        ReferencePropertiesType props = new ReferencePropertiesType();
        props.add(PerformanceTestHome.TEST_KEY.toSOAPElement());
        testServiceEPR.setProperties(props);
        
        int t = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int m = Integer.parseInt(args[2]);

        switch(t) {
        case 1:
            test1(testServiceEPR, n, m);
            break;
        case 2:
            test2(testServiceEPR, n, m);
            break;
        case 3:
            test3(testServiceEPR, n, m);
            break;
        default:
            System.err.println("Wrong test number");
            System.exit(1);
        }
    }

    public static void test1(EndpointReferenceType testServiceEPR, 
                             int n, int m) 
        throws Exception {
        BaseType message = createInputMessage(m);
        long time1;
        long time2;
        long accumulator = 0;

        TestServiceAddressingLocator locator =
            new TestServiceAddressingLocator();
        
        TestPortType testService =
            locator.getTestPortTypePort(testServiceEPR);
        
        for (int i=0;i<5;i++) {
            testService.baseline(message);
        }

        for (int i=0;i<n;i++) {
            time1 = System.currentTimeMillis();

            testService.baseline(message);

            time2 = System.currentTimeMillis();
            accumulator += time2 - time1;
        }
        System.out.println("Base: " +
                           String.valueOf(accumulator/n));        
    }

    public static void test2(EndpointReferenceType testServiceEPR, 
                             int n, int m) 
        throws Exception {
        BaseType message = createInputMessage(m);
        long time1;
        long time2;
        long accumulator = 0;
        
        for (int i=0;i<5;i++) {
            TestServiceAddressingLocator locator =
                new TestServiceAddressingLocator();
            TestPortType testService =
                locator.getTestPortTypePort(testServiceEPR);
            testService.baseline(message);
        }
        
        for (int i=0;i<n;i++) {
            time1 = System.currentTimeMillis();

            TestServiceAddressingLocator locator =
                new TestServiceAddressingLocator();
            TestPortType testService =
                locator.getTestPortTypePort(testServiceEPR);
            testService.baseline(message);

            time2 = System.currentTimeMillis();
            accumulator += time2 - time1;
        }
        System.out.println("Base: " +
                           String.valueOf(accumulator/n));        
    }

   public static void test3(EndpointReferenceType testServiceEPR, 
                            int n, int m) 
        throws Exception {
       BaseType message = createInputMessage(m);
       long time1;
        long time2;
        long accumulator = 0;

        TestServiceAddressingLocator locator =
            new TestServiceAddressingLocator();

        for (int i=0;i<5;i++) {
            TestPortType testService =
                locator.getTestPortTypePort(testServiceEPR);
            testService.baseline(message);
        }
        
        for (int i=0;i<n;i++) {
            time1 = System.currentTimeMillis();

            TestPortType testService =
                locator.getTestPortTypePort(testServiceEPR);
            testService.baseline(message);

            time2 = System.currentTimeMillis();
            accumulator += time2 - time1;
        }
        System.out.println("Base: " +
                           String.valueOf(accumulator/n));        
    }
    

    public static BaseType createInputMessage(int count)
        throws Exception {
        BaseType message = new BaseType();
        message.setJob(new BaseTypeJob[count]);
        for(int i = 0; i < count; i++) {
            message.setJob(i, BasicPerformanceTests.createJobDescritption());
        }
        return message;
    }

}
