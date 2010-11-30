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
package org.globus.wsrf.samples.management;

import org.apache.axis.AxisEngine;
import org.apache.axis.MessageContext;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.WSDDEngineConfiguration;
import org.apache.axis.deployment.wsdd.WSDDDeployment;
import org.apache.axis.deployment.wsdd.WSDDService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceHome;
import org.globus.wsrf.RemoveNotSupportedException;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.NoSuchResourceException;

import javax.xml.namespace.QName;

public class AxisRegistryHome implements ResourceHome {

    static Log logger =
        LogFactory.getLog(AxisRegistryHome.class.getName());

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
    
    public AxisRegistryHome() {
        MessageContext context = MessageContext.getCurrentContext();
        if (context == null) {
            throw new RuntimeException("no ctx");
        }
        this.engine = context.getAxisEngine();
        if (this.engine == null) {
            throw new RuntimeException("no engine");
        }
        EngineConfiguration config = this.engine.getConfig();
        if (!(config instanceof WSDDEngineConfiguration)) {
            throw new RuntimeException("wrong config");
        }
        this.deployment = ((WSDDEngineConfiguration)config).getDeployment();
    }

    public Resource find(ResourceKey key) 
        throws ResourceException,
               NoSuchResourceException,
               InvalidResourceKeyException {
        if (key == null) {
            throw new InvalidResourceKeyException();
        }
        QName serviceName = new QName("", (String)key.getValue());
        WSDDService service = this.deployment.getWSDDService(serviceName);
        if (service == null) {
            throw new NoSuchResourceException();
        }
        return new AxisService(this.deployment, service);
    }

    public void remove(ResourceKey key) 
        throws ResourceException {
        throw new RemoveNotSupportedException();
    }

}
