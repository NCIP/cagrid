package gov.nih.nci.cagrid.gridgrouper.subject;

import java.util.Set;

import edu.internet2.middleware.subject.SourceUnavailableException;
import edu.internet2.middleware.subject.Subject;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import edu.internet2.middleware.subject.provider.BaseSourceAdapter;
import edu.internet2.middleware.subject.provider.SubjectTypeEnum;


/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */
public class GridSourceAdapter extends BaseSourceAdapter {

	public GridSourceAdapter() {
		super();
		this.addSubjectType(SubjectTypeEnum.PERSON.getName());
		this.addSubjectType(SubjectTypeEnum.APPLICATION.getName());
	}


	public GridSourceAdapter(String id, String name) {
		super(id, name);
		this.addSubjectType(SubjectTypeEnum.PERSON.getName());
		this.addSubjectType(SubjectTypeEnum.APPLICATION.getName());
	}


	public Subject getSubject(String id) throws SubjectNotFoundException {
		return createSubject(id);
	}


	public Subject getSubjectByIdentifier(String name) throws SubjectNotFoundException {
		return createSubject(id);
	}


	private Subject createSubject(String id) throws SubjectNotFoundException {
		if ((id == null) || (id.equals(AnonymousGridUserSubject.ANONYMOUS_GRID_USER_ID))) {
			return new AnonymousGridUserSubject(this);
		} else if (id.indexOf("CN=host/") != -1) {
			validateGridId(id);
			return new GridHostSubject(id, this);
		} else {
			validateGridId(id);
			return new GridUserSubject(id, this);
		}
	}
	
	private void validateGridId(String id) throws SubjectNotFoundException{
		if (!id.startsWith("/")) {
			throw new SubjectNotFoundException("The id " + id + " is not a valid grid identity.");
		}

		if (id.indexOf("CN=") == -1) {
			throw new SubjectNotFoundException("The id " + id + " is not a valid grid identity.");
		}
	}


	public void init() throws SourceUnavailableException {
		// Nothing
	} // public void init()


	public Set search(String searchValue) {
		return null;
	}

}
