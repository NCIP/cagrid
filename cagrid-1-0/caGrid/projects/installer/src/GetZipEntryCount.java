import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipFile;

/**
 * 
 */

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class GetZipEntryCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String zipFilePath = "/Users/joshua/packages/tomcat/jakarta-tomcat-5.0.28/webapps/downloads/caGrid.zip";
		ZipFile zipFile = new ZipFile(new File(zipFilePath));
		int count = 0;
		Enumeration e = zipFile.entries();
		while(e.hasMoreElements()){
			e.nextElement();
			count++;
		}
		System.out.println(count);
	}

}
