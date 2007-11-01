/**
 * 
 */
package gov.nih.nci.cagrid.portal2.portlet.tab;

import gov.nih.nci.cagrid.portal2.portlet.ActionResponseHandler;
import gov.nih.nci.cagrid.portal2.portlet.CommandActionResponseHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.Errors;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class TabActionResponseHandler implements ActionResponseHandler,
		CommandActionResponseHandler {

	private static final Log logger = LogFactory
			.getLog(TabActionResponseHandler.class);

	private TabControlConfig tabControlConfig;

	private String newTabPath;

	private String errorTabPath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.cagrid.portal2.portlet.ActionPostHandler#handle(javax.portlet.ActionRequest,
	 *      javax.portlet.ActionResponse)
	 */
	public void handle(ActionRequest request, ActionResponse response) {
		handle(request, response, getNewTabPath());
	}

	public void handle(ActionRequest request, ActionResponse response,
			Object command, Errors errors) {
		if (errors != null && errors.hasErrors()) {
			handle(request, response, getErrorTabPath());
		} else {
			handle(request, response);
		}
	}

	private void handle(ActionRequest request, ActionResponse response,
			String newPath) {
		String paramName = getTabControlConfig().getSelectedPathParameterName();
		String path = getTabControlConfig().getTabModel().getCurrentPath();
		if (newPath != null) {
			logger.debug("old path = " + path);
			getTabControlConfig().getTabModel().select(newPath);
			path = getTabControlConfig().getTabModel().getCurrentPath();
			logger.debug("new path = " + path);
		}
		logger.debug("Setting " + paramName + " = " + path);
		response.setRenderParameter(paramName, path);
	}

	@Required
	public TabControlConfig getTabControlConfig() {
		return tabControlConfig;
	}

	public void setTabControlConfig(TabControlConfig tabControlConfig) {
		this.tabControlConfig = tabControlConfig;
	}

	public String getNewTabPath() {
		return newTabPath;
	}

	public void setNewTabPath(String newTabPath) {
		this.newTabPath = newTabPath;
	}

	public String getErrorTabPath() {
		return errorTabPath;
	}

	public void setErrorTabPath(String errorTabPath) {
		this.errorTabPath = errorTabPath;
	}

}
