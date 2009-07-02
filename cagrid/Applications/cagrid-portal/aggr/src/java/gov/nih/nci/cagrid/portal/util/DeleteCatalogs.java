package gov.nih.nci.cagrid.portal.util;

import gov.nih.nci.cagrid.portal.dao.catalog.CatalogEntryRelationshipInstanceDao;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRelationshipInstance;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRoleInstance;
import gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry;

import java.util.Iterator;
import java.util.List;

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

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext-db.xml",
						"classpath:applicationContext-aggr-catalog.xml" });

		logger.debug("Will delete all catalog entries");

		HibernateTemplate templ = (HibernateTemplate) ctx
				.getBean("hibernateTemplate");

		CatalogEntryRelationshipInstanceDao relInstDao = (CatalogEntryRelationshipInstanceDao) ctx
				.getBean("catalogEntryRelationshipInstanceDao");
		List entries1 = templ
				.find("from CatalogEntry ce where ce.class != PersonCatalogEntry");
		for (Iterator<CatalogEntry> i = entries1.iterator(); i.hasNext();) {

			CatalogEntry ce = i.next();
			deleteRelationships(templ, relInstDao, ce);
			templ.delete(ce);
		}

		List entries2 = templ
				.find("from PersonCatalogEntry p where not p.id in (select u.catalog.id from PortalUser u)");
		for (Iterator<PersonCatalogEntry> i = entries2.iterator(); i.hasNext();) {
			PersonCatalogEntry pce = i.next();
			deleteRelationships(templ, relInstDao, pce);
			templ.delete(pce);
		}

	}

	private static void deleteRelationships(HibernateTemplate templ,
			CatalogEntryRelationshipInstanceDao relInstDao, CatalogEntry ce) {

		List<CatalogEntryRelationshipInstance> relInsts = relInstDao
				.getRelationshipsForCatalogEntry(ce.getId());

		for (CatalogEntryRelationshipInstance rel : relInsts) {

			CatalogEntryRoleInstance roleA = rel.getRoleA();
			roleA.setRelationship(null);
			roleA.setCatalogEntry(null);
			templ.save(roleA);

			CatalogEntryRoleInstance roleB = rel.getRoleB();
			roleB.setRelationship(null);
			roleB.setCatalogEntry(null);
			templ.save(roleB);

			rel.setRoleA(null);
			rel.setRoleB(null);
			relInstDao.save(rel);

			templ.flush();

			templ.delete(roleA);
			templ.delete(roleB);
		}
		templ.flush();

		for (CatalogEntryRelationshipInstance r : relInsts) {
			CatalogEntryRelationshipInstance rel = relInstDao.getById(r.getId()); 
			if (rel.getRoleA() != null) {
				throw new RuntimeException("roleA still exists for "
						+ rel.getId());
			}
			if (rel.getRoleB() != null) {
				throw new RuntimeException("roleB still exists for "
						+ rel.getId());
			}
			relInstDao.delete(rel);
		}
	}
}
