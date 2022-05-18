package org.wso2.carbon.identity.workflow.engine;

import org.wso2.carbon.identity.workflow.mgt.bean.Parameter;
import org.wso2.carbon.identity.workflow.mgt.dto.WorkflowRequest;
import org.wso2.carbon.identity.workflow.mgt.workflow.WorkFlowExecutor;

import java.util.List;

public class DefaultWorkflowExecutor implements WorkFlowExecutor {

    private static final String EXECUTOR_NAME = "";

    @Override
    public boolean canHandle(WorkflowRequest workflowRequest) {

        return true;
    }

    @Override
    public void initialize(List<Parameter> parameterList) {
    }

    @Override
    public void execute(WorkflowRequest workflowRequest) {

        DefaultWorkflowEventRequest defaultWorkflowEventRequest = new DefaultWorkflowEventRequest();
        String eventId = defaultWorkflowEventRequest.addApproversOfRequests(workflowRequest, null
                , null, null);

        if (eventId != null) {
            defaultWorkflowEventRequest.createStatesOfRequest(eventId, 0);
        }
    }

    @Override
    public String getName() {

        return EXECUTOR_NAME;
    }
}
