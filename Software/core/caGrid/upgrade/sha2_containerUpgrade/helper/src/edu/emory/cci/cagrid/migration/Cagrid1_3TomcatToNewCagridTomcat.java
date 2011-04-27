package edu.emory.cci.cagrid.migration;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Copy jars and the service directory for a deployed CaGrid service from the
 * old tomcat container to a new tomcat container, substituting newer caGrid
 * jars as seems appropriate. Also update the service's introduceDeployment.xml
 * file to reflect changes in file names.
 * 
 * @author Mark Grand
 */
public class Cagrid1_3TomcatToNewCagridTomcat {
	private static final String NEW_CAGRID_VERSION = "1.4.1";

	private File newServiceDir;
	private File newLibDir;

	/**
	 * Constructor
	 * 
	 * @param oldServiceDir
	 * @param newTomcatDir
	 */
	public Cagrid1_3TomcatToNewCagridTomcat(File oldServiceDir,
			File newTomcatDir) {
		super();
		String serviceDirName = oldServiceDir.getName();
		File webappsDir = new File(newTomcatDir, "webapps");
		File wsrfDir = new File(webappsDir, "wsrf");
		File webinfDir = new File(wsrfDir, "WEB-INF");
		newLibDir = new File(webinfDir, "lib");
		File etcDir = new File(wsrfDir, "etc");
		newServiceDir = new File(etcDir, serviceDirName);
	}

	/**
	 * @param args
	 *            A two element array containing the path of the service
	 *            directory in the old tomcat container and the path of the new
	 *            tomcat container.
	 */
	public static void main(String[] args) {
		try {
			if (args.length != 2) {
				String msg = "The program requires two command-line arguments:"
						+ " the path of the old tomcat's service directory and the path of the new tomcat container";
				System.err.println(msg);
				System.exit(1);
			}
			File oldServiceDir = new File(args[0]);
			ensureIsDirectory(oldServiceDir);
			File newTomcatDir = new File(args[1]);
			ensureIsDirectory(newTomcatDir);
			Cagrid1_3TomcatToNewCagridTomcat instance = new Cagrid1_3TomcatToNewCagridTomcat(
					oldServiceDir, newTomcatDir);
			instance.copyIntroduceJarFile();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(99);
		}
	}

	/**
	 * Make a modified copy of introduceDeployment.xml. For each .jar file
	 * listed, a determination is made if it identified .jar file has been
	 * superseded in the new version of caGrid.
	 * <p>
	 * If the named jar file has been superseded, then the superseding file is
	 * copied from the CaGrid repository to the ../lib directory and then name
	 * of the superseding jar file replaces the name of the original in the
	 * introduceDeployment.xml file.
	 * <p>
	 * If the named jar file has NOT been superseded, then the named file is
	 * copied from the old container's lib directory to the new container's lib
	 * directory. The original name is kept in the introduceDeployment.xml file.
	 * 
	 * @throws Exception
	 *             if there is a problem
	 */
	private void copyIntroduceJarFile() throws Exception {
		File introduceDeployment = new File(newServiceDir,
				"introduceDeployment.xml");
		ensureIsFile(introduceDeployment);
		makeBackupCopy(introduceDeployment, ".original");
		File newIntroduceDeployment = new File(newServiceDir,
				"introduceDeployment.xml.new");

		InputStream in = null;
		OutputStream out = null;
		try {
			in = FileUtils.openInputStream(introduceDeployment);
			in = IOUtils.toBufferedInputStream(in);
			out = new BufferedOutputStream(
					FileUtils.openOutputStream(newIntroduceDeployment));
			copyIntroduceJarStream(in, out);
		} catch (Exception e) {
			FileUtils.deleteQuietly(newIntroduceDeployment);
			throw e;
		} finally {
			if (in != null) {
				IOUtils.closeQuietly(in);
			}
			if (out != null) {
				IOUtils.closeQuietly(out);
			}
		}
		FileUtils.moveFile(newIntroduceDeployment, introduceDeployment);
	}

	/**
	 * Copy of introduceDeployment.xml from the input stream to the output
	 * stream with some possible modifications. For each .jar file listed, a
	 * determination is made if it identified .jar file has been superseded in
	 * the new version of caGrid.
	 * <p>
	 * If the named jar file has been superseded, then the superseding file is
	 * copied from the CaGrid repository to the ../lib directory and then name
	 * of the superseding jar file replaces the name of the original in the
	 * introduceDeployment.xml file.
	 * <p>
	 * If the named jar file has NOT been superseded, then the named file is
	 * copied from the old container's lib directory to the new container's lib
	 * directory. The original name is kept in the introduceDeployment.xml file.
	 * 
	 * @param in
	 *            The input stream to read from.
	 * @param out
	 *            The output stream to write to.
	 * @throws IOException
	 *             If there is a problem
	 */
	private void copyIntroduceJarStream(InputStream in, OutputStream out)
			throws IOException {
		while (readPast(in, out, "name=\"")) {
			String jarFileName = readUpto(in, '"');
			// TODO Auto-generated method stub
		}
	}

	/**
	 * Read characters from the input stream up to, but not inlcuding the next
	 * instance of the given character.
	 * 
	 * @param in
	 *            the input stream to read from.
	 * @param target
	 *            the character to read up to.
	 * @return the read characters as a string.
	 * @throws IOException
	 *             if there is a problem.
	 */
	private String readUpto(InputStream in, char target) throws IOException {
		StringBuffer buffer = new StringBuffer();
		while (true) {
			int c = in.read();
			if (c == -1) {
				throw new IOException("Encountered end-of-file while reading a jar file name.");
			}
			if (c == target) {
				break;
			}
			buffer.append(target);
		}
		return buffer.toString();
	}

	/**
	 * Read character from then input stream and write the characters to the
	 * output stream until the most recently read characters match the target
	 * string.
	 * 
	 * @param in
	 *            The character stream to read from.
	 * @param out
	 *            The character stream to write to.
	 * @param target
	 *            the string to look for in the stream.
	 * @return true if this method returned because the most recently read
	 *         characters matched the target string. If this method stops
	 *         because the inputStream has seen end of file.
	 * @throws IOException
	 *             if there is a problem.
	 */
	private boolean readPast(InputStream in, OutputStream out, String target)
			throws IOException {
		int length = target.length();
		int[] buffer = new int[length];
		int firstBuffered = 0;
		int firstUnbuffered = 0;

		while (true) {
			int c = in.read();
			if (c == -1) {
				return false;
			}
			out.write(c);
			buffer[firstUnbuffered % length] = c;
			firstUnbuffered += 1;
			if (firstUnbuffered - firstBuffered > length) {
				firstBuffered = firstUnbuffered - length;
			}
			window: do {
				for (int i = 0; i < length; i++) {
					if (buffer[(firstBuffered + i) % length] != target
							.charAt(i)) {
						break window;
					}
				}
				return true;
			} while (false);
		}
	}

	private void ensureIsFile(File f) {
		if (!f.isFile()) {
			System.err.println(f.getAbsolutePath()
					+ " does not exist or is not a regular file.");
			System.exit(3);
		}
	}

	/**
	 * Make a backup copy of the given file. The name of the backup copy will be
	 * the name of the original followed by the given suffix.
	 * 
	 * @param f
	 *            The file to be copied
	 * @param suffix
	 *            the suffix to be appended to the file name.
	 * @throws IOException
	 *             if there is a problem.
	 */
	private void makeBackupCopy(File f, String suffix) throws IOException {
		File backup = new File(f.getParent(), f.getName() + suffix);
		FileUtils.copyFileToDirectory(f, backup, true);
	}

	private static void ensureIsDirectory(File dir) {
		if (!dir.isDirectory()) {
			String msg = dir.getAbsolutePath()
					+ " does not exist or is not a directory";
			System.err.println(msg);
			System.exit(2);
		}
	}

}
