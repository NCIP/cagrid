package gov.nih.nci.cagrid.portal.util;

import java.util.Iterator;
import java.util.List;

import gov.nih.nci.cagrid.portal.dao.catalog.CatalogEntryDao;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

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
        
        HibernateTemplate templ = (HibernateTemplate)ctx.getBean("hibernateTemplate");
        
        List entries1 = templ.find("from CatalogEntry ce where ce.class != PersonCatalogEntry");
        for(Iterator<CatalogEntry> i = entries1.iterator(); i.hasNext();){
        	templ.delete(i.next());
        }
        
        List entries2 = templ.find("from PersonCatalogEntry p where not p.id in (select u.catalog.id from PortalUser u)");
        for(Iterator<PersonCatalogEntry> i = entries2.iterator(); i.hasNext();){
        	PersonCatalogEntry pce = i.next();
        	templ.delete(pce);
        }
        
    }
}





