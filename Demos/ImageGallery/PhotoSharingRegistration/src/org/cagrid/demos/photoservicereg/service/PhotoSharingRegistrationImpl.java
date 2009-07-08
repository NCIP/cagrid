package org.cagrid.demos.photoservicereg.service;

import edu.internet2.middleware.grouper.GrantPrivilegeException;
import edu.internet2.middleware.grouper.GroupDeleteException;
import edu.internet2.middleware.grouper.InsufficientPrivilegeException;
import edu.internet2.middleware.grouper.SchemaException;
import edu.internet2.middleware.grouper.StemDeleteException;
import edu.internet2.middleware.grouper.StemNotFoundException;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouper;
import gov.nih.nci.cagrid.gridgrouper.common.SubjectUtils;
import gov.nih.nci.cagrid.gridgrouper.grouper.GroupI;
import gov.nih.nci.cagrid.gridgrouper.grouper.StemI;

import java.rmi.RemoteException;
import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;

import org.globus.gsi.jaas.JaasSubject;
import org.oasis.wsrf.faults.BaseFaultTypeDescription;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class PhotoSharingRegistrationImpl extends PhotoSharingRegistrationImplBase {

	private String grouperURL;
	private GridGrouper grouper;
	private String photoSharingSystemStemName;
	private StemI photoStem;

	public PhotoSharingRegistrationImpl() throws RemoteException {
		super();

		try {
			this.grouperURL = PhotoSharingRegistrationConfiguration.getConfiguration().getGridGrouperURL();
			this.photoSharingSystemStemName = PhotoSharingRegistrationConfiguration.getConfiguration().getPhotoSharingStemSystemName();		

			System.out.println("Photo sharing registration service is using grid grouper URL: " + this.grouperURL);
			System.out.println("Photo sharing registration service is using grid grouper stem: " + this.photoSharingSystemStemName);

			//Connect to grid grouper using service credential (default as it is first on the credential search order)
			this.grouper = new PhotoSharingGridGrouper(this.grouperURL, false);
			//Get a handle to the grid grouper stem we should use for authorization
			this.photoStem = this.grouper.findStem(this.photoSharingSystemStemName);

			//find our service's identity
			Subject serviceSubject = JaasSubject.getCurrentSubject();
			Set<Principal> principals = serviceSubject.getPrincipals();
			for (Principal p : principals) {
				System.out.println("Connected to grid grouper as identity : " + p.getName());
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new RemoteException("Registration service cannot start: " + e.getMessage());
		}
	}

	public void registerPhotoSharingService(java.lang.String stemSystemExtension,java.lang.String stemDisplayExtension,java.lang.String serviceIdentity,java.lang.String userIdentity) throws RemoteException, org.cagrid.demos.photoservicereg.stubs.types.RegistrationException {
		try {
			StemI serviceStem = this.photoStem.addChildStem(stemSystemExtension, stemDisplayExtension);

			//NOTE: any of these privileges might exist... don't error if that's the case
			//add stem privileges
			
			try {
				addStemPrivilege(serviceIdentity, serviceStem);
			} catch(Exception e) {
				//ignore
				System.out.println("STEM privileges already exist on stem " + serviceStem.getName() + " for identity " + serviceIdentity);
			}
			try {
				addStemPrivilege(userIdentity, serviceStem);
			} catch(Exception e) {
				//ignore
				System.out.println("STEM privileges already exist on stem " + serviceStem.getName() + " for identity " + userIdentity);
			}

			//add group privileges
			try {
				addGroupPrivilege(serviceIdentity, serviceStem);
			} catch(Exception e) {
				//ignore
				System.out.println("GROUP privileges already exist on stem " + serviceStem.getName() + " for identity " + serviceIdentity);
			}

			try {
				addGroupPrivilege(userIdentity, serviceStem);
			} catch(Exception e) {
				//ignore
				System.out.println("GROUP privileges already exist on stem " + serviceStem.getName() + " for identity " + userIdentity);
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			org.cagrid.demos.photoservicereg.stubs.types.RegistrationException re = new org.cagrid.demos.photoservicereg.stubs.types.RegistrationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
			re.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw re;

		}
	}

	public void unregisterPhotoSharingService(java.lang.String stemSystemExtension) throws RemoteException, org.cagrid.demos.photoservicereg.stubs.types.RegistrationException {
		StemI serviceStem = findChildStem(this.photoStem, stemSystemExtension);

		if (serviceStem != null) {
			//remove it
			try {
			deleteStemHierarchy(serviceStem);
			serviceStem.delete();
			} catch(Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				org.cagrid.demos.photoservicereg.stubs.types.RegistrationException re = new org.cagrid.demos.photoservicereg.stubs.types.RegistrationException();
				BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription(e.getMessage());
				re.setDescription(new BaseFaultTypeDescription[] { faultDesc });
				throw re;

			}

		} else {
			org.cagrid.demos.photoservicereg.stubs.types.RegistrationException re = new org.cagrid.demos.photoservicereg.stubs.types.RegistrationException();
			BaseFaultTypeDescription faultDesc = new BaseFaultTypeDescription("Cannot find stem with system extension: " + stemSystemExtension);
			re.setDescription(new BaseFaultTypeDescription[] { faultDesc });
			throw re;

		}

	}

	public static void addStemPrivilege(String identity, StemI stem) throws SubjectNotFoundException, GrantPrivilegeException, InsufficientPrivilegeException, SchemaException {
		edu.internet2.middleware.subject.Subject subject = SubjectUtils.getSubject(identity);
		stem.grantPriv(subject, edu.internet2.middleware.grouper.NamingPrivilege.STEM);
	}

	public static void addGroupPrivilege(String identity, StemI stem) throws GrantPrivilegeException, InsufficientPrivilegeException, SchemaException, SubjectNotFoundException {
		edu.internet2.middleware.subject.Subject subject = SubjectUtils.getSubject(identity);
		stem.grantPriv(subject, edu.internet2.middleware.grouper.NamingPrivilege.CREATE);
	}

	/**
	 * Return a stem with matching display extension, or null if that stem doesn't exist
	 * @return
	 */
	public static StemI findChildStem(StemI parentStem, String systemExtension) {
		Set<StemI> existingStems = parentStem.getChildStems();
		for (StemI stem : existingStems) {
			if (stem.getExtension().equals(systemExtension)) {
				return stem;
			}
		}
		return null;
	}

	/**
	 * WARNING: this will remove all sub-groups and sub-stems recursively. The stem itself is left unchanged.
	 * @param stem
	 * @throws InsufficientPrivilegeException 
	 * @throws GroupDeleteException 
	 * @throws StemDeleteException 
	 */
	public static void deleteStemHierarchy(StemI curStem) throws GroupDeleteException, InsufficientPrivilegeException, StemDeleteException {
		Set<StemI> subStems = (Set<StemI>) curStem.getChildStems();
		for (StemI stem: subStems) {
			deleteStemHierarchy(stem);
			stem.delete();
		}
		Set<GroupI> groups = (Set<GroupI>) curStem.getChildGroups();
		for (GroupI group : groups) {
			group.delete();
		}
	}
	

}

