package org.cagrid.cacore.sdk4x.cql2.test;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.metadata.MetadataUtils;
import gov.nih.nci.cagrid.metadata.dataservice.DomainModel;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.cagrid.cacore.sdk4x.cql2.processor.CQL2ToParameterizedHQL;
import org.cagrid.cacore.sdk4x.cql2.processor.HibernateConfigTypesInformationResolver;
import org.cagrid.cacore.sdk4x.cql2.processor.TypesInformationResolver;
import org.cagrid.cql.utilities.CQL2SerializationUtil;
import org.cagrid.cql2.CQLQuery;
import org.hibernate.cfg.Configuration;

public abstract class AbstractCQL2ExamplesTestCase extends TestCase {

    public static final String CQL2_EXAMPLES_LOCATION = "test/docs/cql2Examples";
    public static final boolean CASE_INSENSITIVE_QUERIES = false;
    
    protected CQL2ToParameterizedHQL cqlTranslator = null;
    
    public AbstractCQL2ExamplesTestCase(String name) {
        super(name);
    }
    
    
    protected abstract String getDomainModelFilename();
    
    
    public void setUp() {
        createTranslator();
    }
    
    
    protected abstract void testQuery(String queryFilename);
    
    
    public void testAllAttributePredicates() {
        testQuery("allAttributePredicates.xml");
    }
    
    
    public void testTopLevelStringAttribute() {
        testQuery("topLevelStringAttribute.xml");
    }
    
    
    public void testPlainTargetObject() {
        testQuery("plainTargetObject.xml");
    }
    
    
    public void testTargetWithAssociation() {
        testQuery("targetWithAssociation.xml");
    }
    
    
    public void testTargetWithAssociationWithAttribute() {
        testQuery("targetWithAssociationWithAttribute.xml");
    }
    
    
    public void testTargetWithNestedAssociation() {
        testQuery("targetWithNestedAssociation.xml");
    }
    
    
    public void testTargetWithNestedAssociationWithAttribute() {
        testQuery("targetWithNestedAssociationWithAttribute.xml");
    }
    
    
    public void testNestedGroups() {
        testQuery("nestedGroups.xml");
    }
    
    
    public void testDistinctAttributeOfTarget() {
        testQuery("distinctAttributeOfTarget.xml");
    }
    
    
    public void testNamedAttributesOfTarget() {
        testQuery("namedAttributesOfTarget.xml");
    }
    
    
    public void testCountDistinctAttributeOfTarget() {
        testQuery("countDistinctAttributeOfTarget.xml");
    }
    
    
    public void testMinDistinctAttributeOfTarget() {
        testQuery("minDistinctAttributeOfTarget.xml");
    }
    
    
    public void testMaxDistinctAttributeOfTarget() {
        testQuery("maxDistinctAttributeOfTarget.xml");
    }
    
    
    public void testTargetWithSubclassedAssociation() {
        testQuery("targetWithSubclassedAssociation.xml");
    }
    
    
    public void testDoubleTypeAttribute() {
        testQuery("doubleTypeAttribute.xml");
    }
    
    
    public void testFloatTypeAttribute() {
        testQuery("floatTypeAttribute.xml");
    }
    
    
    public void testIntegerTypeAttribute() {
        testQuery("integerTypeAttribute.xml");
    }
    
    
    public void testLongTypeAttribute() {
        testQuery("longTypeAttribute.xml");
    }
    
    
    protected void createTranslator() {
        Configuration config = new Configuration();
        InputStream configStream = getClass().getResourceAsStream("/hibernate.cfg.xml");
        assertNotNull("No hibernate configuration found", configStream);
        config.addInputStream(configStream);
        config.configure();
        try {
            configStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Error closing configuration stream: " + e.getMessage());
        }
        TypesInformationResolver resolver = new HibernateConfigTypesInformationResolver(config);
        cqlTranslator = new CQL2ToParameterizedHQL(resolver, CASE_INSENSITIVE_QUERIES);
    }
    
    
    protected CQLQuery loadQuery(String filename) {
        CQLQuery query = null;
        File cqlFile = new File(CQL2_EXAMPLES_LOCATION + File.separator + filename);
        assertTrue("Query file " + cqlFile.getAbsolutePath() + " not found", cqlFile.exists());
        try {
            String text = Utils.fileToStringBuffer(cqlFile).toString();
            query = CQL2SerializationUtil.deserializeCql2Query(text);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Error reading CQL2 query: " + ex.getMessage());
        }
        return query;
    }
    
    
    protected DomainModel getDomainModel() {
        DomainModel model = null;
        File modelFile = new File(getDomainModelFilename());
        assertTrue("Model file " + modelFile.getAbsolutePath() + " not found", modelFile.exists());
        FileReader reader = null;
        try {
            reader = new FileReader(modelFile);
            model = MetadataUtils.deserializeDomainModel(reader);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Error reading domain model: " + ex.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    fail("Unable to close model file reader: " + ex.getMessage());
                }
            }
        }
        return model;
    }
}
