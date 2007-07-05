import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 
 */

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class TestClassLoading {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String url = "/Users/joshua/dev3/cagrid_head/caGrid/projects/dorian/lib/mysql-connector-java-3.0.16-ga-bin.jar";
		String className = "com.mysql.jdbc.Driver";
		addFile(url);
		Class klass = Class.forName(className);
		Method[] methods = klass.getMethods();
		for(int i = 0; i < methods.length; i++){
			System.out.println(methods[i].getName());
		}
	}

	private static final Class[] parameters = new Class[] { URL.class };

	public static void addFile(String s) throws IOException {
		File f = new File(s);
		addFile(f);
	}// end method

	public static void addFile(File f) throws IOException {
		addURL(f.toURL());
	}// end method

	public static void addURL(URL u) throws IOException {

		URLClassLoader sysloader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;

		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { u });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException(
					"Error, could not add URL to system classloader");
		}// end try catch

	}// end method

}
