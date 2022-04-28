package org.wso2.carbon.identity.workflow.engine.util;

import org.wso2.carbon.identity.workflow.engine.exception.WorkflowEngineClientException;

public class WorkflowEngineExceptionManagementUtil {
    public static WorkflowEngineClientException handleClientException(
            WorkflowEngineConstants.ErrorMessage error) {

        String message = error.getMessage();
        return new WorkflowEngineClientException(message);
    }
}
