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

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.deployment.wsdd.WSDDConstants;
import org.apache.axis.deployment.wsdd.WSDDDeployment;
import org.apache.axis.deployment.wsdd.WSDDService;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.utils.XMLUtils;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceProperties;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.impl.ReflectionResourceProperty;

public class AxisService implements Resource, ResourceProperties {

    public static final QName RP_SET =
        new QName(WSDDConstants.URI_WSDD, "ServiceWSDD");

    public static final QName WSDL =
        new QName(WSDDConstants.URI_WSDD, "wsdlFile");

    public static final QName PROVIDER =
        new QName(WSDDConstants.URI_WSDD, "provider");

    private ResourcePropertySet propSet;
    private WSDDService service;
    private WSDDDeployment deployment;

    public AxisService(WSDDDeployment deployment, WSDDService service) {
        this.deployment = deployment;
        this.service = service;
        this.propSet = new AxisPropertySet(RP_SET, this);

        ResourceProperty prop = null;

        // add all wsdd parameters as RPs
        Map wsddParameters = service.getParametersTable();
        Iterator iter = wsddParameters.entrySet().iterator();
        String name = null;
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            // skip all non-string values in case
            if (!(entry.getValue() instanceof String)) {
                continue;
            }
            name = (String)entry.getKey();
            if (name.equals("providers")) {
                prop = new AxisServiceProperty(this, name, " ");
            } else {
                prop = new AxisServiceProperty(this, name);
            }
            this.propSet.add(prop);
        }

        // add wsdl parameter RP (special case)
        try {
            prop = new ReflectionResourceProperty(WSDL, "WSDLFile", this);
            this.propSet.add(prop);
            prop = new ReflectionResourceProperty(PROVIDER, "Provider", this);
            this.propSet.add(prop);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResourcePropertySet getResourcePropertySet() {
        return this.propSet;
    }

    public WSDDService getWSDDService() {
        return this.service;
    }

    public String getWSDLFile() {
        return this.service.getServiceDesc().getWSDLFile();
    }

    public void setWSDLFile(String file) {
        undeploy();
        this.service.getServiceDesc().setWSDLFile(file);
        deploy();
    }

    public QName getProvider() {
        return this.service.getProviderQName();
    }

    public void setProvider(QName name) {
        undeploy();
        this.service.setProviderQName(name);
        deploy();
    }

    void undeploy() {
        this.deployment.undeployService(this.service.getQName());
    }

    void deploy() {
        StringWriter writer = new StringWriter();
        SerializationContext context =
            new SerializationContext(writer, null);
        context.setPretty(true);
        try {
            this.service.writeToContext(context);
            writer.close();
            Document doc = XMLUtils.newDocument(new InputSource(new StringReader(writer.getBuffer().toString())));
            WSDDService newService = new WSDDService(doc.getDocumentElement());
            this.deployment.deployService(newService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
