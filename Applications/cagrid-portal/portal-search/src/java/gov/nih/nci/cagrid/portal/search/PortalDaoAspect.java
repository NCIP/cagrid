package gov.nih.nci.cagrid.portal.search;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
@Aspect
public class PortalDaoAspect extends AbstractSolrCommandExecutor {


    public PortalDaoAspect(String command) throws URISyntaxException {
        super(command);
    }

    @AfterReturning("execution(* gov.nih.nci.cagrid.portal.dao.catalog.*.save*(gov.nih.nci.cagrid.portal.domain.catalog.*))")
    public void runCommand() throws Exception {
        try {
            logger.info("Calling update on SOLR. Changes detected to the Portal DB");
            HttpMethod method = new GetMethod(getBaseSolrURL() + getCommand());
            getHttpClient().executeMethod(method);
            logger.info("Update sucessfully executed on SOLR.");
        } catch (IOException e) {
            String msg = "Could not execute Update command on SOLR HTTP service. Make sure SOLR is running.";
            logger.warn(msg);
            throw new PortalSearchRuntimeException(msg);
        }


    }
}
