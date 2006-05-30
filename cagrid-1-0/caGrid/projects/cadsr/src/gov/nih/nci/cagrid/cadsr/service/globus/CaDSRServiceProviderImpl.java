package gov.nih.nci.cagrid.cadsr.service.globus;

import gov.nih.nci.cagrid.cadsr.common.CaDSRServiceI;
import gov.nih.nci.cagrid.cadsr.service.CaDSRServiceImpl;

import java.rmi.RemoteException;


/**
 * DO NOT EDIT: This class is autogenerated!
 * 
 * @created by caGrid toolkit version 1.0
 */
public class CaDSRServiceProviderImpl {

	CaDSRServiceI impl;


	public CaDSRServiceProviderImpl() {
		impl = new CaDSRServiceImpl();
	}


















































































































































































	public gov.nih.nci.cagrid.cadsr.stubs.FindAllProjectsResponse findAllProjects(gov.nih.nci.cagrid.cadsr.stubs.FindAllProjects params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindAllProjectsResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindAllProjectsResponse();
		boxedResult.setProject(impl.findAllProjects());
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindProjectsResponse findProjects(gov.nih.nci.cagrid.cadsr.stubs.FindProjects params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindProjectsResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindProjectsResponse();
		boxedResult.setProject(impl.findProjects(params.getContext()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindPackagesInProjectResponse findPackagesInProject(gov.nih.nci.cagrid.cadsr.stubs.FindPackagesInProject params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindPackagesInProjectResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindPackagesInProjectResponse();
		boxedResult.setUMLPackageMetadata(impl.findPackagesInProject(params.getProject().getProject()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindClassesInProjectResponse findClassesInProject(gov.nih.nci.cagrid.cadsr.stubs.FindClassesInProject params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindClassesInProjectResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindClassesInProjectResponse();
		boxedResult.setUMLClassMetadata(impl.findClassesInProject(params.getProject().getProject()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindClassesInPackageResponse findClassesInPackage(gov.nih.nci.cagrid.cadsr.stubs.FindClassesInPackage params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindClassesInPackageResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindClassesInPackageResponse();
		boxedResult.setUMLClassMetadata(impl.findClassesInPackage(params.getProject().getProject(),params.getPackageName()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForProjectResponse generateDomainModelForProject(gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForProject params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForProjectResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForProjectResponse();
		boxedResult.setDomainModel(impl.generateDomainModelForProject(params.getProject().getProject()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForPackagesResponse generateDomainModelForPackages(gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForPackages params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForPackagesResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForPackagesResponse();
		boxedResult.setDomainModel(impl.generateDomainModelForPackages(params.getProject().getProject(),params.getPackageNames()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForClassesResponse generateDomainModelForClasses(gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForClasses params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForClassesResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.GenerateDomainModelForClassesResponse();
		boxedResult.setDomainModel(impl.generateDomainModelForClasses(params.getProject().getProject(),params.getClasses().getUMLClassMetadata(),params.getAssociations().getUMLAssociation()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindAttributesInClassResponse findAttributesInClass(gov.nih.nci.cagrid.cadsr.stubs.FindAttributesInClass params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindAttributesInClassResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindAttributesInClassResponse();
		boxedResult.setUMLAttributeMetadata(impl.findAttributesInClass(params.getProject().getProject(),params.getClazz().getUMLClassMetadata()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindSemanticMetadataForClassResponse findSemanticMetadataForClass(gov.nih.nci.cagrid.cadsr.stubs.FindSemanticMetadataForClass params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindSemanticMetadataForClassResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindSemanticMetadataForClassResponse();
		boxedResult.setSemanticMetadata(impl.findSemanticMetadataForClass(params.getProject().getProject(),params.getClazz().getUMLClassMetadata()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindValueDomainForAttributeResponse findValueDomainForAttribute(gov.nih.nci.cagrid.cadsr.stubs.FindValueDomainForAttribute params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindValueDomainForAttributeResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindValueDomainForAttributeResponse();
		boxedResult.setValueDomain(impl.findValueDomainForAttribute(params.getProject().getProject(),params.getAttribute().getUMLAttributeMetadata()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsForClassResponse findAssociationsForClass(gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsForClass params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsForClassResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsForClassResponse();
		boxedResult.setUMLAssociation(impl.findAssociationsForClass(params.getProject().getProject(),params.getClazz().getUMLClassMetadata()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsInPackageResponse findAssociationsInPackage(gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsInPackage params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsInPackageResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsInPackageResponse();
		boxedResult.setUMLAssociation(impl.findAssociationsInPackage(params.getProject().getProject(),params.getPackageName()));
		return boxedResult;
	}

	public gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsInProjectResponse findAssociationsInProject(gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsInProject params) throws RemoteException {
		gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsInProjectResponse boxedResult = new gov.nih.nci.cagrid.cadsr.stubs.FindAssociationsInProjectResponse();
		boxedResult.setUMLAssociation(impl.findAssociationsInProject(params.getProject().getProject()));
		return boxedResult;
	}

}
