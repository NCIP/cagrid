package org.cagrid.demo.photosharing.utils;

import org.cagrid.demo.photosharing.utils.exceptions.AuthorizationException;

import edu.internet2.middleware.grouper.InsufficientPrivilegeException;
import edu.internet2.middleware.grouper.MemberAddException;
import edu.internet2.middleware.grouper.MemberDeleteException;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import gov.nih.nci.cagrid.gridgrouper.bean.GroupIdentifier;
import gov.nih.nci.cagrid.gridgrouper.bean.LogicalOperator;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipExpression;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipQuery;
import gov.nih.nci.cagrid.gridgrouper.bean.MembershipStatus;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouper;
import gov.nih.nci.cagrid.gridgrouper.grouper.GroupI;

/**
 * This is an image that returns data only if the caller has sufficient authorization.
 * This class maintains a group of users that can access the image. If this group has no members, then everyone can access the image.
 * If the group has one or more members, then only users who are members can access the image.
 * @author jpermar
 *
 */
public class GrouperImage {

	private Image image;
	private MembershipExpression membershipExpression;
	private GridGrouper grouper;
	private GroupI group;

	/**
	 * 
	 * @param i
	 * @param membershipExpression
	 * @param grouper
	 * @param group need group instance to add to blacklist group
	 */
	public GrouperImage(Image i, String grouperURL, GridGrouper grouper, GroupI group) {
		this.image = i;
		this.membershipExpression = createAllowedViewersMembershipExpression(grouperURL, group);
		this.grouper = grouper;
		this.group = group;
	}
	
	private MembershipExpression createAllowedViewersMembershipExpression(String grouperURL, GroupI group) {
		GroupIdentifier groupIdentifier = new GroupIdentifier(grouperURL, group.getName());	
		MembershipQuery[] query = new MembershipQuery[] { new MembershipQuery(groupIdentifier, MembershipStatus.MEMBER_OF) };
		MembershipExpression expression = new MembershipExpression(LogicalOperator.AND,
				new MembershipExpression[0],
				query);
		return expression;
	}
	
	/**
	 * This operation returns description if the authorization group is empty or if it's non-empty and the caller is a member of the group
	 * @param callerIdentity
	 * @return
	 * @throws AuthorizationException
	 */
	public ImageDescription getDescription(String callerIdentity) throws AuthorizationException {
		if (GroupUtils.isEmptyGroup(this.group)) {
			return this.image.getDescription();
		} else if (!(GroupUtils.isEmptyGroup(this.group)) && (GroupUtils.memberCheck(this.grouper, callerIdentity, this.membershipExpression))) {
			return this.image.getDescription();
		} else {
			throw new AuthorizationException("getDescription unauthorized! " + callerIdentity + " did not pass membership expression: " + this.membershipExpression);
		}
	}

	/**
	 * This operation returns the image data if the authorization group is empty or if it's non-empty and the caller is a member of the group
	 * @param callerIdentity
	 * @return
	 * @throws AuthorizationException
	 */
	public Image getImage(String callerIdentity) throws AuthorizationException {
		if (GroupUtils.isEmptyGroup(this.group)) {
			return this.image;
		} else if (!(GroupUtils.isEmptyGroup(this.group)) && (GroupUtils.memberCheck(this.grouper, callerIdentity, this.membershipExpression))) {
			return this.image;
		} else {
			throw new AuthorizationException("getImageData unauthorized! " + callerIdentity + " did not pass membership expression: " + this.membershipExpression);
		}
	}
	
	public void addAllowedUser(String identity) throws InsufficientPrivilegeException, MemberAddException, SubjectNotFoundException {
		if (!(GroupUtils.hasMember(this.group, identity))) {
			GroupUtils.addGroupMember(this.group, identity);
		}
	}

	public void removeAllowedUser(String identity) throws SubjectNotFoundException, InsufficientPrivilegeException, MemberDeleteException {
		if (GroupUtils.hasMember(this.group, identity)) {
			GroupUtils.removeGroupMember(this.group, identity);
		}
	}
	
}
