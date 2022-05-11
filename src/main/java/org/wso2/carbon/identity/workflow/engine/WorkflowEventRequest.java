package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.engine.dao.impl.WorkflowEventRequestDAO;
import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.bean.RequestParameter;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;
import org.wso2.carbon.identity.workflow.mgt.util.WorkflowRequestStatus;
import org.wso2.carbon.identity.workflow.mgt.workflow.WorkFlowExecutor;

import java.util.List;

public class WorkflowEventRequest implements WorkFlowExecutor {

    private List<Parameter> parameterList;
    private static final String EXECUTOR_NAME = "";

    @Override
    public boolean canHandle(WorkflowRequest workflowRequest) {

        return true;
    }

    @Override
    public void initialize(List<Parameter> parameterList) {

        this.parameterList = parameterList;
    }

    @Override
    public void execute(WorkflowRequest workflowRequest) {
            String workflowId;
        String requestId;
        for (RequestParameter parameter : workFlowRequest.getRequestParameters()) {
            if (parameter.isRequiredInWorkflow()) {

                WorkflowEventRequestDAO eventRequestExecutorDAO = new WorkflowEventRequestDAO();
                String approverType;
                String approver;
                eventRequestExecutorDAO.addApproversOfRequest(workflowId, requestId, approverType, approver);
            }
        }

               for (RequestParameter parameter : workFlowRequest.getRequestParameters()) {
            if (parameter.isRequiredInWorkflow()) {
                eventRequestExecutorDAO.addListTypeParam(parameter.getName(), (List<Object>) parameter.getValue());
            }
        }

        List<String> statesOfRequest = workflowRequestAssociationDAO.getWorkflowStatesOfRequest(requestId);
        for (String state : statesOfRequest) {
            if (state.equals(WorkflowRequestStatus.APPROVED.toString())) {
                workflowRequestDAO.updateStatusOfRequest(requestId, state);
            } else {
                EventRequestExecutorDAO eventRequestExecutorDAO = new EventRequestExecutorDAO();
                eventRequestExecutorDAO.addRequestApprover(workflow, requestId, approveType, approveName);
            }
        }


    }

    @Override
    public String getName() {

        return EXECUTOR_NAME;
    }
}
