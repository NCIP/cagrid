package org.cagrid.introduce.test.system.steps;

import java.io.File;
import java.util.List;

import gov.nih.nci.cagrid.introduce.common.AntTools;
import gov.nih.nci.cagrid.introduce.common.CommonTools;
import gov.nih.nci.cagrid.introduce.test.TestCaseInfo;
import gov.nih.nci.cagrid.introduce.test.steps.BaseStep;
import gov.nih.nci.cagrid.testing.system.deployment.ServiceContainer;


public class InvokeClientStep extends BaseStep {
    public static final String TEST_URL_SUFFIX = "/wsrf/services/cagrid/";

    private TestCaseInfo tci;
    private String methodName;
    private ServiceContainer container;


    public InvokeClientStep(ServiceContainer container, TestCaseInfo tci) throws Exception {
        super(tci.getDir(), false);
        this.tci = tci;
        this.container = container;
    }


    public void runStep() throws Throwable {
        System.out.println("Invoking a simple methods implementation.");

        List<String> cmd = AntTools.getAntCommand("runClient", tci.getDir());
        String urlArg = "-Dservice.url=";
        if (container.getProperties().isSecure()) {
            urlArg += "https://";
            String certdir = "-DX509_CERT_DIR=" + container.getProperties().getContainerDirectory().getAbsolutePath() + File.separator + "certificates" + File.separator + "ca";
            cmd.add(certdir);
        } else {
            urlArg += "http://";
        }
        urlArg += "localhost:" + container.getProperties().getPortPreference().getPort() + TEST_URL_SUFFIX + tci.getName();
        cmd.add(urlArg);

        Process p = CommonTools.createAndOutputProcess(cmd);
        p.waitFor();

        assertTrue("ant runClient did not successfully complete!", p.exitValue() == 0);
    }

}
