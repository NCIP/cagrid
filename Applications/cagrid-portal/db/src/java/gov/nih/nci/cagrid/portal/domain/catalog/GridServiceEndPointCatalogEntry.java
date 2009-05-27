package gov.nih.nci.cagrid.portal.domain.catalog;

import gov.nih.nci.cagrid.portal.domain.GridService;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("tool_grid_service_endpoint")
public class GridServiceEndPointCatalogEntry extends ToolDeploymentCatalogEntry {

    public GridService about;

    @OneToOne
    @JoinColumn(name = "grid_service_id")
    public GridService getAbout() {
        return about;
    }

    public void setAbout(GridService about) {
        about.setCatalog(this);
        this.about = about;
    }
}