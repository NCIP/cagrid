package gov.nih.nci.cagrid.portal.portlet.browse.sharedQuery;

import gov.nih.nci.cagrid.portal.domain.dataservice.*;
import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.domain.GridDataService;
import gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryCatalogEntry;
import gov.nih.nci.cagrid.portal.dao.*;
import gov.nih.nci.cagrid.portal.portlet.browse.CatalogEntryManagerFacade;
import gov.nih.nci.cagrid.portal.portlet.query.QueryModel;
import gov.nih.nci.cagrid.portal.portlet.query.cql.CQLQueryService;
import gov.nih.nci.cagrid.common.SchemaValidator;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Required;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
@Transactional
public class QueryExecutionManager extends CatalogEntryManagerFacade {

    private GridServiceDao gridServiceDao;
    private QueryModel queryModel;
    private int maxActiveQueries = 5;
    private SchemaValidator cqlXMLSchemaValidator;
    private CQLQueryService cqlQueryService;


    @Override
    public String validate() {
        int active = 0;
        for (QueryInstance instance : getQueryModel().getSubmittedQueries()) {
            if (QueryInstanceState.RUNNING.equals(instance.getState())
                    || QueryInstanceState.SCHEDULED.equals(instance.getState())
                    || QueryInstanceState.UNSCHEDULED.equals(instance.getState())) {
                active++;
            }
        }
        if (active >= getMaxActiveQueries()) {
            return "Exceeded maximum number of queries. Please wait for some queries to finish";
        }
        return null;
    }

    public String submitQuery(String serviceUrl) {
        String errStr = validate();
        if (errStr != null)
            return errStr;

        CQLQuery query = ((SharedQueryCatalogEntry) getUserModel().getCurrentCatalogEntry()).getAbout();
        try {
            GridDataService ds = (GridDataService) gridServiceDao.getByUrl(serviceUrl);
            if (ds == null)
                throw new Exception("Please enter a valid service URL");
            PortalUser user = getUserModel().getPortalUser();
            cqlQueryService.submitQuery(user, ds, query.getXml());
        } catch (Exception e) {
            return "Could not submit query.";
        }
        return null;
    }


    /**
     * Returns all query instances for a cql query
     *
     * @param cql
     * @return
     */
    public List<QueryInstance> getQueryInstances(String cql) {
        CQLQuery query = cqlQueryService.loadQuery(cql);
        List<QueryInstance> result = new ArrayList<QueryInstance>();
        if (query != null) {
            for (QueryInstance instance : getQueryModel().getSubmittedQueries()) {
                if (query.getId().equals(instance.getQuery().getId()))
                    result.add(instance);
            }
        }
        return result;
    }


    public String getResults(String instanceId) {
        String result = "Could not find query with ID " + instanceId;

        QueryInstance instance = loadInstance(instanceId);
        if (instance != null) {
            getQueryModel().setSelectedQueryInstance(instance);

            Map<String, Object> infoMap = new HashMap<String, Object>();
            infoMap.put("instance", instance);

            try {
                return getView("/WEB-INF/jsp/browse/sharedQuery/query_results_display.jsp", infoMap);
            } catch (Exception ex) {
                String msg = "Error rendering query results: " + ex.getMessage();
                logger.error(msg, ex);
                throw new RuntimeException(msg, ex);
            }
        }

        return result;

    }

    public String getError(String instanceId) {
        String result = "Could not find query with ID " + instanceId;

        QueryInstance instance = loadInstance(instanceId);
        if (instance != null) {
            result = instance.getError();

            Map<String, Object> infoMap = new HashMap<String, Object>();
            infoMap.put("instance", instance);

            try {
                return getView("/WEB-INF/jsp/browse/sharedQuery/query_error_display.jsp", infoMap);
            } catch (Exception ex) {
                String msg = "Error rendering query error log: " + ex.getMessage();
                logger.error(msg, ex);
                throw new RuntimeException(msg, ex);
            }
        }

        return result;


    }

    // should throw no exceptions
    public void navigateToInstance(String instanceId){
        logger.debug("Navigating to Query instance "  + instanceId);
       QueryInstance instance = loadInstance(instanceId);
        if (instance != null)
            getQueryModel().setSelectedQueryInstance(instance);
    }


    public QueryInstance loadInstance(String instanceId) {

        for (QueryInstance inst : getQueryModel().getSubmittedQueries()) {
            if (instanceId.equals(String.valueOf(inst.getId()))) {
                return inst;
            }
        }
        return null;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public SchemaValidator getCqlXMLSchemaValidator() {
        return cqlXMLSchemaValidator;
    }

    public void setCqlXMLSchemaValidator(SchemaValidator cqlXMLSchemaValidator) {
        this.cqlXMLSchemaValidator = cqlXMLSchemaValidator;
    }

    public CQLQueryService getCqlQueryService() {
        return cqlQueryService;
    }

    @Required
    public void setCqlQueryService(CQLQueryService cqlQueryService) {
        this.cqlQueryService = cqlQueryService;
    }


    public GridServiceDao getGridServiceDao() {
        return gridServiceDao;
    }

    public void setGridServiceDao(GridServiceDao gridServiceDao) {
        this.gridServiceDao = gridServiceDao;
    }

    public int getMaxActiveQueries() {
        return maxActiveQueries;
    }

    public void setMaxActiveQueries(int maxActiveQueries) {
        this.maxActiveQueries = maxActiveQueries;
    }
}
