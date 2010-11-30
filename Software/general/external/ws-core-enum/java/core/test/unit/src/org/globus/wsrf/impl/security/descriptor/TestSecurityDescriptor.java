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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.security.auth.Subject;
import javax.xml.namespace.QName;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.axis.gsi.GSIConstants;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.gsi.jaas.GlobusPrincipal;
import org.globus.gsi.jaas.PasswordCredential;
import org.globus.security.gridmap.GridMap;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authentication.encryption.EncryptionCredentials;
import org.globus.wsrf.impl.security.authorization.HostAuthorization;
import org.globus.wsrf.impl.security.util.AuthUtil;
import org.globus.wsrf.impl.security.util.FixedObjectInputStream;
import org.globus.wsrf.test.GridTestCase;
import org.globus.wsrf.utils.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class TestSecurityDescriptor extends GridTestCase {

    static Log logger =
        LogFactory.getLog(TestSecurityDescriptor.class.getName());

    private static final String SEC_DESC_1 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<securityConfig xmlns=\"http://www.globus.org\">" +
	"<context-lifetime value=\"1000\"/>" +
	"<reject-limited-proxy value=\"true\"/>" +
	"<gridmap value=\"fooGridMap\"/>" +
	"<replay-attack-filter value=\"true\"/>" +
	"<credential>" +
	"<key-file value=\"keyFile\"/>" + 
	"<cert-file value=\"certFile\"/>" + 
	"</credential>" +
	"<proxy-file value=\"proxyFile\"/>" +
        "<authz value=\"someNS:org.globus.someAuthz,gridmap\"/>" +
	" </securityConfig>"; 
	
    private static final String SEC_DESC_2 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<securityConfig xmlns=\"http://www.globus.org\">" +
	"<context-lifetime value=\"1000\"/>" +
	"<credential>" +
	"<cert-file value=\"certFile\"/>" + 
	"</credential>" +
	"<proxy-file value=\"proxyFile\"/>" + 
	"<authz value=\"self\"/>" + 
	" </securityConfig>"; 

    private static final String SERVICE_DESC_1 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<securityConfig xmlns=\"http://www.globus.org\">" +
        "<method name=\"gss:initTokenExchange\" xmlns:gss=\"http://gss.com\">" +
        "  <run-as>" + 
	"   <service-identity/>" + 
	"  </run-as>" +
        "  <auth-method>" + 
	"    <GSISecureConversation/>" + 
	"  </auth-method>" + 
	"</method>" +
        "<method name=\"gss:continueTokenExchange\" "
	+ "xmlns:gss=\"http://gss.com\">" +
        "  <run-as>" + 
	"   <system-identity/>" + 
	"  </run-as>" +
        "  <auth-method>" + 
	"    <GSISecureConversation>" + 
	"      <protection-level>" +
        "         <integrity/>" + 
	"      </protection-level>" + 
	"    </GSISecureConversation>" +
        "    <GSISecureMessage>" +
	"       <protection-level>" +
	"          <integrity/>" +
	"      </protection-level> " +
	"     </GSISecureMessage>" +
	"  </auth-method>" + 
	"</method>" +
        "<method name=\"wsse:findData\" xmlns:wsse=\"http://wsse.com\">" +
        "  <auth-method>" + 
	"    <none/>" + 
	"  </auth-method>" + 
	"</method>" +
        "<run-as>" + 
	" <caller-identity/>" + 
	"</run-as>" + 
	"<auth-method>" +
        "    <GSISecureConversation>" + 
	"      <protection-level>" + 
	"         <privacy/>" +
        "      </protection-level>" + 
	"    </GSISecureConversation>" + 
	"</auth-method>" +
	"<method name=\"wsse:dummyMeth\" xmlns:wsse=\"http://wsse.com\">" +
	" <auth-method>" +
	"   <GSISecureMessage>" +
	"      <protection-level>" +
	"        <integrity/>" +
	"      </protection-level>" +
	"    </GSISecureMessage>" +
	"   <GSISecureConversation> " +
	"       <protection-level>" +
	"         <privacy/>" +
	"       </protection-level> " +
	"    </GSISecureConversation>" +
	" </auth-method>" +
	"</method> " +
	"<method name=\"wsse:dummyMeth1\" xmlns:wsse=\"http://wsse.com\">" +
	" <auth-method>" +
	"   <GSISecureMessage/>" +
	"   <GSISecureConversation> " +
	"      <protection-level>" +
	"          <privacy/>" +
	"      </protection-level> " +
	"   </GSISecureConversation>" +
	" </auth-method>" +
	"</method>" +
	"<method name=\"dummyMeth2\">" +
	" <auth-method>" +
	"   <GSISecureMessage/>" +
	"   <GSISecureConversation/> " +
	" </auth-method>" +
	"</method> <reject-limited-proxy value=\"false\"/> " +
	"<context-lifetime value=\"100\"/>" +
	"</securityConfig>";

    private static final String SERVICE_DESC_2 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<securityConfig xmlns=\"http://www.globus.org\">" +
        "<method name=\"gss:continueTokenExchange\" "	+ 
        "xmlns:gss=\"http://gss.com\">" +
        "  <run-as>" + 
	"   <service-identity/>" + 
	"  </run-as>" +
        "  <auth-method>" + 
	"    <GSISecureConversation>" + 
	"      <protection-level>" +
        "         <integrity/>" + 
	"      </protection-level>" + 
	"    </GSISecureConversation>" +
        "    <GSISecureMessage>" +
	"       <protection-level>" +
	"          <integrity/>" +
	"      </protection-level> " +
	"     </GSISecureMessage>" +
	"  </auth-method>" + 
	"</method>" +
        "<method name=\"gss:tempMethod\" "	+ 
        "xmlns:gss=\"http://gss.com\">" +
        "  <auth-method>" + 
	"    <GSITransport>" + 
	"      <protection-level>" +
        "         <integrity/>" + 
	"      </protection-level>" + 
	"    </GSITransport>" + 
	"  </auth-method>" + 
	"</method>" +
        "  <auth-method>" + 
	"    <GSITransport>" + 
	"      <protection-level>" +
        "         <privacy/>" + 
	"      </protection-level>" + 
	"    </GSITransport>" + 
	"  </auth-method>" +
	"</securityConfig>";

    private static final String CONTAINER_DESC_1 = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<securityConfig xmlns=\"http://www.globus.org\">" +
	"<replay-timer-interval value=\"1000\"/>" + 
	"<context-timer-interval value=\"12000\"/>" + 
	"</securityConfig>";

    private static final String CLIENT_DESC_1 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<securityConfig xmlns=\"http://www.globus.org\">" +
	"<authz value=\"host\"/>" +
	"<GSISecureMessage><privacy/><peer-credentials value=\"dummyPeer\"/>" +
	"</GSISecureMessage>" +
       	"<credential>" +
	"<cert-file value=\"certFile\"/>" + 
	"<key-file value=\"keyFile\"/>" + 
	"</credential>" +
	"<proxy-file value=\"proxyFile\"/>" + 
	"</securityConfig>";

    private static final String CLIENT_DESC_2 =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
        "<securityConfig xmlns=\"http://www.globus.org\">" +
	"<GSISecureMessage><privacy/><peer-credentials value=\"dummyPeer\"/>" +
	"</GSISecureMessage>" +
	"<GSISecureConversation><integrity/><delegation value=\"full\"/>" +
	"<anonymous/></GSISecureConversation></securityConfig>";

    public TestSecurityDescriptor(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TestSecurityDescriptor.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    protected Element getDescriptor(String desc) throws Exception {
        InputSource is =
            new InputSource(new ByteArrayInputStream(desc.getBytes()));
        Document doc = XmlUtils.newDocument(is);

        return doc.getDocumentElement();
    }

    // test gsi transport
    public void testGSITransport() throws Exception {

        ServiceSecurityDescriptor desc = new ServiceSecurityDescriptor();
        Element elem = getDescriptor(SERVICE_DESC_2);
        desc.parse(elem);

        // tempMethod GSITransport (integrity) and default privacy
        QName name = new QName("http://gss.com", "tempMethod");
        List methods = new Vector();
        methods.add(GSITransportAuthMethod.INTEGRITY);
        compare(methods, desc.getAuthMethods(name));
        
        methods.clear();
        
        methods.add(GSITransportAuthMethod.PRIVACY);
        compare(methods, desc.getDefaultAuthMethods());
    }

    // Tests descriptor with parameters that can be in both Service
    // and Container SecurityDescriptor
    public void testSecurityDesc() throws Exception {

        assertTrue(TEST_CONTAINER != null);

	// All parameters are correctly set
	ServiceSecurityDescriptor desc = new ServiceSecurityDescriptor();
	Element elem = getDescriptor(SEC_DESC_1);
	desc.parse(elem);
        verifyDescriptor1(desc);
   
	ContainerSecurityDescriptor desc1 = new ContainerSecurityDescriptor();
	elem = getDescriptor(SEC_DESC_1);
	desc1.parse(elem);
	
	// Credential parameter is not set correctly
	desc = new ServiceSecurityDescriptor();
	elem = getDescriptor(SEC_DESC_2);
	boolean exp = false;
	try {
	    desc.parse(elem);
	    fail("Did not throw exception as expected");
	} catch (Exception e) {
	    if (e.getMessage().indexOf("Credential") != -1)
		exp = true; 
	    else 
		e.printStackTrace();
	}
	assertTrue(exp);
    }

    private void verifyDescriptor1(ServiceSecurityDescriptor desc) 
        throws Exception {

	assertTrue(desc.getContextLifetime().equals(new Integer(1000)));
	assertTrue(desc.getRejectLimitedProxyState().equals("true"));
	assertTrue(desc.getGridMapFile().equals("fooGridMap"));
	assertTrue(desc.getReplayAttackFilter().equals("true"));
	assertTrue(desc.getKeyFilename().equals("keyFile"));
	assertTrue(desc.getCertFilename().equals("certFile"));
	assertTrue(desc.getProxyFilename().equals("proxyFile"));
	assertTrue(desc.getAuthz()
                   .equals("someNS:org.globus.someAuthz,gridmap"));
    }

    // Run-as stuff
    public void testMethodIdentity() throws Exception {
        ServiceSecurityDescriptor desc = new ServiceSecurityDescriptor();

        Element elem = getDescriptor(SERVICE_DESC_1);
        desc.parse(elem);
        verifyDescriptor2(desc);

        ServiceSecurityDescriptor desc1 = new ServiceSecurityDescriptor();
        elem = getDescriptor(SERVICE_DESC_2);
        desc1.parse(elem);
        
        QName name = new QName("http://gss.com", "continueTokenExchange");
        assertEquals(-1, desc1.getDefaultRunAsType());
        assertEquals(RunAsConstants.SERVICE, desc1.getRunAsType(name));
    }

    private void verifyDescriptor2(ServiceSecurityDescriptor desc) {

        QName name = null;

        assertEquals(RunAsConstants.CALLER, desc.getDefaultRunAsType());

        name = new QName("foo", "baar");
        assertEquals(-1, desc.getRunAsType(name));

        name = new QName("http://gss.com", "initTokenExchange");
        assertEquals(RunAsConstants.SERVICE, desc.getRunAsType(name));

        name = new QName("http://gss.com", "continueTokenExchange");
        assertEquals(RunAsConstants.SYSTEM, desc.getRunAsType(name));
    }

    // default auth
    public void testDefaultAuthMethods() throws Exception {
        Element elem = null;
        QName name = null;
        List methods = new Vector();
        ServiceSecurityDescriptor desc = new ServiceSecurityDescriptor();

        elem = getDescriptor(SERVICE_DESC_1);
        desc.parse(elem);

        methods.add(GSISecureConvAuthMethod.PRIVACY);
        compare(methods, desc.getDefaultAuthMethods());

        name = new QName("foo", "baar");
        List getList = desc.getAuthMethods(name);
	assertTrue(getList == null);

        methods.clear();
        methods.add(GSISecureConvAuthMethod.BOTH);
        name = new QName("http://gss.com", "initTokenExchange");
        compare(methods, desc.getAuthMethods(name));

        methods.clear();
        methods.add(GSISecureConvAuthMethod.INTEGRITY);
        methods.add(GSISecureMsgAuthMethod.INTEGRITY);
        name = new QName("http://gss.com", "continueTokenExchange");
        compare(methods, desc.getAuthMethods(name));

        methods.clear();
        methods.add(NoneAuthMethod.getInstance());
        name = new QName("http://wsse.com", "findData");
        compare(methods, desc.getAuthMethods(name));

	methods.clear();
	methods.add(GSISecureMsgAuthMethod.INTEGRITY);
	methods.add(GSISecureConvAuthMethod.PRIVACY);
	name = new QName("http://wsse.com", "dummyMeth");
        compare(methods, desc.getAuthMethods(name));

	methods.clear();
	methods.add(GSISecureMsgAuthMethod.BOTH);
	methods.add(GSISecureConvAuthMethod.PRIVACY);
	name = new QName("http://wsse.com", "dummyMeth1");
        compare(methods, desc.getAuthMethods(name));

	// checks operation name matching with arbitrary namespace
	methods.clear();
	methods.add(GSISecureMsgAuthMethod.BOTH);
	methods.add(GSISecureConvAuthMethod.BOTH);
	name = new QName("http://wsse.com", "dummyMeth2");
        compare(methods, desc.getAuthMethods(name));
	name = new QName("http://foo", "dummyMeth2");
        compare(methods, desc.getAuthMethods(name));
    }

    public void testContainerDesc() throws Exception {
	ContainerSecurityDescriptor desc = new ContainerSecurityDescriptor();
	Element elem = getDescriptor(CONTAINER_DESC_1);
	desc.parse(elem);
        verifyContainerDesc(desc);
    }
    
    private void verifyContainerDesc(ContainerSecurityDescriptor desc) {

	assertTrue(desc.getRejectLimitedProxyState() == null);
	assertTrue(desc.getContextLifetime() == null);
	assertTrue(desc.getReplayTimerInterval().equals("1000"));
	assertTrue(desc.getContextTimerInterval().equals("12000"));
    }

    public void testClientDesc() throws Exception {
	ClientSecurityDescriptor desc1 = new ClientSecurityDescriptor();
	Element elem = getDescriptor(CLIENT_DESC_1);
	desc1.parse(elem);
        verifyClientDesc1(desc1);

	ClientSecurityDescriptor desc2 = new ClientSecurityDescriptor();
	elem = getDescriptor(CLIENT_DESC_2);
	desc2.parse(elem);
        verifyClientDesc2(desc2);
        
    }

    private void verifyClientDesc1(ClientSecurityDescriptor desc) 
        throws Exception {

	assertTrue(desc.getAuthz() instanceof HostAuthorization);
	assertTrue(Constants.ENCRYPTION.equals(desc.getGSISecureMsg()));
	assertTrue(desc.getGSISecureConv() == null);
	assertTrue(desc.getAnonymous() == null);
	assertTrue("dummyPeer".equals(desc.getPeerCredentials()));
	assertTrue("proxyFile".equals(desc.getProxyFilename()));
	assertTrue("certFile".equals(desc.getCertFilename()));
	assertTrue("keyFile".equals(desc.getKeyFilename()));
    }

    private void verifyClientDesc2(ClientSecurityDescriptor desc1) 
        throws Exception {
	
	assertTrue(desc1.getAuthz() == null);
	assertTrue(Constants.ENCRYPTION.equals(desc1.getGSISecureMsg()));
	assertTrue(Constants.SIGNATURE.equals(desc1.getGSISecureConv()));
	assertTrue(Boolean.TRUE.equals(desc1.getAnonymous()));
	assertTrue(GSIConstants.GSI_MODE_FULL_DELEG
		   .equals(desc1.getDelegation()));
	assertTrue("dummyPeer".equals(desc1.getPeerCredentials()));
    }

    private void compare(List expected, List current) {
        assertEquals(expected.size(), current.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), current.get(i));
        }
    }

    private Subject getSubject() throws Exception {
        GlobusCredential credential = GlobusCredential.getDefaultCredential();
        Subject subject = new Subject();
        subject.getPrincipals().add(new GlobusPrincipal("random one"));
        subject.getPublicCredentials().add(credential.getCertificateChain());
        subject.getPublicCredentials().add(credential.getIdentityCertificate());
        subject.getPublicCredentials()
            .add(new EncryptionCredentials(credential.getCertificateChain()));
        subject.getPrivateCredentials()
            .add(new GlobusGSSCredentialImpl(credential, 1));
        subject.getPrivateCredentials().add(new PasswordCredential("newPass"));
        return subject;
    }
    
    private void verifySubject(Subject subject) throws Exception {
        assertTrue(subject != null);
        Set publicCreds = subject.getPublicCredentials();
        assertTrue(!publicCreds.isEmpty());
        assertTrue(publicCreds.size() == 3);
        Iterator iter = publicCreds.iterator();
        Object obj = iter.next();
        assertTrue(obj instanceof X509Certificate[]);
        obj = iter.next();
        assertTrue(obj instanceof X509Certificate);
        obj = iter.next();
        assertTrue(obj instanceof EncryptionCredentials);
        assertTrue(((EncryptionCredentials)obj).getCertificates() != null);
        Set privateCreds = subject.getPrivateCredentials();
        assertTrue(!privateCreds.isEmpty());
        assertTrue(privateCreds.size() == 2);
        iter = privateCreds.iterator();
        obj = iter.next();
        assertTrue(obj instanceof GlobusGSSCredentialImpl);
        obj = iter.next();
        assertTrue(obj instanceof PasswordCredential);
    }

    public void testPersistence() throws Exception {
        ServiceSecurityDescriptor desc1 = new ServiceSecurityDescriptor();
	Element elem = getDescriptor(SEC_DESC_1);
	desc1.parse(elem);

        ServiceSecurityDescriptor desc2 = new ServiceSecurityDescriptor();
	elem = getDescriptor(SERVICE_DESC_1);
	desc2.parse(elem);

	ContainerSecurityDescriptor contDesc = 
            new ContainerSecurityDescriptor();
	elem = getDescriptor(CONTAINER_DESC_1);
	contDesc.parse(elem);

        GridMap gridMap = new GridMap();
        gridMap.map("hello", "world");
        gridMap.map("some", "map");

        Subject subject = getSubject();

	ClientSecurityDescriptor clientDesc1 = new ClientSecurityDescriptor();
	elem = getDescriptor(CLIENT_DESC_1);
	clientDesc1.parse(elem);

	ClientSecurityDescriptor clientDesc2 = new ClientSecurityDescriptor();
	elem = getDescriptor(CLIENT_DESC_2);
	clientDesc2.parse(elem);

        ServiceSecurityDescriptor serviceDesc = new ServiceSecurityDescriptor();
        serviceDesc.setSubject(subject);

        // save it
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File tmpFile = new File("security-desc-test.tmp");
        try {
            fos = new FileOutputStream(tmpFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(desc1);
            oos.writeObject(desc2);
            oos.writeObject(contDesc);
            oos.writeObject(gridMap);
            AuthUtil.writeSubject(subject, oos);
            oos.writeObject(clientDesc1);
            oos.writeObject(clientDesc2);
            oos.writeObject(serviceDesc);
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (oos != null)
                    oos.close();
            } catch (Exception e) { }
        }
        FileInputStream fis = null;
        FixedObjectInputStream ois = null;
        ServiceSecurityDescriptor readInDesc1 = null;
        ServiceSecurityDescriptor readInDesc2 = null;
        ContainerSecurityDescriptor readInCont = null;
        GridMap readInGridMap = null;
        Subject readInSubject = null;
        ClientSecurityDescriptor readClient1 = null;
        ClientSecurityDescriptor readClient2 = null;
        ServiceSecurityDescriptor readInDesc3 = null;
        try {
            fis = new FileInputStream(tmpFile);
            ois = new FixedObjectInputStream(fis);
            readInDesc1 = (ServiceSecurityDescriptor)ois.readObject();
            readInDesc2 = (ServiceSecurityDescriptor)ois.readObject();
            readInCont = (ContainerSecurityDescriptor)ois.readObject();
            readInGridMap = (GridMap)ois.readObject();
            readInSubject = AuthUtil.readSubject(ois);
            readClient1 = (ClientSecurityDescriptor)ois.readObject();
            readClient2 = (ClientSecurityDescriptor)ois.readObject();
            readInDesc3 = (ServiceSecurityDescriptor)ois.readObject();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (ois != null)
                    ois.close();
                tmpFile.delete();
            } catch (Exception e) { }
        }
        verifyDescriptor1(readInDesc1);
        verifyDescriptor2(readInDesc2);
        verifyContainerDesc(readInCont);
        verifyClientDesc1(readClient1);
        verifySubject(readInSubject);
        verifyClientDesc2(readClient2);
        Subject retr = readInDesc3.getSubject();
        verifySubject(retr);
        tmpFile.delete();
    }
}

