package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.dao.AbstractDao;
import gov.nih.nci.cagrid.portal.domain.catalog.Term;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class TermDao extends AbstractDao<Term> {

    public TermDao() {
    }

    /* (non-Javadoc)
    * @see gov.nih.nci.cagrid.portal.dao.AbstractDao#domainClass()
    */
    @Override
    public Class domainClass() {
        return Term.class;
    }

}
