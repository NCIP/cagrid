package gov.nih.nci.cagrid.cadsr.common;

import java.rmi.RemoteException;


/**
 * This class is autogenerated, DO NOT EDIT.
 * 
 * @created by caGrid toolkit version 0.5
 */
public interface CaDSRServiceI {












































     public gov.nih.nci.cadsr.umlproject.domain.Project[] findAllProjects() throws RemoteException ;
     public gov.nih.nci.cadsr.umlproject.domain.Project[] findProjects(String context) throws RemoteException ;
     public gov.nih.nci.cadsr.umlproject.domain.UMLPackageMetadata[] findPackagesInProject(gov.nih.nci.cadsr.umlproject.domain.Project project) throws RemoteException ;
     public gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata[] findClassesInProject(gov.nih.nci.cadsr.umlproject.domain.Project project) throws RemoteException ;
     public gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata[] findClassesInPackage(gov.nih.nci.cadsr.umlproject.domain.UMLPackageMetadata pkg) throws RemoteException ;
     public String[] generateMetadataExtractForProject(gov.nih.nci.cadsr.umlproject.domain.Project project) throws RemoteException ;
     public String generateMetadataExtractForPackages(gov.nih.nci.cadsr.umlproject.domain.UMLPackageMetadata[] packages) throws RemoteException ;
     public String generateMetadataExtractForClasses(gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata[] classes) throws RemoteException ;
     public gov.nih.nci.cadsr.umlproject.domain.UMLAttributeMetadata[] findAttributesInClass(gov.nih.nci.cadsr.umlproject.domain.UMLClassMetadata clazz) throws RemoteException ;

}
