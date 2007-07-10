/**
 * 
 */
package org.cagrid.installer.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cagrid.installer.model.CaGridInstallerModel;
import org.cagrid.installer.steps.Constants;
import org.w3c.dom.Node;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class InstallerUtils {

	/**
	 * 
	 */
	public InstallerUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isWindows(){
		return System.getProperty("os.name").toLowerCase().indexOf("windows") != -1;
	}
	
	public static String getRequiredProperty(Map state, String name) {
		String value = (String) state.get(name);
		if(value == null){
			throw new IllegalStateException("Required property '" + name + "' not found in state.");
		}
		return value;
	}

	public static void downloadFile(URL fromUrl, File toFile) throws Exception {

		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(toFile));
		InputStream in = fromUrl.openStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		out.flush();
		out.close();
		in.close();
	}

	public static void main(String[] args) throws Exception {

		URL url = new URL(
				"file:/Users/joshua/downloads/apache-ant-1.6.5-bin.zip");
		File file = new File("/Users/joshua/temp/ant.zip");
		downloadFile(url, file);

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
		return "true".equals(model.getState().get(
				Constants.USE_SECURE_CONTAINER))
				&& !"true".equals(model.getState().get(
						Constants.SERVICE_CERT_PRESENT))
				&& !"true".equals(model.getState().get(
						Constants.CA_CERT_PRESENT));
	}

	public static void copyCACertToTrustStore(String certPath)
			throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(certPath));
		File trustDir = new File(System.getProperty("user.home")
				+ "/.globus/certificates");
		if (!trustDir.exists()) {
			trustDir.mkdirs();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(trustDir
				.getAbsolutePath()
				+ "/CA.0"));
		String line = null;
		while ((line = in.readLine()) != null) {
			out.write(line + "\n");
		}
		in.close();
		out.flush();
		out.close();
	}

	public static String getServiceDestDir(Map state) {
		return state.get(Constants.TEMP_DIR_PATH) + "/services";
	}

	public static String getScriptsBuildFilePath() {
		return new File("scripts/build.xml").getAbsolutePath();
	}

	public static boolean isSecureContainerRequired(Map state) {
		return "true".equals(state.get(Constants.INSTALL_DORIAN))
				|| "true".equals(state.get(Constants.INSTALL_GTS))
				|| "true".equals(state.get(Constants.INSTALL_AUTHN_SVC))
				|| "true".equals(state.get(Constants.INSTALL_GRID_GROUPER))
				|| "true".equals(state.get(Constants.INSTALL_INDEX_SVC));
	}
	
	public static String toString(Node node) throws Exception {
		StringWriter w = new StringWriter();
		Source s = new DOMSource(node);
		Result r = new StreamResult(w);
		Transformer t = TransformerFactory.newInstance().newTransformer();
//		t.setOutputProperty("omit-xml-declaration", "yes");
		t.setOutputProperty("indent", "yes");
		t.transform(s, r);
		return w.getBuffer().toString();
	}
}
