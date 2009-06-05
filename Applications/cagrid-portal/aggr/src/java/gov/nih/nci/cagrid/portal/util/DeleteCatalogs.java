package gov.nih.nci.cagrid.portal.util;

import gov.nih.nci.cagrid.portal.dao.catalog.CatalogEntryDao;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class DeleteCatalogs {

    static Log logger = LogFactory.getLog(DeleteCatalogs.class);


    public static void main(String[] args) {


        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{
                "classpath:applicationContext-db.xml",
                "classpath:applicationContext-aggr-catalog.xml"});

        logger.debug("Will delete all catalog entries");

        CatalogEntryDao catalogEntryDao = (CatalogEntryDao) ctx.getBean("catalogEntryDao");

        for (CatalogEntry entry : catalogEntryDao.getAll()) {
            catalogEntryDao.delete(entry);
        }
    }
}





