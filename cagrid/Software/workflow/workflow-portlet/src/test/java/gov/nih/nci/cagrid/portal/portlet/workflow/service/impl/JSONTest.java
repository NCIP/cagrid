package gov.nih.nci.cagrid.portal.portlet.workflow.service.impl;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

public class JSONTest {

	@Test
	public void testJacksion() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("String", "stinrg");
		ObjectMapper om = new ObjectMapper();
		StringWriter sw = new StringWriter();
		om.writeValue(sw, model);
		System.out.println("String: " + sw.toString());
	}
	
}
