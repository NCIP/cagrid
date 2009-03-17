package gov.nih.nci.cagrid.portal;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class PortalTestUtils {

    public static Long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Will create a string of specified length
     * @param length in bytes
     * @return
     */
    public static String createReallyLongString(int length){
        byte[] _arr = new byte[length];
        return new String(_arr);

    }
}
