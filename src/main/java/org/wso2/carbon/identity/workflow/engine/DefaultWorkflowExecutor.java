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
    public void execute(WorkflowRequest workflowRequest) {

        DefaultWorkflowEventRequest defaultWorkflowEventRequest = new DefaultWorkflowEventRequest();
        String eventId = defaultWorkflowEventRequest.addApproversOfRequests(workflowRequest);
        if (eventId != null) {
            defaultWorkflowEventRequest.createStatesOfRequest(eventId, 0);
        }
    }

    @Override
    public String getName() {

        return EXECUTOR_NAME;
    }
}
