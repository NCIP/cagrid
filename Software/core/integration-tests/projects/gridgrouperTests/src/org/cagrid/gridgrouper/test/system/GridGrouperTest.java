package org.cagrid.gridgrouper.test.system;

import gov.nih.nci.cagrid.common.security.ProxyUtil;
import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainer;
import gov.nih.nci.cagrid.testing.system.deployment.steps.CopyServiceStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.DeleteServiceStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.DeployServiceStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.DestroyContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.StartContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.StopContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.UnpackContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.story.ServiceStoryBase;
import gov.nih.nci.cagrid.testing.system.haste.Step;
import gov.nih.nci.cagrid.testing.system.utils.steps.ModifyConfigurationStep;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.naming.ldap.LdapName;

import org.bouncycastle.asn1.x509.X509Name;
import org.cagrid.gaards.authentication.BasicAuthentication;
import org.cagrid.gaards.authentication.test.system.steps.AuthenticationStep;
import org.cagrid.gaards.authentication.test.system.steps.SuccessfullAuthentication;
import org.cagrid.gaards.dorian.idp.Application;
import org.cagrid.gaards.dorian.idp.CountryCode;
import org.cagrid.gaards.dorian.idp.LocalUserStatus;
import org.cagrid.gaards.dorian.idp.StateCode;
import org.cagrid.gaards.dorian.service.BeanUtils;
import org.cagrid.gaards.dorian.service.DorianProperties;
import org.cagrid.gaards.dorian.test.system.steps.CleanupDorianStep;
import org.cagrid.gaards.dorian.test.system.steps.ConfigureGlobusToTrustDorianStep;
import org.cagrid.gaards.dorian.test.system.steps.GetAsserionSigningCertificateStep;
import org.cagrid.gaards.dorian.test.system.steps.GridCredentialRequestStep;
import org.cagrid.gaards.dorian.test.system.steps.RegisterUserWithDorianIdentityProviderStep;
import org.cagrid.gaards.dorian.test.system.steps.SuccessfullGridCredentialRequest;
import org.cagrid.gaards.dorian.test.system.steps.UpdateLocalUserStatusStep;
import org.cagrid.gridgrouper.test.system.steps.DorianAuthenticateStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperAddAdminStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperAddMemberStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperAddMembershipRequestStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperCheckGroupsStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperCheckMembersStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperCheckPrivilegesStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperCheckStemsStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperCreateDbStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperCreateGroupStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperCreateStemStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperDropDbStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperGrantMembershipRequestsStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperGrantPrivilegeStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperInitStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperRemoveMemberStep;
import org.cagrid.gridgrouper.test.system.steps.GrouperUpdateMembershipRequestStep;
import org.globus.gsi.bc.X509NameHelper;
import org.springframework.core.io.FileSystemResource;

public class GridGrouperTest extends ServiceStoryBase {

	private static final String PATH_TO_DORIAN_PROJECT = "../../../caGrid/projects/dorian";
	private static final String PATH_TO_GRIDGROUPER_PROJECT = "../../../caGrid/projects/gridgrouper";

	private File properties;
	private File tempDorianService;
	private File tempGridGrouperService;

	private ConfigureGlobusToTrustDorianStep trust;

	public GridGrouperTest() {
		super();
	}

	public GridGrouperTest(ServiceContainer container) {
		this(container, null);
	}

	public GridGrouperTest(ServiceContainer container, File properties) {
		super(container);
		this.properties = properties;
	}

	@Override
	public String getDescription() {
		return "Grid Grouper System Test";
	}

	@Override
	protected Vector<?> steps() {
		Vector<Step> steps = new Vector<Step>();
		try {
			steps.add(new UnpackContainerStep(getContainer()));
			File originalPropertyFile = new File(tempDorianService
					.getAbsolutePath()
					+ File.separator
					+ "etc"
					+ File.separator
					+ "dorian.properties");
            steps.add(new ModifyConfigurationStep(originalPropertyFile, properties));

			steps.add(new DeployServiceStep(getContainer(), this.tempDorianService.getAbsolutePath()));
			steps.add(new DeployServiceStep(getContainer(), this.tempGridGrouperService.getAbsolutePath()));

			trust = new ConfigureGlobusToTrustDorianStep(getContainer());
			steps.add(trust);

			steps.add(new GrouperCreateDbStep("."));
			steps.add(new GrouperInitStep(new File(PATH_TO_GRIDGROUPER_PROJECT)));
			
			String idp = getDorianSubject();

			steps.add(new GrouperAddAdminStep(new File(PATH_TO_GRIDGROUPER_PROJECT),
					idp + "jdoe0"));

			steps.add(new StartContainerStep(getContainer()));

			String gridGrouperServiceURL = getContainer().getServiceEPR("cagrid/GridGrouper").getAddress().toString();
			String dorianServiceURL = getContainer().getServiceEPR("cagrid/Dorian").getAddress().toString();

			GetAsserionSigningCertificateStep signingCertStep = new GetAsserionSigningCertificateStep(getContainer());
			steps.add(signingCertStep);

			SuccessfullAuthentication success = new SuccessfullAuthentication("dorian", "Mr.", "Administrator",
					"dorian@dorian.org", signingCertStep);

			// Test Successful authentication
			BasicAuthentication cred = new BasicAuthentication();
			cred.setUserId("dorian");
			cred.setPassword("DorianAdmin$1");
			AuthenticationStep adminAuth = new AuthenticationStep(dorianServiceURL, success, cred);
			steps.add(adminAuth);

			// Get Admin's Grid Credentials

			GridCredentialRequestStep admin = new GridCredentialRequestStep(dorianServiceURL, adminAuth,
					new SuccessfullGridCredentialRequest());
			steps.add(admin);

			// Create Users
			List<Application> users = new ArrayList<Application>();
			for (int i = 0; i < 4; i++) {
				Application a = new Application();
				a.setUserId("jdoe" + i);
				a.setPassword("K00lM0N$$" + i);
				a.setFirstName("John" + i);
				a.setLastName("Doe" + i);
				a.setEmail(a.getUserId() + "@cagrid.org");
				a.setOrganization("cagrid.org");
				a.setAddress("123" + i + " Grid Way");
				a.setCity("Columbus");
				a.setState(StateCode.OH);
				a.setCountry(CountryCode.US);
				a.setZipcode("43210");
				a.setPhoneNumber("(555) 555-555" + i);
				users.add(a);
				steps.add(new RegisterUserWithDorianIdentityProviderStep(dorianServiceURL, a));

				steps.add(new UpdateLocalUserStatusStep(dorianServiceURL, admin, users.get(i).getUserId(), LocalUserStatus.Active));

			}

			steps.add(new DorianAuthenticateStep(users.get(0).getUserId(), users.get(0).getPassword(), dorianServiceURL));

			// add stems and groups
			steps.add(new GrouperCreateStemStep("test:stem1", gridGrouperServiceURL));
			steps.add(new GrouperCreateStemStep("test:stem2:stem3", gridGrouperServiceURL));
			steps.add(new GrouperCreateStemStep("test:stem2:stem4", gridGrouperServiceURL));
			steps.add(new GrouperCreateGroupStep("test:stem1:group1", gridGrouperServiceURL));
			steps.add(new GrouperCreateGroupStep("test:stem2:stem3:group2", gridGrouperServiceURL));
			steps.add(new GrouperCreateGroupStep("test:stem2:stem3:group3", gridGrouperServiceURL));

			// add members
			steps.add(new GrouperAddMemberStep("test:stem1:group1", idp + users.get(1).getUserId(), gridGrouperServiceURL));
			steps.add(new GrouperAddMemberStep("test:stem1:group1", idp + users.get(2).getUserId(), gridGrouperServiceURL));
			steps.add(new GrouperAddMemberStep("test:stem2:stem3:group2", idp + users.get(1).getUserId(), gridGrouperServiceURL));
			steps.add(new GrouperAddMemberStep("test:stem2:stem3:group2", idp + users.get(2).getUserId(), gridGrouperServiceURL));

			// check stems, groups, and members
			steps.add(new GrouperCheckStemsStep("test", new String[] { "stem1", "stem2" }, gridGrouperServiceURL));
			steps.add(new GrouperCheckStemsStep("test:stem1", new String[] {}, gridGrouperServiceURL));
			steps.add(new GrouperCheckStemsStep("test:stem2", new String[] { "stem3", "stem4" }, gridGrouperServiceURL));
			steps.add(new GrouperCheckGroupsStep("test", new String[] {}, gridGrouperServiceURL));
			steps.add(new GrouperCheckGroupsStep("test:stem1", new String[] { "group1" }, gridGrouperServiceURL));
			steps.add(new GrouperCheckGroupsStep("test:stem2:stem3", new String[] { "group2", "group3" }, gridGrouperServiceURL));
			steps.add(new GrouperCheckMembersStep("test:stem1:group1", "All", new String[] { idp + users.get(1).getUserId(),
					idp + users.get(2).getUserId() }, gridGrouperServiceURL));
			steps.add(new GrouperCheckMembersStep("test:stem2:stem3:group2", "All", new String[] { idp + users.get(1).getUserId(),
					idp + users.get(2).getUserId() }, gridGrouperServiceURL));
			steps.add(new GrouperCheckMembersStep("test:stem2:stem3:group3", "All", new String[] {}, gridGrouperServiceURL));

			// grant privileges
			steps.add(new GrouperGrantPrivilegeStep("test:stem1:group1", idp + users.get(1).getUserId(), "admin",
					gridGrouperServiceURL));
			steps.add(new GrouperGrantPrivilegeStep("test:stem2:stem3:group2", idp + users.get(2).getUserId(), "admin",
					gridGrouperServiceURL));
			steps.add(new GrouperGrantPrivilegeStep("test:stem2:stem3:group2", idp + users.get(1).getUserId(), "optout",
					gridGrouperServiceURL));
			steps.add(new GrouperGrantPrivilegeStep("test:stem1", idp + users.get(1).getUserId(), "stem", gridGrouperServiceURL));

			steps.add(new GrouperGrantMembershipRequestsStep("test:stem1:group1", gridGrouperServiceURL));

			// check privileges
			steps.add(new GrouperCheckPrivilegesStep("test:stem1:group1", idp + users.get(1).getUserId(), new String[] { "admin" },
					gridGrouperServiceURL));
			steps.add(new GrouperCheckPrivilegesStep("test:stem2:stem3:group2", idp + users.get(2).getUserId(),
					new String[] { "admin" }, gridGrouperServiceURL));
			steps.add(new GrouperCheckPrivilegesStep("test:stem2:stem3:group2", idp + users.get(1).getUserId(),
					new String[] { "optout" }, gridGrouperServiceURL));
			steps.add(new GrouperCheckPrivilegesStep("test:stem1", idp + users.get(1).getUserId(), new String[] { "stem" },
					gridGrouperServiceURL));

			// test group admin privileges
			steps.add(new DorianAuthenticateStep(users.get(1).getUserId(), users.get(1).getPassword(), dorianServiceURL));
			steps.add(new GrouperAddMemberStep("test:stem1:group1", idp + "subject3", gridGrouperServiceURL));
			steps.add(new GrouperCheckMembersStep("test:stem1:group1", "All", new String[] { idp + users.get(1).getUserId(),
					idp + users.get(2).getUserId(), idp + "subject3" }, gridGrouperServiceURL));
			steps.add(new GrouperGrantPrivilegeStep("test:stem1:group1", idp + "subject3", "admin", gridGrouperServiceURL));
			steps.add(new GrouperCheckPrivilegesStep("test:stem1:group1", idp + "subject3", new String[] { "admin" },
					gridGrouperServiceURL));
			steps.add(new GrouperRemoveMemberStep("test:stem1:group1", idp + "subject3", gridGrouperServiceURL));
			steps.add(new GrouperCheckMembersStep("test:stem1:group1", "All", new String[] { idp + users.get(1).getUserId(),
					idp + users.get(2).getUserId() }, gridGrouperServiceURL));
		

			steps.add(new DorianAuthenticateStep(users.get(3).getUserId(), users.get(3).getPassword(), dorianServiceURL));
			steps.add(new GrouperAddMembershipRequestStep("test:stem1:group1", gridGrouperServiceURL));
			steps.add(new DorianAuthenticateStep(users.get(0).getUserId(), users.get(0).getPassword(), dorianServiceURL));
			steps.add(new GrouperUpdateMembershipRequestStep("test:stem1:group1", idp + users.get(3).getUserId(), gridGrouperServiceURL));

		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to create the GridGrouper System Tests: " + e.getMessage());
		}
		return steps;

	}

	protected boolean storySetUp() throws Throwable {
		this.tempDorianService = new File("tmp/dorian");
		this.tempGridGrouperService = new File("tmp/gridgrouper");
		File dorianLocation = new File(PATH_TO_DORIAN_PROJECT);
		File gridgrouperLocation = new File(PATH_TO_GRIDGROUPER_PROJECT);
		CopyServiceStep copyDorianService = new CopyServiceStep(dorianLocation, tempDorianService);
		copyDorianService.runStep();
		CopyServiceStep copyGridGrouperService = new CopyServiceStep(gridgrouperLocation, tempGridGrouperService);
		copyGridGrouperService.runStep();
		return true;
	}

	protected void storyTearDown() throws Throwable {
		StopContainerStep stopContainerStep = new StopContainerStep(getContainer());
		try {
			stopContainerStep.runStep();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		try {
			if (this.tempDorianService != null) {
				new DeleteServiceStep(tempDorianService).runStep();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		try {
			if (this.tempGridGrouperService != null) {
				new DeleteServiceStep(tempGridGrouperService).runStep();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		CleanupDorianStep cleanup = new CleanupDorianStep(getContainer(), trust);
		try {
			cleanup.runStep();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		DestroyContainerStep destroyContainerStep = new DestroyContainerStep(getContainer());
		try {
			destroyContainerStep.runStep();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		GrouperDropDbStep dropDB = new GrouperDropDbStep(".");
		try {
			dropDB.runStep();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		ProxyUtil.destroyDefaultProxy();	
	}

	private String getDorianSubject() throws Exception {
		File conf = new File(tempDorianService + File.separator + "etc" + File.separator + "dorian-configuration.xml");
		File props = new File(tempDorianService + File.separator + "etc" + File.separator + "dorian.properties");
		BeanUtils utils = new BeanUtils(new FileSystemResource(conf), new FileSystemResource(props));
		DorianProperties c = utils.getDorianProperties();
		String subject = c.getCertificateAuthority().getProperties().getCreationPolicy().getSubject();
		LdapName ldapname = new LdapName(subject);
		ldapname.remove(ldapname.size() - 1);
		ldapname.add("OU=Dorian");
		X509Name name = new X509Name(false, ldapname.toString());
		return X509NameHelper.toString(name) + "/CN=";
	}

}
