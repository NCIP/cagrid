package gov.nih.nci.cagrid.introduce.upgrade.introduce;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.introduce.IntroduceConstants;
import gov.nih.nci.cagrid.introduce.beans.ServiceDescription;
import gov.nih.nci.cagrid.introduce.info.ServiceInformation;
import gov.nih.nci.cagrid.introduce.templates.etc.RegistrationTemplate;
import gov.nih.nci.cagrid.introduce.upgrade.IntroduceUpgraderBase;

public class Introduce_1_0__1_1_Upgrader extends IntroduceUpgraderBase {

	public Introduce_1_0__1_1_Upgrader(ServiceDescription sd, String servicePath) {
		super(sd, servicePath, "1.0", "1.1");
	}

	protected void upgrade() throws Exception {
		// need to replace the build.xml
		Utils.copyFile(new File("." + File.separator + "skeleton"
				+ File.separator + "build.xml"), new File(getServicePath()
				+ File.separator + "build.xml"));

		// need to replace the build-deploy.xml
		Utils.copyFile(new File("." + File.separator + "skeleton"
				+ File.separator + "build-deploy.xml"), new File(
				getServicePath() + File.separator + "build-deploy.xml"));

		// need to replace the registration.xml
		Properties serviceProperties = new Properties();
		serviceProperties.load(new FileReader(
				new File(getServicePath() + File.separator
						+ IntroduceConstants.INTRODUCE_PROPERTIES_FILE)));
		ServiceInformation info = new ServiceInformation(
				getServiceDescription(), serviceProperties, new File(
						getServicePath()));

		RegistrationTemplate registrationT = new RegistrationTemplate();
		String registrationS = registrationT.generate(info);
		File registrationF = new File(getServicePath() + File.separator + "etc"
				+ File.separator + "registration.xml");
		FileWriter registrationFW = new FileWriter(registrationF);
		registrationFW.write(registrationS);
		registrationFW.close();

		// need to add to the deploy.properties
		Properties deployProperties = new Properties();
		deployProperties.load(new FileReader(new File(getServicePath()
				+ File.separator + IntroduceConstants.DEPLOY_PROPERTIES_FILE)));
		deployProperties.put("index.service.registration.refresh_seconds",
				"600");
		deployProperties.put("index.service.index.refresh_milliseconds",
				"30000");
		deployProperties.store(new FileOutputStream(new File(getServicePath()
				+ File.separator + IntroduceConstants.DEPLOY_PROPERTIES_FILE)),
				"Service Deployment Properties");

		// need to add the soapFix.jar to the lib directory
		Utils.copyFile(new File("." + File.separator + "skeleton"
				+ File.separator + "lib" + File.separator
				+ "caGrid-1.0-Introduce-1.1-soapBindingFix.jar"), new File(
				getServicePath() + File.separator + "lib" + File.separator
						+ "caGrid-1.0-Introduce-1.1-soapBindingFix.jar"));
	}

}
