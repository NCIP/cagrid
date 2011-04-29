package gov.nih.nci.cagrid.portal.portlet.workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.util.Map;

import org.junit.Test;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

public abstract class AbstractWorkflowRegistryTest extends AbstractJUnit4SpringContextTests {

	@Test
	public void testGetWorkflows() throws WorkflowException {
		Map<String, WorkflowDescription> workflows = getRegistry().getWorkflows();
		assertNotNull(workflows);
	}
	@Test
	public void testGetWorkflow() throws WorkflowException {
		WorkflowDescription wd = getRegistry().getWorkflow("1508");
		assertNotNull(wd);
		assertEquals("1508", wd.getId());
	}

	public abstract WorkflowRegistryService getRegistry();
}
