package gov.nih.nci.cagrid.introduce.portal.modification;

import gov.nih.nci.cagrid.introduce.common.ResourceManager;

import java.io.File;

import org.cagrid.grape.ApplicationComponent;
import org.cagrid.grape.GridApplication;
import org.cagrid.grape.model.Dimensions;
import org.cagrid.grape.model.RenderOptions;


public class ModificationLauncher {

    public File methodsDirectory = null;


    public ModificationLauncher() {
        try {
            chooseService();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.methodsDirectory != null) {
            try {
                ModificationViewer viewer = new ModificationViewer(this.methodsDirectory);
                RenderOptions ro = new RenderOptions();
                GridApplication.getContext().addApplicationComponent(viewer, null, ro);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void chooseService() throws Exception {
        String dir = ResourceManager.promptDir(null);
        if (dir != null) {
            this.methodsDirectory = new File(dir);
        }
    }

}
