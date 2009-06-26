/**
 *
 */
package gov.nih.nci.cagrid.portal.portlet.browse.sharedQuery;

import gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryCatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryToolsRelationship;
import gov.nih.nci.cagrid.portal.domain.catalog.GridServiceEndPointCatalogEntry;
import gov.nih.nci.cagrid.portal.domain.dataservice.CQLQuery;
import gov.nih.nci.cagrid.portal.util.PortalUtils;
import gov.nih.nci.cagrid.portal.dao.CQLQueryDao;
import gov.nih.nci.cagrid.portal.dao.catalog.GridServiceEndPointCatalogEntryDao;
import gov.nih.nci.cagrid.portal.portlet.query.shared.XMLSchemaValidatorFactory;
import gov.nih.nci.cagrid.portal.portlet.browse.ToolCatalogEntryManagerFacade;
import gov.nih.nci.cagrid.portal.portlet.browse.GridServiceEndpointDescriptorBean;
import gov.nih.nci.cagrid.common.SchemaValidationException;
import gov.nih.nci.cagrid.common.SchemaValidator;
import gov.nih.nci.cagrid.common.Utils;

import java.util.List;
import java.util.ArrayList;
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @author kherm manav.kher@semanticbits.com
 */
@Transactional
public class SharedQueryCatalogEntryManagerFacade extends
        ToolCatalogEntryManagerFacade {

    CQLQueryDao cqlQueryDao;
    GridServiceEndPointCatalogEntryDao gridServiceEndPointCatalogEntryDao;
    String cqlQueryXML;
    private SchemaValidator cqlXMLSchemaValidator;

    private static final Log logger = LogFactory
            .getLog(SharedQueryCatalogEntryManagerFacade.class);

    /**
     *
     */
    public SharedQueryCatalogEntryManagerFacade() {
        // TODO Auto-generated constructor stub
    }

    public String setQuery(String query) {
        cqlQueryXML = query;
        return null;

    }

    /**
     * for autocompleterer. Will only get services that are associated with CE's
     *
     * @param partialUrl
     * @return
     */
    public List<GridServiceEndpointDescriptorBean> getCatalogEntriesForPartialUrl(String partialUrl) {
        List<GridServiceEndpointDescriptorBean> result = new ArrayList<GridServiceEndpointDescriptorBean>();
        for (GridServiceEndPointCatalogEntry entry : gridServiceEndPointCatalogEntryDao.getByPartialUrl(partialUrl)) {
            GridServiceEndpointDescriptorBean descriptor = new GridServiceEndpointDescriptorBean(String.valueOf(entry.getId()), String.valueOf(entry.getAbout().getId()), entry.getAbout().getUrl());
            result.add(descriptor);
        }
        return result;
    }


    @Override
    public String validate() {
        CQLQuery query = null;

        SharedQueryCatalogEntry ce = ((SharedQueryCatalogEntry) getUserModel().getCurrentCatalogEntry());
        if (ce != null)
            query = ce.getAbout();

        if (query == null && cqlQueryXML == null)
            return "Please enter a valid XML query";
//        else {
//            try {
//                cqlXMLSchemaValidator.validate(query.getXml());
//            } catch (SchemaValidationException e) {
//                return "XML Query does not conform to CQL Schema.";
//            }


//        }
        return null;
    }

    public String getTargetClass() {
        String queryXML = ((SharedQueryCatalogEntry) getUserModel().getCurrentCatalogEntry()).getAbout().getXml();

        StringReader reader = new StringReader(queryXML);
        try {
            gov.nih.nci.cagrid.cqlquery.CQLQuery query = (gov.nih.nci.cagrid.cqlquery.CQLQuery) Utils.deserializeObject(reader,
                    gov.nih.nci.cagrid.cqlquery.CQLQuery.class);
            return query.getTarget().getName();
        } catch (Exception e) {
            return "Could not be determined";
        }

    }

    @Override
    public Integer save() {
        loop:
        if (this.cqlQueryXML != null) {
            String hash = PortalUtils.createHash(this.cqlQueryXML);

            CQLQuery aboutCQL = null;

            SharedQueryCatalogEntry ce = ((SharedQueryCatalogEntry) getUserModel().getCurrentCatalogEntry());
            if (ce != null)
                aboutCQL = ce.getAbout();

            if (aboutCQL == null) {
                logger.debug("Will create a new query");
                aboutCQL = new CQLQuery();
            } else {
                aboutCQL = cqlQueryDao.getById(aboutCQL.getId());

                if (hash.equals(aboutCQL.getHash())){
                    logger.debug("Will not save query. Same as existing query");
                    break loop;
                }
            }

            aboutCQL.setHash(hash);
            aboutCQL.setXml(this.cqlQueryXML);
            cqlQueryDao.save(aboutCQL);

            ((SharedQueryCatalogEntry) getUserModel().getCurrentCatalogEntry()).setAbout(aboutCQL);
        }
        return super.save();
    }

    public CQLQueryDao getCqlQueryDao() {
        return cqlQueryDao;
    }

    public void setCqlQueryDao(CQLQueryDao cqlQueryDao) {
        this.cqlQueryDao = cqlQueryDao;
    }

    public GridServiceEndPointCatalogEntryDao getGridServiceEndPointCatalogEntryDao() {
        return gridServiceEndPointCatalogEntryDao;
    }

    public void setGridServiceEndPointCatalogEntryDao(GridServiceEndPointCatalogEntryDao gridServiceEndPointCatalogEntryDao) {
        this.gridServiceEndPointCatalogEntryDao = gridServiceEndPointCatalogEntryDao;
    }

    public SchemaValidator getCqlXMLSchemaValidator() {
        return cqlXMLSchemaValidator;
    }

    public void setCqlXMLSchemaValidator(SchemaValidator cqlXMLSchemaValidator) {
        this.cqlXMLSchemaValidator = cqlXMLSchemaValidator;
    }
}
