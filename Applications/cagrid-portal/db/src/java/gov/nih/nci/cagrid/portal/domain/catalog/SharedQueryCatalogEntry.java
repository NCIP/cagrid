package gov.nih.nci.cagrid.portal.domain.catalog;

import gov.nih.nci.cagrid.portal.domain.dataservice.SharedCQLQuery;
import gov.nih.nci.cagrid.portal.domain.dataservice.CQLQuery;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("tool_shared_query")

public class SharedQueryCatalogEntry extends ToolCatalogEntry {

    public List<CriterionDescriptor> criteria = new ArrayList<CriterionDescriptor>();
    public List<QueryResultColumnDescriptor> columns = new ArrayList<QueryResultColumnDescriptor>();
    public List<SharedQueryToolsRelationship> toolRelationships = new ArrayList<SharedQueryToolsRelationship>();
    public List<Term> typesOfCancer = new ArrayList<Term>();
    private CQLQuery about;

    @OneToMany(mappedBy = "query")
    public List<CriterionDescriptor> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<CriterionDescriptor> criteria) {
        this.criteria = criteria;
    }

    @OneToMany(mappedBy = "query")
    public List<QueryResultColumnDescriptor> getColumns() {
        return columns;
    }

    public void setColumns(List<QueryResultColumnDescriptor> columns) {
        this.columns = columns;
    }

    @OneToMany(mappedBy = "sharedQuery")
    public List<SharedQueryToolsRelationship> getToolRelationships() {
        return toolRelationships;
    }

    public void setToolRelationships(List<SharedQueryToolsRelationship> toolRelationships) {
        this.toolRelationships = toolRelationships;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<Term> getTypesOfCancer() {
        return typesOfCancer;
    }

    public void setTypesOfCancer(List<Term> typesOfCancer) {
        this.typesOfCancer = typesOfCancer;
    }

    @OneToOne
    @JoinColumn(name = "cql_id")
    public CQLQuery getAbout() {
        return about;
    }

    public void setAbout(CQLQuery about) {
        this.about = about;
    }
}