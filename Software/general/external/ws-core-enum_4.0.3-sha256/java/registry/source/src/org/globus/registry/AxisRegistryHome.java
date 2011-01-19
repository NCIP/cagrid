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
package org.globus.registry;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.AxisEngine;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.MessageContext;
import org.apache.axis.WSDDEngineConfiguration;
import org.apache.axis.deployment.wsdd.WSDDDeployment;
import org.apache.axis.deployment.wsdd.WSDDService;
import org.apache.axis.message.addressing.AttributedURI;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.message.addressing.ReferencePropertiesType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.util.I18n;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.NoSuchResourceException;
import org.globus.wsrf.RemoveNotSupportedException;
import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceHome;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.config.ContainerConfig;
import org.globus.wsrf.impl.SimpleResourceKey;
import org.globus.wsrf.utils.Resources;
import org.oasis.wsrf.servicegroup.EntryType;

public class AxisRegistryHome implements ResourceHome {

    private static final I18n i18n = 
        I18n.getI18n(Resources.class.getName());

    private static final Log logger =
        LogFactory.getLog(AxisRegistryHome.class.getName());

    private static final int TIMEOUT = 1000 * 60 * 5;

    private static final QName KEY = 
        new QName("http://axis.org", "ServiceName");

    public Class getKeyTypeClass() {
        return String.class;
    }

    public QName getKeyTypeName() {
        return KEY;
    }
    
    private AxisEngine engine;
    private WSDDDeployment deployment;
    private EndpointReferenceType serviceGroupEPR;
    private String serviceGroupServiceName;
    private String serviceGroupEntryServiceName;
    private String baseURL;
    private Map services;
    private long lastRefresh;

    public void setServiceGroupServiceName(String name) {
        this.serviceGroupServiceName = name;
    }
    
    public String getServiceGroupServiceName() {
        return this.serviceGroupServiceName;
    }

    public AxisRegistryHome() {
        MessageContext ctx = MessageContext.getCurrentContext();
        this.serviceGroupEntryServiceName = ctx.getTargetService();
    }

    // assumes context is associated with the thread
    // with TRANS_URL property set.
    private synchronized void init() throws Exception {
        if (this.baseURL != null) {
            return;
        }
            
        MessageContext context = MessageContext.getCurrentContext();
        if (context == null) {
            throw new Exception(i18n.getMessage("noMsgContext"));
        }

        this.engine = context.getAxisEngine();

        EngineConfiguration config = this.engine.getConfig();
        if (!(config instanceof WSDDEngineConfiguration)) {
            Object [] args =  
                new Object[] {WSDDEngineConfiguration.class,
                              (config == null) ? null : config.getClass()};
            throw new Exception(i18n.getMessage("expectedType", args));
        }
        
        this.deployment = ((WSDDEngineConfiguration)config).getDeployment();

        String transURL = 
            (String)context.getProperty(MessageContext.TRANS_URL);

        if (transURL == null) {
            throw new Exception(i18n.getMessage("noTransURL"));
        }

        ContainerConfig conConfig = ContainerConfig.getConfig(this.engine);

        URL url = new URL(transURL);
        URL basicURL = new URL(url.getProtocol(),
                               url.getHost(),
                               url.getPort(),
                               "/" + conConfig.getWSRFLocation());
        this.baseURL = basicURL.toString();
        this.serviceGroupEPR = getEPR(this.serviceGroupServiceName);
    }

    private synchronized void refresh() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastRefresh > TIMEOUT) {
            // perform refresh

            try {
                init();
                createEntries();
                this.lastRefresh = currentTime;
            } catch (Exception e) {
                logger.error("Failed to initialize registry", e);
            }
        }
    }
    
    // assumes initialization was done
    private synchronized void createEntries() throws Exception {
        this.services = new HashMap();
        WSDDService[] services = this.deployment.getServices();
        
        SimpleResourceKey key = null;
        AxisServiceGroupEntry entry = null;
        EndpointReferenceType sgeEPR = null;
        EndpointReferenceType memberEPR = null;

        for (int i=0;i<services.length;i++) {
            String serviceName = services[i].getQName().getLocalPart();
            
            key = new SimpleResourceKey(KEY, serviceName);
            
            sgeEPR = getSGEEPR(this.serviceGroupEntryServiceName,
                               serviceName);
            
            memberEPR = getEPR(serviceName);
            
            entry = new AxisServiceGroupEntry(sgeEPR, 
                                              this.serviceGroupEPR,
                                              memberEPR);
            
            this.services.put(key, entry);
        }
    }

    public Resource find(ResourceKey key) 
        throws ResourceException,
               NoSuchResourceException,
               InvalidResourceKeyException {
        if (key == null) {
            throw new InvalidResourceKeyException();
        }
        
        refresh();
        
        Map srvs = this.services;
        if (srvs == null) {
            throw new NoSuchResourceException();
        } else {
            Resource service = (Resource)srvs.get(key);
            if (service == null) {
                throw new NoSuchResourceException();
            } else {
                return service;
            }
        }
    }

    public void remove(ResourceKey key) 
        throws ResourceException {
        throw new RemoveNotSupportedException();
    }

    protected EntryType[] getEntries() {

        refresh();

        Map srvs = this.services;
        if (srvs == null) {
            return null;
        }
        
        EntryType[] entries = new EntryType[srvs.size()];
        AxisServiceGroupEntry entry;
        Iterator iter = srvs.entrySet().iterator();
        int i = 0;
        while(iter.hasNext()) {
            entry = (AxisServiceGroupEntry)((Map.Entry)iter.next()).getValue();
            
            entries[i] = new EntryType();
            entries[i].setMemberServiceEPR(entry.getMemberEPR());
            entries[i].setServiceGroupEntryEPR(entry.getServiceGroupEntryEPR());
            entries[i].setContent(entry.getContent());

            i++;
        }
        
        return entries;
    }

    private EndpointReferenceType getSGEEPR(String service, 
                                            String serviceName)
        throws Exception {
        EndpointReferenceType epr = getEPR(service);
        ReferencePropertiesType refProps = new ReferencePropertiesType();
        SimpleResourceKey key = new SimpleResourceKey(KEY, serviceName);
        refProps.add(key.toSOAPElement());
        epr.setProperties(refProps);
        return epr;
    }

    private EndpointReferenceType getEPR(String service)
        throws IOException {
        EndpointReferenceType epr = new EndpointReferenceType();
        epr.setAddress(new AttributedURI(this.baseURL + service));
        epr.setProperties(null);
        return epr;
    }

}
