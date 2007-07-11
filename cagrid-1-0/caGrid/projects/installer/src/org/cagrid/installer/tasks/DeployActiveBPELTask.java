/**
 * 
 */
package org.cagrid.installer.tasks;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.steps.Constants;
import org.cagrid.installer.util.IOThread;
import org.cagrid.installer.util.InstallerUtils;
/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class DeployActiveBPELTask extends BasicTask {

	private static final Log logger = LogFactory.getLog(DeployActiveBPELTask.class);
	
	

	

	public DeployActiveBPELTask(String name, String description) {
		super(name, description);
		
	}

	protected Object internalExecute(Map state) throws Exception {
		

		
		String baseDir = InstallerUtils.getRequiredProperty(state,Constants.ACTIVEBPEL_HOME);
		try {
	
			File activebpelHome = new File(baseDir);
			if (!activebpelHome.exists()) {
				throw new RuntimeException("ActiveBPEL directory doesnt exist."
						+ baseDir);
			}
			runCommand(activebpelHome);
			
		} catch (Exception ex) {
			throw new RuntimeException("Error encountered: " + ex.getMessage(),
					ex);
		}
		return null;
	}

	protected void runCommand(File dir) throws IOException, InterruptedException {


		boolean isWindows = false;
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
			isWindows = true;
		}
		//build command
		String cmd = "install";
		if(isWindows){
			cmd += ".bat";
		}else{
			cmd = "./"+cmd+".sh";
		}

		logger.debug("########## Executing: " + cmd);

		// run ant
		Process p = Runtime.getRuntime().exec(cmd, null,dir);
		// track stdout and stderr
		StringBuffer stdout = new StringBuffer();
		StringBuffer stderr = new StringBuffer();
		new IOThread(p.getInputStream(), System.out, stdout).start();
		new IOThread(p.getErrorStream(), System.err, stderr).start();

		// wait and return
		int result = p.waitFor();
		if (stdout.indexOf("BUILD FAILED") != -1
				|| stderr.indexOf("BUILD FAILED") != -1
				|| stdout.indexOf("Build failed") != -1
				|| stderr.indexOf("Build failed") != -1) {
			// System.err.println(stderr);
			System.out.println(stdout);
			System.out.println(stderr);
			throw new IOException("command '" + cmd+ "' failed");
		}
	}

}
