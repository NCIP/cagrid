package gov.nih.nci.cagrid.portal.domain.catalog;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("information_model")

public class InformationModelCatalogEntry extends DataSetCatalogEntry {
}