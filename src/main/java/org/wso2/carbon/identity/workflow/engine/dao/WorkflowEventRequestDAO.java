package org.wso2.carbon.identity.workflow.engine.dao;

public interface WorkflowEventRequestDAO {

    /**
     * Add who approves the relevant request.
     *
     * @param eventId      the request ID that need to be checked.
     * @param workflowId   workflow ID.
     * @param approverType the type of the approved user EX: user or Role.
     * @param approverName the value of the approver type.
     * @return
     */
    String addApproversOfRequest(String eventId, String workflowId, String approverType, String approverName);

    /**
     * Add what step to approve.
     *
     * @param eventId     the request ID that need to be checked.
     * @param currentStep the current step.
     */
    void createStatesOfRequest(String eventId, int currentStep);
}
