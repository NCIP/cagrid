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


    public static String getRegistrationService() throws Exception {
        ServiceConfiguration conf = (ServiceConfiguration) GridApplication.getContext().getConfigurationManager()
            .getConfigurationObject("photosharingregistration");
        Services s = conf.getServices();
        if (s != null) {
            ServiceDescriptor[] list = s.getServiceDescriptor();
            if (list != null) {
                if (list.length == 0) {
                    throw new Exception(
                        "No photo sharing registration service configured, you must configure one registration service!!!");
                } else if (list.length == 1) {
                    return list[0].getServiceURL();
                } else {
                    throw new Exception(
                        "Multiple photo sharing registrations service were configured, where as only one is allowed.");
                }

            } else {
                throw new Exception(
                    "No photo sharing registration service configured, you must configure one registration service!!!");
            }
        } else {
            throw new Exception(
                "No photo sharing registration service configured, you must configure one registration service!!!");
        }

    }

}
