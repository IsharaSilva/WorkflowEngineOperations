package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.engine.dao.WorkflowEventRequestDAO;
import org.wso2.carbon.identity.workflow.engine.dao.impl.WorkflowEventRequestDAOImpl;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineException;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerServiceImpl;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementServiceImpl;
import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.bean.WorkflowAssociation;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;
import org.wso2.carbon.identity.workflow.mgt.exception.InternalWorkflowException;
import org.wso2.carbon.identity.workflow.mgt.exception.WorkflowException;

import java.util.Collections;
import java.util.List;

public class DefaultWorkflowEventRequest {

    private List<Parameter> parameterList;
    WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();

    /**
     * Add who approves the relevant request.
     *
     * @param request      request object from WorkflowRequest.
     * @param workflowId   workflow ID.
     * @param approverType the type of the approved user EX: user or Role.
     * @param approverName the value of the approver type.
     * @return eventId.
     */
    public String addApproversOfRequests(WorkflowRequest request, String workflowId, String approverType, String approverName) {

        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        String eventId = request.getUuid();
        List<WorkflowAssociation> associations;
        WorkflowExecutorManagerService workflowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        try {
            associations = workflowExecutorManagerService.getWorkflowAssociationsForRequest(
                    request.getEventType(), request.getTenantId());
        } catch (InternalWorkflowException e) {
            throw new WorkflowEngineException("The associations are not connecting with any request");
        }
        for (WorkflowAssociation association : associations) {
            try {
                workflowId = String.valueOf(workflowManagementService.getWorkflow(association.getWorkflowId()));
            } catch (WorkflowException e) {
                throw new WorkflowEngineException("The workflow Id is not valid");
            }
            for (Parameter parameter : this.parameterList) {
                if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP)) {
                    //use split method to get string last word EX: UserAndRole-step-1-roles -> lastWord=roles
                    String[] stepName = parameter.getqName().split("-");
                    approverType = stepName[stepName.length - 1];
                }
                String approver = parameter.getParamValue();
                String[] approvers = approver.split(",");
                if (approvers != null) {
                    List<String> approverList = Collections.singletonList(approver);
                    for (String name : approverList) {
                        approverName = name;
                    }
                }
            }
        }
        return workflowEventRequestDAO.addApproversOfRequest(eventId, workflowId, approverType, approverName);

    }

    /**
     * Identify the current Step.
     *
     * @param eventId     the request ID that need to be checked.
     * @param currentStep the current step.
     */
    public void createStatesOfRequest(String eventId, int currentStep) {

        for (Parameter parameter : this.parameterList) {
            if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP)) {
                String[] stepName = parameter.getqName().split("-");
                currentStep = Integer.parseInt(stepName[2]);
            }
        }
        workflowEventRequestDAO.createStatesOfRequest(eventId, currentStep);
    }
}
