/**
 * 
 */
package org.cagrid.installer.tasks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
	private Map<String, String> environment;

	

	public DeployActiveBPELTask(String name, String description) {
		super(name, description);
		this.environment = new HashMap<String,String>();
		
	}

	protected Object internalExecute(Map state) throws Exception {
		
		
		
		Map<String, String> env = new HashMap<String, String>(System
				.getenv());
		for (Iterator i = this.environment.entrySet().iterator(); i
				.hasNext();) {
			Entry entry = (Entry) i.next();
			if (entry.getKey() instanceof String
					&& entry.getValue() instanceof String) {
				env.put((String) entry.getKey(), (String) entry.getValue());
			}
		}
		env.put((String)"GLOBUS_LOCATION", (String)state.get(Constants.GLOBUS_HOME));
		env.put((String)"CATALINA_HOME", (String)state.get(Constants.TOMCAT_HOME));
		Map<String,String> myEnv = new HashMap<String,String>(env);
	
		String[] envp = new String[myEnv.size()];
		int i = 0;
		for (String key : myEnv.keySet()) {
			envp[i++] = key + "=" + myEnv.get(key);
		}

						
		
		String baseDir = InstallerUtils.getRequiredProperty(state,Constants.ACTIVEBPEL_HOME);
		try {
			if(baseDir==null){
				throw new RuntimeException("ACTIVEBPEL_HOME not set...");
			}
			File activebpelHome = new File(baseDir);
			if (!activebpelHome.exists()) {
				throw new RuntimeException("ActiveBPEL directory doesnt exist."
						+ baseDir);
			}
			runCommand(activebpelHome,envp);
			
		} catch (Exception ex) {
			throw new RuntimeException("Error encountered: " + ex.getMessage(),
					ex);
		}
		return null;
	}

	protected void runCommand(File dir, String[] envp) throws IOException, InterruptedException {


		boolean isWindows = false;
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
			isWindows = true;
		}
		//build command
		String[] cmd = new String[4];
		
		if(isWindows){
			cmd[0]="cmd.exe" ;
			
			cmd[1]="/C";
			cmd[2]="start";
			cmd[3]="install.bat";
			
		}else{
			cmd[0] = "./"+cmd+".sh";
		}

		logger.debug("########## Executing: " + cmd);

		// run ant
		Process p = Runtime.getRuntime().exec(cmd, envp,dir);
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
