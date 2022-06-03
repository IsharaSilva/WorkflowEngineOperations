package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.engine.dao.WorkflowEventRequestDAO;
import org.wso2.carbon.identity.workflow.engine.dao.impl.WorkflowEventRequestDAOImpl;
import org.wso2.carbon.identity.workflow.engine.util.WorkflowEngineConstants;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerService;
import org.wso2.carbon.identity.workflow.mgt.WorkflowExecutorManagerServiceImpl;
import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.bean.WorkflowAssociation;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;
import org.wso2.carbon.identity.workflow.mgt.exception.InternalWorkflowException;

import java.util.List;

public class DefaultApprovalEventRequestService implements DefaultApprovalEventRequest {

    @Override
    public void handleCallBack(String eventId, String status, String approverName) {

        validateApprovers(eventId, approverName);
        WorkflowExecutorManagerService workFlowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        WorkflowRequest request;
        try {
            request = workFlowExecutorManagerService.retrieveWorkflow(eventId);
        } catch (InternalWorkflowException e) {
            throw new RuntimeException(e);
        }
        DefaultWorkflowEventRequest defaultWorkflowEventRequest = new DefaultWorkflowEventRequestService();
        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        List<Parameter> parameterList = defaultWorkflowEventRequest.getParameterList(request);
        String taskId = defaultWorkflowEventRequest.getApprovalOfRequest(eventId);
        if (taskId != null) {
            defaultWorkflowEventRequest.deleteApprovalOfRequest(taskId);
        }
        String workflowId = defaultWorkflowEventRequest.getWorkflowId(request);
        int stepValue = workflowEventRequestDAO.getStateOfRequest(eventId, workflowId);
        if (stepValue < numOfStates(request)) {
            defaultWorkflowEventRequest.addApproversOfRequests(request, parameterList);
        }

            /*        for (int i = 0; i < numOfStates(request); i++) {
            if (!(status.equals(WorkflowEngineConstants.EventState.APPROVED.toString()))) {
                addApproversOfRequests(request, parameterList);
            }*/
    }

    private void validateApprovers(String eventId, String approverName) {

        //TODO
        WorkflowExecutorManagerService workFlowExecutorManagerService = new WorkflowExecutorManagerServiceImpl();
        WorkflowRequest request;
        try {
            //requestId = workFlowExecutorManagerService.getRequestIdOfRelationship(eventId);
            request = workFlowExecutorManagerService.retrieveWorkflow(eventId);
        } catch (InternalWorkflowException e) {
            throw new RuntimeException(e);
        }
        DefaultWorkflowEventRequest defaultWorkflowEventRequest = new DefaultWorkflowEventRequestService();
        WorkflowEventRequestDAO workflowEventRequestDAO = new WorkflowEventRequestDAOImpl();
        String workflowId = defaultWorkflowEventRequest.getWorkflowId(request);
        //int step = defaultWorkflowEventRequest.getStateOfRequest(eventId, workflowId);
        String approver = workflowEventRequestDAO.getApproversOfCurrentStep(eventId);
    }

    private int numOfStates(WorkflowRequest request) {

        DefaultWorkflowEventRequest defaultWorkflowEventRequest = new DefaultWorkflowEventRequestService();
        List<WorkflowAssociation> associations = defaultWorkflowEventRequest.getAssociations(request);
        List<Parameter> parameterList = defaultWorkflowEventRequest.getParameterList(request);
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
}
