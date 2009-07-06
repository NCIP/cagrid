/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.query.results;

import gov.nih.nci.cagrid.portal.dao.QueryResultTableDao;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultCell;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultColumn;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultRow;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class QueryResultTableToJSONObjectBuilder {

	private QueryResultTableDao queryResultTableDao;
	
	public JSONObject build(Integer tableId){
		return build(tableId, null, null, null, null);
	}

	public JSONObject build(Integer tableId, String pSort, String pDir,
			Integer pStartIndex, Integer pResults) {
		JSONObject tableJO = new JSONObject();
		try {
			Integer numRows = getQueryResultTableDao().getRowCount(tableId);

			String sort = StringUtils.isEmpty(pSort) ? "id" : pSort;
			String dir = StringUtils.isEmpty(pDir) ? "asc" : pDir;
			Integer startIndex = pStartIndex == null ? 0 : pStartIndex;
			Integer results = pResults == null ? numRows - startIndex : Math
					.min(pResults, numRows - startIndex);

			QueryResultTable table = getQueryResultTableDao().getById(tableId);

			List<QueryResultRow> rows = getQueryResultTableDao().getSortedRows(
					table.getId(), sort, dir, startIndex, results);

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
		} catch (Exception ex) {
			throw new RuntimeException("Error building JSON: "
					+ ex.getMessage(), ex);
		}
		return tableJO;
	}

	public QueryResultTableDao getQueryResultTableDao() {
		return queryResultTableDao;
	}

	public void setQueryResultTableDao(QueryResultTableDao queryResultTableDao) {
		this.queryResultTableDao = queryResultTableDao;
	}

}
