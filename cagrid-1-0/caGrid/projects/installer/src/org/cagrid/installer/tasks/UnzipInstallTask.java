/**
 * 
 */
package org.cagrid.installer.tasks;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.steps.Constants;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class UnzipInstallTask extends BasicTask {

	private static final int BUFFER_SIZE = 1024;

	private String tempFileNameProp;

	private String installDirPathProp;

	private String dirNameProp;

	private String homeProp;

	private static final Log logger = LogFactory.getLog(UnzipInstallTask.class);

	public UnzipInstallTask(String name, String description,
			String tempFileNameProp, String installDirPathProp,
			String dirNameProp, String homeProp) {
		super(name, description);
		this.tempFileNameProp = tempFileNameProp;
		this.installDirPathProp = installDirPathProp;
		this.dirNameProp = dirNameProp;
		this.homeProp = homeProp;
	}

	protected Object internalExecute(Map state) throws Exception {

		ZipFile zipFile = null;
		try {
			String path = state.get(Constants.TEMP_DIR_PATH) + "/"
					+ state.get(this.tempFileNameProp);

			logger.info("Trying to open ZipFile for " + path);
			zipFile = new ZipFile(new File(path));
		} catch (Exception ex) {
			throw new RuntimeException("Error instantiating zip file: "
					+ ex.getMessage(), ex);
		}
		// setStepCount(zipFile.size());

		File installDir = new File((String) state.get(this.installDirPathProp));
		File home = new File(installDir.getAbsolutePath() + "/"
				+ state.get(this.dirNameProp));

		// TODO: change this. this strays from the norm. usually steps modify
		// state
		// while tasks do not.
		state.put(this.homeProp, home.getAbsolutePath());

		home.delete();

		String baseOut = installDir.getAbsolutePath() + "/";
		Enumeration entries = zipFile.entries();
		int subTaskNum = 0;
		int logAfterSize = 100;
		int nextLog = -1;
		int numFiles = 0;
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String fileName = baseOut + entry.getName();
			File file = new File(fileName);

			if (entry.isDirectory()) {
				file.mkdirs();
			} else {
				numFiles++;
				if (numFiles > nextLog) {
					nextLog += logAfterSize;
					System.out.println("Extracting: " + fileName);
				}
				BufferedOutputStream out = null;
				InputStream in = zipFile.getInputStream(entry);
				try {
					if (!file.getParentFile().exists()) {
						// createDir(file.getParentFile());
						file.getParentFile().mkdirs();
					}
					file.createNewFile();
					out = new BufferedOutputStream(new FileOutputStream(file));

				} catch (Exception ex) {
					String msg = "Error creating output stream for '"
							+ file.getAbsolutePath() + "': " + ex.getMessage();
					logger.error(msg, ex);
					throw new RuntimeException(msg, ex);
				}
				byte[] buffer = new byte[BUFFER_SIZE];
				int len = -1;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
					buffer = new byte[BUFFER_SIZE];
				}
				out.flush();
				out.close();
				in.close();
			}
			// setLastStep(getLastStep() + 1);
		}
		zipFile.close();

		return null;
	}

	private void createDir(File dir) {
		if (dir == null) {
			throw new IllegalArgumentException("dir is null");
		}
		logger.debug("Checking if " + dir.getAbsolutePath() + " exists");
		if (!dir.exists()) {
			logger.debug("It doesn't. Recursing.");
			createDir(dir.getParentFile());
		}
		logger.debug("Creating " + dir.getAbsolutePath());
		dir.mkdir();
	}

}
