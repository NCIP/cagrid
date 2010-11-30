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
package org.globus.ant;

import java.io.File;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class AntTaskLauncher {

    public static final int DEBUG_MODE = Project.MSG_DEBUG;
    public static final int VERBOSE_MODE = Project.MSG_VERBOSE;
    public static final int INFO_MODE = Project.MSG_INFO;

    private Project project;
    private AntLogger logger;

    public AntTaskLauncher() {
        this.project = new Project();
        this.logger = new AntLogger();
        
        this.project.addBuildListener(logger);
    }
    
    public void setProperty(String key, String value) {
        this.project.setProperty(key, value);
    }

    public void setFinishSuccessMessage(String msg) {
        this.logger.setFinishSuccessMessage(msg);
    }

    public void setFinishFailMessage(String msg) {
        this.logger.setFinishFailMessage(msg);
    }

    public void setStartMessage(String msg) {
        this.logger.setStartMessage(msg);
    }

    public void setOutputLevel(int mode) {
        this.logger.setOutputLevel(mode);
    }

    public void executeTarget(File buildFile, String taskName) {
        ProjectHelper helper = ProjectHelper.getProjectHelper();
        this.project.addReference("ant.projectHelper", helper);

        try {
            this.project.setUserProperty("ant.file",
                                         buildFile.getAbsolutePath());
            this.project.init();

            helper.parse(this.project, buildFile);

            this.project.fireBuildStarted();
            this.project.executeTarget(taskName);
            this.project.fireBuildFinished(null);
        } catch (Exception e) {
            this.project.fireBuildFinished(e);
            System.exit(1);
        }
    }
    
    private static class AntLogger implements BuildListener {

        private String startMessage;
        private String successMessage;
        private String failMessage;
        private int outputLevel = INFO_MODE;

        public AntLogger() {
        }

        public void setOutputLevel(int mode) {
            this.outputLevel = mode;
        }

        public void setStartMessage(String msg) {
            this.startMessage = msg;
        }

        public void setFinishSuccessMessage(String msg) {
            this.successMessage = msg;
        }

        public void setFinishFailMessage(String msg) {
            this.failMessage = msg;
        }

        public void buildStarted(BuildEvent event) {
            if (this.startMessage != null) {
                System.out.println(this.startMessage);
            }
            System.out.println();
        }

        public void buildFinished(BuildEvent event) {
            Throwable error = event.getException();
            System.out.println();
            if (error == null) {
                System.out.println(successMessage);
            } else {
                if (Project.MSG_VERBOSE <= this.outputLevel) {
                    System.err.println(failMessage);
                    error.printStackTrace(System.err);
                } else {
                    System.err.println(failMessage + ": " + 
                                       error.getMessage());
                }
            }
        }

        public void targetStarted(BuildEvent event) {
        }

        public void targetFinished(BuildEvent event) {
        }

        public void taskStarted(BuildEvent event) {
        }

        public void taskFinished(BuildEvent event) {
        }

        public void messageLogged(BuildEvent event) {
            int priority = event.getPriority();

            if (priority <= this.outputLevel) {
                if (priority == Project.MSG_ERR) {
                    System.err.println("  " + event.getMessage());
                } else {
                    System.err.println("  " + event.getMessage());
                }
            }
        }

    }

        
}
