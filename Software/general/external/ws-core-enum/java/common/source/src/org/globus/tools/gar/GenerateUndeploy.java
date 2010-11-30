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
import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.globus.tools.DeployConstants;

public class GenerateUndeploy {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Usage: GenerateUndeploy <gar file> <undeploy dir>");
        }

        JarFile jar = new JarFile(args[0]);
        String garID = new File(args[0]).getName();
        int extensionPos = garID.lastIndexOf('.');

        if (extensionPos != -1) {
            garID = garID.substring(0, extensionPos);
        }

        String baseDir = args[1] + File.separator + "etc" + File.separator +
            "globus_packages" + File.separator + garID + File.separator;
        File dir = new File(baseDir);
        dir.mkdirs();

        FileWriter file = new FileWriter(baseDir + "undeploy.xml");
        PrintWriter out = new PrintWriter(file);
        out.println("<project default=\"undeploy\" basedir=\".\">");

        /*
        out.println("<path id=\"classpath\">");
        out.println("  <fileset dir=\"${deploy.dir}/lib\">");
        out.println("    <include name=\"*.jar\"/>");
        out.println("  </fileset>");
        out.println("  <pathelement path=\"${java.class.path}\"/>");
        out.println("</path>");
        */

        out.println("\n");
        out.println("<property environment=\"env\"/>");
        out.println("<property file=\"build.properties\"/>");
        out.println("<property file=\"${user.home}/build.properties\"/>");
        out.println(
            "<property name=\"env.GLOBUS_LOCATION\" value=\"../../..\"/>");
        out.println(
            "<property name=\"deploy.dir\" value=\"${env.GLOBUS_LOCATION}\"/>");
        out.println("\n");
        out.println("<target name=\"undeploy\">");

        out.println("  <delete verbose=\"true\" dir=\"${deploy.dir}/" + 
                    DeployConstants.CONFIG_BASE_DIR + "/" + garID + "\"/>");

        /*
        out.println(
            "  <available file=\"" + "undeploy.wsdd\" " +
            "property=\"undeploy.available\"/>"
        );
        out.println("  <antcall target=\"undeployDescriptor\"/>");
        */

        for (Enumeration e = jar.entries(); e.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) e.nextElement();

            String name = entry.getName();

            if (entry.isDirectory()) {
                if (name.equals("docs/")) {
                    out.println(
                        "  <delete verbose=\"true\" dir=\"${deploy.dir}/docs/" + garID + "\"/>"
                    );
                } else if (name.equals("share/")) {
                    out.println(
                        "  <delete verbose=\"true\" dir=\"${deploy.dir}/share/" + garID + "\"/>"
                    );
                }
                continue;
            }

            if (name.startsWith("lib/")) {
                String nm = name.substring("lib/".length());
                if (name.endsWith(".jar")) {
                    out.println(
                       "  <delete verbose=\"true\" file=\"${deploy.dir}/lib/" +
                       nm + "\"/>");
                } else if (nm.indexOf("LICENSE") != -1) {
                    out.println(
                       "  <delete verbose=\"true\" file=\"${deploy.dir}/share/licenses/" +
                       nm + "\"/>");
                }
            } else if (name.startsWith("schema/")) {
                out.println(
                    "  <delete verbose=\"true\" file=\"${deploy.dir}/share/"
                    + name + "\"/>"
                );
            } else if (name.startsWith("bin/")) {
                out.println(
                    "  <delete verbose=\"true\" file=\"${deploy.dir}/" + name +
                    "\"/>"
                );
                //            } else if (name.equals("server-deploy.wsdd")) {
                // writeUndeployment(jar, baseDir, entry);
            }
        }

        out.println("</target>");

        /*
        out.println(
            "<target name=\"undeployDescriptor\" " +
            "if=\"undeploy.available\">"
        );
        out.println(
            "  <echo message=\"undeploying " + garID +
            " from server config\"/>"
        );
        out.println("  <java classname=\"org.apache.axis.utils.Admin\"");
        out.println("        classpathref=\"classpath\"");
        out.println("        fork=\"true\"");
        out.println("        dir=\"${deploy.dir}/\"");
        out.println("        failonerror=\"true\">");
        out.println("    <arg value=\"server\"/>");
        out.println(
            "    <arg value=\"${deploy.dir}/etc/globus_packages/" + garID +
            "/undeploy.wsdd\"/>"
        );
        out.println("  </java>");
        out.println("</target>");
        */

        out.println("</project>");
        out.flush();
        out.close();
    }

    /*
    private static void writeUndeployment(
        JarFile jar,
        String undeployLocation,
        ZipEntry entry
    ) throws Exception {
        FileWriter file =
            new FileWriter(undeployLocation + "undeploy.wsdd");
        PrintWriter out = new PrintWriter(file);
        InputStream input = jar.getInputStream(entry);
        Document doc = XMLUtils.newDocument(input);
        NodeList nodes =
            doc.getElementsByTagNameNS(
                "http://xml.apache.org/axis/wsdd/", "service"
            );
        out.println(
            "<undeployment xmlns=\"http://xml.apache.org/axis/wsdd/\">"
        );

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            out.println(
                "<service name=\"" + element.getAttribute("name") + "\"/>"
            );
        }

        out.println("</undeployment>");
        out.flush();
        out.close();
    }
    */

}
