/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.query.results;

import gov.nih.nci.cagrid.portal.domain.table.QueryResultColumn;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultTable;
import gov.nih.nci.cagrid.portal.portlet.util.PortletUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.JSONObject;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class QueryResultTableToDataTableMetadataBuilder {

	private Map<String, String> columnTypeFormatterMap;

	public JSONObject build(QueryResultTable table) {
		try {
			JSONObject meta = new JSONObject();

			Map<String, String> formatters = new HashMap<String, String>();
			List<String> allColNames = getOrderedColumnNames(table);

			for (String colName : allColNames) {
				JSONObject colDef = new JSONObject();
				colDef.put("key", colName);
				colDef.put("sortable", true);
				colDef.put("resizeable", true);
				String formatter = formatters.get(colName);
				if (formatter != null) {
					colDef.put("formatter", formatter);
				}
				meta.append("columnDefs", colDef);
			}

			JSONObject responseSchema = new JSONObject();
			meta.put("responseSchema", responseSchema);
			responseSchema.put("resultsList", "rows");
			JSONObject metaFields = new JSONObject();
			responseSchema.put("metaFields", metaFields);
			metaFields.put("totalRecords", "numRows");

			for (String colName : allColNames) {
				responseSchema.append("fields", colName);
			}

			return meta;
		} catch (Exception ex) {
			throw new RuntimeException("Error building metadata: "
					+ ex.getMessage(), ex);
		}
	}
	
	public static List<String> getOrderedColumnNames(QueryResultTable table){
		List<String> allColNames = new ArrayList<String>();

		if (PortletUtils.isCountQuery(table.getQueryInstance().getQuery()
				.getXml())) {
			allColNames.add("count");
		} else {
			SortedSet<String> sortedColNames = new TreeSet<String>();
			for (QueryResultColumn col : table.getColumns()) {
				sortedColNames.add(col.getName());
			}
			if (sortedColNames.contains("id")) {
				allColNames.add("id");
			}
			for (String colName : sortedColNames) {
				if (!"id".equals(colName)) {
					allColNames.add(colName);
				}
			}
		}
		allColNames.add(ResultConstants.DATA_SERVICE_URL_COL_NAME);
		return allColNames;
	}

	protected String getFormatter(QueryResultColumn column) {
		String formatter = null;
		if (getColumnTypeFormatterMap() != null) {
			formatter = getColumnTypeFormatterMap().get(column.getName());
		}
		return formatter;
	}

	public Map<String, String> getColumnTypeFormatterMap() {
		return columnTypeFormatterMap;
	}

	public void setColumnTypeFormatterMap(
			Map<String, String> columnTypeFormatterMap) {
		this.columnTypeFormatterMap = columnTypeFormatterMap;
	}

}
