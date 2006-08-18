package gov.nih.nci.cagrid.gridgrouper.common;

import edu.internet2.middleware.subject.Subject;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import gov.nih.nci.cagrid.gridgrouper.bean.MemberDescriptor;
import gov.nih.nci.cagrid.gridgrouper.bean.MemberType;
import gov.nih.nci.cagrid.gridgrouper.subject.GridGroupSubject;
import gov.nih.nci.cagrid.gridgrouper.subject.GridSourceAdapter;
import gov.nih.nci.cagrid.gridgrouper.subject.NonGridSourceAdapter;

/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @author <A href="mailto:ervin@bmi.osu.edu">David Ervin </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */
public class SubjectUtils {

	public static final GridSourceAdapter GRID_SOURCE = new GridSourceAdapter(
			"grid", "Grid Grouper: Grid Source Adapter");

	public static final NonGridSourceAdapter NON_GRID_SOURCE = new NonGridSourceAdapter(
			"Unknown", "Grid Grouper: Unknown Source Adapter");

	public static Subject getSubject(String id) throws SubjectNotFoundException {
		return getSubject(id, false);
	}

	public static Subject getSubject(String id, boolean allowNonGridSubject)
			throws SubjectNotFoundException {
		try {
			return GRID_SOURCE.getSubject(id);

		} catch (SubjectNotFoundException e) {
			if (allowNonGridSubject) {
				return NON_GRID_SOURCE.getSubject(id);
			} else {
				throw e;
			}
		}

	}

	public static Subject getSubject(MemberDescriptor des)
			throws SubjectNotFoundException {
		if (des.getMemberType().equals(MemberType.Grid)) {
			return GRID_SOURCE.getSubject(des.getSubjectId());
		} else if (des.getMemberType().equals(MemberType.Other)) {
			return NON_GRID_SOURCE.getSubject(des.getSubjectId());
		} else if (des.getMemberType().equals(MemberType.GrouperGroup)) {
			return new GridGroupSubject(des.getSubjectId(), des
					.getSubjectName(), null);
		} else {
			throw new SubjectNotFoundException(des.getSubjectId()
					+ " was not found.");
		}
	}
}
