/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.query.results;

import gov.nih.nci.cagrid.portal.dao.QueryResultTableDao;
import gov.nih.nci.cagrid.portal.domain.dataservice.QueryInstance;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultCell;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultColumn;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultRow;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultTable;
import gov.nih.nci.cagrid.portal.portlet.UserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private UserModel userModel;
	// private QueryResultColumnNameResolver queryResultColumnNameResolver;
	private QueryResultTableDao queryResultTableDao;

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

		String sort = req.getParameter("sort");
		String dir = req.getParameter("dir");
		String startIndex = req.getParameter("startIndex");
		String results = req.getParameter("results");

		QueryInstance instance = getUserModel().getCurrentQueryInstance();
		QueryResultTable table = getQueryResultTableDao().getByQueryInstanceId(
				instance.getId());
		Integer numRows = getQueryResultTableDao().getRowCount(table.getId());
		List<QueryResultRow> rows = getQueryResultTableDao().getSortedRows(
				table.getId(), sort, dir, Integer.parseInt(startIndex),
				Integer.parseInt(results));
		JSONObject tableJO = new JSONObject();
		tableJO.put("numRows", numRows);
		for (QueryResultRow row : rows) {

			Map<String, String> rowMap = new HashMap<String, String>();
			for (QueryResultColumn col : table.getColumns()) {

				String value = "";
				for (QueryResultCell cell : row.getCells()) {
					if (cell.getColumn().getId().equals(col.getId())) {
						value = cell.getValue();
						break;
					}
				}
				rowMap.put(col.getName(), value);
			}
			tableJO.append("rows", rowMap);
		}
		res.setContentType("application/json");
		res.getWriter().write(tableJO.toString());

		// List<String> colNames = getQueryResultColumnNameResolver()
		// .getColumnNames(instance.getId());
		// String xml = instance.getResult();
		//		
		// SAXParserFactory fact = SAXParserFactory.newInstance();
		// fact.setNamespaceAware(true);
		// SAXParser parser = fact.newSAXParser();
		// XMLQueryResultToJSONHandler handler = new
		// XMLQueryResultToJSONHandler();
		// if (colNames != null) {
		// handler.setColumnNames(colNames);
		// }
		// parser.parse(new ByteArrayInputStream(xml.getBytes()), handler);
		//		
		// res.setContentType("application/json");
		// res.getWriter().write(handler.getTable().toString());

		return null;
	}

	// public QueryResultColumnNameResolver getQueryResultColumnNameResolver() {
	// return queryResultColumnNameResolver;
	// }
	//
	// public void setQueryResultColumnNameResolver(
	// QueryResultColumnNameResolver queryResultColumnNameResolver) {
	// this.queryResultColumnNameResolver = queryResultColumnNameResolver;
	// }

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public QueryResultTableDao getQueryResultTableDao() {
		return queryResultTableDao;
	}

	public void setQueryResultTableDao(QueryResultTableDao queryResultTableDao) {
		this.queryResultTableDao = queryResultTableDao;
	}

}
