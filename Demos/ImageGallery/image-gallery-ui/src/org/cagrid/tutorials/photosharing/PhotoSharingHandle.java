package org.cagrid.tutorials.photosharing;

import gov.nih.nci.cagrid.common.security.ProxyUtil;

import org.cagrid.demo.photosharing.client.PhotoSharingClient;
import org.cagrid.gaards.ui.common.ServiceHandle;
import org.cagrid.grape.configuration.ServiceDescriptor;
import org.globus.gsi.GlobusCredential;


public class PhotoSharingHandle extends ServiceHandle {
    
   PhotoSharingClient client;

    public PhotoSharingHandle(ServiceDescriptor des) throws Exception{
        super(des);
        GlobusCredential cred = ProxyUtil.getDefaultProxy();
        client = new PhotoSharingClient(des.getServiceURL(),cred);  
    }

}
