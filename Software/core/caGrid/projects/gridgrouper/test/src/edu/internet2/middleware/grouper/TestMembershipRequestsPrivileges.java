package edu.internet2.middleware.grouper;

import gov.nih.nci.cagrid.common.FaultUtil;
import gov.nih.nci.cagrid.gridgrouper.bean.GroupDescriptor;
import gov.nih.nci.cagrid.gridgrouper.bean.GroupPrivilegeType;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipRequestDescriptor;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipRequestStatus;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipRequestUpdate;
import gov.nih.nci.cagrid.gridgrouper.bean.StemDescriptor;
import gov.nih.nci.cagrid.gridgrouper.service.GridGrouper;
import gov.nih.nci.cagrid.gridgrouper.service.tools.GridGrouperBootstrapper;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.GroupNotFoundFault;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.MemberAddFault;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.StemAddFault;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;
import gov.nih.nci.cagrid.gridgrouper.testutils.Utils;
import junit.framework.TestCase;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

public class TestMembershipRequestsPrivileges extends TestCase {

	public static final String SUPER_USER = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=super admin";

	public GridGrouper grouper = null;

	public static final String GROUPER_ALL = "GrouperAll";

	private String USER_A = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=user a";

	private String USER_Aadmin = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=user aadmin";

	private String USER_B = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=user b";

	private String USER_Badmin = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=user badmin";

	private String USER_C = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=user c";

	private String USER_D = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=user d";

	protected void setUp() throws Exception {
		super.setUp();
		// Need to clear the MembershipRequests table prior to calling
		// RegistryReset.reset as RegistryReset.reset is not aware of the MembershipRequests
		clearMembershipRequestsTable();
		RegistryReset.reset();
		this.grouper = new GridGrouper();
	}

	private void clearMembershipRequestsTable() throws HibernateException {
		Session hs = GridGrouperHibernateHelper.getSession();
		Transaction tx = hs.beginTransaction();

		hs.delete("from MembershipRequests");

		tx.commit();
		hs.close();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		clearMembershipRequestsTable();
		RegistryReset.reset();
	}

	public void testWheelGetPrivileges() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();

			MembershipRequestDescriptor[] members = grouper.getMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Pending);

			assertEquals("Do not retrieve the expected pending membership requests", 4, members.length);
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testAdminGetPrivileges() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();

			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_Aadmin);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_Badmin);
			grouper.grantGroupPrivilege(SUPER_USER, Utils.getGroupIdentifier(grp), USER_Aadmin, GroupPrivilegeType.admin);

			MembershipRequestDescriptor[] members = grouper.getMembershipRequests(USER_Aadmin, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Pending);
			assertEquals("Did not retrieve the expected pending membership requests", 4, members.length);

			members = grouper.getMembershipRequests(USER_Badmin, Utils.getGroupIdentifier(grp), MembershipRequestStatus.Pending);
			assertEquals("USER_Badmin should not be able to retrieve any requests", 0, members.length);
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testAdminUpdatePrivileges() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();

			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_Aadmin);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_Badmin);
			grouper.grantGroupPrivilege(SUPER_USER, Utils.getGroupIdentifier(grp), USER_Aadmin, GroupPrivilegeType.admin);
			

			MembershipRequestUpdate update = new MembershipRequestUpdate("", MembershipRequestStatus.Approved);
			try {
				grouper.updateMembershipRequest(USER_Aadmin, Utils.getGroupIdentifier(grp), USER_A, update);
			} catch (InsufficientPrivilegeFault e) {
				fail("Should be able to approve membership to group with admin privileges");
			}

			try {
				grouper.updateMembershipRequest(USER_Badmin, Utils.getGroupIdentifier(grp), USER_B, update);
				fail("Should not be able to approve membership to group without admin privileges");
			} catch (InsufficientPrivilegeFault e) {
				// Expected Fault
			}

		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testUserGetPrivileges() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();

			MembershipRequestDescriptor[] members = grouper.getMembershipRequests(USER_A, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Pending);

			assertEquals("Did not retrieve the expected pending membership requests", 1, members.length);
			assertEquals("User A retrieved a request different than his own.", USER_A, members[0].getRequestorId());

			members = grouper.getMembershipRequests(USER_Aadmin, Utils.getGroupIdentifier(grp), MembershipRequestStatus.Pending);

			assertEquals("Did not retrieve the expected pending membership requests", 0, members.length);

		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testUserUpdatePrivileges() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();

			MembershipRequestUpdate update = new MembershipRequestUpdate("", MembershipRequestStatus.Approved);
			grouper.updateMembershipRequest(USER_A, Utils.getGroupIdentifier(grp), USER_A, update);

			fail("Should not be able to self approve membership");
		} catch (InsufficientPrivilegeFault e) {
			// Expected Fault
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}
	
	public void testUserGrantMembershipRequests() {
		try {
			GridGrouperBootstrapper.addAdminMember(SUPER_USER);
			grouper.getStem(SUPER_USER, Utils.getRootStemIdentifier());

			String testStem = "TestStem";
			StemDescriptor test = grouper.addChildStem(SUPER_USER, Utils.getRootStemIdentifier(), testStem, testStem);
			final String groupExtension = "mygroup";
			final String groupDisplayExtension = "My Group";

			GroupDescriptor grp = createAndCheckGroup(test, groupExtension, groupDisplayExtension, 1);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A);
			grouper.grantMembershipRequests(USER_A, Utils.getGroupIdentifier(grp));

			fail("Should not be able to grant membership requests");
		} catch (InsufficientPrivilegeFault e) {
			// Expected Fault
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testUserRevokeMembershipRequests() {
		try {
			GridGrouperBootstrapper.addAdminMember(SUPER_USER);
			grouper.getStem(SUPER_USER, Utils.getRootStemIdentifier());

			String testStem = "TestStem";
			StemDescriptor test = grouper.addChildStem(SUPER_USER, Utils.getRootStemIdentifier(), testStem, testStem);
			final String groupExtension = "mygroup";
			final String groupDisplayExtension = "My Group";

			GroupDescriptor grp = createAndCheckGroup(test, groupExtension, groupDisplayExtension, 1);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A);
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));
			grouper.revokeMembershipRequests(USER_A, Utils.getGroupIdentifier(grp));
			fail("Should not be able to revoke membership requests");
		} catch (InsufficientPrivilegeFault e) {
			// Expected Fault
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testAdminGrantMembershipRequests() {
		try {
			GridGrouperBootstrapper.addAdminMember(SUPER_USER);
			grouper.getStem(SUPER_USER, Utils.getRootStemIdentifier());

			String testStem = "TestStem";
			StemDescriptor test = grouper.addChildStem(SUPER_USER, Utils.getRootStemIdentifier(), testStem, testStem);
			String groupExtension = "mygroup";
			String groupDisplayExtension = "My Group";

			GroupDescriptor grp = createAndCheckGroup(test, groupExtension, groupDisplayExtension, 1);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A);
			grouper.grantGroupPrivilege(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A, GroupPrivilegeType.admin);
			grouper.grantMembershipRequests(USER_A, Utils.getGroupIdentifier(grp));

			groupExtension = "mygroup2";
			groupDisplayExtension = "My Group 2";

			GroupDescriptor grp2 = createAndCheckGroup(test, groupExtension, groupDisplayExtension, 1);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp2), USER_B);
			grouper.grantGroupPrivilege(SUPER_USER, Utils.getGroupIdentifier(grp2), USER_B, GroupPrivilegeType.admin);
			try {
				grouper.grantMembershipRequests(USER_A, Utils.getGroupIdentifier(grp2));
				fail("Should not be able to grant membership requests");
			} catch (InsufficientPrivilegeFault e) {
				// Expected Fault
			}
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testAdminRevokeMembershipRequests() {
		try {
			GridGrouperBootstrapper.addAdminMember(SUPER_USER);
			grouper.getStem(SUPER_USER, Utils.getRootStemIdentifier());

			String testStem = "TestStem";
			StemDescriptor test = grouper.addChildStem(SUPER_USER, Utils.getRootStemIdentifier(), testStem, testStem);
			String groupExtension = "mygroup";
			String groupDisplayExtension = "My Group";

			GroupDescriptor grp = createAndCheckGroup(test, groupExtension, groupDisplayExtension, 1);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A);
			grouper.grantGroupPrivilege(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A, GroupPrivilegeType.admin);
			grouper.grantMembershipRequests(USER_A, Utils.getGroupIdentifier(grp));
			grouper.revokeMembershipRequests(USER_A, Utils.getGroupIdentifier(grp));

			groupExtension = "mygroup2";
			groupDisplayExtension = "My Group 2";

			GroupDescriptor grp2 = createAndCheckGroup(test, groupExtension, groupDisplayExtension, 1);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp2), USER_B);
			grouper.grantGroupPrivilege(SUPER_USER, Utils.getGroupIdentifier(grp2), USER_B, GroupPrivilegeType.admin);
			grouper.grantMembershipRequests(USER_B, Utils.getGroupIdentifier(grp2));
			try {
				grouper.revokeMembershipRequests(USER_A, Utils.getGroupIdentifier(grp2));
				fail("Should not be able to revoke membership requests");
			} catch (InsufficientPrivilegeFault e) {
				// Expected Fault
			}
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testWheelGrantMembershipRequests() {
		try {
			GridGrouperBootstrapper.addAdminMember(SUPER_USER);
			grouper.getStem(SUPER_USER, Utils.getRootStemIdentifier());

			String testStem = "TestStem";
			StemDescriptor test = grouper.addChildStem(SUPER_USER, Utils.getRootStemIdentifier(), testStem, testStem);
			String groupExtension = "mygroup";
			String groupDisplayExtension = "My Group";

			GroupDescriptor grp = createAndCheckGroup(test, groupExtension, groupDisplayExtension, 1);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A);
			grouper.grantGroupPrivilege(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A, GroupPrivilegeType.admin);
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));

		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testWheelRevokeMembershipRequests() {
		try {
			GridGrouperBootstrapper.addAdminMember(SUPER_USER);
			grouper.getStem(SUPER_USER, Utils.getRootStemIdentifier());

			String testStem = "TestStem";
			StemDescriptor test = grouper.addChildStem(SUPER_USER, Utils.getRootStemIdentifier(), testStem, testStem);
			String groupExtension = "mygroup";
			String groupDisplayExtension = "My Group";

			GroupDescriptor grp = createAndCheckGroup(test, groupExtension, groupDisplayExtension, 1);
			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A);
			grouper.grantGroupPrivilege(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A, GroupPrivilegeType.admin);
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));
			grouper.revokeMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));

		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	private GroupDescriptor initialGroupAndRequestSetup() throws GridGrouperRuntimeFault, StemNotFoundFault,
			InsufficientPrivilegeFault, StemAddFault, Exception, GroupNotFoundFault, MemberAddFault {
		GridGrouperBootstrapper.addAdminMember(SUPER_USER);
		grouper.getStem(SUPER_USER, Utils.getRootStemIdentifier());

		String testStem = "TestStem";
		StemDescriptor test = grouper.addChildStem(SUPER_USER, Utils.getRootStemIdentifier(), testStem, testStem);
		final String groupExtension = "mygroup";
		final String groupDisplayExtension = "My Group";

		GroupDescriptor grp = createAndCheckGroup(test, groupExtension, groupDisplayExtension, 1);

		final String subGroupExtension = "mysubgroup";
		final String subGroupDisplayExtension = "My Sub Group";

		createAndCheckGroup(test, subGroupExtension, subGroupDisplayExtension, 2);

		grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));

		grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));
		grouper.addMembershipRequest(USER_B, Utils.getGroupIdentifier(grp));
		grouper.addMembershipRequest(USER_C, Utils.getGroupIdentifier(grp));
		grouper.addMembershipRequest(USER_D, Utils.getGroupIdentifier(grp));
		return grp;
	}

	protected GroupDescriptor createAndCheckGroup(StemDescriptor stem, String extension, String displayExtension,
			int childGroupCount) throws Exception {
		GroupDescriptor grp = grouper.addChildGroup(SUPER_USER, Utils.getStemIdentifier(stem), extension, displayExtension);
		return grp;
	}

}
