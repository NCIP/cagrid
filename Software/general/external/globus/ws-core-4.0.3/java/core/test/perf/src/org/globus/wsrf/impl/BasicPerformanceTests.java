/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
package org.globus.wsrf.impl;

import java.security.cert.X509Certificate;
import java.util.Calendar;

import javax.security.auth.Subject;
import javax.xml.rpc.Stub;

import org.apache.axis.components.uuid.UUIDGenFactory;
import org.apache.axis.message.addressing.AttributedURI;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.message.addressing.ReferencePropertiesType;
import org.apache.axis.types.URI;

import org.oasis.wsn.TopicExpressionType;

import org.globus.gsi.GlobusCredential;
import org.globus.wsrf.NotificationConsumerManager;
import org.globus.wsrf.WSNConstants;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authentication.encryption.EncryptionCredentials;
import org.globus.wsrf.impl.security.authorization.SelfAuthorization;
import org.globus.wsrf.test.GridTestCase;
import org.globus.wsrf.tests.performance.basic.BaseType;
import org.globus.wsrf.tests.performance.basic.BaseTypeJob;
import org.globus.wsrf.tests.performance.basic.BaseTypeSubscribe;
import org.globus.wsrf.tests.performance.basic.DeleteRequestType;
import org.globus.wsrf.tests.performance.basic.DeleteType;
import org.globus.wsrf.tests.performance.basic.NameValuePairType;
import org.globus.wsrf.tests.performance.basic.TestPortType;
import org.globus.wsrf.tests.performance.basic.TransferRequestType;
import org.globus.wsrf.tests.performance.basic.TransferType;
import org.globus.wsrf.tests.performance.basic.service.TestServiceAddressingLocator;

public class BasicPerformanceTests extends GridTestCase {

    TestServiceAddressingLocator locator = new TestServiceAddressingLocator();
    TestPortType testService = null;
    EndpointReferenceType testServiceEPR = null;
    public static int TEST_ITERATIONS = 10;
    public static int MESSAGE_SIZE_ITERATIONS = 30;

    static String mechanism = null;
    static Object protection = null;

    public static final String INSECURE_SERVICE_PATH =
        "PerformanceTestService";

    public static final String SECURE_SERVICE_PATH =
        "SecurePerformanceTestService";

    static String servicePath = INSECURE_SERVICE_PATH;

    public BasicPerformanceTests(String name) {
        super(name);
    }

    public static void setSecurityReq(String securityType, Object protType) {

        mechanism = securityType;
        if (mechanism != null) {
            servicePath = SECURE_SERVICE_PATH;
        } else {
            servicePath = INSECURE_SERVICE_PATH;
        }
        protection = protType;
        System.out.println("Security machanism: " + mechanism
                           + " with protection: " + protType);
    }

    public void testBasicPerformance() throws Exception {

        long time1;
        long time2;
        long accumulator;

        for(int i = 1; i < MESSAGE_SIZE_ITERATIONS ; i++) {

            BaseType message = createInputMessage(i);
            accumulator = 0;
            for(int j = 0; j < TEST_ITERATIONS; j++) {

                time1 = System.currentTimeMillis();
                testService.baseline(message);
                time2 = System.currentTimeMillis();
                accumulator += time2 - time1;
            }
            System.out.println("Base: " +
                               String.valueOf(accumulator/TEST_ITERATIONS));
        }
    }

    public void testDispatchPerformance() throws Exception {

        long time1;
        long time2;
        long accumulator;

        for(int i = 1; i < MESSAGE_SIZE_ITERATIONS; i++) {

            BaseType message = createInputMessage(i);
            accumulator = 0;
            for(int j = 0; j < TEST_ITERATIONS; j++) {

                time1 = System.currentTimeMillis();
                testService.resourceDispatch(message);
                time2 = System.currentTimeMillis();
                accumulator += time2 - time1;
            }
            System.out.println("Dispatch: " +
                               String.valueOf(accumulator/TEST_ITERATIONS));
        }
    }

    protected void setUp() throws Exception {

        super.setUp();
        testService = locator.getTestPortTypePort(getServiceAddress());

        if (mechanism != null) {
            ((Stub)testService)._setProperty(mechanism, protection);
            ((Stub)testService)._setProperty(Constants.AUTHORIZATION,
                                             SelfAuthorization.getInstance());

            if ((mechanism.equals(Constants.GSI_SEC_MSG)) &&
                (protection.equals(Constants.ENCRYPTION))) {
                GlobusCredential cred =
                    GlobusCredential.getDefaultCredential();
                X509Certificate array[] = cred.getCertificateChain();
                EncryptionCredentials encryptionCreds =
                    new EncryptionCredentials(array);
                Subject subject = new Subject();
                subject.getPublicCredentials().add(encryptionCreds);
                ((Stub)testService)._setProperty(Constants.PEER_SUBJECT,
                                                 subject);
            }
        }

        //Warmup
        for(int i = 0; i < 5; i++) {
            testService.baseline(createInputMessage(i));
        }

        for(int i = 0; i < 5; i++) {
            testService.resourceDispatch(createInputMessage(i));
        }
    }

    //Todo: Do I need to randomize strings here?
    public static BaseType createInputMessage(int count)
        throws Exception {

        BaseType message = new BaseType();
        message.setInitialTerminationTime(Calendar.getInstance());
        message.setJob(new BaseTypeJob[count]);
        for(int i = 0; i < count; i++) {
            message.setJob(i, createJobDescritption());
        }
        message.setJobID(new AttributedURI(
            "uuid:" + UUIDGenFactory.getUUIDGen().nextUUID()));
        message.setSubscribe(createSubscribe());
        return message;
    }

    private static BaseTypeSubscribe createSubscribe()
        throws Exception {

        BaseTypeSubscribe subscribe =
            new BaseTypeSubscribe();
        NotificationConsumerManager manager =
            NotificationConsumerManager.getInstance();
        manager.startListening();
        subscribe.setConsumerReference(manager.createNotificationConsumer());
        subscribe.setInitialTerminationTime(Calendar.getInstance());
        subscribe.setUseNotify(Boolean.TRUE);
        TopicExpressionType topicExpression = new TopicExpressionType();
        topicExpression.setDialect(WSNConstants.SIMPLE_TOPIC_DIALECT);
        topicExpression.setValue(WSNConstants.TOPIC_EXPRESSION_DIALECTS);
        subscribe.setTopicExpression(topicExpression);
        return subscribe;
    }

    public static BaseTypeJob createJobDescritption() {

        BaseTypeJob jobDescription = new BaseTypeJob();
        jobDescription.setArgument(
            new String[] {"12", "abc", "34",
                          "pdscaex_instr_GrADS_grads23_28919.cfg",
                          "pgwynnel was here"});
        jobDescription.setDirectory("${GLOBUS_USER_HOME}");
        jobDescription.setExecutable("/bin/echo");
        NameValuePairType[] environment = new NameValuePairType[]
            {new NameValuePairType(), new NameValuePairType()};
        environment[0].setName("PI");
        environment[0].setValue("3.1415");
        environment[1].setName("GLOBUS_DUROC_SUBJOB_INDEX");
        environment[1].setValue("0");
        jobDescription.setEnvironment(environment);
        jobDescription.setStdout("${GLOBUS_USER_HOME}/stdout");
        jobDescription.setStderr("${GLOBUS_USER_HOME}/stderr");
        DeleteRequestType deleteRequest = new DeleteRequestType();
        DeleteType[] deletion = new DeleteType[] {new DeleteType()};
        deletion[0].setFile("file://${GLOBUS_USER_HOME}/stdout");
        deleteRequest.setDeletion(deletion);
        jobDescription.setFileCleanUp(deleteRequest);
        TransferRequestType stageInRequest = new TransferRequestType();
        TransferType[] stageIn = new TransferType[] {new TransferType()};
        stageIn[0].setSourceUrl(
            "gsiftp://localhost:2811/${GLOBUS_USER_HOME}/stdin");
        stageIn[0].setDestinationUrl("file:///tmp/stdin");
        stageInRequest.setTransfer(stageIn);
        jobDescription.setFileStageIn(stageInRequest);
        TransferRequestType stageOutRequest = new TransferRequestType();
        TransferType[] stageOut = new TransferType[] {new TransferType()};
        stageOut[0].setSourceUrl("file://${GLOBUS_USER_HOME}/stdout");
        stageOut[0].setDestinationUrl("gsiftp://localhost:2811/tmp/stdout");
        stageOutRequest.setTransfer(stageOut);
        jobDescription.setFileStageIn(stageOutRequest);
        return jobDescription;
    }

    public EndpointReferenceType getServiceAddress() throws Exception {

        if(this.testServiceEPR == null) {
            String address =
                TEST_CONTAINER.getBaseURL() + servicePath;
            this.testServiceEPR =
                new EndpointReferenceType(new URI(address));
            ReferencePropertiesType props = new ReferencePropertiesType();
            props.add(PerformanceTestHome.TEST_KEY.toSOAPElement());
            this.testServiceEPR.setProperties(props);
        }
        return this.testServiceEPR;
    }
}
