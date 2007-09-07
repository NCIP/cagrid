package gov.nci.nih.cagrid.validator.steps.gme;

import java.io.File;
import java.util.List;

import org.projectmobius.common.MobiusException;
import org.projectmobius.common.Namespace;
import org.projectmobius.gme.XMLDataModelService;

/** 
 *  DomainsAndNamespacesStep
 *  Step to pull domains and namespaces from the GME
 * 
 * @author David Ervin
 * 
 * @created Aug 27, 2007 4:17:32 PM
 * @version $Id: DomainsAndNamespacesStep.java,v 1.3 2007-09-07 14:19:35 dervin Exp $ 
 */
public class DomainsAndNamespacesStep extends BaseGmeTestStep {

    public DomainsAndNamespacesStep(String serviceUrl, File tempDir) {
        super(serviceUrl, tempDir);
    }


    public void runStep() throws Throwable {
        XMLDataModelService gmeClient = getGmeHandle();
        
        // list out the namespace domains
        List<String> namespaceDomains = null;
        try {
            namespaceDomains = gmeClient.getNamespaceDomainList();
        } catch (MobiusException ex) {
            ex.printStackTrace();
            fail("Error loading namespace domains from GME " + getServiceUrl() 
                + " : " + ex.getMessage());
        }
        
        for (String domain : namespaceDomains) {
            // TODO: could validate the domains here
            System.out.println("GME has domain for " + domain);
            List<Namespace> namespaces = null;
            try {
                namespaces = gmeClient.getSchemaListForNamespaceDomain(domain);
                for (Namespace ns : namespaces) {
                    System.out.println("Domain contains schema " + ns);
                }
            } catch (MobiusException ex) {
                ex.printStackTrace();
                fail("Error loading schema names from gme " + getServiceUrl() 
                    + " : " + ex.getMessage());
            }
        }
    }
}
