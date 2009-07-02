/**
 * 
 */
package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.dao.AbstractDao;
import gov.nih.nci.cagrid.portal.domain.catalog.GridServiceInterfaceCatalogEntry;

import java.util.List;

import javax.persistence.NonUniqueResultException;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class GridServiceInterfaceCatalogEntryDao extends
		AbstractDao<GridServiceInterfaceCatalogEntry> {

	@Override
	public Class domainClass() {
		return GridServiceInterfaceCatalogEntry.class;
	}

	public GridServiceInterfaceCatalogEntry getDynamicInterfaceForNameAndVersion(
			String name, String version) {

		GridServiceInterfaceCatalogEntry gsiCe = null;

		List l = getHibernateTemplate()
				.find(
						"from GridServiceInterfaceCatalogEntry g where g.author is null and g.name = ? and g.version = ?",
						new Object[] { name, version });
		if (l.size() > 1) {
			throw new NonUniqueResultException(
					"More than one dynamic GridServiceInterfaceCatalogEntry found for name:version = "
							+ name + ":" + version);
		}
		if (l.size() == 1) {
			gsiCe = (GridServiceInterfaceCatalogEntry) l.iterator().next();
		}

		return gsiCe;
	}

	public GridServiceInterfaceCatalogEntry getInterfaceForNameAndVersion(
			String name, String version) {

		GridServiceInterfaceCatalogEntry gsiCe = null;

		List l = getHibernateTemplate()
				.find(
						"from GridServiceInterfaceCatalogEntry g where g.name = ? and g.version = ?",
						new Object[] { name, version });
		if (l.size() > 1) {
			throw new NonUniqueResultException(
					"More than one GridServiceInterfaceCatalogEntry found for name:version = "
							+ name + ":" + version);
		}
		if (l.size() == 1) {
			gsiCe = (GridServiceInterfaceCatalogEntry) l.iterator().next();
		}

		return gsiCe;
	}

}
