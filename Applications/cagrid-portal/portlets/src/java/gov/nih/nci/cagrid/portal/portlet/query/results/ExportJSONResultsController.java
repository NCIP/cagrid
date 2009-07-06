/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.query.results;

import gov.nih.nci.cagrid.portal.dao.QueryResultTableDao;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultTable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ExportJSONResultsController extends AbstractController {

	private QueryResultTableDao queryResultTableDao;
	private QueryResultTableToJSONObjectBuilder queryResultTableToJSONObjectBuilder;

	/**
	 * 
	 */
	public ExportJSONResultsController() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String instanceId = req.getParameter("instanceId");
		String sort = req.getParameter("sort");
		String dir = req.getParameter("dir");
		String startIndex = req.getParameter("startIndex");
		String results = req.getParameter("results");

		QueryResultTable table = getQueryResultTableDao().getByQueryInstanceId(
				Integer.valueOf(instanceId));
		JSONObject tableJO = getQueryResultTableToJSONObjectBuilder().build(
				table.getId(), sort, dir, Integer.valueOf(startIndex),
				Integer.valueOf(results));

		res.setContentType("application/json");
		res.getWriter().write(tableJO.toString());

		return null;
	}

	public QueryResultTableDao getQueryResultTableDao() {
		return queryResultTableDao;
	}

	public void setQueryResultTableDao(QueryResultTableDao queryResultTableDao) {
		this.queryResultTableDao = queryResultTableDao;
	}

	public QueryResultTableToJSONObjectBuilder getQueryResultTableToJSONObjectBuilder() {
		return queryResultTableToJSONObjectBuilder;
	}

	public void setQueryResultTableToJSONObjectBuilder(
			QueryResultTableToJSONObjectBuilder queryResultTableToJSONObjectBuilder) {
		this.queryResultTableToJSONObjectBuilder = queryResultTableToJSONObjectBuilder;
	}

}
