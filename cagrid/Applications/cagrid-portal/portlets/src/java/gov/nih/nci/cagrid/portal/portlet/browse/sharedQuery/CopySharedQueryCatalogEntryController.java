package gov.nih.nci.cagrid.portal.portlet.browse.sharedQuery;

import gov.nih.nci.cagrid.portal.dao.catalog.CatalogEntryDao;
import gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryCatalogEntry;
import gov.nih.nci.cagrid.portal.portlet.UserModel;
import gov.nih.nci.cagrid.portal.portlet.browse.BrowseViewDetailsController;
import gov.nih.nci.cagrid.portal.portlet.browse.CatalogEntryFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.ModelAndView;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class CopySharedQueryCatalogEntryController extends BrowseViewDetailsController {

    private UserModel userModel;
    private CatalogEntryFactory catalogEntryFactory;
    private String sharedQueryEntryType = "SHAREDQUERY";
    private String queryCopyParam = "queryCopy";

    private CatalogEntryDao catalogEntryDao;

    private static final Log logger = LogFactory
            .getLog(CopySharedQueryCatalogEntryController.class);

    protected void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        Integer entryId = null;
        try {
            entryId = Integer.valueOf(request.getParameter("entryId"));
        } catch (Exception ex) {
            throw new RuntimeException("Error getting entryId from request: "
                    + ex.getMessage(), ex);
        }
        SharedQueryCatalogEntry catalogEntry = (SharedQueryCatalogEntry) getCatalogEntryDao().getById(entryId);
        if (catalogEntry == null) {
            throw new RuntimeException("Couldn't find catalog entry for id: "
                    + entryId);
        }

        logger.debug("Creating a copy of shared query");
        SharedQueryCatalogEntry copyCatalogEntry = (SharedQueryCatalogEntry) (getCatalogEntryFactory().newCatalogEntry(sharedQueryEntryType));
        copyCatalogEntry.setAreasOfFocus(catalogEntry.getAreasOfFocus());
        copyCatalogEntry.setDescription(copyCatalogEntry.getDescription());

        getUserModel().setCurrentCatalogEntry(copyCatalogEntry);
        response.setRenderParameter(queryCopyParam, catalogEntry.getAbout().getXml());
        response.setRenderParameter("operation", "copySharedQueryCatalog");
        response.setRenderParameter("viewMode", "edit");
    }

    protected ModelAndView handleRenderRequestInternal(RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
        ModelAndView mav = super.handleRenderRequestInternal(renderRequest, renderResponse);
        mav.addObject(queryCopyParam, renderRequest.getParameter(queryCopyParam));
        return mav;
    }


    public String getQueryCopyParam() {
        return queryCopyParam;
    }

    public void setQueryCopyParam(String queryCopyParam) {
        this.queryCopyParam = queryCopyParam;
    }

    public CatalogEntryDao getCatalogEntryDao() {
        return catalogEntryDao;
    }

    public void setCatalogEntryDao(CatalogEntryDao catalogEntryDao) {
        this.catalogEntryDao = catalogEntryDao;
    }

    public String getSharedQueryEntryType() {
        return sharedQueryEntryType;
    }

    public void setSharedQueryEntryType(String sharedQueryEntryType) {
        this.sharedQueryEntryType = sharedQueryEntryType;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public CatalogEntryFactory getCatalogEntryFactory() {
        return catalogEntryFactory;
    }

    public void setCatalogEntryFactory(CatalogEntryFactory catalogEntryFactory) {
        this.catalogEntryFactory = catalogEntryFactory;
    }
}
