package org.cagrid.fqp.test.remote;

import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainer;
import gov.nih.nci.cagrid.testing.system.deployment.steps.DestroyContainerStep;
import gov.nih.nci.cagrid.testing.system.deployment.steps.StopContainerStep;

import java.io.File;

import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.fqp.test.common.AggregationStory;
import org.cagrid.fqp.test.common.DataServiceDeploymentStory;
import org.cagrid.fqp.test.common.FQPTestingConstants;
import org.cagrid.fqp.test.common.FederatedQueryProcessorHelper;
import org.cagrid.fqp.test.common.QueryStory;
import org.cagrid.fqp.test.common.ServiceContainerSource;
import org.junit.After;
import org.junit.Test;

/** 
 *  RemoteFqpSystemTests
 *  System tests for FQP using the remote Federated Query API
 * 
 * @author David Ervin
 * 
 * @created Jul 10, 2008 10:57:40 AM
 * @version $Id: RemoteFqpSystemTests.java,v 1.22 2009-05-04 20:13:36 dervin Exp $ 
 */
public class RemoteFqpSystemTests {
    
    public static final Log logger = LogFactory.getLog(RemoteFqpSystemTests.class);
    
    private DataServiceDeploymentStory[] dataServiceDeployments;
    private FQPServiceDeploymentStory fqpDeployment;
    
    
    @Test
    public void remoteFqpSystemTests() throws Throwable {
        // deploy two example SDK data services which pull from slightly different data
        DataServiceDeploymentStory exampleService1Deployment = 
            new DataServiceDeploymentStory(new File("resources/services/ExampleSdkService1.zip"), false, false);
        DataServiceDeploymentStory exampleService2Deployment =
            new DataServiceDeploymentStory(new File("resources/services/ExampleSdkService2.zip"), false, false);
        dataServiceDeployments = new DataServiceDeploymentStory[] {
            exampleService1Deployment, exampleService2Deployment
        };
        
        exampleService1Deployment.runBare();
        exampleService2Deployment.runBare();
        
        // sources of data service containers.  This allows stories to grab
        // service containers after they've been created in the order of execution
        ServiceContainerSource[] containerSources = new ServiceContainerSource[] {
            exampleService1Deployment, exampleService2Deployment
        };
        
        // deploy the FQP with transfer service
        fqpDeployment = new FQPServiceDeploymentStory(getFqpDir(), getTransferDir(), false);
        fqpDeployment.runBare();
        
        // run query constraint checking stories
        QueryConstraintsStory constraintsStory =
            new QueryConstraintsStory(containerSources, fqpDeployment, 
                fqpDeployment.getTempFqpServiceDir());
        constraintsStory.runBare();
        
        // query helper
        FederatedQueryProcessorHelper standardQueryHelper = 
            new FederatedQueryProcessorHelper(fqpDeployment);
        
        // initialize ws-notification client side
        NotificationClientSetupStory notificationSetupStory = new NotificationClientSetupStory();
        notificationSetupStory.runBare();
        
        // run standard queries
        QueryStory queryTests = new QueryStory(containerSources, standardQueryHelper);
        queryTests.runBare();
        
        // run the aggregation queries
        AggregationStory aggregationTests = new AggregationStory(containerSources, standardQueryHelper);
        aggregationTests.runBare();
        
        // run asynchronous queries
        AsynchronousExecutionStory asynchronousStory = 
            new AsynchronousExecutionStory(containerSources, fqpDeployment);
        asynchronousStory.runBare();

        // run enumeration queries
        EnumerationExecutionStory enumerationStory = 
            new EnumerationExecutionStory(containerSources, fqpDeployment);
        enumerationStory.runBare();

        // run partial results queries
        PartialResultsStory partialResultsStory = 
            new PartialResultsStory(containerSources, fqpDeployment);
        partialResultsStory.runBare();
        
        // run transfer queries
        TransferExecutionStory transferStory = 
            new TransferExecutionStory(containerSources, fqpDeployment);
		transferStory.runBare();
    }
    
    
    private File getFqpDir() {
        String value = System.getProperty(FQPTestingConstants.FQP_DIR_PROPERTY);
        if (value == null) {
            value = FQPTestingConstants.DEFAULT_FQP_DIR;
            logger.warn("System property " + FQPTestingConstants.FQP_DIR_PROPERTY + " was not set!");
            logger.warn("Using default value of " + value);
        }
        File dir = new File(value);
        return dir;
    }
    
    
    private File getTransferDir() {
        String value = System.getProperty(FQPTestingConstants.TRANSFER_SERVICE_DIR_PROPERTY);
        if (value == null) {
            value = FQPTestingConstants.DEFAULT_TRANSFER_DIR;
            logger.warn("System property " + FQPTestingConstants.TRANSFER_SERVICE_DIR_PROPERTY + " was not set!");
            logger.warn("Using default value of " + value);
        }
        File dir = new File(value);
        return dir;
    }
    
    
    @After
    public void cleanUp() {
        logger.debug("Cleaning Up Remote FQP Tests");
        for (DataServiceDeploymentStory deployment : dataServiceDeployments) {
            if (deployment != null && deployment.getServiceContainer() != null) {
                ServiceContainer container = deployment.getServiceContainer();
                try {
                    new StopContainerStep(container).runStep();
                    new DestroyContainerStep(container).runStep();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }
        try {
            if (fqpDeployment != null && fqpDeployment.getServiceContainer() != null) {
                ServiceContainer fqpContainer = fqpDeployment.getServiceContainer();
                // give FQP container 60 seconds to shut down
                fqpContainer.getProperties().setMaxShutdownWaitTime(Integer.valueOf(60));
                new StopContainerStep(fqpContainer).runStep();
                new DestroyContainerStep(fqpContainer).runStep();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

 
    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        TestResult result = runner.doRun(new TestSuite(RemoteFqpSystemTests.class));
        System.exit(result.errorCount() + result.failureCount());
    }
}
