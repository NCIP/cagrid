package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.AbstractWorkflowRegistryTest;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowRegistryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class MyExperimentRegistryTest extends AbstractWorkflowRegistryTest {

	@Autowired
	private MyExperimentWorkflowRegistry registry;
	
//	@Test
//	public void testSessionCookie() throws HttpException, IOException {
//		registry.getSessionCookie("kedzie", "Catch-22");
//	}

	@Override
	public WorkflowRegistryService getRegistry() {
		return this.registry;
	}
}
