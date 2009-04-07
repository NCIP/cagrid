package gov.nih.nci.cagrid.portal.util;

import gov.nih.nci.cadsr.domain.Context;
import gov.nih.nci.cadsr.umlproject.domain.Project;
import gov.nih.nci.cagrid.cadsr.client.CaDSRServiceClient;
import gov.nih.nci.cagrid.cadsr.common.CaDSRServiceI;
import gov.nih.nci.cagrid.metadata.MetadataUtils;
import gov.nih.nci.cagrid.portal.aggr.MetadataThread;
import gov.nih.nci.cagrid.portal.domain.metadata.dataservice.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.gme.client.GlobalModelExchangeClient;
import org.cagrid.gme.domain.XMLSchemaNamespace;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.persistence.NonUniqueResultException;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XMLSchemaUtils {
    private static final Log logger = LogFactory.getLog(XMLSchemaUtils.class);


    public static List<XMLSchema> getXMLSchemas(DomainModel domainModel,
                                                String cadsrUrl, String gmeUrl) {
        List<XMLSchema> schemas = new ArrayList<XMLSchema>();

        Project proj = new Project();
        proj.setShortName(domainModel.getProjectShortName());
        proj.setVersion(domainModel.getProjectVersion());
        String context = "caBIG";
        try {
            CaDSRServiceI cadsrService = new CaDSRServiceClient(cadsrUrl);
            Context ctx = cadsrService.findContextForProject(proj);
            context = ctx.getName();
        } catch (Exception ex) {
            // logger.warn("Coudn't get context from project '"
            // + proj.getShortName() + "': " + ex.getMessage()
            // + ". Using '" + context + "'.", ex);
        }
        String projectVersion = proj.getVersion();
        if (projectVersion == null) {
            projectVersion = "1.0";
        }
        if (projectVersion.indexOf(".") < 0) {
            projectVersion += ".0";
        }
        Set<String> packageNames = new HashSet<String>();
        for (UMLClass klass : domainModel.getClasses()) {
            packageNames.add(klass.getPackageName());
        }
        for (String packageName : packageNames) {
            String schemaUrl = "gme://" + proj.getShortName() + "." + context
                    + "/" + projectVersion + "/" + packageName;
            String schemaContents = getXmlSchemaContent(schemaUrl, gmeUrl);
            if (schemaContents != null) {
                XMLSchema xmlSchema = new XMLSchema();
                xmlSchema.setNamespace(schemaUrl);
                xmlSchema.setSchemaContent(schemaContents);
                schemas.add(xmlSchema);
            }
        }
        return schemas;
    }

    public static String getXmlSchemaContent(String namespace, String gmeUrl) {
        String content = null;
        try {
            XMLSchemaNamespace ns = new XMLSchemaNamespace(namespace);
            GlobalModelExchangeClient client = new GlobalModelExchangeClient(gmeUrl);
            org.cagrid.gme.domain.XMLSchema schema = client.getXMLSchema(ns);
            content = schema.getRootDocument().getSchemaText();
        } catch (Exception ex) {
            // logger.warn("Error getting XML schema with namespace '" +
            // namespace
            // + "': " + ex.getMessage());
        }
        return content;
    }

    public static XMLSchema getXMLSchemaForQName(HibernateTemplate templ,
                                                 String qName, String gmeUrl) {
        XMLSchema xmlSchema = null;
        try {
            int idx = qName.indexOf("{");
            if (idx != -1) {
                String namespace = qName.substring(idx + 1, qName.indexOf("}",
                        idx + 1));
                XMLSchema eg = new XMLSchema();
                eg.setNamespace(namespace);
                eg = (XMLSchema) PortalUtils.getByExample(templ, eg);
                if (eg == null) {
                    String content = XMLSchemaUtils.getXmlSchemaContent(namespace,
                            gmeUrl);
                    if (content != null) {
                        xmlSchema = new XMLSchema();
                        xmlSchema.setNamespace(namespace);
                        xmlSchema.setSchemaContent(content);
                    }
                } else {
                    xmlSchema = eg;
                }
            }
        } catch (Exception ex) {
            // logger.warn("Couldn't get XMLSchema for QName '" + qName + "': "
            // + ex.getMessage());
        }
        if (xmlSchema != null) {
            XMLSchemaUtils.logger.debug("######### Found schema for QName: " + qName);
        }
        return xmlSchema;
    }
}