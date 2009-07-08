package org.cagrid.demo.photosharing.serviceclient;

import org.cagrid.demos.photoservicereg.client.PhotoSharingRegistrationClient;

public class PhotoSharingRegistrationApplication {

	public static void usage(){
		System.out.println(PhotoSharingRegistrationApplication.class.getName() + " -url <service url>");
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Running the Photo Sharing Registration Application");
		try{
			if(!(args.length < 2)){
				if(args[0].equals("-url")){
					String photoSharingRegistrationServiceURL = args[1];
					
					PhotoSharingRegistrationClient registrationClient = new PhotoSharingRegistrationClient(photoSharingRegistrationServiceURL);
					
					String hostname = "justin-permars-macbook-pro.local";
					String stemSystemExtension = hostname;
					String stemDisplayExtension = hostname;
					
					String serviceIdentity = "/O=caBIG/OU=caGrid/OU=Training/OU=Services/CN=justin-permars-macbook-pro.local";
					String userIdentity = "/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=jpermar";
					registrationClient.registerPhotoSharingService(stemSystemExtension, stemDisplayExtension, serviceIdentity, userIdentity);
					
					
				} else {
					usage();
					System.exit(1);
				}
			} else {
				usage();
				System.exit(1);
			}

		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);

		}

	}

}
