package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.dao.AbstractDao;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;

import java.util.ArrayList;
import java.util.List;

public class CatalogEntryDao extends AbstractDao<CatalogEntry> {
    public CatalogEntryDao() {
    }/* (non-Javadoc)
    * @see gov.nih.nci.cagrid.portal.dao.AbstractDao#domainClass()
    */

    public List<CatalogEntry> getLatestContent(int limit) {
        List<CatalogEntry> latest = new ArrayList<CatalogEntry>();

        List<Integer> ids = getHibernateTemplate().find(
                "select entry.id from CatalogEntry entry "
                        + "order by entry.updatedAt desc");
        List<CatalogEntry> l = new ArrayList<CatalogEntry>();
        for (Integer id : ids) {
            l.add((CatalogEntry) getHibernateTemplate().get(CatalogEntry.class,
                    id));
        }
        for (int i = 0; i < limit && i < l.size(); i++) {
            latest.add(l.get(i));
        }
        return latest;
    }

    // is  a JoinPoint   
    @Override
    public void save(CatalogEntry domainObject) {
        super.save(domainObject);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Class domainClass() {
        return CatalogEntry.class;
    }
}