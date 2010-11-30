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
package org.globus.wsrf.test;

import junit.framework.TestResult;
import junit.framework.TestSuite;

public class GridTestSuite extends TestSuite
{
    public static int timeout =  1000 * 60 * 2;

    protected TestContainer testContainer;
    private String testClassPattern;
    private String containerConfigFile;
    private boolean transportSecurity = false;

    public GridTestSuite(String name, String containerConfigFile,
                         boolean transportSecurity)
    {
        super(name);
        this.testClassPattern =
            System.getProperty("org.globus.wsrf.test.pattern");
        this.containerConfigFile = containerConfigFile;
        this.transportSecurity = transportSecurity;
    }

    public GridTestSuite(String name)
    {
        this(name, null);
    }

    public GridTestSuite(String name, boolean transportSecurity)
    {
        this(name, null, transportSecurity);
    }

    public GridTestSuite(String name, String containerConfigFile)
    {
        this(name, containerConfigFile, false);
    }

    public void addTestSuite(Class testClass)
    {
        if((this.testClassPattern != null) &&
           (!this.testClassPattern.equals("*")))
        {
            if(testClass.getName().indexOf(this.testClassPattern) == -1)
            {
                return;
            }
        }

        super.addTestSuite(testClass);
    }

    public void run(TestResult result)
    {
        if(this.transportSecurity)
        {
            if(this.containerConfigFile == null)
            {
                this.testContainer = new GSITestContainer();
            }
            else
            {
                this.testContainer =
                    new GSITestContainer(this.containerConfigFile);
            }
        }
        else
        {
            if(this.containerConfigFile == null)
            {
                this.testContainer = new TestContainer();
            }
            else
            {
                this.testContainer =
                    new TestContainer(this.containerConfigFile);
            }
        }
        try
        {
            this.testContainer.start();
            GridTestCase.setTestServer(this.testContainer);
            super.run(result);
        }
        catch(Exception e)
        {
            result.addError(this, e);
        }
        finally
        {
            try
            {
                this.testContainer.stop();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
