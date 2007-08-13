/**
 * 
 */
package org.cagrid.installer.util;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.steps.Constants;
import org.w3c.dom.Node;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class InstallerUtils {

	private static final Log logger = LogFactory.getLog(InstallerUtils.class);
	
	public InstallerUtils() {

	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().indexOf("windows") != -1;
	}

	public static void showError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void unzipFile(File toFile, File toDir) throws Exception {
		String baseOut = toDir.getAbsolutePath() + "/";
		ZipFile zipFile = new ZipFile(toFile);
		Enumeration entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			if (entry.isDirectory()) {
				File dir = new File(baseOut + entry.getName());
				if (!dir.exists()) {
					dir.mkdirs();
				}
				continue;
			}

			InputStream in = zipFile.getInputStream(entry);
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(baseOut + entry.getName()));
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
				buffer = new byte[1024];
			}
			out.flush();
			out.close();
			in.close();
		}
		zipFile.close();
	}

	public static void addToClassPath(String s) throws IOException {
		File f = new File(s);
		addToClassPath(f);
	}

	public static void addToClassPath(File f) throws IOException {
		addToClassPath(f.toURL());
	}

	public static void addToClassPath(URL u) throws IOException {

		URLClassLoader sysloader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;

		try {
			Method method = sysclass.getDeclaredMethod("addURL",
					new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { u });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException(
					"Error, could not add URL to system classloader");
		}

	}

	public static boolean checkGenerateCA(CaGridInstallerModel model) {
		return model.isTrue(Constants.USE_SECURE_CONTAINER)
				&& !model.isTrue(Constants.SERVICE_CERT_PRESENT)
				&& !model.isTrue(Constants.CA_CERT_PRESENT);
	}

	public static void copyCACertToTrustStore(String certPath)
			throws IOException {
		copyCACertToTrustStore(certPath, "CA.0");
	}

	public static void copyCACertToTrustStore(String certPath, String caFileName)
			throws IOException {

		File trustDir = new File(System.getProperty("user.home")
				+ "/.globus/certificates");
		copyFile(certPath, trustDir.getAbsolutePath() + "/" + caFileName);

	}

	public static void copyFile(String from, String to) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(from));
		File toFile = new File(to);
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(toFile));
		String line = null;
		while ((line = in.readLine()) != null) {
			out.write(line + "\n");
		}
		in.close();
		out.flush();
		out.close();
	}

	public static String getScriptsBuildFilePath() {
		return new File("scripts/build.xml").getAbsolutePath();
	}

	public static String toString(Node node) throws Exception {
		StringWriter w = new StringWriter();
		Source s = new DOMSource(node);
		Result r = new StreamResult(w);
		Transformer t = TransformerFactory.newInstance().newTransformer();
		// t.setOutputProperty("omit-xml-declaration", "yes");
		t.setOutputProperty("indent", "yes");
		t.transform(s, r);
		return w.getBuffer().toString();
	}

	public static String getInstallerTempDir() {
		return getInstallerDir() + "/tmp";

	}

	public static String getInstallerDir() {
		return System.getProperty("user.home") + "/.cagrid/installer";
	}

	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	public static void setUpCellRenderer(JTable table) {
		DefaultTableCellRenderer r = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component renderer = super.getTableCellRendererComponent(table,
						value, isSelected, hasFocus, row, column);
				setBorder(BorderFactory.createEtchedBorder());
				return renderer;
			}
		};
		int colCount = table.getColumnCount();
		for (int i = 0; i < colCount; i++) {
			TableColumn col = table.getColumnModel().getColumn(i);
			col.setCellRenderer(r);
		}
	}

	public static GridBagConstraints getGridBagConstraints(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.gridy = y;
		return gbc;
	}

	public static String getDbNameFromJdbcUrl(String jdbcUrl) {
		return jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1);
	}

	public static String getJdbcBaseFromJdbcUrl(String jdbcUrl) {
		return jdbcUrl.substring(0, jdbcUrl.lastIndexOf("/"));
	}

	public static boolean checkCaGridVersion(String home) {
		boolean isCorrectVersion = false;
		try {
			File propsFile = new File(home
					+ "/share/resources/cagrid.properties");
			if (propsFile.exists()) {

				Properties props = new Properties();
				props.load(new FileInputStream(propsFile));

				if (Constants.CAGRID_VERSION.equals(props
						.getProperty("cagrid.master.project.version"))) {
					isCorrectVersion = true;
				}
			}
		} catch (Exception ex) {
			logger.debug("Error checking caGrid version: " + ex.getMessage(), ex);
		}
		return isCorrectVersion;
	}
	
	
	public static boolean checkTomcatVersion(String home) {
		boolean correctVersion = false;
		try {
			String[] envp = new String[] { "JAVA_HOME="
					+ System.getProperty("java.home") };

			String antHome = System.getenv("CATALINA_HOME");
			String[] cmd = null;
			if (InstallerUtils.isWindows()) {
				cmd = new String[] { antHome + "/bin/version.bat" };
			} else {
				cmd = new String[] { "sh", antHome + "/bin/version.sh" };
			}
			Process p = Runtime.getRuntime().exec(cmd, envp);
			StringBuffer stdout = new StringBuffer();
			new IOThread(p.getInputStream(), System.out, stdout).start();
			p.waitFor();
			correctVersion = stdout.toString().indexOf("Apache Tomcat/5.0.28") != -1;
		} catch (Exception ex) {
			logger
					.warn("Error checking Tomcat version: " + ex.getMessage(),
							ex);
		}
		return correctVersion;

	}	
	
	public static boolean checkGlobusVersion(String home) {
		return home.indexOf("4.0.3") != -1;
	}



	public static boolean checkAntVersion(String home) {
		boolean correctVersion = false;
		try {
			String[] envp = new String[] { "JAVA_HOME="
					+ System.getProperty("java.home") };

			String antHome = System.getenv("ANT_HOME");

			String[] cmd = null;
			if (InstallerUtils.isWindows()) {
				cmd = new String[] { antHome + "/bin/ant.bat", "-version" };
			} else {
				cmd = new String[] { "sh", antHome + "/bin/ant", "-version" };
			}

			Process p = Runtime.getRuntime().exec(cmd, envp);
			StringBuffer stdout = new StringBuffer();
			new IOThread(p.getInputStream(), System.out, stdout).start();
			p.waitFor();
			correctVersion = stdout.toString().indexOf(
					"Apache Ant version 1.6.5") != -1;
		} catch (Exception ex) {
			logger.warn("Error checking Ant version: " + ex.getMessage(), ex);
		}
		return correctVersion;
	}

	public static boolean checkActiveBPELVersion(String home) {
		//TODO: improve this check
		return new File(home).exists();
	}	
	
}
