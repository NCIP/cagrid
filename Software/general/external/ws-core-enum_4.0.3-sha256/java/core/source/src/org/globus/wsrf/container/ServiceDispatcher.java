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
package org.globus.wsrf.container;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

import org.apache.axis.AxisEngine;
import org.apache.axis.Constants;
import org.apache.axis.MessageContext;
import org.apache.axis.configuration.DirProvider;
import org.apache.axis.server.AxisServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.tools.DeployConstants;
import org.globus.util.I18n;
import org.globus.wsrf.config.ContainerConfig;
import org.globus.wsrf.container.usage.ContainerUsageBasePacket;
import org.globus.wsrf.impl.security.descriptor.ContainerSecurityDescriptor;
import org.globus.wsrf.utils.Resources;

/**
 * Dispatcher reading requests off the socket and putting them into a
 * request queue.
 */
public class ServiceDispatcher implements Runnable {

    static Log logger =
        LogFactory.getLog(ServiceDispatcher.class.getName());

    static I18n i18n = I18n.getI18n(Resources.class.getName());

    private ServerSocket serverSocket;
    private volatile Thread worker = null;
    private volatile boolean stopped = false;
    private Semaphore semaphore = new Semaphore();
    protected ServiceRequestQueue queue;
    protected ServiceThreadPool threadPool;
    protected int numThreads;
    protected int maxThreads;
    protected int highWaterMark;
    protected AxisServer engine;
    protected MessageContext msgContext;
    protected ContainerSecurityDescriptor containerDescriptor;

    protected ServiceDispatcher() {}

    public ServiceDispatcher(Map properties) throws Exception {
        // single file configuration
        // this.engine = new AxisServer(new FileProvider(config));

        String configFile =
            (String)properties.get(ServiceContainer.SERVER_CONFIG);
        configFile = (configFile == null) ?
            ContainerConfig.DEFAULT_SERVER_CONFIG : configFile;

        String configProfile =
            (String)properties.get(ContainerConfig.CONFIG_PROFILE);
        if (configProfile != null) {
            configFile = configProfile + "-" + configFile;
        }

        String baseDir = ContainerConfig.getGlobusLocation() +
            File.separator + DeployConstants.CONFIG_BASE_DIR;
        // init engine
        this.engine = new AxisServer(new DirProvider(baseDir, configFile));

        String webInfPath = ServiceThread.getConfigRootPath(this.engine);
        String homeDir = ServiceThread.getWebRootPath(this.engine);
        
        BaseContainerConfig.setEngine(this.engine);
        BaseContainerConfig.setBaseDirectory(webInfPath);
        BaseContainerConfig.setSchemaDirectory(homeDir);

        // init message context
        this.msgContext = new MessageContext(this.engine);
        this.msgContext.setProperty(Constants.MC_HOME_DIR, homeDir);
        this.msgContext.setProperty(Constants.MC_CONFIGPATH, webInfPath);

        // set config profile info
        if (configProfile != null) {
            this.msgContext.setProperty(ContainerConfig.CONFIG_PROFILE,
                                        configProfile);
        }

        // set container type
        UsageConfig.setContainerType(
                      ContainerUsageBasePacket.STANDALONE_CONTAINER);

        this.containerDescriptor = (ContainerSecurityDescriptor)properties
            .get(ServiceContainer.CONTAINER_DESCRIPTOR);
        this.numThreads = -1;
    }

    protected void init() throws Exception {
        ContainerConfig config = ContainerConfig.getConfig(this.engine);

        // ensure host info is ok
        String host = ServiceHost.getHost(config);

        // set serverID - step 1) lookup system property
        String serverID = 
            System.getProperty(ContainerConfig.CONTAINER_ID_PROPERTY);
        if (serverID == null) {
            // set serverID - step 2) get it from configuration
            serverID = config.getOption(ContainerConfig.CONTAINER_ID);
            if (serverID == null) {
                // set serverID - step 3) default to host-port
                serverID = host + "-" + ServiceHost.getPort(null);
            }
        }
        BaseContainerConfig.setContainerID(serverID);
        
        // if container config is not initialized off command line,
        // this will do it.
        ServiceManager.getServiceManager(this.engine).start(this.msgContext);

        this.queue = new ServiceRequestQueue();
        setupThreadPool();
    }

    protected void setupThreadPool() throws Exception {
        this.threadPool = new ServiceThreadPool(this.queue, this.engine);
    }

    public AxisEngine getAxisEngine() {
        return this.engine;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public void setThreads(int numThreads) {
        this.numThreads = numThreads;
    }

    private static int getOptionAsInt(ContainerConfig config,
                                      String property,
                                      int defaultValue) {
        String value = config.getOption(property);
        if (value == null) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                logger.error(i18n.getMessage("configError", property), e);
                return defaultValue;
            }
        }
    }

    public void run() {
        ContainerConfig config = ContainerConfig.getConfig(this.engine);

        int timeout = 
            getOptionAsInt(config,
                           ContainerConfig.CONTAINER_TIMEOUT,
                           // default 3 minutes timeout 
                           1000 * 60 * 3); 
        
        if (numThreads <= 0) {
            numThreads = 
                getOptionAsInt(config,
                               ContainerConfig.CONTAINER_THREADS,
                               0);
            
            maxThreads = 
                getOptionAsInt(config,
                               ContainerConfig.CONTAINER_THREADS_MAX,
                               0);

            highWaterMark = 
                getOptionAsInt(config,
                               ContainerConfig.CONTAINER_THREADS_WATERMARK,
                               0);
        }

        if (numThreads < 1) {
            numThreads = 1;
        }

        if (maxThreads == 0) {
            maxThreads = numThreads * 4;
        }

        if (highWaterMark == 0) {
            highWaterMark = numThreads * 2;
        }

        logger.debug("Starting up container with " + numThreads + " threads");

        this.threadPool.startThreads(numThreads);
        this.semaphore.sendSignal();

        int addedThreads = 0;
        int acceptError = 0;

        while (!isStopped()) {
            Socket socket = null;

            try {
                socket = serverSocket.accept();
            } catch (IOException ioe) {
                if (isStopped()) {
                    // got stopped - just exit
                    break;
                } else {
                    // error
                    logger.error(
                       i18n.getMessage("serviceDispatcherAcceptError01"), ioe);
                    if (acceptError > 0) {
                        // sleep 10 sec
                        try { 
                            Thread.sleep(1000 * 10);
                        } catch (InterruptedException e) {}
                    } else {
                        acceptError++;
                    }
                    continue;
                }
            }

            acceptError = 0;

            try {
                socket.setSoTimeout(timeout);
            } catch (SocketException e) {
                logger.warn(i18n.getMessage("soTimeoutFailed"), e);
            }

            int waitingThreads =
                this.queue.enqueue(new ServiceRequest(socket, serverSocket));
            
            if (logger.isDebugEnabled()) {
                logger.debug("waiting threads: " + waitingThreads);
            }
            
            if (waitingThreads == 0 &&
                this.threadPool.getThreads() < maxThreads) {
                addedThreads += addThread();
            } else if (waitingThreads > highWaterMark) {
                if (addedThreads > 0) {
                    removeThread();
                    addedThreads --;
                }
            }
        }
    }

    public void waitForInit() throws InterruptedException {
        this.semaphore.waitForSignal();
    }

    public void waitForStop() throws InterruptedException {
        this.threadPool.waitForThreads();
    }

    private synchronized int addThread() {
        if (this.stopped) {
            return 0;
        } else {
            this.threadPool.startThreads(1);
            logger.debug("added thread");
            return 1;
        }
    }
    
    private void removeThread() {
        this.threadPool.stopThreads(1);
        logger.debug("removed thread");
    }

    public synchronized void stop() throws IOException {
        if (this.stopped) {
            return;
        }

        logger.debug("Stopping dispatcher");

        this.stopped = true;

        // close server socket first
        // so that no more connection can be made
        try {
            if (this.serverSocket != null) {
                this.serverSocket.close();
            }
        } finally {
            if (this.threadPool != null) {
                // request threads to stop
                this.threadPool.stopThreads();
                // wait util they actually stop or 2 min time out
                try {
                    this.threadPool.waitForThreads(1000 * 60 * 2);
                } catch (InterruptedException e) {
                    // we can ignore it
                }
            }
            
            // stop services
            if (this.engine != null) {
                ServiceManager.getServiceManager(this.engine).stop();
                this.engine.cleanup();
            }

            logger.debug("Stopped dispatcher");
        }
    }

    public synchronized boolean isStopped() {
        return this.stopped;
    }

    /**
     * Start this dispatcher.
     *
     * Spawns a worker thread to listen for HTTP requests.
     *
     * @param daemon a boolean indicating if the thread should be a daemon.
     */
    public void start(boolean daemon) {
        this.worker = new Thread(this);
        this.worker.setName("ServiceDispacher" + this.worker.getName());
        this.worker.setDaemon(daemon);
        this.worker.start();
    }
}
