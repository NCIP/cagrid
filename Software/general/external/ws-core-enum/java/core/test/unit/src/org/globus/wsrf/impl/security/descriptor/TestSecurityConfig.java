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
package org.globus.wsrf.impl.security.descriptor;

import java.util.List;
import java.util.Vector;

import javax.security.auth.Subject;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.globus.axis.gsi.GSIConstants;
import org.globus.gsi.jaas.GlobusPrincipal;
import org.globus.security.gridmap.GridMap;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authorization.HostAuthorization;
import org.globus.wsrf.jndi.JNDIUtils;
import org.globus.wsrf.security.SecureResource;
import org.globus.wsrf.security.SecurityManager;
import org.globus.wsrf.test.GridTestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TestSecurityConfig extends GridTestCase {

    static Log logger =
        LogFactory.getLog(TestSecurityConfig.class.getName());

    private String USER1 = "/CN=foo";
    private String service1Path = "TestService1";
    private String service2Path = "TestService2";
    private Subject service1Subject = null;
    private Subject service2Subject = null;
    private Subject containerSubject = null;
    private GridMap service1GridMap = null;
    private GridMap service2GridMap = null;
    private GridMap containerGridMap = null;
    private String containerReplayWin = "10000";
    private String replayInterval = "randomVal1";
    private String contextInterval = "ramdomVal2";
    private String service2ReplayWin = "20000";

    public TestSecurityConfig(String name) throws Exception {
        super(name);
        // Initialize JNDI
        JNDIUtils.initJNDI();
        logger.debug("Done");
    }

    public static Test suite() {
        return new TestSuite(TestSecurityConfig.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void testServiceSecurityConfig() throws Exception {

        assertTrue(TEST_CONTAINER != null);

        // Store security properties for a service and retrieve. No
        // container stuff stored.
        service1Test();
        logger.debug("service 1 Done");

        // Here context and replay timer shld be null
        ContainerSecurityDescriptor desc =
            ContainerSecurityConfig.getConfig().getSecurityDescriptor();
        assertTrue(desc.getReplayTimerInterval() == null);
        assertTrue(desc.getContextTimerInterval() == null);

        // Store container stuff
        containerTest();
        logger.debug("container Done");

        ContainerSecurityDescriptor desc1 =
            ContainerSecurityConfig.getConfig().getSecurityDescriptor();
        assertTrue(replayInterval.equals(desc1.getReplayTimerInterval()));
        assertTrue(contextInterval.equals(desc1.getContextTimerInterval()));

        // Store a second service to check if properties are placed in
        // correct location. Reset subject and gridamp to be null;
        service2Test();
        logger.debug("service 2 Done");
        // Retrieve properties of both services thrr' helper API.
        serviceHelperTest();
        logger.debug("service helper Done");

        clientDescriptorTest();
    }

    private void clientDescriptorTest() throws Exception {

        ClientSecurityDescriptor desc =
            ClientSecurityConfig.initialize("org/globus/wsrf/impl/security/descriptor/client-security-desc.xml");
        assertTrue(Constants.ENCRYPTION.equals(desc.getGSISecureMsg()));
        assertTrue(Constants.SIGNATURE.equals(desc.getGSISecureConv()));
        assertTrue(Boolean.TRUE.equals(desc.getAnonymous()));
        assertTrue(GSIConstants.GSI_MODE_FULL_DELEG
                   .equals(desc.getDelegation()));
        assertTrue(desc.getAuthz() instanceof HostAuthorization);
    }

    private void service1Test() throws Exception {

        logger.debug("service one test");
        // Initialize
        ServiceSecurityConfig.initialize(this.service1Path, null);
        logger.debug("service1Path initialized");
        // Subject should be null as yet.
        assertTrue(ServiceSecurityConfig.getSubject(this.service1Path)
                   == null);
        // Refresh shld not throw an error despite not having credential
        ServiceSecurityConfig.refresh(this.service1Path);
        logger.debug("refresh done");
        // Create and store subject
        this.service1Subject = new Subject();
        this.service1Subject.getPrincipals().add(new GlobusPrincipal(USER1));
        ServiceSecurityConfig.setSubject(this.service1Subject,
                                         this.service1Path);

        // Try refresh, will not happen since no security descriptor,
        // but shld not throw errors
        ServiceSecurityConfig.refresh(this.service1Path);

        Subject subject = ServiceSecurityConfig.getSubject(this.service1Path);
        assertTrue(subject != null);
        assertTrue(subject.equals(this.service1Subject));

        // Create and store gridmap
        this.service1GridMap = new GridMap();
        this.service1GridMap.map(USER1, "nobody");
        ServiceSecurityConfig.setGridMap(this.service1GridMap,
                                         this.service1Path);

        GridMap gridMap = ServiceSecurityConfig.getGridMap(this.service1Path);
        assertTrue(gridMap != null);
        assertTrue(gridMap.equals(this.service1GridMap));

        // Create and store security descriptor, overwriting what
        // exists before
        ServiceSecurityDescriptor desc = new ServiceSecurityDescriptor();
        desc.setRunAsType(RunAsConstants.CALLER);
        // Default method auth
        Vector methods = new Vector();
        QName qName1 = new QName(SecurityDescriptor.NS, "method1");
        methods.add(qName1);
        QName qName2 = new QName(SecurityDescriptor.NS, "method2");
        methods.add(qName2);
        desc.setAuthMethods(methods);
        // Set rejected proxy
        desc.setRejectLimitedProxy("false");
        desc.setSubject(subject);
        desc.setGridMap(gridMap);
        // Store descriptor
        ServiceSecurityConfig.setSecurityDescriptor(desc, this.service1Path);

        // Retrieve all the above
        // Subject
        subject = ServiceSecurityConfig.getSubject(this.service1Path);
        assertTrue(subject != null);
        assertTrue(subject.equals(this.service1Subject));

        // gridmap
        gridMap = ServiceSecurityConfig.getGridMap(this.service1Path);
        assertTrue(gridMap != null);
        assertTrue(gridMap.equals(this.service1GridMap));

        // security desc
        ServiceSecurityDescriptor secDesc =
            (ServiceSecurityDescriptor)ServiceSecurityConfig
            .getSecurityDescriptor(this.service1Path);
        assertTrue(secDesc != null);
        compare(desc.getAuthMethods(qName1), secDesc.getAuthMethods(qName1));
        compare(desc.getAuthMethods(qName2), secDesc.getAuthMethods(qName2));
        compare(desc.getDefaultAuthMethods(), secDesc.getDefaultAuthMethods());

        assertTrue(desc.getRejectLimitedProxyState() != null);
        assertTrue(desc.getRejectLimitedProxyState().equals("false"));
    }

    private void containerTest() throws Exception {

        logger.debug("container test");

        ContainerSecurityConfig config = ContainerSecurityConfig.getConfig();
        Subject contSubject = config.getSubject();
        // gets default
        assertTrue(contSubject != null);
        logger.debug(contSubject);

        // Create and store subject
        this.containerSubject = new Subject();
        this.containerSubject.getPrincipals().add(new GlobusPrincipal(
            "containerUser"));
        ContainerSecurityConfig.getConfig().setSubject(this.containerSubject);

        // Create and store gridmap
        this.containerGridMap= new GridMap();
        this.containerGridMap.map(USER1, "containerLogin");
        ContainerSecurityConfig.getConfig().setGridMap(this.containerGridMap);

        // Retrieve all the above
        Subject retSubject = ContainerSecurityConfig.getConfig().getSubject();
        assertTrue(retSubject != null);
        assertTrue(this.containerSubject.equals(retSubject));
        logger.debug("Container " + this.containerSubject);

        GridMap retGridMap = ContainerSecurityConfig.getConfig().getGridMap();
        assertTrue(this.containerGridMap.equals(retGridMap));

        // Create and store sec desc
        ContainerSecurityDescriptor desc = new ContainerSecurityDescriptor();
        desc.setRejectLimitedProxy("true");
        desc.setSubject(this.containerSubject);
        desc.setGridMap(this.containerGridMap);
        desc.setReplayAttackWindow(containerReplayWin);
        desc.setReplayTimerInterval(replayInterval);
        desc.setContextTimerInterval(contextInterval);
        ContainerSecurityConfig.getConfig().setSecurityDescriptor(desc);

        ContainerSecurityDescriptor retDesc =
            ContainerSecurityConfig.getConfig().getSecurityDescriptor();
        assertTrue(retDesc != null);
        assertTrue(retDesc.getRejectLimitedProxyState() != null);
        assertTrue(retDesc.getRejectLimitedProxyState().equals("true"));

        retSubject = ContainerSecurityConfig.getConfig().getSubject();
        assertTrue(retSubject != null);
        assertTrue(this.containerSubject.equals(retSubject));
        logger.debug("Container " + this.containerSubject);
    }

    private void service2Test() throws Exception {

        logger.debug("service two test");

        // Initialize
        ServiceSecurityConfig.initialize(this.service2Path, null);
        // Subject should be null as yet.
        assertTrue(ServiceSecurityConfig.getSubject(this.service2Path)
                   == null);

        // Create and store subject
        this.service2Subject = new Subject();
        this.service2Subject.getPrincipals().add(new GlobusPrincipal(USER1));
        ServiceSecurityConfig.setSubject(this.service2Subject,
                                         this.service2Path);
        // Create and store gridmap
        this.service2GridMap = new GridMap();
        this.service2GridMap.map(USER1, "dummy");
        ServiceSecurityConfig.setGridMap(this.service2GridMap,
                                         this.service2Path);

        // Retrieve all the above
        // Subject
        Subject subject = ServiceSecurityConfig.getSubject(this.service2Path);
        assertTrue(subject != null);
        assertTrue(subject.equals(this.service2Subject));

        // gridmap
        GridMap gridMap = ServiceSecurityConfig.getGridMap(this.service2Path);
        assertTrue(gridMap != null);
        assertTrue(gridMap.equals(this.service2GridMap));

        ServiceSecurityDescriptor desc = new ServiceSecurityDescriptor();
        desc.setReplayAttackWindow(service2ReplayWin);
        ServiceSecurityConfig.setSecurityDescriptor(desc, this.service2Path);
    }

    private void serviceHelperTest() throws Exception {

        logger.debug("service helper test");

        // Set subject and gridMap of service 2 to be null
        ServiceSecurityConfig.setSubject(null, this.service2Path);
        ServiceSecurityConfig.setGridMap(null, this.service2Path);

        ContainerSecurityDescriptor retDesc =
            ContainerSecurityConfig.getConfig().getSecurityDescriptor();
        assertTrue(retDesc != null);
        assertTrue(retDesc.getRejectLimitedProxyState() != null);
        assertTrue(retDesc.getRejectLimitedProxyState().equals("true"));

        // Get System subject
        Subject systemSub = SecurityManager.getManager().getSystemSubject();
        assertTrue(systemSub != null);
        assertTrue(!systemSub.equals(this.containerSubject));
        this.containerSubject =
            SecurityManager.getManager().getSystemSubject();

        // Should not affect any other part of the descriptor
        retDesc =
        ContainerSecurityConfig.getConfig().getSecurityDescriptor();
        assertTrue(retDesc != null);
        assertTrue(retDesc.getRejectLimitedProxyState() != null);
        assertTrue(retDesc.getRejectLimitedProxyState().equals("true"));

        // Get service1 subject
        Subject serviceSub =
            SecurityManager.getManager().getServiceSubject(this.service1Path);
        assertTrue(serviceSub != null);
        assertTrue(serviceSub.equals(this.service1Subject));

        // Get service2 subject
        Subject service2Sub =
            SecurityManager.getManager().getServiceSubject(this.service2Path);
        assertTrue(service2Sub.equals(this.containerSubject));

        // Get service2/container subject
        Subject sub =
            SecurityManager.getManager().getSubject(service2Path, null);
        assertTrue(sub != null);
        assertTrue(sub.equals(this.containerSubject));

        // Get reject limited for service 1, shld be false
        assertTrue(Boolean.FALSE
                   .equals(SecurityPropertiesHelper
                           .getRejectLimitedProxyState(this.service1Path,
                                                       null)));
        // shld be true, because of container property
        assertTrue(Boolean.TRUE.equals(
            SecurityPropertiesHelper.getRejectLimitedProxyState(
                this.service2Path, null)));

        // Get replayAttackWin for service1, shld be container's
        assertTrue(containerReplayWin
                   .equals(SecurityPropertiesHelper
                           .getReplayAttackWindow(this.service1Path, null)));
        // Get replayAttackWin for service2
        assertTrue(service2ReplayWin
                   .equals(SecurityPropertiesHelper
                           .getReplayAttackWindow(this.service2Path, null)));

        // Get service1 gridmap
        assertTrue(SecurityPropertiesHelper.getGridMap(this.service1Path, null)
                   .equals(this.service1GridMap));
        assertTrue(SecurityPropertiesHelper.getGridMap(this.service2Path, null)
                   .equals(this.containerGridMap));

        // Try adding resoure for above
        TestResource res1 = new TestResource("true");
        // Should be true, now
        assertTrue(Boolean.TRUE.equals(
            SecurityPropertiesHelper.getRejectLimitedProxyState(
                this.service1Path, res1)));

        // Second resource.
        String filename =
            "org/globus/wsrf/impl/security/descriptor/test-security-true.xml";
        SecondTestResource res2 = new SecondTestResource(filename);
        assertTrue(Boolean.TRUE.equals(
            SecurityPropertiesHelper.getRejectLimitedProxyState(
                this.service1Path, res2)));
        filename =
        "org/globus/wsrf/impl/security/descriptor/test-security-false.xml";
        SecondTestResource res3 = new SecondTestResource(filename);
        assertTrue(Boolean.FALSE.equals(
            SecurityPropertiesHelper.getRejectLimitedProxyState(
                this.service2Path, res3)));
    }

    private void compare(List expected, List current) {
	if (expected == null) {
	    assertTrue(current == null);
	    return;
	} else {
	    assertTrue(current != null);
	}

        assertEquals(expected.size(), current.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), current.get(i));
        }
    }
}

class TestResource implements SecureResource {

    ResourceSecurityDescriptor desc = null;

    public TestResource(String reject) {
        this.desc = new ResourceSecurityDescriptor();
        desc.setRejectLimitedProxy(reject);
    }

    public ResourceSecurityDescriptor getSecurityDescriptor() {

        return this.desc;
    }
}

class SecondTestResource implements SecureResource {

    static Log logger =
        LogFactory.getLog(TestSecurityConfig.class.getName());

    ResourceSecurityDescriptor desc = null;

    public SecondTestResource(String filename) throws Exception {

        ResourceSecurityConfig config = new ResourceSecurityConfig(filename);
        config.init();
        this.desc = config.getSecurityDescriptor();
    }

    public ResourceSecurityDescriptor getSecurityDescriptor() {

        return this.desc;
    }
}
