package edu.internet2.middleware.grouper;

import gov.nih.nci.cagrid.common.FaultUtil;
import gov.nih.nci.cagrid.gridgrouper.bean.GroupDescriptor;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipRequestDescriptor;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipRequestStatus;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipRequestUpdate;
import gov.nih.nci.cagrid.gridgrouper.bean.StemDescriptor;
import gov.nih.nci.cagrid.gridgrouper.service.GridGrouper;
import gov.nih.nci.cagrid.gridgrouper.service.tools.GridGrouperBootstrapper;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.GridGrouperRuntimeFault;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.InsufficientPrivilegeFault;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.MemberAddFault;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.StemAddFault;
import gov.nih.nci.cagrid.gridgrouper.stubs.types.StemNotFoundFault;
import gov.nih.nci.cagrid.gridgrouper.testutils.Utils;

import java.util.Date;

import junit.framework.TestCase;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

public class TestMembershipRequestsEdgeCases extends TestCase {

	public static final String SUPER_USER = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=super admin";

	public GridGrouper grouper = null;

	public static final String GROUPER_ALL = "GrouperAll";

	private String USER_A = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=user a";

	private String USER_B = "/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=cagrid05/OU=IdP [1]/CN=user b";

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

		hs.delete("from MembershipRequestHistory");
		hs.delete("from MembershipRequest");

		tx.commit();
		hs.close();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		clearMembershipRequestsTable();
		RegistryReset.reset();
	}

	public void testAddMembershipRequestsTwice() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));


			grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));
			grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));
			
			MembershipRequestDescriptor[] members = grouper.getMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Pending);

			assertEquals("Only one membership request should be generated.", 1, members.length);
		} catch (MemberAddFault e) {
			// Expected fault
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testMembershipRetrieveal() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));

			grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));
			
			MembershipRequestDescriptor[] members = grouper.getMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Pending);
			
			MembershipRequestUpdate update = new MembershipRequestUpdate("A note", "A note",MembershipRequestStatus.Rejected);
			grouper.updateMembershipRequest(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A, update);

			members = grouper.getMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Rejected);

			assertEquals("Only one membership request should be generated.", 1, members.length);
						
		} catch (MemberAddFault e) {
			// Expected fault
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	
	public void testAddMembershipRequestsForExistingMember() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));


			grouper.addMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A);
			grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));

			MembershipRequestDescriptor[] members = grouper.getMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Pending);

			assertEquals("Shouldn't be able to request membership if already a member", 0, members.length);
		} catch (MemberAddFault e) {
			// Expected fault
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testAddMembershipRequestsForRejectedMember() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));

			grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));
			
			MembershipRequestUpdate update = new MembershipRequestUpdate(null, "A note", MembershipRequestStatus.Rejected);
			grouper.updateMembershipRequest(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A, update);
			
			try {
				grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));
				fail("Should not be able to add a membership request if a previous request has been rejected");
			} catch (MemberAddFault e) {
				// Expected fault				
			}

			MembershipRequestDescriptor[] members = grouper.getMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Rejected);

			assertEquals("Membership request should still be rejected", 1, members.length);
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testAddMembershipRequestsForRemovedMember() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));

			grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));
			
			MembershipRequestUpdate update = new MembershipRequestUpdate(null, "A note", MembershipRequestStatus.Approved);
			grouper.updateMembershipRequest(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A, update);
			
			grouper.deleteMember(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A);

			grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));

			MembershipRequestDescriptor[] members = grouper.getMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Pending);

			assertEquals("Should be able to request membership", 1, members.length);
			
			assertEquals("Should have membership request history", 4, members[0].getHistory().length);
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}

	public void testNotePrivieges() {
		try {
			GroupDescriptor grp = initialGroupAndRequestSetup();
			grouper.grantMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp));

			grouper.addMembershipRequest(USER_A, Utils.getGroupIdentifier(grp));
			
			MembershipRequestUpdate update = new MembershipRequestUpdate("An Approved Admin Note", "An Approved Public Note", MembershipRequestStatus.Approved);
			grouper.updateMembershipRequest(SUPER_USER, Utils.getGroupIdentifier(grp), USER_A, update);
			
			MembershipRequestDescriptor[] members = grouper.getMembershipRequests(SUPER_USER, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Approved);

			assertEquals("Should be able to request membership", 1, members.length);
			
			assertEquals("Should have membership request history", 2, members[0].getHistory().length);
			
			if (members[0].getHistory(0).getAdminNote() != null) {
				assertEquals("Admin should be able to see see the admin note", "An Approved Admin Note", members[0].getHistory(0).getAdminNote());
			} else {
				assertEquals("Admin should be able to see see the admin note", "An Approved Admin Note", members[0].getHistory(1).getAdminNote());				
			}
			
			members = grouper.getMembershipRequests(USER_A, Utils.getGroupIdentifier(grp),
					MembershipRequestStatus.Approved);

			assertEquals("Should have membership request history", 2, members[0].getHistory().length);
			assertNull("User should not see the admin not", members[0].getHistory(0).getAdminNote());
			assertNull("User should not see the admin not", members[0].getHistory(1).getAdminNote());
		} catch (Exception e) {
			FaultUtil.printFault(e);
			fail(e.getMessage());
		}

	}
	
	private GroupDescriptor initialGroupAndRequestSetup() throws GridGrouperRuntimeFault, StemNotFoundFault,
			InsufficientPrivilegeFault, StemAddFault, Exception {
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
		return grp;
	}


	protected GroupDescriptor createAndCheckGroup(StemDescriptor stem, String extension, String displayExtension,
			int childGroupCount) throws Exception {
		GroupDescriptor grp = grouper.addChildGroup(SUPER_USER, Utils.getStemIdentifier(stem), extension, displayExtension);
		return grp;
	}

}
