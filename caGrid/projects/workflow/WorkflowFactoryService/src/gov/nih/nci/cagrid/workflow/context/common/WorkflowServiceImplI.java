package gov.nih.nci.cagrid.workflow.context.common;

import java.rmi.RemoteException;

/**
 * This class is autogenerated, DO NOT EDIT.
 * 
 * @created by Introduce Toolkit version 1.0
 * 
 */
public interface WorkflowServiceImplI {

    public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException ;

    public gov.nih.nci.cagrid.workflow.stubs.types.WorkflowStatusType start(gov.nih.nci.cagrid.workflow.stubs.types.StartInputType startInputElement) throws RemoteException, gov.nih.nci.cagrid.workflow.stubs.types.WorkflowExceptionType, gov.nih.nci.cagrid.workflow.context.stubs.types.StartCalledOnStartedWorkflowFaultType ;

    public gov.nih.nci.cagrid.workflow.stubs.types.WorkflowStatusType getStatus() throws RemoteException, gov.nih.nci.cagrid.workflow.stubs.types.WorkflowExceptionType ;

    public gov.nih.nci.cagrid.workflow.stubs.types.WorkflowStatusType pause() throws RemoteException, gov.nih.nci.cagrid.workflow.stubs.types.WorkflowExceptionType, gov.nih.nci.cagrid.workflow.context.stubs.types.CannotPauseWorkflowFaultType ;

    public gov.nih.nci.cagrid.workflow.stubs.types.WorkflowStatusType resume() throws RemoteException, gov.nih.nci.cagrid.workflow.stubs.types.WorkflowExceptionType, gov.nih.nci.cagrid.workflow.context.stubs.types.CannotResumeWorkflowFaultType ;

    public void cancel() throws RemoteException, gov.nih.nci.cagrid.workflow.stubs.types.WorkflowExceptionType, gov.nih.nci.cagrid.workflow.context.stubs.types.CannotCancelWorkflowFaultType ;

    public gov.nih.nci.cagrid.workflow.stubs.types.WorkflowOutputType getWorkflowOutput() throws RemoteException, gov.nih.nci.cagrid.workflow.stubs.types.WorkflowExceptionType ;

}
