package org.cagrid.demo.photosharing.client;

import java.awt.image.RenderedImage;
import java.io.InputStream;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import org.cagrid.demo.photosharing.stubs.PhotoSharingPortType;
import org.cagrid.demo.photosharing.stubs.service.PhotoSharingServiceAddressingLocator;
import org.cagrid.demo.photosharing.utils.ImageType;
import org.cagrid.demo.photosharing.utils.ImageUtils;
import org.cagrid.demo.photosharing.common.PhotoSharingI;
import org.cagrid.demo.photosharing.gallery.client.GalleryClient;
import org.castor.util.Base64Decoder;
import org.castor.util.Base64Encoder;

import com.sun.media.jai.widget.DisplayJAI;

import gov.nih.nci.cagrid.common.security.ProxyUtil;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class PhotoSharingClient extends PhotoSharingClientBase implements PhotoSharingI {	

	public PhotoSharingClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public PhotoSharingClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
		super(url,proxy);
	}

	public PhotoSharingClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
		this(epr,null);
	}

	public PhotoSharingClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
		super(epr,proxy);
	}

	public static void usage(){
		System.out.println(PhotoSharingClient.class.getName() + " -url <service url>");
	}

	public static void main(String [] args){
		System.out.println("Running the Grid Service Client");
		try{
			if(!(args.length < 2)){
				if(args[0].equals("-url")){
					PhotoSharingClient client = new PhotoSharingClient(args[1]);
					// place client calls here if you want to use this main as a
					// test....
					GlobusCredential userCredential = ProxyUtil.getDefaultProxy();
					System.out.println("Logged in as: " + userCredential.getIdentity());

					String galleryName = "Summer Vacation";
					GalleryClient galleryClient = client.createGallery(galleryName);

					//list galleries... ensure ours is there.
					GalleryClient[] galleries = client.listGalleries();
					if (!(galleries[0].getGalleryName()).equals(galleryName)) {
						throw new RuntimeException("Gallery isn't present!");
					}

					//remove gallery and test that it was removed properly
					galleryClient.destroy();

					galleries = client.listGalleries();
					//if the gallery list is empty, we actually get a null back instead of an empty List... this is due to Axis serialization issues
					if (galleries != null) {
						throw new RuntimeException("Gallery wasn't destroyed!");
					}

					galleryClient = client.createGallery(galleryName);

					String testIdentity = userCredential.getIdentity() + "testinguser";
					org.cagrid.demo.photosharing.domain.User user = new org.cagrid.demo.photosharing.domain.User();
					user.setId(Long.valueOf(0));
					user.setUserIdentity(testIdentity);
					galleryClient.grantAddImagePrivileges(user);
					galleryClient.grantViewGalleryPrivileges(user);
					galleryClient.revokeAddImagePrivileges(user);
					galleryClient.revokeViewGalleryPrivileges(user);


					String imageName = "OSU Medical Center Logo";
					String imageDescription = "Displaying OSU Medical Center Logo";

					byte[] imageBytes = ImageUtils.loadImageAsBytes("osu_medcenter logo.jpg");
					//Note: only the castor Base64Encoder encodes properly... the Sun one doesn't (corrupts image)
					String encoded = new String(Base64Encoder.encode(imageBytes));

					org.cagrid.demo.photosharing.domain.ImageDescription beanDesc = new org.cagrid.demo.photosharing.domain.ImageDescription();
					beanDesc.setId(Long.valueOf(0)); //doesn't matter what this is set to
					beanDesc.setDescription(imageDescription);
					beanDesc.setName(imageName);
					beanDesc.setType(ImageType.JPG.name());

					org.cagrid.demo.photosharing.domain.Image beanImage = new org.cagrid.demo.photosharing.domain.Image();
					beanImage.setId(Long.valueOf(0)); //doesn't matter what this is set to

					beanImage.setImageDescription(beanDesc);
					beanImage.setData(encoded);

					//this ImageDescription instance contains a real image ID (returned from server)
					org.cagrid.demo.photosharing.domain.ImageDescription galleryImageDescription = galleryClient.addImage(beanImage);

					org.cagrid.demo.photosharing.domain.ImageDescription[] imageDescriptions = galleryClient.listImages();


					for (org.cagrid.demo.photosharing.domain.ImageDescription desc : imageDescriptions) {

						org.cagrid.demo.photosharing.domain.Image image = galleryClient.getImage(desc);
						System.out.println(image.getImageDescription().getId());
						System.out.println(image.getImageDescription().getName());
						System.out.println(image.getImageDescription().getDescription());
						System.out.println(image.getImageDescription().getType());
						System.out.println(image.getData());
					}

					try {

						//create image with ID that doesn't exist
						beanDesc = new org.cagrid.demo.photosharing.domain.ImageDescription();
						beanDesc.setId(Long.valueOf(1314)); //doesn't matter what this is set to
						beanDesc.setDescription(imageDescription);
						beanDesc.setName(imageName);
						beanDesc.setType(ImageType.JPG.name());

						galleryClient.getImage(beanDesc);
						System.err.println("found image that shouldn't exist!");
					} catch(org.cagrid.demo.photosharing.stubs.types.PhotoSharingException e) {
						System.out.println("Correctly received image not found exception");
					}

					//test image permissions
					String fakeIdentity = userCredential.getIdentity() + "fakeappended";
					galleryClient.grantImageRetrievalPrivileges(galleryImageDescription, user);
					//now try to retrieve image
					try {
						galleryClient.getImage(galleryImageDescription);
						System.out.println("Image authorization FAILED********");
					} catch(Exception e) {
						System.out.println("Image authorization worked");
					}

					//now add us and confirm retrieval
					org.cagrid.demo.photosharing.domain.User realUser = new org.cagrid.demo.photosharing.domain.User();
					realUser.setId(Long.valueOf(0));
					realUser.setUserIdentity(userCredential.getIdentity());

					galleryClient.grantImageRetrievalPrivileges(galleryImageDescription, realUser);
					//now try to retrieve image
					org.cagrid.demo.photosharing.domain.Image image = galleryClient.getImage(galleryImageDescription);

					byte[] imageDataFromServer = Base64Decoder.decode(image.getData());
					RenderedImage renderedImage = ImageUtils.loadImageFromBytes(imageDataFromServer);
					DisplayJAI display = new DisplayJAI(renderedImage);

					JFrame frame = new JFrame();
					frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
					frame.getContentPane().add(display);
					frame.pack();
					frame.show();

				} else {
					usage();
					System.exit(1);
				}
			} else {
				usage();
				System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException {
		synchronized(portTypeMutex){
			configureStubSecurity((Stub)portType,"getMultipleResourceProperties");
			return portType.getMultipleResourceProperties(params);
		}
	}

	public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException {
		synchronized(portTypeMutex){
			configureStubSecurity((Stub)portType,"getResourceProperty");
			return portType.getResourceProperty(params);
		}
	}

	public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException {
		synchronized(portTypeMutex){
			configureStubSecurity((Stub)portType,"queryResourceProperties");
			return portType.queryResourceProperties(params);
		}
	}

	public org.cagrid.demo.photosharing.gallery.client.GalleryClient createGallery(java.lang.String galleryName) throws RemoteException, org.apache.axis.types.URI.MalformedURIException, org.cagrid.demo.photosharing.stubs.types.PhotoSharingException {
		synchronized(portTypeMutex){
			configureStubSecurity((Stub)portType,"createGallery");
			org.cagrid.demo.photosharing.stubs.CreateGalleryRequest params = new org.cagrid.demo.photosharing.stubs.CreateGalleryRequest();
			params.setGalleryName(galleryName);
			org.cagrid.demo.photosharing.stubs.CreateGalleryResponse boxedResult = portType.createGallery(params);
			EndpointReferenceType ref = boxedResult.getGalleryReference().getEndpointReference();
			return new org.cagrid.demo.photosharing.gallery.client.GalleryClient(ref,getProxy());
		}
	}

	public org.cagrid.demo.photosharing.gallery.client.GalleryClient[] listGalleries() throws RemoteException, org.apache.axis.types.URI.MalformedURIException {
		synchronized(portTypeMutex){
			configureStubSecurity((Stub)portType,"listGalleries");
			org.cagrid.demo.photosharing.stubs.ListGalleriesRequest params = new org.cagrid.demo.photosharing.stubs.ListGalleriesRequest();
			org.cagrid.demo.photosharing.stubs.ListGalleriesResponse boxedResult = portType.listGalleries(params);
			org.cagrid.demo.photosharing.gallery.client.GalleryClient[] clientArray = null;
			if(boxedResult.getGalleryReference()!=null){
				clientArray = new org.cagrid.demo.photosharing.gallery.client.GalleryClient[boxedResult.getGalleryReference().length];
				for(int i = 0; i < boxedResult.getGalleryReference().length; i++){
					clientArray[i] = new org.cagrid.demo.photosharing.gallery.client.GalleryClient(boxedResult.getGalleryReference(i).getEndpointReference(),getProxy());
				}
			}
			return clientArray;
		}
	}

}
