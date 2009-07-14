package org.cagrid.demo.photosharing.service;

import edu.internet2.middleware.grouper.GrantPrivilegeException;
import edu.internet2.middleware.grouper.InsufficientPrivilegeException;
import edu.internet2.middleware.grouper.SchemaException;
import edu.internet2.middleware.subject.Subject;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouper;
import gov.nih.nci.cagrid.gridgrouper.common.SubjectUtils;
import gov.nih.nci.cagrid.gridgrouper.grouper.NamingPrivilegeI;
import gov.nih.nci.cagrid.gridgrouper.grouper.StemI;

import java.net.URL;
import java.rmi.RemoteException;
import java.security.Principal;
import java.util.Set;

import org.apache.axis.message.addressing.AttributedURI;
import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.demo.photosharing.service.globus.resource.PhotoSharingResource;
import org.cagrid.demo.photosharing.utils.GalleryManager;
import org.cagrid.demo.photosharing.utils.GroupUtils;
import org.cagrid.demo.photosharing.utils.GrouperGallery;
import org.globus.gsi.jaas.JaasSubject;
import org.globus.wsrf.container.ServiceHost;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class PhotoSharingImpl extends PhotoSharingImplBase {

	private String grouperURL;
	private GridGrouper grouper;
	private GalleryManager galleryManager;
	private String photoSharingServiceSystemStemName;

	public PhotoSharingImpl() throws RemoteException {
		super();

		//Load service properties
		try {
			this.grouperURL = PhotoSharingConfiguration.getConfiguration().getGridGrouperURL();
			this.photoSharingServiceSystemStemName = PhotoSharingConfiguration.getConfiguration().getServiceStemSystemName();		

			System.out.println("Photo sharing service is using grid grouper URL: " + this.grouperURL);
			System.out.println("Photo sharing service is using grid grouper stem: " + this.photoSharingServiceSystemStemName);

			//Connect to grid grouper using service credential (default as it is first on the credential search order)
			this.grouper = new PhotoSharingGridGrouper(this.grouperURL, false);
			//Get a handle to the grid grouper stem we should use for authorization
			StemI serviceStem = this.grouper.findStem(this.photoSharingServiceSystemStemName);

			//find our service's identity
			javax.security.auth.Subject serviceSubject = JaasSubject.getCurrentSubject();
			Set<Principal> principals = serviceSubject.getPrincipals();
			String serviceIdentity = null;
			for (Principal p : principals) {
				System.out.println("Principal: " + p.getName());
				serviceIdentity = p.getName();
			}

			Set<NamingPrivilegeI> privs = (Set<NamingPrivilegeI>)serviceStem.getPrivs(SubjectUtils.getSubject(serviceIdentity));
			for (NamingPrivilegeI priv : privs) {
				System.out.println(priv.getSubject().getId() + " " + priv.getName());
			}

			//Delete anything that is old under this stem... we do this because this service doesn't persist data
			GroupUtils.deleteStemHierarchy(serviceStem);

			//Create a gallery manager helper to manage galleries for our service
			this.galleryManager = new GalleryManager(serviceIdentity, serviceStem, grouper, grouperURL);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new RemoteException("Photo Sharing service failed to initialize: " + e.getMessage());
		}
	}

	public static void addStemPrivilege(String identity, StemI stem) throws SubjectNotFoundException, GrantPrivilegeException, InsufficientPrivilegeException, SchemaException {
		Subject subject = SubjectUtils.getSubject(identity);
		stem.grantPriv(subject, edu.internet2.middleware.grouper.NamingPrivilege.STEM);
	}

	public static void addGroupPrivilege(String identity, StemI stem) throws GrantPrivilegeException, InsufficientPrivilegeException, SchemaException, SubjectNotFoundException {
		Subject subject = SubjectUtils.getSubject(identity);
		stem.grantPriv(subject, edu.internet2.middleware.grouper.NamingPrivilege.CREATE);
	}

  public org.cagrid.demo.photosharing.gallery.stubs.types.GalleryReference createGallery(java.lang.String galleryName) throws RemoteException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		org.apache.axis.message.addressing.EndpointReferenceType epr = new org.apache.axis.message.addressing.EndpointReferenceType();
		org.cagrid.demo.photosharing.gallery.service.globus.resource.GalleryResourceHome home = null;
		org.globus.wsrf.ResourceKey resourceKey = null;
		org.apache.axis.MessageContext ctx = org.apache.axis.MessageContext.getCurrentContext();
		String servicePath = ctx.getTargetService();
		String homeName = org.globus.wsrf.Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/" + "galleryHome";

		try {
			javax.naming.Context initialContext = new javax.naming.InitialContext();
			home = (org.cagrid.demo.photosharing.gallery.service.globus.resource.GalleryResourceHome) initialContext.lookup(homeName);
			resourceKey = home.createResource();

			//  Grab the newly created resource
			org.cagrid.demo.photosharing.gallery.service.globus.resource.GalleryResource thisResource = (org.cagrid.demo.photosharing.gallery.service.globus.resource.GalleryResource)home.find(resourceKey);

			//  This is where the creator of this resource type can set whatever needs
			//  to be set on the resource so that it can function appropriatly  for instance
			//  if you want the resouce to only have the query string then there is where you would
			//  give it the query string.
			/* BEGIN CODE BLOCK */
			String userDN = gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.getCallerIdentity();

			GrouperGallery gallery = this.galleryManager.createGallery(galleryName, userDN);
			
			// sample of setting creator only security.  This will only allow the caller that created
			// this resource to be able to use it.
			//thisResource.setSecurityDescriptor(gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.createCreatorOnlyResourceSecurityDescriptor());
			thisResource.setGallery(gallery);
			/* END CODE BLOCK */

			String transportURL = (String) ctx.getProperty(org.apache.axis.MessageContext.TRANS_URL);
			//modify EPR to fix a bug
			//BEGIN
	        AttributedURI uri = new AttributedURI(transportURL);
	        URL baseURL = ServiceHost.getBaseURL();
	        String correctHost = baseURL.getHost();
	        uri.setHost(correctHost);
	        transportURL = uri.toString();
			//END
			transportURL = transportURL.substring(0,transportURL.lastIndexOf('/') +1 );
			transportURL += "Gallery";
			epr = org.globus.wsrf.utils.AddressingUtils.createEndpointReference(transportURL,resourceKey);
	        System.out.println("Creating gallery EPR of: " + epr.getAddress().toString());
		} catch (Exception e) {
			throw new RemoteException("Error looking up Gallery home:" + e.getMessage(), e);
		}

		//return the typed EPR
		org.cagrid.demo.photosharing.gallery.stubs.types.GalleryReference ref = new org.cagrid.demo.photosharing.gallery.stubs.types.GalleryReference();
		ref.setEndpointReference(epr);
		// BEGIN CODE
		//Add the reference to our list of maintained references
		try {
			PhotoSharingResource resource = getResourceHome().getAddressedResource();
			
			resource.addGallery(galleryName, ref);
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new RemoteException("Cannot get reference to PhotoSharingResource: " + e.getMessage());
		}
		// END CODE

		return ref;
	}

  public org.cagrid.demo.photosharing.gallery.stubs.types.GalleryReference[] listGalleries() throws RemoteException {
		try {
			PhotoSharingResource resource = getResourceHome().getAddressedResource();
			
			return resource.listGalleries();
		} catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new RemoteException("Cannot get reference to PhotoSharingResource: " + e.getMessage());
		}
	}

}

