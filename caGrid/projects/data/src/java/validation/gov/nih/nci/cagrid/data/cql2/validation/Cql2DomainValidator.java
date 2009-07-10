package gov.nih.nci.cagrid.data.cql2.validation;

import gov.nih.nci.cagrid.cql2.components.CQLQuery;

/**
 * Cql2DomainValidator
 * Validates a CQL 2 query against a domain model
 * 
 * @author David
 */
public interface Cql2DomainValidator {

    public void validateAgainstDomainModel(CQLQuery query) throws DomainValidationException;
}
