package gov.nih.nci.cagrid.gums.ifs;

import gov.nih.nci.cagrid.gums.common.Database;
import gov.nih.nci.cagrid.gums.common.FaultUtil;
import gov.nih.nci.cagrid.gums.common.ca.CertUtil;
import gov.nih.nci.cagrid.gums.ifs.bean.IFSUser;
import gov.nih.nci.cagrid.gums.ifs.bean.IFSUserFilter;
import gov.nih.nci.cagrid.gums.ifs.bean.IFSUserRole;
import gov.nih.nci.cagrid.gums.ifs.bean.IFSUserStatus;
import gov.nih.nci.cagrid.gums.test.TestUtils;

import java.io.StringReader;
import java.security.cert.X509Certificate;

import junit.framework.TestCase;

/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */
public class TestUserManager extends TestCase {

	private Database db;

	public void testSingleUser() {
		try {
			UserManager um = new UserManager(db, getOneYearConf(), TestUtils
					.getCA(db));

			// Test adding user

			IFSUser user = new IFSUser();
			user.setIdPId(1);
			user.setUID("user");
			user.setEmail("user@user.com");
			user = um.addUser(user);
			assertNotNull(user.getCertificate());
			assertNotNull(user.getGridId());
			assertNotNull(user.getUserRole());
			assertNotNull(user.getUserStatus());
			assertEquals(IFSUserRole.Non_Administrator, user.getUserRole());
			assertEquals(IFSUserStatus.Pending, user.getUserStatus());
			StringReader ureader = new StringReader(user.getCertificate());
			X509Certificate cert = CertUtil.loadCertificate(ureader);
			assertEquals(user.getGridId(), cert.getSubjectDN().getName());
			assertEquals(user, um.getUser(user.getIdPId(), user.getUID()));
			assertEquals(user, um.getUser(user.getGridId()));

			// Test Querying for users
			IFSUserFilter f1 = new IFSUserFilter();
			IFSUser[] l1 = um.getUsers(f1);
			assertEquals(1, l1.length);
			assertEquals(user, l1[0]);

			// Test querying by uid
			IFSUserFilter f2 = new IFSUserFilter();
			f2.setUID("nobody");
			IFSUser[] l2 = um.getUsers(f2);
			assertEquals(0, l2.length);
			f2.setUID("use");
			l2 = um.getUsers(f2);
			assertEquals(1, l2.length);
			assertEquals(user, l2[0]);

			// Test querying by IdP_Id
			IFSUserFilter f3 = new IFSUserFilter();
			f3.setIdPId(Long.MAX_VALUE);
			IFSUser[] l3 = um.getUsers(f3);
			assertEquals(0, l3.length);
			f3.setIdPId(user.getIdPId());
			l3 = um.getUsers(f3);
			assertEquals(1, l3.length);
			assertEquals(user, l3[0]);

			// Test querying by GID
			IFSUserFilter f4 = new IFSUserFilter();
			f4.setGridId("nobody");
			IFSUser[] l4 = um.getUsers(f4);
			assertEquals(0, l4.length);
			f4.setGridId(user.getGridId());
			l4 = um.getUsers(f4);
			assertEquals(1, l4.length);
			assertEquals(user, l4[0]);

			// Test querying by Email
			IFSUserFilter f5 = new IFSUserFilter();
			f5.setEmail("nobody");
			IFSUser[] l5 = um.getUsers(f5);
			assertEquals(0, l5.length);
			f5.setEmail(user.getEmail());
			l5 = um.getUsers(f5);
			assertEquals(1, l5.length);
			assertEquals(user, l5[0]);

			// Test querying by Role
			IFSUserFilter f6 = new IFSUserFilter();
			f6.setUserRole(IFSUserRole.Administrator);
			IFSUser[] l6 = um.getUsers(f6);
			assertEquals(0, l6.length);
			f6.setUserRole(IFSUserRole.Non_Administrator);
			l6 = um.getUsers(f6);
			assertEquals(1, l6.length);
			assertEquals(user, l6[0]);

			// Test querying by Status
			IFSUserFilter f7 = new IFSUserFilter();
			f7.setUserStatus(IFSUserStatus.Suspended);
			IFSUser[] l7 = um.getUsers(f7);
			assertEquals(0, l7.length);
			f7.setUserStatus(user.getUserStatus());
			l7 = um.getUsers(f7);
			assertEquals(1, l7.length);
			assertEquals(user, l7[0]);

			// Test All
			IFSUserFilter f8 = new IFSUserFilter();
			f8.setIdPId(user.getIdPId());
			f8.setUID(user.getUID());
			f8.setGridId(user.getGridId());
			f8.setEmail(user.getEmail());
			f8.setUserRole(user.getUserRole());
			f8.setUserStatus(user.getUserStatus());
			IFSUser[] l8 = um.getUsers(f8);
			assertEquals(1, l8.length);

			// Test Update
			IFSUser u1 = um.getUser(user.getGridId());
			u1.setEmail("newemail@example.com");
			um.updateUser(u1);
			assertEquals(u1, um.getUser(u1.getGridId()));

			IFSUser u2 = um.getUser(user.getGridId());
			u2.setUserRole(IFSUserRole.Administrator);
			um.updateUser(u2);
			assertEquals(u2, um.getUser(u2.getGridId()));

			IFSUser u3 = um.getUser(user.getGridId());
			u3.setUserStatus(IFSUserStatus.Active);
			um.updateUser(u3);
			assertEquals(u3, um.getUser(u3.getGridId()));

			IFSUser u4 = um.getUser(user.getGridId());
			u4.setUserRole(IFSUserRole.Non_Administrator);
			u4.setUserStatus(IFSUserStatus.Suspended);
			u4.setEmail("newemail2@example.com");
			um.updateUser(u4);
			assertEquals(u4, um.getUser(u4.getGridId()));

			IFSUser u5 = um.getUser(user.getGridId());
			u5.setGridId("changed grid id");
			um.updateUser(u5);
			assertEquals(u5, um.getUser(u5.getGridId()));

			// Now we test updating credentials
			um.renewUserCredentials(u5);
			assertEquals(u5, um.getUser(u5.getGridId()));
			StringReader r = new StringReader(u5.getCertificate());
			X509Certificate newCert = CertUtil.loadCertificate(r);
			if (cert.equals(newCert)) {
				assertTrue(false);
			}

			um.removeUser(u5);
			assertEquals(0, um.getUsers(new IFSUserFilter()).length);
		} catch (Exception e) {
			FaultUtil.printFault(e);
			assertTrue(false);
		}
	}

	public void testMultipleUsers() {
		try {
			UserManager um = new UserManager(db, getOneYearConf(), TestUtils
					.getCA(db));

			String prefix = "user";
			
			int userCount = 50;
			
			for (int i = 0; i < userCount; i++) {
				// Test adding user
				long idpId = (i%5)+1;
				long idpCount = (i / 5)+1;
			
				String uname = prefix+i;
				
				IFSUser user = new IFSUser();
				
				user.setIdPId(idpId);
				user.setUID(uname);
				user.setEmail(uname+"@user.com");
				user = um.addUser(user);
				assertNotNull(user.getCertificate());
				assertNotNull(user.getGridId());
				assertNotNull(user.getUserRole());
				assertNotNull(user.getUserStatus());
				assertEquals(IFSUserRole.Non_Administrator, user.getUserRole());
				assertEquals(IFSUserStatus.Pending, user.getUserStatus());
				StringReader ureader = new StringReader(user.getCertificate());
				X509Certificate cert = CertUtil.loadCertificate(ureader);
				assertEquals(user.getGridId(), cert.getSubjectDN().getName());
				assertEquals(user, um.getUser(user.getIdPId(), user.getUID()));
				assertEquals(user, um.getUser(user.getGridId()));

				// Test Querying for users
				IFSUserFilter f1 = new IFSUserFilter();
				IFSUser[] l1 = um.getUsers(f1);
				assertEquals((i+1), l1.length);

				// Test querying by uid
				IFSUserFilter f2 = new IFSUserFilter();
				f2.setUID("nobody");
				IFSUser[] l2 = um.getUsers(f2);
				assertEquals(0, l2.length);
				f2.setUID("use");
				l2 = um.getUsers(f2);
				assertEquals((i+1), l2.length);
				f2.setUID(uname);
				l2 = um.getUsers(f2);
				assertEquals(1, l2.length);
				assertEquals(user, l2[0]);

				// Test querying by IdP_Id
				IFSUserFilter f3 = new IFSUserFilter();
				f3.setIdPId(Long.MAX_VALUE);
				IFSUser[] l3 = um.getUsers(f3);
				assertEquals(0, l3.length);
				f3.setIdPId(user.getIdPId());
				l3 = um.getUsers(f3);
				assertEquals(idpCount, l3.length);
				

				// Test querying by GID
				IFSUserFilter f4 = new IFSUserFilter();
				f4.setGridId("nobody");
				IFSUser[] l4 = um.getUsers(f4);
				assertEquals(0, l4.length);
				
				String temp = user.getGridId();
				int index = temp.lastIndexOf(",");
				temp = temp.substring(0,index);
				f4.setGridId(temp);
				l4 = um.getUsers(f4);
				assertEquals(idpCount, l4.length);
				f4.setGridId(user.getGridId());
				l4 = um.getUsers(f4);
				assertEquals(1, l4.length);
				assertEquals(user, l4[0]);

				// Test querying by Email
				IFSUserFilter f5 = new IFSUserFilter();
				f5.setEmail("nobody");
				IFSUser[] l5 = um.getUsers(f5);
				assertEquals(0, l5.length);
				f5.setEmail(user.getEmail());
				l5 = um.getUsers(f5);
				assertEquals(1, l5.length);
				assertEquals(user, l5[0]);

				// Test querying by Role
				IFSUserFilter f6 = new IFSUserFilter();
				f6.setUserRole(IFSUserRole.Administrator);
				IFSUser[] l6 = um.getUsers(f6);
				assertEquals(0, l6.length);
				f6.setUserRole(IFSUserRole.Non_Administrator);
				l6 = um.getUsers(f6);
				assertEquals((i+1), l6.length);

				// Test querying by Status
				IFSUserFilter f7 = new IFSUserFilter();
				f7.setUserStatus(IFSUserStatus.Suspended);
				IFSUser[] l7 = um.getUsers(f7);
				assertEquals(i, l7.length);
				f7.setUserStatus(user.getUserStatus());
				l7 = um.getUsers(f7);
				assertEquals(1, l7.length);
				assertEquals(user, l7[0]);

				// Test All
				IFSUserFilter f8 = new IFSUserFilter();
				f8.setIdPId(user.getIdPId());
				f8.setUID(user.getUID());
				f8.setGridId(user.getGridId());
				f8.setEmail(user.getEmail());
				f8.setUserRole(user.getUserRole());
				f8.setUserStatus(user.getUserStatus());
				IFSUser[] l8 = um.getUsers(f8);
				assertEquals(1, l8.length);

				// Test Update
				IFSUser u1 = um.getUser(user.getGridId());
				u1.setEmail("newemail@example.com");
				um.updateUser(u1);
				assertEquals(u1, um.getUser(u1.getGridId()));

				IFSUser u2 = um.getUser(user.getGridId());
				u2.setUserRole(IFSUserRole.Administrator);
				um.updateUser(u2);
				assertEquals(u2, um.getUser(u2.getGridId()));

				IFSUser u3 = um.getUser(user.getGridId());
				u3.setUserStatus(IFSUserStatus.Active);
				um.updateUser(u3);
				assertEquals(u3, um.getUser(u3.getGridId()));

				IFSUser u4 = um.getUser(user.getGridId());
				u4.setUserRole(IFSUserRole.Non_Administrator);
				u4.setUserStatus(IFSUserStatus.Suspended);
				u4.setEmail("newemail2@example.com");
				um.updateUser(u4);
				assertEquals(u4, um.getUser(u4.getGridId()));

				IFSUser u5 = um.getUser(user.getGridId());
				u5.setGridId("changed grid id");
				um.updateUser(u5);
				assertEquals(u5, um.getUser(u5.getGridId()));

				// Now we test updating credentials
				um.renewUserCredentials(u5);
				assertEquals(u5, um.getUser(u5.getGridId()));
				StringReader r = new StringReader(u5.getCertificate());
				X509Certificate newCert = CertUtil.loadCertificate(r);
				if (cert.equals(newCert)) {
					assertTrue(false);
				}
               
			}
            
			// um.removeUser(u5);
			IFSUser[] list = um.getUsers(new IFSUserFilter());
			assertEquals(userCount,list.length);
			int count = userCount;
			for(int i=0; i<list.length; i++){
				count = count - 1;
				um.removeUser(list[i]);
				assertEquals(count,um.getUsers(new IFSUserFilter()).length);
			}
			assertEquals(0,um.getUsers(new IFSUserFilter()).length);
		} catch (Exception e) {
			FaultUtil.printFault(e);
			assertTrue(false);
		}
	}

	private IFSConfiguration getOneYearConf() {
		IFSConfiguration conf = new IFSConfiguration();
		conf.setCredentialsValidYears(1);
		conf.setCredentialsValidMonths(0);
		conf.setCredentialsValidDays(0);
		return conf;
	}

	protected void setUp() throws Exception {
		super.setUp();
		try {
			db = TestUtils.getDB();
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
