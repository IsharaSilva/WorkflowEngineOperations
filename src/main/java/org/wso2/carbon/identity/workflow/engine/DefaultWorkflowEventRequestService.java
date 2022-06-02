package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.engine.dao.WorkflowEventRequestDAO;
import org.wso2.carbon.identity.workflow.engine.dao.impl.WorkflowEventRequestDAOImpl;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineException;
import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineRuntimeException;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerServiceImpl;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowManagementServiceImpl;
import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.bean.RequestParameter;
import org.wso2.carbon.identity.workflow.mgt.bean.Workflow;
import org.wso2.carbon.identity.workflow.mgt.bean.WorkflowAssociation;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;
import org.wso2.carbon.identity.workflow.mgt.exception.InternalWorkflowException;
import org.wso2.carbon.identity.workflow.mgt.exception.WorkflowException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DefaultWorkflowEventRequestService {

    /**
     * Add who approves the relevant request.
     *
     * @param request       workflow request object.
     * @param parameterList parameterList.
     */
    public void addApproversOfRequests(WorkflowRequest request, List<Parameter> parameterList) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        String taskId = UUID.randomUUID().toString();
        String eventId = getRequestId(request);
        String workflowId = getWorkflowId(request);
        String approverType = null;
        String approverName = null;
        int currentStep;
        int currentStepValue = getStateOfRequest(eventId, workflowId);
        if (currentStepValue == 0) {
            createStatesOfRequest(eventId, workflowId, currentStepValue);
        }
        List<WorkflowAssociation> associations = getAssociations(request);
        for (WorkflowAssociation association : associations) {
            for (Parameter parameter : parameterList) {
                if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP)
                        && parameter.getParamValue() == null) {
                    String[] stepName = parameter.getqName().split("-");
                    int step = Integer.parseInt(stepName[2]);
                    currentStep = getStateOfRequest(eventId, workflowId);
                    currentStep += 1;
                    updateStateOfRequest(eventId, workflowId);
                    if (currentStep == step) {
                        //use split method to get string last word EX: UserAndRole-step-1-roles -> lastWord=roles
                        approverType = stepName[stepName.length - 1];

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
                    break;
                }
            }
            workflowEventRequestDAO.addApproversOfRequest(taskId, eventId, workflowId, approverType, approverName);
        }
    }

    private String getRequestId(WorkflowRequest request) {

        List<RequestParameter> requestParameter;
        Object event = null;
        for (int i = 0; i < request.getRequestParameters().size(); i++) {
            requestParameter = request.getRequestParameters();
            if (requestParameter.get(i).getName().equals(WorkflowEngineConstants.ParameterName.REQUEST_ID)) {
                event = requestParameter.get(i).getValue();
            }
        }
        return (String) event;
    }

    private String getWorkflowId(WorkflowRequest request) {

        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        List<WorkflowAssociation> associations = getAssociations(request);
        Workflow workflow = null;
        String workflowId;
        for (WorkflowAssociation association : associations) {
            try {
                workflow = workflowManagementService.getWorkflow(association.getWorkflowId());
            } catch (WorkflowException e) {
                throw new WorkflowEngineException("The workflow Id is not valid");
            }
        }
        workflowId = workflow.getWorkflowId();
        return workflowId;
    }

    private List<WorkflowAssociation> getAssociations(WorkflowRequest workflowRequest) {

        List<WorkflowAssociation> associations;
        WorkflowExecutorManagerService workFlowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        try {
            associations = workFlowExecutorManagerService.getWorkflowAssociationsForRequest(
                    workflowRequest.getEventType(), workflowRequest.getTenantId());
        } catch (InternalWorkflowException e) {
            throw new WorkflowEngineRuntimeException("The associations are not connecting with any request");
        }
        return associations;
    }

    public void createStatesOfRequest(String eventId, String workflowId, int currentStep) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        workflowEventRequestDAO.createStatesOfRequest(eventId, workflowId, currentStep);
    }

    public int getStateOfRequest(String eventId, String workflowId) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        return workflowEventRequestDAO.getStateOfRequest(eventId, workflowId);
    }

    public void updateStateOfRequest(String eventId, String workflowId) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();

        int currentStep = getStateOfRequest(eventId, workflowId);
        currentStep += 1;

        workflowEventRequestDAO.updateStateOfRequest(eventId, workflowId, currentStep);
    }

    public void handleCallBack(String eventId) {

        validateApprovers(eventId);
        WorkflowExecutorManagerService workFlowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        String requestId;
        WorkflowRequest request;
        try {
            requestId = workFlowExecutorManagerService.getRequestIdOfRelationship(eventId);
            request = workFlowExecutorManagerService.retrieveWorkflow(requestId);
        } catch (InternalWorkflowException e) {
            throw new RuntimeException(e);
        }
        List<Parameter> parameterList = getParameterList(request);
        String status = null;
        for (int i = 0; i < numOfStates(request); i++) {
            if (!(status.equals(WorkflowEngineConstants.EventState.APPROVED.toString()))) {
                addApproversOfRequests(request, parameterList);
                            /*for (String states : statesOfRequest) {
                if (!states.equals(WorkflowEngineConstants.EventState.APPROVED.toString())) {
                    addApproversOfRequests(request, parameterList);
                }
            }*/
            }
        }
    }

    private void validateApprovers(String eventId) {

        DefaultWorkflowEventRequestService defaultWorkflowEventRequestService = new DefaultWorkflowEventRequestService();
        WorkflowExecutorManagerService workFlowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        String requestId;
        WorkflowRequest request;
        try {
            requestId = workFlowExecutorManagerService.getRequestIdOfRelationship(eventId);
            request = workFlowExecutorManagerService.retrieveWorkflow(requestId);
        } catch (InternalWorkflowException e) {
            throw new RuntimeException(e);
        }
        String workflowId=getWorkflowId(request);
        defaultWorkflowEventRequestService.getStateOfRequest(eventId, workflowId);

    }

    private int numOfStates(WorkflowRequest request) {

        List<WorkflowAssociation> associations = getAssociations(request);
        List<Parameter> parameterList = getParameterList(request);
        int count = 0;
        for (WorkflowAssociation association : associations) {
            for (Parameter parameter : parameterList) {
                if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP)
                        && parameter.getParamValue() == null) {
                    count++;
                }
            }
        }
        return count;
    }

    private List<Parameter> getParameterList(WorkflowRequest request) {

        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        List<WorkflowAssociation> associations = getAssociations(request);
        List<Parameter> parameterList = null;
        for (WorkflowAssociation association : associations) {
            try {
                parameterList = workflowManagementService.getWorkflowParameters(association.getWorkflowId());
            } catch (WorkflowException e) {
                throw new WorkflowEngineException("The parameterList can't get");
            }
        }
        return parameterList;
    }
}

