/*
 * Created on Sep 21, 2006
 */
package gov.nci.nih.cagrid.tests.core.steps;

import gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouperClient;

import com.atomicobject.haste.framework.Step;

public class GrouperAddMemberStep
	extends Step
{
	private String endpoint;
	private String group;
	private String subject;
	private boolean shouldFail = false;
	
	public GrouperAddMemberStep(String group, String subject)
	{
		this(group, subject, false, "https://localhost:9443/wsrf/services/cagrid/GridGrouper");
	}
	
	public GrouperAddMemberStep(String group, String subject, boolean shouldFail)
	{
		this(group, subject, shouldFail, "https://localhost:9443/wsrf/services/cagrid/GridGrouper");
	}
	
	public GrouperAddMemberStep(String group, String subject, boolean shouldFail, String endpoint)
	{
		super();
		
		this.endpoint = endpoint;
		this.group = group;
		this.subject = subject;
		this.shouldFail = shouldFail;
	}
	
	public void runStep() 
		throws Exception
	{
		GridGrouperClient grouper = new GridGrouperClient(endpoint);
		
		// add member
		try {
			grouper.addMember(new GroupIdentifier(null, group), subject);
			if (shouldFail) fail("addMember should fail");
		} catch (Exception e) {
			if (! shouldFail) throw e;
		}
	}
	
	public static void main(String[] args) 
		throws Exception
	{
		new GrouperAddMemberStep(
			"test:mygroup",
			"/O=OSU/OU=BMI/OU=caGrid/OU=Dorian/OU=localhost/OU=IdP [1]/CN=subject1"
		).runStep();
	}
}
