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

import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.globus.wsrf.container.ServiceContainer;

public class TestContainer
{
    protected ServiceContainer server = null;
    protected Map properties = null;

    protected String baseURL;
    protected String webServerURL = null;

    protected static final String WEB_SERVER_URL = 
        "web.server.url";

    private boolean collocated;

    private static Log logger = 
        LogFactory.getLog(TestContainer.class.getName());

    public TestContainer()
    {
        this.properties = new HashMap();
        this.properties.put(ServiceContainer.MAIN_THREAD,
                            Boolean.FALSE);
    }

    public TestContainer(String containerConfigFile)
    {
        this();
        this.properties.put(ServiceContainer.SERVER_CONFIG,
                            containerConfigFile);
    }

    public boolean isCollocated()
    {
        return this.collocated;
    }

    protected String getPropertySeverUrl() {
        return System.getProperty(WEB_SERVER_URL);
    }

    public void start() throws Exception
    {
        this.webServerURL = getPropertySeverUrl();

        if(this.webServerURL == null)
        {
            if(server == null)
            {
                this.collocated = true;
                this.server =
                    ServiceContainer.createContainer(this.properties);
                logger.debug("Starting standalone container at:" +
                             this.server.getURLString());
            }

            this.baseURL = this.server.getURLString();
        }
        else
        {
            this.collocated = false;
            this.baseURL = getIP(this.webServerURL);
            logger.debug(
                "Starting test using server container at:" + this.baseURL);
        }
    }

    public void stop() throws Exception
    {
        if(this.webServerURL == null && this.server != null)
        {
            this.server.stop();
        }
    }

    public String getBaseURL()
    {
        return this.baseURL;
    }

    public int getPort()
    {
        int port = 0;

        try
        {
            port = new URL(this.baseURL).getPort();
        }
        catch(Exception e)
        {
        }

        return port;
    }

    public String getHost()
    {
        try
        {
            return new URL(this.baseURL).getHost();
        }
        catch(Exception e)
        {
            return "localhost";
        }
    }

    public String getIP(String urlString) throws Exception
    {
        URL url = new URL(urlString);
        String host = url.getHost();
        String ipHost = null;

        if(host.equals("localhost") || host.equals("127.0.0.1"))
        {
            ipHost = InetAddress.getLocalHost().getHostAddress();
        }
        else
        {
            try
            {
                ipHost = InetAddress.getByName(host).getHostAddress();
            }
            catch(Exception e)
            {
                // assume ip-address was used
                ipHost = host;
            }
        }

        url = new URL(url.getProtocol(), ipHost,
                      url.getPort(), url.getPath());
        return url.toExternalForm();
    }
}
