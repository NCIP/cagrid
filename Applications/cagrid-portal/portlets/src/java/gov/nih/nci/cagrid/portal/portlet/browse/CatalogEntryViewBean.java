/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.browse;

import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.CatalogEntryRoleInstance;
import gov.nih.nci.cagrid.portal.domain.catalog.Citation;
import gov.nih.nci.cagrid.portal.domain.catalog.CommunityCatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.DataSetCatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.File;
import gov.nih.nci.cagrid.portal.domain.catalog.Hyperlink;
import gov.nih.nci.cagrid.portal.domain.catalog.InstitutionCatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry;
import gov.nih.nci.cagrid.portal.domain.catalog.ToolCatalogEntry;
import gov.nih.nci.cagrid.portal.portlet.util.TemporalComparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class CatalogEntryViewBean {

	private Map<BrowseTypeEnum, SortedSet<CatalogEntryRoleTypeViewBean>> entryTypeMap = new HashMap<BrowseTypeEnum, SortedSet<CatalogEntryRoleTypeViewBean>>();
	private SortedSet<Citation> orderedCitations;
	private CatalogEntry catalogEntry;
	private SortedSet<TemporalCommentableViewBean<Hyperlink>> orderedHyperlinks;
	private SortedSet<TemporalCommentableViewBean<File>> orderedFiles;
	private CatalogEntryRoleTypeViewBean pocRoleType;
	private String pocRelationshipTypeName;

	public CatalogEntryViewBean() {
	}
	
	public void initialize(){
		for (CatalogEntryRoleInstance sourceRole : getCatalogEntry().getRoles()) {
			CatalogEntryRoleInstance targetRole = sourceRole.getRelationship()
					.getRoleA();
			if (targetRole == sourceRole) {
				targetRole = sourceRole.getRelationship().getRoleB();
			}

			if (!handleSpecialRole(targetRole)) {

				BrowseTypeEnum entryType = null;
				CatalogEntry targetCe = targetRole.getCatalogEntry();
				if (targetCe instanceof PersonCatalogEntry) {
					entryType = BrowseTypeEnum.PERSON;
				} else if (targetCe instanceof InstitutionCatalogEntry) {
					entryType = BrowseTypeEnum.INSTITUTION;
				} else if (targetCe instanceof DataSetCatalogEntry) {
					entryType = BrowseTypeEnum.DATASET;
				} else if (targetCe instanceof ToolCatalogEntry) {
					entryType = BrowseTypeEnum.TOOL;
				} else if (targetCe instanceof CommunityCatalogEntry) {
					entryType = BrowseTypeEnum.COMMUNITY;
				}

				SortedSet<CatalogEntryRoleTypeViewBean> roleTypes = entryTypeMap
						.get(entryType);
				if (roleTypes == null) {
					roleTypes = new TreeSet<CatalogEntryRoleTypeViewBean>(
							new Comparator<CatalogEntryRoleTypeViewBean>() {
								public int compare(
										CatalogEntryRoleTypeViewBean c1,
										CatalogEntryRoleTypeViewBean c2) {
									return c1
											.getCatalogEntryRoleType()
											.getName()
											.compareTo(
													c2
															.getCatalogEntryRoleType()
															.getName());
								}
							});
					entryTypeMap.put(entryType, roleTypes);
				}
				CatalogEntryRoleTypeViewBean roleTypeVB = null;
				for (CatalogEntryRoleTypeViewBean aRoleTypeVB : roleTypes) {
					if (aRoleTypeVB.getCatalogEntryRoleType().getName().equals(
							targetRole.getType().getName())) {
						roleTypeVB = aRoleTypeVB;
						break;
					}
				}
				if (roleTypeVB == null) {
					roleTypeVB = new CatalogEntryRoleTypeViewBean(targetRole
							.getType());
					roleTypes.add(roleTypeVB);
				}
				roleTypeVB.addRoleInstance(targetRole);
			}

		}

		orderedCitations = new TreeSet<Citation>(new Comparator<Citation>() {
			public int compare(Citation c1, Citation c2) {
				return c1.getCitation().compareTo(c2.getCitation());
			}
		});
		orderedCitations.addAll(catalogEntry.getCitations());

		orderedHyperlinks = new TreeSet<TemporalCommentableViewBean<Hyperlink>>(
				new TemporalComparator());
		for (Hyperlink h : catalogEntry.getHyperlinks()) {
			orderedHyperlinks
					.add(new TemporalCommentableViewBean<Hyperlink>(h));
		}

		orderedFiles = new TreeSet<TemporalCommentableViewBean<File>>(
				new TemporalComparator());
		for (File f : catalogEntry.getFiles()) {
			orderedFiles.add(new TemporalCommentableViewBean<File>(f));
		}		
	}
	
	protected boolean handleSpecialRole(CatalogEntryRoleInstance role){
		boolean handle = false;
		
		//TODO: Enable handling of sub types of relationships
//		if(role.getRelationship().getType().getName().equals(
//				getPocRelationshipTypeName())){
		if(role.getType().getName().indexOf("Contact") != -1){
			handle = true;
			// Don't add to set of related items
			if (pocRoleType == null) {
				pocRoleType = new CatalogEntryRoleTypeViewBean(role
						.getType());
			}
			pocRoleType.addRoleInstance(role);
		}
		return handle;
	}

	public CatalogEntry getCatalogEntry() {
		return catalogEntry;
	}

	public SortedSet<CatalogEntryRoleTypeViewBean> getPersonRoleTypes() {
		return entryTypeMap.get(BrowseTypeEnum.PERSON);
	}

	public SortedSet<CatalogEntryRoleTypeViewBean> getInstitutionRoleTypes() {
		return entryTypeMap.get(BrowseTypeEnum.INSTITUTION);
	}

	public SortedSet<CatalogEntryRoleTypeViewBean> getToolRoleTypes() {
		return entryTypeMap.get(BrowseTypeEnum.TOOL);
	}

	public SortedSet<CatalogEntryRoleTypeViewBean> getDataSetRoleTypes() {
		return entryTypeMap.get(BrowseTypeEnum.DATASET);
	}

	public SortedSet<CatalogEntryRoleTypeViewBean> getCommunityRoleTypes() {
		return entryTypeMap.get(BrowseTypeEnum.COMMUNITY);
	}

	public SortedSet<Citation> getOrderedCitations() {
		return orderedCitations;
	}

	public SortedSet<TemporalCommentableViewBean<Hyperlink>> getOrderedHyperlinks() {
		return orderedHyperlinks;
	}

	public SortedSet<TemporalCommentableViewBean<File>> getOrderedFiles() {
		return orderedFiles;
	}

	public CatalogEntryRoleTypeViewBean getPocRoleType() {
		return pocRoleType;
	}

	public String getPocRelationshipTypeName() {
		return pocRelationshipTypeName;
	}

	public void setPocRelationshipTypeName(String pocRelationshipTypeName) {
		this.pocRelationshipTypeName = pocRelationshipTypeName;
	}

	public void setCatalogEntry(CatalogEntry catalogEntry) {
		this.catalogEntry = catalogEntry;
	}

}
