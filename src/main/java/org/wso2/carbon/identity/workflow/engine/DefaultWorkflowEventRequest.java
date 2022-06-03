package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.bean.WorkflowAssociation;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;

import java.util.List;

public interface DefaultWorkflowEventRequest {

    /**
     * Add who approves the relevant request.
     *
     * @param request       workflow request object.
     * @param parameterList parameterList.
     */
    void addApproversOfRequests(WorkflowRequest request, List<Parameter> parameterList);

    /**
     *
     * @param eventId request Id.
     * @return task Id.
     */
    String getApprovalOfRequest(String eventId);

    /**
     *
     * @param taskId unique id of WF_REQUEST_APPROVAL_RELATION
     */
     void deleteApprovalOfRequest(String taskId);

    /**
     *
     * @param eventId request id.
     * @param workflowId workflow id.
     * @param currentStep
     */
    void createStatesOfRequest(String eventId, String workflowId, int currentStep);

    /**
     *
     * @param eventId
     * @param workflowId
     * @return
     */
     int getStateOfRequest(String eventId, String workflowId);

    /**
     *
     * @param eventId
     * @param workflowId
     */
    void updateStateOfRequest(String eventId, String workflowId);

    List<Parameter> getParameterList(WorkflowRequest request);

    List<WorkflowAssociation> getAssociations(WorkflowRequest workflowRequest);
    String getWorkflowId(WorkflowRequest request);
}
