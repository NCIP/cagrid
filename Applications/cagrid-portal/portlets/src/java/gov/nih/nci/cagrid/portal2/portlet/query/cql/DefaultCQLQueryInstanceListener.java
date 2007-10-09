/**
 * 
 */
package gov.nih.nci.cagrid.portal2.portlet.query.cql;

import java.util.Date;

import gov.nih.nci.cagrid.portal2.dao.CQLQueryInstanceDao;
import gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstance;
import gov.nih.nci.cagrid.portal2.domain.dataservice.QueryInstanceState;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public class DefaultCQLQueryInstanceListener implements
		CQLQueryInstanceListener {
	
	private static final Log logger = LogFactory.getLog(DefaultCQLQueryInstanceListener.class);
	
	private CQLQueryInstanceDao cqlQueryInstanceDao;

	/**
	 * 
	 */
	public DefaultCQLQueryInstanceListener() {

	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstanceListener#onCancelled(gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstance, boolean)
	 */
	public void onCancelled(CQLQueryInstance instance, boolean cancelled) {
		if(!cancelled){
			logger.warn("Couldn't cancel CQLQueryInstance:" + instance.getId());
		}else{
			instance.setState(QueryInstanceState.CANCELLED);
			getCqlQueryInstanceDao().save(instance);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstanceListener#onComplete(gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstance, java.lang.String)
	 */
	public void onComplete(CQLQueryInstance instance, String results) {
		instance.setFinishTime(new Date());
		instance.setState(QueryInstanceState.COMPLETE);
		getCqlQueryInstanceDao().save(instance);
		instance.setResult(results);
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstanceListener#onError(gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstance, java.lang.Exception)
	 */
	public void onError(CQLQueryInstance instance, Exception error) {
		String msg = error.getMessage();
		logger.info("CQLQueryInstance:" + instance.getId() + " encountered error: " + msg, error);
		instance.setError(msg);
		instance.setState(QueryInstanceState.ERROR);
		getCqlQueryInstanceDao().save(instance);
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstanceListener#onRunning(gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstance)
	 */
	public void onRunning(CQLQueryInstance instance) {
		instance.setState(QueryInstanceState.RUNNING);
		instance.setStartTime(new Date());
		getCqlQueryInstanceDao().save(instance);
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstanceListener#onSheduled(gov.nih.nci.cagrid.portal2.domain.dataservice.CQLQueryInstance)
	 */
	public void onSheduled(CQLQueryInstance instance) {
		instance.setState(QueryInstanceState.SCHEDULED);
		getCqlQueryInstanceDao().save(instance);		
	}

	public CQLQueryInstanceDao getCqlQueryInstanceDao() {
		return cqlQueryInstanceDao;
	}

	public void setCqlQueryInstanceDao(CQLQueryInstanceDao cqlQueryInstanceDao) {
		this.cqlQueryInstanceDao = cqlQueryInstanceDao;
	}

}
