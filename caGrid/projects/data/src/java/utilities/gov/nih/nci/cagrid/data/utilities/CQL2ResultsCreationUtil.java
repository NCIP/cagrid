package gov.nih.nci.cagrid.data.utilities;

import gov.nih.nci.cagrid.cql2.results.CQLObjectResult;
import gov.nih.nci.cagrid.cql2.results.CQLQueryResults;
import gov.nih.nci.cagrid.data.mapping.ClassToQname;
import gov.nih.nci.cagrid.data.mapping.Mappings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CQL2ResultsCreationUtil
 * Utility for creating CQL 2 Results
 * 
 * @author David
 */
public class CQL2ResultsCreationUtil {
    
    private static final Log LOG = LogFactory.getLog(CQL2ResultsCreationUtil.class);
    
    private Map<String, QName> classToQname = null;
    
    public CQL2ResultsCreationUtil(Mappings mappings) {
        classToQname = new HashMap<String, QName>();
        initializeMappings(mappings);
    }
    
    
    private void initializeMappings(Mappings mappings) {
        for (ClassToQname c2q : mappings.getMapping()) {
            classToQname.put(c2q.getClassName(), QName.valueOf(c2q.getQname()));
        }
    }
    

    public CQLQueryResults createObjectResults(List<Object> data, String targetClassname) throws ResultsCreationException {
        CQLQueryResults results = new CQLQueryResults();
        results.setTargetClassname(targetClassname);
        QName qname = classToQname.get(targetClassname);
        CQLObjectResult[] objectResults = new CQLObjectResult[data.size()];
        int index = 0;
        for (Object o : data) {
            MessageElement elem = new MessageElement(qname, o);
            objectResults[index] = new CQLObjectResult(new MessageElement[] {elem});
            index++;
        }
        return results;
    }
}
