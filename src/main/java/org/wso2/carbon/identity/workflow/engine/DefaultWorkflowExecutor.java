package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;
import org.wso2.carbon.identity.workflow.mgt.workflow.WorkFlowExecutor;

import java.util.List;

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
        defaultWorkflowEventRequest.addApproversOfRequests(request, parameterList);

    }

    public void handleCallBack(String eventId) {

        DefaultWorkflowEventRequestService defaultWorkflowEventRequest = new DefaultWorkflowEventRequestService();
        defaultWorkflowEventRequest.handleCallBack(eventId);
    }

    @Override
    public String getName() {

        return EXECUTOR_NAME;
    }
}
