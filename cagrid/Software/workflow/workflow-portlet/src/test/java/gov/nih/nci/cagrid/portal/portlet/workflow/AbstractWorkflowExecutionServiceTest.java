package gov.nih.nci.cagrid.portal.portlet.workflow;

import static org.junit.Assert.assertNotNull;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.junit.Test;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

public abstract class AbstractWorkflowExecutionServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Test
	public void testSubmitWorkflow() throws WorkflowException {
		String url = super.applicationContext.getResource("fishsoup.t2flow").getFilename();
		EndpointReferenceType epr = getExecutionService().submitWorkflow("fishsoup", url, null);
		assertNotNull("Endpoint Reference", epr);
	}
	
	public abstract WorkflowExecutionService getExecutionService();
}
