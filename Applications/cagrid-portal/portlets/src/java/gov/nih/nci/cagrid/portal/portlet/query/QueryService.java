/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.query;

import gov.nih.nci.cagrid.portal.dao.GridServiceDao;
import gov.nih.nci.cagrid.portal.dao.QueryDao;
import gov.nih.nci.cagrid.portal.dao.QueryInstanceDao;
import gov.nih.nci.cagrid.portal.domain.GridDataService;
import gov.nih.nci.cagrid.portal.domain.dataservice.CQLQuery;
import gov.nih.nci.cagrid.portal.domain.dataservice.CQLQueryInstance;
import gov.nih.nci.cagrid.portal.domain.dataservice.DCQLQuery;
import gov.nih.nci.cagrid.portal.domain.dataservice.DCQLQueryInstance;
import gov.nih.nci.cagrid.portal.domain.dataservice.Query;
import gov.nih.nci.cagrid.portal.domain.dataservice.QueryInstance;
import gov.nih.nci.cagrid.portal.portlet.UserModel;
import gov.nih.nci.cagrid.portal.portlet.query.cql.CQLQueryInstanceExecutor;
import gov.nih.nci.cagrid.portal.portlet.util.PortletUtils;
import gov.nih.nci.cagrid.portal.util.PortalUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
@Transactional
public class QueryService implements ApplicationContextAware {
	
	private Map<Integer, QueryInstanceExecutor> executors = new HashMap<Integer, QueryInstanceExecutor>();
	private ApplicationContext applicationContext;
	private String cqlQueryExecutorBeanName = "cqlQueryInstanceExecutorPrototype";
	private String dcqlQueryExecutorBeanName = "dcqlQueryInstanceExecutorPrototype";
	private UserModel userModel;
	private QueryDao queryDao;
	private QueryInstanceDao queryInstanceDao;
	private GridServiceDao gridServiceDao;

	public QueryInstance submitQuery(String queryXML, String serviceUrl) {
		QueryInstance instance = null;
		if (PortletUtils.isCQL(queryXML)) {
			instance = submitCQLQuery(queryXML, serviceUrl);
		} else {
			instance = submitDCQLQuery(queryXML, serviceUrl);
		}
		return instance;
	}
	
	public List<QueryInstance> getSubmittedQueries(){
        SortedSet<QueryInstance> set = new TreeSet<QueryInstance>(new Comparator<QueryInstance>(){
			public int compare(QueryInstance q1, QueryInstance q2) {
				return q2.getCreateTime().compareTo(q1.getCreateTime());
			}
        });
        synchronized (executors) {
            for (QueryInstanceExecutor executor : executors.values()) {
                set.add(executor.getQueryInstance());
            }
        }
        return new ArrayList<QueryInstance>(set);
	}
	
	public Query loadQuery(String queryXML){
		return getQueryDao().getQueryByHash(PortalUtils.createHash(queryXML));
	}

	protected DCQLQueryInstance submitDCQLQuery(String queryXML,
			String serviceUrl) {
		DCQLQueryInstance instance = new DCQLQueryInstance();

		GridDataService dataService = (GridDataService) getGridServiceDao()
				.getByUrl(serviceUrl);

		DCQLQuery query = (DCQLQuery) getQuery(queryXML);
		Set<String> svcUrls = null;
		try {
			svcUrls = PortletUtils.getTargetServiceUrls(queryXML);
		} catch (Exception ex) {
			throw new RuntimeException(
					"Error getting target service URLs from DCQL query: "
							+ ex.getMessage(), ex);
		}
		for (String svcUrl : svcUrls) {
			GridDataService svc = (GridDataService) getGridServiceDao()
					.getByUrl(svcUrl);
			query.getTargetServices().add(svc);
		}
		instance.setPortalUser(getUserModel().getPortalUser());
		instance.setQuery(query);
		getQueryInstanceDao().save(instance);

		CQLQueryInstanceExecutor executor = (CQLQueryInstanceExecutor) getApplicationContext()
				.getBean(getDcqlQueryExecutorBeanName());
		startQueryInstance(instance, executor);

		return instance;
	}

	protected CQLQueryInstance submitCQLQuery(String queryXML, String serviceUrl) {
		CQLQueryInstance instance = new CQLQueryInstance();

		GridDataService dataService = (GridDataService) getGridServiceDao()
				.getByUrl(serviceUrl);
		instance.setDataService(dataService);
		instance.setPortalUser(getUserModel().getPortalUser());
		instance.setQuery(getQuery(queryXML));
		getQueryInstanceDao().save(instance);

		CQLQueryInstanceExecutor executor = (CQLQueryInstanceExecutor) getApplicationContext()
				.getBean(getCqlQueryExecutorBeanName());
		startQueryInstance(instance, executor);

		return instance;
	}

	protected Query getQuery(String queryXML) {
		String xml = PortletUtils.normalizeCQL(queryXML);
		String hash = PortalUtils.createHash(xml);
		Query query = getQueryDao().getQueryByHash(hash);
		if (query == null) {
			query = new CQLQuery();
			query.setXml(xml);
			query.setHash(hash);
			getQueryDao().save(query);
		}
		return query;
	}

	protected void startQueryInstance(QueryInstance instance,
			QueryInstanceExecutor executor) {
		executor.setQueryInstance(instance);
		executor.start();
		executors.put(instance.getId(), executor);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public String getCqlQueryExecutorBeanName() {
		return cqlQueryExecutorBeanName;
	}

	public void setCqlQueryExecutorBeanName(String cqlQueryExecutorBeanName) {
		this.cqlQueryExecutorBeanName = cqlQueryExecutorBeanName;
	}

	public String getDcqlQueryExecutorBeanName() {
		return dcqlQueryExecutorBeanName;
	}

	public void setDcqlQueryExecutorBeanName(String dcqlQueryExecutorBeanName) {
		this.dcqlQueryExecutorBeanName = dcqlQueryExecutorBeanName;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public QueryDao getQueryDao() {
		return queryDao;
	}

	public void setQueryDao(QueryDao queryDao) {
		this.queryDao = queryDao;
	}

	public GridServiceDao getGridServiceDao() {
		return gridServiceDao;
	}

	public void setGridServiceDao(GridServiceDao gridServiceDao) {
		this.gridServiceDao = gridServiceDao;
	}

	public QueryInstanceDao getQueryInstanceDao() {
		return queryInstanceDao;
	}

	public void setQueryInstanceDao(QueryInstanceDao queryInstanceDao) {
		this.queryInstanceDao = queryInstanceDao;
	}

}
