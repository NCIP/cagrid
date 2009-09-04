package org.cagrid.cacore.sdk4x.cql2.test;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.cql2.components.CQLQuery;
import gov.nih.nci.cagrid.metadata.MetadataUtils;
import gov.nih.nci.cagrid.metadata.dataservice.DomainModel;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.cagrid.cacore.sdk4x.cql2.processor.CQL2ToParameterizedHQL;
import org.cagrid.cacore.sdk4x.cql2.processor.ParameterizedHqlQuery;

import junit.framework.TestCase;

public abstract class AbstractCQL2ToHQLConversionTestCase extends TestCase {
    
    public static final String CQL2_EXAMPLES_LOCATION = "test/docs/cql2Examples";
    public static final boolean CASE_INSENSITIVE_QUERIES = false;
    
    private CQL2ToParameterizedHQL cqlTranslator = null;
    
    public AbstractCQL2ToHQLConversionTestCase(String name) {
        super(name);
    }
    

    protected abstract String getSdkLibDir();
    
    
    protected abstract String getDomainModelFilename();
    
    
    public void setUp() {
        DomainModel model = getDomainModel();
        // load up libraries for the SDK version being tested
        File libDir = new File(getSdkLibDir());
        assertTrue("SDK library dir " + libDir.getAbsolutePath() + " not found", libDir.exists());
        File[] jars = libDir.listFiles(new FilenameFilter() {
            
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jar");
            }
        });
        URL[] jarUrls = new URL[jars.length];
        for (int i = 0; i < jars.length; i++) {
            try {
                jarUrls[i] = jars[i].toURL();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                fail("Error converting file " + jars[i].getAbsolutePath() + " to a URL: " + ex.getMessage());
            }
        }
        URLClassLoader sdkLibClassloader = new URLClassLoader(jarUrls, Thread.currentThread().getContextClassLoader());
        try {
            Class<?> translatorClass = sdkLibClassloader.loadClass(CQL2ToParameterizedHQL.class.getName());
            Constructor<?> translatorConstructor = translatorClass.getConstructor(DomainModel.class, boolean.class);
            cqlTranslator = (CQL2ToParameterizedHQL) translatorConstructor.newInstance(model, CASE_INSENSITIVE_QUERIES);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Error instantiating CQL2 Translator: " + ex.getMessage());
        }
    }
    
    
    public void testAllAttributePredicates() {
        translateQuery("allAttributePredicates.xml");
    }
    
    
    public void testTopLevelStringAttribute() {
        translateQuery("topLevelStringAttribute.xml");
    }
    
    
    public void testPlainTargetObject() {
        translateQuery("plainTargetObject.xml");
    }
    
    
    protected void translateQuery(String filename) {
        CQLQuery query = loadQuery(filename);
        ParameterizedHqlQuery hql = null;
        try {
            hql = cqlTranslator.convertToHql(query);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Error converting CQL2 to HQL: " + ex.getMessage());
        }
        System.out.println("Converted CQL2 (" + filename + ") to HQL");
        System.out.println("\t" + hql);
    }
    
    
    protected CQLQuery loadQuery(String filename) {
        CQLQuery query = null;
        File cqlFile = new File(CQL2_EXAMPLES_LOCATION + File.separator + filename);
        assertTrue("Query file " + cqlFile.getAbsolutePath() + " not found", cqlFile.exists());
        try {
            FileReader reader = new FileReader(cqlFile);
            query = (CQLQuery) Utils.deserializeObject(reader, CQLQuery.class);
            reader.close();
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
