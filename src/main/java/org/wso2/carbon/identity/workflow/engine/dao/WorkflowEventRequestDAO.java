package org.wso2.carbon.identity.workflow.engine.dao;

public interface WorkflowEventRequestDAO {

    /**
     * Add who approves the relevant request.
     *
     * @param taskId       random generated unique Id.
     * @param eventId      the request ID that need to be checked.
     * @param workflowId   workflow ID.
     * @param approverType the type of the approved user EX: user or Role.
     * @param approverName the value of the approver type.
     * @return event ID.
     */
    String addApproversOfRequest(String taskId, String eventId, String workflowId, String approverType, String approverName);

    /**
     * @param eventId
     * @return
     */
    String getApproversOfRequest(String eventId);

    /**
     * @param eventId
     * @return
     */
    String getApproversOfCurrentStep(String eventId);

    /**
     * @param taskId
     * @param eventId
     * @param workflowId
     * @param approverType
     * @param approverName
     */
    void updateApproversOfRequest(String taskId, String eventId, String workflowId, String approverType, String approverName);

    /**
     * @param taskId
     */
    void deleteApproversOfRequest(String taskId);

    /**
     * Add what step to approve.
     *
     * @param eventId     the request ID that need to be checked.
     * @param workflowId  workflow ID.
     * @param currentStep the current step.
     */
    void createStatesOfRequest(String eventId, String workflowId, int currentStep);

    /**
     * Returns the current step given the event ID and workflow ID.
     *
     * @param eventId    the request ID that need to be checked.
     * @param workflowId workflow ID.
     * @return current step value.
     */
    int getStateOfRequest(String eventId, String workflowId);

    /**
     * Updates a state of request given the event ID, workflow ID and current step.
     *
     * @param eventId     the request ID that need to be checked.
     * @param workflowId  workflow ID.
     * @param currentStep the current step.
     */
    void updateStateOfRequest(String eventId, String workflowId, int currentStep);
}
