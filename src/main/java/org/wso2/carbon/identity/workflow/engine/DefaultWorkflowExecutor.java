package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineClientException;
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
import org.wso2.carbon.identity.workflow.mgt.workflow.WorkFlowExecutor;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DefaultWorkflowExecutor implements WorkFlowExecutor {

    List<Parameter> parameterList;
    private static final String EXECUTOR_NAME = "DefaultWorkflowEngine";

    @Override
    public boolean canHandle(WorkflowRequest workflowRequest) {

        return true;
    }

    @Override
    public void initialize(List<Parameter> parameterList) {

        this.parameterList = parameterList;
    }

    @Override
    public void execute(WorkflowRequest request) {

        DefaultWorkflowEventRequestService defaultWorkflowEventRequest = new DefaultWorkflowEventRequestService();
        String taskId = UUID.randomUUID().toString();
        String eventId = request.getUuid();
        List<WorkflowAssociation> associations;
        WorkflowExecutorManagerService workflowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        try {
            associations = workflowExecutorManagerService.getWorkflowAssociationsForRequest(
                    request.getEventType(), request.getTenantId());
        } catch (InternalWorkflowException e) {
            throw new WorkflowEngineException("The associations are not connecting with any request");
        }
        String workflowId = null;
        String approverType = null;
        String approverName = null;
        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        for (WorkflowAssociation association : associations) {
            try {
                workflowId = String.valueOf(workflowManagementService.getWorkflow(association.getWorkflowId()));
                parameterList = workflowManagementService.getWorkflowParameters(association.getWorkflowId());
            } catch (WorkflowException e) {
                throw new WorkflowEngineException("The workflow Id is not valid");
            }
            int currentStep = 0;
            for (Parameter parameter : parameterList) {
                if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP)) {
                    String[] stepName = parameter.getqName().split("-");
                    currentStep = Integer.parseInt(stepName[2]);
                    defaultWorkflowEventRequest.createStatesOfRequest(eventId, workflowId, currentStep);
                    //use split method to get string last word EX: UserAndRole-step-1-roles -> lastWord=roles
                    approverType = stepName[stepName.length - 1];
                }
                String approver = parameter.getParamValue();
                String[] approvers = approver.split(",");
                if (approvers != null) {
                    List<String> approverList = Collections.singletonList(approver);
                    String stepValue = WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP + "-step-" +
                            currentStep + "-users";
                    if (stepValue.equals(parameter.getqName())) {
                        for (String name : approverList) {
                            approverName = name;
                        }
                    }
                    stepValue = WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP + "-step-" +
                            currentStep + "-roles";
                    if (stepValue.equals(parameter.getqName())) {
                        for (String name : approverList) {
                            approverName = name;
                        }
                    }
                }
            }
        }
        defaultWorkflowEventRequest.addApproversOfRequests(taskId, eventId, workflowId, approverType, approverName);
    }

    private void validateApprovers(String eventId) throws WorkflowEngineClientException {

        DefaultWorkflowEventRequestService defaultWorkflowEventRequestService = new DefaultWorkflowEventRequestService();
        defaultWorkflowEventRequestService.getCurrentStep(eventId);
    }

    private boolean isWorkflowEventCompleted(String requestId) throws InternalWorkflowException {

        WorkflowExecutorManagerService workflowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        List<String> statesOfRequest = workflowExecutorManagerService.getWorkflowStatesOfRequest(requestId);
        for (int i = 0; i < statesOfRequest.size(); i++) {
            if (!statesOfRequest.get(i).equals(WorkflowEngineConstants.EventState.APPROVED.toString())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {

        return EXECUTOR_NAME;
    }
}
