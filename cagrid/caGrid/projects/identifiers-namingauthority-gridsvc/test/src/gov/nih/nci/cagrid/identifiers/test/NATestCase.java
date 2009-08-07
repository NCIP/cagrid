package gov.nih.nci.cagrid.identifiers.test;

import gov.nih.nci.cagrid.identifiers.client.IdentifiersNAServiceClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;

public class NATestCase extends TestCase {
    private static Log log = LogFactory.getLog(NATestCase.class);
    
	public void testNamingAuthority() throws Exception {
		String gridSvcUrl = "http://localhost:8081/wsrf/services/cagrid/IdentifiersNAService";
		IdentifiersNAServiceClient client = new IdentifiersNAServiceClient( gridSvcUrl );
		
		// create identifier
		
		// get values
		// gov.nih.nci.cagrid.identifiers.common.MappingUtil.toIdentifierValues(
		//		client.getTypeValues(identifier) );
	}
	

   public static void main(String[] args) {
      junit.textui.TestRunner.run(NATestCase.class);
   }
}

