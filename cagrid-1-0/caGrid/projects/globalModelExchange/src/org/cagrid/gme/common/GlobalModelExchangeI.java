package org.cagrid.gme.common;

import java.rmi.RemoteException;

/**
 * The Global Model Exchange (GME) acts as an authoritative source
 *         for XML Schemas, providing a robust type management system for
 *         use in Grids and other XML-based environments.
 *
 * This class is autogenerated, DO NOT EDIT.
 * This interface represents the API
 * which is accessable on the grid service from the client.
 * 
 * @created by Introduce Toolkit version 1.0
 */
public interface GlobalModelExchangeI {

    public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata()
        throws RemoteException;

  /**
   * Publishes the provided schemas, replacing existing versions as necessary.
   *
   * @param schemas
   *	The schemas to publish
   */
  public void publishXMLSchemas(org.cagrid.gme.domain.XMLSchema[] schemas) throws RemoteException ;

  /**
   * Returns a single XML Schema, not including any referenced (i.e. imported) XML Schemas
   *
   * @param targetNamespace
   *	A namespace representing the targetNamespace of the desired XMLSchema
   * @return The XMLSchema with a targetNamespace equal to the given XMLSchemaNamespace
   */
  public org.cagrid.gme.domain.XMLSchema getXMLSchema(org.cagrid.gme.domain.XMLSchemaNamespace targetNamespace) throws RemoteException ;

  /**
   * Returns the targetNamespaces (represented by XMLSchemaNamespaces) of all published XMLSchemas
   *
   * @return the targetNamespaces (represented by XMLSchemaNamespaces) of all published XMLSchemas 
   */
  public org.cagrid.gme.domain.XMLSchemaNamespace getXMLSchemaNamespaces() throws RemoteException ;

  /**
   * Returns an XMLSchemaBundle containing the transitive closure of the XMLSchema with the targetNamespace of the given XMLSchemaNamespace and every XMLSchema it imports (as well as each imported XMLSchema's imports)
   *
   * @param targetNamespace
   *	The targetNamespace of the XMLSchema of interest
   * @return an XMLSchemaBundle containing the transitive closure of the XMLSchema with the targetNamespace of the given XMLSchemaNamespace and every XMLSchema it imports (as well as each imported XMLSchema's imports)
   */
  public org.cagrid.gme.domain.XMLSchemaBundle getXMLSchemaAndDependencies(org.cagrid.gme.domain.XMLSchemaNamespace targetNamespace) throws RemoteException ;

  /**
   * Deletes the XMLSchemas with targetNamespaces identified by the given XMLSchemaNamespace array
   *
   * @param targetNamespaces
   *	The targetNamespaces of the XMLSchemas to delete
   */
  public void deleteXMLSchemas(org.cagrid.gme.domain.XMLSchemaNamespace[] targetNamespaces) throws RemoteException ;

  /**
   * Returns the targetNamespaces of all the XMLSchemas which are imported by the given XMLSchema (identififed by its targetNamespace)
   *
   * @param targetNamespace
   *	The targetNamespace of the XMLSchema of interest
   * @return The imported XMLSchemas' targetNamespaces
   */
  public org.cagrid.gme.domain.XMLSchemaNamespace[] getImportedXMLSchemaNamespaces(org.cagrid.gme.domain.XMLSchemaNamespace targetNamespace) throws RemoteException ;

  /**
   * Returns all the targetNamespaces of the XMLSchemas which import the given XMLSchema, identified its targetNamespace
   *
   * @param targetNamespace
   *	The targetNamespace of the XMLSchema of interest
   * @return all the targetNamespaces of the XMLSchemas which import the given XMLSchema, identified its targetNamespace
   */
  public org.cagrid.gme.domain.XMLSchemaNamespace[] getImportingXMLSchemaNamespaces(org.cagrid.gme.domain.XMLSchemaNamespace targetNamespace) throws RemoteException ;

  public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException ;

  public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException ;

  public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException ;

}
