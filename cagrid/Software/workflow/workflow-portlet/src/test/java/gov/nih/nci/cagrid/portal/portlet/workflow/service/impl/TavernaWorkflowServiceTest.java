package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.WorkflowException;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.AbstractSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("")
public class TavernaWorkflowServiceTest  extends AbstractJUnit4SpringContextTests {
	
	@Test
	public void testSubmitWorkflow() throws WorkflowException {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("test-context.xml");
		 TavernaWorkflowService svc = ctx.getBean(TavernaWorkflowService.class);
		 EndpointReferenceType epr = svc.submitWorkflow("fishsoup", "/home/marek/java/work/workflow-portlet/src/test/resources/fishsoup.t2flow", null);
	}
}
