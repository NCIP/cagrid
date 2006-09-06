package gov.nih.nci.cagrid.cadsr.common;

import java.rmi.RemoteException;

/**
 * This class is autogenerated, DO NOT EDIT.
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public interface CaDSRServiceI {

public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException ;
public gov.nih.nci.cadsr.umlproject.domain.Project[] findAllProjects() throws RemoteException ;
public gov.nih.nci.cadsr.umlproject.domain.Project[] findProjects(java.lang.String context) throws RemoteException ;
public gov.nih.nci.cadsr.umlproject.domain.UMLPackageMetadata[] findPackagesInProject(gov.nih.nci.cadsr.umlproject.domain.Project project) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata[] findClassesInProject(gov.nih.nci.cadsr.umlproject.domain.Project project) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata[] findClassesInPackage(gov.nih.nci.cadsr.umlproject.domain.Project project,java.lang.String packageName) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cagrid.metadata.dataservice.DomainModel generateDomainModelForProject(gov.nih.nci.cadsr.umlproject.domain.Project project) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cagrid.metadata.dataservice.DomainModel generateDomainModelForPackages(gov.nih.nci.cadsr.umlproject.domain.Project project,java.lang.String[] packageNames) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cagrid.metadata.dataservice.DomainModel generateDomainModelForClassesWithExcludes(gov.nih.nci.cadsr.umlproject.domain.Project project,java.lang.String[] fullClassNames,gov.nih.nci.cagrid.cadsr.domain.UMLAssociationExclude[] associationExcludes) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cadsr.umlproject.domain.UMLAttributeMetadata[] findAttributesInClass(gov.nih.nci.cadsr.umlproject.domain.Project project,gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata clazz) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cadsr.umlproject.domain.SemanticMetadata[] findSemanticMetadataForClass(gov.nih.nci.cadsr.umlproject.domain.Project project,gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata clazz) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cadsr.domain.ValueDomain findValueDomainForAttribute(gov.nih.nci.cadsr.umlproject.domain.Project project,gov.nih.nci.cadsr.umlproject.domain.UMLAttributeMetadata attribute) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cagrid.cadsr.domain.UMLAssociation[] findAssociationsForClass(gov.nih.nci.cadsr.umlproject.domain.Project project,gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata clazz) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cagrid.cadsr.domain.UMLAssociation[] findAssociationsInPackage(gov.nih.nci.cadsr.umlproject.domain.Project project,java.lang.String packageName) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cagrid.cadsr.domain.UMLAssociation[] findAssociationsInProject(gov.nih.nci.cadsr.umlproject.domain.Project project) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;
public gov.nih.nci.cagrid.metadata.dataservice.DomainModel generateDomainModelForClasses(gov.nih.nci.cadsr.umlproject.domain.Project project,java.lang.String[] fullClassNames) throws RemoteException, gov.nih.nci.cagrid.cadsr.stubs.types.InvalidProjectException ;

}
