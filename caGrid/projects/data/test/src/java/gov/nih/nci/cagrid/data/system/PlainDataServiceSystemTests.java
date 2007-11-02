package gov.nih.nci.cagrid.data.system;

import gov.nih.nci.cagrid.data.creation.CreationTests;
import gov.nih.nci.cagrid.data.creation.DataTestCaseInfo;
import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainer;
import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainerFactory;
import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainerType;
import gov.nih.nci.cagrid.testing.system.deployment.steps.DeployServiceStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.DestroyContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.StartContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.StopContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.UnpackContainerStep;

import java.util.Vector;

import com.atomicobject.haste.framework.Step;

/** 
 *  PlainDataServiceSystemTests
 *  System test to just create a data service, deploy it, 
 *  and make sure it can start up.
 * 
 * @author David Ervin
 * 
 * @created Sep 28, 2007 12:22:29 PM
 * @version $Id: PlainDataServiceSystemTests.java,v 1.5 2007-11-02 17:48:47 dervin Exp $ 
 */
public class PlainDataServiceSystemTests extends BaseSystemTest {
    
    private static ServiceContainer container = null;
    
    static {
        try {
            container = ServiceContainerFactory.createContainer(ServiceContainerType.GLOBUS_CONTAINER);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Failed to create container: " + ex.getMessage());
        }
    }
    
    
    public PlainDataServiceSystemTests() {
        setName("Plain Data Service System Test");
    }
    
    
    public String getName() {
        return "Plain Data Service System Test";
    }


    public String getDescription() {
        return "System test to just create a data service, deploy it, " +
                "and make sure it can start up.";
    }
    
    
    protected boolean storySetUp() {
        // 1) set up a clean, temporary service container
        Step step = new UnpackContainerStep(container);
        try {
            step.runStep();
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
        return true;
    }


    protected Vector steps() {
        DataTestCaseInfo info = new CreationTests.PlainDataServiceInfo();
        Vector<Step> steps = new Vector<Step>();
        // data service presumed to have been created
        // by the data service creation tests
        // 2) Rebuild the service
        steps.add(new RebuildServiceStep(info, getIntroduceBaseDir()));
        // 3) deploy data service
        steps.add(new DeployServiceStep(container, info.getDir()));
        // 4) start the container
        steps.add(new StartContainerStep(container));
        return steps;
    }
    
    
    protected void storyTearDown() throws Throwable {
        // 5) stop globus
        Step stopStep = new StopContainerStep(container);
        try {
            stopStep.runStep();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        // 6) throw away globus
        Step destroyStep = new DestroyContainerStep(container);
        try {
            destroyStep.runStep();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
