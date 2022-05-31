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
import org.wso2.carbon.identity.workflow.mgt.exception.WorkflowRuntimeException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DefaultWorkflowEventRequestService {

    private List<Parameter> parameterList;

    /**
     * Add who approves the relevant request.
     *
     * @param request       workflow request object.
     * @param parameterList
     */
    public void addApproversOfRequests(WorkflowRequest request, List<Parameter> parameterList) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        WorkflowExecutorManagerService workFlowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        String taskId = UUID.randomUUID().toString();
        List<RequestParameter> requestParameter = request.getRequestParameters();
        Object event = requestParameter.get(6).getValue();
        String eventId = (String) event;
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
        Workflow workflow;
        String workflowId = null;
        for (WorkflowAssociation association : associations) {
            try {
                workflow = workflowManagementService.getWorkflow(association.getWorkflowId());
            } catch (WorkflowException e) {
                throw new WorkflowEngineException("The workflow Id is not valid");
            }
            workflowId = workflow.getWorkflowId();
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
        workflowEventRequestDAO.addApproversOfRequest(taskId, eventId, workflowId, approverType, approverName);
    }

    private Workflow getWorkflowId(WorkflowRequest request) {

        WorkflowManagementService workflowManagementService = new WorkflowManagementServiceImpl();
        List<WorkflowAssociation> associations = getAssociations(request);
        Workflow workflowId = null;
        for (WorkflowAssociation association : associations) {
            try {
                workflowId = workflowManagementService.getWorkflow(association.getWorkflowId());
            } catch (WorkflowException e) {
                throw new WorkflowEngineException("The workflow Id is not valid");
            }
        }
        return workflowId;
    }

/*
    private Map<Integer, Map<String, List<String>>> approvalMap() {

        int currentStep = 0;
        Map<Integer, Map<String, List<String>>> map = new HashMap<Integer, Map<String, List<String>>>();
        for (Parameter parameter : parameterList) {
            if (parameter.getParamName().equals(WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP) && parameter.getParamValue() != null) {
                String[] stepName = parameter.getqName().split("-");
                currentStep = Integer.parseInt(stepName[2]);
                Map<String, List<String>> valueMap = map.get(currentStep);
                if (valueMap == null) {
                    valueMap = new HashMap<String, List<String>>();
                    map.put(currentStep, valueMap);
                }

                String approver = parameter.getParamValue();
                String[] approvers = approver.split(",");
                if (approvers != null) {
                    List<String> approverList = Arrays.asList(approver);
                    String stepValue = WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP + "-step-" +
                            currentStep + "-users";
                    if (stepValue.equals(parameter.getqName())) {
                        //approverName = name;
                        valueMap.put("users", approverList);
                    }
                    stepValue = WorkflowEngineConstants.ParameterName.USER_AND_ROLE_STEP + "-step-" +
                            currentStep + "-roles";
                    if (stepValue.equals(parameter.getqName())) {
                        //approverName = name;
                        valueMap.put("roles", approverList);
                    }
                }
            }
        }
        return map;
    }
*/

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

    /**
     * Identify the current Step.
     *
     * @param workflowRequest the request ID that need to be checked.
     */
    public void createStatesOfRequest(WorkflowRequest workflowRequest) {

        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        String eventId = workflowRequest.getUuid();
        String workflowId = String.valueOf(getWorkflowId(workflowRequest));
        int currentStep = 0;

       /* for (int i = 1; i <= approvalStepMap.size(); i++) {
            currentStep = i;
            workflowEventRequestDAO.createStatesOfRequest(eventId, workflowId, currentStep);
        }*/
    }
}
