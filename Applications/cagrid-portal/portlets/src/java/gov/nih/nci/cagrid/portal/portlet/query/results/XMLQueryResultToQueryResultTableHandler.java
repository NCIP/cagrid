/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.query.results;

import gov.nih.nci.cagrid.portal.dao.QueryResultTableDao;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultCell;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultColumn;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultData;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultRow;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultTable;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class XMLQueryResultToQueryResultTableHandler extends
		BaseQueryResultHandler {

	private static final Log logger = LogFactory
			.getLog(XMLQueryResultToQueryResultTableHandler.class);

	private QueryResultTableDao queryResultTableDao;
	private QueryResultColumnNameResolver queryResultColumnNameResolver;
	private QueryResultTable table = new QueryResultTable();
	private Map<String, QueryResultColumn> cols = new HashMap<String, QueryResultColumn>();
	private QueryResultRow currentRow;
	private int maxValueLength = 256;

	/**
	 * 
	 */
	public XMLQueryResultToQueryResultTableHandler() {

	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		elementStack.push(new ElementInfo(uri, localName, qName, attributes));

		if (queryType == null) {
			getQueryResultTableDao().save(table);
			if ("DCQLQueryResultsCollection".equals(localName)) {
				queryType = QueryType.DCQL;
			} else {
				queryType = QueryType.CQL;
			}
		} else if (resultType == null) {
			int size = elementStack.size();
			if (size == 2 && QueryType.CQL.equals(queryType) || size == 4
					&& QueryType.DCQL.equals(queryType)) {
				if ("ObjectResult".equals(localName)) {
					resultType = ResultType.OBJECT;
				} else if ("AttributeResult".equals(localName)) {
					resultType = ResultType.ATTRIBUTE;
				} else if ("CountResult".equals(localName)) {
					resultType = ResultType.COUNT;
				}
			}
		}

		if (resultType != null) {
			if (ResultType.COUNT.equals(resultType)) {
				QueryResultColumn col = new QueryResultColumn();
				col.setName("count");
				col.setTable(table);
				getQueryResultTableDao().getHibernateTemplate().save(col);
				table.getColumns().add(col);

				QueryResultRow row = new QueryResultRow();
				row.setTable(table);
				getQueryResultTableDao().getHibernateTemplate().save(row);
				table.getRows().add(row);

				QueryResultCell cell = new QueryResultCell();
				cell.setValue(attributes.getValue("count"));
				cell.setRow(row);
				cell.setColumn(col);
				getQueryResultTableDao().getHibernateTemplate().save(cell);
				row.getCells().add(cell);

			} else if (ResultType.ATTRIBUTE.equals(resultType)) {

				if ("AttributeResult".equals(localName)) {

					if (currentRow != null) {
						getQueryResultTableDao().getHibernateTemplate().save(
								currentRow);
					}
					currentRow = new QueryResultRow();
					currentRow.setTable(table);
					getQueryResultTableDao().getHibernateTemplate().save(
							currentRow);
					table.getRows().add(currentRow);

				} else if ("Attribute".equals(localName)) {

					String name = attributes.getValue("name");
					String value = attributes.getValue("value");
					QueryResultColumn col = cols.get(name);
					if (col == null) {
						col = new QueryResultColumn();
						col.setName(name);
						col.setTable(table);
						getQueryResultTableDao().getHibernateTemplate().save(
								col);
						cols.put(name, col);
					}
					QueryResultCell cell = new QueryResultCell();
					cell.setRow(currentRow);
					cell.setColumn(col);
					cell.setValue(value);
					getQueryResultTableDao().getHibernateTemplate().save(cell);
					currentRow.getCells().add(cell);
				}
			} else if (ResultType.OBJECT.equals(resultType)) {

				if ("ObjectResult".equals(localName)) {
					if (currentRow != null) {
						getQueryResultTableDao().getHibernateTemplate().save(
								currentRow);
					}
					currentRow = new QueryResultRow();
					currentRow.setTable(table);
					getQueryResultTableDao().getHibernateTemplate().save(
							currentRow);
					table.getRows().add(currentRow);

				} else if ("ObjectResult".equals(elementStack.get(elementStack
						.size() - 2).localName)) {

					for (int i = 0; i < attributes.getLength(); i++) {

						String name = attributes.getLocalName(i);
						String value = attributes.getValue(i);
						QueryResultColumn col = cols.get(name);
						if (col == null) {
							col = new QueryResultColumn();
							col.setName(name);
							col.setTable(table);
							getQueryResultTableDao().getHibernateTemplate()
									.save(col);
							cols.put(name, col);
						}
						QueryResultCell cell = new QueryResultCell();
						cell.setRow(currentRow);
						cell.setColumn(col);
						cell.setValue(value);
						getQueryResultTableDao().getHibernateTemplate().save(
								cell);
						currentRow.getCells().add(cell);

					}
				}
			}
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		elementStack.pop();
	}

	public void characters(char[] ch, int start, int length)
			throws org.xml.sax.SAXException {

	}

	public void endDocument() {
		if (ResultType.OBJECT.equals(resultType) && getColumnNames() != null
				&& getColumnNames().size() > 0) {
			for (String colName : getColumnNames()) {
				if (!cols.containsKey(colName)) {
					QueryResultColumn col = new QueryResultColumn();
					col.setName(colName);
					col.setTable(table);
					getQueryResultTableDao().getHibernateTemplate().save(col);
					cols.put(colName, col);
				}
			}

		}
		try {
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gOut = new GZIPOutputStream(out);
			gOut.write(table.getQueryInstance().getResult().getBytes());
			table.getQueryInstance().setResult(null);
			
			QueryResultData data = new QueryResultData();
			data.setData(out.toByteArray());
			
			getQueryResultTableDao().getHibernateTemplate().save(data);
			table.setData(data);
			getQueryResultTableDao().save(table);
		} catch (Exception ex) {
			throw new RuntimeException("Error saving data: " + ex.getMessage(),
					ex);
		}
	}

	@Override
	public List<String> getColumnNames() {
		if (super.getColumnNames() == null
				|| super.getColumnNames().size() == 0) {
			return getQueryResultColumnNameResolver().getColumnNames(
					getTable().getQueryInstance().getId());
		}
		return super.getColumnNames();
	}

	public QueryResultTable getTable() {
		return table;
	}

	public static void main(String[] args) throws Exception {
		SAXParserFactory fact = SAXParserFactory.newInstance();
		fact.setNamespaceAware(true);
		SAXParser parser = fact.newSAXParser();
		XMLQueryResultToQueryResultTableHandler handler = new XMLQueryResultToQueryResultTableHandler();
		parser.parse(new FileInputStream("tissueQueryResults_dcql_atts.xml"),
				handler);
		System.out.println(handler.getTable());
	}

	public int getMaxValueLength() {
		return maxValueLength;
	}

	public void setMaxValueLength(int maxValueLength) {
		this.maxValueLength = maxValueLength;
	}

	public QueryResultTableDao getQueryResultTableDao() {
		return queryResultTableDao;
	}

	public void setQueryResultTableDao(QueryResultTableDao queryResultTableDao) {
		this.queryResultTableDao = queryResultTableDao;
	}

	public QueryResultColumnNameResolver getQueryResultColumnNameResolver() {
		return queryResultColumnNameResolver;
	}

	public void setQueryResultColumnNameResolver(
			QueryResultColumnNameResolver queryResultColumnNameResolver) {
		this.queryResultColumnNameResolver = queryResultColumnNameResolver;
	}
}
