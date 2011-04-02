package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;
import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
public class MyExperimentRegistryTest  extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MyExperimentWorkflowRegistry reg;
	
	public MyExperimentRegistryTest() {
		
	}
	
	@Test
	public void testSessionCookie() throws HttpException, IOException {
		reg.getSessionCookie();
	}
	@Test
	public void testGetWorkflows() throws WorkflowException {
		Map<String, WorkflowDescription> workflows = reg.getWorkflows();
		assertNotNull(workflows);
	}
	@Test
	public void testGetWorkflow() throws WorkflowException {
		WorkflowDescription wd = reg.getWorkflow("1508");
		assertNotNull(wd);
		assertEquals("1508", wd.getId());
	}
}
