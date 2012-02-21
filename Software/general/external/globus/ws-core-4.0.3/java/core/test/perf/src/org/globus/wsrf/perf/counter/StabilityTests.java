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
package org.globus.wsrf.perf.counter;

import java.util.Random;

public class StabilityTests {

    public static void main(String[] args) 
        throws Exception {
        
        int scenarioNum = Integer.parseInt(args[0]);
        
        String base = (scenarioNum == PerformanceTests.TRANSPORT) ? 
            "https://localhost:8443" : "http://localhost:8080";
        String service = base + "/wsrf/services/PerformanceCounterService";

        int iterations = 100;
        int delay = 5000;
        int range = -1;
        int specific = -1;

        for (int i=1;i<args.length;i++) {
            if (args[i].equals("-service")) {
                service = args[++i];
            } else if (args[i].equals("-n")) {
                iterations = Integer.parseInt(args[++i]);
            } else if (args[i].equals("-r")) {
                range = Integer.parseInt(args[++i]);
            } else if (args[i].equals("-s")) {
                specific = Integer.parseInt(args[++i]);
            } else if (args[i].equals("-d")) {
                delay = 1000 * Integer.parseInt(args[++i]);
            }
        }

        if (specific != -1 && range != -1) {
            throw new Exception("Cannot specify both -r ad -s");
        }

        if (range == -1 && specific == -1) {
            range = 5;
        }
        
        PerformanceTests tests = new PerformanceTests();
        tests.setIterations(iterations);
        tests.setService(service);
        tests.setScenario(scenarioNum);

        if (range != -1) {
            Random r = new Random();
            int i = 1;
            for (;;) {
                int testNum = r.nextInt(range)+1;
                tests.run(testNum);
                System.out.println();
                System.out.println("Total tests: " + (i*iterations));
                System.out.println();
                i++;
                Thread.sleep(delay);
            }
        } else if (specific != -1) {
            int i = 1;
            for (;;) {
                tests.run(specific);
                System.out.println();
                System.out.println("Total tests: " + (i*iterations));
                System.out.println();
                i++;
                Thread.sleep(delay);
            }
        } else {
            throw new Exception("somthig is not right");
        }
    }
    
}
