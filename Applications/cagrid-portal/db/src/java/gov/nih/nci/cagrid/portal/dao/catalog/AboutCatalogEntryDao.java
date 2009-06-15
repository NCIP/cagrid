package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.dao.AbstractDao;
import gov.nih.nci.cagrid.portal.domain.DomainObject;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import gov.nih.nci.cagrid.portal.util.TimestampProvider;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.NonUniqueResultException;
import java.util.List;

/**
 * Dao for Catalog entries that are "about" another Portal domain object
 * <p/>
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public abstract class AboutCatalogEntryDao<T extends CatalogEntry, D extends DomainObject> extends AbstractDao<T> {

    TimestampProvider timestampProvider;
    private final CatalogEntryDao catalogEntryDao = new CatalogEntryDao();

    public AboutCatalogEntryDao() {
    }


    public abstract Class domainClass();

    /**
     * Subclasses need to provide implementation for creating
     * catalog items from domain objects
     *
     * @param domainObject
     */
    public abstract T createCatalogAbout(D domainObject);


    /**
     * @param d
     * @return CatalogEntry from the domain object that the catalog is about
     */
    public T isAbout(DomainObject d) {

        T catalog = null;
        List l = getHibernateTemplate().find("from " + domainClass().getSimpleName() + " where about.id = ?",
                new Object[]{d.getId()});
        if (l.size() > 1) {
            throw new NonUniqueResultException("More than one CatalogEntry found for Domain Object with ID = " + d.getId());
        }
        if (l.size() == 1) {
            catalog = (T) l.iterator().next();
        }
        return catalog;
    }


    @Override
    /**
     * Saves the catalog entry with the current timestamp
     */
    public void save(T entry) {
        entry.setUpdatedAt(timestampProvider.getTimestamp());
        super.save(entry);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public TimestampProvider getTimestampProvider() {
        return timestampProvider;
    }

    @Required
    public void setTimestampProvider(TimestampProvider timestampProvider) {
        this.timestampProvider = timestampProvider;
    }
}