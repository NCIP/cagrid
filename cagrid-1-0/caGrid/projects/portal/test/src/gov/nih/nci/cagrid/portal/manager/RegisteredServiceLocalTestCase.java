package gov.nih.nci.cagrid.portal.manager;


import gov.nih.nci.cagrid.metadata.MetadataUtils;
import gov.nih.nci.cagrid.portal.BaseSpringDataAccessAbstractTestCase;
import gov.nih.nci.cagrid.portal.domain.DomainModel;
import gov.nih.nci.cagrid.portal.domain.RegisteredService;
import gov.nih.nci.cagrid.portal.domain.ResearchCenter;
import gov.nih.nci.cagrid.portal.utils.MetadataAggregatorUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 23, 2006
 * Time: 11:14:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class RegisteredServiceLocalTestCase extends BaseSpringDataAccessAbstractTestCase {

    private GridServiceManager gridServiceManager;
    private static final String DOMAIN_XML = "domainModel.xml";
    private static final String SERVICE_XML = "serviceMetadata.xml";
    private RegisteredService rService;


    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();    //To change body of overridden methods use File | Settings | File Templates.
        rService = new RegisteredService("http://test");
    }


    public void testRegisteredServiceWithModel() {

        MetadataAggregatorUtils mUtils = new MetadataAggregatorUtils();

        try {
            org.springframework.core.io.Resource resource = new ClassPathResource(DOMAIN_XML);

            try {
                DomainModel domainModel = mUtils.loadDomainModel(MetadataUtils.deserializeDomainModel(new FileReader(resource.getFile())));
                rService.setDomainModel(domainModel);
            } catch (Exception e) {
                fail(e.getMessage());
            }

            //Add RC
            try {
                org.springframework.core.io.Resource sMetaDataResource = new ClassPathResource(SERVICE_XML);
                ResearchCenter rc = mUtils.loadRC(MetadataUtils.deserializeServiceMetadata(new FileReader(sMetaDataResource.getFile())));
                gridServiceManager.save(rc);
                rService.setResearchCenter(rc);

            } catch (Exception e) {
                fail(e.getMessage());
            }

            gridServiceManager.save(rService);

            RegisteredService newService = (RegisteredService) gridServiceManager.getObjectByPrimaryKey(RegisteredService.class, rService.getPk());

            assertEquals(rService.getEPR(), newService.getEPR());


        } catch (Exception e) {

            fail(e.getMessage());
        }
        setComplete();
    }


    public void testRegisteredServiceWithOperations() {

        try {
            org.springframework.core.io.Resource resource = new ClassPathResource(SERVICE_XML);

            gov.nih.nci.cagrid.metadata.ServiceMetadata mData = MetadataUtils.deserializeServiceMetadata(new FileReader(resource.getFile()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


    public void setGridServiceManager(GridServiceManager gridServiceManager) {
        this.gridServiceManager = gridServiceManager;
    }
}
