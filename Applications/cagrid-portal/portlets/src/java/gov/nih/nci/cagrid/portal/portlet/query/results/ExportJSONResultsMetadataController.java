/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.query.results;

import gov.nih.nci.cagrid.portal.dao.QueryResultTableDao;
import gov.nih.nci.cagrid.portal.domain.dataservice.QueryInstance;
import gov.nih.nci.cagrid.portal.domain.metadata.common.UMLAttribute;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultColumn;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultTable;
import gov.nih.nci.cagrid.portal.portlet.UserModel;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ExportJSONResultsMetadataController extends AbstractController {

	private UserModel userModel;
	private QueryResultColumnNameResolver queryResultColumnNameResolver;
	private QueryResultTableDao queryResultTableDao;

	/**
	 * 
	 */
	public ExportJSONResultsMetadataController() {

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

		JSONObject meta = new JSONObject();
		QueryInstance instance = getUserModel().getCurrentQueryInstance();
		for (UMLAttribute att : getQueryResultColumnNameResolver()
				.getTargetUMLClass(instance.getId())
				.getUmlAttributeCollection()) {
			JSONObject colDef = new JSONObject();
			colDef.put("key", att.getName());
			colDef.put("sortable", true);
			colDef.put("resizeable", true);
			if (isNumber(att.getDataTypeName())) {
				colDef.put("formatter", "YAHOO.widget.DataTable.formatNumber");
			} else if (isDate(att.getDataTypeName())) {
				colDef.put("formatter", "YAHOO.widget.DataTable.formatDate");
			}
			meta.append("columnDefs", colDef);
		}
		
		JSONObject responseSchema = new JSONObject();
		meta.put("responseSchema", responseSchema);
		responseSchema.put("resultsList", "rows");
		JSONObject metaFields = new JSONObject();
		responseSchema.put("metaFields", metaFields);
		metaFields.put("totalRecords", "numRows");
		
//		List<String> colNames = getQueryResultColumnNameResolver()
//				.getColumnNames(instance.getId());
//		
//		for (String colName : colNames) {
//			responseSchema.append("fields", colName);
//		}
		QueryResultTable table = getQueryResultTableDao().getByQueryInstanceId(instance.getId());
		for (QueryResultColumn col : table.getColumns()) {
			responseSchema.append("fields", col.getName());
		}
		res.setContentType("application/json");
		res.getWriter().write(meta.toString());

		return null;
	}

	private boolean isDate(String dataTypeName) {
		try {
			return Date.class.isAssignableFrom(Class.forName(dataTypeName));
		} catch (Exception ex) {

		}
		return false;
	}

	private boolean isNumber(String dataTypeName) {
		try {
			return Number.class.isAssignableFrom(Class.forName(dataTypeName));
		} catch (Exception ex) {

		}
		return false;
	}

	public QueryResultColumnNameResolver getQueryResultColumnNameResolver() {
		return queryResultColumnNameResolver;
	}

	public void setQueryResultColumnNameResolver(
			QueryResultColumnNameResolver queryResultColumnNameResolver) {
		this.queryResultColumnNameResolver = queryResultColumnNameResolver;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	
	public static void main(String[] args) throws Exception {
		JSONObject meta = new JSONObject();
		JSONObject responseSchema = new JSONObject();
		meta.put("responseSchema", responseSchema);
		responseSchema.put("resultsList", "rows");
		responseSchema.append("fields", "one");
		responseSchema.append("fields", "two");

		System.out.println(meta);
	}

	public QueryResultTableDao getQueryResultTableDao() {
		return queryResultTableDao;
	}

	public void setQueryResultTableDao(QueryResultTableDao queryResultTableDao) {
		this.queryResultTableDao = queryResultTableDao;
	}

}
