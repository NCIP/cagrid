package gov.nih.nci.cagrid.bdt.test.steps;

import gov.nih.nci.cagrid.bdt.test.unit.CreationTest;
import gov.nih.nci.cagrid.common.Utils;

import java.io.File;

import com.atomicobject.haste.framework.Step;

/** 
 *  DeleteOldServiceStep
 *  TODO:DOCUMENT ME
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * 
 * @created Aug 22, 2006 
 * @version $Id: DeleteOldServiceStep.java,v 1.1 2007-03-20 18:44:46 hastings Exp $ 
 */
public class DeleteOldServiceStep extends Step {
	
	public DeleteOldServiceStep() {
		super();
	}
	

	public void runStep() throws Throwable {
		File oldServiceDir = new File(CreationTest.SERVICE_DIR);
		if (oldServiceDir.exists()) {
			boolean deleted = Utils.deleteDir(oldServiceDir);
			assertTrue("Failed to delete directory: " + oldServiceDir.getAbsolutePath(), deleted);
		} else {
			System.out.println("Service dir " + oldServiceDir.getAbsolutePath() + " did not exist...");
		}
	}

}
