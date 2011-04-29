package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration
public class JSONTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	WorkflowDescription wd;
	@Autowired
	ObjectMapper om;
	
	@Test
	public void testJacksion() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workflow", wd);
		om.writeValue( System.out, model);
	}
	
}
