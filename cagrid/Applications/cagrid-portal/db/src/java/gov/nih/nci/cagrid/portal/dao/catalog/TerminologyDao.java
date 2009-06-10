package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.dao.AbstractDao;
import gov.nih.nci.cagrid.portal.domain.catalog.Terminology;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class TerminologyDao extends AbstractDao<Terminology> {

    public TerminologyDao() {
    }

    /* (non-Javadoc)
    * @see gov.nih.nci.cagrid.portal.dao.AbstractDao#domainClass()
    */
    @Override
    public Class domainClass() {
        return Terminology.class;
    }
}