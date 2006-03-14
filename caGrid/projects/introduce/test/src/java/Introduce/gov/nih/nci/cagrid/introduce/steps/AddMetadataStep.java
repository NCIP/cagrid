package gov.nih.nci.cagrid.introduce.steps;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.introduce.TestCaseInfo;
import gov.nih.nci.cagrid.introduce.beans.ServiceDescription;
import gov.nih.nci.cagrid.introduce.beans.metadata.MetadataListType;
import gov.nih.nci.cagrid.introduce.beans.metadata.MetadataType;
import gov.nih.nci.cagrid.introduce.codegen.SyncTools;
import gov.nih.nci.cagrid.introduce.common.CommonTools;

import java.io.File;

import javax.xml.namespace.QName;

import com.atomicobject.haste.framework.Step;


public class AddMetadataStep extends Step {
	private TestCaseInfo tci;


	public AddMetadataStep(TestCaseInfo tci) {
		this.tci = tci;
	}


	public void runStep() throws Throwable {
		System.out.println("Adding metadata.");

		String pathtobasedir = System.getProperty("basedir");
		System.out.println(pathtobasedir);
		if (pathtobasedir == null) {
			System.err.println("basedir system property not set");
			throw new Exception("basedir system property not set");
		}

		ServiceDescription introService = (ServiceDescription) Utils.deserializeDocument(pathtobasedir + File.separator
			+ tci.getDir() + File.separator + "introduce.xml", ServiceDescription.class);
		MetadataListType metadatasType = introService.getMetadataList();

		MetadataType metadata = new MetadataType();
		// copy over the bookstore schema to be used with the test
		Utils.copyFile(new File(pathtobasedir + File.separator + TestCaseInfo.GOLD_SCHEMA_DIR + File.separator
			+ "caDSRMetadata.xsd"), new File(pathtobasedir + File.separator + tci.getDir() + File.separator + "schema"
			+ File.separator + tci.getName() + File.separator + "caDSRMetadata.xsd"));

		metadata.setLocation("./caDSRMetadata.xsd");
		metadata.setNamespace("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata.cadsr");
		metadata.setPopulateFromFile(false);
		metadata.setRegister(true);
		metadata.setType("caDSRMetadata");
		metadata.setQName(new QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata.cadsr", "caDSRMetadata"));

		// add new metadata to array in bean
		// this seems to be a wierd way be adding things....
		MetadataType[] newMetadatas;
		int newLength = 0;
		if (metadatasType.getMetadata() != null) {
			newLength = metadatasType.getMetadata().length + 1;
			newMetadatas = new MetadataType[newLength];
			System.arraycopy(metadatasType.getMetadata(), 0, newMetadatas, 0, metadatasType.getMetadata().length);
		} else {
			newLength = 1;
			newMetadatas = new MetadataType[newLength];
		}
		newMetadatas[newLength - 1] = metadata;
		metadatasType.setMetadata(newMetadatas);

		Utils.serializeDocument(pathtobasedir + File.separator + tci.getDir() + File.separator + "introduce.xml",
			introService, new QName("gme://gov.nih.nci.cagrid/1/Introduce", "ServiceSkeleton"));

		try {
			SyncTools sync = new SyncTools(new File(pathtobasedir + File.separator + tci.getDir()));
			sync.sync();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		// look at the interface to make sure method from file does not
		// exists.......
		String serviceInterface = pathtobasedir + File.separator + tci.dir + File.separator + "src" + File.separator
			+ tci.getPackageDir() + "/service/" + File.separator + "globus" + File.separator + "resource"
			+ File.separator + "BaseResource.java";
		assertFalse("Checking that BaseResource contains the load method", StepTools.methodExists(serviceInterface,
			"loadAnalyticalServiceMetadataFromFile"));

		// build the service
		String cmd = CommonTools.getAntAllCommand(pathtobasedir + File.separator + tci.getDir());

		Process p = CommonTools.createAndOutputProcess(cmd);
		p.waitFor();
		assertEquals("Checking build status", 0, p.exitValue());
	}

}
