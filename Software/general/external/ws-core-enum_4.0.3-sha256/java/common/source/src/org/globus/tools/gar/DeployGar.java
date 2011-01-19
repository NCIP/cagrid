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
package org.globus.tools.gar;

import java.io.File;

import org.globus.ant.AntTaskLauncher;

public class DeployGar extends AntTaskLauncher {

    private static final String USAGE =
        "globus-deploy-gar <gar file> [options]\n" +
        "Options:\n" +
        "  -help, -h             Print this message\n" +
        "  -verbose, -v          Enable verbose mode\n" +
        "  -debug, -d            Enable debug mode\n" +
        "  -backup, -b           Create backup of configuration files\n" +
        "  -profile <name>       Specify configuration profile name\n" +
        "  -D<property>=<value>  Pass arbitrary property value\n";
    
    public static void main(String[] args) {
        
        String globusLocation = System.getProperty("GLOBUS_LOCATION");
        if (globusLocation == null) {
            System.err.println("Error: GLOBUS_LOCATION system property is not set");
            System.exit(1);
        }
        
        if (args.length == 0 || 
            (args.length == 1 && 
             (args[0].equals("-h") ||
              args[0].equalsIgnoreCase("-help")))) {
            System.err.println(USAGE);
            System.exit(3);
        }

        File globusLocationFile = new File(globusLocation);
        File buildFile = 
            new File(globusLocation, 
                     "share/globus_wsrf_common/build-packages.xml");
        
        DeployGar deploy = new DeployGar();
                
        File garFile = new File(args[0]);

        deploy.setProperty("env.GLOBUS_LOCATION", 
                           globusLocationFile.getAbsolutePath());
        deploy.setProperty("gar.name", 
                           garFile.getAbsolutePath());
        deploy.setStartMessage("Deploying gar file...");
        deploy.setFinishSuccessMessage("Deploy successful");
        deploy.setFinishFailMessage("Deploy failed");
       
        int mode = AntTaskLauncher.INFO_MODE;

        for (int i=1;i<args.length;i++) {
            if (args[i].startsWith("-D")) {

                /* From Ant code:
                 * Interestingly enough, we get to here when a user
                 * uses -Dname=value. However, in some cases, the OS
                 * goes ahead and parses this out to args
                 *   {"-Dname", "value"}
                 * so instead of parsing on "=", we just make the "-D"
                 * characters go away and skip one argument forward.
                 *
                 * I don't know how to predict when the JDK is going
                 * to help or not, so we simply look for the equals sign.
                 */

                String name = args[i].substring(2, args[i].length());
                String value = null;
                int posEq = name.indexOf("=");
                if (posEq > 0) {
                    value = name.substring(posEq + 1);
                    name = name.substring(0, posEq);
                } else if (i < args.length - 1) {
                    value = args[++i];
                } else {
                    System.err.println("Error: Missing value for property " + 
                                       name);
                    System.exit(2);
                }
                deploy.setProperty(name, value);
            } else if (args[i].equalsIgnoreCase("-profile")) {
                if (i < args.length - 1) {
                    deploy.setProperty("profile", args[++i]);
                } else {
                    System.err.println("Error: Missing profile name");
                    System.exit(2);
                }
            } else if (args[i].equalsIgnoreCase("-help") ||
                       args[i].equals("-h")) {
                System.err.println(USAGE);
                System.exit(3);
            } else if (args[i].equalsIgnoreCase("-verbose") ||
                       args[i].equals("-v")) {
                mode = AntTaskLauncher.VERBOSE_MODE;
            } else if (args[i].equalsIgnoreCase("-debug") ||
                       args[i].equals("-d")) {
                mode = AntTaskLauncher.DEBUG_MODE;
            } else if (args[i].equalsIgnoreCase("-backup") ||
                       args[i].equals("-b")) {
                deploy.setProperty("createBackup", "true");
            } else {
                System.err.println("Error: Unknown argument: " + args[i]);
                System.exit(2);
            }
        }

        deploy.setOutputLevel(mode);
        deploy.executeTarget(buildFile, "deployGar");
    }
        
}
