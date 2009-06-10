package gov.nih.nci.cagrid.portal.portlet.query.cql;

import gov.nih.nci.cagrid.common.SchemaValidationException;
import gov.nih.nci.cagrid.common.SchemaValidator;
import gov.nih.nci.cagrid.portal.portlet.PortletConstants;
import org.springframework.validation.Errors;

import java.net.URL;

/**
 * Will validate CQL query against CQL schema
 * <p/>
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class CQLQuerySchemaValidator extends CQLQueryCommandValidator {


    // default values. Will be configured with DI
    String cqlSchema = "1_gov.nih.nci.cagrid.CQLQuery-1.3.xsd";
    String dcqlSchema = "Distributed_CQL_schema_2.0.xsd";

    @Override
    public void validate(Object target, Errors errors) {
        boolean error = false;

        CQLQueryCommand command = (CQLQueryCommand) target;
        if (command.getCqlQuery() == null) {
            logger.info("Query XML has not been set. Skipping validation");
            return;
        }

        try {
            SchemaValidator validator;
            if (!command.isDcql()) {
                URL schemaPath = getClass().getClassLoader().getResource(cqlSchema);
                validator = new SchemaValidator(schemaPath.getFile());

            } else {
                logger.debug("Is a DCQL query. WIll validate against DCQL query");
                URL schemaPath = getClass().getClassLoader().getResource(dcqlSchema);
                validator = new SchemaValidator(schemaPath.getFile());
            }
            validator.validate(command.getCqlQuery());
        } catch (SchemaValidationException e) {
            logger.info("Invalid query submitted", e);
            error = true;
        }

        if (error) {
            // using cqlQuery filed name for backward compatibility with UI
            errors.rejectValue("cqlQuery", PortletConstants.BAD_CQL_MSG,
                    "Could not validate query XML against XML Schema. Query XML is invalid");
        }

    }

    public String getCqlSchema() {
        return cqlSchema;
    }

    public void setCqlSchema(String cqlSchema) {
        this.cqlSchema = cqlSchema;
    }

    public String getDcqlSchema() {
        return dcqlSchema;
    }

    public void setDcqlSchema(String dcqlSchema) {
        this.dcqlSchema = dcqlSchema;
    }
}
