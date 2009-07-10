package org.cagrid.tutorials.photosharing;

import java.util.ArrayList;
import java.util.List;

import org.cagrid.grape.GridApplication;
import org.cagrid.grape.configuration.ServiceConfiguration;
import org.cagrid.grape.configuration.ServiceDescriptor;
import org.cagrid.grape.configuration.Services;


public class Utils {

    public static List<PhotoSharingHandle> getPhotoSharingServices() throws Exception {
        List<PhotoSharingHandle> services = new ArrayList<PhotoSharingHandle>();

        ServiceConfiguration conf = (ServiceConfiguration) GridApplication.getContext().getConfigurationManager()
            .getConfigurationObject("photosharing");
        Services s = conf.getServices();
        if (s != null) {
            ServiceDescriptor[] list = s.getServiceDescriptor();
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    PhotoSharingHandle handle = new PhotoSharingHandle(list[i]);
                    services.add(handle);
                }
            }
        }

        return services;
    }
}
