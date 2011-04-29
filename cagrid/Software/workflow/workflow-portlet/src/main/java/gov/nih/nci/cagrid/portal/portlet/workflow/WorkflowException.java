package gov.nih.nci.cagrid.portal.portlet.workflow;

import org.springframework.core.NestedCheckedException;

public class WorkflowException extends NestedCheckedException {
	private static final long serialVersionUID = 1L;

	public WorkflowException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkflowException(String message) {
		super(message);
	}
public WorkflowException(Throwable cause) {
	super(cause.getMessage(), cause);
}
}
