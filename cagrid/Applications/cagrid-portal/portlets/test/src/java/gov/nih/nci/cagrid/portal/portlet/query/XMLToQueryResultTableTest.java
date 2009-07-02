/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.query;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import gov.nih.nci.cagrid.portal.AbstractDBTestBase;
import gov.nih.nci.cagrid.portal.TestDB;
import gov.nih.nci.cagrid.portal.aggr.regsvc.RegisteredServiceEvent;
import gov.nih.nci.cagrid.portal.aggr.regsvc.RegisteredServiceListener;
import gov.nih.nci.cagrid.portal.dao.DCQLQueryDao;
import gov.nih.nci.cagrid.portal.dao.DomainModelDao;
import gov.nih.nci.cagrid.portal.dao.GridServiceDao;
import gov.nih.nci.cagrid.portal.dao.IndexServiceDao;
import gov.nih.nci.cagrid.portal.dao.QueryResultTableDao;
import gov.nih.nci.cagrid.portal.dao.UMLClassDao;
import gov.nih.nci.cagrid.portal.dao.catalog.GridServiceEndPointCatalogEntryDao;
import gov.nih.nci.cagrid.portal.domain.GridDataService;
import gov.nih.nci.cagrid.portal.domain.dataservice.CQLQuery;
import gov.nih.nci.cagrid.portal.domain.dataservice.CQLQueryInstance;
import gov.nih.nci.cagrid.portal.domain.metadata.dataservice.DomainModel;
import gov.nih.nci.cagrid.portal.domain.table.QueryResultTable;
import gov.nih.nci.cagrid.portal.portlet.query.results.XMLQueryResultToQueryResultTableHandler;
import gov.nih.nci.cagrid.portal.util.DefaultCatalogEntryRelationshipTypesFactory;
import gov.nih.nci.cagrid.portal.util.Metadata;
import gov.nih.nci.cagrid.portal.util.MetadataUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class XMLToQueryResultTableTest extends AbstractDBTestBase {

	@Test
	public void testSave() {
		try {


//			RegisteredServiceListener listener = new RegisteredServiceListener();
//			listener.setGridServiceDao((GridServiceDao)TestDB.getApplicationContext().getBean("gridServiceDao"));
//			listener.setIndexServiceDao((IndexServiceDao)TestDB.getApplicationContext().getBean("indexServiceDao"));
//
//			TestDB.loadData("../aggr/test/data/GridServiceEndPointCatalog.xml");
//			DefaultCatalogEntryRelationshipTypesFactory b = (DefaultCatalogEntryRelationshipTypesFactory) TestDB
//					.getApplicationContext().getBean(
//							"defaultCatalogEntryRelationshipTypesFactory");
//			b.init();
//
//			MetadataUtils mockUtil = mock(MetadataUtils.class);
//			Metadata meta = new Metadata();
//			meta.smeta = (gov.nih.nci.cagrid.metadata.MetadataUtils
//					.deserializeServiceMetadata(new FileReader(
//							"../aggr/test/data/cabioServiceMetadata.xml")));
//			meta.dmodel = (gov.nih.nci.cagrid.metadata.MetadataUtils
//					.deserializeDomainModel(new FileReader(
//							"../aggr/test/data/cabioDomainModel.xml")));
//
//			doReturn(meta).when(mockUtil).getMetadata(anyString(), anyLong());
//			listener.setMetadataUtils(mockUtil);
//
//			RegisteredServiceEvent evt = mock(RegisteredServiceEvent.class);
//			doReturn("http://").when(evt).getServiceUrl();
//			doReturn("http://").when(evt).getIndexServiceUrl();
//
//			listener.persistService(evt);
//
//			XMLToQueryResultTableHandler handler = new XMLToQueryResultTableHandler();
//			handler.setDcqlQueryDao((DCQLQueryDao) TestDB
//					.getApplicationContext().getBean("dcqlQueryDao"));
//			handler.setDomainModelDao((DomainModelDao) TestDB
//					.getApplicationContext().getBean("domainModelDao"));
//			handler.setQueryResultTableDao((QueryResultTableDao) TestDB
//					.getApplicationContext().getBean("queryResultTableDao"));
//			handler.setUmlClassDao((UMLClassDao) TestDB.getApplicationContext()
//					.getBean("umlClassDao"));
//			
//			
//			
//
//			StringBuilder sb = new StringBuilder();
//			BufferedReader br = new BufferedReader(new FileReader(
//					"test/data/cabioGeneQueryResults.xml"));
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//			String xml = sb.toString();
//			CQLQuery query = new CQLQuery();
//			query.setXml(xml);
//			CQLQueryInstance instance = new CQLQueryInstance();
//			instance.setQuery(query);
//			handler.getTable().setQueryInstance(instance);
//			
//			DomainModel model = handler.getDomainModelDao().getAll().iterator().next();
//			GridDataService dataService = new GridDataService();
//			dataService.setDomainModel(model);
//			instance.setDataService(dataService);
//
//			SAXParserFactory fact = SAXParserFactory.newInstance();
//			fact.setNamespaceAware(true);
//			SAXParser parser = fact.newSAXParser();
//			parser.parse(new ByteArrayInputStream(xml.getBytes()), handler);
//			QueryResultTable table = handler.getTable();
//			System.out.println(table);
		} catch (Exception ex) {
			ex.printStackTrace();
			String msg = "Error encountered: " + ex.getMessage();
			fail(msg);
		}
	}

}
