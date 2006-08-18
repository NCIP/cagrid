package gov.nih.nci.cagrid.gridgrouper.subject;
import edu.internet2.middleware.subject.Source;
import edu.internet2.middleware.subject.provider.SubjectTypeEnum;

/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @author <A href="mailto:ervin@bmi.osu.edu">David Ervin </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */
public class GridHostSubject extends GridSubject {
	public GridHostSubject(String id, Source source) {
	   super(id,SubjectTypeEnum.APPLICATION,source);
	}
}
