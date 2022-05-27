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
     * @param request workflow request object.
     * @return eventId.
     */
    public String addApproversOfRequests(WorkflowRequest request) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        String taskId = UUID.randomUUID().toString();
        String eventId = request.getUuid();
        String workflowId = getWorkflowId(request);
        String approverType = null;
        String approverName = null;
        int currentStep = 0;
        for (Parameter parameter : getParameterList(request)) {
            if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP)) {
                String[] stepName = parameter.getqName().split("-");
                currentStep = Integer.parseInt(stepName[2]);
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
       return workflowEventRequestDAO.addApproversOfRequest(taskId, eventId, workflowId, approverType, approverName);
       /* WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        WorkflowExecutorManagerService workFlowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        String taskId = UUID.randomUUID().toString();
        String eventId = request.getUuid();
        List<WorkflowAssociation> associations;
        try {
            associations = workFlowExecutorManagerService.getWorkflowAssociationsForRequest(
                    request.getEventType(), request.getTenantId());
        } catch (InternalWorkflowException e) {
            throw new WorkflowRuntimeException("The associations are not connecting with any request");
        }
        int currentStep = 0;
        String approverType = null;
        String approverName = null;
        //String workflowId = null;
        Workflow workflow;
        String workflowId = null;
        for (WorkflowAssociation association : associations) {
            try {
                workflow = workflowManagementService.getWorkflow(association.getWorkflowId());
            } catch (WorkflowException e) {
                throw new WorkflowEngineException("The workflow Id is not valid");
            }
            workflowId = workflow.getWorkflowId();
            List<Parameter> parameterList;
            try {
                parameterList = workflowManagementService.getWorkflowParameters(association.getWorkflowId());
            } catch (WorkflowException e) {
                throw new RuntimeException(e);
            }
            for (Parameter parameter : parameterList) {
                if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP)) {
                    String[] stepName = parameter.getqName().split("-");
                    currentStep = Integer.parseInt(stepName[2]);
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
        return workflowEventRequestDAO.addApproversOfRequest(taskId, eventId, workflowId, approverType, approverName);*/
    }

    private String getWorkflowId(WorkflowRequest request) {

        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        List<WorkflowAssociation> associations = getAssociations(request);
        String workflowId = null;
        for (WorkflowAssociation association : associations) {
            try {
                workflowId = String.valueOf(workflowManagementService.getWorkflow(association.getWorkflowId()));
            } catch (WorkflowException e) {
                throw new WorkflowEngineException("The workflow Id is not valid");
            }
        }
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

/*
    private void getApproverList(WorkflowRequest request, Approver approver1){

        int currentStep = 0;
        for (Parameter parameter : getParameterList(request)) {
            if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP)) {
                String[] stepName = parameter.getqName().split("-");
                currentStep = Integer.parseInt(stepName[2]);
                //use split method to get string last word EX: UserAndRole-step-1-roles -> lastWord=roles
               String approverType = stepName[stepName.length - 1];
                approver1.setApproverName(approverType);
            }
            String approver = parameter.getParamValue();
            String[] approvers = approver.split(",");
            if (approvers != null) {
                List<String> approverList = Collections.singletonList(approver);
                String stepValue = WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP + "-step-" +
                        currentStep + "-users";
                if (stepValue.equals(parameter.getqName())) {
                    for (String name : approverList) {
                       String approverName = name;
                       approver1.setApproverName(approverName);
                    }
                }
                stepValue = WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP + "-step-" +
                        currentStep + "-roles";
                if (stepValue.equals(parameter.getqName())) {
                    for (String name : approverList) {
                       String approverName = name;
                        approver1.setApproverName(approverName);
                    }
                }
            }
        }
    }
*/

    /**
     * Identify the current Step.
     *
     * @param workflowRequest the request ID that need to be checked.
     */
    public void createStatesOfRequest(WorkflowRequest workflowRequest) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        String eventId = workflowRequest.getUuid();
        String workflowId = getWorkflowId(workflowRequest);
        int currentStep = 0;
        for (Parameter parameter : getParameterList(workflowRequest)) {
            if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP)) {
                String[] stepName = parameter.getqName().split("-");
                currentStep = Integer.parseInt(stepName[2]);
            }
            workflowEventRequestDAO.createStatesOfRequest(eventId, workflowId, currentStep);
        }
    }

   /* public void getCurrentStep(String eventId) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        workflowEventRequestDAO.getCurrentStep(eventId);
    }
        private boolean isWorkflowEventCompleted(String requestId) throws InternalWorkflowException {

        WorkflowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        List<String> statesOfRequest = workflowExecutorManagerService.getWorkflowStatesOfRequest(requestId);
        for (int i = 0; i < statesOfRequest.size(); i++) {
            if (!statesOfRequest.get(i).equals(WorkflowEngineConstants.EventState.APPROVED.toString())) {
                return false;
            }
        }
        return true;
    }
    private void validateApprovers(String eventId) {

        DefaultWorkflowEventRequestService defaultWorkflowEventRequestService = new DefaultWorkflowEventRequestService();
        //defaultWorkflowEventRequestService.getCurrentStep(eventId);

    }*/

}
