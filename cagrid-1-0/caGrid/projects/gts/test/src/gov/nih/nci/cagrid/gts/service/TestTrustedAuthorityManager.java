package gov.nih.nci.cagrid.gts.service;

import gov.nih.nci.cagrid.common.FaultUtil;
import gov.nih.nci.cagrid.gridca.common.CRLEntry;
import gov.nih.nci.cagrid.gridca.common.CertUtil;
import gov.nih.nci.cagrid.gts.bean.Status;
import gov.nih.nci.cagrid.gts.bean.TrustLevel;
import gov.nih.nci.cagrid.gts.bean.TrustedAuthority;
import gov.nih.nci.cagrid.gts.bean.TrustedAuthorityFilter;
import gov.nih.nci.cagrid.gts.bean.X509CRL;
import gov.nih.nci.cagrid.gts.bean.X509Certificate;
import gov.nih.nci.cagrid.gts.common.Database;
import gov.nih.nci.cagrid.gts.stubs.IllegalTrustedAuthorityFault;
import gov.nih.nci.cagrid.gts.stubs.InvalidTrustedAuthorityFault;
import gov.nih.nci.cagrid.gts.test.CA;
import gov.nih.nci.cagrid.gts.test.Utils;

import java.math.BigInteger;

import junit.framework.TestCase;

import org.bouncycastle.asn1.x509.CRLReason;


/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */
public class TestTrustedAuthorityManager extends TestCase {

	private Database db;


	public void testCreateAndDestroy() {
		try {
			TrustedAuthorityManager trust = new TrustedAuthorityManager("localhost", db);
			trust.buildDatabase();
			trust.destroy();
		} catch (Exception e) {
			FaultUtil.printFault(e);
			assertTrue(false);
		}
	}


	public void testAddTrustedAuthorityWithCRL() {
		try {
			TrustedAuthorityManager trust = new TrustedAuthorityManager("localhost", db);
			CA ca = new CA();
			BigInteger sn = new BigInteger(String.valueOf(System.currentTimeMillis()));
			CRLEntry entry = new CRLEntry(sn, CRLReason.PRIVILEGE_WITHDRAWN);
			ca.updateCRL(entry);
			TrustedAuthority ta = new TrustedAuthority();
			ta.setTrustedAuthorityName(ca.getCertificate().getSubjectDN().toString());
			ta.setCertificate(new X509Certificate(CertUtil.writeCertificate(ca.getCertificate())));
			ta.setCRL(new X509CRL(CertUtil.writeCRL(ca.getCRL())));
			ta.setStatus(Status.Trusted);
			ta.setTrustLevel(TrustLevel.Five);
			trust.addTrustedAuthority(ta);
			assertEquals(ta, trust.getTrustedAuthority(ta.getTrustedAuthorityName()));
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}
	}


	public void testAddTrustedAuthorityWithInvalidCRL() {
		try {
			TrustedAuthorityManager trust = new TrustedAuthorityManager("localhost", db);
			CA ca = new CA();
			CA ca2 = new CA();
			BigInteger sn = new BigInteger(String.valueOf(System.currentTimeMillis()));
			CRLEntry entry = new CRLEntry(sn, CRLReason.PRIVILEGE_WITHDRAWN);
			ca2.updateCRL(entry);
			try {
				TrustedAuthority ta = new TrustedAuthority();
				ta.setTrustedAuthorityName(ca.getCertificate().getSubjectDN().toString());
				ta.setCertificate(new X509Certificate(CertUtil.writeCertificate(ca.getCertificate())));
				ta.setCRL(new X509CRL(CertUtil.writeCRL(ca2.getCRL())));
				ta.setStatus(Status.Trusted);
				ta.setTrustLevel(TrustLevel.Five);
				trust.addTrustedAuthority(ta);
				fail("Did not generate error when an invalidly signed CRL was provided.");
			} catch (IllegalTrustedAuthorityFault f) {

			}
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}
	}


	public void testAddInvalidTrustedAuthority() {
		try {
			TrustedAuthorityManager trust = new TrustedAuthorityManager("localhost", db);
			CA ca = new CA();

			// No Certificate
			try {
				TrustedAuthority ta = new TrustedAuthority();
				ta.setTrustedAuthorityName(ca.getCertificate().getSubjectDN().toString());
				ta.setStatus(Status.Trusted);
				ta.setTrustLevel(TrustLevel.Five);
				trust.addTrustedAuthority(ta);
				fail("Did not generate error when an invalidl Trusted Authority was provided.");
			} catch (IllegalTrustedAuthorityFault f) {

			}
			// No Status
			try {
				TrustedAuthority ta = new TrustedAuthority();
				ta.setTrustedAuthorityName(ca.getCertificate().getSubjectDN().toString());
				ta.setCertificate(new X509Certificate(CertUtil.writeCertificate(ca.getCertificate())));
				ta.setTrustLevel(TrustLevel.Five);
				trust.addTrustedAuthority(ta);
				fail("Did not generate error when an invalidl Trusted Authority was provided.");
			} catch (IllegalTrustedAuthorityFault f) {

			}

			// No Trust Level
			try {
				TrustedAuthority ta = new TrustedAuthority();
				ta.setTrustedAuthorityName(ca.getCertificate().getSubjectDN().toString());
				ta.setCertificate(new X509Certificate(CertUtil.writeCertificate(ca.getCertificate())));
				ta.setStatus(Status.Trusted);
				trust.addTrustedAuthority(ta);
				fail("Did not generate error when an invalidl Trusted Authority was provided.");
			} catch (IllegalTrustedAuthorityFault f) {

			}
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}
	}


	public void testAddTrustedAuthorityNoCRL() {
		try {
			TrustedAuthorityManager trust = new TrustedAuthorityManager("localhost", db);
			CA ca = new CA();
			TrustedAuthority ta = new TrustedAuthority();
			ta.setTrustedAuthorityName(ca.getCertificate().getSubjectDN().toString());
			ta.setCertificate(new X509Certificate(CertUtil.writeCertificate(ca.getCertificate())));
			ta.setStatus(Status.Trusted);
			ta.setTrustLevel(TrustLevel.Five);
			trust.addTrustedAuthority(ta);
			assertEquals(ta, trust.getTrustedAuthority(ta.getTrustedAuthorityName()));
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}
	}


	public void testRemoveTrustedAuthority() {
		try {
			TrustedAuthorityManager trust = new TrustedAuthorityManager("localhost", db);
			CA ca = new CA();
			BigInteger sn = new BigInteger(String.valueOf(System.currentTimeMillis()));
			CRLEntry entry = new CRLEntry(sn, CRLReason.PRIVILEGE_WITHDRAWN);
			ca.updateCRL(entry);
			TrustedAuthority ta = new TrustedAuthority();
			ta.setTrustedAuthorityName(ca.getCertificate().getSubjectDN().toString());
			ta.setCertificate(new X509Certificate(CertUtil.writeCertificate(ca.getCertificate())));
			ta.setCRL(new X509CRL(CertUtil.writeCRL(ca.getCRL())));
			ta.setStatus(Status.Trusted);
			ta.setTrustLevel(TrustLevel.Five);
			trust.addTrustedAuthority(ta);
			assertEquals(ta, trust.getTrustedAuthority(ta.getTrustedAuthorityName()));
			trust.removeTrustedAuthority(ta.getTrustedAuthorityName());
			try {
				trust.getTrustedAuthority(ta.getTrustedAuthorityName());
				fail("Trusted Authority still exists when it should have been removed");
			} catch (InvalidTrustedAuthorityFault f) {

			}

			try {
				trust.removeTrustedAuthority(ta.getTrustedAuthorityName());
				fail("Trusted Authority still exists when it should have been removed");
			} catch (InvalidTrustedAuthorityFault f) {

			}
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}
	}


	public void testFindTrustedAuthorities() {
		try {
			TrustedAuthorityManager trust = new TrustedAuthorityManager("localhost", db);
			int count = 5;
			String dnPrefix = "O=Organization ABC,OU=Unit XYZ,CN=Certificate Authority";
			TrustedAuthority[] auths = new TrustedAuthority[count];
			for (int i = 0; i < count; i++) {
				String dn = dnPrefix + i;
				CA ca = new CA(dn);
				String name = ca.getCertificate().getSubjectDN().toString();
				
				BigInteger sn = new BigInteger(String.valueOf(System.currentTimeMillis()));
				CRLEntry entry = new CRLEntry(sn, CRLReason.PRIVILEGE_WITHDRAWN);
				ca.updateCRL(entry);
				auths[i] = new TrustedAuthority();
				auths[i].setTrustedAuthorityName(name);
				auths[i].setCertificate(new X509Certificate(CertUtil.writeCertificate(ca.getCertificate())));
				auths[i].setCRL(new X509CRL(CertUtil.writeCRL(ca.getCRL())));
				auths[i].setStatus(Status.Trusted);
				auths[i].setTrustLevel(TrustLevel.Five);
				trust.addTrustedAuthority(auths[i]);
				assertEquals(auths[i], trust.getTrustedAuthority(auths[i].getTrustedAuthorityName()));
				TrustedAuthority[] tas = trust.findTrustAuthorities(new TrustedAuthorityFilter());
				assertEquals(tas.length, (i + 1));

			

				// Filter by name
				TrustedAuthorityFilter tf2 = new TrustedAuthorityFilter();
				tf2.setTrustedAuthorityName(name);
				TrustedAuthority[] tas2 = trust.findTrustAuthorities(tf2);
				assertEquals(1, tas2.length);
				assertEquals(auths[i], tas2[0]);
				tf2.setTrustedAuthorityName("yada yada");
				tas2 = trust.findTrustAuthorities(tf2);
				assertEquals(0, tas2.length);
				tf2.setTrustedAuthorityName(dnPrefix);
				tas2 = trust.findTrustAuthorities(tf2);
				assertEquals((i + 1), tas2.length);

				// Filter by DN
				TrustedAuthorityFilter tf3 = new TrustedAuthorityFilter();
				tf3.setCertificateDN(dn);
				TrustedAuthority[] tas3 = trust.findTrustAuthorities(tf3);
				assertEquals(1, tas3.length);
				assertEquals(auths[i], tas3[0]);
				tf3.setCertificateDN("yada yada");
				tas3 = trust.findTrustAuthorities(tf3);
				assertEquals(0, tas3.length);
				tf3.setCertificateDN(dnPrefix);
				tas3 = trust.findTrustAuthorities(tf3);
				assertEquals((i + 1), tas3.length);

				// Filter by Trust Level
				TrustedAuthorityFilter tf4 = new TrustedAuthorityFilter();
				tf4.setTrustLevel(TrustLevel.Five);
				TrustedAuthority[] tas4 = trust.findTrustAuthorities(tf4);
				assertEquals((i + 1), tas4.length);
				tf4.setTrustLevel(TrustLevel.Four);
				tas4 = trust.findTrustAuthorities(tf4);
				assertEquals(0, tas4.length);

				// Filter by Status
				TrustedAuthorityFilter tf5 = new TrustedAuthorityFilter();
				tf5.setStatus(Status.Trusted);
				TrustedAuthority[] tas5 = trust.findTrustAuthorities(tf5);
				assertEquals((i + 1), tas5.length);
				tf5.setStatus(Status.Suspended);
				tas5 = trust.findTrustAuthorities(tf5);
				assertEquals(0, tas5.length);
				
				//Filter by IsAuthority and Authority
				TrustedAuthorityFilter tf6 = new TrustedAuthorityFilter();
				tf6.setIsAuthority(Boolean.TRUE);
				tf6.setAuthorityTrustService("localhost");
				TrustedAuthority[] tas6 = trust.findTrustAuthorities(tf6);
				assertEquals((i + 1), tas6.length);
				tf6.setIsAuthority(Boolean.FALSE);
				tas6 = trust.findTrustAuthorities(tf6);
				assertEquals(0, tas6.length);
				tf6.setIsAuthority(Boolean.TRUE);
				tf6.setAuthorityTrustService("yada yada");
				tas6 = trust.findTrustAuthorities(tf6);
				assertEquals(0, tas6.length);
				
				// Filter by ALL
				TrustedAuthorityFilter tf7 = new TrustedAuthorityFilter();
				tf7.setTrustedAuthorityName(name);
				TrustedAuthority[] tas7 = trust.findTrustAuthorities(tf7);
				assertEquals(1, tas7.length);
				assertEquals(auths[i], tas7[0]);
				tf7.setCertificateDN(dn);
				tas7 = trust.findTrustAuthorities(tf7);
				assertEquals(1, tas7.length);
				assertEquals(auths[i], tas7[0]);
				tf7.setTrustLevel(TrustLevel.Five);
				tas7 = trust.findTrustAuthorities(tf7);
				assertEquals(1, tas7.length);
				assertEquals(auths[i], tas7[0]);
				tf7.setStatus(Status.Trusted);
				tas7 = trust.findTrustAuthorities(tf7);
				assertEquals(1, tas7.length);
				assertEquals(auths[i], tas7[0]);
				tf7.setIsAuthority(Boolean.TRUE);
				tas7 = trust.findTrustAuthorities(tf7);
				assertEquals(1, tas7.length);
				assertEquals(auths[i], tas7[0]);
				tf7.setAuthorityTrustService("localhost");
				tas7 = trust.findTrustAuthorities(tf7);
				assertEquals(1, tas7.length);
				assertEquals(auths[i], tas7[0]);
			}
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}


	protected void setUp() throws Exception {
		super.setUp();
		try {
			db = Utils.getDB();
			assertEquals(0, db.getUsedConnectionCount());
		} catch (Exception e) {
			FaultUtil.printFault(e);
			assertTrue(false);
		}
	}


	protected void tearDown() throws Exception {
		super.setUp();
		try {
			assertEquals(0, db.getUsedConnectionCount());
			db.destroyDatabase();
		} catch (Exception e) {
			FaultUtil.printFault(e);
			assertTrue(false);
		}
	}

}
