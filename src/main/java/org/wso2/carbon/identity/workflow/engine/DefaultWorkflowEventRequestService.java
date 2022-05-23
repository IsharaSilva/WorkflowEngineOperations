package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.engine.dao.WorkflowEventRequestDAO;
import org.wso2.carbon.identity.workflow.engine.dao.impl.WorkflowEventRequestDAOImpl;

public class DefaultWorkflowEventRequestService {

    /**
     * Add who approves the relevant request.
     *
     * @param taskId       random generated unique Id.
     * @param eventId      the request ID that need to be checked.
     * @param workflowId   workflow Id.
     * @param approverType the type of the approved user EX: user or Role.
     * @param approverName the value of the approver type.
     */
    public void addApproversOfRequests(String taskId, String eventId, String workflowId, String approverType, String approverName) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        workflowEventRequestDAO.addApproversOfRequest(taskId, eventId, workflowId, approverType, approverName);
    }

    /**
     * Identify the current Step.
     *
     * @param eventId     the request ID that need to be checked.
     * @param workflowId  workflow Id.
     * @param currentStep the current step.
     */
    public void createStatesOfRequest(String eventId, String workflowId, int currentStep) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        workflowEventRequestDAO.createStatesOfRequest(eventId, workflowId, currentStep);
    }

    public void getCurrentStep(String eventId){
        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        workflowEventRequestDAO.getCurrentStep(eventId);
    }
}
