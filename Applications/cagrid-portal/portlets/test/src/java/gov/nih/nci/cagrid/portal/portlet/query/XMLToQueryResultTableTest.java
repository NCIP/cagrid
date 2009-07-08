/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.query;

import static org.junit.Assert.*;
import gov.nih.nci.cagrid.portal.AbstractDBTestBase;
import gov.nih.nci.cagrid.portal.domain.dataservice.DCQLQuery;
import gov.nih.nci.cagrid.portal.domain.dataservice.DCQLQueryInstance;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultTable;
import gov.nih.nci.cagrid.portal.portlet.query.results.XMLQueryResultToQueryResultTableHandler;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class XMLToQueryResultTableTest extends AbstractDBTestBase {

	@Test
	public void testParseDCQL() {
		try {
			List<String> colNames = new ArrayList<String>();
			colNames.add("id");
			colNames.add("lsidAuthority");
			colNames.add("lsidNamespace");
			colNames.add("lsidObjectId");
			colNames.add("name");
			colNames.add("assayType");
			colNames.add("version");
			DCQLQuery query = new DCQLQuery();
			DCQLQueryInstance queryInstance = new DCQLQueryInstance();
			queryInstance.setResult("some data");
			queryInstance.setQuery(query);
			XMLQueryResultToQueryResultTableHandler handler = new XMLQueryResultToQueryResultTableHandler();
			handler.setPersist(false);
			handler.setColumnNames(colNames);
			handler.getTable().setQueryInstance(queryInstance);
			SAXParserFactory fact = SAXParserFactory.newInstance();
			fact.setNamespaceAware(true);
			SAXParser parser = fact.newSAXParser();
			parser.parse(new FileInputStream(
					"test/data/caarray_dcql_results.xml"), handler);
			QueryResultTable table = handler.getTable();
			assertEquals("http://array.nci.nih.gov:80/wsrf/services/cagrid/CaArraySvc", table.getRows().get(0).getServiceUrl());
		} catch (Exception ex) {
			ex.printStackTrace();
			String msg = "Error encountered: " + ex.getMessage();
			fail(msg);
		}
	}

}
